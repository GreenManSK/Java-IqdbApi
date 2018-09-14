package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AJsoupParseer;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for chan.sankakucomplex.com
 */
public class SankakuChannelParser extends AJsoupParseer {

    protected static final String IMAGE_LINK_SELECTOR = "#stats li:contains(Original:)";
    protected static final String TAGS_SELECTOR = "#tag-sidebar li";

    /**
     * Get tags of the image
     *
     * @return List of all tags of the image
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public List<Tag> getTags() throws IllegalStateException {
        checkParseCall();
        List<Tag> result = new ArrayList<>();
        Elements tags = dom.select(TAGS_SELECTOR);
        for (Element tag : tags) {
            result.add(new Tag(getTagType(tag.attr("class")), tag.getElementsByTag("a").first().text()));
        }
        return result;
    }

    /**
     * Transfer konachan internal tag name to TagType
     *
     * @param type tag name
     * @return TagType
     */
    protected TagType getTagType(String type) {
        switch (type) {
            case "tag-type-artist":
                return TagType.ARTIST;
            case "tag-type-character":
                return TagType.CHARACTER;
            case "tag-type-copyright":
                return TagType.COPYRIGHT;
            case "tag-type-medium":
                return TagType.MEDIUM;
            case "tag-type-meta":
                return TagType.META;
            case "tag-type-studio":
                return TagType.STUDIO;
            default:
                return TagType.GENERAL;
        }
    }

    /**
     * Get url of the image file
     *
     * @return url
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public String getImage() throws IllegalStateException {
        checkParseCall();
        return dom.select(IMAGE_LINK_SELECTOR).first().getElementsByTag("a").attr("abs:href");
    }

    /**
     * Get url to the source of image if any is known
     *
     * @return url of null
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public String getSource() throws IllegalStateException {
        checkParseCall();
        return null;
    }
}
