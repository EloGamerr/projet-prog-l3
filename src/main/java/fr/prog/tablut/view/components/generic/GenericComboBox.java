package fr.prog.tablut.view.components.generic;

import java.awt.Dimension;

import javax.swing.JComboBox;

/**
 * A component that extends JComboBox, a drop-down list.
 * @see JComboBox
 */
public class GenericComboBox<E> extends JComboBox<E> {
	/**
	 * Creates a drop-down list of elements of type <E>.
	 * <p>Sets the size and preselect the first item.</p>
	 * @param data The data (each options)
	 */
	public GenericComboBox(E[] data) {
		super(data);
		setSelectedIndex(0);
		setPreferredSize(new Dimension(200, 30));
	}
}
