package fr.prog.tablut.view.pages.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Graphics;

import javax.swing.Timer;

import fr.prog.tablut.controller.adaptators.ButtonQuitGameAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonRestartAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameKeyAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameMouseAdaptator;
import fr.prog.tablut.controller.game.gameAdaptator.GameTimeAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.MoveType;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPanel;
import fr.prog.tablut.view.components.ImageComponent;
import fr.prog.tablut.view.components.NavPage;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.pages.game.sides.center.CenterSideGame;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardInterface;
import fr.prog.tablut.view.pages.game.sides.left.LeftSideGame;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;
import fr.prog.tablut.view.utils.Time;

/**
 * The page component that manages the game's view and regroups every components of the page
 * @see Page
 */
public class GamePage extends Page {
    private final CenterSideGame centerSide;
    private final RightSideGame rightSide;
    private final LeftSideGame leftSide;

    private GenericLabel winnerDescLabel, winnerTimeAndPlaysLabel;
    private NavPage winnerPage;
    private ImageComponent blackBT, blackT, whiteBT, whiteT;
    
    private PlayerEnum lastPlayer = null;

    /**
     * Creates the game's view manager
     * @param config The configuration to apply to the page
     */
    public GamePage(WindowConfig config) {
        super(config);
        windowName = PageName.GamePage;

        // the controller that's do the communication's bridge between the model and the view for the game
        GameController gameController = new GameController(this);

        final int s = (int)(config.windowHeight / 1.2); // size of the board
        final Dimension d = new Dimension((config.windowWidth - s)/2, config.windowHeight); // size of sides

        centerSide = new CenterSideGame(config, new Dimension(s, s));
        rightSide = new RightSideGame(config, d, gameController);
        leftSide = new LeftSideGame(config, gameController, d, this, rightSide);

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
        
        // add a board listener with the game controller
        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, this);
        centerSide.getBoard().addMouseListener(gameMouseAdaptator);
        centerSide.getBoard().addMouseMotionListener(gameMouseAdaptator);

        // communication's update time
        Timer time = new Timer(10, new GameTimeAdaptator(gameController));
        time.start();

        addKeyListener(new GameKeyAdaptator(gameController));
        setFocusable(true);

