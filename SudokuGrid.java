/*
 * @author: Ashutosh Patra
 * version: 1.0
 */

import java.lang.*;

//Code to randomly generate a complete sudoku grid.
public class SudokuGrid {

    int[] grid[];

    public SudokuGrid(){
      grid = new int[9][9];
    }

    public void fillGrid(){
      fillDiagonal();
      fillRemaining(0,3);
    }

    public void fillDiagonal(){

      for (int i = 0; i<9; i=i+3){
        fillBox(i,i);
      }
    }

    public boolean unUsedInBox(int rowPos, int columnPos, int num){

      for (int i = 0; i < 3; i++){
        for (int j = 0; j < 3; j++){
          if (grid[rowPos+i][columnPos+j] == num){
            return false;
          }
        }
      }
      return true;
    }

    public void fillBox(int row, int column){
      int num;
      for (int i = 0; i < 3; i++){
        for (int j = 0; j < 3; j++){
          do {
            num = (int) Math.floor((Math.random()*9+1));
          }
          while(!unUsedInBox(row,column,num));

          grid[row+i][column+j] = num;
        }
      }
    }

    public boolean CheckIfSafe(int i,int j,int num){
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%3, j-j%3, num));
    }

    public boolean unUsedInRow(int i,int num){
        for (int j = 0; j<9; j++)
           if (grid[i][j] == num)
                return false;
        return true;
    }

    public boolean unUsedInCol(int j,int num){
        for (int i = 0; i<9; i++)
            if (grid[i][j] == num)
                return false;
        return true;
    }

    public void printGrid(){
        for (int i = 0; i<9; i++)
        {
            for (int j = 0; j<9; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public boolean fillRemaining(int i, int j){
        if (j>=9 && i<9-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=9 && j>=9)
            return true;

        if (i < 3)
        {
            if (j < 3)
                j = 3;
        }
        else if (i < 6)
        {
            if (j==(int)(i/3)*3)
                j =  j + 3;
        }
        else
        {
            if (j == 6)
            {
                i = i + 1;
                j = 0;
                if (i>=9)
                    return true;
            }
        }

        for (int num = 1; num<=9; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                grid[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                grid[i][j] = 0;
            }
        }
        return false;
    }

    public void subtractOne(){
      for (int i = 0; i < 9; i++){
        for (int j = 0; j < 9; j++){
          grid[i][j]--;
        }
      }
    }

    public static void main(String args[]){
      SudokuGrid sudoku = new SudokuGrid();
      sudoku.fillGrid();
      sudoku.printGrid();
    }


}
