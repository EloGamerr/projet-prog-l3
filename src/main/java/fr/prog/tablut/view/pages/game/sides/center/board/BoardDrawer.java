package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics2D;

public class BoardDrawer {
    private int cellNumber = 9;
    private int boardSize = 0;
    private int leftX = 0;
    private int leftY = 0;
    private int cellSize = 0;
    private int widthSeparator = 2;
	private int widthBorder = 25;

    private Graphics2D g2d;

    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);


    public BoardDrawer() {

    }


    public void setBoardDimension(int size) {
        boardSize = size;
        cellSize = (boardSize - 2 * widthBorder) / cellNumber;
    }

    public void setPosition(int x, int y) {
        leftX = x;
        leftY = y;
    }
    
    public void setColor(Color color) {
        g2d.setColor(color);
    }

    public void setGraphics(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public int getSize() {
        return boardSize;
    }

    public int getBorderWidth() {
        return widthBorder;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getCellSepSize() {
        return widthSeparator;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public int getRealX(int x) {
    	return leftX + widthBorder + x * cellSize;
    }

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
        /* if(cursor == "hand") g2d.setCursor(handCursor);
        else g2d.setCursor(defaultCursor); */
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
