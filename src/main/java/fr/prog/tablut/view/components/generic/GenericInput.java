package fr.prog.tablut.view.components.generic;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.InputAdaptator;
import fr.prog.tablut.model.window.ComponentStyle;

/**
 * A component that extends a JTextField, a basic text input
 * @see JTextField
 */
@SuppressWarnings("deprecation")
public class GenericInput extends JTextField implements GenericComponent {
    protected String styleName;
    protected final int borderRadius = 10;
    protected Rectangle2D textBounds;
    protected float labelX = 0;
    protected float labelY = 0;
    protected boolean hovering = false;
    protected boolean canHoverStyle = false;
    protected boolean canFocusStyle = false;
    protected boolean canDisableStyle = false;
    protected boolean isDisabled = false;
    private final FontRenderContext frc = new FontRenderContext(null, false, false);
    protected final Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);
    protected final Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    /**
     * Default constructor.
     * <p>Creates a text input of type JTextField</p>
     * <p>Sets its style (size, background, color, ...).</p>
     * <p>Sets an action listener to update its draw depending to its value.</p>
     */
    public GenericInput() {
        super();
        init();
    }

    /**
     * Creates a text input of type JTextField, putting its value inside.
     * <p>Sets its style (size, background, color, ...).</p>
     * <p>Sets an action listener to update its draw depending to its value.</p>
     */
    public GenericInput(String value) {
        super();
		setText(value);
        init();
    }

    /**
     * Initializes the input (style, event handler)
     */
    private void init() {
        int width = 200;
        int height = 30;

        setPreferredSize(new Dimension(width, height));
        setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setForeground(GenericObjectStyle.getProp(styleName, "color"));

        addActionListener(new InputAdaptator(this));

        textBounds = getFont().getStringBounds(getText(), frc);

        labelX = (float) width / 2;
        labelY = (float) getFont().getSize() + (float) (height - getFont().getSize()) / 2 - 1;

        setStyle("input");

        // hover listener
		//TODO : move it in the controller
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
				hovering = true;
                revalidate();
                repaint();
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hovering = false;
                revalidate();
                repaint();
            }

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				hovering = false;
                revalidate();
                repaint();
			}
        });

        addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent e) {
                revalidate();
                repaint();
            }
      
            public void focusLost(java.awt.event.FocusEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Add a key listener for key press action
     * @param action The controller's action to set
     */
    public void onKeyPress(KeyListener action) {
        addKeyListener(action);
    }

    /**
     * Defines the style of the input.
     * <p>Looks if the style is existing in the style's database. If not, then it doesn't load it.</p>
     * @param style The style to apply
     */
    public void setStyle(String style) {
        if(GenericObjectStyle.getStyle().has(style)) {
            this.styleName = style;
            canHoverStyle = GenericObjectStyle.getStyle().has(style + ":hover");
            canFocusStyle = GenericObjectStyle.getStyle().has(style + ":focus");
            canDisableStyle = GenericObjectStyle.getStyle().has(styleName + ":disabled");
        }
    }

    /**
     * Enables the input
     */
    @Override
    public void enable() {
        if(isDisabled) {
            isDisabled = false;
            canHoverStyle = GenericObjectStyle.getStyle().has(styleName + ":hover");
            setCursor(this.textCursor);
        }
    }

    /**
     * Disables the input
     */
    @Override
    public void disable() {
        if(!isDisabled) {
            isDisabled = true;
            canHoverStyle = false;
            setCursor(this.defaultCursor);
        }
    }

    /**
     * Repaints the input
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliased lines and text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        String sstyle = styleName;
        
        if      (isDisabled && canDisableStyle) sstyle += ":disabled";
        else if (hasFocus() && canFocusStyle)   sstyle += ":focus";
        else if (hovering && canHoverStyle)     sstyle += ":hover";
        
        ComponentStyle style = GenericObjectStyle.getStyle().get(sstyle);

        final float xLabel = labelX - (float)getFont().getStringBounds(getText(), frc).getWidth()/2;

        // fill
        g2d.setColor(style.get("background"));
        g2d.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);
        
        // stroke + caret
        g2d.setColor(style.get("borderColor"));
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);

        if(hasFocus()) {
            final String caretSubstringPos = getText().substring(0, getCaretPosition());
            final Rectangle2D subTextBounds = getFont().getStringBounds(caretSubstringPos, frc);

            final double caretPosition = xLabel + subTextBounds.getWidth() + ((getCaretPosition() == getText().length())? 2.0 : 0.0);
            final double caretHeight = (60.0 * (double)getHeight() / 100.0);

            Shape l = new Line2D.Double(caretPosition, getHeight()/2 - caretHeight/2, caretPosition, getHeight()/2 + caretHeight/2);
            g2d.draw(l);
        }
        
            // text
        g2d.setColor(style.get("color"));
        g2d.setFont(new Font("Farro-Regular", Font.PLAIN, 12));
        g2d.drawString(this.getText(), xLabel, labelY);

    }

    /**
     * Returns the input's style
     */
    public String getStyle() {
		return styleName;
	}

    /**
     * Returns either the input is disabled or not
     * @return Either the input is disabled or not
     */
	public boolean isDisabled() {
		return getStyle().endsWith(":disabled");
	}
}
