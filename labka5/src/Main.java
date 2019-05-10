import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Universe<Thing> Universe = new Universe<>();

        String path = "suslik.xml";
        for (String s: args) {
            path = s;
        }

        CommandsCore cmd = new CommandsCore(path);
        cmd.load(Universe);
        Scanner Reader = new Scanner(System.in);
        boolean result = true;
        String txtcmd;
        try{
            do{
                System.out.println("Введите команду");
                txtcmd = Reader.nextLine();
                result = cmd.parseAndExecute(txtcmd, Universe);
            }
            while(result != false);
        }
        finally {

            try {
                XMLOutputFactory output = XMLOutputFactory.newInstance();
                XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(path));
                writer.writeStartDocument("1.0");
                writer.writeStartElement("set");
                for(Thing k: Universe) {
                    writer.writeEmptyElement("Thing");
                    writer.writeAttribute("name", k.getName());
                    writer.writeAttribute("size", Integer.toString(k.getSize()));
                }
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.flush();

            } catch (Exception e) {
                System.out.println("Trouble in saving File");
            }
        }
    }
}
