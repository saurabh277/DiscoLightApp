package com.example.hardwarelistener2

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity(),SensorEventListener {

    lateinit var sensorManager: android.hardware.SensorManager
    lateinit var proxSensor: Sensor
    lateinit var accelSensor:Sensor

    val colors= arrayOf(Color.BLUE,Color.CYAN,Color.RED,Color.YELLOW,Color.DKGRAY,Color.MAGENTA)

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        /*if(event!!.values[0]>0){
            flProxIndicator.setBackgroundColor(colors[Random.nextInt(6)])
        }
     Log.d("HWSENS","""
         -----
         ax = ${event!!.values[0]}
         ay = ${event!!.values[1]}
         az = ${event!!.values[2]}
         -----
         
     """.trimIndent())*/
     val bgcolor =Color.rgb(
         accel2Color(event!!.values[0]),
         accel2Color(event!!.values[1]),
         accel2Color(event!!.values[2])
     )
        flAccelIndicator.setBackgroundColor(bgcolor)
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as android.hardware.SensorManager
        //A proximity sensor is a sensor able to detect the presence of nearby objects without any physical contact
       // proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, accelSensor, 1000 * 1000

        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()

    }
    private fun accel2Color(accel:Float):Int=  (((accel + 12) / 24) * 255).roundToInt()

}

