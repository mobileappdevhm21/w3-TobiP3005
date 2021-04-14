package edu.hm.w3timer

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import edu.hm.w3timer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


/** onSaveInstanceState Bundle Keys **/
const val KEY_TIMER_SECONDS_INITIAL = "timer_seconds_initial_key"
const val KEY_TIMER_SECONDS = "timer_seconds_key"
const val KEY_TIMER_STATE = "timer_state_key"

class MainActivity : AppCompatActivity(), Timer.TimerListener, LifecycleObserver {

    private lateinit var timer: Timer

    // Contains all the views
    private lateinit var binding: ActivityMainBinding
    private lateinit var vibe: Vibrator


    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate Called")
        super.onCreate(savedInstanceState)
        vibe = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fab_stop.isEnabled = false
        fab_pause.isEnabled = false
        // Setup Timer, passing in the lifecycle
        timer = Timer(this.lifecycle)
        timer.setTimerListener(this)

        // Get saved information values if there is a savedInstanceState bundle
        if (savedInstanceState != null) {
            timer.setInitialSeconds(savedInstanceState.getInt(KEY_TIMER_SECONDS_INITIAL, 10 * 60))
            timer.setRemainingSeconds(savedInstanceState.getInt(KEY_TIMER_SECONDS, 10 * 60))
            timer.setState(
                Timer.TimerState.valueOf(
                    savedInstanceState.getString(
                        KEY_TIMER_STATE,
                        "Stopped"
                    )
                )
            )
        }

        binding.buttonSetTimer.setOnClickListener {
            openSetTimerDialog()
        }

        binding.fabStart.setOnClickListener {
            timer.start()
        }
        binding.fabPause.setOnClickListener {
            timer.pause()
        }
        binding.fabStop.setOnClickListener {
            timer.stop()
        }
    }

    /**
     * Method to open dialog to set timer value
     */
    private fun openSetTimerDialog(){
        val d = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_number_picker_timer, null)
        d.setTitle("Set Timer")
        d.setMessage("Select Minutes and Seconds")
        d.setView(dialogView)
        val numberPickerMin = dialogView.findViewById<NumberPicker>(R.id.dialog_number_picker_min)
        val numberPickerSec = dialogView.findViewById<NumberPicker>(R.id.dialog_number_picker_sec)
        numberPickerMin.maxValue = 60
        numberPickerSec.maxValue = 60
        numberPickerMin.minValue = 0
        numberPickerSec.minValue = 0
        numberPickerMin.value = 1
        numberPickerMin.wrapSelectorWheel = false
        numberPickerSec.wrapSelectorWheel = false
        numberPickerMin.setOnValueChangedListener { numberPicker, i, i1 -> println("onValueChange: ") }
        numberPickerSec.setOnValueChangedListener { numberPicker, i, i1 -> println("onValueChange: ") }
        d.setPositiveButton("Done") { dialogInterface, i ->
            timer.stop()
            timer.setInitialSeconds(numberPickerMin.value * 60 + numberPickerSec.value)
            println("onClick: " + numberPickerMin.value)
        }
        d.setNegativeButton("Cancel") { dialogInterface, i -> }
        val alertDialog = d.create()
        alertDialog.show()
    }

    /**
     * Called when the user navigates away from the app but might come back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_TIMER_SECONDS_INITIAL, timer.getInitialSeconds())
        outState.putInt(KEY_TIMER_SECONDS, timer.getRemainingSeconds())
        outState.putString(KEY_TIMER_STATE, timer.getState().toString())

        Timber.i("onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun updated(remainingTime: Int) {
        val minutesUntilFinished = remainingTime  / 60
        val secondsInMinuteUntilFinished = remainingTime - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.timer = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        binding.progressBar.max = timer.getInitialSeconds()
        binding.progressBar.progress = (timer.getInitialSeconds() - remainingTime).toInt()

        if (remainingTime == 0){
            vibe.vibrate(VibrationEffect.createOneShot(1000, 99));
        }
    }

    override fun updateButtons(timerState: Timer.TimerState) {
        when (timerState) {
            Timer.TimerState.Running -> {
                fab_start.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            Timer.TimerState.Stopped -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
            Timer.TimerState.Paused -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
        }
    }

    /** Lifecycle Methods **/
    override fun onStart() {
        super.onStart()
        Timber.i("onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }

}
