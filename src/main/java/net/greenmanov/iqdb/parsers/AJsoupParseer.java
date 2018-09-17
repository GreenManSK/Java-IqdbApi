package net.greenmanov.iqdb.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Abstract implementation of IParser that downlaods content of the url and prepare Jsoup to parse html of this page
 *
 * @author Lukáš Kurčík
 */
abstract public class AJsoupParseer implements IParser {
    protected String url;
    protected Document dom;

    public AJsoupParseer() {
    }

    /**
     * Parse content of the url and saves information into this object.
     * Have to be called before any getter
     *
     * @param url URL to the image page
     * @throws IOException If there is problem with getting content of the image page
     */
    @Override
    public void parse(String url) throws IOException {
        dom = Jsoup.connect(url).get();
    }

    /**
     * Check if parse was called and throw exception if not
     * @throws IllegalStateException If there is problem with getting content of the image page
     */
    protected void checkParseCall() throws IllegalStateException {
        if (dom == null) {
            throw new IllegalStateException("Need to call parse() function on this object before using getters");
        }
    }

    /**
     * Call function on first element of this list if one exists
     * @param elements List of elements
     * @param callback Callback function
     */
    protected void onFirst(Elements elements, FirstCallback callback) {
        if (elements.isEmpty())
            return;
        callback.call(elements.first());
    }

    @FunctionalInterface
    protected interface FirstCallback {
        void call(Element element);
    }
}
