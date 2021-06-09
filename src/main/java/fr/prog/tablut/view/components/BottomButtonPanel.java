package fr.prog.tablut.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;

import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;

/**
 * A component that places a GenericPanel of 1 or 2 buttons which will href to another page
 * <p>The second button has the button.green style</p>
 * @see GenericPanel
 * @see PageName
 * @see GenericRoundedButton
 */
public class BottomButtonPanel extends GenericPanel {
    private GenericRoundedButton btn1 = null;
    private GenericRoundedButton btn2 = null;

    /**
     * Default constructor.
     * <p>Creates a button with text "Retour"</p>
     * @see PageName
     * @param href1 The href location of the button
     */
    public BottomButtonPanel(PageName href1) {
        this(href1, null, null, "Retour");
    }

    /**
     * Creates a button, having the given text
     * @see PageName
     * @param href1 The href of the button
     * @param btnText The button label
     */
    public BottomButtonPanel(PageName href1, String btnText) {
        this(href1, null, null, btnText);
    }

    /**
     * Creates two buttons, the first with the text "Retour"
     * and the second with the text "Confirmer" and with button.green style.
     * @see PageName
     * @param href1 The href of the cancel button
     * @param href2 The href of the confirm button
     */
    public BottomButtonPanel(PageName href1, PageName href2) {
        this(href1, href2, "Confirmer", "Retour");
    }

    /**
     * Creates two buttons, with first having the text "Retour",
     * the second with given text, and button.green style.
     * @see PageName
     * @param href1 The href of the first button
     * @param href2 The href of the second button
     * @param btnTextConfirm The text of the second button
     */
    public BottomButtonPanel(PageName href1, PageName href2, String btnTextConfirm) {
        this(href1, href2, btnTextConfirm, "Retour");
    }

    /**
     * Creates two buttons, with given labels and href locations.
     * <p>The second button has button.green style.</p>
     * @see PageName
     * @param href1 The href of the first button
     * @param href2 The href of the second button
     * @param btnTextConfirm The label of the second button
     * @param btnTextCancel The label of the first button
     */
    public BottomButtonPanel(PageName href1, PageName href2, String btnTextConfirm, String btnTextCancel) {
        super(new GridBagLayout());
		setBorder(new EmptyBorder(10, 0, 10, 0));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 1, 0, 1);
		c.gridx = 0;

        btn1 = new GenericRoundedButton(btnTextCancel, 170, 35);
        btn1.setHref(href1);
        btn1.setBorderRadius(8);
        c.gridy = 0;
		add(btn1, c);
		
        if(href2 != null) {
            btn2 = new GenericRoundedButton(btnTextConfirm, 170, 35);
            btn2.setHref(href2);
            btn2.setBorderRadius(8);
            btn2.setStyle("button.green");
            c.gridx = 1;
            add(btn2, c);
        }
    }

    public void setFirstButtonHref(PageName href) {
        btn1.setHref(href);
    }

    public void setSecondButtonHref(PageName href) {
        if(btn2 != null)
            btn2.setHref(href);
    }

    public GenericRoundedButton getButton1() {
        return btn1;
    }

    public GenericRoundedButton getButton2() {
        return btn2;
    }
}
