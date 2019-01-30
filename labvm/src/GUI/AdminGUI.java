package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JOptionPane;

import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AdminGUI {

	private JFrame frame;
	private String[] functionList = { "Advanced Backup Management", "Advanced Parameter Setup",
			"Server Setting(under construction)", "Exit" };
	private JList list;
	private String function = "";
	private GeneralVMHandler vmHandler;

	/**
	 * Create the application.
	 */
	public AdminGUI(GeneralVMHandler vmm) {
		vmHandler = vmm;
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
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblSelectFunctions = new JLabel("Select Functions");
		GridBagConstraints gbc_lblSelectFunctions = new GridBagConstraints();
		gbc_lblSelectFunctions.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectFunctions.gridx = 0;
		gbc_lblSelectFunctions.gridy = 0;
		frame.getContentPane().add(lblSelectFunctions, gbc_lblSelectFunctions);

		list = new JList(functionList);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				function = list.getSelectedValue().toString();
				System.out.println(function);
			}
		});
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 4;
		gbc_list.gridheight = 8;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		frame.getContentPane().add(list, gbc_list);

		JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (function) {
				case "Advanced Backup Management": {
					JOptionPane.showMessageDialog(null, "Under Construction", "Notice", 1);
					break;
				}
				case "Exit":{
					try {
						pciConfiguration pci = new pciConfiguration();
						pci.vfioUnbind();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
					break;
				}
				default: {
					JOptionPane.showMessageDialog(null, "Under Construction", "Notice", 1);
					break;
				}
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 9;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new OSSelectionGUI(vmHandler);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 9;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		frame.setSize(1366, 768);
		frame.setVisible(true);
	}

}
