package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.api.ServiceType;
import net.greenmanov.iqdb.parsers.IParser;
import net.greenmanov.iqdb.parsers.Tag;

import java.io.IOException;
import java.util.List;

/**
 * Parser able to dynamicaly choose right type of parsing for provided site.
 * Supports all sites in ServiceType
 */
public class DynamicParser implements IParser {

    protected IParser innerParser;

    public DynamicParser() {
    }

    /**
     * Parse content of the url and saves information into this object.
     * Have to be called before any getter
     *
     * @param url URL to the image page
     * @throws IOException              If there is problem with getting content of the image page
     * @throws IllegalArgumentException If there is no parser for this url
     */
    @Override
    public void parse(String url) throws IOException {
        ServiceType type = ServiceType.getTypeByUrl(url);
        switch (type) {
            case DANBOORU:
                innerParser = new DanbooruParser();
                break;
            case KONACHAN:
                innerParser = new KonachanParser();
                break;
            case YANDE_RE:
                innerParser = new YandeReParser();
                break;
            case GELBOORU:
                innerParser = new GelbooruParser();
                break;
            case SANKAKU_CHANNEL:
                new SankakuChannelParser();
                break;
            case E_SHUUSHUU:
                innerParser = new EShuusshuuParser();
                break;
            case ZEROCHAN:
                innerParser = new ZerochanParser();
                break;
            case ANIME_PICTURES:
                innerParser = new AnimePicturesParser();
                break;
            default:
                throw new IllegalArgumentException(type.getDomain() + " is not supported right now");
        }
        innerParser.parse(url);
    }

    /**
     * Get tags of the image
     *
     * @return List of all tags of the image
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public List<Tag> getTags() throws IllegalStateException {
        return innerParser.getTags();
    }

    /**
     * Get url of the image file
     *
     * @return url
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public String getImage() throws IllegalStateException {
        return innerParser.getImage();
    }

    /**
     * Get url to the source of image if any is known
     *
     * @return url of null
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public String getSource() throws IllegalStateException {
        return innerParser.getSource();
    }

    /**
     * Check if parse was called and throw exception if not
     *
     * @throws IllegalStateException If there is problem with getting content of the image page
     */
    protected void checkParser() throws IllegalStateException {
        if (innerParser == null) {
            throw new IllegalStateException("Need to call parse() function on this object before using getters");
        }
    }
}
