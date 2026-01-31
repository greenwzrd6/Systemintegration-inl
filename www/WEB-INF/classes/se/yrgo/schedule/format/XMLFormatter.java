package se.yrgo.schedule.format;

import java.io.*;
import java.util.List;
import java.util.stream.*;

import javax.xml.crypto.dsig.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.yrgo.schedule.domain.*;

/**
 * A class implementing the Formatter interface. Formats a List of Assignment to
 * XML.
 */
public class XMLFormatter
        implements
        Formatter {
    
    /**
     * Checks if the list of Assignment is empty
     * If empty it returns a default empty xml format
     * Otherwise returns a finished XML-String with all the information from the list of assignments
     * 
     * @param assignments The list of Assignment.
     * @return A default empty xml format or a String with the information from the list as XML.
     */
    @Override
    public String format(List<Assignment> assignments) {
        if (assignments.isEmpty()) {
            return new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                    .append("<schedules></schedules>\n")
                    .toString();
        }
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootE = doc.createElement("schedules");
            doc.appendChild(rootE);

            createXML(assignments, doc, rootE);

            StringWriter xml = new StringWriter();
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer trans = transFactory.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xml);
            trans.transform(source, result);
            return xml.toString();
        } catch (ParserConfigurationException | TransformerException e) {
            return "XML error";
        }
    }

    /**
     * Creates an XML document of a list of Assignment
     * 
     * @param assignments The list of Assignment
     * @param doc The document to write in
     * @param rootE The root element to store all the information.
     */
    private void createXML(List<Assignment> assignments, Document doc, Element rootE) {
        for (Assignment assignment : assignments) {
            Element schedule = doc.createElement("schedule");
            schedule.setAttribute("date", assignment.date());

            Element school = doc.createElement("school");
            Element schoolName = doc.createElement("school_name");
            schoolName.appendChild(doc.createTextNode(assignment.school().name()));
            school.appendChild(schoolName);
            Element address = doc.createElement("address");
            address.appendChild(doc.createTextNode(assignment.school().address()));
            school.appendChild(address);
            schedule.appendChild(school);

            Element substitute = doc.createElement("substitute");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(assignment.teacher().name()));
            substitute.appendChild(name);

            schedule.appendChild(substitute);

            rootE.appendChild(schedule);
        }
    }
}
