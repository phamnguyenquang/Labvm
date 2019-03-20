package GUI;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import BackEnd_Misc.CommandExecutor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextInputFieldGUI {

	private JFrame frame;
	private String text = "";
	private JTextField textField;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private String tittle;
	private String dirPath;
	private CommandExecutor command;
	private String action;

	/**
	 * Create the GUI.
	 * 
	 * @param ioArea
	 */
	public TextInputFieldGUI(String title, String path, String act) {
		initialize(title);
		tittle = title;
		dirPath = path;
		action = act;
		command = new CommandExecutor();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String title) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		lblNewLabel = new JLabel(title);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {

						text = textField.getText();
						switch (action) {
						case "backup": {
							String backupPath = dirPath + "/backup/" + text + ".img";
//							System.out.println("sudo cp " + dirPath + "/linux.img " + backupPath);
							command.startCommand("sudo cp " + dirPath + "/linux.img " + backupPath);
							frame.dispose();
//							command.startCommand("sudo cp " + dirPath + "/linux.img " + backupPath);
							JOptionPane.showMessageDialog(null, "Backup Created", "Notice", 1);
							break;
							}
						}
						Thread.sleep(3000);
						return null;
					}
				};

				new ShowWaitAction(mySwingWorker, arg0);
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 4;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

		btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 4;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		frame.getRootPane().setDefaultButton(btnNewButton);
		frame.setSize(500, 120);
		frame.setVisible(true);
	}

	public String getText() {
		return text;
	}

}
