package Development;

import java.io.File;

import javax.sound.sampled.*;

public class VBoxHandler {
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
}
