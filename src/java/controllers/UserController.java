/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import api.UserStore;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
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

    // referred from: https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // referred from: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@#$%^&+=])(?=\\S+$).{8,}$");
    public static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^(?=\\S+$).{6,}$");

    private User tempUser;
    private List<User> tempUserList;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewIndex() {
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

    @RequestMapping(value = "/processsignin", method = RequestMethod.POST)
    public String processSignin(@RequestParam("userid") String userid, @RequestParam("signinPassword") String password, Model model, HttpSession session) throws ParseException, IOException, org.json.simple.parser.ParseException {
        if (session == null) {
            System.out.println("session is null");
        } else {
            System.out.println("session is not null");
        }
        String signinErrorMsg = "";
        boolean isFilled = !userid.isEmpty() && !password.isEmpty();
        if (!isFilled) {
            signinErrorMsg = "Fill blanks";
            model.addAttribute("signinErrorMsg", signinErrorMsg);
            return "signupSignin";
        } else {
            boolean isLoggedin = setToLoggedin(userid, password);

            if (isLoggedin) {
                tempUser = this.getUser(userid);
                // put the user id into session. This value will be used everywhere.
                session.setAttribute("username", tempUser.getUsername());
                // goes to index
                return "redirect:/getlists";
            } else {
                signinErrorMsg = "User name and password don't match. Try Again.";
                model.addAttribute("signinErrorMsg", signinErrorMsg);
                return "signupSignin";
            }
        }
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
        while(it.hasNext()) {
            tempUser = it.next();
            if (tempUser.getId().equalsIgnoreCase(userid)) {
                return tempUser;
            }
        }
        return null;        
    }

}
