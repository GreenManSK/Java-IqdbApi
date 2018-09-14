# Java Iqdb API
Simple library for matching images using https://iqdb.org/ from Java

## Usage

### API usage
```Java
IIqdbApi api = new IqdbApi();
List<Match> matches = api.searchUrl(
        "https://raikou1.donmai.us/d3/4e/__kousaka_tamaki_to_heart_2_drawn_by_kyougoku_shin__d34e4cf0a437a5d65f8e82b7bcd02606.jpg",
         Options.DEFAULT
);
for (Match match: matches) {
    System.out.println(match.toString());
}
```

Custom [HttpClient](https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/) can be provided to IdqbApi in
 constructor. \
Options object specify search options for form from the iqdb such as if colors should be ignored and which services 
should be searched.

### Parsers
This library provide parsers for services supported on the iqdb and is able to get tags of each image, link to the 
image file and possible source of the image. All parsers can be found in ``net.greenmanov.iqdb.parsers.impl``. You 
can use DynamicParser that can automatically detect which site URL was provided.

```Java
IParser parser = new DynamicParser();
parser.parse("https://gelbooru.com/index.php?page=post&s=view&id=101569");
System.out.println(parser.getImage());
System.out.println(parser.getSource());
for (Tag tag: parser.getTags()) {
    System.out.println(tag.getTag() + " - " + tag.getValue());
}
```

## TODO
* zerochan.net ability to log in for more tags and images
* theanimegallery.com parser when the site starts working