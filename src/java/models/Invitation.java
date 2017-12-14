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
public class Invitation {

    private String id;
    private String receiverid;
    private String senderid;
    private String todolistid;

    public Invitation() {

    }

    public Invitation(String receiverid, String senderid, String todolistid) {
        this.receiverid = receiverid;
        this.senderid = senderid;
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
     * @return the receiverid
     */
    public String getReceiverid() {
        return receiverid;
    }

    /**
     * @param receiverid the receiverid to set
     */
    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    /**
     * @return the senderid
     */
    public String getSenderid() {
        return senderid;
    }

    /**
     * @param senderid the senderid to set
     */
    public void setSenderid(String senderid) {
        this.senderid = senderid;
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
