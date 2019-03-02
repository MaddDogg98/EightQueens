//begin queen class
public class Queen
{
    //declare private variables
    private int row;
    private int column;

    //constructor for class
    public Queen(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    //method to check if there is a conflict
    public boolean inConflict(Queen q)
    {
        //check rows and columns
        if(row == q.getRow() || column == q.getCol())
            return true;
            //check diagonals
        else if(Math.abs(column-q.getCol()) == Math.abs(row-q.getRow()))
            return true;

        return false;
    }
    //end method

    //method to retrieve row
    public int getRow()
    {
        return row;
    }

    //method to retreive the column
    public int getCol()
    {
        return column;
    }

    //method to move the queen down
    public void down()
    {
        row++;
    }

    //method to make class into a string
    public String toString()
    {
        return row + "," + column;
    }
}