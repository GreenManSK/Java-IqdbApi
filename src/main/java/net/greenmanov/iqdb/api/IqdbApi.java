package net.greenmanov.iqdb.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of IIqdbApi using Apache HttpClient
 */
public class IqdbApi implements IIqdbApi {

    protected final static String CONTAINER_ID = "pages";

    // Number of tr rows in valid match HTML div
    protected final static int VALID_ROW_COUNT = 5;

    protected final static int IMAGE_ROW = 1;
    protected final static int SERVICE_ROW = 2;
    protected final static int SIZE_ROW = 3;
    protected final static int SIMILARITY_ROW = 4;

    protected final static Pattern SIMILARITY_REGEX = Pattern.compile("(\\d+)");
    protected final static Pattern WIDTH_REGEX = Pattern.compile("(\\d+)×");
    protected final static Pattern HEIGHT_REGEX = Pattern.compile("×(\\d+)");

    protected HttpClient client;

    /**
     * Create IqdbApi that uses HttpClient without any configuration
     */
    public IqdbApi() {
        this(HttpClientBuilder.create().build());
    }

    /**
     * IqdbApi
     *
     * @param client HttpClient used for requests
     */
    public IqdbApi(HttpClient client) {
        this.client = client;
    }

    /**
     * Search image file on the iqdb and return sorted list of matches
     *
     * @param file    File to upload for matching
     * @param options Form options
     * @return List sorted from most similar to least similar match
     * @throws IOException On problem with getting response from server
     */
    public List<Match> searchFile(File file, Options options) throws IOException {
        MultipartEntityBuilder builder = createEntityBuilder();
        builder.addPart(IIqdbApi.FIELD_FILE, new FileBody(file));
        return search(builder, options);
    }

    /**
     * Search image from url on the iqdb and return sorted list of matches
     *
     * @param url     URL of image
     * @param options Form options
     * @return List sorted from most similar to least similar match
     * @throws IOException On problem with getting response from server
     */
    public List<Match> searchUrl(String url, Options options) throws IOException {
        MultipartEntityBuilder builder = createEntityBuilder();
        builder.addPart(IIqdbApi.FIELD_URL, toStringBody(url));
        return search(builder, options);
    }

    /**
     * Search on the iqdb and return sorted list of matches
     *
     * @param builder MultipartEntityBuilder with file or url for image
     * @param options Form options
     * @return List sorted from most similar to least similar match
     * @throws IOException On problem with getting response from server
     */
    protected List<Match> search(MultipartEntityBuilder builder, Options options) throws IOException {
        for (ServiceType service : options.getServices()) {
            builder.addPart(IIqdbApi.FILED_SERVICE, toStringBody(String.valueOf(service.getId())));
        }
        builder.addPart(IIqdbApi.FILED_IGNORE_COLORS, toStringBody(options.isIgnoreColors() ? "1" : "0"));

        HttpEntity entity = builder.build();
        HttpPost post = new HttpPost(IIqdbApi.URL);
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        String htmlContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        return parseHtml(htmlContent);
    }

    protected List<Match> parseHtml(String html) {
        Document dom = Jsoup.parse(html);
        Element pageContainer = dom.getElementById(CONTAINER_ID);
        Elements matches = pageContainer.getElementsByTag("div");
        ArrayList<Match> result = new ArrayList<>();
        for (Element div : matches) {
            Elements rows = div.getElementsByTag("tr");
            if (rows.size() != VALID_ROW_COUNT || div.getElementsByTag("img").size() == 0) {
                continue;
            }
            int similarity = Integer.valueOf(extract(rows.get(SIMILARITY_ROW).text(), SIMILARITY_REGEX));
            int width = Integer.valueOf(extract(rows.get(SIZE_ROW).text(), WIDTH_REGEX));
            int height = Integer.valueOf(extract(rows.get(SIZE_ROW).text(), HEIGHT_REGEX));
            String url = rows.get(IMAGE_ROW).getElementsByTag("a").first().attr("href");
            ServiceType type = ServiceType.getTypeByUrl(url);
            result.add(new Match(similarity, width, height, type, url));
        }
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    /**
     * Extract part of text matching regex
     *
     * @param text  Text
     * @param regex regex
     * @return Part of string matched or empty string
     */
    protected String extract(String text, Pattern regex) {
        Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * Creates entity builder
     *
     * @return Entity builder with set mode
     */
    protected MultipartEntityBuilder createEntityBuilder() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        return builder;
    }

    /**
     * Create plain text StringBody
     *
     * @param text Text for StringBody
     * @return StringBody object
     */
    protected StringBody toStringBody(String text) {
        return new StringBody(text, ContentType.TEXT_PLAIN);
    }
}
