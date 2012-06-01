package rcptest.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import rcptest.view.SampleViewPart;

public class SampleAction implements IViewActionDelegate {
	private SampleViewPart view;

	@Override
	public void run(IAction arg0) {
		// TODO Auto-generated method stub
		MessageDialog.openInformation(view.getViewSite().getShell(), "Information",
				"Very well,you did it,you did add an action to this view.You are my hero!");
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IViewPart arg0) {
		// TODO Auto-generated method stub
		this.view = (SampleViewPart) arg0;
	}

}
