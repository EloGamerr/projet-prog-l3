package fr.prog.tablut.view.pages.load;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPannel;
import fr.prog.tablut.view.components.NavPage;

public class LoadPage extends Page {
	public LoadPage(WindowConfig config) {
		super(config);

		windowName = WindowName.LoadWindow;

		NavPage page = new NavPage(
			"Charger une partie",
			"Choisissez une sauvegarde à charger",
			new BottomButtonPannel(WindowName.HomeWindow, WindowName.GameWindow, "Jouer !")
		);
		
		// load games
		page.setContent(new ButtonTogglePannel());

		add(page);
	}
}
