package edu.hm.w3timer

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/**
 * A timer class for creating, starting and pausing a timer which is counting down
 * Also comes with an interface via users are notified about the status of the timer.
 */
class Timer(lifecycle: Lifecycle) : LifecycleObserver{

    enum class TimerState{
        Running, Paused, EventPaused, Stopped
    }

    private var seconds = 10*60
    private var initialSeconds = 10*60
    private var timerState = TimerState.Stopped

    private lateinit var listener: TimerListener

    private var handler = Handler()
    private lateinit var runnable: Runnable

    interface TimerListener{
        fun updated(remainingTime : Int)
        fun updateButtons(timerState : TimerState)
    }

    init {
        lifecycle.addObserver(this)
    }

    /**
     * @param listener to notify to.
     */
    fun setTimerListener(listener : TimerListener){
        this.listener = listener
    }

    /**
     * @param secondsLeft to start from
     */
    fun setInitialSeconds(secondsLeft : Int){
        this.seconds = secondsLeft
        this.initialSeconds = secondsLeft
        listener.updated(secondsLeft)
    }

    /**
     * Return initial seconds timer started from.
     */
    fun getInitialSeconds() : Int{
        return initialSeconds
    }

    fun setRemainingSeconds(secondsLeft : Int){
        this.seconds = secondsLeft
        listener.updated(secondsLeft)
    }

    fun getRemainingSeconds() : Int{
        return seconds
    }

    fun setState(state : TimerState){
        this.timerState = state
    }

    fun getState() : TimerState{
        return timerState
    }

    /**
     * Starting the timer. Notifying all listeners about [seconds].
     */
    fun start() {
        Timber.i("Timer started")
        timerState = TimerState.Running
        listener.updateButtons(timerState)
            runnable = Runnable {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)

                Timber.i("Timer: "+ seconds)

                if(seconds > 0){
                    seconds--
                    listener.updated(seconds)
                    handler.postDelayed(runnable, 1000)
                }else{
                    stop()
                }
            }
            handler.postDelayed(runnable, 1000)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun resume(){
        if (timerState == TimerState.EventPaused) {
            start()
            Timber.i("Timer resumed on event")
        }
    }

    fun pause() {
        handler.removeCallbacks(runnable)
        timerState = TimerState.Paused
        listener.updateButtons(timerState)
        Timber.i("Timer paused")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun eventPause() {
        if (timerState != TimerState.Stopped){
            handler.removeCallbacks(runnable)
            timerState = TimerState.EventPaused
            listener.updateButtons(timerState)
            Timber.i("Timer paused by event")
        }
    }

    fun stop() {
        if (timerState != TimerState.Stopped){
            handler.removeCallbacks(runnable)
            timerState = TimerState.Stopped
            listener.updateButtons(timerState)
            this.seconds = getInitialSeconds()
            listener.updated(seconds)
            Timber.i("Timer stopped and reset")
        }
    }
}