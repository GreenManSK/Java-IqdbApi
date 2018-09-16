package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AJsoupParseer;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser for e-shuushuu.net
 */
public class EShuusshuuParser extends AJsoupParseer {
    protected static final String IMAGE_LINK_SELECTOR = ".thumb_image";

    /**
     * Get tags of the image
     *
     * @return List of all tags of the image
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public List<Tag> getTags() throws IllegalStateException {
        checkParseCall();
        List<Tag> tags = new ArrayList<>();
        tags.addAll(getTags("Tags", TagType.GENERAL));
        tags.addAll(getTags("Source", TagType.COPYRIGHT));
        tags.addAll(getTags("Characters", TagType.CHARACTER));
        tags.addAll(getTags("Artist", TagType.ARTIST));
        return tags;
    }

    /**
     * Get tags from one category and creates object for them
     * @param name Name of the category on the site
     * @param type TagType
     * @return List of tags
     */
    protected List<Tag> getTags(String name, TagType type) {
        Elements tagContainer = dom.select("dt:contains(" + name + ":)");
        if (tagContainer.size() == 0) {
            return Collections.emptyList();
        }
        return tagContainer.first().nextElementSibling().getElementsByTag("a")
                .stream().map(e -> new Tag(type, e.text())).collect(Collectors.toList());
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
