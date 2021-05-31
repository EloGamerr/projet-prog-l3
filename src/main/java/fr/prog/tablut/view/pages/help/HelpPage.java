package fr.prog.tablut.view.pages.help;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPannel;
import fr.prog.tablut.view.components.NavPage;

/**
 * The Help page. Extends Page class.
 * <p>Shows shortcuts and their action.</p>
 * @see Page
 */
public class HelpPage extends Page {
	/**
	 * Creates a help page.
	 * <p>It needs to know the reference of the last opened page so when the user clicks on "Retour",
	 * it goes back to the previous page.</p>
	 * @param config The configuration to apply to the page
	 * @param currentPage The previous page to go on when the user closes this page
	 */
	public HelpPage(WindowConfig config, Page currentPage) {
		super(config);

		windowName = WindowName.HelpWindow;

		NavPage page = new NavPage(new BottomButtonPannel(WindowName.HomeWindow, "Retour"));
		page.setContent(new HelpTable());
		add(page);
	}
}