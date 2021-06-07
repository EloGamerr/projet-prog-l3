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
import fr.prog.tablut.model.game.Game;
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

/**
 * The page component that manages the game's view and regroups every components of the page
 * @see Page
 */
public class GamePage extends Page {
    private final CenterSideGame centerSide;
    private final RightSideGame rightSide;
    private final LeftSideGame leftSide;

    private GenericLabel winnerDescLabel;
    private NavPage winnerPage;
    private ImageComponent blackBT, blackT, whiteBT, whiteT;

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
        winnerPage = new NavPage("Player 1", "a gagn\u00e9", bottomButton);
        winnerPage.setSize(winnerPage.getWidth(), getHeight());
        winnerPage.getTitle().setForeground(GenericObjectStyle.getProp("title.light", "color"));
        winnerPage.getDescription().setForeground(GenericObjectStyle.getProp("label.light", "color"));
        
        // PAGE MIDDLE CONTENT
        GenericPanel p = new GenericPanel(new GridBagLayout());
        
        winnerDescLabel = new GenericLabel("L'attaquant n'a pas réussi à encercler le Roi", 16);
        winnerDescLabel.setForeground(GenericObjectStyle.getProp("label.light", "color"));
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

        c.insets = new Insets(20, 0, 0, 0);
        c.gridy = 1;
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
        String desc = wid? "Le Roi a réussi à s'échapper" : "Le Roi n'a pas réussi à s'échapper";
        winnerDescLabel.setText(desc);

        if(wid) {
            blackBT.setVisible(true);
            blackT.setVisible(false);
            whiteT.setVisible(true);
            whiteBT.setVisible(false);
        }

        else {
            blackT.setVisible(true);
            blackBT.setVisible(false);
            whiteBT.setVisible(true);
            whiteT.setVisible(false);
        }


        // display the page
        foregroundPanel.setVisible(true);
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