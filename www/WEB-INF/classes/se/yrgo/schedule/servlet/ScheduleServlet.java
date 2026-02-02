package se.yrgo.schedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

import se.yrgo.schedule.data.*;
import se.yrgo.schedule.domain.*;
import se.yrgo.schedule.format.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * Listens to requests on localhost:8080/v1/ and accepts the following
 * parameters:
 * <ul>
 * <li>none - lists all schedules for all teachers</li>
 * <li>substitute_id - the ID for a substitute teacher you want to list the
 * schedult for</li>
 * <li>day - the day (YYYY-mm-dd) you want to see the schedule for</li>
 * </ul>
 * <p>
 * The substitute_id and day parameters can be combined or used alone.
 * </p>
 */
public class ScheduleServlet
        extends
        HttpServlet {

    /**
     * Handles the requests and responses from the servlet.
     * If an error occurs it sets the appropriate status codes.
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        // Read the request as UTF-8
        request.setCharacterEncoding(UTF_8.name());
        // Parse the arguments - see ParamParser class
        ParamParser parser = new ParamParser(request);
        // Set the content type (using the parser)
        response.setContentType(parser.contentType());
        // To write the response, we're using a PrintWriter
        response.setCharacterEncoding(UTF_8.name());
        PrintWriter out = response.getWriter();

        // Sets the response status to 400 when the format is not supported/missing.
        if (!parser.isValidFormat()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/html;charset=" + UTF_8.name());
            out.println("<html><head><title>Format error</title></head>");
            out.println("<body>ERROR: Format missing or not supported");
            out.println(" - We support xml and json</body>");
            out.println("</html>");
            System.err.println("Error: Format is not supported");
            return;
        }

        // Get access to the database, using a factory
        // Assignments is an interface - see Assignments interface
        Assignments db = AssignmentsFactory.getAssignments();
        // Start with an empty list (makes code easier)
        List<Assignment> assignments = new ArrayList<>();
        // Call the correct method, depending on the parser's type value
        // checks if the request is valid, otherwise sends a 404 status code.
        try {
            switch (parser.type()) {
                case ALL:
                    assignments = db.all();
                    break;
                case TEACHER_ID_AND_DAY:
                    assignments = db.forTeacherAt(parser.teacherId(), parser.day());
                    break;
                case DAY:
                    assignments = db.at(parser.day());
                    break;
                case TEACHER_ID:
                    assignments = db.forTeacher(parser.teacherId());
                    break;
            }

            // Sets the response status to 404 when a parameter is not found or not valid
            // and prints out the defaultFormat for json/xml
            if (assignments.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println(parser.defaultFormat());
                System.err.println("Error: Parameter not valid/not found");
                return;
            }

        } catch (AccessException e) {
            out.println("Error fetching data: " + e.getMessage());
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
        // Get a formatter, by asking the parser for the format
        try {
            Formatter formatter = FormatterFactory.getFormatter(parser.format());
            // Format the result to the format according to the parser:
            String result = formatter.format(assignments);

            // Print the result and close the PrintWriter
            out.println(result);
        } catch (IllegalArgumentException ex) {
            out.println("<html><head><title>Format error</title></head>");
            out.println("<body>Format missing or not supported");
            out.println(" - We support xml and json</body>");
            out.println("</html>");
        }
        out.close();
    }
}
