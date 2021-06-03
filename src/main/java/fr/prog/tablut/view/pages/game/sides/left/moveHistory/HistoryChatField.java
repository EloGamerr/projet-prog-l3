package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.Plays;
import fr.prog.tablut.structures.Couple;

public class HistoryChatField extends JPanel {
    private Game game;
    private GridBagConstraints c;
    private List<labelField> allHistory = new ArrayList<labelField>();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public HistoryChatField(Game game, Dimension d) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        this.game = game;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        

        for(int i = 0; i < 5; i++) {
        	addAction(new Couple(new Couple(1, 1), new Couple(2, 2)),game.getPlays());
        }
    }

    public void addAction(Couple<Couple<Integer, Integer>, Couple<Integer, Integer>> action, Plays plays) {
        labelField newPlayerAction = new labelField(action, String.format("Action du joueur"));

        // GenericObjectStyle.getProp("chat", "color");         // -- couleur orange du texte
        // GenericObjectStyle.getProp("chat.timing", "color");  // -- couleur blanche des minutes [3:24]
        // GenericObjectStyle.getProp("chat.red", "color");     // -- couleur rouge de "Attaquant"
        // GenericObjectStyle.getProp("chat.blue", "color");    // -- couleur bleue de "Défenseur"
        // GenericObjectStyle.getProp("chat.yellow", "color");  // -- couleur jaune d'un message système

        newPlayerAction.addMouseListener(new MouseAdapter(){
			
			//Utilisé quand le curseur n'est plus sur le text
			//On va devoir remètre le plateau à son état normal
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Curseur parti");
				
			}
			
			@Override
			/**
			 * Utilisé quand le surseur survole le text
			 * On va vouloir changer le plateau de jeu pour montrer l'historique
			 */
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Curseur dessus");
			}
			/**
			 * Utilisé quand on clique sur le text
			 * On va vouloir charger un des coups et donc revenir en arrière
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Clik sur le bouton");
                /* for(Play play : plays.getPlays()) {
                    System.out.println(String.format("Pion bougé de %d,%d vers %d,%d", play.getMovement().fromC,play.getMovement().fromL, play.getMovement().toC,play.getMovement().toL));
                } */
				if(plays.getPlays() == null) {
					System.out.println("Aucun play trouvé ...");
				}
				else {
					System.out.println("Play trouvé");
				}
				
			}
		});
        
        newPlayerAction.setPosition(c.gridy);
        allHistory.add(newPlayerAction);
        this.add(newPlayerAction, this.c);
        c.gridy++;

    }

}

class labelField extends JLabel{
	
	private Game game;
    private GridBagConstraints c;
    private Couple<Couple<Integer, Integer>, Couple<Integer, Integer>> action;
    
    private Integer position;
    
	public labelField(Couple<Couple<Integer, Integer>, Couple<Integer, Integer>> action, String name) {
		this.action = action;
        this.setText(name);
	}
	
	public void setPosition(Integer num) {
		this.position = num;
	}
	
	public Integer getPosition() {
		return this.position;
	}
}