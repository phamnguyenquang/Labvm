package BackEnd_VMtypeHandlers;

import javax.swing.DefaultListModel;

public abstract class GeneralVMHandler {
	public abstract void startVM(String name);
	public abstract void startSnapShotFrom(String path);
	public abstract DefaultListModel<String> getOSList();
	public abstract String vmType();
}
