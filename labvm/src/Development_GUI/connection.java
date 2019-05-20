package Development_GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import Development.Network;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class connection {

	protected Shell shell;
	private Text userField;
	private Text PassFoeld;
	private Text addressField;
	private Label Address;
	private Network test;

	/**
	 * Launch the application.
	 * @param args
	 */
	public connection()
	{
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
		shell.setSize(450, 184);
		shell.setText("SWT Application");
		shell.setLayout(null);
		
		Label user = new Label(shell, SWT.NONE);
		user.setBounds(10, 10, 64, 19);
		user.setText("Username");
		
		Label Pass = new Label(shell, SWT.NONE);
		Pass.setBounds(10, 46, 64, 19);
		Pass.setText("Password");
		
		userField = new Text(shell, SWT.BORDER);
		userField.setBounds(84, 0, 358, 30);
		
		PassFoeld = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		PassFoeld.setBounds(84, 36, 358, 30);
		
		addressField = new Text(shell, SWT.BORDER);
		addressField.setBounds(84, 72, 358, 30);
		
		Label Address = new Label(shell, SWT.NONE);
		Address.setBounds(10, 83, 64, 19);
		Address.setText("Address");
		
		Button Cancel = new Button(shell, SWT.NONE);

		Cancel.setBounds(354, 119, 88, 29);
		Cancel.setText("Cancel");
		
		Button connect = new Button(shell, SWT.NONE);

		connect.setBounds(260, 119, 88, 29);
		connect.setText("OK");
		
		connect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		Cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				new TestSWT();
			}
		});
	}
}
