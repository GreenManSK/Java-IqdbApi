package net.greenmanov.iqdb.parsers;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return tag == tag1.tag &&
                Objects.equals(value, tag1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }
}
