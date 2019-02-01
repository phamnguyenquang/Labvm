package GUI;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;

import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.vmHandler;

import javax.swing.JList;
import javax.swing.JOptionPane;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class BackupSelection {

	private JFrame frame;
	private CommandExecutor linuxCommandHandler;
	private String OSname;
	private String path;
	private GeneralVMHandler vmHandler;
	private JList<String> list;

	/**
	 * Create the UI.
	 */
	public BackupSelection(String pp, CommandExecutor bb, GeneralVMHandler vmm) {
		path = pp;
		linuxCommandHandler = bb;
		vmHandler = vmm;

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Backup Functionality");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		linuxCommandHandler.listOS(path);
		System.out.println(path);
		list = new JList<String>(linuxCommandHandler.getOutput());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OSname = list.getSelectedValue().toString();
				// System.out.println(command);
			}
		});

		JLabel lblNewLabel = new JLabel("Select a Backed up Image");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 4;
		gbc_list.gridwidth = 14;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		frame.getContentPane().add(list, gbc_list);

		JButton btnNewButton = new JButton("Start the OS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (OSname == "" || OSname == null) {
					JOptionPane.showMessageDialog(null, "Please select an OS", "Error", 0);
				} else {
					String type = vmHandler.vmType();
					String startPath = path + OSname;
					switch (type) {
					case "kvm": {
						vmHandler.startVM(startPath);
						break;
					}
					case "virsh": {
						vmHandler.startVM(OSname);
						break;
					}
					default: {
						break;
					}
					}

				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

		JButton btnTestFunction = new JButton("Back");
		btnTestFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new OSSelectionGUI(vmHandler);
			}
		});
		GridBagConstraints gbc_btnTestFunction = new GridBagConstraints();
		gbc_btnTestFunction.anchor = GridBagConstraints.WEST;
		gbc_btnTestFunction.gridwidth = 2;
		gbc_btnTestFunction.insets = new Insets(0, 0, 5, 5);
		gbc_btnTestFunction.gridx = 1;
		gbc_btnTestFunction.gridy = 5;
		frame.getContentPane().add(btnTestFunction, gbc_btnTestFunction);
		frame.setSize(1366, 768);
		// frame.pack();
		frame.setVisible(true);
	}

	public void reload() {
		frame.dispose();
		initialize();
	}

}
