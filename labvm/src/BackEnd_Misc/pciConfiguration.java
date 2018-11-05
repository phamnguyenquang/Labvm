package BackEnd_Misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import XMLhandler.XMLReadWrite;

/*This section of the code reads the PCI addresses of the Ethernet and Wireless card from an existing file
 * If the file does not exist, create one
 * a bit "unsafe" method from string processing, this can be a room for improvement
 */

public class pciConfiguration {
	private ArrayList<String> pciEthernetList = new ArrayList<String>();
	private CommandExecutor bw = new CommandExecutor();
	private String vfioOperation;
	private XMLReadWrite xmlHandler;

	public pciConfiguration() throws IOException {
		bw.startCommand("pwd");
		String config = bw.getResult();
		System.out.println("resut "+config);
		GenerateConfigFile(config + "/pciAddressList");
		readConfigFile(config + "/pciAddressList");
	}

	public pciConfiguration(XMLReadWrite xml) throws IOException {
		bw.startCommand("pwd");
		String config = bw.getResult();
		GenerateConfigFile(config + "/pciAddressList");
		readConfigFile(config + "/pciAddressList");
		xmlHandler = xml;
	}

	private void GenerateConfigFile(String path) {
		System.out.println(path);
		bw.startCommand("sudo lspci | grep -e Ethernet -e Wireless > " + path);
		bw.startCommand("sudo chmod 777 " + path);
	}

	private void readConfigFile(String path) throws IOException {
		File file = new File(path);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			pciEthernetList.add(line);
		}
		fileReader.close();
		for (int i = 0; i < pciEthernetList.size(); ++i) {
			line = pciEthernetList.get(i);
			line = line.substring(0, 7);
			pciEthernetList.set(i, "0000:" + line);
		}
	}

	public ArrayList<String> getPCIConfig() {
		for (int i = 0; i < pciEthernetList.size(); ++i) {
			System.out.println(pciEthernetList.get(i));
		}
		return pciEthernetList;
	}

	public int PCISlotNumber() {
		return pciEthernetList.size();
	}

	public String getPCIaddressAt(int i) {
		return pciEthernetList.get(i);

	}

	public void vfioBind() {
		vfioOperation = "sudo vfio-bind ";
		vfioOperation += "0000:04:00.0" + " ";
		for (int i = 0; i < pciEthernetList.size(); ++i) {
			vfioOperation += pciEthernetList.get(i) + " ";
		}
		System.out.println(vfioOperation);
		bw.startCommand(vfioOperation);

	}

	public void vfioUnbind() {
		vfioOperation = "sudo vfio-unbind ";
		for (int i = 0; i < pciEthernetList.size(); ++i) {
			vfioOperation += pciEthernetList.get(i) + " ";
		}
		System.out.println(vfioOperation);
		bw.startCommand(vfioOperation);
		bw.startCommand("sudo systemctl restart networking");
	}

	public void writeAddresses() {
		if (xmlHandler != null) {
			xmlHandler.removeHostDevices();
			for (int i = 0; i < PCISlotNumber(); ++i) {
				xmlHandler.addHostDevice(pciEthernetList.get(i));
			}
		}
		else {
			System.out.println("cannot write message, plz restate");
		}
	}
	

}