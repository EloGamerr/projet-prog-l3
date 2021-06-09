package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import fr.prog.tablut.controller.game.listener.ChatMouseListener;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.utils.Time;

public class HistoryChatField extends GenericPanel {
	private final GamePage gamePage;
    private int labelHeight;
    private List<LabelAction> allHistory = new ArrayList<LabelAction>();
    private GridBagConstraints c;
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
    /**
     * Creates a chat's action manager. Privates, only used by its parent.
     * <p>All other components communicates with MoveHistoryPanel which will communicates with HistoryChatField.</p>
     * @see MoveHistoryPanel
     * @param gamePage
     */
	public HistoryChatField(GamePage gamePage) {
        super(new GridBagLayout());

        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTH;

		this.gamePage = gamePage;

        labelHeight = 20;
    }

    /**
     * Adds a player action in the chat
     */
    public void addAction() {
        // update the view's size
        LabelAction.setDimension(new Dimension(getWidth(), labelHeight));

        // new action's label in the chat
        LabelAction newPlayerAction = new LabelAction(false, null, c.gridy);

        newPlayerAction.addMouseListener(new ChatMouseListener(newPlayerAction, this, gamePage));

        // default text's color
		newPlayerAction.setForeground(GenericObjectStyle.getProp("chat", "color"));

        // erase next moves if these exist
		if(allHistory.size() >= c.gridy) {
			for(int i = allHistory.size() - 1; i >= c.gridy; i--) {
				if(allHistory.size() <= i)
					break;
				
				remove(allHistory.remove(i));
			}
		}

        // add action to the chat
        allHistory.add(newPlayerAction);

        add(newPlayerAction, c);
		
        // number of moves displayed
        c.gridy++;
    }

    /**
     * Returns the height of an action's label
     * @return The action's label height
     */
    public int getLabelHeight() {
        return labelHeight;
    }

    /**
     * Returns the history list's size
     * @return The history list's size
     */
    public int getHistoryLength() {
        return allHistory.size();
    }

    /**
     * Clears the history
     */
    public void clear() {
        removeAll();

        c.gridy = Game.getInstance().getMovementsNumber();

        allHistory.clear();
    }

    /**
     * Returns the number of moves in the history view
     * @return
     */
    public int getMovesNumber() {
        return c.gridy;
    }

    /**
     * Removes an action from the chat
     */
	public void undo() {
		remove(allHistory.remove(--c.gridy));
	}

    /**
     * Re-add a previously removed action in the chat
     */
	public void redo() {
		addAction();
	}

    /**
     * Sets the cursor's type when hovering the chat
     * @param cursorType
     */
	public void setCursorType(String cursorType) {
        if(cursorType == "hand") setCursor(handCursor);
        else setCursor(defaultCursor);
	}

    /**
     * Returns the position of a given label in the chat
     * @return The action label's position
     */
    public int getActionPosition(JLabel action) {
        return ((LabelAction)action).getPosition();
    }

    /**
     * Returns the move history list
     * @see LabelAction
     * @return The move history list
     */
    public List<LabelAction> getHistory() {
        return allHistory;
    }

    /**
     * Removes an action's label in the chat view and list
     * @param i The action's index
     */
    public void removeAction(int i) {
        if(i < allHistory.size()) {
            c.gridy--;
            remove(allHistory.remove(i));
        }
    }

    /**
     * Defines the hovering state's style of an action's label
     * @param i The action label's index
     * @param hover The hovering's state
     */
    public void setHoveringAction(int i, boolean hover) {
        allHistory.get(i).hover(hover);
    }
}

/**
 * A component used for a one-line action message
 */
class LabelAction extends JLabel {
    protected static Dimension size = new Dimension(0, 0);

    private int position; // self position in the chat history
	private boolean systemMessage = false;
	private GenericLabel timing, player, sentence;

    /**
     * Sets the dimension of all the LabelActions
     * @param d The dimension to set to the action's labels
     */
    public static void setDimension(Dimension d) {
        LabelAction.size = d;
    }
    
    /**
     * Creates a new action's label
     * @param isSystem Is the message a system message or a player move's indication
     * @param systemMessage If it's a system message, then this argument is the message to show
     * @param pos The position of the label in the chat
     */
	public LabelAction(boolean isSystem, String systemMessage, int pos) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        setSize(LabelAction.size);
        setPreferredSize(LabelAction.size);
        setMaximumSize(LabelAction.size);
        setMinimumSize(LabelAction.size);
		
        this.systemMessage = isSystem;

        // system message
		if(this.systemMessage) {
            setText("[System] ...");
            setForeground(GenericObjectStyle.getProp("chat.yellow", "color"));
        }
        // player move message
		else {
            PlayerEnum p = Game.getInstance().getPlayingPlayerEnum();
            Movement lastMove = Game.getInstance().getCurrentLastPlay();

            int pp = p==PlayerEnum.ATTACKER? 2 : p==PlayerEnum.DEFENDER? 1 : 0;

            // The date when the message has been created / sent
            // from the game's creation
			timing = new GenericLabel("[" +  Time.format(Game.getInstance().getDuration()) + "]", 12);
			timing.setForeground(GenericObjectStyle.getProp("chat.timing", "color"));

            // The player's type
			player = new GenericLabel(pp==1? "L'attaquant" : "Le D\u00e9fenseur", 12);
			player.setForeground(GenericObjectStyle.getProp("chat." + (pp==1? "red" : "blue"), "color"));

            // The core message : the move that's been done
			String text = "a jou\u00e9 ";

			text += (lastMove != null)? getColName(lastMove.getFromC()) + getRowName(lastMove.getFromL()) : "??"; // from
			text += " en ";
			text += (lastMove != null)? getColName(lastMove.getToC()) + getRowName(lastMove.getToL()) : "??"; // to

			sentence = new GenericLabel(text, 12);
			sentence.setForeground(GenericObjectStyle.getProp("chat", "color"));

			add(timing);
			add(player);
			add(sentence);
		}


		setPosition(pos);
	}

    /**
     * Returns the timing label
     * @return The timing label
     */
	public GenericLabel getTiming() {
		return timing;
	}

    /**
     * Returns the player's type label
     * @return The player's type label
     */
	public GenericLabel getPlayer() {
		return player;
	}

    /**
     * Returns the core message's label
     * @return The core message's label
     */
	public GenericLabel getSentence() {
		return sentence;
	}
	
    /**
     * Returns the column's letter [A-I] of the given index
     * @param c The column's index
     * @return The column's letter
     */
	private String getColName(int c) {
        return Character.toString((char)(65 + c));
	}

    /**
     * Returns the row's index of a given index (+1) as a string
     * @param l The row's index
     * @return The row's real index starting from 1, as a string
     */
	private String getRowName(int l) {
        return "" + (l+1);
	}

    /**
     * Sets the label's position in the chat
     * @param num
     */
	public void setPosition(int num) {
		position = num;
	}
	
    /**
     * Returns the label's position in the chat
     * @return The label's position in the chat
     */
	public int getPosition() {
		return position;
	}

    /**
     * Defines either the label is hovered or not. Changes its style.
     * @param state The hovering's state of the label
     */
	public void hover(boolean state) {
		if(!systemMessage) {
			String style = "chat" + (state? ".hover" : "");
			sentence.setForeground(GenericObjectStyle.getProp(style, "color"));
		}
	}
}