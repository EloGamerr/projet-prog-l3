package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.Plays;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.utils.Time;

public class HistoryChatField extends GenericPanel {
    private int labelHeight;
	private final GamePage gamePage;
    private GridBagConstraints c;
    private List<LabelField> allHistory = new ArrayList<LabelField>();
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
	public HistoryChatField(GamePage gamePage) {
        super(new GridBagLayout());

        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTH;

		this.gamePage = gamePage;

        labelHeight = 20;
    }

    public void addAction() {
        // update the view's size
        LabelField.setDimension(new Dimension(getWidth(), labelHeight));

        // new action's label in the chat
        LabelField newPlayerAction = new LabelField(false, null, this, c.gridy);

        // default text's color
		newPlayerAction.setForeground(GenericObjectStyle.getProp("chat", "color"));

        // erase next moves if these exist
		if(allHistory.size() >= c.gridy) {
			for(int i = allHistory.size() - 1; i >= c.gridy; i--) {
				remove(allHistory.get(i));
				allHistory.remove(i);
			}
		}

        // add action to the chat
        allHistory.add(newPlayerAction);

        add(newPlayerAction, c);
		
        // number of moves displayed
        c.gridy++;
    }

    public int getLabelHeight() {
        return labelHeight;
    }

    public void clear() {
        removeAll();

        c.gridy = 0;

        allHistory.clear();
    }

    public int getMovesNumber() {
        return c.gridy;
    }

	public void undo() {
		LabelField currentLabel = allHistory.get(--c.gridy);
        currentLabel.setActive(true);
        currentLabel.hover(true);
	}

	public void redo() {
		LabelField currentLabel = allHistory.get(c.gridy++);
        currentLabel.setActive(false);
        currentLabel.hover(false);
	}

	public void enteredChange(Integer pos) {

		//System.out.println(String.format("Position : %d", pos));

		setCursor(handCursor);

		Plays plays = Game.getInstance().getPlays();
		List<Play> allPlays = plays.getPlays();
		CellContent[][] currentGrid = copyGrid(Game.getInstance().getGrid());

		if(pos <= plays.getCurrentMovement()) {
			for(int i=plays.getCurrentMovement(); i >= pos; i--) {
				LabelField label = allHistory.get(i);
				Play currentPlay = allPlays.get(i);

				for(Map.Entry<Point, CellContent> m : currentPlay.getModifiedOldCellContents().entrySet()) {
					currentGrid[m.getKey().y][m.getKey().x] = m.getValue();
				}

                label.hover(true);
			}
		}
		else {
			for(int i=plays.getCurrentMovement()+1; i < pos; i++) {
                // avoid out of bounds exception
                if(allPlays.size() <= i)
                    break;
                
				Play currentPlay = allPlays.get(i);

				for(Map.Entry<Point, CellContent> m : currentPlay.getModifiedNewCellContents().entrySet()) {
					currentGrid[m.getKey().y][m.getKey().x] = m.getValue();
				}
			}
		}

		gamePage.setPreviewGrid(currentGrid);
		gamePage.refresh();
	}

	public void exitedChange(Integer pos) {
		setCursor(defaultCursor);

		for(Integer i=c.gridy-1; i >= pos; i--) {
			LabelField label = allHistory.get(i);

			if(!label.isActive())
				label.hover(false);
		}

		gamePage.setPreviewGrid(null);
		gamePage.refresh();
	}

	public void clickChange(Integer pos) {
		Plays plays = Game.getInstance().getPlays();

		if(pos <= plays.getCurrentMovement()) {
			for(int i = plays.getCurrentMovement(); i >= pos; i--) {
				Game.getInstance().undo_move();
				c.gridy--;
				allHistory.get(i).setActive(true);
				allHistory.get(i).hover(true);
			}
		}
		else {
			for(int i=plays.getCurrentMovement()+1; i < pos; i++) {
				Game.getInstance().redo_move();
				c.gridy++;
			}
		}

		gamePage.refresh();
	}

	private CellContent[][] copyGrid(CellContent[][] grid) {
		if (grid == null)
			return null;
        
		CellContent[][] newGrid = new CellContent[grid.length][];

		for(int i=0; i < grid.length; i++) {
			newGrid[i] = grid[i].clone();
		}

		return newGrid;
	}
}

class LabelField extends JLabel {
    protected static Dimension size = new Dimension(0, 0);

    private Integer position;
	private boolean active = false;
	private boolean systemMessage = false;
	private GenericLabel timing, player, sentence;

    public static void setDimension(Dimension d) {
        LabelField.size = d;
    }
    
	public LabelField(boolean isSystem, String systemMessage, HistoryChatField historyMain, Integer pos) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        setSize(LabelField.size);
        setPreferredSize(LabelField.size);
        setMaximumSize(LabelField.size);
        setMinimumSize(LabelField.size);
		
        this.systemMessage = isSystem;

        // system message
		if(this.systemMessage) {
            setText("[System] ...");
            setForeground(GenericObjectStyle.getProp("chat.yellow", "color"));
        }
        // player move message
		else {
            PlayerEnum p = Game.getInstance().getPlayingPlayerEnum();
            Movement lastMove = Game.getInstance().getLastPlay();

            int pp = p==PlayerEnum.ATTACKER? 2 : p==PlayerEnum.DEFENDER? 1 : 0;

			timing = new GenericLabel("[" +  Time.format(Game.getInstance().getDuration()) + "]", 12);
			timing.setForeground(GenericObjectStyle.getProp("chat.timing", "color"));

			player = new GenericLabel(pp==1? "L'attaquant" : "Le D\u00e9fenseur", 12);
			player.setForeground(GenericObjectStyle.getProp("chat." + (pp==1? "red" : "blue"), "color"));

			String text = "a jou\u00e9 ";

			text += (lastMove != null)? getColName(lastMove.getFromC()) + getRowName(lastMove.getFromL()) : "??"; // from
			text += " en ";
			text += (lastMove != null)? getColName(lastMove.getToC()) + getRowName(lastMove.getToL()) : "??"; // to

			sentence = new GenericLabel(text, 12);
			sentence.setForeground(GenericObjectStyle.getProp("chat", "color"));

			add(timing);
			add(player);
			add(sentence);

            addMouseListener(new MouseAdapter() {
				/**
                 * Triggered when mouse's over
				 * Replace the current board's context
                 */
				@Override
				public void mouseExited(MouseEvent e) {
					historyMain.exitedChange(pos);
				}
				
				/**
				 * Triggered when mouse's hovering the chat
                 * Changes the board's context to hovered movement
				 */
				@Override
				public void mouseEntered(MouseEvent e) {
					historyMain.enteredChange(pos);
				}

				/**
				 * Triggered when player selected an older move
                 * Change board's context and confirm it
				 */
				@Override
				public void mouseClicked(MouseEvent e) {
					historyMain.clickChange(pos);
				}
			});
		}


		setPosition(pos);
	}

	public GenericLabel getTiming() {
		return timing;
	}

	public GenericLabel getPlayer() {
		return player;
	}

	public GenericLabel getSentence() {
		return sentence;
	}
	
	private String getColName(int c) {
        return Character.toString((char)(65 + c));
	}

	private String getRowName(int l) {
        return "" + (l+1);
	}

	public void setPosition(Integer num) {
		position = num;
	}
	
	public Integer getPosition() {
		return position;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean state) {
		active = state;
	}

	public void hover(boolean state) {
		if(!systemMessage) {
			String style = "chat" + (state? ".hover" : "");
			sentence.setForeground(GenericObjectStyle.getProp(style, "color"));
		}
	}
}