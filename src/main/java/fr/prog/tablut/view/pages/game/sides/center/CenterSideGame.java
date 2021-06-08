package fr.prog.tablut.view.pages.game.sides.center;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JLabel;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.view.components.ImageComponent;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardInterface;

public class CenterSideGame extends GameInterfaceSide {
    protected BoardInterface boardInterface;
    protected GenericPanel turnOf;

    public CenterSideGame(WindowConfig config, Dimension d) {
        super(d);

        setLayout(new BorderLayout());

        boardInterface = new BoardInterface(d.height);

        initTurnOf();

        add(boardInterface, BorderLayout.CENTER);
        add(turnOf, BorderLayout.SOUTH);
    }

    public BoardInterface getBoard() {
        return boardInterface;
    }

    private void initTurnOf() {
        turnOf = new GenericPanel(null, new Dimension(getWidth(), 60));
        updateTurnOf();
    }

    public void updateTurnOf() {
        if(Game.getInstance() != null && Game.getInstance().getPlayingPlayerEnum() != null) {
            turnOf.removeAll();

            String name = null;
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
    
    public void setPreviewGrid(CellContent[][] grid, int moveIndex) {
        boardInterface.setPreviewGrid(grid, moveIndex);
    }
}
