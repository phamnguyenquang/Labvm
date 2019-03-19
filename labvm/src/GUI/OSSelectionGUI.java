package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import BackEnd_Misc.CommandExecutor;
import BackEnd_Misc.pciConfiguration;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;
import BackEnd_VMtypeHandlers.vmHandler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

public class OSSelectionGUI {

	private JFrame frame;
	private CommandExecutor linuxCommandExecutor;
	private String path = "";
	private String OSName = "";
	private JList<String> list;
	private String vmManagerType;

	private ArrayList<JButton> Btn_list = new ArrayList<JButton>();
	private DefaultListModel<String> OSImageList = new DefaultListModel<String>();
	private ArrayList<JButton> Btn_list_special = new ArrayList<JButton>();
	private GeneralVMHandler vmHandler;

	public OSSelectionGUI(GeneralVMHandler vmm) {
		try {
			linuxCommandExecutor = new CommandExecutor();
			vmHandler = vmm;
			vmManagerType = vmm.vmType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Operating System List");
		frame.setBounds(100, 100, 450, 300);
		// set to DO_NOTHING_ON_CLOSE upon implementation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		/*
		 * Setting up the components
		 */
		JButton btnNewButton_4 = new JButton("Start without state saving");
		Btn_list.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (path == "" || path == null) {
					System.out.println("Please select a user");
					JOptionPane.showMessageDialog(null, "Please select a user", "Error", 0);
				} else {
					if (JOptionPane.showConfirmDialog(null,
							"This optoion will not allow any changed in state to be saved\n To make any changes in the state, please select the appropriate starting option\n In case any option is disabled, please contact the administrator \n Proceed?",
							"NOTE", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						/*
						 * Non Virsh method, comment accordingly
						 */
						String pathStart = path + "/linux.img";
						switch (vmManagerType) {
						case "virsh": {
							vmHandler.startSnapShotFrom(OSName);
							break;
						}
						case "kvm": {
							vmHandler.startSnapShotFrom(pathStart);
							break;
						}
						default: {
							break;
						}
						}
					} else {
						System.out.println("No chosen");
					}

				}
				System.out.println(path);
			}
		});
		JButton btnNewButton_7 = new JButton("Start with state saving");
		Btn_list_special.add(btnNewButton_7);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (path == "" || path == null) {
					System.out.println("Please select a user");
					JOptionPane.showMessageDialog(null, "Please select a an OS", "Error", 0);
				} else {
					String pathStart = path + "/linux.img";
					switch (vmManagerType) {
					case "virsh": {
						vmHandler.startVM(OSName);
						break;
					}
					case "kvm": {
						vmHandler.startVM(pathStart);
						break;
					}
					default: {
						break;
					}
					}
				}
			}
		});

		JButton btnNewButton_3 = new JButton("Restart");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				linuxCommandExecutor.startCommand("sudo shutdown -r now");
			}
		});

		JButton btnNewButton_2 = new JButton("Shutdown");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				linuxCommandExecutor.startCommand("sudo shutdown -h now");
			}
		});

		JButton btnNewButton = new JButton("Admin Mode");
		// Btn_list.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new PasswordGUI(frame, vmHandler);
			}
		});

		JButton btnNewButton_1 = new JButton("More Functions");
		Btn_list.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new FunctionListGUI(path, linuxCommandExecutor, vmHandler);
			}
		});

		JButton button = new JButton("?");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Please consult the user manual or contact the system administrator", "Warning", 1);
			}
		});
		/*
		 * Done setting up necessary buttons
		 */
		for (int i = 0; i < Btn_list.size(); ++i) {
			Btn_list.get(i).setEnabled(false);
		}
		for (int i = 0; i < Btn_list_special.size(); ++i) {
			Btn_list_special.get(i).setEnabled(false);
		}

		/*
		 * putting the OS name to display
		 */

		OSImageList = vmHandler.getOSList();
		list = new JList<String>(OSImageList);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OSName = list.getSelectedValue().toString();
				String path1 = "/home/$(whoami)/virsh/";
				path = "/home/$(whoami)/virsh/" + '"' + OSName + '"';
				OSName = list.getSelectedValue().toString();
				System.out.println(path);
				for (int i = 0; i < Btn_list.size(); ++i) {
					Btn_list.get(i).setEnabled(true);
				}
				linuxCommandExecutor.startCommand("cat " + path1 + '"' + OSName + '"' + "/state");
				System.out.println(linuxCommandExecutor.getResult());
				if (linuxCommandExecutor.getResult().equals("false")) {
					System.out.println("test success");
					for (int i = 0; i < Btn_list_special.size(); ++i) {
						Btn_list_special.get(i).setEnabled(false);
					}
				} else if (linuxCommandExecutor.getResult().equals("true")) {
					for (int i = 0; i < Btn_list_special.size(); ++i) {
						Btn_list_special.get(i).setEnabled(true);
					}
				}
			}
		});
		JLabel lblSelectUserlecturer = new JLabel("Select an Operating System/Exercise:");
		/*
		 * Done setting up "Decorating" the component on the board
		 */

		GridBagConstraints gbc_lblSelectUserlecturer = new GridBagConstraints();
		gbc_lblSelectUserlecturer.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectUserlecturer.gridx = 1;
		gbc_lblSelectUserlecturer.gridy = 0;
		frame.getContentPane().add(lblSelectUserlecturer, gbc_lblSelectUserlecturer);

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 16;
		gbc_list.gridheight = 8;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		frame.getContentPane().add(list, gbc_list);

		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 0;
		gbc_btnNewButton_4.gridy = 9;
		frame.getContentPane().add(btnNewButton_4, gbc_btnNewButton_4);

		GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
		gbc_btnNewButton_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_7.gridx = 1;
		gbc_btnNewButton_7.gridy = 9;
		frame.getContentPane().add(btnNewButton_7, gbc_btnNewButton_7);

		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.gridwidth = 2;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 9;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);

		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 15;
		gbc_button.gridy = 9;
		frame.getContentPane().add(button, gbc_button);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 10;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 10;
		frame.getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);

		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_3.gridx = 2;
		gbc_btnNewButton_3.gridy = 10;
		frame.getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
		frame.setSize(1366, 768);
		frame.setVisible(true);
	}

	public void dispose() {
		frame.dispose();
	}

}
