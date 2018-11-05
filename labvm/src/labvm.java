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
		GeneralVMHandler vmHandler = new vmHandler();
		new OSSelectionGUI(vmHandler);
//		XMLReadWrite testIO = new XMLReadWrite("/home/quang/DevOS.xml");
//		
//		testIO.removeInterface();
//		testIO.addInterface("enp3s0");

//		testIO.removeHostDevices();
//		testIO.addHostNode();
//		testIO.addHostDevice("0000:00:20.0");
//		
//		CommandExecutor comd = new CommandExecutor();
//		comd.startCommand("ls /home/quang");
	}
}
