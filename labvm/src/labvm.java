import java.io.*;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.vmHandler;
import GUI.OSSelectionGUI;

public class labvm {

	public static void main(String args[]) throws IOException {
		pciConfiguration pci = new pciConfiguration();
		CommandExecutor linuxCommandExecutor = new CommandExecutor();
		GeneralVMHandler vmHandler = new vmHandler(pci, linuxCommandExecutor);
		new OSSelectionGUI(vmHandler);
	}
}
