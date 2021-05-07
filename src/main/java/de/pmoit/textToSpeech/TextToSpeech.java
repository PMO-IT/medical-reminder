package de.pmoit.textToSpeech;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.modules.synthesis.Voice;


public class TextToSpeech {

    private AudioPlayer audioPlayer;
    private MaryInterface marytts;

    public TextToSpeech() {
        try {
            marytts = new LocalMaryInterface();
        } catch (MaryConfigurationException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "ConfigurationException " + e.getMessage(), e);
        }
    }

    public void speak(String text) {
        speak(text, false, true);
    }

    public void speak(String phrase, boolean setAsDaemon, boolean join) {
        stopPlayer();

        try {
            AudioInputStream ais = marytts.generateAudio(phrase);
            setConfiguration(setAsDaemon, ais);
            joinThread(join);
        } catch (SynthesisException e) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Error " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Interrupted ", e);
            audioPlayer.interrupt();
        }
    }

    public Collection<Voice> getAvailableVoices() {
        return Voice.getAvailableVoices();
    }

    public void setVoice(String voice) {
        marytts.setVoice(voice);
    }

    private void stopPlayer() {
        if (audioPlayer != null)
            audioPlayer.stopThread();
    }

    private void setConfiguration(boolean daemon, AudioInputStream ais) {
        audioPlayer = new AudioPlayer();
        audioPlayer.setDaemon(daemon);
        audioPlayer.setAudio(ais);
        audioPlayer.start();
    }

    private void joinThread(boolean join) throws InterruptedException {
        if (join)
            audioPlayer.join();
    }

}
