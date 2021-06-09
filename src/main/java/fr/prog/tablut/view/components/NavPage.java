package fr.prog.tablut.view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericPanel;

/**
 * A component extending GenericPanel that creates a page with header and footer.
 * <p>The header has a title and can have a description.</p>
 * <p>The footer is a BottomButtonPanel with one or two buttons.</p>
 * <p>The content of the page is centered.</p>
 * @see GenericPanel
 * @see BottomButtonPanel
 * @see Title
 * @see GenericLabel
 */
public class NavPage extends GenericPanel {
    protected static Dimension maxDimension;

    private Title title = null;
    private GenericLabel description = null;
    private BottomButtonPanel bottomPanel = null;

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
        super(new BorderLayout());

        setSize(maxDimension);
        setPreferredSize(maxDimension);
        setMaximumSize(maxDimension);
        setMinimumSize(maxDimension);

        if(title != null) {
            this.title = new Title(title.toUpperCase(), 70);

            GenericPanel gb = new GenericPanel(new GridBagLayout());
            gb.setBorder(new EmptyBorder(50, 0, 0, 0));

            Dimension gbSize = new Dimension((int)maxDimension.getWidth(), 170);
            gb.setPreferredSize(gbSize);
            gb.setMaximumSize(gbSize);
            gb.setMinimumSize(gbSize);

            // title
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = 1;

            gb.add(this.title, gbc);

            // description
            if(description != null) {
                this.description = new GenericLabel(description, 14);
                this.description.setForeground(GenericObjectStyle.getProp("description", "color"));
                gbc.gridy = 2;
                gb.add(this.description, gbc);
            }

            add(gb, BorderLayout.NORTH);
        }

        this.bottomPanel = bottomPanel;

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

    /**
     * Returns the title of the nav page
     * @return The title of the nav page
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Returns the description of the nav page
     * @return The description of the nav page
     */
    public GenericLabel getDescription() {
        return description;
    }

    /**
     * Returns the bottomPanel object of the nav page
     * @return The bottomPanel
     */
    public BottomButtonPanel getBottomPanel() {
        return bottomPanel;
    }
}
