package Development_GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import BackEnd_Misc.CommandExecutor;
import BackEnd_VMtypeHandlers.GeneralVMHandler;
import BackEnd_VMtypeHandlers.virshHandler;
import Development.Network;

import org.eclipse.swt.widgets.Button;

import java.awt.event.ActionEvent;
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
import org.eclipse.swt.widgets.Text;

public class TestSWT {

	protected Shell shell;
	private CommandExecutor bw;
	private GeneralVMHandler vmHandler = new virshHandler();
	private DefaultListModel<String> OSImageList = new DefaultListModel<String>();
	private JScrollPane scrollPane;
	private JList<String> Jlist;
	private Network net;
	private String path = "";
	private String OSName = "";
	private String vmManagerType;
	private Text text;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestSWT window = new TestSWT();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TestSWT() {
		try {
			open();
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
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
		//net = new Network();

		OSImageList = vmHandler.getOSList();
		Jlist = new JList<String>(OSImageList);
		// --------------------------------------------------------
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(null);

		Button btnNewButton = new Button(shell, SWT.NONE);

		btnNewButton.setBounds(0, 0, 56, 26);
		btnNewButton.setText("Start");

		SashForm sashForm = new SashForm(shell, SWT.NONE);
		sashForm.setBounds(0, 26, 782, 531);

		// ----------------------------------------------------------
		List list = new List(sashForm, SWT.BORDER);

		text = new Text(sashForm, SWT.READ_ONLY | SWT.BORDER);
		sashForm.setWeights(new int[] { 180, 599 });

		for (int i = 0; i < Jlist.getModel().getSize(); ++i) {
			list.add(Jlist.getModel().getElementAt(i));
		}

		Button Sync = new Button(shell, SWT.NONE);

		Sync.setBounds(56, 0, 56, 26);
		Sync.setText("Sync");

		Button Root = new Button(shell, SWT.NONE);

		Root.setBounds(109, -3, 56, 29);
		Root.setText("Root");

		Button Shutdown = new Button(shell, SWT.NONE);

		Shutdown.setBounds(161, -3, 81, 29);
		Shutdown.setText("Shutdown");
		
		Button Restart = new Button(shell, SWT.NONE);
		Restart.setBounds(242, -3, 81, 34);
		Restart.setText("Restart");

		Button More = new Button(shell, SWT.NONE);
		More.setBounds(323, -3, 81, 34);
		More.setText("More");
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
		text.setText("Read Only");



		Sync.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("success");
			}
		});
		Root.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new Password(shell);
			}
		});
		Shutdown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bw.startCommand("sudo shutdown -h now");
			}
		});
		Restart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bw.startCommand("sudo shutdown -r now");
			}
		});
		More.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new connection();
				shell.dispose();
			}
		});
	}

	public void exit() {
		shell.close();
	}

	public void suspend() {
		shell.dispose();
	}
}
