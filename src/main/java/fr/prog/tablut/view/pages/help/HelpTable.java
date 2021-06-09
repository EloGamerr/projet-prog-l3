package fr.prog.tablut.view.pages.help;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericPanel;

/**
 * Creates a help table, with multiple rows and columns.
 * <p>Extends GenericPanel</p>
 * @see GenericPanel
 */
public class HelpTable extends GenericPanel {
	public HelpTable() {
		super(new BorderLayout());

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
	private final int numCol;

	// headers <th>
	private final ArrayList<String> headers = new ArrayList<>();
	// rows <tr> containing cells <td>
	private final ArrayList<String[]> rows = new ArrayList<>();

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

		this.headers.addAll(Arrays.asList(headers).subList(0, min));
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
		GenericPanel table = new GenericPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;

		// add headers
		for(int i=0; i < numCol; i++) {
			String h = headers.get(i);
			gbc.gridx = i;
			// header and cell's properties (font size)
			int headerFontSize = 20;
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
				int tdFontSize = 15;
				GenericLabel tdLabel = new GenericLabel(td, tdFontSize);
				tdLabel.setForeground(GenericObjectStyle.getProp("table.td", "color"));
				tdLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
				table.add(tdLabel, gbc);
			}
		}

		tableContainer.add(table, BorderLayout.CENTER);
	}
}