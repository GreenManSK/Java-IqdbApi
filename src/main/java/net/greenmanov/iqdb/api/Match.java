package net.greenmanov.iqdb.api;

/**
 * Match found by the iqdb
 * Matches are comparable and uses similarity to compare themselves
 */
public class Match implements Comparable<Match> {
    protected int similarity;
    protected int width;
    protected int height;
    protected ServiceType type;
    protected String url;

    public Match(int similarity, int width, int height, ServiceType type, String url) {
        this.similarity = similarity;
        this.width = width;
        this.height = height;
        this.type = type;
        this.url = url;
    }

    /**
     * Similarity of this image compared to original image
     * @return integer in range 0 to 100
     */
    public int getSimilarity() {
        return similarity;
    }

    /**
     * Get width of the match
     * @return width in px
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get height of the match
     * @return height in px
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get service hosting this image
     * @return ServiceType
     */
    public ServiceType getType() {
        return type;
    }

    /**
     * Get url of the image page on the service.
     * Warning: It is not direct url of image file!
     * @return url of the image page
     */
    public String getUrl() {
        return url;
    }

    /**
     * Compares two matches using similarity
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    public int compareTo(Match o) {
        return similarity - o.similarity;
    }

    @Override
    public String toString() {
        return "Match{" +
                "similarity=" + similarity +
                ", width=" + width +
                ", height=" + height +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }
}
