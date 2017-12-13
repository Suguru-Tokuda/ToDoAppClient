package api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import models.ToDoList;

/**
 *
 * @author Suguru
 */
public class ToDoListAPI {
    
    private static String BASE_URL = "http://gfish3.it.ilstu.edu:8080/stokuda_fall2017_ToDoAppWS/webresources/entities.todolists/";
    
    public static String getBASE_URL() {
        return BASE_URL;
    }
    
    public boolean postToDoList(ToDoList toDoList) {
        Client client = ClientBuilder.newClient();
        
        String response = client
                .target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(toDoList), String.class);
        return true;
    }
    
    public boolean putToDoList(ToDoList toDoList, String id) {
        Client client = ClientBuilder.newClient();
        String putURL = BASE_URL + id;
        String response = client
               .target(putURL)
               .request(MediaType.APPLICATION_JSON)
               .accept(MediaType.TEXT_PLAIN_TYPE)
                .put(Entity.json(toDoList), String.class);
        return true;
    }
    
    public boolean deleteToDoList(String id) {
        Client client = ClientBuilder.newClient();
        String deleteURL = BASE_URL + id;
        String response = client
                .target(deleteURL)
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .delete(String.class);
        return true;  
    }
    
    public String getAllToDoLists() {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
    public String getToDoListsByUserid(String userid) {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL + "getToDoListsByUserid/" + userid;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
    public String getInactiveToDoListsByUserid(String userid) {
                Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL + "getInactiveToDoListsByUserid/" + userid;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
}
