package net.greenmanov.iqdb.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface of java api for iqdb image matching
 */
public interface IIqdbApi {
    int MAX_FILE_SIZE = 8388608;
    int MAX_IMAGE_WIDTH = 7500;
    int MAX_IMAGE_HEIGHT = 7500;

    String URL = "https://iqdb.org/";
    String FILED_SERVICE = "service[]";
    String FIELD_FILE = "file";
    String FIELD_URL = "url";
    String FILED_IGNORE_COLORS = "forcegray";


    /**
     * Search image file on the iqdb and return sorted list of matches
     *
     * @param file    File to upload for matching
     * @param options Form options
     * @return List sorted from most similar to least similar match
     * @throws IOException On problem with getting response from server
     * @throws FileSizeLimitException If file size is larger than allowed by iqdb
     */
    List<Match> searchFile(File file, Options options) throws IOException, FileSizeLimitException;

    /**
     * Search image from url on the iqdb and return sorted list of matches
     *
     * @param url     URL of image
     * @param options Form options
     * @return List sorted from most similar to least similar match
     * @throws IOException On problem with getting response from server
     */
    List<Match> searchUrl(String url, Options options) throws IOException;
}
