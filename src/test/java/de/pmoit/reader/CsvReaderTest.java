package de.pmoit.reader;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.pmoit.intervalltimer.Reminder;


public class CsvReaderTest {
    private CsvReader cut = new CsvReader();

    @Test
    public void wellformedFileIsReadCorrectly() {
        String headline = "Hour,Minute,Second,Repeatrate,Phrase,Voice";
        String firstLine = "20,37,00,10000,Oma Aspirin einnehmen,bits3-hsmm";
        String csvContent = headline + "\n" + firstLine;

        List<Reminder> result = cut.readCsvData(new StringReader(csvContent));
        Reminder expectedReminder = new Reminder(20, 37, 00, 10000, "Oma Aspirin einnehmen", "bits3-hsmm");

        assertEquals(asList(expectedReminder), result);
    }

    @Test
    public void wellformedFileWith2EntriesIsReadCorrectly() {
        String headline = "Hour,Minute,Second,Repeatrate,Phrase,Voice";
        String firstLine = "20,37,00,10000,Oma Aspirin einnehmen,bits3-hsmm";
        String secondLine = "21,56,00,15000,Opa Vitamine einnehmen,bits1-hsmm";
        String csvContent = headline + "\n" + firstLine + "\n" + secondLine;

        List<Reminder> result = cut.readCsvData(new StringReader(csvContent));
        Reminder expectedReminderLine1 = new Reminder(20, 37, 00, 10000, "Oma Aspirin einnehmen", "bits3-hsmm");
        Reminder expectedReminderLine2 = new Reminder(21, 56, 00, 15000, "Opa Vitamine einnehmen", "bits1-hsmm");

        assertEquals(asList(expectedReminderLine1, expectedReminderLine2), result);
    }
}
