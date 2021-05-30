package fr.prog.tablut.view.pages.help;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;

public class HelpTable extends JPanel {
	public HelpTable() {
		setOpaque(false);
		setLayout(new BorderLayout());

		HTable table = new HTable(2);

		table.setHeader("TOUCHES RACCOURCIS", "DESCRIPTION");
		table.addLine("CRTL+S", "Sauvegarder");
		table.addLine("Espace", "Pause (fonctionne uniquement en ordinateur contre ordinateur)");
		table.addLine("CRTL+Z", "Annuler le dernier coup");
		table.addLine("CRTL+Y", "Refaire le dernier coup");
		table.addLine("CRTL+N", "Recommencer la partie");

		table.insertInto(this);
	}
}

class HTable {
	private int numCol = 0;

	private ArrayList<String> headers = new ArrayList<>();
	private ArrayList<String[]> rows = new ArrayList<>();

	private int headerFontSize = 20;
	private int tdFontSize = 15;

	public HTable(int numCol) {
		this.numCol = numCol;
	}

	public void setHeader(String... headers) {
		this.headers.clear();

		int min = Math.min(numCol, headers.length);

		for(int i=0; i < min; i++) {
			this.headers.add(headers[i]);
		}
	}

	public void addLine(String... row) {
		if(numCol == row.length) {
			rows.add(row);
		}
		else {
			System.out.println("[Warning] HTable::addLine : Incorrect number of argument given, compared to table column's number.");
		}
	}

	public void insertInto(HelpTable tableContainer) {
		HelpTable table = tableContainer;
		table.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;

		for(int i=0; i < numCol; i++) {
			String h = headers.get(i);
			gbc.gridx = i;
			GenericLabel hLabel = new GenericLabel(h, headerFontSize);
			hLabel.setForeground(GenericObjectStyle.getProp("table.th", "color"));
			hLabel.setBorder(new EmptyBorder(0, 50, 50, 50));
			table.add(hLabel, gbc);
		}

		for(int i=0; i < rows.size(); i++) {
			gbc.gridy = i+1;

			for(int j=0; j < numCol; j++) {
				gbc.gridx = j;
				String td = rows.get(i)[j];
				GenericLabel tdLabel = new GenericLabel(td, tdFontSize);
				tdLabel.setForeground(GenericObjectStyle.getProp("table.td", "color"));
				tdLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
				table.add(tdLabel, gbc);
			}
		}

		// TODO: fix why the container has huge size overflowing the window
		// and then the table can't be seen at the top of it (BorderLayout.NORTH)
		//tableContainer.add(table, BorderLayout.CENTER);
	}
}