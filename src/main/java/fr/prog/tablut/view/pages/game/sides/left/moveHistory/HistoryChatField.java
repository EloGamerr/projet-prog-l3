package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private GridBagConstraints c;
    private List<labelField> allHistory = new ArrayList<labelField>();
	private final GamePage gamePage;
	private static HistoryChatField instance;
	private final MoveHistoryPanel moveHistoryPanel;
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
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
        labelField newPlayerAction = new labelField(Game.getInstance().getPlayingPlayerEnum(), Game.getInstance().getLastPlay(), this, c.gridy);

		newPlayerAction.setForeground(GenericObjectStyle.getProp("chat", "color"));

		if(allHistory.size() >= c.gridy) {
			for(Integer i = allHistory.size() - 1; i >= c.gridy; i--) {
				remove(allHistory.get(i));
				allHistory.remove(i);
			}
		}

        allHistory.add(newPlayerAction);

        add(newPlayerAction, c);
		
        c.gridy++;

		moveHistoryPanel.revalidate();
		moveHistoryPanel.repaint();
    }

	public void updateContent() {

	}

	public void undo() {
		System.out.println("Undo move");
		labelField currentLabel = allHistory.get(--c.gridy);
		currentLabel.setForeground(GenericObjectStyle.getProp("chat.yellow", "color"));
	}

	public void redo() {
		System.out.println("Redo move");
		labelField currentLabel = allHistory.get(c.gridy++);
		currentLabel.setForeground(GenericObjectStyle.getProp("chat", "color"));
	}

	public void enteredChange(Integer pos) {
		setCursor(handCursor);

		Plays plays = Game.getInstance().getPlays();
		List<Play> allPlays = plays.getPlays();
		CellContent[][] currentGrid = copyGrid(Game.getInstance().getGrid());

		if(pos <= plays.getCurrentMovement()) {
			for(int i = plays.getCurrentMovement() ; i >= pos ; i--) {
				labelField label = allHistory.get(i);
				Play currentPlay = allPlays.get(i);

				for(Map.Entry<Couple<Integer, Integer>, CellContent> m : currentPlay.getModifiedOldCellContents().entrySet()) {
					currentGrid[m.getKey().getFirst()][m.getKey().getSecond()] = m.getValue();
				}
				label.setForeground(GenericObjectStyle.getProp("chat.yellow", "color"));
			}
		}
		else {
			for(int i = plays.getCurrentMovement()+1 ; i < pos ; i++) {
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

		for(Integer i = c.gridy - 1; i >= pos; i--) {
			labelField label = allHistory.get(i);

			if(!label.isActive())
				label.hover(false);
		}

		gamePage.setGrid(null);
		gamePage.refresh();
	}

	public void clickChange(Integer pos) {
		Plays plays = Game.getInstance().getPlays();

		if(pos <= plays.getCurrentMovement()) {
			for(int i = plays.getCurrentMovement() ; i >= pos ; i--) {
				Game.getInstance().undo_move();
				c.gridy --;
				allHistory.get(i).setActive(true);
				allHistory.get(i).hover(true);
			}
		}
		else {
			for(int i = plays.getCurrentMovement()+1 ; i < pos; i++) {
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
		for (int i = 0; i < grid.length; i++) {
			newGrid[i] = grid[i].clone();
		}
		return newGrid;
	}

	private void printBoard(CellContent[][] grid) {
		for (CellContent[] linCellContents : grid) {
			System.out.print("|");
			for (CellContent cellContent : linCellContents) {
				switch (cellContent) {
					case EMPTY:
						System.out.print("   |");
						break;
					case ATTACK_TOWER: 
						System.out.print(" N |");
						break;
					case DEFENSE_TOWER:
						System.out.print(" B |");
						break;
					case KING:
						System.out.print(" K |");
						break;
					case GATE:
						System.out.print(" X |");
						break;
					case KINGPLACE:
						System.out.print(" P |");
						break;
					default:
						break;
				}
			}
			System.out.println("");
		}
		System.out.flush();
	}
}

class labelField extends JLabel {
    
    private Integer position;
	private boolean active = false;
	private boolean systemMessage = false;
	private GenericLabel timing, player, sentence;
    
	public labelField(PlayerEnum p, Movement movement, HistoryChatField historyMain, Integer pos) {
		int pp = p==PlayerEnum.ATTACKER? 1 : p==PlayerEnum.DEFENDER? 2 : 0;
		
		// player move message
		if(pp > 0) {
			timing = new GenericLabel("[" + formatTime(Game.getInstance().getDuration()) + "]", 12);
			timing.setForeground(GenericObjectStyle.getProp("chat.timing", "color"));

			player = new GenericLabel(pp==1? "L'attaquant" : "Le D\u00e9fenseur", 12);
			player.setForeground(GenericObjectStyle.getProp("chat." + (pp==1? "red" : "blue"), "color"));

			String text = " a joué ";
			text += getColName(movement.getFromC()) + getRowName(movement.getFromL()); // from
			text += " en ";
			text += getColName(movement.getToC()) + getRowName(movement.getToL()); // to

			sentence = new GenericLabel(text, 12);
			sentence.setForeground(GenericObjectStyle.getProp("chat", "color"));

			add(timing);
			add(player);
			add(sentence);
		}

		// system message
		else {
			setText("[System] ...");
			systemMessage = true;
		}


		setPosition(pos);

		if(!systemMessage) {
			addMouseListener(new MouseAdapter() {
				
				//Utilisé quand le curseur n'est plus sur le text
				//On va devoir remètre le plateau à son état normal
				@Override
				public void mouseExited(MouseEvent e) {
					historyMain.exitedChange(pos);
				}
				
				@Override
				/**
				* Utilisé quand le surseur survole le text
				* On va vouloir changer le plateau de jeu pour montrer l'historique
				*/
				public void mouseEntered(MouseEvent e) {
					historyMain.enteredChange(pos);
				}
				/**
				* Utilisé quand on clique sur le text
				* On va vouloir charger un des coups et donc revenir en arrière
				*/
				@Override
				public void mouseClicked(MouseEvent e) {
					historyMain.clickChange(pos);
					
				}
			});
		}
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
		return "00:00";
	}
	
	private String getColName(int c) {
		switch(c) {
			case 0:
				return "A";
			case 1:
				return "B";
			case 2:
				return "C";
			case 3:
				return "D";
			case 4:
				return "E";
			case 5:
				return "F";
			case 6:
				return "G";
			case 7:
				return "H";
			case 8:
				return "I";
			default:
				return null;
		}
	}

	private String getRowName(int l) {
		switch(l) {
			case 0:
				return "1";
			case 1:
				return "2";
			case 2:
				return "3";
			case 3:
				return "4";
			case 4:
				return "5";
			case 5:
				return "6";
			case 6:
				return "7";
			case 7:
				return "8";
			case 8:
				return "9";
			default:
				return null;
		}
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