package fr.prog.tablut.view.components.generic;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.InputAdaptator;
import fr.prog.tablut.model.window.ComponentStyle;

/**
 * A component that extends a JTextField, a basic text input
 * @see JTextField
 */
public class GenericInput extends JTextField implements GenericComponent {
    protected String styleName = "input";
    protected int borderRadius = 10;
    protected Rectangle2D textBounds;
    protected float labelX = 0;
    protected float labelY = 0;

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

        FontRenderContext frc = new FontRenderContext(null, false, false);
        textBounds = getFont().getStringBounds(getText(), frc);

        labelX = (float) width / 2;
        labelY = (float) getFont().getSize() + (float) (height - getFont().getSize()) / 2 - 1;
    }

    /**
     * Defines the style of the input.
     * <p>Looks if the style is existing in the style's database. If not, then it doesn't load it.</p>
     * @param style The style to apply
     */
    public void setStyle(String style) {
        if(GenericObjectStyle.getStyle().has(style))
            this.styleName = style;
    }

    /**
     * Repaints the input
     */
    public void paintComponent(Graphics g) {
        //TODO : issue - the real text is drawn behind
        //TODO : iisue - the carret isn't drawn
        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliased lines and text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        ComponentStyle style = GenericObjectStyle.getStyle().get(styleName);

        // fill
        g2d.setColor(style.get("background"));
        g2d.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);
        // stroke
        g2d.setColor(style.get("borderColor"));
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);
        // text
        g2d.setColor(style.get("color"));
        g2d.setFont(new Font("Farro", Font.PLAIN, 12));
        g2d.drawString(this.getText(), labelX - (float)getFont().getStringBounds(getText(), new FontRenderContext(null, false, false)).getWidth()/2, labelY);

        revalidate();
    }

    public String getStyle() {
		return styleName;
	}

	public boolean isDisabled() {
		return getStyle().endsWith(":disabled");
	}
}
