package edu.quinnipiac.ser210.fourinarow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

/**
 * Back end for the Game Fragment
 * This is backend for the four in a row game
 * @author Kevin Rodriguez
 * @date 2/25/2023
 * NOTE: I couldn't figure out how to change the background color of buttons so,
 * so here are icons representations to keep in mind:
 * 1. open_spots = Empty spot on the board (smiley face icon)
 * 2. player_icon = Red has taken that spot (baby face icon)
 * 3. blue = Blue has taken that spot (android symbol icon)
 * Hope this helps :)
 * Thank you professor for suggesting the use of array's to store the id of buttons and buttons
 */

class GameFragment : Fragment(), View.OnClickListener {
    // instantiate the Four in a row class that contains the backend for the four in a row game
    private val FIR_board = FourInARow()
    // array that contains the buttons that represent the board using their id's
    private val boardButtons = arrayOf(
        R.id.button_0, R.id.button_1, R.id.button_2,
        R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
        R.id.button_8, R.id.button_9,
        R.id.button_10, R.id.button_11, R.id.button_12, R.id.button_13, R.id.button_14,
        R.id.button_15, R.id.button_16, R.id.button_17, R.id.button_18, R.id.button_19,
        R.id.button_20, R.id.button_21, R.id.button_22, R.id.button_23, R.id.button_24,
        R.id.button_25, R.id.button_26, R.id.button_27, R.id.button_28, R.id.button_29,
        R.id.button_30, R.id.button_31, R.id.button_32, R.id.button_33,
        R.id.button_34, R.id.button_35
    )
    // array list of all the buttons on the board
    private val buttons = arrayListOf<Button>()
    // string that will display the user's name
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        // Textview that will display the user's name and the AI's name at the top of the board
        val displayName = view.findViewById<TextView>(R.id.displayName_textView)
        // get the name passed in from the home fragment
        userName = GameFragmentArgs.fromBundle(requireArguments()).name
        // set the top text to user's name and AI
        displayName.text = " $userName vs AI"
        // adding buttons to the array of buttons and set onclickListener's for each button in the board
        for (index in 0 until boardButtons.size) {
            val gameButton = view.findViewById<Button>(boardButtons[index])
            buttons.add(gameButton)
            gameButton.setOnClickListener(this)
        }
        // initialize the reset button and set an onclick listener method for it
        view.findViewById<Button>(R.id.reset_button).setOnClickListener(this)
        return view
    }


    // logic for when the reset button and user clicks on button on the board
    override fun onClick(v: View?) {
        when (v!!.id) {
            // if reset button's clicked, call resetButtons function which gets
            // rid of the backgrounds for buttons, clear the board,
            // and allow the user to replay the game
            R.id.reset_button -> {
                // get the game status textview and reset button
                val gameStatus: TextView = requireView().findViewById(R.id.gamestatus_textView)
                val resetButton: Button = requireView().findViewById(R.id.reset_button)
                gameStatus.text = "Game Status: Playing"
                // set each button back to open spots
                for (index in 0 until buttons.size) {
                    buttons[index].setBackgroundResource(R.drawable.open_spot)
                    buttons[index].isClickable = true
                }
                FIR_board.clearBoard()
                resetButton.isEnabled = false
            }
            else -> {
                // get the game status textview and reset button
                val gameStatus: TextView = requireView().findViewById(R.id.gamestatus_textView)
                val resetButton: Button = requireView().findViewById(R.id.reset_button)
                Toast.makeText(context, "$userName 's turn", Toast.LENGTH_SHORT).show()
                // looking to find the button that was clicked and setting it to red
                for (index in 0 until buttons.size) {
                    if (buttons[index] == requireView().findViewById(v.id)) {
                        buttons[index].setBackgroundResource(R.drawable.player_icon)
                        FIR_board.setMove(GameConstants.RED, index)
                        buttons[index].isClickable = false
                    }
                }

                Toast.makeText(context, "AI's turn", Toast.LENGTH_SHORT).show()
                // get the computer's move and set it to the appropriate spot
                val computer = FIR_board.computerMove
                val buttonComputer: Button = buttons[computer]
                println()
                buttonComputer.setBackgroundResource(R.drawable.blue)
                buttonComputer.isClickable = false
                // logic for checking winner
                val winner = FIR_board.checkForWinner()
                // blue wins
                if (winner == GameConstants.BLUE_WON) {
                    gameStatus.text = "Game Status: AI Wins!"
                    // red wins
                } else if (winner == GameConstants.RED_WON) {
                    gameStatus.text = "Game Status: $userName Wins!"
                    // game ends in a tie
                } else if (winner == GameConstants.TIE) {
                    gameStatus.text = "Game Status: Tie Wins!"
                }

                // disables all board button's if there's a winner/tie and enables the reset button
                if (winner != GameConstants.PLAYING) {
                    resetButton.isEnabled = true
                    for (index in 0 until buttons.size) {
                        buttons[index].isClickable = false
                    }
                }
            }
        }
    }


}