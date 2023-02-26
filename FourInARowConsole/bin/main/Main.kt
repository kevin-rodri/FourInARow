
/**
 * Main class that runs the FourInARow game
 * @author relkharboutly
 *  Modified by: Kevin Rodriguez
 * @date 2/2/2023
 */


val FIR_board = FourInARow()

/** The entry main method (the program starts here)  */
fun main() {
    var currentState: Int = GameConstants.PLAYING
    var userInput = ""
    //game loop
    do {
        FIR_board.printBoard()
        /** TODO implement the game loop
         * 1- accept user move
         * 2- call getComputerMove
         * 3- Check for winner
         * 4- Print game end messages in case of Win , Lose or Tie !
         */

        // welcomes the user and takes a move from user
        println("Welcome to four in a row :), please enter a move from 0-35")
        userInput = readln()
        // if user pressess q, then stop
        if (userInput == "q") {
            break
        }
        val userMove = Integer.parseInt(userInput)
        // verify if the user is allowed to make the move. If so, player is allowed to set that move according so.
        if (FIR_board.isNotAvailable(userMove)){
            println("Please enter a move from 0 to 35.")
        } else {
            FIR_board.setMove(GameConstants.RED, userMove)
        }
        // have the AI make a move (set's the move in the helper function created for the AI
        FIR_board.computerMove
        // now verify if there's a winner in the game  and set the current state according to the winner/ tie
        val winner =  FIR_board.checkForWinner()

        // blue wins
        if (winner == GameConstants.BLUE_WON){
            println("Winner is blue! Game over.")
            FIR_board.printBoard()
            currentState = GameConstants.BLUE_WON
            // red wins
        } else if (winner == GameConstants.RED_WON){
            println("Winner is red! Game over.")
            FIR_board.printBoard()
            currentState = GameConstants.RED_WON
            // game ends in a tie
        } else if (winner == GameConstants.TIE){
            println("Game ended as a tie!")
            currentState = GameConstants.TIE
            FIR_board.printBoard()
        }


    } while (currentState == GameConstants.PLAYING && userInput != "q")
// repeat if not game-over

}
