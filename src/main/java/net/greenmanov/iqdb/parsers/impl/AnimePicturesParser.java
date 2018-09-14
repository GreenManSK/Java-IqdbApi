package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AJsoupParseer;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser for amine-pictures.net
 */
public class AnimePicturesParser extends AJsoupParseer {
    protected static final String IMAGE_LINK_SELECTOR = ".download_icon";
    protected static final String TAGS_SELECTOR = "#post_tags li";

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
        tags.addAll(getTags("", TagType.GENERAL));
        tags.addAll(getTags("green", TagType.COPYRIGHT));
        tags.addAll(getTags("orange", TagType.ARTIST));
        tags.addAll(getTags("blue", TagType.CHARACTER));
        return tags;
    }
    /**
     * Get tags from one category and creates object for them
     * @param className Class name of the category on the site, uses colors
     * @param type TagType
     * @return List of tags
     */
    protected List<Tag> getTags(String className, TagType type) {
        return dom.select(TAGS_SELECTOR + "[class='" + className + "']").stream()
                .map(e -> new Tag(type, e.getElementsByTag("a").text())).collect(Collectors.toList());
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
