//import utilities
import java.util.Arrays;
import java.util.Random;

//begin driver class
public class Driver {

    //declare private variables for use through program
    private static int tempHeuristic = 0;
    private static int nearbyQueen = 0;
    private static int resets = 0;
    private static int stateChanges = 0;

    //begin main class
    public static void main(String[] args)
    {
        //declare variables
        Queen[] currentBoard;
        int currentH = 0;

        //initialize boards
        Queen[] initBoard = createBoard();

        //fill board
        currentBoard = Arrays.copyOf(initBoard, 8);
        tempHeuristic = checkHeuristic(initBoard);
        currentH = tempHeuristic;

        // test other states
        while (currentH != 0)
        {
            currentBoard = testBoards(currentBoard, currentH);  //  Sets the best board as current
            currentH = tempHeuristic;
        }

        //print out blank line between boards
        System.out.println();

        //print last board
        printBoard(currentBoard, currentH, nearbyQueen);

        //print out results
        System.out.println("Solution Found!");
        System.out.println("\nState changes: " + stateChanges);
        System.out.println("Number of resets: " + resets);
    }
    //end main method

    //Tests heuristic of each potential state
    public static Queen[] testBoards (Queen[] currentBoard, int currentH)
    {
        //declare local variables
        Queen[] bestBoard = new Queen[8];
        Queen[] tempBoard = new Queen[8];
        int bestH = currentH;
        int tempH;
        int numnearbyQueen = 0;

        //loop to copy over boards
        for (int i=0; i<8; i++)
        {
            bestBoard[i] = new Queen(currentBoard[i].getRow(), currentBoard[i].getCol());
            tempBoard[i] = new Queen(bestBoard[i].getRow(), bestBoard[i].getCol());
        }
        //end column loop

        //iterate each column
        for (int i=0; i<8; i++)
        {
            //reset the board
            if (i>0)
                tempBoard[i-1] = new Queen (currentBoard[i-1].getRow(), currentBoard[i-1].getCol());

            tempBoard[i] = new Queen (0, tempBoard[i].getCol());

            //iterate each row
            for (int j=0; j<8; j++) {

                //check heuristic
                tempH = checkHeuristic(tempBoard);

                //if there is a nearby queen with a lower heursitic reset the heursitic to 1
                if (tempH < bestH)
                {
                    numnearbyQueen++;
                    bestH = tempH;

                    //copy board with the best heuristic
                    for (int g=0; g<8; g++)
                    {
                        bestBoard[g] = new Queen(tempBoard[g].getRow(), tempBoard[g].getCol());
                    }
                }

                //moves the queen down
                if (tempBoard[i].getRow()!=7)
                    tempBoard[i].down();
            }
            //end row iteration
        }
        //end column iteration

        //print out blank line
        System.out.println();

        //print out the previous board
        printBoard(currentBoard, currentH, numnearbyQueen);
        System.out.println("Setting next state...");

        //reset the board if there is no better board found
        if (bestH == currentH)
        {
            System.out.println("\nNo better board found. Resetting...");
            bestBoard = createBoard();
            tempHeuristic = checkHeuristic(bestBoard);
            resets++;
        }
        else
            tempHeuristic = bestH;

        //end rest statement

        //increment change value
        stateChanges++;

        //return the best board
        return bestBoard;
    }

    //method to check the heuristic
    public static int checkHeuristic (Queen[] board)
    {
        //declare local variables
        int h = 0;

        //iteration to check the heuristic
        for (int i = 0; i< board.length; i++)
        {
            for (int k=i+1; k<board.length; k++ )
            {
                if (board[i].inConflict(board[k]))
                {
                    h++;
                }
            }
        }
        //end iteration

        //return the heuristic value
        return h;
    }
    //end heuristic method

    //method to create the board
    public static Queen[] createBoard()
    {
        //declare local variables
        Queen[] startPos = new Queen[8];
        Random rd = new Random();

        //iteration to set the positions
        for(int i=0; i<8; i++)
        {
            startPos[i] = new Queen(rd.nextInt(8), i);
        }

        //return the position of the queen
        return startPos;
    }
    //end create board method

    //method to print the board
    private static void printBoard (Queen[] state, int h, int nearbyQueen)
    {
        //declare local variables
        int[][] board = new int[8][8];

        //iteration to set each position to zero
        for (int i=0; i<8; i++)
        {
            for (int k=0; k<8; k++)
            {
                board[i][k]=0;
            }
        }
        //end iteration

        //loop to change values to one at certain coordinates
        for (int i=0; i<8; i++)
        {
            board[state[i].getRow()][state[i].getCol()]=1;
        }

        //print out the results
        System.out.println("Current heuristic: " + h);

        //print out the current state of board
        System.out.println("Current state... ");

        for (int i=0; i<8; i++)
        {
            for (int k = 0; k < 8; k++)
            {
                System.out.print(board[i][k] + " ");
            }
            System.out.println();
        }

        //print out the nearby queen
        System.out.println("nearbyQueen with lower heuristic: " + nearbyQueen);
    }
    //end print method
}
//end class