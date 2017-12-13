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
public class Assignment {
    
    private String id;
    private String userid;
    private String todolistid;
    
    public Assignment(){}
    
    public Assignment(String userid, String listid) {
        this.userid = userid;
        this.todolistid = listid;
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
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the todolistid
     */
    public String getToDoListId() {
        return todolistid;
    }

    /**
     * @param listid the listid to set
     */
    public void setToDoListId(String todolistid) {
        this.todolistid = todolistid;
    }
    
    
    
}
