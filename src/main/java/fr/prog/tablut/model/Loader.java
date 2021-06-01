package fr.prog.tablut.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Loader {
    /**
     * Default constructor. Nothing to do.
     */
    public Loader() {

    }

    /**
     * Reads a file and returns a JSONObject from it
     * @see JSONObject
     * @param filepath The file path
     * @return The created JSONObject
     * @throws ParseException
     */
    public JSONObject getJSON(String filepath) throws ParseException {
        String content = "{}";
 
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        return new JSONObject(content);
    }

    /**
     * Converts from a JSONArray to a Color
     * @see JSONArray
     * @see Color
     * @param array the JSONArray
     * @return The converted Color
     */
    public Color getColorFromArray(JSONArray array) {
        int r = 255, g = 255, b = 255;

        if(array.length() > 2) {
            r = array.getInt(0);
            g = array.getInt(1);
            b = array.getInt(2);

            if(array.length() == 4) {
                int a = array.getInt(3);
                return  new Color(r, g, b, a);
            }
        }
        
        return new Color(r, g, b);
    }

    /**
     * Loads a font family from given file (truetype only)
     * @see Font
     * @param fontFamily
     */
    public void loadCustomFont(String fontFamily) {
        try {
            String fontPath = "res/fonts/" + fontFamily;
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        }
        catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
