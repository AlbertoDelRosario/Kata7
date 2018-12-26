package kata7;

import static java.lang.Math.PI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Watch {
    private final Timer timer;
    private final List<Observer> observers = new ArrayList<>();
    private final double SecondStep = 2 * PI / 60;
    private final double MinuteStep = SecondStep / 60;
    private final double HourStep = MinuteStep / 12;
    Calendar calendar = new GregorianCalendar();

    public Watch() {
        timer = new Timer();
        timer.schedule(timerTask(), 0, 1000);
    }

    public double getHours() {
        return normalize(Math.PI / 2 - ((calendar.get(Calendar.HOUR_OF_DAY)%12)*60*60+calendar.get(Calendar.MINUTE)*60+calendar.get(Calendar.SECOND))*HourStep);
    }

    public double getMinutes() {
        return normalize(Math.PI / 2-(calendar.get(Calendar.MINUTE)*60+calendar.get(Calendar.SECOND))*MinuteStep);
    }

    public double getSeconds() {
        return normalize(Math.PI / 2-calendar.get(Calendar.SECOND)*SecondStep);
    }
    
    

    private TimerTask timerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                step();
                updateObservers();
            }

        };
    }
    
    private void step() {
        calendar = new GregorianCalendar();
    }
    
    public void add(Observer observer) {
        observers.add(observer);
    }
    
    private void updateObservers() {
        for (Observer observer : observers)
            observer.update();
    }

    private double normalize(double v) {
        return v % (2 * PI);
    }

    public interface Observer {

        public void update();

    } 
}
