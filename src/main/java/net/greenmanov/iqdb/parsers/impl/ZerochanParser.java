package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AJsoupParseer;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for zerochan.net
 */
public class ZerochanParser extends AJsoupParseer {

    protected static final String IMAGE_LINK_SELECTOR = ".preview";
    protected static final String TAGS_SELECTOR = "#tags li";

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
            result.add(new Tag(getTagType(tag), tag.getElementsByTag("a").first().text()));
        }
        return result;
    }

    /**
     * Tries to detect tag type from the html element
     *
     * @param node HTML node representing tag
     * @return TagType
     */
    protected TagType getTagType(Element node) {
        String[] tagTypes = node.ownText().split(", ");
        for (String tagText : tagTypes) {
            switch (tagText.toLowerCase()) {
                case "studio":
                    return TagType.STUDIO;
                case "visual novel":
                case "series":
                case "game":
                    return TagType.COPYRIGHT;
                case "character":
                    return TagType.CHARACTER;
                case "mangaka":
                    return TagType.ARTIST;
                case "source":
                    return TagType.SOURCE;

            }
        }
        return TagType.GENERAL;
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
        return dom.select(IMAGE_LINK_SELECTOR).first().attr("abs:href");
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
