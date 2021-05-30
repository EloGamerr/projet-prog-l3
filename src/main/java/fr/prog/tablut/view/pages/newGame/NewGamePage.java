package fr.prog.tablut.view.pages.newGame;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPannel;
import fr.prog.tablut.view.components.NavPage;

public class NewGamePage extends Page {
	public NewGamePage(WindowConfig config) {
		super(config);

		windowName = WindowName.NewGameWindow;

		NavPage page = new NavPage(
			"Nouvelle partie",
			"Choisissez les paramètres de partie qui vous conviennent puis cliquez sur confirmer",
			new BottomButtonPannel(WindowName.HomeWindow, WindowName.GameWindow, "Jouer !")
		);
		
		// game settings
		page.setContent(new SelectionPlayer());

		add(page);
	}
}