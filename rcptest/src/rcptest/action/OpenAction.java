package rcptest.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import rcptest.Activator;
import rcptest.editor.FileInput;
import rcptest.editor.SampleTextEditor;

public class OpenAction extends Action {
	private static final String ID = "rcptest.action.open";
	private IWorkbenchWindow window;

	public OpenAction(IWorkbenchWindow window) {
		this.window = window;
		setText("&Open File");
		setId(ID);
		setImageDescriptor(Activator.getImageDescriptor("icons/open.gif"));
	}

	public void run() {
		FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
		String path = dialog.open();
		if (path != null) {
			//			MessageDialog.openInformation(window.getShell(), "Info", path);
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.openEditor(new FileInput(path), SampleTextEditor.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
