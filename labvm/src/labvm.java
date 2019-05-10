import java.io.*;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;
import BackEnd_VMtypeHandlers.vmHandler;
import Development.SomeTest;
import Development_GUI.TestUI;
import GUI.OSSelectionGUI;
import XMLhandler.XMLReadWrite;

public class labvm {

	public static void main(String args[]) throws IOException {
//		GeneralVMHandler vmHandler = new virshHandler();
//		new OSSelectionGUI(vmHandler);
		SomeTest tt = new SomeTest();
		tt.makeFTPConnection("192.168.20.128", "quang", "kalimdor");
		tt.downloadFile("/home/quang/test.ovpn", "test.ovpn");
		tt.terminateConnection();

//		new TestUI();
	}
}
