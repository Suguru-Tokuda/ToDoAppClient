package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Suguru
 */
public class UserStore {

    private List<User> tempUserList;
    private User tempUser;
    @Autowired
    private UserAPI userAPI;

    ObjectMapper mapper = new ObjectMapper();
    JSONParser jParser = new JSONParser();
    JSONObject jsonObject;
    String jsonString;
    JSONArray jsonArray;

    public List<User> getAllUsers() {
        jsonString = userAPI.getAllUsers();

        mapper = new ObjectMapper();
        tempUser = null;
        tempUserList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempUser = mapper.readValue(jsonArray.get(i).toString(), User.class);
                tempUserList.add(tempUser);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return tempUserList;
    }

    public User getUserForUsername(String username) {
        jsonString = userAPI.getAllUsers();

        mapper = new ObjectMapper();
        tempUser = null;
        tempUserList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempUser = mapper.readValue(jsonArray.get(i).toString(), User.class);
                tempUserList.add(tempUser);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempUserList.get(0);
    }

}
