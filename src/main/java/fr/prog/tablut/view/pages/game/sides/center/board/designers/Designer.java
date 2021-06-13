package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;

/**
 * An abstract class for a board's designer.
 * <p>A board designer is a layer of drawing for a specific thing.</p>
 * <p>It has a BoardDrawer's reference</p>
 */
public abstract class Designer {
    protected BoardDrawer g;

    public Designer() {

    }

    /**
     * Creates a designer with a BoardDrawer's reference
     * @see BoardDrawer
     * @param bd The board's drawer reference
     */
    public Designer(BoardDrawer bd) {
        g = bd;
    }

    /**
     * Draw method called by the layer manager
     * @param data The board's data the layer can take care of to draw
     */
    public abstract void draw(BoardData data);
}
