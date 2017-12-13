package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.ToDoList;
import models.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Suguru
 */
public class ToDoListStore {
    
    private List<ToDoList> tempToDoListList;
    private ToDoList tempToDoList;
    private final ToDoListAPI toDoListAPI = new ToDoListAPI();

    ObjectMapper mapper = new ObjectMapper();
    JSONParser jParser = new JSONParser();
    JSONObject jsonObject;
    String jsonString;
    JSONArray jsonArray;

    public List<ToDoList> getAllToDoLists() throws ParseException, IOException {
        jsonString = toDoListAPI.getAllToDoLists();

        mapper = new ObjectMapper();
        tempToDoList = null;
        tempToDoListList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempToDoList = mapper.readValue(jsonArray.get(i).toString(), ToDoList.class);
                tempToDoListList.add(tempToDoList);
            }
        } catch (Exception ex) {
            JSONObject jsonObject = (JSONObject) jParser.parse(jsonString);
            System.out.println(jsonObject);
            tempToDoList = mapper.readValue(jsonObject.toString(), ToDoList.class);
            tempToDoListList.add(tempToDoList);
        }

        return tempToDoListList;
    }
    
    public List<ToDoList> getToDoListsForUserid(String userid) throws IOException, ParseException {
        jsonString = toDoListAPI.getToDoListsByUserid(userid);

        mapper = new ObjectMapper();
        tempToDoList = null;
        tempToDoListList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempToDoList = mapper.readValue(jsonArray.get(i).toString(), ToDoList.class);
                tempToDoListList.add(tempToDoList);
            }
        } catch (Exception ex) {
            JSONObject jsonObject = (JSONObject) jParser.parse(jsonString);
            System.out.println(jsonObject);
            tempToDoList = mapper.readValue(jsonObject.toString(), ToDoList.class);
            tempToDoListList.add(tempToDoList);
        }

        return tempToDoListList;
    }


    
}
