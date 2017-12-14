package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.Item;
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
public class ItemStore {
    
    private List<Item> tempItemList;
    private Item tempItem;
    @Autowired
    private ItemAPI itemAPI;

    ObjectMapper mapper = new ObjectMapper();
    JSONParser jParser = new JSONParser();
    JSONObject jsonObject;
    String jsonString;
    JSONArray jsonArray;

    public List<Item> getAllitems() {
        jsonString = itemAPI.getAllItems();

        mapper = new ObjectMapper();
        tempItem = null;
        tempItemList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempItem = mapper.readValue(jsonArray.get(i).toString(), Item.class);
                tempItemList.add(tempItem);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempItemList;
    }
    
    public List<Item> getItemsByToDoListId(String toDoListId) {
        jsonString = itemAPI.getItemsByToDoListId(toDoListId);
        mapper = new ObjectMapper();
        tempItem = null;
        tempItemList = new ArrayList<>();
        int index = 0;
        String dueStr = null;
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempItem = mapper.readValue(jsonArray.get(i).toString(), Item.class);
                index = tempItem.getDue().indexOf("T");
                dueStr = tempItem.getDue().substring(0, index);
                tempItem.setDue(dueStr);
                tempItemList.add(tempItem);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempItemList;
    }
    
}
