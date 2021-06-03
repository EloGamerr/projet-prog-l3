package fr.prog.tablut.view.pages.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Timer;

import javax.swing.JFrame;

import fr.prog.tablut.controller.game.gameAdaptator.GameKeyAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameMouseAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameTimeAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.pages.game.sides.center.CenterSideGame;
import fr.prog.tablut.view.pages.game.sides.center.board.GridWindow;
import fr.prog.tablut.view.pages.game.sides.left.LeftSideGame;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;

/**
 * The page component that manages the game's view and regroups every components of the page
 * @see Page
 */
public class GamePage extends Page {
    private final CenterSideGame centerSide;
    private final RightSideGame rightSide;
    private final LeftSideGame leftSide;

    /**
     * Creates the game's view manager
     * @param config The configuration to apply to the page
     */
    public GamePage(WindowConfig config, JFrame jFrame) {
        super(config);
        GameController gameController = new GameController(this);
        
        windowName = WindowName.GameWindow;

        final int s = (int)(config.windowHeight / 1.2);
        final Dimension d = new Dimension((config.windowWidth - s)/2, config.windowHeight);

        centerSide = new CenterSideGame(config, new Dimension(s, s));
        leftSide = new LeftSideGame(config, gameController, d);
        rightSide = new RightSideGame(config, d, gameController);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 0;

        add(leftSide, c);

        c.gridx = 1;
        add(centerSide, c);

        c.gridx = 2;
        add(rightSide, c);
        
        
        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, centerSide.getBoard());
        centerSide.getBoard().addMouseListener(gameMouseAdaptator);
        centerSide.getBoard().addMouseMotionListener(gameMouseAdaptator);

        Timer time = new Timer(50, new GameTimeAdaptator(gameController));
        time.start();

        jFrame.addKeyListener(new GameKeyAdaptator(gameController));
    }
    
    /**
     * Returns the grid window object
     * @return The grid window
     */
    public GridWindow getGridWindow() {
        return centerSide.getBoard();
    }

    /**
     * Returns the right side component of the page
     * @return The right side component of the page
     */
    public RightSideGame getRightSide() {
        return rightSide;
    }

    /**
     * Returns the left side component of the page
     * @return The left side component of the page
     */
    public LeftSideGame getLeftSide() {
        return leftSide;
    }

    @Override
    public void update() {
         boolean a = PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI(),
            b = PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI();

        rightSide.enablePauseButton(a && b);
    }
}