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
public class User {
    
    private String id;
    private String userName;
    private String email;
    private String password;
    private String listId;
    
    public User() {
    }
    
    public User(String id, String userName, String email, String password, String listId) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.listId = listId;
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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the ListId
     */
    public String getListId() {
        return listId;
    }

    /**
     * @param ListId the ListId to set
     */
    public void setListId(String listId) {
        this.listId = listId;
    }
    
    
    
    
    
}
