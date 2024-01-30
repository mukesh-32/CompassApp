package com.example.magnetometerapp

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() , SensorEventListener{

    var sensor:Sensor? =null
    var sensorManager: SensorManager?=null
    lateinit var compassImage:ImageView
    lateinit var rotationTV:TextView
    //to keep track of rotation
    var currentDegree =0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        compassImage = findViewById(R.id.imageView)
        rotationTV=findViewById(R.id.textView)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //** import fun()
        var degree = Math.round(event!!.values[0])
        Log.d(TAG, "onSensorChanged: "+degree)
        rotationTV.text=degree.toString()+"degrees"

        var rotateAnimation=RotateAnimation(currentDegree, (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)

        rotateAnimation.duration=210
        rotateAnimation.fillAfter=true

        compassImage.startAnimation(rotateAnimation)
        currentDegree=(-degree).toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
    //register a listener for the sensor
    override fun onResume(){
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }
    //be sure to unregister
    override fun onPause(){
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}