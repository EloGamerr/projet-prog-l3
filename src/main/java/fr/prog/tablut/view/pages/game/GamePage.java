package fr.prog.tablut.view.pages.game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Graphics;

import javax.swing.Timer;

import fr.prog.tablut.controller.game.gameAdaptator.GameKeyAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameMouseAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameTimeAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.pages.game.sides.center.CenterSideGame;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardInterface;
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
    public GamePage(WindowConfig config) {
        super(config);
        GameController gameController = new GameController(this);
        
        windowName = PageName.GamePage;

        final int s = (int)(config.windowHeight / 1.2);
        final Dimension d = new Dimension((config.windowWidth - s)/2, config.windowHeight);

        centerSide = new CenterSideGame(config, new Dimension(s, s));
        rightSide = new RightSideGame(config, d, gameController);
        leftSide = new LeftSideGame(config, gameController, d, rightSide);

        setPLayout(new GridBagLayout());

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
        
        
        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, this);
        centerSide.getBoard().addMouseListener(gameMouseAdaptator);
        centerSide.getBoard().addMouseMotionListener(gameMouseAdaptator);

        Timer time = new Timer(1, new GameTimeAdaptator(gameController));
        time.start();

        addKeyListener(new GameKeyAdaptator(gameController));
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        requestFocusInWindow();
    }

    @Override
    public void update() {
         boolean a = PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI(),
            b = PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI();

         boolean enablePauseButton = a && b;

        rightSide.enablePauseButton(enablePauseButton);

        if(!enablePauseButton)
            this.getRightSide().togglePauseButton(false);
        else
            this.getRightSide().togglePauseButton(Game.getInstance().isPaused());
    }

    /**
     * Returns the grid window object
     * @return The grid window
     */
    public BoardInterface getBoardInterface() {
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

    public int getColFromXCoord(int x) {
        return getBoardInterface().getColFromXCoord(x);
    }

    public int getRowFromYCoord(int y) {
        return getBoardInterface().getRowFromYCoord(y);
    }

    public int getXCoordFromCol(int x) {
        return getBoardInterface().getXCoordFromCol(x);
    }

    public int getYCoordFromRow(int y) {
        return getBoardInterface().getYCoordFromRow(y);
    }

    public void updateCellHovering(Point hoveringCell) {
    	centerSide.getBoard().updateCellHovering(hoveringCell);
        repaint();
    }
	
	public void clearImageOnMouse() {
		centerSide.getBoard().clearImageOnMouse();
		repaint();
	}

    public void updateImageOnMouse(Image img, Point selectedCell) {
    	centerSide.getBoard().updateImageOnMouse(img, selectedCell);
		repaint();
    }


	public void update_anim(Point animPosition, Point animatedCell, Point animatedFinalCell) {
		centerSide.getBoard().update_anim(animPosition,animatedCell,animatedFinalCell);	
		repaint();
	}
    
	public void stop_anim() {
		centerSide.getBoard().stop_anim();
		repaint();
	}

    public void setIsInAnim(boolean state) {
        centerSide.getBoard().getBoardData().isAnim = state;
    }

    public boolean isInAnim() {
        return centerSide.getBoard().getBoardData().isAnim;
    }

    public void togglePauseButton(boolean isPaused) {
        rightSide.togglePauseButton(isPaused);
    }

    public void enableUndoButton(boolean enable) {
        leftSide.getMoveButtons().enableUndoButton(enable);
    }

    public void enableRedoButton(boolean enable) {
        leftSide.getMoveButtons().enableRedoButton(enable);
    }
}