        initWinnerPanel(gameController);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        requestFocusInWindow();
    }

    /**
     * Triggered when the user comes on this page.
     * <p>Explicitly called if it wants to update the view</p>
     */
    @Override
    public void update() {
        boolean a = PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI(),
            b = PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI();
        
        if(!a || !b)
            getRightSide().togglePauseButton(false);
        else
            getRightSide().togglePauseButton(Game.getInstance().isPaused());

        centerSide.updateTurnOf();
        leftSide.update();

        // hide winner screen in the case the previous game was terminated
        foregroundPanel.setVisible(false);
        
        lastPlayer = null;
    }

    /**
     * Initializes all components and style for the victory screen
     * @param gc The game controller
     */
    private void initWinnerPanel(GameController gc) {
        foregroundPanel.setOpaque(true);
        
        foregroundPanel.setBackground(new Color(0, 0, 0, 230));
        
        // BOTTOM BUTTON PANEL
        BottomButtonPanel bottomButton = new BottomButtonPanel(PageName.HomePage, PageName.GamePage, "Rejouer", "Quitter");

        bottomButton.getButton1().setStyle("button.dark");

        bottomButton.getButton2().setAction(new ButtonRestartAdaptator((GenericButton)bottomButton.getButton2(), gc, true, this));
        bottomButton.getButton1().setHref(PageName.HomePage, new ButtonQuitGameAdaptator(bottomButton.getButton1(), GenericObjectStyle.getGlobalWindow()));
        //

        // NAV PAGE
        winnerPage = new NavPage("Joueur 1", "a gagn\u00e9", bottomButton);
        winnerPage.setSize(winnerPage.getWidth(), getHeight());
        winnerPage.getTitle().setForeground(GenericObjectStyle.getProp("title.light", "color"));
        winnerPage.getDescription().setForeground(GenericObjectStyle.getProp("label.light", "color"));
        
        // PAGE MIDDLE CONTENT
        GenericPanel p = new GenericPanel(new GridBagLayout());
        
        winnerDescLabel = new GenericLabel("L'attaquant n'a pas r\u00e9ussi \u00e0 encercler le Roi", 16);
        winnerTimeAndPlaysLabel = new GenericLabel("0h 0m 0s - 0 coups", 16);
        winnerDescLabel.setForeground(GenericObjectStyle.getProp("label.light", "color"));
        winnerTimeAndPlaysLabel.setForeground(GenericObjectStyle.getProp("label.darker", "color"));
        GenericRoundedButton replay = new GenericRoundedButton("Revoir le match", 170, 40);

        replay.setStyle("button.dark");

        replay.setAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foregroundPanel.setVisible(false);
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;

        c.gridy = 0;
        p.add(winnerDescLabel, c);

        c.gridy = 1;
        c.insets = new Insets(20, 0, 0, 0);
        p.add(winnerTimeAndPlaysLabel, c);

        c.gridy = 3;
        p.add(replay, c);
        //

        winnerPage.setContent(p);

        foregroundPanel.add(winnerPage);

        // SIDE IMAGES
        final int iconSize = 500;
        blackBT = new ImageComponent("theme/cut_big_black_tower.png", -iconSize/2, getHeight()/2 - iconSize/2, iconSize, iconSize);
        blackT = new ImageComponent("theme/semi_transparent_black_tower.png", -iconSize/2, getHeight()/2 - iconSize/2, iconSize, iconSize);
        
        whiteBT = new ImageComponent("theme/cut_big_white_tower.png", getWidth()-iconSize/2, getHeight()/2 - iconSize/2, iconSize, iconSize);
        whiteT = new ImageComponent("theme/semi_transparent_white_tower.png", getWidth()-iconSize/2, getHeight()/2 - iconSize/2, iconSize, iconSize);

        blackBT.load();
        blackT.load();
        whiteBT.load();
        whiteT.load();

        blackBT.setVisible(false);
        blackT.setVisible(false);
        whiteBT.setVisible(false);
        whiteT.setVisible(false);

        foregroundPanel.add(blackBT);
        foregroundPanel.add(blackT);
        foregroundPanel.add(whiteBT);
        foregroundPanel.add(whiteT);
    }

    /**
     * Updates the turn of the game's view
     * @param movetype The type of the move done
     */
    public void updateTurn(MoveType movetype) {
        // updates undo/redo buttons
        enableRedoButton(Game.getInstance().hasNextMove());
		enableUndoButton(Game.getInstance().hasPreviousMove());

        // updates the board
        centerSide.updateTurnOf();
        
        // this condition is done because animations are conflicting with this method.
        // This issue will be resolved in a future version, where the animation's manager
        // will be improved.
        if((lastPlayer == null || lastPlayer != Game.getInstance().getPlayingPlayerEnum()) && Game.getInstance().getLastPlay() != null) {
            lastPlayer = Game.getInstance().getPlayingPlayerEnum();
            
            switch(movetype) {
                case MOVE: leftSide.getMoveHistoryPanel().addAction(); break;
                case UNDO: leftSide.getMoveHistoryPanel().undo(); break;
                case REDO: leftSide.getMoveHistoryPanel().redo(); break;
                case RESTART: leftSide.getMoveHistoryPanel().clearChat(); break;
                default: break;
            }
        }
    }

    /**
     * Shows the game result's panel
     */
    public void announceWinner() {
        centerSide.getBoard().getBoardData().mousePosition = null;
        centerSide.getBoard().getBoardData().selectedCell = null;

        // setup winner page
        Game game = Game.getInstance();

        PlayerEnum winner = game.getWinner();
        boolean wid = winner == PlayerEnum.DEFENDER;

        // title (winner name)
        winnerPage.getTitle().setText(wid? game.getDefenderName() : game.getAttackerName());

        // desc (to know the role of the winner / loser)
        String desc = wid? "Le Roi a r\u00e9ussi \u00e0 s'\u00e9chapper" : "Le Roi n'a pas r\u00e9ussi \u00e0 s'\u00e9chapper";
        winnerDescLabel.setText(desc);

        winnerTimeAndPlaysLabel.setText(Time.formatToString(game.getDuration()) + " - " + ((game.getMovementsNumber()) / 2) + " coups");

        // show the broken / normal rook
        blackBT.setVisible(wid);
        blackT.setVisible(!wid);
        whiteT.setVisible(wid);
        whiteBT.setVisible(!wid);

        // display the page
        foregroundPanel.setVisible(true);
    }
    
    /**
     * Refreshes the view, refresh undo/redo buttons
     */
    public void refresh() {
    	enableRedoButton(Game.getInstance().hasNextMove());
		enableUndoButton(Game.getInstance().hasPreviousMove());
		
        revalidate();
		repaint();
    }
    
    /**
     * Removes the preview board
     */
    public void removePreview() {
    	centerSide.getBoard().removePreview();
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
    
    /**
     * Resets the known player that was the last to play
     */
    public void resetLastPlayer() {
    	lastPlayer = null;
    }

    /**
     * Returns the column index depending of pixel coord
     * @param x The pixel coord
     * @return The column index
     */
    public int getColFromXCoord(int x) {
        return getBoardInterface().getColFromXCoord(x);
    }

    /**
     * Returns the row index depending of pixel coord
     * @param y The pixel coord
     * @return The row index
     */
    public int getRowFromYCoord(int y) {
        return getBoardInterface().getRowFromYCoord(y);
    }

    /**
     * Returns the pixel coord of a column index
     * @param x The column index
     * @return The pixel coord
     */
    public int getXCoordFromCol(int x) {
        return getBoardInterface().getXCoordFromCol(x);
    }

    /**
     * Returns the pixel coord of a row index
     * @param y The row index
     * @return The pixel coord
     */
    public int getYCoordFromRow(int y) {
        return getBoardInterface().getYCoordFromRow(y);
    }

    /**
     * Updates the hovered cell and repaint the view
     */
    public void updateCellHovering(Point hoveringCell) {
    	centerSide.getBoard().updateCellHovering(hoveringCell);
        repaint();
    }
	
    /**
     * Clears the image on the mouse
     */
	public void clearImageOnMouse() {
		centerSide.getBoard().clearImageOnMouse();
		repaint();
	}

    /**
     * Updates the image on the mouse
     * @param img The image to put on the mouse
     * @param selectedCell The selected cell index
     */
    public void updateImageOnMouse(Image img, Point selectedCell) {
    	centerSide.getBoard().updateImageOnMouse(img, selectedCell);
		repaint();
    }

    /**
     * Updates the current piece's animation and repaint the view
     * @param animPosition The animation's point
     * @param animatedCell The animated starting cell
     * @param animatedFinalCell The animated final cell
     */
	public void update_anim(Point animPosition, Point animatedCell, Point animatedFinalCell) {
		centerSide.getBoard().update_anim(animPosition,animatedCell,animatedFinalCell);	
		repaint();
	}
    
    /**
     * Stops the current piece's animation and repaint the view
     */
	public void stop_anim() {
		centerSide.getBoard().stop_anim();
		repaint();
	}

    /**
     * Sets either the board is animating a piece or not
     * @param state The state of the animation
     */
    public void setIsInAnim(boolean state) {
        centerSide.getBoard().getBoardData().isAnim = state;
    }

    /**
     * Returns either a piece is animated
     * @return Either a piece is animated
     */
    public boolean isInAnim() {
        return centerSide.getBoard().getBoardData().isAnim;
    }

    /**
     * Toggles the pause button
     * @param isPaused The pause button's state
     */
    public void togglePauseButton(boolean isPaused) {
        rightSide.togglePauseButton(isPaused);
    }

    /**
     * Enables or disables the undo button
     * @param enable Either it has to enable the undo button or not
     */
    public void enableUndoButton(boolean enable) {
        leftSide.getMoveButtons().enableUndoButton(enable);
    }

    /**
     * Enables or disables the redo button
     * @param enable Either it has to enable the undo button or not
     */
    public void enableRedoButton(boolean enable) {
        leftSide.getMoveButtons().enableRedoButton(enable);
    }

    /**
     * Sets the given preview grid
     * @param grid The preview grid to set
     */
    public void setPreviewGrid(CellContent[][] grid, Integer moveIndex) {
        centerSide.setPreviewGrid(grid, moveIndex);
    }

    /**
     * Clears the move history chat
     */
    public void clearChat() {
        leftSide.clearChat();
    }
}
