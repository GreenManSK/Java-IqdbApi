package net.greenmanov.iqdb.parsers;

import java.io.IOException;
import java.util.List;

/**
 * Interface for parsing service sites supported by the iqdb.
 * Is able to get tags of the image, link to image file and possible original source of the image
 *
 * @author Lukáš Kurčík
 */
public interface IParser {
    /**
     * Parse content of the url and saves information into this object.
     * Have to be called before any getter
     * @param url URL to the image page
     * @throws IOException If there is problem with getting content of the image page
     */
    void parse(String url) throws IOException;

    /**
     * Get tags of the image
     * @return List of all tags of the image
     * @throws IllegalStateException If parse was not called before
     */
    List<Tag> getTags() throws IllegalStateException;

    /**
     * Get url of the image file
     * @return url
     * @throws IllegalStateException If parse was not called before
     */
    String getImage() throws IllegalStateException;

    /**
     * Get url to the source of image if any is known
     * @return url of null
     * @throws IllegalStateException If parse was not called before
     */
    String getSource() throws IllegalStateException;
}
