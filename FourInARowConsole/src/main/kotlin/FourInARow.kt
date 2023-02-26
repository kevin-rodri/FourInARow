import kotlin.random.Random

/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 *  Modified by: Kevin Rodriguez
 * @date 2/2/2023
 */
class FourInARow
/**
 * clear board and set current player
 */
    : IGame {
    // game board in 2D array initialized to zeros
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS) { 0 } }
    // variable that will keep count of the amount of spots that have been taken
    private var spotCounter = 0

    // will iterate through entire board and set each indicie to empty
    override fun clearBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                board[row][col] = GameConstants.EMPTY
            }
        }
    }

    // get computer or human's location and set it accordingly in the board
    override fun setMove(player: Int, location: Int) {
        // if space is empty, then it is safe to set the current players move at that location. Will also keep track of the spot taken
        if (board[location / GameConstants.ROWS][location % GameConstants.COLS] == GameConstants.EMPTY) {
            board[location / GameConstants.ROWS][location % GameConstants.COLS] = player
            spotCounter += 1
        }
    }

    // get the generated AI move
    override val computerMove: Int
        get() = computerMovementAI()

    // simply returns a random spot if available
    private fun computerMovementAI(): Int {
        // generate a random value
        var computer = Random.nextInt(0, 35)
        // if the generated value has already been taken, regenerate a new value until you get a value that's not been taken.
        while (isNotAvailable(computer)) {
            computer = Random.nextInt(0, 35)
        }
        // set move if the spot has not been taken
        setMove(GameConstants.BLUE, computer)
        // return the generated value
        return computer
    }


    //checks to see if the location is not available -> True if it is not or false if it is.
    fun isNotAvailable(location: Int): Boolean {
        return board[location / GameConstants.ROWS][location % GameConstants.COLS] != GameConstants.EMPTY
    }


    override fun checkForWinner(): Int {
        // counters that will keep track if blue and red have four in a row
        var blueCounter = 0
        var redCounter = 0

        // Horizontal check using a two-pointer approach
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                for (colPointer in col + 1 until GameConstants.COLS) {
                    // if a blue spot was found
                    if (board[row][col] == GameConstants.BLUE) {
                        // check horizontally for the next one. If found, increment the counter
                        if (board[row][colPointer] == GameConstants.BLUE) {
                            blueCounter += 1
                        }
                        // if 4, then blue wins
                        if (blueCounter == 4) {
                            return GameConstants.BLUE_WON
                        }
                        // otherwise, keep looking
                    } else {
                        blueCounter = 0
                    }
                    // if a red spot was found
                    if (board[row][col] == GameConstants.RED) {
                        // check horizontally for the next one. If found, increment the counter
                        if (board[row][colPointer] == GameConstants.RED) {
                            redCounter += 1
                        }
                        // if 4, then blue wins
                        if (redCounter == 4) {
                            return GameConstants.RED_WON
                        }

                    } // otherwise, keep looking
                    else {
                        redCounter = 0
                    }
                }
            }
        }

        // Vertical check using a two-pointer approach
        for (col in 0 until GameConstants.COLS) {
            for (row in 0 until GameConstants.ROWS) {
                for (rowPointer in row + 1 until GameConstants.ROWS) {
                    // if a blue spot was found
                    if (board[row][col] == GameConstants.BLUE) {
                        // check vertically for the next one. If found, increment the counter
                        if (board[rowPointer][col] == GameConstants.BLUE) {
                            blueCounter += 1
                        }
                        // if 4, then blue wins
                        if (blueCounter == 4) {
                            return GameConstants.BLUE_WON
                        }
                    } else {
                        // otherwise, keep looking
                        blueCounter = 0
                    }

                    // if a red spot was found
                    if (board[row][col] == GameConstants.RED) {
                        // check vertically for the next one. If found, increment the counter
                        if (board[rowPointer][col] == GameConstants.RED) {
                            redCounter += 1
                        }
                        // if 4, then red wins
                        if (redCounter == 4) {
                            return GameConstants.RED_WON
                        }
                        // otherwise, keep looking
                    } else {
                        redCounter = 0
                    }
                }
            }
        }


        // Diagonal left
        /*
       x
         x
             x
                 x
      */
        // used source here:https://stackoverflow.com/questions/54725276/how-to-iterate-a-2d-array-diagonally-from-top-left-to-bottom-right
        for (row in 0 until GameConstants.ROWS + GameConstants.ROWS - 2) {
            for (col in 0 until row) {
                val index = row - col //  rows
                val mirror = GameConstants.ROWS  - index
                // ensure the values
                if (mirror >= 0 && mirror < GameConstants.ROWS && col < GameConstants.COLS) {
                    if (board[mirror][col] == GameConstants.RED) {
                        redCounter += 1
                        if (redCounter == 4){
                            return  GameConstants.RED_WON
                        }
                    }  else{
                        redCounter = 0
                    }
                    if (board[mirror][col] == GameConstants.BLUE) {
                        blueCounter += 1
                        if (blueCounter == 4){
                            return  GameConstants.BLUE_WON
                        }
                    } else{
                        blueCounter = 0
                    }
                }
            }
        }

        //       Checks for Diagnol Right winner
//        /*
//                    x
//                x
//            x
//          x
//         */
        // used source here:https://stackoverflow.com/questions/54725276/how-to-iterate-a-2d-array-diagonally-from-top-left-to-bottom-right
        for (row in 0  until GameConstants.ROWS + GameConstants.ROWS - 2) {
            for (col in 0 until row) { // cols
                val l = row - col //  rows
                if (l < GameConstants.ROWS && col < GameConstants.COLS) {
                    if (board[l][col] == GameConstants.RED) {
                        redCounter += 1
                        if (redCounter == 4){
                            return  GameConstants.RED_WON
                        }
                    }  else{
                        redCounter = 0
                    }
                    if (board[l][col] == GameConstants.BLUE) {
                        blueCounter += 1
                        if (blueCounter == 4){
                            return  GameConstants.BLUE_WON
                        }
                    }  else{
                        blueCounter = 0
                    }
                }
            }
        }

        // if the board is full, then the game will end as a tie
        if (spotCounter == 36) {
            return GameConstants.TIE
        }
        // the game is still currently being played
        return GameConstants.PLAYING
    }

    /**
     * Print the game board
     */
    fun printBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("-----------") // print horizontal partition
            }
        }
        println()
    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" B ")
            GameConstants.RED -> print(" R ")
        }
    }
}