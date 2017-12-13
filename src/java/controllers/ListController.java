/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import api.ItemAPI;
import api.ItemStore;
import api.ToDoListAPI;
import api.ToDoListStore;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import models.ToDoList;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller handles all the list related activities.
 * 3. Create and Add to List
 * 5. Sync with Cloud
 * 6. Prioritize List Items
 * 7. Support Multiple Lists
 * 8. Build List from History
 * 
 * @author Suguru
 */
@Controller
public class ListController {
    
    @Autowired
    ToDoListStore toDoListStore;
    @Autowired
    ToDoListAPI listAPI;
    @Autowired
    ItemStore itemStore;
    @Autowired
    ItemAPI itemAPI;
    
    String useridInSession;
    List<ToDoList> tempToDoList;
    
    @RequestMapping(value = "/getlists", method = RequestMethod.GET)
    public String showLists(Model model, HttpSession session) throws IOException, ParseException {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        }
        
        tempToDoList = toDoListStore.getToDoListsForUserid(useridInSession);
        model.addAttribute("lists", tempToDoList);
        return "viewlist";
    }    
    
}
