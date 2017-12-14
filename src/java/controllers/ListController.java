/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import api.ItemAPI;
import api.ItemStore;
import api.ListAssignmentAPI;
import api.ToDoListAPI;
import api.ToDoListStore;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import models.ListAssignment;
import models.ToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Calendar;
import java.util.Collections;
import models.Item;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This controller handles all the list related activities. 3. Create and Add to
 * List 5. Sync with Cloud 6. Prioritize List Items 7. Support Multiple Lists 8.
 * Build List from History
 *
 * @author Suguru
 */
@Controller
public class ListController {

    @Autowired
    ToDoListStore toDoListStore;
    @Autowired
    ToDoListAPI toDoListAPI;
    @Autowired
    ItemStore itemStore;
    @Autowired
    ItemAPI itemAPI;
    @Autowired
    ListAssignmentAPI listAssignmentAPI;
    String useridInSession;
    List<ToDoList> tempToDoList;
    ToDoList tempToDoListVal;
    List<Item> tempItemList;
    Item tempItem;
    String itemid;
    String todolistid;

    @RequestMapping(value = "/getlists", method = RequestMethod.GET)
    public String showLists(Model model, HttpSession session) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        }
        tempToDoList = toDoListStore.getActiveToDoListsForUserid(useridInSession);
        int index = 0;
        String dateStr = null;
        for (int i = 0; i < tempToDoList.size(); i++) {
            tempToDoListVal = tempToDoList.get(i);
            index = tempToDoListVal.getCreatedate().indexOf("T");
            dateStr = tempToDoListVal.getCreatedate().substring(0, index);
            tempToDoListVal.setCreatedate(dateStr);
            tempToDoList.set(i, tempToDoListVal);
        }
        model.addAttribute("lists", tempToDoList);
        return "viewlist";
    }

    @RequestMapping(value = "/getlistdetails/{todolistid}", method = RequestMethod.GET)
    public String showListDetails(@PathVariable("todolistid") String todolistid, Model model, HttpSession session) throws IOException {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            this.todolistid = todolistid;
            tempItemList = itemStore.getItemsByToDoListId(todolistid);
            Collections.sort(tempItemList, (o1, o2) -> {
                boolean v1 = ((Item) o1).isImportant();
                boolean v2 = ((Item) o2).isImportant();
                if (v1 && v2) {
                    return 0;
                } else if (v1 && !v2) {
                    return -1;
                } else {
                    return 1;
                }
            });
            tempToDoListVal = toDoListStore.getToDoListById(todolistid);
            model.addAttribute("toDoList", tempToDoListVal);
            model.addAttribute("itemList", tempItemList);
            return "listdetails";
        }
    }

    @RequestMapping(value = "/remove/{todolistid}", method = RequestMethod.GET)
    public String removeList(@PathVariable("todolistid") String todolistid, Model model, HttpSession session) throws IOException {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            tempToDoListVal = toDoListStore.getToDoListById(todolistid);
            String createdateStr = tempToDoListVal.getCreatedate() + "T00:00:00";
            tempToDoListVal.setActive(false);
            tempToDoListVal.setCreatedate(createdateStr);
            toDoListAPI.putToDoList(tempToDoListVal, todolistid);
            return "redirect:/getlists";
        }
    }

    @RequestMapping(value = "/createfromhistory", method = RequestMethod.POST)
    public String createFromHistory(@RequestParam("todolistid") String todolistid, Model model, HttpSession session) throws IOException {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            this.todolistid = todolistid;
            tempToDoListVal = toDoListStore.getToDoListById(todolistid);
            String createdateStr = tempToDoListVal.getCreatedate() + "T00:00:00";
            tempToDoListVal.setActive(true);
            tempToDoListVal.setCreatedate(createdateStr);
            toDoListAPI.putToDoList(tempToDoListVal, todolistid);
            tempItemList = itemStore.getItemsByToDoListId(todolistid);
            for (int i = 0; i < tempItemList.size(); i++) {
                tempItem = tempItemList.get(i);
                tempItem.setFinished(false);
                tempItem.setImportant(false);
                tempItem.setDue(tempItem.getDue() + "T00:00:00");
                tempItemList.set(i, tempItem);
                itemAPI.putItem(tempItem, tempItem.getId());
            }
            model.addAttribute("itemList", tempItemList);
            model.addAttribute("toDoList", tempToDoListVal);
            return "listdetails";
        }
    }

    @RequestMapping(value = "/addnewitem", method = RequestMethod.POST)
    public String addNewItem(@RequestParam("itemname") String itemname, @RequestParam("due") String due, @RequestParam("important") boolean important, Model model, HttpSession session) {
        String errorMsg = "";
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            if (itemname.isEmpty() && due.isEmpty()) {
                errorMsg = "Fill blank";
                model.addAttribute("errorMsg", errorMsg);
                return "listdetails";
            } else {
                itemname = itemname.trim();
                due = due + "T00:00:00";
                itemAPI.postItem(new Item(todolistid, itemname, due, important, false));
                tempItemList = itemStore.getItemsByToDoListId(todolistid);
                Collections.sort(tempItemList, (o1, o2) -> {
                    boolean v1 = ((Item) o1).isImportant();
                    boolean v2 = ((Item) o2).isImportant();
                    if (v1 && v2) {
                        return 0;
                    } else if (v1 && !v2) {
                        return -1;
                    } else {
                        return 1;
                    }
                });
                model.addAttribute("toDoList", tempToDoListVal);
                model.addAttribute("itemList", tempItemList);
                return "listdetails";
            }
        }
    }

    @RequestMapping(value = "/viewitem/{itemid}", method = RequestMethod.GET)
    public String viewItem(@PathVariable("itemid") String itemid, Model model, HttpSession session) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            this.itemid = itemid;
            tempItemList = itemStore.getItemsByToDoListId(todolistid);
            Iterator<Item> it = tempItemList.iterator();
            while (it.hasNext()) {
                tempItem = it.next();
                if (tempItem.getId().equals(itemid)) {
                    break;
                }
            }
            Collections.sort(tempItemList, (o1, o2) -> {
                boolean v1 = ((Item) o1).isImportant();
                boolean v2 = ((Item) o2).isImportant();
                if (v1 && v2) {
                    return 0;
                } else if (v1 && !v2) {
                    return -1;
                } else {
                    return 1;
                }
            });
            model.addAttribute("itemList", tempItemList);
            model.addAttribute("itemToView", tempItem);
            model.addAttribute("toDoList", tempToDoListVal);
            return "listdetails";
        }
    }

    @RequestMapping(value = "/updateitem", method = RequestMethod.POST)
    public String updateItem(@RequestParam("itemname") String itemname, @RequestParam("due") String newdue, @RequestParam(value = "important", required = false) boolean important, @RequestParam(value = "finished", required = false) boolean finished, Model model, HttpSession session) {
        String errorMsg = "";
        String notificationMsg = "";
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            if (itemname.isEmpty() && newdue.isEmpty()) {
                errorMsg = "Fill blank to update";
                model.addAttribute("errorMsg", errorMsg);
                return "listdetails";
            } else {
                tempItemList = itemStore.getItemsByToDoListId(todolistid);
                for (int i = 0; i < tempItemList.size(); i++) {
                    if (tempItemList.get(i).getId().equals(itemid)) {
                        tempItem = tempItemList.get(i);
                        tempItem.setDue(newdue + "T00:00:00");
                        tempItem.setFinished(finished);
                        tempItem.setImportant(important);
                        tempItem.setItemname(itemname);
                        tempItemList.set(i, tempItem);
                        itemAPI.putItem(tempItem, tempItem.getId());
                        break;
                    }
                }
                int index = 0;
                String dateStr = null;
                for (int i = 0; i < tempItemList.size(); i++) {
                    tempItem = tempItemList.get(i);
                    index = tempItem.getDue().indexOf("T");
                    if (index != -1) {
                        dateStr = tempItem.getDue().substring(0, index);
                        tempItem.setDue(dateStr);
                        tempItemList.set(i, tempItem);
                    }
                }
                Collections.sort(tempItemList, (o1, o2) -> {
                    boolean v1 = ((Item) o1).isImportant();
                    boolean v2 = ((Item) o2).isImportant();
                    if (v1 && v2) {
                        return 0;
                    } else if (v1 && !v2) {
                        return -1;
                    } else {
                        return 1;
                    }
                });
                model.addAttribute("itemList", tempItemList);
                model.addAttribute("itemToView", tempItem);
                model.addAttribute("toDoList", tempToDoListVal);
                notificationMsg = "Item updated";
                model.addAttribute("notificationMsg", notificationMsg);
                return "listdetails";
            }
        }
    }

    @RequestMapping(value = "/createlist", method = RequestMethod.GET)
    public String showCreateList(Model model, HttpSession session) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            tempToDoList = toDoListStore.getInactiveToDoListsForUserid(useridInSession);
            if (!tempToDoList.isEmpty()) {
                int index = 0;
                String dateStr = null;
                for (int i = 0; i < tempToDoList.size(); i++) {
                    tempToDoListVal = tempToDoList.get(i);
                    index = tempToDoListVal.getCreatedate().indexOf("T");
                    dateStr = tempToDoListVal.getCreatedate().substring(0, index);
                    tempToDoListVal.setCreatedate(dateStr);
                    tempToDoList.set(i, tempToDoListVal);

                }
                model.addAttribute("toDoLists", tempToDoList);
            }
            return "createlist";
        }
    }

    @RequestMapping(value = "/proceedlistcreation", method = RequestMethod.POST)
    public String proceedListCreation(Model model, HttpSession session, @RequestParam("todolistname") String todolistname, @RequestParam("todolistid") String todolistid) {
        useridInSession = (String) session.getAttribute("userid");
        if (useridInSession == null) {
            return "redirect:/";
        } else {
            if (todolistname.isEmpty()) { // make the particular list from the toDoListId
                tempToDoList = toDoListStore.getInactiveToDoListsForUserid(useridInSession);
                Iterator<ToDoList> it = tempToDoList.iterator();
                while (it.hasNext()) {
                    tempToDoListVal = it.next();
                    if (tempToDoListVal.getId().equals(todolistid)) {
                        tempToDoListVal.setActive(true);
                        toDoListAPI.putToDoList(tempToDoListVal, todolistid);
                    }
                }
                return "redirect:/getlists";
            } else { // create a brandnew list
                Calendar now = Calendar.getInstance();
                int month = now.get(Calendar.MONTH);
                int date = now.get(Calendar.DATE);
                int year = now.get(Calendar.YEAR);
                String dateStr = year + "-" + month + "-" + date + "T00:00:00";
                tempToDoListVal = new ToDoList(todolistname, dateStr, true);
                toDoListAPI.postToDoList(tempToDoListVal);
                // need to get the toDoList which is just created
                tempToDoListVal = toDoListStore.getToDoListsOrderByIdDesc().get(0);
                // create a new ListAssignment object and post it.
                ListAssignment tempAssignment = new ListAssignment(useridInSession, tempToDoListVal.getId());
                listAssignmentAPI.postListAssignment(tempAssignment);
                // go back to listview.
                return "redirect:/getlists";
            }
        }
    }

}
