package fr.prog.tablut.view.pages.help;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPannel;
import fr.prog.tablut.view.components.NavPage;

public class HelpPage extends Page {
	public HelpPage(WindowConfig config, Page currentPage) {
		super(config);

		windowName = WindowName.HelpWindow;

		NavPage page = new NavPage(new BottomButtonPannel(WindowName.HomeWindow, "Retour"));
		page.setContent(new HelpTable());
		add(page);
	}
}