package soytea.sodapop;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shaw on 15-01-30.
 */
public class MotionHandler extends Activity implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 1500;
    //a TextView
    private TextView tv;
    private TextView shakes;
    //the Sensor Manager
    private SensorManager sManager;

    private Counter counter;

    private long lastUpdate;
    private float last_x, last_y, last_z;

/*
    PagerAdapter pagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        pagerAdapter =
                new PagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }
    */

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.counter = new Counter();

        //get the TextView from the layout file
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("MotionHandler created");

        shakes = (TextView) findViewById(R.id.shakes);

        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }


    public void addPassive (View view){
        if (counter.sub(100)) {
            counter.addPassive(1);
        }
    }

    public void addMultiply (View view){
        if (counter.sub(200)) {
            counter.addMultiplier(2);
        }
    }

    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {

        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        /*
        //else it will output the Roll, Pitch and Yawn values
        tv.setText("Orientation X (Roll) :"+ Float.toString(event.values[2]) +"\n"+
                "Orientation Y (Pitch) :"+ Float.toString(event.values[1]) +"\n"+
                "Orientation Z (Yaw) :"+ Float.toString(event.values[0]));
                */

        //if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                shakes.setText(""+counter.getCount());

                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = event.values[2];
                float y = event.values[1];
                float z = event.values[0];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    counter.add1();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        //}
    }
}
