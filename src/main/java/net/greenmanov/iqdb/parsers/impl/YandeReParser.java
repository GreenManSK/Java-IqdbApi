package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AMoebooruParser;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for yande.re
 *
 * @author Lukáš Kurčík
 */
public class YandeReParser extends AMoebooruParser {

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
        Elements tags = dom.select(TAG_CONTAINER_SELECTOR).first().getElementsByTag("li");
        for (Element tag : tags) {
            result.add(new Tag(getTagType(tag.attr("class")), tag.getElementsByTag("a").get(1).text()));
        }
        return result;
    }

    /**
     * Transfer yandere internal tag name to TagType
     *
     * @param type tag name
     * @return TagType
     */
    protected TagType getTagType(String type) {
        switch (type) {
            case "tag-type-copyright":
                return TagType.COPYRIGHT;
            case "tag-type-circle":
                return TagType.CIRCLE;
            case "tag-type-character":
                return TagType.CHARACTER;
            case "tag-type-artist":
                return TagType.ARTIST;
            case "tag-type-faults":
                return TagType.FAULTS;
            default:
                return TagType.GENERAL;
        }
    }
}
