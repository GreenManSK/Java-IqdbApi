package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AJsoupParseer;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GelbooruParser extends AJsoupParseer {

    protected static final String SOURCE_SELECTOR = "li:not([class]):contains(Source:)";
    protected static final String IMAGE_LINK_SELECTOR = "li:not([class]) a:contains(Original image)";
    protected static final String TAGS_SELECTOR = "li[class^=tag-type-]";

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
            result.add(new Tag(getTagType(tag.attr("class")), tag.getElementsByTag("a").get(1).text()));
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
            case "tag-type-copyright":
                return TagType.COPYRIGHT;
            case "tag-type-character":
                return TagType.CHARACTER;
            case "tag-type-artist":
                return TagType.ARTIST;
            case "tag-type-metadata":
                return TagType.META;
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
        final String[] href = {null};
        onFirst(dom.select(IMAGE_LINK_SELECTOR), e -> {
            onFirst(e.getElementsByTag("a"), e2 -> {
                href[0] = e2.attr("abs:href");
            });
        });
        return href[0];
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
        Elements element = dom.select(SOURCE_SELECTOR);
        if (element.isEmpty())
            return null;
        return element.first().getElementsByTag("a").first().attr("abs:href");
    }
}
