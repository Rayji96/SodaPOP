package soytea.sodapop;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shaw on 15-01-31.
 */
public class Counter {

    private int count;
    private int multiplier;
    private int passive;

    Timer timer;

    public Counter() {
        count = 0;
        multiplier = 1;
        passive = 0;

        timer = new Timer();
        timer.schedule(new TimerHandler(this), 1000);
    }

    public void add1(){
        count+= multiplier;
    }

    public void add(int n){
        this.count += n;
    }

    public void addMultiplier(int m){
        this.multiplier *= m;
    }

    public void addPassive(int p){
        this.passive += p;
    }

    public int getCount(){
        return count;
    }

    private class TimerHandler extends TimerTask{

        Counter counter;

        public TimerHandler(Counter c) {

            this.counter = c;

        }

        @Override
        public void run() {
            counter.count += counter.passive;
        }
    }

}
