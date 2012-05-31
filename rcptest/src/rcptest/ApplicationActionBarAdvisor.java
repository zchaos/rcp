package rcptest;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction iExitAction;
	private IWorkbenchAction iAboutAction;
	private IWorkbenchAction iNewWindowAction;
	private IWorkbenchAction iSaveAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		register(iExitAction = ActionFactory.QUIT.create(window));
		register(iAboutAction = ActionFactory.ABOUT.create(window));
		register(iNewWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window));
		register(iSaveAction = ActionFactory.SAVE.create(window));
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		fileMenu.add(iNewWindowAction);
		fileMenu.add(iSaveAction);
		fileMenu.add(new Separator());
		fileMenu.add(iExitAction);

		helpMenu.add(iAboutAction);
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		toolbar.add(iNewWindowAction);
		toolbar.add(iSaveAction);
		toolbar.add(iAboutAction);
		toolbar.add(iExitAction);
	}
}
