package collection;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Xml {
    public ArrayList<String[]> readFile(String s){

        ArrayList<String[]> returnArray = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(s);
            String[] tags = new String[]{"name", "coordinates", "creationDate", "height", "weight", "hairColor", "nationality", "location"};
            int counter = document.getDocumentElement().getElementsByTagName("Person").getLength();
            for (int k = 0; k < counter; k++) {
                String[] values = new String[12];
                for (int i = 0; i < tags.length; i++) {
                    try {
                        if ((i != 1) && (i != 7)) {
                            NodeList personElements = document.getDocumentElement().getElementsByTagName(tags[i]);
                            String value = personElements.item(k).getFirstChild().toString();
                            values[i] = value.substring(8, value.length() - 1);
                        } else {
                            if (i == 1) {
                                for (int nc = 0; nc < 2; nc++) {
                                    String[] tagsCoordinates = new String[]{"x", "y"};
                                    NodeList personElements = document.getDocumentElement().getElementsByTagName(tagsCoordinates[nc]);
                                    String value = personElements.item(k).getFirstChild().toString();
                                    values[7 + nc] = value.substring(8, value.length() - 1);
                                }
                            }
                            if (i == 7) {
                                for (int nl = 0; nl < 3; nl++) {
                                    String[] tagsLocation = new String[]{"xl", "yl", "zl"};
                                    NodeList personElements = document.getDocumentElement().getElementsByTagName(tagsLocation[nl]);
                                    String value = personElements.item(k).getFirstChild().toString();
                                    values[9 + nl] = value.substring(8, value.length() - 1);
                                }
                            }
                        }

                    } catch (NullPointerException e) {
                        values[i] = "null";
                    }
                }
                for(int i=2;i<11;i++){
                    values[i-1]=values[i];
                }
                returnArray.add(values);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return returnArray;
    }
}
