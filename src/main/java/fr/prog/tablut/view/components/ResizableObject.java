package fr.prog.tablut.view.components;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * A component extending JPanel that's used to be resized easily, and with a callback function
 * @see JPanel
 */
public class ResizableObject extends JPanel {
    /**
     * Creates a new resizable object
     */
    public ResizableObject() {

    }

    /**
     * Resizes the object with given dimension.
     * <p>Does not work if the parameter is null.</p>
     * @param d The dimension to set to the object
     */
    @Override
    public void resize(Dimension d) {
        if(d != null) {
            setSize(d.width, d.height);
            setPreferredSize(d);
            setMaximumSize(d);
            setMinimumSize(d);

            onResize(d.width, d.height);
        }
    }

    /**
     * Called when we resize the object.
     * @param width The new object's width
     * @param height The new object's height
     */
    protected void onResize(int width, int height) {

    }
}
