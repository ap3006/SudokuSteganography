/*
 *@author Ashutosh Patra
 *@version 1.0
 */

import java.util.*;
import java.lang.Math;

public class SudokuSteganographyTestSuite  {

  public static void main(String[] args){


      // int[][] sudokuGrid = {{1, 8, 4, 0, 2, 5, 3, 6, 7},
      // {6, 5, 0, 3, 1, 7, 2, 8, 4},
      // {3, 2, 7, 4, 6, 8, 0, 5, 1},
      // {4, 0, 3, 6, 5, 2, 7, 1, 8},
      // {5, 7, 8, 1, 0, 3, 4, 2, 6},
      // {2, 6, 1, 8, 7, 4, 5, 0, 3},
      // {8, 1, 2, 7, 3, 0, 6, 4, 5},
      // {0, 3, 6, 5, 4, 1, 8, 7, 2},
      // {7, 4, 5, 2, 8, 6, 1, 3, 0 }};
      // int firstPixel = 128;
      // int secondPixel = 149;
      // int si = 4;

      // int[][] sudokuGrid = {{4, 8, 2, 0, 3, 1, 5, 6, 7},
      // {3, 5, 0, 6, 7, 2, 4, 1, 8 },
      // {1, 7, 6, 4, 5, 8, 0, 3, 2 },
      // {5, 1, 4, 3, 0, 7, 2, 8, 6 },
      // {0, 2, 3, 8, 4, 6, 7, 5, 1 },
      // {7, 6, 8, 2, 1, 5, 3, 0, 4 },
      // {2, 0, 1, 7, 8, 3, 6, 4, 5 },
      // {8, 3, 7, 5, 6, 4, 1, 2, 0 },
      // {6, 4, 5, 1, 2, 0, 8, 7, 3 }};
      //
      // int firstPixel = 128;
      // int secondPixel = 20;
      // int si = 4;

      // int[][] sudokuGrid = {{8, 5, 2, 3, 0, 1, 4, 6, 7 },
      // {0, 6, 1, 4, 7, 5, 3, 8, 2 },
      // {4, 7, 3, 6, 2, 8, 0, 1, 5 },
      // {1, 2, 4, 0, 8, 7, 5, 3, 6 },
      // {6, 8, 5, 1, 3, 2, 7, 0, 4 },
      // {7, 3, 0, 5, 6, 4, 8, 2, 1 },
      // {3, 1, 6, 7, 4, 0, 2, 5, 8 },
      // {5, 4, 8, 2, 1, 3, 6, 7, 0 },
      // {2, 0, 7, 8, 5, 6, 1, 4, 3 }};
      //
      // int firstPixel = 157;
      // int secondPixel = 166;
      //
      // int si = 5;

      // int[][] sudokuGrid = {{3, 2, 7, 0, 1, 4, 5, 8, 6 },
      // {5, 1, 6, 3, 8, 7, 0, 2, 4 },
      // {8, 4, 0, 5, 2, 6, 1, 3, 7 },
      // {1, 5, 2, 8, 6, 0, 4, 7, 3 },
      // {4, 7, 3, 2, 5, 1, 6, 0, 8 },
      // {0, 6, 8, 4, 7, 3, 2, 5, 1 },
      // {7, 8, 5, 1, 4, 2, 3, 6, 0 },
      // {6, 0, 4, 7, 3, 5, 8, 1, 2 },
      // {2, 3, 1, 6, 0, 8, 7, 4, 5 }};
      //
      // int firstPixel = 22;
      // int secondPixel = 125;
      //
      // int si = 7;

      // int[][] sudokuGrid = {{0, 5, 4, 1, 3, 2, 6, 8, 7 },
      // {8, 1, 2, 6, 0, 7, 3, 5, 4 },
      // {7, 3, 6, 4, 5, 8, 0, 1, 2 },
      // {2, 0, 8, 5, 7, 1, 4, 3, 6 },
      // {1, 4, 7, 3, 6, 0, 8, 2, 5 },
      // {3, 6, 5, 8, 2, 4, 1, 7, 0 },
      // {5, 8, 1, 7, 4, 6, 2, 0, 3 },
      // {6, 2, 3, 0, 1, 5, 7, 4, 8 },
      // {4, 7, 0, 2, 8, 3, 5, 6, 1 }};
      //
      // int firstPixel = 89;
      // int secondPixel = 161;
      // int si = 2;


      ////////////////////////////////////////////////////////////

      SudokuGrid grid = new SudokuGrid();
      grid.fillGrid();
      grid.subtractOne();
      int[][] sudokuGrid = grid.returnGrid();

      Random rand = new Random();
      int firstPixel = rand.nextInt(256);
      int secondPixel = rand.nextInt(256);
      int si = rand.nextInt(9);

      PixelPair first = new PixelPair(firstPixel,secondPixel);
      int pix =  first.getX() % 9;
      int piy = first.getY() % 9;

      System.out.println("Sudoku Grid: ");
      // grid.printGrid();
      printGrid(sudokuGrid);
      System.out.println("");
      System.out.println("Pixel pair is: " + first.toString());
      System.out.println("The value of pix is " + pix + " and piy is " + piy);
      System.out.println("Si: " + si);
      System.out.println("");


      int[] CEH = new int[9];
      int[] CEV = new int[9];
      int[][] CEB = new int[3][3];

      // System.out.println(sudokuGrid[2][4]);
      // System.out.println(Arrays.toString(sudokuGrid[3]));
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

    piy = first.getY() % 9;

    int firstX = first.getX() % 9;
    int firstY = first.getY() % 9;

    int py = (int) (piy / 3);
    posy = (py * 3) % 3;

    for (int i = 0; i < 3; i++){
      pix = first.getX() % 9;
      int px = (int) (pix / 3);
      posx = (px * 3) % 3;

      for (int j = 0; j < 3; j++){
        CEB[posy][posx] = sudokuGrid[piy][pix];
        // CEB[posy][posx] = 1;
        // System.out.println("The value of pix is: " + pix + " The value of piy is: " + piy + " and value in matrix is " + sudokuGrid[pix][piy]);
        // System.out.println("(Posx,Posy): " + "(" + posx + ", " + posy + ")");
        posx++;
        pix = (pix+1) % 9;

      }
      posy++;
      piy = (piy+1) % 9;
    }

    System.out.println("firstX: " + firstX);
    System.out.println("firstY: " + firstY);

    System.out.println("Starting embedding process...");
    System.out.println("");
    System.out.println("CEH: " + Arrays.toString(CEH));
    System.out.println("");
    System.out.println("CEV: " + Arrays.toString(CEV));
    System.out.println("");
    System.out.println("CEB: ");
    for (int i = 0; i < 3; i++){
      System.out.println(Arrays.toString(CEB[i]));
    }

    int DH = linearSearch(CEH, si) - 4;
    int DV = linearSearch(CEV, si) - 4;

    int SQX = 0;
    int SQY = 0;
    int SQD = 0;
    for (int i = 0; i < CEB.length; i++){
      if (linearSearch(CEB[i],si) != -1){
        SQX = linearSearch(CEB[i], si) + firstX - (pix % 3);
        SQY = i + firstY - (piy%3);
        System.out.println(Arrays.toString(CEB[i]));
        System.out.println("(Posx,Posy): " + "(" + (linearSearch(sudokuGrid[i], si) + firstX) + ", " + (i + firstY) + ")");
        System.out.println(linearSearch(CEB[i], si));
        SQD = (Math.abs(SQX) + Math.abs(SQY));
        break;
      }
      if (i == CEB.length - 1){
        SQD = Integer.MAX_VALUE;
      }
    }

    System.out.println("Value of DH: " + DH + "\n");
    System.out.println("Value of DV: " + DV + "\n");
    System.out.println("Value of SQX: " + SQX + "\n");
    System.out.println("Value of SQY: " + SQY + "\n");
    System.out.println("Value of SQD: " + SQD + "\n");

    // SQD = 50;
    if (Math.abs(DH) <= Math.abs(DV) && Math.abs(DH) <= Math.abs(SQD)){
      //firstPixel += DH;
      first.setX(first.getX() + DH);
    }
    else if (Math.abs(DV) <= Math.abs(DH) && Math.abs(DV) <= Math.abs(SQD)){
      first.setY(first.getY() + DV);
      //secondPixel += DV;
    }
    else{
      System.out.println("SQD is min");
      first.setX(first.getX() + SQX);
      first.setY(first.getY() + SQY);
      // firstPxel += SQX;
      // secondPixel += SQY;
    }

    System.out.println(first.toString());

    if (first.getX() < 0 || first.getX() > 255){
      // System.out.println("Here");
      if (first.getX() < 0){
        first.setX(first.getX() + 9);
      }
      else{
        first.setX(first.getX() - 9);
      }
    }

    if (first.getY() < 0 || first.getY() > 255){
      // System.out.println("Here");
      if (first.getY() < 0){
        first.setY(first.getY() + 9);
      }
      else{
        first.setY(first.getY() - 9);
      }
    }

    System.out.println("");
    System.out.println("Embedding over...\n");

    System.out.println("Data extraction taking place: \n");
    System.out.println("Pixel pair is: " + first.toString());

    pix = first.getX() % 9;
    piy = first.getY() % 9;
    System.out.println("The value of pix is " + pix + " and piy is " + piy);

    si = sudokuGrid[piy][pix];

    System.out.println("Si: " + si);

    ////////////////////////////////////////////////////////////////////////////////


    // int i = 54;
    // byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
    // byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
    // byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
    // byte byte0 = (byte)((i & 0x000000FF)	   );
    // System.out.println(byte3);
    // System.out.println(byte2);
    // System.out.println(byte1);
    // System.out.println(byte0);

  }

  public static int linearSearch(int[] array, int value){

    for (int i = 0; i < array.length; i++){
      if (array[i] == value){
        return i;
      }
    }
    return -1;
  }

  public static void printGrid(int[][] grid){
      for (int i = 0; i<9; i++)
      {
          for (int j = 0; j<9; j++)
              System.out.print(grid[i][j] + " ");
          System.out.println();
      }
      System.out.println();
  }

}
