package net.greenmanov.iqdb.parsers.impl;

import net.greenmanov.iqdb.parsers.AJsoupParseer;
import net.greenmanov.iqdb.parsers.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for danbooru.donmai.us
 *
 * @author Lukáš Kurčík
 */
public class DanbooruParser extends AJsoupParseer {

    protected final static String INFO_SECTION_ID = "post-information";

    protected final static String COPYRIGHT_TAGS_CLASS = "copyright-tag-list";
    protected final static String CHARACTER_TAGS_CLASS = "character-tag-list";
    protected final static String ARTIST_TAGS_CLASS = "artist-tag-list";
    protected final static String GENERAL_TAGS_CLASS = "general-tag-list";
    protected final static String META_TAGS_CLASS = "meta-tag-list";
    protected final static String[] TAG_LIST_CLASS = new String[]{
            COPYRIGHT_TAGS_CLASS,
            CHARACTER_TAGS_CLASS,
            ARTIST_TAGS_CLASS,
            GENERAL_TAGS_CLASS,
            META_TAGS_CLASS
    };

    protected final static String TAG_NAME_ELEMENT_CLASS = "search-tag";

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
        for (String sectionId: TAG_LIST_CLASS) {
            tags.addAll(getTags(sectionId, sectionIdToTagType(sectionId)));
        }
        return tags;
    }

    /**
     * Get all tags from tag container element with provided class
     * @param sectionClass Class name
     * @param type TagType for created Tag objects
     * @return List of all tags from the container
     */
    protected List<Tag> getTags(String sectionClass, TagType type) {
        List<Tag> tags = new ArrayList<>();
        Elements tagContainers = dom.select("ul." + sectionClass).first().getElementsByTag("li");
        for (Element container: tagContainers) {
            String tagName = container.getElementsByClass(TAG_NAME_ELEMENT_CLASS).first().text();
            tags.add(new Tag(type, tagName));
        }
        return tags;
    }

    /**
     * Transforms id of section with tags to TagType
     * @param sectionId HTML id
     * @return TagType
     */
    protected TagType sectionIdToTagType(String sectionId) {
        checkParseCall();
        switch (sectionId) {
            case COPYRIGHT_TAGS_CLASS:
                return TagType.COPYRIGHT;
            case CHARACTER_TAGS_CLASS:
                return TagType.CHARACTER;
            case ARTIST_TAGS_CLASS:
                return TagType.ARTIST;
            case META_TAGS_CLASS:
                return TagType.META;
            case GENERAL_TAGS_CLASS:
                return TagType.GENERAL;
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
        Elements infoRows = dom.getElementById(INFO_SECTION_ID).getElementsByTag("li");
        for (Element row : infoRows) {
            if (row.text().contains("Size")) {
                return row.getElementsByTag("a").first().attr("abs:href");
            }
        }
        return null;
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
        Elements infoRows = dom.getElementById(INFO_SECTION_ID).getElementsByTag("li");
        for (Element row : infoRows) {
            if (row.text().contains("Source")) {
                return row.getElementsByTag("a").first().attr("abs:href");
            }
        }
        return null;
    }
}
