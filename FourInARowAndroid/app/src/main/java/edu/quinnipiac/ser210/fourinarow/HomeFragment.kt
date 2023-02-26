package edu.quinnipiac.ser210.fourinarow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController

/**
 * Back end for the Home Fragment
 * Will simply take in user input, and passing it to the game fragment
 * @author Kevin Rodriguez
 * @date 2/25/2023
 */

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // get reference of the button in the HomeFragment
        val button = view.findViewById<Button>(R.id.next_button)
        // initialize edit text that takes in user input
        val nameInput = view.findViewById<EditText>(R.id.name_editText)
        // ensure that it navigates the user to the game fragment
        button.setOnClickListener {
            // get the name of the user
            val userName = nameInput.text.toString()
            // if user did not enter a name, kindly ask for them to enter a name
            if (userName.equals("")) {
                Toast.makeText(view.context, "Please enter a name", Toast.LENGTH_SHORT).show()
            } else {
                // passing the name of the user  to the game fragment
                val passUserName = HomeFragmentDirections.actionHomeFragmentToGameFragment(userName)
                view.findNavController().navigate(passUserName)
            }
        }
        // return the inflated view
        return view
    }
}