package net.greenmanov.iqdb.parsers;

/**
 * Image tag
 * @author Lukáš Kurčík
 */
public class Tag {
    protected TagType tag;
    protected String value;

    public Tag(TagType tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    /**
     * Type of tag
     * @return tag type
     */
    public TagType getTag() {
        return tag;
    }

    /**
     * Value of tag
     * @return string
     */
    public String getValue() {
        return value;
    }
}
