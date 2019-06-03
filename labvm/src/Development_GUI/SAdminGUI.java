package Development_GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.ScrolledComposite;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class SAdminGUI {

	protected Shell shell;
	private String[] functionList = { "Client Configuration","Create New VM", "Parameter Setup", "Exit" };
	private String selectedFunction;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public SAdminGUI() {
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
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		/*----------------------------------------
		 * Set up Elemets
		 */

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(0, 0, 442, 233);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		List list = new List(scrolledComposite, SWT.BORDER);

		scrolledComposite.setContent(list);
		scrolledComposite.setMinSize(list.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Button btnNewButton = new Button(shell, SWT.NONE);

		btnNewButton.setBounds(89, 232, 88, 29);
		btnNewButton.setText("Back");

		Button btnNewButton_1 = new Button(shell, SWT.NONE);

		btnNewButton_1.setBounds(0, 227, 95, 34);
		btnNewButton_1.setText("OK");

		/*----------------------------------------
		 * Set up panel content
		 */

		for (int i = 0; i < functionList.length; ++i) {
			list.add(functionList[i]);
		}
		/*----------------------------------------
		 * Setup Functions
		 */

		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectedFunction = list.getItem(list.getSelectionIndex()).toString();
				System.out.println(selectedFunction);
			}
		});

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				new TestSWT();
			}
		});
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (selectedFunction) {
				case "Client Configuration": {
					JOptionPane.showMessageDialog(null, "Under Construction", "Notice", 1);
					break;
				}
				case "Create New VM": {
					JOptionPane.showMessageDialog(null, "Under Construction", "Notice", 1);
					break;
				}
				default: {
					JOptionPane.showMessageDialog(null, "Under Construction", "Notice", 1);
					break;
				}
				}
			}
		});

	}
}
