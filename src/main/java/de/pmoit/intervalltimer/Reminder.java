package de.pmoit.intervalltimer;

import java.util.Calendar;
import java.util.Date;


public class Reminder {
    public final Date firstExecutionTime;
    public final int repeatrate;
    public final String voice;
    public final String phrase;

    public Reminder(int hour, int minute, int second, int repeatrate, String phrase, String voice) {
        Calendar calendar = Calendar.getInstance();
        int hourOfTheDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteOfTheDay = calendar.get(Calendar.MINUTE);
        int secondsOfTheDay = calendar.get(Calendar.SECOND);

        if (hourOfTheDay > hour || ( hourOfTheDay == hour && minuteOfTheDay > minute ) || ( hourOfTheDay == hour
            && minuteOfTheDay == minute && secondsOfTheDay > second )) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);

        this.firstExecutionTime = calendar.getTime();
        this.repeatrate = repeatrate;
        this.phrase = phrase;
        this.voice = voice;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( firstExecutionTime == null ) ? 0 : firstExecutionTime.hashCode() );
        result = prime * result + ( ( phrase == null ) ? 0 : phrase.hashCode() );
        result = prime * result + repeatrate;
        result = prime * result + ( ( voice == null ) ? 0 : voice.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reminder other = ( Reminder ) obj;
        if (firstExecutionTime == null) {
            if (other.firstExecutionTime != null)
                return false;
        } else if ( ! firstExecutionTime.equals(other.firstExecutionTime))
            return false;
        if (phrase == null) {
            if (other.phrase != null)
                return false;
        } else if ( ! phrase.equals(other.phrase))
            return false;
        if (repeatrate != other.repeatrate)
            return false;
        if (voice == null) {
            if (other.voice != null)
                return false;
        } else if ( ! voice.equals(other.voice))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reminder [firstExecutionTime=" + firstExecutionTime + ", repeatrate=" + repeatrate + ", voice=" + voice
            + ", phrase=" + phrase + "]";
    }

}
