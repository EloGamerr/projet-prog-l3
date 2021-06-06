package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.center.board.designers.*;

public class BoardInterface extends GameInterfaceSide {
	private final Designer boardDesigner, piecesDesigner, indicatorsDesigner;
    private BoardDrawer boardDrawer;
	
    public BoardInterface(int size) {
        super(new Dimension(size, size));

        boardDrawer = new BoardDrawer();

        // initialize the board drawer's data
        boardDrawer.setBoardDimension(size);
        boardDrawer.setPosition(getWidth()/2 - size/2, getHeight()/2 - size/2);

        // organize layers
    	boardDesigner = new BoardDesigner(boardDrawer);
    	piecesDesigner = new PiecesDesigner(boardDrawer);
    	indicatorsDesigner = new IndicatorsDesigner(boardDrawer);
	}

    @Override
    protected void paint(Graphics2D g2d) {
        // Anti-aliased text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // update board drawer's data in the case the container changed its size
        boardDrawer.setGraphics(g2d);
        boardDrawer.setBoardDimension(Math.min(getWidth(), getHeight()));
        boardDrawer.setPosition(getWidth()/2 - boardDrawer.getSize()/2, getHeight()/2 - boardDrawer.getSize()/2);
    	
        // draw board layers
        boardDesigner.draw();
    	piecesDesigner.draw();
    	indicatorsDesigner.draw();
    }
}