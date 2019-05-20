package Development_GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import BackEnd_Misc.SHA512Handler;
import GUI.AdminGUI;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Password {

	protected Shell shell;
	private Text password;
	private Button OK;
	private Button Cancel;
	private Button Reset;
	private Shell pshell;

	public Password(Shell ss) {
		pshell = ss;
		open();
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
		shell = new Shell();
		shell.setSize(450, 130);
		shell.setText("SWT Application");
		shell.setLayout(null);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(0, 0, 444, 20);
		lblNewLabel.setText("Enter the Password:");

		password = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		password.setEchoChar('*');
		password.setBounds(0, 26, 444, 30);

		OK = new Button(shell, SWT.NONE);

		OK.setBounds(0, 62, 95, 34);
		OK.setText("OK");

		Cancel = new Button(shell, SWT.NONE);

		Cancel.setBounds(96, 62, 95, 34);
		Cancel.setText("Cancel");

		Reset = new Button(shell, SWT.NONE);

		Reset.setBounds(349, 62, 95, 34);
		Reset.setText("Reset");

		OK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String pass = new String(password.getText());
				SHA512Handler check = new SHA512Handler(pass);
				if (check.isMatch()) {
					System.out.println("correct pass");
					pshell.dispose();
					System.out.println("test");
					shell.dispose();
					new SAdminGUI();
				} else {
					JOptionPane.showMessageDialog(null, "Wrong Password", "Error", 0);
				}
			}
		});
		Cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		Reset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				password.setText("");
			}
		});

	}
}
