package soytea.sodapop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by shaw on 15-01-31.
 */
public class ShopActivity extends Activity {

    private Counter counter;

    private Button bPassive, bMultiply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("shopacc created");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shop);

        bPassive = (Button) findViewById(R.id.passive);
        bMultiply = (Button) findViewById(R.id.multiply);

        this.counter = Singleton.getInstance().counter;

        update();

//       Thread loop = new Thread(new UpdateThread(this, 400));
//       loop.start();

    }

    private void update() {

        bPassive.setText("Enslave Minions (+1 shake / sec)\n Cost: " + Singleton.getInstance().pricePassive);
        if (counter.getCount() >= Singleton.getInstance().pricePassive){
            bPassive.setEnabled(true);
        } else {
            bPassive.setEnabled(false);

        }
        bMultiply.setText("Buy Efficient Shaking (x2 per shake) \n Cost: "+Singleton.getInstance().priceMultiply);
        if (counter.getCount() >= Singleton.getInstance().priceMultiply){
            bMultiply.setEnabled(true);
        } else {

            bMultiply.setEnabled(false);
        }
    }

    public void addPassive (View view){
        if (counter.sub(Singleton.getInstance().pricePassive)) {
            counter.addPassive(1);
            Singleton.getInstance().pricePassive += 20;
        }
        update();
    }

    public void addMultiply (View view){
        if (counter.sub(Singleton.getInstance().priceMultiply)) {
            counter.addMultiplier(2);
            Singleton.getInstance().priceMultiply *= 2;
        }
        update();
    }




    private class UpdateThread implements Runnable {

        ShopActivity shop;
        long startTime, sleepTime;
        long time;
        Boolean running;


        public UpdateThread(ShopActivity s, long time){

            this.shop = s;
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
                shop.update();
            }
        }


    }
}
