package BackEnd_VMtypeHandlers;

import javax.swing.DefaultListModel;

import BackEnd_Misc.CommandExecutor;

public class virshHandler extends GeneralVMHandler {
	private CommandExecutor bw;

	public virshHandler() {
		bw = new CommandExecutor();
		bw.startCommand("sudo virsh connect qemu:///system");
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

	private void createSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-create-as --domain " + name + " --name " + name + "_snapshot_fresh");
	}

	private void restorefromSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-revert " + name + " " + name + "_snapshot_fresh");
	}

	private void deleteSnapshotFresh(String name) {
		bw.startCommand("sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "_snapshot_fresh");
	}

	private void createSnapshotCurent(String name) {
		bw.startCommand(
				"sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "_snapshot_current");
		bw.startCommand("sudo virsh snapshot-create-as --domain " + name + " --name " + name + "_snapshot_current");
		System.out.println("current created");
	}

	private void restorefromSnapshotCurrent(String name) {
		bw.startCommand("sudo virsh snapshot-revert " + name + " " + name + "_snapshot_current");
		System.out.println("current restored");
	}

	private void deleteSnapshotCurrent(String name) {
		bw.startCommand(
				"sudo virsh snapshot-delete --domain " + name + " --snapshotname " + name + "_snapshot_current");
	}

	@Override
	public DefaultListModel<String> getOSList() {
		return bw.listDir("/home/$(whoami)/virsh");
	}

	public void startSnapShotFrom(String path) {
		shutdownVM(path);
		restorefromSnapshotFresh(path);
		bw.startCommand("sudo virsh start " + path);
		displayVM(path);

	}

	public void startVM(String name) {
		shutdownVM(name);
		restorefromSnapshotCurrent(name);
		bw.startCommand("sudo virsh start " + name);
		displayVM(name);
		createSnapshotCurent(name);
	}

	@Override
	public String vmType() {
		// TODO Auto-generated method stub
		return "virsh";
	}
}
