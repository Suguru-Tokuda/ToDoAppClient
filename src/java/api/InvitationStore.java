package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.Invitation;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Suguru
 */
public class InvitationStore {

    private List<Invitation> tempInvitationList;
    private Invitation tempInvitation;
    @Autowired
    private InvitationAPI invitationAPI;

    ObjectMapper mapper = new ObjectMapper();
    JSONParser jParser = new JSONParser();
    JSONObject jsonObject;
    String jsonString;
    JSONArray jsonArray;

    public List<Invitation> getAllitems() {
        jsonString = invitationAPI.getAllInvitations();

        mapper = new ObjectMapper();
        tempInvitation = null;
        tempInvitationList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempInvitation = mapper.readValue(jsonArray.get(i).toString(), Invitation.class);
                tempInvitationList.add(tempInvitation);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempInvitationList;
    }

    public List<Invitation> getInvitationsByReceiverId(String receiverid, String todolistid) {
        jsonString = invitationAPI.getInvitationsByReceiveridAndToDoListId(receiverid, todolistid);
        mapper = new ObjectMapper();
        tempInvitation = null;
        tempInvitationList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempInvitation = mapper.readValue(jsonArray.get(i).toString(), Invitation.class);
                tempInvitationList.add(tempInvitation);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempInvitationList;
    }

}
