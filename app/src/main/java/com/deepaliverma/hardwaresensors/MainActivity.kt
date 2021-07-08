package com.deepaliverma.hardwaresensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var sensorEventListener: SensorEventListener
    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor
    lateinit var accelSensor: Sensor
    val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager=getSystemService<SensorManager>()
        proxSensor= sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!
        accelSensor= sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        sensorEventListener=object: SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {
                //               if (event!!.values[0] > 0) {
                //show random color everytime  PROXIMITY SENSOR
                //       flProxIndicator.setBackgroundColor(colors[Random.nextInt(6)])
                //    }
                /*  //ACCELEROMETER SENSOR BASED ON GRAVITY
                Log.d("HWSENS", """
            ----
            ax = ${event!!.values[0]}
            ay = ${event!!.values[1]}
            az = ${event!!.values[2]}
            ----
        """.trimIndent())

            }
*/
              val bgColor=Color.rgb(
                  accel2Color(event!!.values[0]),
                         accel2Color(event!!.values[1]),
                  accel2Color(event!!.values[2])
              )
flAccelIndicator.setBackgroundColor(bgColor)
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

        }



       /* if(sensorManager==null){
            Toast.makeText(this,"Could not get 0Sensors",Toast.LENGTH_SHORT).show()
            finish()
        }
        else{
           val sensors= sensorManager.getSensorList(Sensor.TYPE_ALL)
            sensors.forEach{
                Log.d("HWSENS","""
                ${it.name} | ${it.stringType} | ${it.vendor}
                """.trimIndent()
                    )
            }
        }*/

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener,proxSensor,1000*1000
        )
    }

    override fun onPause() {
        super.onPause()
       sensorManager.unregisterListener(sensorEventListener)
       // super.onPause()
    }
    private  fun accel2Color(accel:Float):Int=(((accel+12)/24)*255).roundToInt()
}