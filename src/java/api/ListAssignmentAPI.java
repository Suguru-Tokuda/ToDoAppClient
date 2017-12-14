package api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import models.ListAssignment;

/**
 *
 * @author Suguru
 */
public class ListAssignmentAPI {
    
    private static String BASE_URL = "http://gfish3.it.ilstu.edu:8080/stokuda_fall2017_ToDoAppWS/webresources/entities.listassignments/";
    
    public static String getBASE_URL() {
        return BASE_URL;
    }
    
    public boolean postAssignment(ListAssignment listAssignment) {
        Client client = ClientBuilder.newClient();
        String response = client
                .target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(listAssignment), String.class);
        return true;
    }
    
    public boolean putAssignment(ListAssignment listAssignment, String id) {
        Client client = ClientBuilder.newClient();
        String putURL = BASE_URL + id;
        String response = client
               .target(putURL)
               .request(MediaType.APPLICATION_JSON)
               .accept(MediaType.TEXT_PLAIN_TYPE)
               .put(Entity.json(listAssignment), String.class);
        return true;
    }
    
    public boolean deleteAssignment(String id) {
        Client client = ClientBuilder.newClient();
        String deleteURL = BASE_URL + id;
        String response = client
                .target(deleteURL)
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .delete(String.class);
        return true;  
    }
    
    public String getAllAssignments() {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
}
