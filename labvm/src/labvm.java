import java.io.*;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;
import BackEnd_VMtypeHandlers.vmHandler;
import GUI.OSSelectionGUI;

public class labvm {

	public static void main(String args[]) throws IOException {
		GeneralVMHandler vmHandler = new virshHandler();
		new OSSelectionGUI(vmHandler);
	}
}
