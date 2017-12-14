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
public class ListAssignment {

    private String id;
    private String userid;
    private String todolistid;

    public ListAssignment() {
    }

    public ListAssignment(String userid, String todolistid) {
        this.userid = userid;
        this.todolistid = todolistid;
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
    public String getTodolistid() {
        return todolistid;
    }

    /**
     * @param todolistid the todolistid to set
     */
    public void setTodolistid(String todolistid) {
        this.todolistid = todolistid;
    }



}
