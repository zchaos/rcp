package rcptest;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		register(exitAction = ActionFactory.QUIT.create(window));
		register(aboutAction = ActionFactory.ABOUT.create(window));
	}

	protected void fillMenuBar(IMenuManager menuBar) {
	}

	public void fillTrayItem(MenuManager trayMenu) {
		trayMenu.add(aboutAction);
		trayMenu.add(exitAction);
	}
}
