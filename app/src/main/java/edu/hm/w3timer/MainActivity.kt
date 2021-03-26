package edu.hm.w3timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    // You may use this timer or write your own.
    // If you use the delivered timer extend it because it is not working out of the box.
    // See TODO´s in Timer.
    private lateinit var timer: Timer //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO
    }

    override fun onStart() {
        super.onStart()
        // TODO Think about the lifecycle, maybe use onRestart()
        // See https://developer.android.com/guide/components/activities/activity-lifecycle
    }

    override fun onPause() {
        super.onPause()
        // TODO
    }
}
