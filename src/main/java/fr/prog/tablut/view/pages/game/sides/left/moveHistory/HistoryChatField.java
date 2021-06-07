package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.Plays;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.pages.game.GamePage;

public class HistoryChatField extends JPanel {
	private static HistoryChatField instance;

	private final GamePage gamePage;
	private final MoveHistoryPanel moveHistoryPanel;
    private GridBagConstraints c;
    private List<LabelField> allHistory = new ArrayList<LabelField>();
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
	public HistoryChatField(Dimension d, GamePage gamePage, MoveHistoryPanel moveHistoryPanel) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

		this.gamePage = gamePage;
		this.moveHistoryPanel = moveHistoryPanel;

        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }

	public static HistoryChatField getInstance() {
		return instance;
	}

	public static void setInstance(HistoryChatField instanceOf) {
		instance = instanceOf;
	}

    public void addAction() {
        // update the view's size
        LabelField.setDimension(new Dimension(getWidth(), 20));

        // new action's label in the chat
        LabelField newPlayerAction = new LabelField(false, null, this, c.gridy);

        // default text's color
		newPlayerAction.setForeground(GenericObjectStyle.getProp("chat", "color"));

        // erase next moves if these exist
        // TODO: issue
		if(allHistory.size() >= c.gridy) {
			for(Integer i = allHistory.size() - 1; i >= c.gridy; i--) {
				remove(allHistory.get(i));
				allHistory.remove(i);
			}
		}

        // add action to the chat
        allHistory.add(newPlayerAction);

        add(newPlayerAction, c);
		
        // number of moves displayed
        c.gridy++;

        // refresh the view
		moveHistoryPanel.revalidate();
		moveHistoryPanel.repaint();
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
		setCursor(handCursor);

		Plays plays = Game.getInstance().getPlays();
		List<Play> allPlays = plays.getPlays();
		CellContent[][] currentGrid = copyGrid(Game.getInstance().getGrid());

		if(pos <= plays.getCurrentMovement()) {
			for(int i=plays.getCurrentMovement(); i >= pos; i--) {
				LabelField label = allHistory.get(i);
				Play currentPlay = allPlays.get(i);

				for(Map.Entry<Couple<Integer, Integer>, CellContent> m : currentPlay.getModifiedOldCellContents().entrySet()) {
					currentGrid[m.getKey().getFirst()][m.getKey().getSecond()] = m.getValue();
				}

                label.hover(true);
			}
		}
		else {
			for(int i=plays.getCurrentMovement()+1; i < pos; i++) {
				Play currentPlay = allPlays.get(i);

				for(Map.Entry<Couple<Integer, Integer>, CellContent> m : currentPlay.getModifiedNewCellContents().entrySet()) {
					currentGrid[m.getKey().getFirst()][m.getKey().getSecond()] = m.getValue();
				}
			}
		}

		gamePage.setGrid(currentGrid);

		gamePage.refresh();
	}

	public void exitedChange(Integer pos) {
		setCursor(defaultCursor);

		for(Integer i=c.gridy-1; i >= pos; i--) {
			LabelField label = allHistory.get(i);

			if(!label.isActive())
				label.hover(false);
		}

		gamePage.setGrid(null);
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

    public void clear() {
        removeAll();
        c.gridy = 0;
    }

    public int getMovesNumber() {
        return c.gridy;
    }

    private void printBoard(CellContent[][] grid) {
		for(CellContent[] linCellContents : grid) {
			System.out.print("|");
			for(CellContent cellContent : linCellContents) {
				switch(cellContent) {
					case EMPTY: System.out.print("   |"); break;
					case ATTACK_TOWER: System.out.print(" N |"); break;
					case DEFENSE_TOWER: System.out.print(" B |"); break;
					case KING: System.out.print(" K |"); break;
					case GATE: System.out.print(" X |"); break;
					case KINGPLACE: System.out.print(" P |"); break;
					default: break;
				}
			}
			System.out.println("");
		}
		System.out.flush();
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

			timing = new GenericLabel("[" + formatTime(Game.getInstance().getDuration()) + "]", 12);
			timing.setForeground(GenericObjectStyle.getProp("chat.timing", "color"));

			player = new GenericLabel(pp==1? "L'attaquant" : "Le D\u00e9fenseur", 12);
			player.setForeground(GenericObjectStyle.getProp("chat." + (pp==1? "red" : "blue"), "color"));

			String text = " a jou\u00e9 ";
			text += getColName(lastMove.getFromC()) + getRowName(lastMove.getFromL()); // from
			text += " en ";
			text += getColName(lastMove.getToC()) + getRowName(lastMove.getToL()); // to

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

	private String formatTime(int time) {
        int ms = time % 1000;
        time = (time - ms) / 1000;
        int secs = time % 60;
        time = (time - secs) / 60;
        int mins = time % 60;
        int hrs = (time - mins) / 60;

        String h = (hrs > 9? "":"0") + hrs,
            m = (mins > 9? "":"0") + mins,
            s = (secs > 9? "":"0") + secs;

        return ((hrs == 0)? "" : h + ":") + m + ":" + s;
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
			String style = "chat" + (state? ".yellow" : "");
			sentence.setForeground(GenericObjectStyle.getProp(style, "color"));
		}
	}
}