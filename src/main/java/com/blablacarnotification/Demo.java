package com.blablacarnotification;

public class Demo {
    public static void main(String[] args) {
        String url = buildUrl(args);
        Parser parser = new Parser();

        parser.writeToXml(parser.getHtml(url));
        //"https://www.blablacar.com.ua/ride-sharing/kijiv/kaniv/?db=5%2F03%2F2018"

    }

    public static String buildUrl(String[] args) {
        String url = "https://www.blablacar.com.ua/ride-sharing/" +
                args[0] + "/" +
                args[1] + "/" +
                "?db=" + args[2].replaceAll("/", "%2F");

        return url;
    }
}
