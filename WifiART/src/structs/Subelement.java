package structs;

/**
 * Represents an LCI/LCR subelement
 */
public interface Subelement {
    /**
     * Produces the hex-buffer encoding for this LCI/LCR subelement
     *
     * @return The hex-buffer encoding
     */
    String toHexBuffer();
}
