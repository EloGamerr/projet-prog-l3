package fr.prog.tablut.view.utils;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * A component that does some operations on images
 */
public abstract class ImageTreatment {
    /**
     * Changes image's brightness
     * @param image The image to change its brightness
     * @param brightness The new image's brightness. 1 means no changes.
     * @return The transformed image
     */
    public static BufferedImage setImageBrightness(BufferedImage image, float brightness) {
        return setRGBFactors(image, brightness, brightness, brightness, brightness);
    }

    /**
     * Changes the color's factors of an image, including alpha.
     * <pre>
     * = 1 = no changes
     *> 1 = lighten
     *< 1 = darken
     * </pre>
     * @param image The image to change its factors
     * @param r The red factor
     * @param g The green factor
     * @param b The blue factor
     * @param a The alpha factor
     * @return The changed image
     */
    public static BufferedImage setRGBFactors(BufferedImage image, float r, float g, float b, float a) {
        float[] factors = new float[] { r, g, b, a };
        float[] offsets = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
        return (new RescaleOp(factors, offsets, null)).filter(image, null);
    }

    /**
     * Changes the color's factors of an image.
     * <pre>
     * = 1 = no changes
     *> 1 = lighten
     *< 1 = darken
     * </pre>
     * @param image The image to change its factors
     * @param r The red factor
     * @param g The green factor
     * @param b The blue factor
     * @return The changed image
     */
    public static BufferedImage setRGBFactors(BufferedImage image, float r, float g, float b) {
        return setRGBFactors(image, r, g, b, 1.f);
    }

    /**
     * Changes the color's factors of an image.
     * <pre>
     * = 1 = no changes
     *> 1 = lighten
     *< 1 = darken
     * </pre>
     * @param image The image to change its factors
     * @param r The global rgb factor
     * @return The changed image
     */
    public static BufferedImage setRGBFactors(BufferedImage image, float factor) {
        return setRGBFactors(image, factor, factor, factor, 1.f);
    }
}
