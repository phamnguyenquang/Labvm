package XMLhandler;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class XMLReadWrite {
	private String filePath;

	public XMLReadWrite(String pathToFile) {
		filePath = pathToFile;
	}

	public void replaceHostDevice(String oldValue, String newValue) {
		try {
			String oldDomain = "0x" + oldValue.substring(0, 4);
			String oldBus = "0x" + oldValue.substring(5, 7);
			String oldSlot = "0x" + oldValue.substring(8, 10);
			String oldFunction = "0x" + oldValue.substring(11);

			String domain = "0x" + newValue.substring(0, 4);
			String bus = "0x" + newValue.substring(5, 7);
			String slot = "0x" + newValue.substring(8, 10);
			String function = "0x" + newValue.substring(11);

			System.out.println(domain);
			System.out.println(bus);
			System.out.println(slot);
			System.out.println(function);
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList devicesNodeList = root.getElementsByTagName("devices");

			NodeList hostdevNodeList = ((Element) devicesNodeList.item(0)).getElementsByTagName("hostdev");
			NodeList sourceNodeList = ((Element) hostdevNodeList.item(0)).getElementsByTagName("source");
			NodeList addressNodeList = ((Element) sourceNodeList.item(0)).getElementsByTagName("address");

			for (int i = 0; i < addressNodeList.getLength(); ++i) {
				System.out.println(i);
				Node addr = addressNodeList.item(i);
				NamedNodeMap adr = addr.getAttributes();
				Node currentDomain = adr.getNamedItem("domain");
				Node currentBus = adr.getNamedItem("bus");
				Node currentSlot = adr.getNamedItem("slot");
				Node currentFunction = adr.getNamedItem("function");
//				attr.setTextContent("0x05");
				if (currentDomain.getTextContent().contains(oldDomain) && currentBus.getTextContent().contains(oldBus)
						&& currentSlot.getTextContent().contains(oldSlot)
						&& currentFunction.getTextContent().contains(oldFunction)) {
					currentDomain.setTextContent(domain);
					currentBus.setTextContent(bus);
					currentSlot.setTextContent(slot);
					currentFunction.setTextContent(function);
				}
			}

			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addHostDevice(String newValue) {
		try {
			String domain = "0x" + newValue.substring(0, 4);
			String bus = "0x" + newValue.substring(5, 7);
			String slot = "0x" + newValue.substring(8, 10);
			String function = "0x" + newValue.substring(11);
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList devicesNodeList = root.getElementsByTagName("devices");
			Node deviceNode = devicesNodeList.item(0);

			Element hostdev = doc.createElement("hostdev");
			hostdev.setAttribute("managed", "yes");
			hostdev.setAttribute("mode", "subsystem");
			hostdev.setAttribute("type", "pci");

			Element driver = doc.createElement("driver");
			driver.setAttribute("name", "vfio");

			Element source = doc.createElement("source");

			Element address = doc.createElement("address");
			address.setAttribute("domain", domain);
			address.setAttribute("bus", bus);
			address.setAttribute("slot", slot);
			address.setAttribute("function", function);

			System.out.println("----------------------------");

			source.appendChild(address);
			hostdev.appendChild(driver);
			hostdev.appendChild(source);
			deviceNode.appendChild(hostdev);

			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void WriteGeneralValue(String part, String attribute, String value) {
		/*
		 * Useful to modify values like memory and stuff
		 */
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList targetedPart = root.getElementsByTagName(part);

			Node addr = targetedPart.item(0);

			System.out.println("----------------------------");

			if (!attribute.isEmpty()) {

				NamedNodeMap adr = addr.getAttributes();
				Node attr = adr.getNamedItem(attribute);

				attr.setTextContent(value);
			} else {
				addr.setTextContent(value);
			}

			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void removeHostDevices() {
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList devicesList = root.getElementsByTagName("devices");
			Node device = devicesList.item(0);

			NodeList hostdevList = ((Element) devicesList.item(0)).getElementsByTagName("hostdev");
			int count = hostdevList.getLength();
			for (int i = 0; i < count; ++i) {
				device.removeChild(hostdevList.item(i));
			}
			;
			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addInterface(String name) {
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList devicesNodeList = root.getElementsByTagName("devices");
			Node hostdevNodeList = devicesNodeList.item(0);

			Element iface = doc.createElement("interface");
			iface.setAttribute("type", "direct");

			Element source = doc.createElement("source");
			source.setAttribute("dev", name);
			source.setAttribute("mode", "passthrough");

			iface.appendChild(source);
			hostdevNodeList.appendChild(iface);
			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeInterface() {
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			// ---------------------------------------------------

			// Devices--------------------------------------------
			NodeList devicesList = root.getElementsByTagName("devices");

			NodeList interfaceList = ((Element) devicesList.item(0)).getElementsByTagName("interface");

			int length = interfaceList.getLength();

			System.out.println(length);

			Node device = devicesList.item(0);

			for (int i = length -1 ; i >= 0; --i) {
				System.out.println(i);
				device.removeChild(interfaceList.item(i));
				System.out.println("done");
			}

			/*
			 * Up next, save the file, actual saving
			 */
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void modifyDisk(String path)
	{
		try {
			File inputFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			// root element------------------------------------------
			Element root = doc.getDocumentElement();
			NodeList devicesNodeList = root.getElementsByTagName("devices");

			
			// ---------------------------------------------------

			// Devices--------------------------------------------
			Node diskNodeList = ((Element) devicesNodeList.item(0)).getElementsByTagName("disk").item(0);
			Node source = ((Element)diskNodeList).getElementsByTagName("source").item(0);
			NamedNodeMap attr = source.getAttributes();
			Node file = attr.getNamedItem("file");
			file.setTextContent(path);
			TransformerFactory transformerfactory = TransformerFactory.newInstance();
			Transformer transformer = transformerfactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(doc);

			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
