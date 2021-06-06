package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;

public abstract class Designer {
    protected BoardDrawer g;

    public Designer() {
        
    }

    public Designer(BoardDrawer bd) {
        g = bd;
    }

    public abstract void draw(BoardData data);
}
