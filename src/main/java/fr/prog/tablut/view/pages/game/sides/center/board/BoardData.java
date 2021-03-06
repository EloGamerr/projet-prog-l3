package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Movement;

/**
 * The view side board's data
 */
public class BoardData {
    public Point selectedCell = null;
    public Point animatedCell = null;
    public Point animatedFinalCell = null;
    public Point hoveringCell = null;
    public Point hoveringPossibleMoveCell = null;
    public Point animPosition = null;
    public Point mousePosition = new Point(0, 0);
    public Point lastMousePosition = new Point(0, 0);
    public Image animatedImage = null;
    public Image imageOnMouse = null;
    public List<Movement> accessibleCells = new ArrayList<>();
	public boolean isAnim = false;
    public CellContent[][] previewGrid = null;
    public Integer previewMoveIndex = -1;
}
