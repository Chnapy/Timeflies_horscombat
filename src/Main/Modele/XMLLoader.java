/*
 * 
 * 
 * 
 */
package Main.Modele;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XMLLoader.java
 *
 */
public class XMLLoader {

	private static final String DEFAULT_PATH = "fr/";

	public static final HashMap<Integer, SimpleStringProperty> LIST_INTERFACE = new HashMap();
	public static final HashMap<Integer, HashMap<String, SimpleStringProperty>> LIST_ENTITES = new HashMap(), LIST_SORTS = new HashMap();

	public static void loadDefaultLang() {
		try {
			loadLang(DEFAULT_PATH);
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			Logger.getLogger(XMLLoader.class.getName()).log(Level.SEVERE, null, ex);
		}
//		printAll();
	}

	public static void loadLang(String dir) throws ParserConfigurationException, SAXException, IOException {
		String directory = Data.PATH_LANG + dir;
		String path = directory + Data.FILE_MAIN_LANG;

		File xmlFile = new File(path);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("include");
		Node n;
		Element e;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			n = nList.item(temp);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) n;
				switch (Integer.parseInt(e.getAttribute("id"))) {
					case 0:	//interface
						defineInterface(new File(directory + e.getAttribute("filename")), builder);
						break;
					case 1:	//entites
						defineEntites(new File(directory + e.getAttribute("filename")), builder);
						break;
					case 2:	//sorts
						defineSorts(new File(directory + e.getAttribute("filename")), builder);
						break;
				}

			}
		}
	}

	private static void defineSorts(File xmlFile, DocumentBuilder builder) throws SAXException, IOException {
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("sort"), cList;
		Node n;
		Element e, c;
		HashMap<String, SimpleStringProperty> hm;
		for (int i = 0, j; i < nList.getLength(); i++) {
			n = nList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) n;
				LIST_SORTS.putIfAbsent(Integer.parseInt(e.getAttribute("id")),
						new HashMap());
				hm = LIST_SORTS.get(Integer.parseInt(e.getAttribute("id")));
				cList = e.getChildNodes();
				for (j = 0; j < cList.getLength(); j++) {
					if (cList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						c = (Element) cList.item(j);
						hm.putIfAbsent(c.getNodeName(), new SimpleStringProperty());
						hm.get(c.getNodeName()).set(c.getTextContent());
					}
				}
			}
		}
	}

	private static void defineEntites(File xmlFile, DocumentBuilder builder) throws SAXException, IOException {
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("entite"), cList;
		Node n;
		Element e, c;
		HashMap<String, SimpleStringProperty> hm;
		for (int i = 0, j; i < nList.getLength(); i++) {
			n = nList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) n;
				LIST_ENTITES.putIfAbsent(Integer.parseInt(e.getAttribute("id")),
						new HashMap());
				hm = LIST_ENTITES.get(Integer.parseInt(e.getAttribute("id")));
				cList = e.getChildNodes();
				for (j = 0; j < cList.getLength(); j++) {
					if (cList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						c = (Element) cList.item(j);
						hm.putIfAbsent(c.getNodeName(), new SimpleStringProperty());
						hm.get(c.getNodeName()).set(c.getTextContent());
					}
				}
			}
		}
	}

	private static void defineInterface(File xmlFile, DocumentBuilder builder) throws SAXException, IOException {
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("string");
		Node n;
		Element e;
		for (int i = 0; i < nList.getLength(); i++) {
			n = nList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) n;
				LIST_INTERFACE.putIfAbsent(Integer.parseInt(e.getAttribute("id")), new SimpleStringProperty());
				LIST_INTERFACE.get(Integer.parseInt(e.getAttribute("id"))).set(e.getTextContent());
			}
		}
	}

	private static void printAll() {
		System.out.println("LIST_INTERFACE\n----------");
		LIST_INTERFACE.forEach((k, v) -> {
			System.out.println("ID : " + k + " STRING : " + v.get());
		});
		System.out.println("----------\n");
		System.out.println("LIST_ENTITES\n----------");
		LIST_ENTITES.forEach((k, v) -> {
			System.out.println("ID : " + k);
			v.forEach((t, u) -> {
				System.out.println("\t" + t + " : " + u.get());
			});
		});
		System.out.println("----------\n");
		System.out.println("LIST_SORTS\n----------");
		LIST_SORTS.forEach((k, v) -> {
			System.out.println("ID : " + k);
			v.forEach((t, u) -> {
				System.out.println("\t" + t + " : " + u.get());
			});
		});
		System.out.println("----------\n");
	}

}
