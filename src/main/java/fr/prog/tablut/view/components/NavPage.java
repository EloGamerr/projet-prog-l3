package fr.prog.tablut.view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;

/**
 * A component extending JPanel that creates a page with header and footer.
 * <p>The header has a title and can have a description.</p>
 * <p>The footer is a BottomButtonPanel with one or two buttons.</p>
 * <p>The content of the page is centered.</p>
 * @see JPanel
 * @see BottomButtonPanel
 * @see Title
 * @see GenericLabel
 */
public class NavPage extends JPanel {
    protected static Dimension maxDimension;

    /**
     * Static method that stores the dimension that the instances of NavPage should take.
     * <p>Normally only called by GlobalWindow once, at initialization or when resizing the window.</p>
     * @param d The dimension of NavPage instances
     */
    public static void setDimension(Dimension d) {
        maxDimension = d;
    }

    /**
     * Default constructor.
     * <p>Creates a page only containing a BottomButtonPanel.</p>
     * @see BottomButtonPanel
     * @param bottomPanel The BottomButtonPanel object to set to the page
     */
    public NavPage(BottomButtonPanel bottomPanel) {
        this(null, null, bottomPanel);
    }

    /**
     * Creates a page only containing a title and a BottomButtonPanel.
     * @see Title
     * @see BottomButtonPanel
     * @param title The title of the page
     * @param bottomPanel The BottomButtonPanel of the page
     */
    public NavPage(String title, BottomButtonPanel bottomPanel) {
        this(title, null, bottomPanel);
    }

    /**
     * Creates a page containing a title, a description, and a bottomPanel.
     * @see TItle
     * @see GenericLabel
     * @see BottomButtonPanel
     * @param title The title
     * @param description THe description
     * @param bottomPanel The BottomButtonPanel
     */
    public NavPage(String title, String description, BottomButtonPanel bottomPanel) {
        setLayout(new BorderLayout());
        setOpaque(false);

        if(title != null) {
            Title Ttitle = new Title(title.toUpperCase(), 70);

            JPanel gb = new JPanel(new GridBagLayout());
            gb.setBorder(new EmptyBorder(50, 0, 0, 0));
            gb.setOpaque(false);

            Dimension gbSize = new Dimension((int)NavPage.maxDimension.getWidth(), 170);
            gb.setPreferredSize(gbSize);
            gb.setMaximumSize(gbSize);
            gb.setMinimumSize(gbSize);

            // title
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = 1;

            gb.add(Ttitle, gbc);

            // description
            if(description != null) {
                GenericLabel desc = new GenericLabel(description, 14);
                desc.setForeground(GenericObjectStyle.getProp("description", "color"));
                gbc.gridy = 2;
                gb.add(desc, gbc);
            }

            add(gb, BorderLayout.NORTH);
        }

        // bottom button panel
		add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the content of the page.
     * <p>The content is centered.</p>
     * @param content The content to put in the page
     */
    public void setContent(JPanel content) {
        add(content, BorderLayout.CENTER);
    }
}
