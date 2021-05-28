package fr.prog.tablut.model.game;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public enum CellContent {
	EMPTY(null),
	ATTACK_TOWER("black_tower.png"),
	DEFENSE_TOWER("white_tower.png"),
	KING("king.png"),
	GATE("door.png");
	
	Image image;

	/**
	 * Loads the image of a cell depending of its type.
	 * 
	 * Images are in the folder res/images/
	 * 
	 * @param imagePath The image's path
	 */
	CellContent(String imagePath) {
		String path = "images/" + imagePath;

		if(imagePath != null) {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

			try {
				image = ImageIO.read(in);
			} catch (IOException e) {
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
