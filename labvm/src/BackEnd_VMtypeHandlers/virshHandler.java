package BackEnd_VMtypeHandlers;

import java.io.IOException;

import javax.swing.DefaultListModel;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import XMLhandler.XMLReadWrite;

public class virshHandler extends GeneralVMHandler {
	private CommandExecutor bw;
	private XMLReadWrite xml;
	private String user;

	public virshHandler() {
		bw = new CommandExecutor();
		bw.startCommand("sudo virsh connect qemu:///system");
		user = bw.startCommand("whoami");
		xml = new XMLReadWrite("/home/quang/virsh/.config/DefOS.xml");
	}

	private void displayVM(String name) {
		bw.startCommand("sudo virt-viewer -f --hotkeys='' " + name);
	}

	private void shutdownVM(String name) {
		bw.startCommand("sudo virsh shutdown " + name);
	}

	private void defineVM(String pathToXml) {
		bw.startCommand("sudo virsh define " + pathToXml);
	}
	@Override
	public DefaultListModel<String> getOSList() {
		return bw.listOS("/home/$(whoami)/virsh");
	}

	public void startSnapShotFrom(String path) {
		shutdownVM(path);
		bw.startCommand("sudo virsh start " + path);
		displayVM(path);

	}

	public void startVM(String name) {
		shutdownVM(name);
		bw.startCommand("sudo virsh undefine " + name);
		String user = bw.startCommand("whoami");
		String path = "/home/quang/virsh/.config/DefOS.xml";
		pciConfiguration pci;
		try {
			pci = new pciConfiguration(path);
			pci.removeInterfaceFromXML();
			pci.writeInterfaceToXML();
			pci.setInterfacesDown();
			xml.modifyDisk("/home/quang/virsh/"+name+"/linux.img");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		defineVM(path);
		bw.startCommand("sudo virsh start " + name);
		displayVM(name);
//		createSnapshotCurent(name);
	}

	@Override
	public String vmType() {
		// TODO Auto-generated method stub
		return "virsh";
	}
}
