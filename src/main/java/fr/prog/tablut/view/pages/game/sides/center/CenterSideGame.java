package fr.prog.tablut.view.pages.game.sides.center;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JLabel;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.ImageComponent;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardInterface;

/**
 * The center component of the game's page.
 * <p>Stores the board's view and the "turn of" component.</p>
 * @see GameInterfaceSide
 * @see BoardInterface
 */
public class CenterSideGame extends GameInterfaceSide {
    protected final BoardInterface boardInterface;
    protected GenericPanel turnOf;

    /**
     * Creates the center component of the game's page.
     * <p>Stores the board's view and the "turn of" component.</p>
     * @see GameInterfaceSide
     * @see BoardInterface
     * @param d The dimension of the component
     */
    public CenterSideGame(Dimension d) {
        super(d);

        setLayout(new BorderLayout());

        boardInterface = new BoardInterface(d.height);

        initTurnOf();

        add(boardInterface, BorderLayout.CENTER);
        add(turnOf, BorderLayout.SOUTH);
    }

    /**
     * Returns the board's view component
     * @return The board's view component
     */
    public BoardInterface getBoard() {
        return boardInterface;
    }

    /**
     * Initializes the "turn of" component
     */
    private void initTurnOf() {
        turnOf = new GenericPanel(null, new Dimension(getWidth(), 60));
        updateTurnOf();
    }

    /**
     * Updates the "turn of" component, which is looking the game's turn.
     * @see Game
     */
    public void updateTurnOf() {
        // if the game is created and if the current playing player exists too
        if(Game.getInstance() != null && Game.getInstance().getPlayingPlayerEnum() != null) {
            // We prefer to clear the component and recreate an image and a text
            turnOf.removeAll();

            String name;
            Image img = null;
            ImageComponent ic;

            switch(Game.getInstance().getPlayingPlayerEnum()) {
                case ATTACKER:
                    name = "Au tour de l'attaquant";
                    img = CellContent.ATTACK_TOWER.getImage();
                    break;

                case DEFENDER:
                    name = "Au tour du d\u00e9fenseur";
                    img = CellContent.DEFENSE_TOWER.getImage();
                    break;

                default:
                    name = "Unknow turn of";
            }

            if(img != null) {
                ic = new ImageComponent(img, new Point(turnOf.getWidth()/2 - 100, 0), new Dimension(30, 30));
                turnOf.add(ic);
            }

            GenericLabel labelTurnOfName = new GenericLabel(name, "Farro", 12);
            labelTurnOfName.setLocation(turnOf.getWidth()/2 - 60, 10);
            labelTurnOfName.setSize(getWidth()/2, turnOf.getHeight());
            labelTurnOfName.setVerticalAlignment(JLabel.TOP);

            turnOf.add(labelTurnOfName);
        }
    }
    
    /**
     * Sets the preview grid to visualize
     * @param grid The preview grid to visualize
     * @param moveIndex The move index, to retrieve its feedback
     */
    public void setPreviewGrid(CellContent[][] grid, int moveIndex) {
        boardInterface.setPreviewGrid(grid, moveIndex);
    }

    /**
     * Called when the window is resized
     * @param width The new width it wants to set
     * @param height The new height it wants to set
     */
    @Override
    public void onResize(int width, int height) {
        if(boardInterface != null)
            boardInterface.resize(new Dimension(width, height));

        if(turnOf != null && turnOf.getComponentCount() > 1) {
            turnOf.getComponent(0).setLocation(turnOf.getWidth()/2 - 70, 10);
            turnOf.getComponent(1).setLocation(turnOf.getWidth()/2 - 60, 10);
        }
    }
}
