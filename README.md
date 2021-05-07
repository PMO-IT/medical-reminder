# medical-reminder
A Java based text-to-speech reminder to take your medicine.

# Description
Both my grandparents live in their own home and need to take their medication regularly. As they sometimes forget to take their medication we often have to remind them to take it. Due to the pandemic we couldnt visit them regularly and hence could not remind them to take their medication in person. Also both dont own a smartphone. To solve this problem I wrote a medication reminder. The programm uses speech to text to remind them at specific times each day. To make things more clear I implemented the option to use different voices depending who is the target person. 


## Config
The CSV-config file

This "data.csv" file is suppose to be in the user-directory (user.dir) / resources

Example
```
Hour,Minute,Second,Repeatrate,Phrase,Voice
14,00,00,10000,notification-msg,bits3-hsmm
```
If the set time has already passed, the timer will start on the following day.

The data.csv has to be in /resources folder not in the pi home folder.

## Service script for Raspberry Pi
Make sure Java is installed, if not do so.

```
curl -sSL https://pi4j.com/install | sudo bash
```

Compile as jar and set the paths in the script.
This script will start the programm during Raspberry boot process

```
[Unit]
Description=MedicationReminder service mit systemd

[Service]
ExecStart=/usr/bin/java -jar /home/pi/reminder/MedicationReminder.jar
Restart=always

[Install]
WantedBy=multi-user.target
```

## Getting all available voices
If you want to get all available voices add the following code in the Main-method and call it after the try{.

```
private static void getAvailableVoices() {
	System.out.println("Available voices");
	TextToSpeech tts = new TextToSpeech();
	Collection<Voice> availableVoices = tts.getAvailableVoices();
	for (Voice voice : availableVoices) {
		System.out.println(voice.getName());
	}
}
```

Copy the script
```
sudo cp /home/pi/reminder/MedicationReminder.service /etc/systemd/system/MedicationReminder.service
```

Enable the script on boot
```
sudo systemctl enable MedicationReminder
```

Check status
```
systemctl status MedicationReminder
```

## Disclaimer
I developed this programm as an open source project, there are no warranties whatsoever, if you use this programm it´s at your own risk.


