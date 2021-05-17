package fr.prog.tablut.view;

import fr.prog.tablut.controller.AdaptateurSouris;
import fr.prog.tablut.controller.Controller;
import fr.prog.tablut.model.Model;
import fr.prog.tablut.view.center.GridWindow;
import fr.prog.tablut.view.east.EastWindow;
import fr.prog.tablut.view.north.NorthWindow;
import fr.prog.tablut.view.south.SouthWindow;
import fr.prog.tablut.view.west.WestWindow;

import javax.swing.*;
import java.awt.*;

public class GlobalWindow extends JPanel {
    private final GridWindow gridWindow;
    private final NorthWindow northWindow;
    private final EastWindow eastWindow;
    private final WestWindow westWindow;
    private final SouthWindow southWindow;

    public GlobalWindow() {
        this.setLayout(new BorderLayout());

        northWindow = new NorthWindow();
        this.add(northWindow, BorderLayout.NORTH);
        eastWindow = new EastWindow();
        this.add(eastWindow, BorderLayout.EAST);
        westWindow = new WestWindow(this);
        this.add(westWindow, BorderLayout.WEST);
        southWindow = new SouthWindow();
        this.add(southWindow, BorderLayout.SOUTH);

        Model model = new Model();
        gridWindow = new GridWindow(model);
        Controller controller = new Controller(model, gridWindow);

        gridWindow.addMouseListener(new AdaptateurSouris(controller, gridWindow));

        this.add(gridWindow, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
    }

    public GridWindow getGridWindow() {
        return gridWindow;
    }

    public NorthWindow getNorthWindow() {
        return northWindow;
    }

    public EastWindow getEastWindow() {
        return eastWindow;
    }

    public WestWindow getWestWindow() {
        return westWindow;
    }

    public SouthWindow getSouthWindow() {
        return southWindow;
    }
}
