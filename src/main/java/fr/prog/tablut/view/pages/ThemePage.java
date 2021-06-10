package fr.prog.tablut.view.pages;

import java.awt.Dimension;

import fr.prog.tablut.view.components.ImageComponent;
import fr.prog.tablut.view.window.PageName;
import fr.prog.tablut.view.window.WindowConfig;

public class ThemePage extends Page {
    private int iconSize = 500;
    private String blackTowerSrc = "theme/transparent_black_tower.png";
    private String whiteTowerSrc = "theme/transparent_white_tower.png";
    private ImageComponent blackTower;
    private ImageComponent whiteTower;

    /**
     * Creates a new themed page
     * @param name The name of the page
     */
    public ThemePage(PageName name) {
        super(name);
    }

    /**
     * Creates a new themed page with given configuration
     * @param config The configuration to set
     * @param name The name of the page
     */
    public ThemePage(WindowConfig config, PageName name) {
        super(config, name);

        // setup background pannel
        iconSize = 3 * config.windowHeight / 4;

        blackTower = new ImageComponent(blackTowerSrc, -iconSize/2, config.windowHeight/2 - iconSize/2, iconSize, iconSize);
        whiteTower = new ImageComponent(whiteTowerSrc, config.windowWidth-iconSize/2, config.windowHeight/2 - iconSize/2, iconSize, iconSize);

        blackTower.load();
        whiteTower.load();

        backgroundPanel.add(blackTower);
        backgroundPanel.add(whiteTower);
    }

    /**
     * Resizes the elements on the page
     */
    @Override
    protected void onResize(int width, int height) {
        if(blackTower != null && whiteTower != null) {
            iconSize = 3 * height / 4;

            Dimension d = new Dimension(iconSize, iconSize);

            blackTower.resize(d);
            blackTower.setLocation(-iconSize/2, height/2 - iconSize/2);

            whiteTower.resize(d);
            whiteTower.setLocation(width-iconSize/2, height/2 - iconSize/2);
        }
    }
}
