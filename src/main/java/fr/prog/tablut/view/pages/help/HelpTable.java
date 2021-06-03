package fr.prog.tablut.view.pages.help;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;

/**
 * Creates a help table, with multiple rows and columns.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
public class HelpTable extends JPanel {
	public HelpTable() {
		setOpaque(false);
		setLayout(new BorderLayout());

		HTable table = new HTable(2);

		table.setHeader("TOUCHES RACCOURCIS", "DESCRIPTION");
		table.addLine("CRTL+S", "Sauvegarder");
		table.addLine("CRTL+Z", "Annuler le dernier coup");
		table.addLine("CRTL+Y", "Refaire le dernier coup");
		table.addLine("CRTL+N", "Recommencer la partie");
		table.addLine("Espace", "Pause (fonctionne uniquement en ordinateur contre ordinateur)");

		table.insertInto(this);
	}
}

/**
 * A help table
 */
class HTable {
	// number of columns
	private int numCol = 0;

	// headers <th>
	private ArrayList<String> headers = new ArrayList<>();
	// rows <tr> containing cells <td>
	private ArrayList<String[]> rows = new ArrayList<>();

	// header and cell's properties (font size)
	private int headerFontSize = 20;
	private int tdFontSize = 15;

		/**
		 * Creates an empty help table with preset number of column
		 * @param numCol Number of columns in the table
		 */
	public HTable(int numCol) {
		this.numCol = numCol;
	}

	/**
	 * Sets the headers row
	 * <p>If the number of fields overflows the preset number of columns in the table,
	 * these will be ignored</p>
	 * @param headers An array of string of headers
	 */
	public void setHeader(String... headers) {
		this.headers.clear();

		int min = Math.min(numCol, headers.length);

		for(int i=0; i < min; i++) {
			this.headers.add(headers[i]);
		}
	}

	/**
	 * Add a line in the table.
	 * <p>If the number of fields overflows the preset number of columns in the table,
	 * these will be ignored</p>
	 * @param row The line to add
	 */
	public void addLine(String... row) {
		if(numCol == row.length) {
			rows.add(row);
		}
		else {
			System.out.println("[Warning] HTable::addLine : Incorrect number of argument given, compared to table column's number.");
		}
	}

	/**
	 * Creates and inserts the table into the given JLabel (HelpTable) container
	 * @param tableContainer The container
	 */
	public void insertInto(HelpTable tableContainer) {
		HelpTable table = tableContainer;
		table.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;

		// add headers
		for(int i=0; i < numCol; i++) {
			String h = headers.get(i);
			gbc.gridx = i;
			GenericLabel hLabel = new GenericLabel(h, headerFontSize);
			hLabel.setForeground(GenericObjectStyle.getProp("table.th", "color"));
			hLabel.setBorder(new EmptyBorder(0, 50, 50, 50));
			table.add(hLabel, gbc);
		}

		// add lines
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

		//TODO : fix why the container has huge size overflowing the window
		// and then the table can't be seen at the top of it (BorderLayout.NORTH)
		//tableContainer.add(table, BorderLayout.CENTER);
	}
}