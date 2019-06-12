package BackEnd_VMtypeHandlers;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;

public class vmHandler extends GeneralVMHandler {
	private CommandExecutor bw;
	private pciConfiguration pci;
	private String pciAddressesComm;
	private String path;

	public vmHandler() {
		try {
			pci = new pciConfiguration();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new CommandExecutor();
	}

	public void startVM(String path) {
		if (path == "" || path == null) {
			JOptionPane.showMessageDialog(null, "Please select an OS", "Error", 0);
		} else {
			pciAddressesComm = "";
			pci.vfioBind();
			for (int i = 0; i < pci.PCISlotNumber(); ++i) {
				pciAddressesComm += ("-device vfio-pci,host=" + pci.getPCIaddressAt(i) + " ");
			}
			System.out.println("sudo kvm -vga qxl -m 2048 -hda " + path
					+ " -sdl -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm);
			bw.startCommand("sudo kvm -vga qxl -m 1024 -hda " + path
					+ " -sdl -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm);
		}
	}

	public void startSnapShotFrom(String path) {
		if (path == "" || path == null) {
			JOptionPane.showMessageDialog(null, "Please select an OS", "Error", 0);
		} else {
			pciAddressesComm = "";
			pci.vfioBind();
			for (int i = 0; i < pci.PCISlotNumber(); ++i) {
				pciAddressesComm += ("-device vfio-pci,host=" + pci.getPCIaddressAt(i) + " ");
			}
			System.out.println("sudo kvm -vga qxl -m 2048 -hda " + path
					+ " -sdl -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm);
			bw.startCommand("sudo kvm -vga qxl -m 1024 -hda " + path
					+ " -usb -usbdevice tablet -cpu host -smp 4,sockets=1,cores=4,threads=1 " + pciAddressesComm
					+ " -snapshot");
		}
	}

	public DefaultListModel<String> getOSList() {
		System.out.println("Getting OS");
		return bw.listOS("/home/$(whoami)/virsh");
	}

	@Override
	public String vmType() {
		// TODO Auto-generated method stub
		return "kvm";
	}

	@Override
	public void DeployNewVM(String name) {
		// TODO Auto-generated method stub
		
	}

}
