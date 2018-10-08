package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;

import java.awt.Insets;

public class FunctionListGUI {
	/*
	 * The option list is fixed, therefore hardcoded.
	 */
	private JFrame frame;
	private String[] data = { "Set parameters", "Backup Selection", "Backup Creation", "Reset To Original Image",
			"Update", "Exit" };
	private JList list;
	private SwingWorker<Void, Void> mySwingWorker;
	private String function;
	private String path;
	private CommandExecutor linuxCommandExecutor;
	private GeneralVMHandler vmHandler;

	/**
	 * Create the GUI.
	 */
	public FunctionListGUI(String p, CommandExecutor bw, GeneralVMHandler vmm) {
		path = p;
		vmHandler = vmm;
		linuxCommandExecutor = bw;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (function) {
				case "Set parameters": {
					JOptionPane.showMessageDialog(null, "Function Under Construction", "Notice", 0);
					break;
				}
				case "Exit": {
					System.exit(0);
					break;
				}
				case "Backup Selection": {
					if (path == "" || path == null) {
						System.out.println("Please select a user");
						JOptionPane.showMessageDialog(null, "Please select a user", "Error", 0);
					} else if (linuxCommandExecutor.startCommand("cat " + path + "/state").equals("false")) {
						JOptionPane.showMessageDialog(null,
								"Back up options is not available for this image \n Please contact the administrator for further information",
								"Error", 1);
					} else {
						path += "/backup/";
						System.out.println(path);
						new BackupSelection(path, linuxCommandExecutor, vmHandler);
						frame.dispose();
					}

					break;
				}
				case "Backup Creation": {
					if (path == "" || path == null) {
						System.out.println("Please select a user");
						JOptionPane.showMessageDialog(null, "Please select a user", "Error", 0);
					} else if (linuxCommandExecutor.startCommand("cat " + path + "/state").equals("false")) {
						JOptionPane.showMessageDialog(null,
								"Back up options is not available for this image \n Please contact the administrator for further information",
								"Error", 1);
					} else {
						new TextInputFieldGUI("Enter the name for back up", path, "backup");
					}
					break;
				}
				case "Update": {
					JOptionPane.showMessageDialog(null, "Function Under Construction", "Notice", 0);
					break;
				}
				case "Reset To Original Image": {
					if (path == "" || path == null) {
						System.out.println("Please select a user");
						JOptionPane.showMessageDialog(null, "Please select a user", "Error", 0);
					} else if (linuxCommandExecutor.startCommand("cat " + path + "/state").equals("false")) {
						JOptionPane.showMessageDialog(null,
								"Back up options is not available for this image \n Please contact the administrator for further information",
								"Error", 1);
					} else {
						JOptionPane.showMessageDialog(null, "Function Under Construction", "Notice", 0);
					}
					break;
				}
				default: {
					break;
				}
				}
			}
		});

		JLabel lblChooseAFunction = new JLabel("Choose a Function");
		GridBagConstraints gbc_lblChooseAFunction = new GridBagConstraints();
		gbc_lblChooseAFunction.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseAFunction.gridx = 0;
		gbc_lblChooseAFunction.gridy = 0;
		frame.getContentPane().add(lblChooseAFunction, gbc_lblChooseAFunction);
		/*
		 * Setting up list and functions
		 */
		list = new JList(data);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				function = list.getSelectedValue().toString();
				System.out.println(function);
			}
		});

		/*
		 * Finished setting up
		 */
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridwidth = 5;
		gbc_list_1.gridheight = 8;
		gbc_list_1.insets = new Insets(0, 0, 5, 0);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 0;
		gbc_list_1.gridy = 1;
		frame.getContentPane().add(list, gbc_list_1);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 9;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new OSSelectionGUI(vmHandler);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 9;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		frame.setSize(1366, 768);
		frame.setVisible(true);
	}

}
