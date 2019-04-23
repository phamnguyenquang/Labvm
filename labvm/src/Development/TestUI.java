package Development;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;

import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ScrollPane;

import javax.swing.JSplitPane;

import BackEnd_Misc.CommandExecutor;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestUI {

	private JFrame frame;
	private CommandExecutor bw;
	private GeneralVMHandler vmHandler = new virshHandler();
	private DefaultListModel<String> OSImageList = new DefaultListModel<String>();
	private JScrollPane scrollPane;
	private JList<String> list;
	
	public TestUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		OSImageList = vmHandler.getOSList();
		frame = new JFrame();
		frame.setSize(new Dimension(600,400));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnSync = new JButton("Sync");

		GridBagConstraints gbc_btnSync = new GridBagConstraints();
		gbc_btnSync.insets = new Insets(0, 0, 5, 5);
		gbc_btnSync.gridx = 0;
		gbc_btnSync.gridy = 0;
		frame.getContentPane().add(btnSync, gbc_btnSync);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO start vm
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 0;
		frame.getContentPane().add(btnStart, gbc_btnStart);
		
		JButton btnUpload = new JButton("Upload");
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpload.gridx = 2;
		gbc_btnUpload.gridy = 0;
		frame.getContentPane().add(btnUpload, gbc_btnUpload);
		
		JButton btnMore = new JButton("More");
		GridBagConstraints gbc_btnMore = new GridBagConstraints();
		gbc_btnMore.insets = new Insets(0, 0, 5, 5);
		gbc_btnMore.gridx = 3;
		gbc_btnMore.gridy = 0;
		frame.getContentPane().add(btnMore, gbc_btnMore);
		
		JButton btnAdmin = new JButton("Admin");
		GridBagConstraints gbc_btnAdmin = new GridBagConstraints();
		gbc_btnAdmin.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdmin.gridx = 4;
		gbc_btnAdmin.gridy = 0;
		frame.getContentPane().add(btnAdmin, gbc_btnAdmin);
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.gridwidth = 6;
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		frame.getContentPane().add(splitPane, gbc_splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		
		OSImageList = vmHandler.getOSList();
		list = new JList<String>(OSImageList);
		
		scrollPane= new JScrollPane(list);
		splitPane.setLeftComponent(scrollPane);
		
		splitPane.setDividerLocation(100);
		
		btnSync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OSImageList.clear();
				list=new JList<String>(OSImageList);
				scrollPane = new JScrollPane(list);
			}
		});
		
		frame.setVisible(true);
	}
	public void dispose() {
		frame.dispose();
	}

}
