/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import api.UserAPI;
import api.UserStore;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller handles all the user related activities. 1. Register for
 * Account 2. Sign in 4. Invite a (Family) Member to List
 *
 * @author Suguru
 */
@Controller
public class UserController {

    @Autowired
    UserStore userStore;
    @Autowired
    UserAPI userAPI;

    // referred from: https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // referred from: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@#$%^&+=])(?=\\S+$).{8,}$");
    public static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^(?=\\S+$).{6,}$");

    private User tempUser;
    private List<User> tempUserList;

    private String useridInSession;
    private String usernameInSession;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewIndex(HttpSession session, Model model) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession != null) {
            return "redirect:/showlists";
        }
        return "index";
    }

    /*
    Used to log out from the session.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    private String logout(HttpSession session) {
        session.setAttribute("username", null);
        return "redirect:/";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignup(HttpSession session) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession != null) {
            return "redirect:/showlists";
        } else {
            return "signup";
        }
    }

    @RequestMapping(value = "/processsignin", method = RequestMethod.POST)
    public String processSignin(@RequestParam("userid") String userid, @RequestParam("signinPassword") String password, Model model, HttpSession session) throws ParseException, IOException, org.json.simple.parser.ParseException {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession != null) {
            return "redirect:/showlists";
        }
        String signinErrorMsg = "";
        boolean isFilled = !userid.isEmpty() && !password.isEmpty();
        if (!isFilled) {
            signinErrorMsg = "Fill blanks";
            model.addAttribute("signinErrorMsg", signinErrorMsg);
            return "index";
        } else {
            boolean isLoggedin = setToLoggedin(userid, password);
            if (isLoggedin) {
                tempUser = this.getUser(userid);
                // put the user id into session. This value will be used everywhere.
                session.setAttribute("username", tempUser.getUsername());
                session.setAttribute("userid", tempUser.getId());
                // goes to index
                return "redirect:/getlists";
            } else {
                signinErrorMsg = "User name and password don't match. Try Again.";
                model.addAttribute("signinErrorMsg", signinErrorMsg);
                return "signupSignin";
            }
        }
    }

    @RequestMapping(value = "/processsignup", method = RequestMethod.POST)
    public String processSignup(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("signupPassword") String password, @RequestParam("confPassword") String confPassword, Model model) throws org.json.simple.parser.ParseException, IOException {
        username = username.trim();
        email = email.trim();

        String signupErrorMsg = "";
        boolean isFilled = !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confPassword.isEmpty();

        if (!isFilled) {
            signupErrorMsg = "Fill the blanks";
            model.addAttribute("signupErrorMsg", signupErrorMsg);
            return "signup";
        }

        boolean isGoodEmail = this.isGoodEmail(email);
        boolean isGoodUsername = this.isGoodUsername(username);
        boolean isGoodPassword = this.isGoodPassword(password);
        boolean passConfPassMatch = password.equals(confPassword);

        if (isGoodEmail && isGoodUsername && isGoodPassword && passConfPassMatch) {
            tempUser = new User(username.toLowerCase(), email.toLowerCase(), password);
            userAPI.postUser(tempUser);
            return this.showSignupSuccess(tempUser, model);
        } else {
            if (!isGoodEmail) {
                signupErrorMsg += "Invalid email or already in use<br>";
            }
            if (!isGoodUsername) {
                signupErrorMsg += "Username already in use<br>";
            }
            if (!isGoodPassword) {
                signupErrorMsg += "Invalid password<br>";
            }
            if (!passConfPassMatch) {
                signupErrorMsg += "Password and confirm password don't match";
            }
        }
        model.addAttribute("signupErrorMsg", signupErrorMsg);
        return "signup";
    }

    private String showSignupSuccess(User user, Model model) {
        model.addAttribute("user", user);
        return "signupsuccess";
    }

    private boolean setToLoggedin(String userid, String password) throws org.json.simple.parser.ParseException, IOException {
        tempUserList = userStore.getAllUsers();
        Iterator<User> it = tempUserList.iterator();
        boolean emailMatches = false;
        boolean usernameMatches = false;
        boolean passwordMatches = false;
        boolean loggedin = false;
        while (it.hasNext()) {
            tempUser = it.next();
            emailMatches = tempUser.getEmail().equalsIgnoreCase(userid);
            usernameMatches = tempUser.getUsername().equalsIgnoreCase(userid);
            passwordMatches = tempUser.getPassword().equals(password);
            if ((emailMatches || usernameMatches) && passwordMatches) {
                loggedin = (emailMatches || usernameMatches) && passwordMatches;
                break;
            }
        }
        return loggedin;
    }

    private User getUser(String userid) {
        Iterator<User> it = tempUserList.iterator();
        while (it.hasNext()) {
            tempUser = it.next();
            if (tempUser.getUsername().equalsIgnoreCase(userid) || tempUser.getEmail().equalsIgnoreCase(userid)) {
                return tempUser;
            }
        }
        return null;
    }

    private boolean isGoodPassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    private boolean isGoodEmail(String email) throws org.json.simple.parser.ParseException, IOException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        // also needs to check if the email is in use already.
        tempUserList = userStore.getAllUsers();
        boolean retVal = true;
        Iterator<User> it = tempUserList.iterator();
        while (it.hasNext()) {
            tempUser = it.next();
            if (tempUser.getEmail().equals(email)) {
                retVal = false;
                break;
            }
        }
        return matcher.find() && retVal;
    }

    private boolean isGoodUsername(String username) {
        Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
        // also needs to check if the username is in use already.
        boolean retVal = true;
        Iterator<User> it = tempUserList.iterator();
        while (it.hasNext()) {
            tempUser = it.next();
            if (tempUser.getUsername().toLowerCase().equals(username.toLowerCase())) {
                retVal = false;
                break;
            }
        }
        return matcher.find() && retVal;
    }

}
