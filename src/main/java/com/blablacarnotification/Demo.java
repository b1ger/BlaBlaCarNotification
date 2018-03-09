package com.blablacarnotification;

import com.blablacarnotification.Parse.DataBuilder;
import com.blablacarnotification.Parse.Parser;

public class Demo {
    public static void main(String[] args) {
        Parser parser = new Parser(args);
        //DataBuilder dataBuilder = new DataBuilder();

        parser.writeToXml(parser.getHtml());
        //dataBuilder.createDomDocument(parser.getXmlFile());
    }
}
