package fr.prog.tablut.view.pages.help;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPanel;
import fr.prog.tablut.view.components.NavPage;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericPanel;

/**
 * The Help page. Extends Page class.
 * <p>Shows shortcuts and their action.</p>
 * @see Page
 */
public class HelpPage extends Page {
	BottomButtonPanel bbp;
    GenericPanel rules;

	/**
	 * Creates a help page.
	 * <p>It needs to know the reference of the last opened page so when the user clicks on "Retour",
	 * it goes back to the previous page.</p>
	 * @param config The configuration to apply to the page
	 * @param currentPage The previous page to go on when the user closes this page
	 */
	public HelpPage(WindowConfig config, PageName pageBack) {
		super(config);

		windowName = PageName.HelpPage;

        createRules();

		bbp = new BottomButtonPanel(pageBack, "Retour");

		NavPage page = new NavPage(bbp);

        GenericPanel pageContent = new GenericPanel(new BorderLayout());
		
        pageContent.add(rules, BorderLayout.NORTH);
        pageContent.add(new HelpTable(), BorderLayout.CENTER);

        page.setContent(pageContent);

		add(page);
	}

	public void setBackPage(PageName pageBack) {
		bbp.setFirstButtonHref(pageBack);
	}

    private void createRules() {
        rules = new GenericPanel();

        GenericLabel text = new GenericLabel(
            "<html>"
            + "<p>Il y a 2 camps : l'attaquant, et le défenseur.</p><br>"
            + "<p>- Le défenseur doit faire échapper son roi dans un coin du plateau,</p>"
            + "<p>- l'attaquant doit capturer le roi en l'entourant avec 4 de ses pièces.</p><br>"
            + "<p>Chaque camp peut détruire une pièce en l'entourant de deux pièces.</p>" +
            "</html>"
        , 12);

        rules.setBorder(new EmptyBorder(80, 0, 0, 0));
        rules.add(text);
    }
}