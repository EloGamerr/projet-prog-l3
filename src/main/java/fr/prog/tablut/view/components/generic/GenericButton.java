package fr.prog.tablut.view.components.generic;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.window.PageName;

/**
 * A component that extends JButton, a basic button.
 * @see JButton
 */
public class GenericButton extends JButton implements GenericComponent {
	protected boolean hovering = false;
    protected boolean canHoverStyle = false;
    protected final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    protected final Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	protected ActionListener actionListenerHref = null;
	protected ActionListener actionListenerBase = null;

    protected String styleName = "button";

    protected PageName href = null;
	
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
	 * @param text The text in the button
	 */
	public GenericButton(String text) {
		super(text);
		init();
    }

	/**
	 * Initializes the button : event handlers
	 */
	private void init() {
		// hover listener
        GenericButton me = this;

        setFont(new Font("Farro-Light", Font.PLAIN, 12));

		//TODO : move it in the controller
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
				hovering = true;

				if(!styleName.contains(":disabled"))
                	me.setCursor(me.handCursor);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hovering = false;
                me.setCursor(me.defaultCursor);
            }

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				hovering = false;
                if(!styleName.contains(":disabled"))
                    me.setCursor(me.handCursor);
			}
        });
	}

    /**
     * Returns the page name's href location of the button
     * @see PageName
     * @return The href location of the button
     */
	public PageName getHref() {
		return href;
	}

	/**
	 * Sets the href location (the page to go to in the app)
	 * when the user clicks on.
	 * @param href The page to go to
	 */
    public void setHref(PageName href) {
        setHref(href, new ButtonNavAdaptator(this, GenericObjectStyle.getGlobalWindow(), href));
    }

    /**
     * Sets the button's href location and sets its action on click event
     * @param href The href location
     * @param action The action to trigger when the button is clicked
     */
	public void setHref(PageName href, ActionListener action) {
		this.href = href;
		setHrefAction(action);
	}

    /**
     * Sets the action listener of the button
     * @param actionHref the action to trigger when the user clicks on the button
     */
	public void setHrefAction(ActionListener actionHref) {
		if(actionListenerHref != null)
			removeActionListener(actionListenerHref);
		
		actionListenerHref = actionHref;

		addActionListener(actionListenerHref);
	}

    /**
     * Sets the second action to perform when the button is clicked
     * @param action The action to perform when the button is clicked
     */
	public void setAction(ActionListener action) {
		if(actionListenerBase != null) {
			removeActionListener(actionListenerBase);
		}

		actionListenerBase = action;
		
		addActionListener(actionListenerBase);
	}

    /**
     * Sets the button's style
     * @param style The style's name to set
     */
	public void setStyle(String style) {
		if(GenericObjectStyle.getStyle().has(style)) {
            styleName = style;
		    canHoverStyle = GenericObjectStyle.getStyle().has(style + ":hover");
        }
	}

    /**
     * Returns the button's style
     * @return The button's style
     */
	public String getStyle() {
		return styleName;
	}

    /**
     * Returns either the button is disabled or not
     * @return Either the button is disabled or not
     */
	public boolean isDisabled() {
		return getStyle().endsWith(":disabled");
	}
}
