package net.greenmanov.iqdb.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Parser for sites based on https://github.com/moebooru/moebooru
 *
 * @author Lukáš Kurčík
 */
abstract public class AMoebooruParser extends AJsoupParseer {

    protected static final String TAG_CONTAINER_SELECTOR = "#tag-sidebar";

    protected static final String STAST_CONTAINER_ID = "stats";
    protected static final String IMAGE_LINK_ORIGINAL_CLASS = "original-file-unchanged";
    protected static final String IMAGE_LINK_CHANGED_CLASS = "original-file-changed";

    /**
     * Get url of the image file
     *
     * @return url
     * @throws IllegalStateException If parse was not called before
     */
    @Override
    public String getImage() throws IllegalStateException {
        checkParseCall();
        Element link = dom.getElementsByClass(IMAGE_LINK_ORIGINAL_CLASS).first();
        if (link == null) {
            link = dom.getElementsByClass(IMAGE_LINK_CHANGED_CLASS).first();
        }
        return link.attr("abs:href");
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
        Elements infoRows = dom.getElementById(STAST_CONTAINER_ID).getElementsByTag("li");
        for (Element row : infoRows) {
            if (row.text().contains("Source")) {
                return row.getElementsByTag("a").first().attr("abs:href");
            }
        }
        return null;
    }
}
