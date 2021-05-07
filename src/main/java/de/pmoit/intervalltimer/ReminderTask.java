package de.pmoit.intervalltimer;

import java.util.TimerTask;

import de.pmoit.textToSpeech.TextToSpeech;


/**
 * ReminderTask class. Handels everything reminder related.
 * 
 */
public class ReminderTask extends TimerTask {
    private TextToSpeech tts;
    private String speakResult;

    /**
     * Constructor, sets up the tts engine and which resultphrase should be
     * spoken.
     */
    public ReminderTask(TextToSpeech tts, String speakResult) {
        this.tts = tts;
        this.speakResult = speakResult;
    }

    /**
     * Speaks the set resultphrase.
     */
    @Override
    public void run() {
        tts.speak(speakResult);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tts.speak(speakResult);
    }

    /**
     * In case you want to stop the timer for some reason.
     */
    public void stopTimer() {
        System.exit(0);
    }
}
