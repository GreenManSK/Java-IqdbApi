# Java Iqdb Api
Simple library for matching images using https://iqdb.org/ from Java

## Usage
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
Options object specify search options for form from iqdb such as if colors should be ignored and which services 
should be searched.

##TODO
* Get info about image from services 