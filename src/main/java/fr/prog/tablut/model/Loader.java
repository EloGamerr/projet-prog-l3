package fr.prog.tablut.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;

import java.text.ParseException;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.prog.tablut.Tablut;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Loader {
    /**
     * Reads a file and returns a JSONObject from it
     * @see JSONObject
     * @param filepath The file path
     * @return The created JSONObject
     */
    public static JSONObject getJSON(String filepath) throws ParseException {
        String content = "{}";
 
        try {
            InputStream in;
            if(Tablut.configPath.equals(filepath)) {
                in = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath);
            }
            else {
                in = new FileInputStream(filepath);
            }
            content = IOUtils.toString(Objects.requireNonNull(in));
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
    public static Color getColorFromArray(JSONArray array) {
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
     */
    public static void loadCustomFont(String fontFamily) {
        try {
            String fontPath = "fonts/" + fontFamily;
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fontPath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(in));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        }
        catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an image and returns its buffer image
     * @param imagePath The image's source path
     * @return The buffer of the image
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(String imagePath) throws IOException {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("images/" + imagePath);
        return ImageIO.read(Objects.requireNonNull(in));
    }

    /**
     * Loads an returns an image
     * @param imagePath The image's source path
     * @return The loaded image
     * @throws IOException
     */
    public static Image getImage(String imagePath) throws IOException {
        return (Image)getBufferedImage(imagePath);
    }
}
