package de.pmoit.reader;

import java.io.File;


public class CsvFile {
    public static File getCurrentDataFilePath() {
        String userdir = System.getProperty("user.dir");
        return new File(userdir + "/" + "resources/data.csv");
    }
}
