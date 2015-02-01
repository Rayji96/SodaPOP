package soytea.sodapop;

/**
 * Created by shaw on 15-01-31.
 */
public class Counter {

    private int count;
    private int multiplier;
    private int passive;

    public Counter() {
        count = 0;
        multiplier = 1;
        passive = 0;
        Thread loop = new Thread(new Passive(this, 1000));
        loop.start();

    }

    public void add1(){
        count+= multiplier;
    }

    /*
    public void add(int n){
        this.count += n;
    }
    */

    public boolean sub(int n){
        if (count < n){
            System.out.println("count="+count+" n="+n);
            return false;
        }
        count -= n;
        return true;
    }

    public void addMultiplier(int m){
        this.multiplier *= m;
    }

    public void addPassive(int p){
        this.passive += p;
    }

    public void passiveTick(){
        this.count += passive;
    }

    public int getCount(){
        return count;
    }

    private class Passive implements Runnable {

        Counter counter;
        long startTime, sleepTime;
        long time;
        Boolean running;


        public Passive(Counter c, long time){

            this.counter = c;
            this.time = time;
            this.running = true;

        }

        @Override
        public void run() {

            while (running) {
                startTime = System.currentTimeMillis();
                sleepTime = time-(System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime > 0)
                        Thread.sleep(sleepTime);
                    else
                        Thread.sleep(1000);
                } catch (Exception e) {}
                counter.passiveTick();
            }
        }


    }

}
