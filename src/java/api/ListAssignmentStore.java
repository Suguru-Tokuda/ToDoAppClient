/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;
import java.util.List;
import models.ListAssignment;
import models.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Suguru
 */
public class ListAssignmentStore {

    private List<ListAssignment> tempListassignmentList;
    private ListAssignment tempListAssignment;
    @Autowired
    private ListAssignmentAPI listAssignmentAPI;

    ObjectMapper mapper = new ObjectMapper();
    JSONParser jParser = new JSONParser();
    JSONObject jsonObject;
    String jsonString;
    JSONArray jsonArray;

    public List<ListAssignment> getAllListAssignments() {
        jsonString = listAssignmentAPI.getAllListAssignments();
        mapper = new ObjectMapper();
        tempListAssignment = null;
        tempListassignmentList = new ArrayList<>();
        try {
            jsonArray = (JSONArray) jParser.parse(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                tempListAssignment = mapper.readValue(jsonArray.get(i).toString(), ListAssignment.class);
                tempListassignmentList.add(tempListAssignment);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempListassignmentList;
    }

}
