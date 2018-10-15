import java.io.*;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;
import BackEnd_VMtypeHandlers.vmHandler;
import GUI.OSSelectionGUI;
import XMLhandler.XMLReadWrite;

public class labvm {

	public static void main(String args[]) throws IOException {
//		GeneralVMHandler vmHandler = new virshHandler();
//		new OSSelectionGUI(vmHandler);
		new XMLReadWrite("/home/quang/virsh/DevOS.xml").replaceDevice("0000:05:02.0", "0000:05:00.0");
		;
	}
}
