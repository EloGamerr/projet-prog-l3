package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Font;

import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;
import fr.prog.tablut.view.pages.game.sides.center.board.GameColors;

/**
 * The designer layer that draws the board
 * @see Designer
 */
public class BoardDesigner extends Designer {
    /**
     * Creates the board designer's layer
     * @see Designer
     * @see BoardDrawer
     * @param bd The board drawer reference
     */
    public BoardDesigner(BoardDrawer bd) {
        super(bd);
    }

    @Override
    public void draw(BoardData data) {
        int bw = g.getBorderWidth();
        int cellSize = g.getCellSize();
        int cellNumber = g.getCellNumber();
        int _x_ = g.getRealX(0);
        int _y_ = g.getRealY(0);

        // background
        g.setColor(GameColors.BACKGROUND_GRID);
        g.fillRectCoords(_x_, _y_, g.getSize() - bw*2, g.getSize() - bw*2);
        
        g.fillRectCoords(_x_, _y_, g.getSize() - bw*2, g.getSize() - bw*2);
        
       
        // border
        g.setColor(GameColors.BORDER_GRID);
        g.strokeWidth(bw);
        g.strokeSquareCoords(_x_ - bw/2, _y_ - bw/2, g.getSize() - bw - 1);
        
        // cells separators + A-I / 1-9 indicators
        g.strokeWidth(g.getCellSepSize());

        g.setFont(new Font("Staatliches", Font.PLAIN, 14));

        int m = g.getSize() - bw * 2 - 1;

        for(int i=0; i <= cellNumber; i++) {
            int z = i * cellSize;
            int x = _x_ + z;
            int y = _y_ + z;

            g.setColor(GameColors.CELL_BORDER);
            g.line(x, _y_, x, _y_ + m); // V
            g.line(_x_, y, _x_ + m, y); // H

            if(i < cellNumber) {
                Integer n = i+1;
                
                int a = - bw/2;
                int b = bw + z/*  + cellSize / 2 */;

                g.setColor(GameColors.LETTERS_NUMBERS_COLOR);
                g.drawString(n.toString(), _x_ + a - 3, _y_ + b + 12); // 0-9
                g.drawString(Character.toString((char)(i + 65)), _x_ + b, _y_ + a + 5); // A-I
            }
        }
    }
}
