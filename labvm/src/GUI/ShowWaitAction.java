package GUI;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import BackEnd.CommandExecutor;

public class ShowWaitAction {
	protected static final long SLEEP_TIME = 3 * 1000;
	private String command;
	private CommandExecutor bw;
	private SwingWorker<Void, Void> mySwingWorker;
	private ActionEvent arg0;

	public ShowWaitAction(SwingWorker<Void, Void> swingWorker, ActionEvent e) {
		mySwingWorker = swingWorker;
		arg0 = e;
		showProgressBar();
	}

	private void showProgressBar() {
		Window win = SwingUtilities.getWindowAncestor((AbstractButton) arg0.getSource());
		final JDialog dialog = new JDialog(win, "Dialog", ModalityType.APPLICATION_MODAL);

		mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("state")) {
					if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
						dialog.dispose();
					}
				}
			}
		});
		mySwingWorker.execute();

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(progressBar, BorderLayout.CENTER);
		panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(win);
		dialog.setSize(400,70);
		dialog.setVisible(true);
	}
}
