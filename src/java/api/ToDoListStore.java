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
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Suguru
 */
public class ToDoListStore {

    private List<ToDoList> tempToDoListList;
    private ToDoList tempToDoList;
    @Autowired
    private ToDoListAPI toDoListAPI;

    ObjectMapper mapper = new ObjectMapper();
    JSONParser jParser = new JSONParser();
    JSONObject jsonObject;
    String jsonString;
    JSONArray jsonArray;

    public List<ToDoList> getAllToDoLists() {
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
            System.out.println(ex);
        }

        return tempToDoListList;
    }

    public List<ToDoList> getToDoListsForUserid(String userid) {
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
            System.out.println(ex);
        }

        return tempToDoListList;
    }

    public List<ToDoList> getInactiveToDoListsForUserid(String userid) {
        jsonString = toDoListAPI.getInactiveToDoListsByUserid(userid);

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
            System.out.println(ex);
        }
        return tempToDoListList;
    }

    public List<ToDoList> getToDoListsOrderByIdDesc() {
        jsonString = toDoListAPI.getToDoListsOrderByIdDesc();

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
            System.out.println(ex);
        }
        return tempToDoListList;
    }

    public ToDoList getToDoListById(String todolistid) throws IOException {
        jsonString = toDoListAPI.getToDoListById(todolistid);
        mapper = new ObjectMapper();
        tempToDoList = null;
        tempToDoListList = new ArrayList<>();
        int index = 0;
        String dateStr = null;
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempToDoList = mapper.readValue(jsonArray.get(i).toString(), ToDoList.class);
                tempToDoListList.add(tempToDoList);
                index = tempToDoList.getCreatedate().indexOf("T");
                dateStr = tempToDoList.getCreatedate().substring(0, index);
                tempToDoList.setCreatedate(dateStr);
                tempToDoListList.add(tempToDoList);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            tempToDoList = mapper.readValue(jsonString, ToDoList.class);
            tempToDoListList.add(tempToDoList);
            index = tempToDoList.getCreatedate().indexOf("T");
            dateStr = tempToDoList.getCreatedate().substring(0, index);
            tempToDoList.setCreatedate(dateStr);
            tempToDoListList.add(tempToDoList);
        }
        return tempToDoListList.get(0);
    }

}
