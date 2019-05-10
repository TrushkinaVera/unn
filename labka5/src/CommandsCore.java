

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * Класс, реализующий функции для работы с коллекцией(Universe)
 */

public class CommandsCore implements Commands {

    private String source;

    public boolean importT(Universe<Thing> l, String path) {
        StringBuilder data = new StringBuilder();
        Scanner read;
        try {
            read = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("File " + path + " not found");
            return false;
        }
        do {
            data.append(read.nextLine());
        }
        while (read.hasNextLine());
        if (data.toString().length() == 0) {
            System.out.println("File " + path + " is empty");
            return true;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(data.toString()));
            Document xmlstr = builder.parse(is);
            NodeList sd = xmlstr.getElementsByTagName("Thing");
            String Name;
            int Size;
            for (int d = 0; d < sd.getLength(); d++) {
                Element ele = (Element) sd.item(d);
                Name = ele.getAttribute("name");
                Size = Integer.valueOf(ele.getAttribute("size")).intValue();
                l.add(new Thing(Name, Size));
            }

        } catch (ParserConfigurationException ex) {
            System.out.println("Error while parsing file 1");
            return false;
        } catch (SAXException e) {
            System.out.println("Error while parsing file 2");
            return false;
        } catch (IOException e) {
            System.out.println("Error while parsing file 3");
            return false;
        }
        return true;
    }

    @Override
    public void load(Universe<Thing> l) {
        l.clear();
        this.importT(l, source);
        System.out.println("Коллекция успешно перезагруженна");
    }

    @Override
    public void remove(Universe<Thing> l, Thing e) {
        l.remove(e);
    }

    @Override
    public void show(Universe<Thing> l) {
        System.out.println(l);
    }

    @Override
    public void info(Universe<Thing> l) {

        System.out.println("Something about this collection");
        System.out.println("Init date: " + l.initDate());
        System.out.println("Lenght: " + l.size());
    }

    @Override
    public void addIfMin(Universe<Thing> l, Thing e) {
        Thing min = e;
        for (Thing k : l) {
            if (min.compareTo(k) > 0) {
                min = k;
            }
        }
        if (min.equals(e)) {
            l.add(e);
        }
    }


    @Override
    public void add(Universe<Thing> l, Thing e) {
        l.add(e);
    }


    public CommandsCore(String path) {
        super();
        source = path;
    }


    public boolean parseAndExecute(String d, Universe<Thing> e) {
        String cmd = d.toLowerCase();

        if (cmd.indexOf("show") == 0 || cmd.indexOf("exit") == 0 || cmd.indexOf("info") == 0 || cmd.indexOf("load") == 0) {
            if (cmd.indexOf("show") == 0) {
                this.show(e);
            } else if (cmd.indexOf("info") == 0) {
                this.info(e);
            } else if (cmd.indexOf("load") == 0) {
                this.load(e);
            } else if (cmd.indexOf("exit") == 0) {
                return false;
            }
        } else if (
                cmd.indexOf("remove") == 0 ||
                        cmd.indexOf("add_if_min") == 0 ||
                        cmd.indexOf("add") == 0) {

            int start = d.indexOf("{");
            if (start == -1) {
                System.out.println("Отсутствуют агрументы команды");
                return true;
            }
            String JData = d.substring(start);
            JSONParser parser = new JSONParser();
            JSONObject JValues;
            try {
                JValues = (JSONObject) parser.parse(JData);
            } catch (ParseException e1) {
                System.out.println("Некорректный формат JSON");
                return true;
            }
            String Name = "";
            int Size;
            try {
                Name = (String) JValues.get("Name");
                Size = ((Long) JValues.get("Size")).intValue();
            } catch (NullPointerException e2) {
                System.out.println("Некоторые аргументы отсутствуют");
                return true;
            }
            Thing item = new Thing(Name, Size);
            if (cmd.indexOf("remove") == 0) {
                this.remove(e, item);
            } else if (cmd.indexOf("add_if_min") == 0) {
                this.addIfMin(e, item);
            } else if (cmd.indexOf("add") == 0) {
                 this.add(e, item);
            } else {
                System.out.println("Команда не существует");
            }
        } else if (
                cmd.indexOf("import") == 0) {
            int start = d.indexOf(" ");
            if (start == -1) {
                System.out.println("Отсутствуют агрументы команды");
                return true;
            }
            String path = d.substring(start + 1);
            File f = new File(path);
            if (!f.exists() || !f.isFile()) {
                System.out.println("Файл не найден");
                return true;
            }
            this.importT(e, path);
            return true;
        } else {
            System.out.println("Команда не существует");
        }
        return true;
    }
}
