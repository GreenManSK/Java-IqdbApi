package net.greenmanov.iqdb.api;

/**
 * Represents types of services supported by the iqdb
 */
public enum ServiceType {
    DANBOORU(1, "danbooru.donmai.us"),
    KONACHAN(2, "konachan.com"),
    YANDE_RE(3, "yande.re"),
    GELBOORU(4, "gelbooru.com"),
    SANKAKU_CHANNEL(5, "chan.sankakucomplex.com"),
    E_SHUUSHUU(6, "e-shuushuu.net"),
    THE_ANIME_GALLERY(10, "theanimegallery.com"),
    ZEROCHAN(11, "zerochan.net"),
    ANIME_PICTURES(13, "anime-pictures.net");

    private int id;
    private String domain;

    ServiceType(int id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    /**
     * Get ID used by the iqdb for service
     *
     * @return id form matching form
     */
    public int getId() {
        return id;
    }

    /**
     * Get service domain
     * @return domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Detects service type by url
     *
     * @param url Image url
     * @return Matching service type
     * @throws IllegalArgumentException If url do not match any type
     */
    public static ServiceType getTypeByUrl(String url) {
        for (ServiceType serviceType : values()) {
            if (url.contains(serviceType.domain)) {
                return serviceType;
            }
        }
        throw new IllegalArgumentException("URl dose not match any type");
    }
}
