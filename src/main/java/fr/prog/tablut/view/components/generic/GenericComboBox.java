package fr.prog.tablut.view.components.generic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;

// src: https://www.codejava.net/java-se/swing/create-custom-gui-for-jcombobox

/**
 * A component that extends JComboBox, a drop-down list.
 * @see JComboBox
 */
public class GenericComboBox<E> extends JComboBox<E> {
    protected int borderRadius = 10;
    protected boolean hovering = false;
    protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	/**
	 * Creates a drop-down list of elements of type <E>.
	 * <p>Sets the size and preselect the first item.</p>
	 * @param data The data (each options)
	 */
	public GenericComboBox(E[] data) {
		super(data);

		setPreferredSize(new Dimension(200, 30));
		setSelectedIndex(0);
        setEditable(true);

        setUI(GenericComboBoxUI.createUI(this));
        setRenderer(new GenericComboBoxRenderer<E>());
        setEditor(new GenericComboBoxEditor());

        GenericComboBox<E> me = this;
        
        // hover listener
		//TODO : move it in the controller
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
				hovering = true;
                me.setCursor(me.handCursor);
                revalidate();
                repaint();
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hovering = false;
                me.setCursor(me.defaultCursor);
                revalidate();
                repaint();
            }

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				hovering = false;
                me.setCursor(me.handCursor);
                revalidate();
                repaint();
			}
        });

        revalidate();
        repaint();
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        String currentStyle = "select.selected" + (hovering? ":hover" : "");

        Color background = GenericObjectStyle.getProp(currentStyle, "background");
        Color borderColor = GenericObjectStyle.getProp(currentStyle, "borderColor");

        // fill
        g2d.setColor(background);
        g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
        // stroke
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
    }
}

/**
 * The general comboBox UI renderer
 */
class GenericComboBoxUI extends BasicComboBoxUI {
    public static ComboBoxUI createUI(JComponent c) {
        return new GenericComboBoxUI(c);
    }

    protected GenericComboBoxUI(JComponent c) {
        super();

        JButton btn = new JButton();
        btn.setBackground(new Color(255, 0, 0));
        btn.setSize(40, 40);
        btn.setLocation(0, 0);
        c.setLayout(null);
        c.add(btn);
    }

    @Override
    protected ArrowButton createArrowButton() {
        return new ArrowButton();
    }
}


/**
 * The arrow component of the comboBox
 */
class ArrowButton extends GenericRoundedButton {
    private final int size = 30;

    protected ArrowButton() {
        super();
        setName("ComboBox.arrowButton");
        setImage("theme/select-arrow.png", size/4, size/4, size/2, size/2);

        setBorder(new EmptyBorder(0, 0, 0, 0));
        setOpaque(false);

        Dimension d = new Dimension(size, size);
        setSize(d);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);

        setBackground(new Color(0, 0, 0, 0));

        revalidate();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        // Don't draw the button or border
        setContentAreaFilled(false);
        setBorderPainted(false);

        Graphics2D g2d = (Graphics2D) g;

        if(image != null) {
            g2d.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        }
    }
}


/**
 * The Items list editor component
 */
class GenericComboBoxRenderer<E> extends JLabel implements ListCellRenderer<E> {
    protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    public GenericComboBoxRenderer() {
        setOpaque(true);
        setFont(new Font("Farro-Light", Font.PLAIN, 14));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        hover(false);
    }

    private void hover(boolean state) {
        String style = "select.item" + (state? ":hover" : "");
        setBackground(GenericObjectStyle.getProp(style, "background"));
        setForeground(GenericObjectStyle.getProp(style, "color"));

        setCursor(state? handCursor : defaultCursor);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        hover(isSelected);
        return this;
    }
}



/**
 * The selected item editor component
 */
class GenericComboBoxEditor extends BasicComboBoxEditor {
    private String text = "";

    private GenericPanel panel = new GenericPanel(null, null) {
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            FontRenderContext frc = new FontRenderContext(null, false, false);
            Rectangle2D textBounds = getFont().getStringBounds(text, frc);
    
            g2d.setColor(GenericObjectStyle.getProp("select.selected", "color"));
            g2d.setFont(new Font("Farro-Light", Font.PLAIN, 12));
            g2d.drawString(text, 15, (int)(getHeight() - 3 - textBounds.getHeight()/2));
        }
    };

    private Object selectedItem;
     
    public GenericComboBoxEditor() {
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
    }
     
    public Component getEditorComponent() {
        return this.panel;
    }
     
    public Object getItem() {
        return this.selectedItem;
    }
     
    public void setItem(Object item) {
        this.selectedItem = item;
        this.text = item.toString();
        this.panel.revalidate();
        this.panel.repaint();
    }
}