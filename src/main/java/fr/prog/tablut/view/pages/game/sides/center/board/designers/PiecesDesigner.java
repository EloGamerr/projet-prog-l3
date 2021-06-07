package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Image;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;

public class PiecesDesigner extends Designer {
    public PiecesDesigner(BoardDrawer bd) {
        super(bd);
    }

    @Override
    public void draw(BoardData data) {
        Game game = Game.getInstance();
		int imgSize = (int)(g.getCellSize() / 2);

        // draw all static pieces
        for(int i = 0; i < game.getColAmout(); i++) {
			for(int j = 0; j < game.getRowAmout(); j++) {
                // don't draw the animated pieces
                if(
                    (data.selectedCell != null && data.selectedCell.x == i && data.selectedCell.y == j) ||
                    (data.isAnim && data.animatedCell != null && data.animatedCell.x == i && data.animatedCell.y == j)
                )
                    continue;

                CellContent cell = game.getCellContent(i, j);
                Image img = cell.getImage();

                if(img != null) {
                    g.drawImage(img, i, j, imgSize, imgSize, true);
                }
            }
        }

        // draw the piece in the mouse
        if(data.imageOnMouse != null && data.lastMousePosition != null) {
            int x = g.getRealX(0);
            int y = g.getRealY(0);

			int xImg = Math.min(Math.max(x, data.lastMousePosition.x - imgSize/2), x + g.getSize() - imgSize);
			int yImg = Math.min(Math.max(y, data.lastMousePosition.y - imgSize/2), y + g.getSize() - imgSize);
			
    		g.drawImageCoords(data.imageOnMouse, xImg, yImg, imgSize, imgSize, null);
    	}
		
        // draw the animated piece
		if(data.isAnim) {
			g.drawImageCoords(data.animatedImage, data.animPosition.x + imgSize/2, data.animPosition.y + imgSize/2, imgSize, imgSize, null);
		}
    }
}
