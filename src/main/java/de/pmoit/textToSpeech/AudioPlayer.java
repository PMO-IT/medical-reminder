package de.pmoit.textToSpeech;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import marytts.util.data.audio.StereoAudioInputStream;


public class AudioPlayer extends Thread {

    private AudioInputStream ais;
    private SourceDataLine sdLine;

    private State state = State.PAUSE;
    private boolean stopThread = false;

    public enum State {
        PAUSE, PLAY;
    }

    public enum OutputFormat {
        MONO ( 0 ), STEREO ( 3 ), LEFT_ONLY ( 1 ), RIGHT_ONLY ( 2 );
        private final int value;

        OutputFormat(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public void setAudio(AudioInputStream ais) {
        if (state == State.PLAY) {
            throw new IllegalStateException("Audio is currently playing, cant set state!");
        }
        this.ais = ais;
    }

    /**
     * Stops AudioPlayer and thread
     */
    public void stopThread() {
        if (sdLine != null) {
            sdLine.stop();
        }
        stopThread = true;
    }

    @Override
    public void run() {
        state = State.PLAY;
        ais = new StereoAudioInputStream(ais, OutputFormat.STEREO.getValue());
        AudioFormat audioFormat = ais.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

        try {
            if (sdLine == null) {
                sdLine = ( SourceDataLine ) AudioSystem.getLine(info);
            }
            sdLine.open(audioFormat);
            sdLine.start();

            writeDataToLine();
            if ( ! stopThread) {
                sdLine.drain();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage(), e);
            return;
        } finally {
            sdLine.close();
        }
    }

    private void writeDataToLine() throws IOException {
        int readLine = 0;
        byte[] data = new byte[65532];
        while ( ( readLine != - 1 ) && ( ! stopThread )) {
            readLine = ais.read(data, 0, data.length);
            if (readLine >= 0) {
                sdLine.write(data, 0, readLine);
            }
        }
    }
}
