package edu.hm.w3timer

import android.os.Handler

/**
 * A timer class for creating, starting and pausing a timer which is counting down
 * Also comes with an interface via users are notified about the status of the timer.
 */
class Timer() {

    private var seconds = 0
    private var initialSeconds = 0

    private lateinit var listener: TimerListener

    private var handler = Handler()
    private lateinit var runnable: Runnable

    // TODO the activity has to implement the interface
    interface TimerListener{
        fun updated(seconds : Int)
        //TODO feel free to extend more methods
    }

    /**
     * @param listener to notify to.
     */
    fun setTimerListener(listener : TimerListener){
        this.listener = listener
    }

    /**
     * Starting the timer. Notifying all listeners about [seconds].
     */
    fun start() {
        runnable = Runnable {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
            seconds--
            // TODO Log at least $seconds
            listener.updated(seconds)
            if(seconds > 0){
                handler.postDelayed(runnable, 1000)
            }
        }

        handler.postDelayed(runnable, 1000)
    }

    /**
     * @param secondsLeft to start from
     */
    fun setInitialSeconds(secondsLeft : Int){
        this.seconds = secondsLeft
        this.initialSeconds = secondsLeft
    }

    /**
     * Return initial seconds timer started from.
     */
    fun getInitialSeconds() : Int{
        return initialSeconds
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }

    // TODO Any more methods required?
}