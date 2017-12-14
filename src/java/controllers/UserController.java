/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import api.InvitationAPI;
import api.InvitationStore;
import api.ListAssignmentAPI;
import api.ToDoListStore;
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
import models.Invitation;
import models.Item;
import models.ListAssignment;
import models.ToDoList;
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
    String useridInSession;
    @Autowired
    ToDoListStore toDoListStore;
    @Autowired
    InvitationAPI invitationAPI;
    @Autowired
    InvitationStore invitationStore;
    ToDoList tempToDoListVal;
    List<Item> tempItemList;
    String itemid;
    String todolistid;
    Invitation tempInvitation;
    List<Invitation> tempInvitationsList;
    ListAssignment tempListAssignment;

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
        usernameInSession = (String) session.getAttribute("userid");
        if (usernameInSession != null) {
            return "redirect:/getlists";
        }
        return "index";
    }

    /*
    Used to log out from the session.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    private String logout(HttpSession session) {
        session.removeAttribute("userid");
        session.removeAttribute("email");
        return "redirect:/";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignup(HttpSession session) {
        usernameInSession = (String) session.getAttribute("userid");
        if (usernameInSession != null) {
            return "redirect:/getlists";
        } else {
            return "signup";
        }
    }

    @RequestMapping(value = "/processsignin", method = RequestMethod.POST)
    public String processSignin(@RequestParam("email") String email, @RequestParam("signinPassword") String password, Model model, HttpSession session) throws ParseException, IOException, org.json.simple.parser.ParseException {
        usernameInSession = (String) session.getAttribute("userid");
        if (usernameInSession != null) {
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
            tempUser = userStore.getUserByEmail(email);
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

    @RequestMapping(value = "/confirmregistration/{userid}", method = RequestMethod.GET)
    public String confirmRegistration(@PathVariable("userid") String userid, Model model) {
        tempUser = userStore.getUserById(userid);
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
            if (tempUser.getEmail().equalsIgnoreCase(email)) {
                retVal = false;
                break;
            }
        }
        return matcher.find() && retVal;
    }

    private boolean isGoodEmailToInvite(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        // also needs to check if the email is in use already.
        tempUserList = userStore.getAllUsers();
        boolean retVal = false;
        Iterator<User> it = tempUserList.iterator();
        while (it.hasNext()) {
            tempUser = it.next();
            if (tempUser.getEmail().equalsIgnoreCase(email)) {
                retVal = true;
                break;
            }
        }
        return matcher.find() && retVal;
    }

    private boolean isGoodName(String name) {
        return name.length() > 1;
    }

    @RequestMapping(value = "/invite/{todolistid}", method = RequestMethod.GET)
    public String showInvitationPage(@PathVariable("todolistid") String todolistid, Model model, HttpSession session) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            tempToDoListVal = toDoListStore.getToDoListById(todolistid);
            this.todolistid = tempToDoListVal.getId();
            model.addAttribute("toDoList", tempToDoListVal);
            return "invite";
        }
    }

    @RequestMapping(value = "/sendinvitation", method = RequestMethod.POST)
    public String sendInvitation(@RequestParam("receiverEmail") String receiverEmail, @RequestParam("todolistid") String todolistid, Model model, HttpSession session) {
        String errorMsg = "";
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            tempUser = userStore.getUserById(useridInSession);
            if (tempUser.getEmail().equalsIgnoreCase(receiverEmail)) {
                errorMsg = "you cannot invite yourself.";
                model.addAttribute("errorMsg", errorMsg);
                tempToDoListVal = toDoListStore.getToDoListById(todolistid);
                this.todolistid = tempToDoListVal.getId();
                model.addAttribute("toDoList", tempToDoListVal);
                return "invite";
            }
            if (receiverEmail.isEmpty()) {
                errorMsg = "Fill in the blank.";
                model.addAttribute("errorMsg", errorMsg);
                tempToDoListVal = toDoListStore.getToDoListById(todolistid);
                this.todolistid = tempToDoListVal.getId();
                model.addAttribute("toDoList", tempToDoListVal);
                return "invite";
            }
            boolean isGoodEmail = this.isGoodEmailToInvite(receiverEmail);
            if (!isGoodEmail) {
                errorMsg = "Invalid email";
                model.addAttribute("errorMsg", errorMsg);
                tempToDoListVal = toDoListStore.getToDoListById(todolistid);
                this.todolistid = tempToDoListVal.getId();
                model.addAttribute("toDoList", tempToDoListVal);
                return "invite";
            } else {
                tempUser = userStore.getUserById(useridInSession);
                User receiver = userStore.getUserByEmail(receiverEmail);
                tempInvitation = new Invitation(tempUser.getId(), receiver.getId(), todolistid);
                invitationAPI.postInvitation(tempInvitation);
                emailSender.sendInvitationEmail(tempUser, receiver, tempToDoListVal);
                model.addAttribute("receiver", receiver);
                return "invitationsentconfirm";
            }
        }
    }

    @RequestMapping(value = "/confirminvitation/{todolistid}")
    public String confirmInvitation(@PathVariable("todolistid") String todolistid, HttpSession session, Model model) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            // get invitation object by todolistid
            tempInvitationsList = invitationStore.getInvitationsByReceiverId(useridInSession, todolistid);
            tempInvitation = tempInvitationsList.get(0);
            // make an assignment - userid, todolistid
            tempListAssignment = new ListAssignment(useridInSession, todolistid);
            return "redirect:/getlists";
        }
    }

}
