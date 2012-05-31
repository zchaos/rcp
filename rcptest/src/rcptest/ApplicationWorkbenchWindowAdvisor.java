package rcptest;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	private TrayItem trayItem;
	private Image trayImage;
	private IWorkbenchWindow window;
	private ApplicationActionBarAdvisor actionBarAdvisor;

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return actionBarAdvisor = new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);
	}

	public void postWindowOpen() {
		super.postWindowOpen();
		window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			createMinimize();
			hookPopupMenu(window);
		}
	}

	private void hookPopupMenu(IWorkbenchWindow win) {
		trayItem.addListener(SWT.MenuDetect, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				MenuManager trayMenu = new MenuManager();
				Menu menu = trayMenu.createContextMenu(window.getShell());
				actionBarAdvisor.fillTrayItem(trayMenu);
				menu.setVisible(true);
			}
		});
	}

	private void createMinimize() {
		window.getShell().addShellListener(new ShellAdapter() {
			public void shellIconified(ShellEvent e) {
				window.getShell().setVisible(false);
			}
		});
		trayItem.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Shell shell = window.getShell();
				if (!shell.isVisible()) {
					shell.setVisible(true);
					window.getShell().setMinimized(false);
				}
			}
		});
	}

	private TrayItem initTaskItem(IWorkbenchWindow win) {
		Tray tray = win.getShell().getDisplay().getSystemTray();
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayImage = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "/icons/alt_about.gif")
				.createImage();
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("TrayItem");
		return trayItem;
	}

	public void dispose() {
		if (trayImage != null) {
			trayImage.dispose();
			trayItem.dispose();
		}
	}
}
