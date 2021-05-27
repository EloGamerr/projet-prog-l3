package fr.prog.tablut.view.load;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ButtonToggleAdaptator;
import fr.prog.tablut.view.generic.GenericButton;

public class ButtonTogglePannel extends JPanel{
	
	public GenericButton button_selected = null;
	public int index_selected = 0;
	
	private final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private static final String savePrefix = "save-";
	private static final String saveSuffix = ".sv";
	
	public ButtonTogglePannel() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		GenericButton button;
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.insets = new Insets(0, 0, 0, 0);
		int i = 1;
		String savePath = Paths.get(savesPath, savePrefix + i + saveSuffix).toString();
		File f = new File(savePath);
		while (f.isFile()) {
			c.gridy = i;
			savePath = Paths.get(savesPath, savePrefix + i + saveSuffix).toString();
		    f = new File(savePath);
		    
			button = new GenericButton("Save n°" + i,new Dimension(300,30));
			button.addActionListener(new ButtonToggleAdaptator(button, i, this));
			this.add(button,c);
			i++;
		}
	}
	
	public void selected(GenericButton button, int index) {
		if(button_selected != null) {
			button_selected.setBackground(new GenericButton("").getBackground());
		}
		
		button_selected = button;
		button_selected.setBackground(Color.green);
		index_selected = index;
	}

}
