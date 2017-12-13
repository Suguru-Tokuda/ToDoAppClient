package api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import models.User;

/**
 * All the codes here are from the lecture.
 * @author Suguru
 */
public class UserAPI {
    
    private static String BASE_URL = "http://gfish3.it.ilstu.edu:8080/stokuda_fall2017_ToDoAppWS/webresources/entities.users/";
    
    public static String getBASE_URL() {
        return BASE_URL;
    }
    
    public boolean postUser(User user) {
        Client client = ClientBuilder.newClient();
        
        String response = client
                .target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(user), String.class);
        return true;
    }
    
    public boolean putUser(User user, String id) {
        Client client = ClientBuilder.newClient();
        String putURL = BASE_URL + id;
        String response = client
               .target(putURL)
               .request(MediaType.APPLICATION_JSON)
               .accept(MediaType.TEXT_PLAIN_TYPE)
                .put(Entity.json(user), String.class);
        return true;
    }
    
    public boolean deleteUser(String id) {
        Client client = ClientBuilder.newClient();
        String deleteURL = BASE_URL + id;
        String response = client
                .target(deleteURL)
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .delete(String.class);
        return true;  
    }
    
    public String getAllUsers() {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
    public String getUserForUsername(String username) {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL + "findByUsername/" + username;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
}
