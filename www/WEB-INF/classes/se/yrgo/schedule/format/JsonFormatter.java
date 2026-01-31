package se.yrgo.schedule.format;

import java.util.List;

import org.json.*;

import se.yrgo.schedule.domain.*;

/**
 * A class implementing the Formatter interface. Formats a List of Assignment to
 * JSON.
 */
public class JsonFormatter
        implements
        Formatter {
    
    /**
     * Creates a JSONObject with all the infromation in an Assignment
     * 
     * @param assignment The assignment with the information
     * @return The JSONObject with information from an Assignment
     */
    private JSONObject JSONAssignment(Assignment assignment) {

        JSONObject JSONAssignment = new JSONObject();
        JSONAssignment.put("date", assignment.date());

        JSONObject JSONSubstitute = new JSONObject();
        JSONSubstitute.put("name", assignment.teacher().name());
        JSONAssignment.put("substitute", JSONSubstitute);

        JSONObject JSONSchool = new JSONObject();
        JSONSchool.put("school_name", assignment.school().name());
        JSONSchool.put("address", assignment.school().address());
        JSONAssignment.put("school", JSONSchool);

        return JSONAssignment;
    }

    /**
     * Checks if the list of assignments is empty.
     * If the list is empty returns an empty json format.
     * If the list of assignment is not empty returns a finished JSON-String
     * with all the information from the list of assignments
     * 
     * @param assignments A list of Assignment
     * @return An empty json format or a String with all information in the list of Assignment as JSON
     */
    @Override
    public String format(List<Assignment> assignments) {
        JSONArray JSON = new JSONArray();
        if (assignments.isEmpty()) {
            return "[]";
        } else {
            for (Assignment assignment : assignments) {
                JSON.put(JSONAssignment(assignment));
            }
        }
        return JSON.toString(2);
    }
}
