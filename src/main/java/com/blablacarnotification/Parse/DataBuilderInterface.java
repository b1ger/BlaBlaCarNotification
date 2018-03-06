package com.blablacarnotification.Parse;

import org.w3c.dom.Document;

import java.util.List;

public interface DataBuilderInterface {

    boolean createDomDocument(String pathToFile);

    List createModels();

    boolean saveModels(List models);
}
