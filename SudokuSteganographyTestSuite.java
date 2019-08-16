
import java.util.*;
import java.lang.Math;

public class SudokuSteganographyTestSuite  {

  public static void main(String[] args){
      SudokuGrid grid = new SudokuGrid();
      grid.fillGrid();

      int[][] sudokuGrid = grid.returnGrid();
      grid.printGrid();

      Random rand = new Random();

      int firstPixel = rand.nextInt(256);
      int secondPixel = rand.nextInt(256);

      PixelPair first = new PixelPair(firstPixel,secondPixel);
      int pix =  first.returnX() % 9;
      int piy = first.returnY() % 9;

      System.out.println("The value of pix is " + pix + " and piy is " + piy);

      int si = 8;

      int[] CEH = new int[9];
      int[] CEV = new int[9];
      int[][] CEB = new int[3][3];

      // System.out.println(sudokuGrid[2][4]);
      System.out.println(Arrays.toString(sudokuGrid[3]));
      for (int i = 0; i < 9; i++){
        int pos = (i+4) % 9;
        CEH[pos] = sudokuGrid[piy][pix];
        pix = (pix+1) % 9;
      }

      for (int i = 0; i < 9; i++){
        int pos = (i+4) % 9;
        CEV[pos] = sudokuGrid[piy][pix];
        piy = (piy+1) % 9;
      }

    int posx = 0;
    int posy = 0;

    piy = first.returnY() % 9;

    int py = (int) (piy / 3);
    posy = (py * 3) % 3;
    for (int i = 0; i < 3; i++){
      pix = first.returnX() % 9;
      int px = (int) (pix / 3);
      posx = (px * 3) % 3;

      for (int j = 0; j < 3; j++){
        CEB[posy][posx] = sudokuGrid[piy][pix];
        posx++;
        System.out.println("The value of pix is: " + pix + " The value of piy is: " + piy);
        pix = (pix+1) % 9;

      }
      posy++;
      piy = (piy+1) % 9;
    }




    grid.printGrid();
    System.out.println("");
    System.out.println("CEH: " + Arrays.toString(CEH));
    System.out.println("");
    System.out.println("CEV: " + Arrays.toString(CEV));
    System.out.println("");
    System.out.println("CEB: ");
    for (int i = 0; i < 3; i++){
      System.out.println(Arrays.toString(CEB[i]));
    }
  }

  public int linearSearch(int[] array, int value){

    for (int i = 0; i < array.length); i++){
      if (array[i] == value){
        return i;
      }
    }
    return -1;
  }


}
