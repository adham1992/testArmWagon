package dload;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ParseXML {
    public static List<InfoXML> parseXML(String fileName) {
        List<InfoXML> infList = new ArrayList<>();
        InfoXML inf = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("td")) {
                        inf = new InfoXML();
                        //Get the 'id' attribute from Employee element
                        Attribute dateAttr = startElement.getAttributeByName(new QName("changedDate"));
                        if (dateAttr != null) {
                            inf.setChangeDate(dateAttr.getValue());
                        }
                        xmlEvent = xmlEventReader.nextEvent();
                        inf.setDocName(xmlEvent.asCharacters().getData());
                    }
                    //set the other varibles from xml elements
//                    else if (startElement.getName().getLocalPart().equals("td")) {
//                        xmlEvent = xmlEventReader.nextEvent();
//                        inf.setDocName(xmlEvent.asCharacters().getData());
//                    }
                }
                //if Employee end element is reached, add employee object to list
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("td")) {
                        infList.add(inf);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return infList;
    }
}
