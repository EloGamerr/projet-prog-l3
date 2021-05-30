package fr.prog.tablut.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;

public class BottomButtonPannel extends JPanel {
    public BottomButtonPannel(WindowName href1) {
        this(href1, null, null, "Annuler");
    }

    public BottomButtonPannel(WindowName href1, String btnText) {
        this(href1, null, null, btnText);
    }

    public BottomButtonPannel(WindowName href1, WindowName href2) {
        this(href1, href2, "Confirmer", "Annuler");
    }

    public BottomButtonPannel(WindowName href1, WindowName href2, String btnTextConfirm) {
        this(href1, href2, btnTextConfirm, "Annuler");
    }

    public BottomButtonPannel(WindowName href1, WindowName href2, String btnTextConfirm, String btnTextCancel) {
        setOpaque(false);
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(10, 0, 10, 0));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 20, 0, 0);
		c.gridx = 0;

        GenericRoundedButton btn1 = new GenericRoundedButton(btnTextCancel, 150, 35);
        btn1.setHref(href1);
        c.gridy = 0;
		add(btn1, c);
		
        if(href2 != null) {
            GenericRoundedButton btn2 = new GenericRoundedButton(btnTextConfirm, 150, 35);
            btn2.setHref(href2);
            btn2.setStyle("button.green");
            c.gridx = 1;
            add(btn2, c);
        }
    }
}
