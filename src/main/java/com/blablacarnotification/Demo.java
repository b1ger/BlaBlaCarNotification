package com.blablacarnotification;

public class Demo {
    public static void main(String[] args) {
        String url = "https://www.blablacar.com.ua/ride-sharing/kijiv/kaniv/?fn=Kyiv%2C+%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0&fc=50.4501%7C30.5234&fcc=UA&fp=0&tn=Kaniv%2C+%D0%A7%D0%B5%D1%80%D0%BA%D0%B0%D1%81%D1%8C%D0%BA%D0%B0+%D0%BE%D0%B1%D0%BB%D0%B0%D1%81%D1%82%D1%8C%2C+%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0&tc=49.751033%7C31.4697&tcc=UA&tp=0&db=02%2F03%2F2018&sort=trip_date&order=asc&limit=10&page=1&v=default&s_uuid=7fd691da-9261-4511-8c88-dfdfb1bafc3d";
        Parser parser = new Parser();

        parser.createDom(parser.getHtml(url));
    }
}
