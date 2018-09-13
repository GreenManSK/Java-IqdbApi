package net.greenmanov.iqdb;

import net.greenmanov.iqdb.api.IqdbApi;
import net.greenmanov.iqdb.api.Match;
import net.greenmanov.iqdb.api.Options;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        IqdbApi api = new IqdbApi();
        List<Match> matches = api.searchUrl("https://www.greenmanov.net/wp-content/uploads/2014/06/Makise.Kurisu.full_.477894.jpg", Options.DEFAULT);
        for (Match match : matches) {
            System.out.println(match.toString());
        }
    }
}
