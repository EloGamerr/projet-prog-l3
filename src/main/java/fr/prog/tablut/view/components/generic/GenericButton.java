package fr.prog.tablut.view.components.generic;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.window.WindowName;

/**
 * A component that extends JButton, a basic button.
 * @see JButton
 */
public class GenericButton extends JButton {
	protected boolean hovering = false;
    protected boolean canHoverStyle = false;
    protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    protected String styleName = "button";

    protected WindowName href = null;
	
	/**
	 * Default constructor.
	 * <p>Creates a generic button of type JButton.</p>
	 * <p>Handles these events : when the mouse enters or leaves the button,
	 * or clicks on, to set the cursor icon and the hovering boolean state.</p>
	 */
	public GenericButton() {
        init();
    }

	/**
	 * Creates a generic button of type JButton with given text inside.
	 * <p>Handles these events : when the mouse enters or leaves the button,
	 * or clicks on, to set the cursor icon and the hovering boolean state.</p>
	 * @param label The text in the button
	 */
	public GenericButton(String label) {
		super(label);
		init();
    }

	/**
	 * Initializes the button : event handlers
	 */
	private void init() {
		// hover listener
        GenericButton me = this;

		//TODO : move it in the controller
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
				hovering = true;
                me.setCursor(me.handCursor);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hovering = false;
                me.setCursor(me.defaultCursor);
            }

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				hovering = false;
                me.setCursor(me.handCursor);
			}
        });
	}

	/**
	 * Sets the href location (the page to go to in the app)
	 * when the user clicks on.
	 * @param href The page to go to
	 */
    public void setHref(WindowName href) {
        this.href = href;
        addActionListener(new ButtonNavAdaptator(GenericObjectStyle.getGlobalWindow(), href));
    }
}
