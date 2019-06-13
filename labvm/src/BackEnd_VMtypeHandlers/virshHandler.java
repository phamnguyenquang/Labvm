package BackEnd_VMtypeHandlers;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import XMLhandler.XMLReadWrite;

/* This whole thing probbably just need a better way of managing VMs
 * something.....fast, sustainable
 */

public class virshHandler extends GeneralVMHandler {
	private CommandExecutor bw;
	private XMLReadWrite xml;
	private String user;

	public virshHandler() {
		bw = new CommandExecutor();
		bw.startCommand("sudo virsh connect qemu:///system");
		bw.startCommand("whoami");
		user = bw.getResult();
		xml = new XMLReadWrite("/home/" + user + "/virsh/.config/DefOS.xml");
	}

	private void displayVM(String name) {
		bw.startCommand("sudo virt-viewer -f --hotkeys='' " + name);
	}

	/*
	 * snapshot handlers Create, restore and delete snapshot from respective states
	 * restore means to restore the current virtual hard disk to the content from
	 * the snapshot delete and create are self-explanatory Fresh snapshot can be
	 * used to implement a "system reset"
	 */
	private void createSnapshotCurrent(String name) {
		bw.startCommand("sudo virsh snapshot-create-as --domain " + name + " --name " + name + "-current");
	}

	private void restoreCurrent(String name) {
		bw.startCommand("sudo virsh snapshot-revert --domain " + name + " --snapshotname " + name + "-current");
	}

	private void deleteCurrent(String name) {
		bw.startCommand("sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "-current");
	}

	// -------------------------------------------------------------------------------------
	private void createSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-create-as --domain " + name + " --name " + name + "-fresh");
	}

	private void restoreFresh(String name) {
		bw.startCommand("sudo virsh snapshot-revert --domain " + name + " --snapshotname " + name + "-fresh");
	}

	private void deleteFresh(String name) {
		bw.startCommand("sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "-fresh");
	}
	/*
	 * done snapshot hander
	 */

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

	/*
	 * start snapshot = no saving: 1.Re-define VM 2.Create snapshot from current
	 * disk 3.Start VM and do stuffs. 4.Restore back the VHD from snapshot 5.Delete
	 * snapshot
	 */
	public void startSnapShotFrom(String name) {
		shutdownVM(name);
		bw.startCommand("sudo virsh undefine " + name);
		String path = "/home/" + user + "/virsh/" + name + "/DefOS.xml";
		File testPath = new File(path);
		if (!testPath.exists()) {
			String pathbak = "/home/" + user + "/virsh/.config/DefOSbak.xml";
			bw.startCommand("cp " + pathbak + " " + path);
			pciConfiguration pci;
			try {
				pci = new pciConfiguration(path);
				pci.removeInterfaceFromXML();
				pci.writeInterfaceToXML();
				pci.setInterfacesDown();
				xml.modifyDisk("/home/" + user + "/virsh/" + name + "/linux.img");
				xml.removeHostDevices();
				xml.addHostDevice("0000:06:00.0");
				xml.WriteGeneralValue("name", "", name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		defineVM(path);
		createSnapshotCurrent(name);
		bw.startCommand("sudo virsh start " + name);
		displayVM(name);
		restoreCurrent(name);
		deleteCurrent(name);
		bw.startCommand("sudo virsh undefine " + name);
	}

	// Need no snapshot stuff when state saving is wanted.
	public void startVM(String name) {
		shutdownVM(name);
		bw.startCommand("sudo virsh undefine " + name);
		String path = "/home/" + user + "/virsh/" + name + "/DefOS.xml";
		File testPath = new File(path);
		if (!testPath.exists()) {
			String pathbak = "/home/" + user + "/virsh/.config/DefOSbak.xml";
			bw.startCommand("cp " + pathbak + " " + path);
			pciConfiguration pci;
			try {
				pci = new pciConfiguration(path);
				pci.removeInterfaceFromXML();
				pci.writeInterfaceToXML();
				pci.setInterfacesDown();
				xml.modifyDisk("/home/" + user + "/virsh/" + name + "/linux.img");
				xml.removeHostDevices();
				xml.addHostDevice("0000:06:00.0");
				xml.WriteGeneralValue("name", "", name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		defineVM(path);
		bw.startCommand("sudo virsh start " + name);
		displayVM(name);
		bw.startCommand("sudo virsh undefine " + name);
	}

	public void DeployNewVM(String name) {
		String path = "mkdir /home/" + user + "/virsh/" + name;
		String pathDef = "mkdir /home/" + user + "/virsh/.config/DefOS.xml";
		String pathXML = "/home/" + user + "/virsh/" + name + "/DefOS.xml";
		String pathbak = "/home/" + user + "/virsh/.config/DefOSbak.xml";
		bw.startCommand("mkdir " + path);
		bw.startCommand("cp " + pathDef + " " + path + "/DefOS.xml");
		bw.startCommand("cp " + pathbak + " " + pathXML);
		pciConfiguration pci;
		try {
			pci = new pciConfiguration(path);
			pci.removeInterfaceFromXML();
			pci.writeInterfaceToXML();
			pci.setInterfacesDown();
			xml.modifyDisk("/home/" + user + "/virsh/" + name + "/linux.img");
			xml.removeHostDevices();
			xml.addHostDevice("0000:06:00.0");
			xml.WriteGeneralValue("name", "", name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: Write General xml config for permission and etc
	}

	@Override
	public String vmType() {
		// TODO Auto-generated method stub
		return "virsh";
	}
}
