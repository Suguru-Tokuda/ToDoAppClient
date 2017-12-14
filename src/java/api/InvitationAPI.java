package api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import models.Invitation;

/**
 *
 * @author Suguru
 */
public class InvitationAPI {
    
    private static String BASE_URL = "http://gfish3.it.ilstu.edu:8080/stokuda_fall2017_ToDoAppWS/webresources/entities.invitations/";
    
    public static String getBASE_URL() {
        return BASE_URL;
    }
    
    public boolean postInvitation(Invitation invitation) {
        Client client = ClientBuilder.newClient();
        
        String response = client
                .target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.json(invitation), String.class);
        return true;
    }
    
    public boolean putInvitation(Invitation invitation, String id) {
        Client client = ClientBuilder.newClient();
        String putURL = BASE_URL + id;
        String response = client
               .target(putURL)
               .request(MediaType.APPLICATION_JSON)
               .accept(MediaType.TEXT_PLAIN_TYPE)
                .put(Entity.json(invitation), String.class);
        return true;
    }
    
    public boolean deleteInvitation(String id) {
        Client client = ClientBuilder.newClient();
        String deleteURL = BASE_URL + id;
        String response = client
                .target(deleteURL)
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .delete(String.class);
        return true;  
    }
    
    public String getAllInvitations() {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
    public String getInvitationsByReceiveridAndToDoListId(String receiverid, String todolistid) {
        Client client = ClientBuilder.newClient();
        String requestURL = BASE_URL + "getInvitationsByReceiveridAndToDoListId/" + receiverid + "/" + todolistid;
        String response = client
                .target(requestURL)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        return response;
    }
    
}
