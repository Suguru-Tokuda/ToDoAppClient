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
    private String senderid;
    private String receiverid;
    private String todolistid;

    public Invitation() {
    }

    public Invitation(String senderid, String receiverid, String todolistid) {
        this.senderid = senderid;
        this.receiverid = receiverid;
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
     * @return the todolistid
     */
    public String getToDoListid() {
        return todolistid;
    }

    /**
     * @param todolistid the todolistid to set
     */
    public void setToDoListid(String todolistid) {
        this.todolistid = todolistid;
    }

}
