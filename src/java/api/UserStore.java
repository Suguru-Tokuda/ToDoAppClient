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
 * Reffered to http://www.baeldung.com/jackson
 * and https://www.mkyong.com/java/json-simple-example-read-and-write-json/
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

    public User getUserByEmail(String email) {
        jsonString = userAPI.getUserByEmail(email);
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
    
        public User getUserById(String userid) {
        jsonString = userAPI.getUserById(userid);
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
