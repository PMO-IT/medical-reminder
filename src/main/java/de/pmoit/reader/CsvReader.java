package de.pmoit.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import de.pmoit.intervalltimer.Reminder;


public class CsvReader {
    public ArrayList<Reminder> readCsvData(Reader reader) {
        ArrayList<Reminder> reminder = new ArrayList<Reminder>();
        BufferedReader br = null;
        int ignoreHeader = 0;
        String csvSplitSymbol = ",";
        String line = "";
        try {
            br = new BufferedReader(reader);
            while ( ( line = br.readLine() ) != null) {
                if (ignoreHeader > 0) {
                    String[] currentLine = line.split(csvSplitSymbol);
                    ArrayList<String> currentLineValues = new ArrayList<String>(Arrays.asList(currentLine));
                    int hour = Integer.parseInt(currentLineValues.get(0));
                    int minute = Integer.parseInt(currentLineValues.get(1));
                    int second = Integer.parseInt(currentLineValues.get(2));
                    int repeatrate = Integer.parseInt(currentLineValues.get(3));
                    String phrase = currentLineValues.get(4);
                    String voice = currentLineValues.get(5);
                    reminder.add(new Reminder(hour, minute, second, repeatrate, phrase, voice));
                }
                ignoreHeader ++ ;
            }
            return reminder;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
