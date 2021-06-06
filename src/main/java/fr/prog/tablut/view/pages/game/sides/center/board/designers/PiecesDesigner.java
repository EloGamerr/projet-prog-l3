package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Color;
import java.awt.Image;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;
import fr.prog.tablut.view.pages.game.sides.center.board.GameColors;

public class PiecesDesigner extends Designer {
    public PiecesDesigner(BoardDrawer bd) {
        super(bd);
    }

    @Override
    public void draw() {
        Game game = Game.getInstance();
		int imgSize = (int)(g.getCellSize() / 1.5);

        // draw all static pieces
        for(int i = 0; i < game.getRowAmout(); i++) {
			for(int j = 0; j < game.getColAmout(); j++) {
                // don't draw the animated pieces
                /* if(
                    (selectedCell != null && selectedCell.getFirst() == i && selectedCell.getSecond() == j) ||
                    (anim && animCell != null && animCell.getFirst() == i && animCell.getSecond() == j)
                )
                    continue; */

                CellContent cell = game.getCellContent(i, j);
                Image img = cell.getImage();

                if(img != null) {
                    g.drawImage(img, j, i, imgSize, imgSize, true);
                }
            }
        }

        // draw the piece in the mouse
        /* if(imageOnMouse != null && lastMousePosition != null) {
			int xImg = Math.max(x, lastMousePosition.x - imgSize/2);
			xImg = Math.min(xImg, x + width - imgSize);
			int yImg = Math.max(y, lastMousePosition.y - imgSize/2);
			yImg = Math.min(yImg, y + height - imgSize);
			
    		gridWindow.drawImage(imageOnMouse, xImg, yImg, imgSize, imgSize);
    	} */
		
        // draw the animated piece
		/* if(anim) {
			gridWindow.drawImage(animImage, xAnim , yAnim, imgSize, imgSize);
		} */
    }
}
