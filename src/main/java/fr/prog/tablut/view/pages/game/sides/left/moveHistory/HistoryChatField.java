package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.Plays;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;

public class HistoryChatField extends JPanel {
    private GridBagConstraints c;
    private List<labelField> allHistory = new ArrayList<labelField>();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public HistoryChatField(Dimension d) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        

        /* for(int i = 0; i < 5; i++) {
        	addAction();
        } */
    }

    public void addAction() {
        labelField newPlayerAction = new labelField(String.format("Action du joueur"), this, c.gridy);

        // GenericObjectStyle.getProp("chat", "color");         // -- couleur orange du texte
        // GenericObjectStyle.getProp("chat.timing", "color");  // -- couleur blanche des minutes [3:24]
        // GenericObjectStyle.getProp("chat.red", "color");     // -- couleur rouge de "Attaquant"
        // GenericObjectStyle.getProp("chat.blue", "color");    // -- couleur bleue de "Défenseur"
        // GenericObjectStyle.getProp("chat.yellow", "color");  // -- couleur jaune d'un message système

        allHistory.add(newPlayerAction);
        this.add(newPlayerAction, this.c);
        c.gridy++;

    }


	public void updateContent() {
		System.out.println("Update content");
	}

	public void enteredChange(Integer pos) {
		for(Integer i = pos; i < allHistory.size(); i++) {
			labelField label = allHistory.get(i);
			label.setForeground(Color.BLUE);
		}
	}

	public void exitedChange(Integer pos) {
		for(Integer i = pos; i < allHistory.size(); i++) {
			labelField label = allHistory.get(i);
			label.setForeground(GenericObjectStyle.getProp("chat", "color"));
		}
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
				System.out.println("Curseur parti");
				historyMain.exitedChange(pos);
			}
			
			@Override
			/**
			 * Utilisé quand le surseur survole le text
			 * On va vouloir changer le plateau de jeu pour montrer l'historique
			 */
			public void mouseEntered(MouseEvent e) {
				System.out.println("Curseur dessus");
				historyMain.enteredChange(pos);

			}
			/**
			 * Utilisé quand on clique sur le text
			 * On va vouloir charger un des coups et donc revenir en arrière
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clik sur le bouton");
				Plays test = Game.getInstance().getPlays();

				if(test == null){
					System.out.println("Pas de donnée");
				}

				else {
					System.out.println("Des données !");
				}
				
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