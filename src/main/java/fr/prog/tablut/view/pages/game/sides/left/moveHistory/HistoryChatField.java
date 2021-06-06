package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.Color;
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
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.Plays;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.pages.game.GamePage;

public class HistoryChatField extends JPanel {
    private GridBagConstraints c;
	CellContent[][] savedGrid;
	CellContent[][] currentWorkingGrid;
    private List<labelField> allHistory = new ArrayList<labelField>();
	private final GamePage gamePage;
	private static HistoryChatField instance;
	private final MoveHistoryPanel moveHistoryPanel;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public HistoryChatField(Dimension d, GamePage gamePage, MoveHistoryPanel moveHistoryPanel) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
		this.gamePage = gamePage;
		this.moveHistoryPanel = moveHistoryPanel;

        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        

        /* for(int i = 0; i < 5; i++) {
        	addAction();
        } */
    }

	public static HistoryChatField getInstance() {
		return instance;
	}

	public static void setInstance(HistoryChatField instanceOf) {
		instance = instanceOf;
	}

    public void addAction() {
		this.savedGrid = getCurrentBoard();

		System.out.println("Saved grid :");

		this.printBoard(this.savedGrid);

        labelField newPlayerAction = new labelField(String.format("Action du joueur"), this, c.gridy);

		newPlayerAction.setForeground(GenericObjectStyle.getProp("chat", "color"));

        // GenericObjectStyle.getProp("chat", "color");         // -- couleur orange du texte
        // GenericObjectStyle.getProp("chat.timing", "color");  // -- couleur blanche des minutes [3:24]
        // GenericObjectStyle.getProp("chat.red", "color");     // -- couleur rouge de "Attaquant"
        // GenericObjectStyle.getProp("chat.blue", "color");    // -- couleur bleue de "Défenseur"
        // GenericObjectStyle.getProp("chat.yellow", "color");  // -- couleur jaune d'un message système

        allHistory.add(newPlayerAction);
        this.add(newPlayerAction, this.c);
        c.gridy++;
		//gamePage.repaint();

		this.moveHistoryPanel.revalidate();
		this.moveHistoryPanel.repaint();

    }


	public void updateContent() {
	}


	public void enteredChange(Integer pos) {

		List<Play> allPlays = Game.getInstance().getPlays().movements();
		CellContent[][] currentGrid = getCurrentBoard();
		for(Integer i = allHistory.size() - 1; i >= pos; i--) {
			labelField label = allHistory.get(i);
			Play currentPlay = allPlays.get(i);

			for(Map.Entry<Couple<Integer, Integer>, CellContent> m : currentPlay.getModifiedOldCellContents().entrySet()) {
				currentGrid[m.getKey().getFirst()][m.getKey().getSecond()] = m.getValue();
			}
			label.setForeground(Color.BLUE);
		}

		Game.getInstance().setGrid(currentGrid);
		this.gamePage.revalidate();
		this.gamePage.repaint();
	}

	public void exitedChange(Integer pos) {
		for(Integer i = allHistory.size() - 1; i >= pos; i--) {
			labelField label = allHistory.get(i);
			label.setForeground(GenericObjectStyle.getProp("chat", "color"));
		}
		System.out.println("Grid envoyé à la game : ");
		this.printBoard(savedGrid);
		Game.getInstance().setGrid(savedGrid);
		this.gamePage.revalidate();
		this.gamePage.repaint();
	}

	public void clickChange(Integer pos) {
		for(Integer i = pos; i < allHistory.size(); i++) {
			this.remove(allHistory.get(i));
			allHistory.remove(i);
		}

		Game.getInstance().setGrid(currentWorkingGrid);
		this.gamePage.revalidate();
		this.gamePage.repaint();

	}

	private CellContent[][] getCurrentBoard() {
		return Game.getInstance().getGrid();
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

class labelField extends JLabel{
	
    private GridBagConstraints c;
	private CellContent[][] historyCell;
    
    private Integer position;
    
	public labelField(String name, HistoryChatField historyMain, Integer pos) {
        this.setText(name);
		this.setPosition(pos);
		this.addMouseListener(new MouseAdapter(){
			
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
	
	public void setPosition(Integer num) {
		this.position = num;
	}
	
	public Integer getPosition() {
		return this.position;
	}
}