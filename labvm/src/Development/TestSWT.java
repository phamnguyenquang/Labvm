package Development;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import BackEnd_Misc.CommandExecutor;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;

import org.eclipse.swt.widgets.Button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TestSWT {

	protected Shell shell;
	private CommandExecutor bw;
	private GeneralVMHandler vmHandler = new virshHandler();
	private DefaultListModel<String> OSImageList = new DefaultListModel<String>();
	private JScrollPane scrollPane;
	private JList<String> Jlist;
	private SomeTest net;
	private String path = "";
	private String OSName = "";
	private String vmManagerType;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestSWT window = new TestSWT();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		vmManagerType = vmHandler.vmType();
		net = new SomeTest();

		OSImageList = vmHandler.getOSList();
		Jlist = new JList<String>(OSImageList);
		// --------------------------------------------------------
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(null);

		Button btnNewButton = new Button(shell, SWT.NONE);

		btnNewButton.setBounds(0, 0, 37, 26);
		btnNewButton.setText("Srt");

		SashForm sashForm = new SashForm(shell, SWT.NONE);
		sashForm.setBounds(0, 26, 782, 531);

		// ----------------------------------------------------------
		List list = new List(sashForm, SWT.BORDER);

		for (int i = 0; i < Jlist.getModel().getSize(); ++i) {
			list.add(Jlist.getModel().getElementAt(i));
		}

		List list_1 = new List(sashForm, SWT.BORDER);
		sashForm.setWeights(new int[] {108, 333});

		Button btnNewButton_1 = new Button(shell, SWT.NONE);

		btnNewButton_1.setBounds(38, 0, 37, 26);
		btnNewButton_1.setText("Syn");
		
		Button btnNewButton_2 = new Button(shell, SWT.NONE);

		btnNewButton_2.setBounds(76, 0, 37, 29);
		btnNewButton_2.setText("New Button");
		
		Button btnNewButton_3 = new Button(shell, SWT.NONE);

		btnNewButton_3.setBounds(113, 0, 37, 29);
		btnNewButton_3.setText("New Button");
		// ------------------------------------------------------------

		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OSName = list.getItem(list.getSelectionIndex()).toString();
//				OSName = list.getSelection().toString();
				String path1 = "/home/$(whoami)/virsh/";
				path = "/home/$(whoami)/virsh/" + '"' + OSName + '"';
				System.out.println(path);
			}
		});

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
						System.out.println("No path chosen");
					}

				}
				System.out.println(path);
			}
		});
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("success");
			}
		});
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Admin Function");
			}
		});
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("shutdown ");
			}
		});
	}
}
