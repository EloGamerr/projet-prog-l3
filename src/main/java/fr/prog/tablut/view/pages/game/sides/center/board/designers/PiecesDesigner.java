package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Image;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;

/**
 * The designer layer that draws all board's pieces
 * @see Designer
 */
public class PiecesDesigner extends Designer {
    /**
     * Creates the pieces designer's layer
     * @see Designer
     * @see BoardDrawer
     * @param bd The board drawer reference
     */
    public PiecesDesigner(BoardDrawer bd) {
        super(bd);
    }

    @Override
    public void draw(BoardData data) {
        Game game = Game.getInstance();
		int imgSize = g.getCellSize() / 2;

        // draw preview grid
        if(data.previewGrid != null) {
            for(int y=0; y < data.previewGrid.length; y++) {
                for(int x=0; x < data.previewGrid[y].length; x++) {
                    CellContent cell = data.previewGrid[y][x];
                    
                    if(cell != null && cell.getImage() != null) {
                        g.drawImage(cell.getImage(), x, y, imgSize, imgSize, true);
                    }
                }
            }
        }

        // draw normal current grid
        else {
            // draw all static pieces
            for(int y = 0; y < game.getRowAmount(); y++) {
                for(int x = 0; x < game.getColAmount(); x++) {
                    // don't draw the animated pieces
                    if(
                        (data.selectedCell != null && data.selectedCell.x == x && data.selectedCell.y == y) ||
                        (data.isAnim && data.animatedCell != null && data.animatedCell.x == x && data.animatedCell.y == y)
                    )
                        continue;

                    CellContent cell = game.getCellContent(x, y);
                    Image img = cell.getImage();

                    if(img != null) {
                        g.drawImage(img, x, y, imgSize, imgSize, true);
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
