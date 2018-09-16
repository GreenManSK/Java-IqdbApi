package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AMoebooruParser;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parser for konachan.com
 *
 * @author Lukáš Kurčík
 */
public class KonachanParser extends AMoebooruParser {

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
        Elements tagContainer = dom.select(TAG_CONTAINER_SELECTOR);
        if (tagContainer.isEmpty()) {
            return Collections.emptyList();
        }
        Elements tags = tagContainer.first().getElementsByTag("li");
        for (Element tag : tags) {
            result.add(new Tag(getTagType(tag.attr("data-type")), tag.attr("data-name")));
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
            case "copyright":
                return TagType.COPYRIGHT;
            case "circle":
                return TagType.CIRCLE;
            case "character":
                return TagType.CHARACTER;
            case "artist":
                return TagType.ARTIST;
            case "style":
                return TagType.STYLE;
            default:
                return TagType.GENERAL;
        }
    }
}
