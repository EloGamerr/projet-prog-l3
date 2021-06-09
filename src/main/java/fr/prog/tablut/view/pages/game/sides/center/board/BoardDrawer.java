package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;

import java.awt.Graphics2D;

/**
 * A class that's used to draw on the board with all sizes, positions etc...
 * <p>It's an util class that ressemble every calculations to draw at correct positions</p>
 * <p>Because most of the drawings are around cell's positions</p>
 * <p>It's an independant class that doesn't look the model, communicates with the controller or with other project's view components.</p>
 */
public class BoardDrawer {
    private int cellNumber = 9;
    private int boardSize = 0;
    private int leftX = 0;
    private int leftY = 0;
    private int cellSize = 0;
    private int widthSeparator = 2;
	private int widthBorder = 25;

    private Graphics2D g2d;
    private JPanel container;

    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    /**
     * Creates a BoardDrawer utility manager
     */
    public BoardDrawer() {

    }

    /**
     * Sets the board's dimension to draw
     * @param size The square board dimension
     */
    public void setBoardDimension(int size) {
        boardSize = size;
        cellSize = (boardSize - 2 * widthBorder) / cellNumber;
    }

    /**
     * Sets the board's position to draw
     * @param x The x-Axis position's coord
     * @param y The y-Axis position's coord
     */
    public void setPosition(int x, int y) {
        leftX = x;
        leftY = y;
    }

    /**
     * Sets in which container it has to draw.
     * <p>Only used to set the cursor's type.</p>
     * @param container
     */
    public void setContainer(JPanel container) {
        this.container = container;
    }
    
    /**
     * Sets the draw color
     * @param color The color to set
     */
    public void setColor(Color color) {
        g2d.setColor(color);
    }

    /**
     * Updates the g2d graphics's reference
     * @param g2d The graphics's object reference
     */
    public void setGraphics(Graphics2D g2d) {
        this.g2d = g2d;
    }

    /**
     * Returns the size of the drawn board
     * @return
     */
    public int getSize() {
        return boardSize;
    }

    /**
     * Returns the board's border width
     * @return The board's border width
     */
    public int getBorderWidth() {
        return widthBorder;
    }

    /**
     * Returns the board's cell size
     * @return The board's cell size
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Returns the board's cell separator lines
     * @return The board's cell separator lines
     */
    public int getCellSepSize() {
        return widthSeparator;
    }

    /**
     * Returns the number of cell on a side
     * @return The number of cell on a side
     */
    public int getCellNumber() {
        return cellNumber;
    }

    /**
     * Returns the real pixel x-Axis coord from a column's index
     * @param x The column's index
     * @return The x-Axis pixel's coord
     */
    public int getRealX(int x) {
    	return leftX + widthBorder + x * cellSize;
    }

    /**
     * Returns the real pixel y-Axis coord from a row's index
     * @param y The row's index
     * @return The y-Axis pixel's coord
     */
    public int getRealY(int y) {
       return leftY + widthBorder + y * cellSize;
    }


    // G2D SETTERS
    public void strokeWidth(int width) {
        g2d.setStroke(new BasicStroke(width));
    }
    
    public void setFont(Font font) {
        g2d.setFont(font);
    }

    public void setCursor(String cursor) {
        if(cursor == "hand") container.setCursor(handCursor);
        else container.setCursor(defaultCursor);
    }


    // RECT
    public void fillRectCoords(int x, int y, int width, int height) {
        g2d.fillRect(x, y, width, height);
    }

    public void strokeRectCoords(int x, int y, int width, int height) {
        g2d.drawRect(x, y, width, height);
    }

    // CIRCLE
    public void fillCircleCoords(int x, int y, int r) {
        g2d.fillOval(x, y, r, r);
    }

    public void strokeCircleCoords(int x, int y, int r) {
        g2d.drawOval(x, y, r, r);
    }

    // LINE
    public void line(int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
    }

    // IMAGE
    public void drawImageCoords(Image img, int x, int y, int width, int height, Color bgColor) {
        g2d.drawImage(img, x, y, width, height, bgColor, null);
    }

    // STRING
    public void drawString(String str, int x, int y) {
        g2d.drawString(str, x, y);
    }

    public void drawString(String str, float x, float y) {
        g2d.drawString(str, x, y);
    }


    // FILL RECT / SQUARE
    public void fillRect(int x, int y, int width, int height) {
        fillRectCoords(getRealX(x) + widthSeparator/2, getRealY(y) + widthSeparator/2, width - widthSeparator, height - widthSeparator);
    }

    public void fillSquare(int x, int y) {
        fillRect(x, y, cellSize, cellSize);
    }

    public void fillSquare(int x, int y, int size) {
        fillRect(x, y, size, size);
    }

    public void fillSquareCoords(int x, int y, int size) {
        fillRectCoords(x, y, size, size);
    }


    // STROKE RECT / SQUARE
    public void strokeRect(int x, int y, int width, int height) {
        strokeRectCoords(getRealX(x) + widthSeparator/2, getRealY(y) + widthSeparator/2, width - widthSeparator, height - widthSeparator);
    }

    public void strokeSquare(int x, int y) {
        strokeRect(x, y, cellSize, cellSize);
    }

    public void strokeSquare(int x, int y, int size) {
        strokeRect(x, y, size, size);
    }

    public void strokeSquareCoords(int x, int y, int size) {
        strokeRectCoords(x, y, size, size);
    }

    // FILL / STROKE CIRCLE
    public void fillCircle(int x, int y, int r) {
        fillCircleCoords(getRealX(x), getRealY(y), r);
    }

    public void strokeCircle(int x, int y, int r) {
        strokeCircleCoords(getRealX(x), getRealY(y), r);
    }


    // IMAGE
    public void drawImage(Image img, int x, int y, int width, int height, Color bgColor) {
        drawImageCoords(img, getRealX(x), getRealY(y), width, height, bgColor);
    }

    public void drawImage(Image img, int x, int y, int width, int height) {
        drawImageCoords(img, getRealX(x), getRealY(y), width, height, null);
    }

    public void drawImage(Image img, int x, int y, Color bgColor) {
        drawImage(img, x, y, cellSize, cellSize, bgColor);
    }

    public void drawImage(Image img, int x, int y) {
        drawImage(img, x, y, cellSize, cellSize, null);
    }

    public void drawImage(Image img, int x, int y, int width, int height, boolean centered) {
        if(centered) {
            x = getRealX(x) + cellSize/2 - width/2;
            y = getRealY(y) + cellSize/2 - height /2;
        }

        drawImageCoords(img, x, y, width, height, null);
    }

    public void drawImage(Image img, int x, int y, int width, int height, Color bgColor, boolean centered) {
        if(centered) {
            x = getRealX(x) + cellSize/2 - width/2;
            y = getRealY(y) + cellSize/2 - height/2;
        }

        drawImageCoords(img, x, y, width, height, bgColor);
    }
}
