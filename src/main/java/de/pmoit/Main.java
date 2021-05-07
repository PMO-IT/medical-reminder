package de.pmoit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;

import de.pmoit.intervalltimer.Reminder;
import de.pmoit.intervalltimer.ReminderTask;
import de.pmoit.reader.CsvFile;
import de.pmoit.reader.CsvReader;
import de.pmoit.textToSpeech.TextToSpeech;


/**
 * @author Pascal Moll
 * @version 1.0
 * 
 * The Main Class. The data.csv path gets read and its content analysed. After
 * the tts engine gets loaded and the reminder set up.
 *
 */
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        File currentDataFilePath = CsvFile.getCurrentDataFilePath();
        Reader fileReader = new InputStreamReader(new FileInputStream(currentDataFilePath), "ISO-8859-1");
        CsvReader csvReader = new CsvReader();

        ArrayList<Reminder> reminders = csvReader.readCsvData(fileReader);
        Timer timer = new Timer();
        for (Reminder reminder : reminders) {
            TextToSpeech tts = new TextToSpeech();
            tts.setVoice(reminder.voice);
            timer.scheduleAtFixedRate(new ReminderTask(tts, reminder.phrase), reminder.firstExecutionTime,
                reminder.repeatrate);
        }
    }
}
