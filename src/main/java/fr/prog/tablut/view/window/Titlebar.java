package fr.prog.tablut.view.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;

import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import fr.prog.tablut.model.Loader;
import fr.prog.tablut.view.utils.ImageTreatment;

/**
 * Custom titlebar UI & manager
 */
class Titlebar extends JPanel {
    private GlobalWindow parent;
    private int width;
    private int height;
    private Point grabPoint = new Point(0, 0);

    /**
     * Creates a custom titlebar UI & manager
     * @see JPanel
     * @see GlobalWindow
     * @param parent The window that contains the titlebar
     */
    public Titlebar(GlobalWindow parent) {
        this.parent = parent;
        width = parent.getConfig().windowWidth;
        height = 20;

        Dimension d = new Dimension(width, height);

        setLayout(new BorderLayout());

        setSize(d);
        setPreferredSize(d);

        setBackground(parent.getConfig().getStyle().get("window").get("background"));

        addButtons();

        addMouseMotionListener(new MouseMotionAdapter() {
            // move the window grabbing the titlebar
            public void mouseDragged(MouseEvent evt) {
                parent.setLocation(evt.getXOnScreen() - grabPoint.x, evt.getYOnScreen() - grabPoint.y);
            }
        });

        addMouseListener(new MouseAdapter() {
            // maximize / minimize
            public void mouseClicked(MouseEvent evt) {
                if(evt.getClickCount() == 2 && !evt.isConsumed()) {
                    parent.maximize();
                }
            }

            // update the grab point on the titlebar
            public void mousePressed(MouseEvent evt) {
                grabPoint = evt.getPoint();
            }
        });
    }

    /**
     * Adds the 3 buttons close, maximize/minimize and reduce
     */
    private void addButtons() {
        JLabel close, maximize, reduce;

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final Dimension d = new Dimension(3 * (height+10), height);

        rightPanel.setSize(d);
        rightPanel.setPreferredSize(d);
        rightPanel.setOpaque(false);

        close = createButton("theme/close.png");
        maximize = createButton("theme/maximize.png");
        reduce = createButton("theme/reduce.png");

        rightPanel.add(reduce);
        rightPanel.add(maximize);
        rightPanel.add(close);

        reduce.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                parent.setState(Frame.ICONIFIED);
            }
        });

        maximize.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                //parent.maximize(); // disabled for the presentation
            }
        });

        close.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                parent.close();
            }
        });

        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Creates a button with given icon path
     * @param iconPath The icon's path to put on the button
     * @return The created button
     */
    private JLabel createButton(String iconPath) {
        JLabel l = new JLabel("", JLabel.CENTER);

        final Dimension d = new Dimension(height, height);

        l.setSize(d);
        l.setPreferredSize(d);
        
		try {
            BufferedImage bufferImage = Loader.getBufferedImage(iconPath);
            Image img = bufferImage.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(img);

            l.setIcon(icon);

            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    l.setIcon(new ImageIcon(ImageTreatment.setImageBrightness(bufferImage, 1.4f).getScaledInstance(10, 10, Image.SCALE_DEFAULT)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    l.setIcon(icon);
                }
            });

		} catch(IOException e) {
			e.printStackTrace();
		}

        return l;
    }
}
