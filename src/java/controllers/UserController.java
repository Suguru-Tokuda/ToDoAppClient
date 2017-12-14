/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import api.UserAPI;
import api.UserStore;
import email.Email;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    Email emailSender;

    // referred from: https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // referred from: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@#$%^&+=])(?=\\S+$).{8,}$");
    public static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^(?=\\S+$).{6,}$");

    private User tempUser;
    private List<User> tempUserList;

    private String emailInSession;
    private String usernameInSession;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewIndex(HttpSession session, Model model) {
        emailInSession = (String) session.getAttribute("email");
        if (emailInSession != null) {
            return "redirect:/getlists";
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
        emailInSession = (String) session.getAttribute("userid");
        if (emailInSession != null) {
            return "redirect:/getlists";
        } else {
            return "signup";
        }
    }

    @RequestMapping(value = "/processsignin", method = RequestMethod.POST)
    public String processSignin(@RequestParam("email") String email, @RequestParam("signinPassword") String password, Model model, HttpSession session) throws ParseException, IOException, org.json.simple.parser.ParseException {
        emailInSession = (String) session.getAttribute("email");
        if (emailInSession != null) {
            return "redirect:/getlists";
        }
        String signinErrorMsg = "";
        boolean isFilled = !email.isEmpty() && !password.isEmpty();
        if (!isFilled) {
            signinErrorMsg = "Fill blanks";
            model.addAttribute("signinErrorMsg", signinErrorMsg);
            return "index";
        } else {
            boolean isLoggedin = setToLoggedin(email, password);
            if (isLoggedin) {
                tempUser = this.getUser(email);
                // put the user id into session. This value will be used everywhere.
                session.setAttribute("email", tempUser.getEmail());
                session.setAttribute("userid", tempUser.getId());
                // goes to index
                return "redirect:/getlists";
            } else {
                signinErrorMsg = "User name and password don't match. Try Again.";
                model.addAttribute("signinErrorMsg", signinErrorMsg);
                return "index";
            }
        }
    }

    @RequestMapping(value = "/processsignup", method = RequestMethod.POST)
    public String processSignup(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("email") String email, @RequestParam("signupPassword") String password, @RequestParam("confPassword") String confPassword, Model model) throws org.json.simple.parser.ParseException, IOException {
        String signupErrorMsg = "";
        boolean isFilled = !firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confPassword.isEmpty();
        if (!isFilled) {
            signupErrorMsg = "Fill the blanks";
            model.addAttribute("signupErrorMsg", signupErrorMsg);
            return "signup";
        }
        firstname = firstname.trim();
        firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1);
        lastname = lastname.trim();
        lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1);
        email = email.trim();
        boolean isGoodName = this.isGoodName(firstname) && this.isGoodName(lastname);
        boolean isGoodEmail = this.isGoodEmail(email);
        boolean isGoodPassword = this.isGoodPassword(password);
        boolean passConfPassMatch = password.equals(confPassword);

        if (isGoodEmail && isGoodPassword && passConfPassMatch) {
            tempUser = new User(firstname, lastname, email, password);
            userAPI.postUser(tempUser);
            // have to send a confirmation email.
            if (emailSender.sendConfirmationEmail(tempUser)) {
                model.addAttribute("user", tempUser);
                return "confirmemailsent";
            } else {
                model.addAttribute("user", tempUser);
                return "resendconfirmemail";
            }
        } else {
            if (!isGoodName) {
                signupErrorMsg += "Name has to have at least 2 characters.<br>";
            }
            if (!isGoodEmail) {
                signupErrorMsg += "Invalid email or already in use.<br>";
            }
            if (!isGoodPassword) {
                signupErrorMsg += "Invalid password.<br>";
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

    @RequestMapping(value = "/confirmregistration/{userid}", method = RequestMethod.POST)
    public String confirmRegistration(@PathVariable("userid") String userid, Model model) {
        tempUser = userStore.getUserForUsername(userid);
        if (!tempUser.isConfirmed()) {
            tempUser.setConfirmed(true);
            userAPI.putUser(tempUser, userid);
            return this.showSignupSuccess(tempUser, model);
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/resendconfemail", method = RequestMethod.POST)
    public String resendConfEmail(Model model, @RequestParam("email") String email, @RequestParam("firstname") String firstname, @RequestParam("lastmame") String lastname, @RequestParam("password") String password) {
        tempUser = new User(firstname, lastname, email, password);
        emailSender.sendConfirmationEmail(tempUser);
        model.addAttribute("user", tempUser);
        return "confirmemailsent";
    }

    private boolean setToLoggedin(String email, String password) throws org.json.simple.parser.ParseException, IOException {
        tempUserList = userStore.getAllUsers();
        Iterator<User> it = tempUserList.iterator();
        boolean emailMatches = false;
        boolean passwordMatches = false;
        boolean loggedin = false;
        while (it.hasNext()) {
            tempUser = it.next();
            emailMatches = tempUser.getEmail().equalsIgnoreCase(email);
            passwordMatches = tempUser.getPassword().equals(password);
            if (emailMatches && passwordMatches) {
                loggedin = emailMatches && passwordMatches;
                break;
            }
        }
        return loggedin;
    }

    private User getUser(String userid) {
        Iterator<User> it = tempUserList.iterator();
        while (it.hasNext()) {
            tempUser = it.next();
            if (tempUser.getEmail().equalsIgnoreCase(userid)) {
                return tempUser;
            }
        }
        return null;
    }

    private boolean isGoodPassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    private boolean isGoodEmail(String email) {
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

    private boolean isGoodName(String name) {
        return name.length() > 1;
    }

}
