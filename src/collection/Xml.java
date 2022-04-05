package collection;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Xml {
    public ArrayList<String[]> readFile(String s){

        ArrayList<String[]> returnArray = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(s)));
            String[] tags = new String[]{"name","id", "coordinates", "creationDate", "height", "weight", "hairColor", "nationality", "location"};
            int counter = document.getDocumentElement().getElementsByTagName("Person").getLength();
            for (int k = 0; k < counter; k++) {
                String[] values = new String[14];
                for (int i = 0; i < tags.length; i++) {
                    try {
                        if ((i != 2) && (i != 8)) {
                            NodeList personElements = document.getDocumentElement().getElementsByTagName(tags[i]);
                            String value = personElements.item(k).getFirstChild().toString();
                            values[i] = value.substring(8, value.length() - 1);
                        } else {
                            if (i == 2) {
                                for (int nc = 0; nc < 2; nc++) {
                                    String[] tagsCoordinates = new String[]{"x", "y"};
                                    NodeList personElements = document.getDocumentElement().getElementsByTagName(tagsCoordinates[nc]);
                                    String value = personElements.item(k).getFirstChild().toString();
                                    values[9 + nc] = value.substring(8, value.length() - 1);
                                }
                            }
                            if (i ==8) {
                                for (int nl = 0; nl < 3; nl++) {
                                    String[] tagsLocation = new String[]{"lx", "ly", "lz"};
                                    NodeList personElements = document.getDocumentElement().getElementsByTagName(tagsLocation[nl]);
                                    String value = personElements.item(k).getFirstChild().toString();
                                    values[11 + nl] = value.substring(8, value.length() - 1);
                                }
                            }
                        }

                    } catch (NullPointerException e) {
                        values[i] = "null";
                    }
                }
                returnArray.add(values);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return returnArray;
    }
}
