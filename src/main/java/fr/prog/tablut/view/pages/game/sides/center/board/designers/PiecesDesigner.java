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

        // draw preview grid
        if(data.previewGrid != null) {
            for(int x=0; x < data.previewGrid.length; x++) {
                for(int y=0; y < data.previewGrid[y].length; y++) {
                    CellContent cell = data.previewGrid[y][x];
                    
                    if(cell != null && cell.getImage() != null) {
                        g.drawImage(cell.getImage(), y, x, imgSize, imgSize, true);
                    }
                }
            }
        }

        // draw normal current grid
        else {
            // draw all static pieces
            for(int i = 0; i < game.getColAmount(); i++) {
                for(int j = 0; j < game.getRowAmount(); j++) {
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
            if(data.imageOnMouse != null && data.mousePosition != null) {
                int x = g.getRealX(0);
                int y = g.getRealY(0);

                int xImg = Math.min(Math.max(x, data.mousePosition.x - imgSize/2), x + g.getSize() - imgSize);
                int yImg = Math.min(Math.max(y, data.mousePosition.y - imgSize/2), y + g.getSize() - imgSize);
                
                g.drawImageCoords(data.imageOnMouse, xImg, yImg, imgSize, imgSize, null);
            }
            
            // draw the animated piece
            if(data.isAnim) {
                g.drawImageCoords(data.animatedImage, data.animPosition.x + imgSize/2, data.animPosition.y + imgSize/2, imgSize, imgSize, null);
            }
        }
    }
}
