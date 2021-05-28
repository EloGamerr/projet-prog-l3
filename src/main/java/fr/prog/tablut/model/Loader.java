package fr.prog.tablut.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.awt.Color;

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
}
