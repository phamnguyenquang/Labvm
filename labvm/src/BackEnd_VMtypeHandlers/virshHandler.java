package BackEnd_VMtypeHandlers;

import javax.swing.DefaultListModel;

import BackEnd_Misc.CommandExecutor;

public class virshHandler extends GeneralVMHandler {
	private CommandExecutor bw;

	public virshHandler() {
		bw = new CommandExecutor();
		bw.startCommand("sudo virsh connect qemu:///system");
	}

	public void displayVM(String name) {
		bw.startCommand("sudo virt-viewer -f --hotkeys='' " + name);
	}

	public void startVM(String name) {
		bw.startCommand("sudo virsh start " + name);
	}

	public void shutdownVM(String name) {
		bw.startCommand("sudo virsh shutdown " + name);
	}

	public void defineVM(String pathToXml) {
		bw.startCommand("sudo virsh define " + pathToXml);
	}

	public void createSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-create-as --domain " + name + " --name " + name + "_snapshot_fresh");
	}

	public void restorefromSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-revert " + name + " " + name + "_snapshot_fresh");
	}

	public void deleteSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "_snapshot_fresh");
	}

	public void createSnapshotCurent(String name) {
		bw.startCommand(
				"sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "_snapshot_current");
		bw.startCommand("sudo virsh snapshot-create-as --domain " + name + " --name " + name + "_snapshot_current");
		System.out.println("current created");
	}

	public void restorefromSnapshotCurrent(String name) {
		bw.startCommand("sudo virsh snapshot-revert " + name + " " + name + "_snapshot_current");
		System.out.println("current restored");
	}

	public void deleteSnapshotCurrent(String name) {
		bw.startCommand(
				"sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "_snapshot_current");
	}

	public DefaultListModel<String> getOSList() {
		return bw.listDir("/home/$(whoami)/virsh");
	}
	@Override
	public void startSnapShotFrom(String path) {
		// TODO Auto-generated method stub
		
	}
}
