package br.com.slmm.meuacelerometro;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 *  Projeto de teste de sensores no android.
 *
 *  by Slmm
 *  24/3/20
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor lightSensor;

    private TextView textSensor;
    private TextView textLight;
    private TextView textSensorA;
    private TextView textX;
    private TextView textY;
    private TextView textZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSensor = (TextView)findViewById(R.id.txtSensor);
        textLight = (TextView)findViewById(R.id.txtLight);
        textSensorA = (TextView)findViewById(R.id.txtSensorA);
        textX = (TextView)findViewById(R.id.txtXCoord);
        textY = (TextView)findViewById(R.id.txtYCoord);
        textZ = (TextView)findViewById(R.id.txtZCoord);

        /*Ligando os sensores e escutando sua resposta*/
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        setListener();

    }

    /**
     *  função para registrar os listener dos sensores
     *  acelerometro
     *  luz
     *
     */
    private void setListener(){
        // adiciona o listener para os sensores
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        if (lightSensor == null){
            textSensor.setText("Não há sensor de luz");
        }
        else{
            textSensor.setText(lightSensor.getName());
            sensorManager.registerListener(lightSensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            textSensorA.setText(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).getName());
        }
    }

    private SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT){
                textLight.setText("Valor :" + String.valueOf(event.values[0]));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    @Override
    protected void onResume(){
        super.onResume();
        setListener();
    }

    @Override
    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            textX.setText("X = " + x);
            textY.setText("Y = " + y);
            textZ.setText("Z = " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
