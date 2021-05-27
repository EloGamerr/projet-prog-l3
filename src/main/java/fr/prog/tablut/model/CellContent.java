package fr.prog.tablut.model;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public enum CellContent {
	EMPTY(null),
	ATTACK_TOWER("black_tower.png"),
	DEFENSE_TOWER("white_tower.png"),
	KING("king.png"),
	GATE("door.png"), KINGPLACE("king_place.png");
	
	Image image;
	CellContent(String imagePath) {
		if(imagePath != null) {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(imagePath);
			try {
				image = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Image getImage() {
		return image;
	}
}
