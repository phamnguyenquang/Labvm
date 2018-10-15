package Development;

import java.io.File;

import javax.sound.sampled.*;
import javax.swing.DefaultListModel;

import BackEnd_VMtypeHandlers.GeneralVMHandler;

public class VBoxHandler extends GeneralVMHandler {
	public void test() {
		try {
			File soundFile = new File("/home/quang/test.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = audioIn.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VBoxHandler() {
		test();
	}

	@Override
	public void startVM(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startSnapShotFrom(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public DefaultListModel<String> getOSList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String vmType() {
		// TODO Auto-generated method stub
		return null;
	}
}
