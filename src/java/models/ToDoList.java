/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Suguru
 */
public class ToDoList {

    private String id;
    private String listname;
    private boolean active;

    public ToDoList() {
    }

    public ToDoList(String id) {

    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the listname
     */
    public String getListname() {
        return listname;
    }

    /**
     * @param listname the listname to set
     */
    public void setListname(String listname) {
        this.listname = listname;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

}
