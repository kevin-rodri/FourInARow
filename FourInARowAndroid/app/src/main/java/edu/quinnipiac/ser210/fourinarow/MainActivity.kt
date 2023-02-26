package edu.quinnipiac.ser210.fourinarow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 *  Main Activity that initializes app. That's it.
 * @author Kevin Rodriguez
 * @date 2/25/2023
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}