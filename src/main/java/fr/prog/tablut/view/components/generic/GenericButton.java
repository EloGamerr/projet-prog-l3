package fr.prog.tablut.view.components.generic;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.window.WindowName;

/**
 * A component that extends JButton, a basic button.
 * @see JButton
 */
public class GenericButton extends JButton implements GenericComponent {
	protected boolean hovering = false;
    protected boolean canHoverStyle = false;
    protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	protected ActionListener actionListenerHref = null;
	protected ActionListener actionListenerBase = null;

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

				if(!styleName.contains(":disabled"))
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
        setHref(href, new ButtonNavAdaptator(this, GenericObjectStyle.getGlobalWindow(), href));
    }

	public void setHref(WindowName href, ActionListener action) {
		this.href = href;
		setHrefAction(action);
	}

	public void setHrefAction(ActionListener actionHref) {
		if(actionListenerHref != null)
			removeActionListener(actionListenerHref);
		
		actionListenerHref = actionHref;

		addActionListener(actionListenerHref);
	}

	public void setAction(ActionListener action) {
		if(actionListenerBase != null) {
			removeActionListener(actionListenerBase);
		}

		actionListenerBase = action;
		
		addActionListener(actionListenerBase);
	}

	public void setStyle(String style) {
		if(GenericObjectStyle.getStyle().has(style)) {
            styleName = style;
		    canHoverStyle = GenericObjectStyle.getStyle().has(style + ":hover");
        }
	}

	public String getStyle() {
		return styleName;
	}

	public boolean isDisabled() {
		return getStyle().endsWith(":disabled");
	}
}
