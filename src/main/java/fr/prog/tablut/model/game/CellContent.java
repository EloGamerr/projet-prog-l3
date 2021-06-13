package fr.prog.tablut.model.game;

import java.awt.Image;
import java.io.IOException;

import fr.prog.tablut.model.Loader;


public enum CellContent {
	EMPTY(null),
	ATTACK_TOWER("chess/small/black_tower_small.png"),
	DEFENSE_TOWER("chess/small/white_tower_small.png"),
	KING("chess/small/king_small.png"),
	GATE("chess/small/door_small.png");

	Image image;

	/**
	 * Loads the image of a cell depending of its type.
	 *
	 * Images are in the folder res/images/
	 *
	 * @param imagePath The image's path
	 */
	CellContent(String imagePath) {
		if(imagePath != null) {
			try {
                image = Loader.getImage(imagePath);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the image of the cell
	 * @return The image
	 */
	public Image getImage() {
		return image;
	}
}
