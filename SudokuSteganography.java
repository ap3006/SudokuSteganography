// /*
//  *@author Ashutosh Patra
//  *@version 1.0
//  */

import java.io.File;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import java.util.Arrays;


public class SudokuSteganography{

  public SudokuSteganography(){

  }

  // public static void main(String[] args){
  //
  //   Test model = new Test();
  //   if (model.encode("testFiles","googleLogo","png","output","Hello World this is me, ya boi Tosh asoidasdjasiodj!")){
  //     System.out.println("Success");
  //   }
  //   else{
  //     System.out.println("Fail");
  //   }
  //
  //   System.out.println(model.decode("testFiles","output"));
  //
  // }

  public boolean encode(String path, String original, String ext1, String stegan, String message)
  {
    String file_name = image_path(path,original,ext1);
    BufferedImage image_orig	= getImage(file_name);

    //user space is not necessary for Encrypting
    BufferedImage image = user_space(image_orig);
    image = add_text(image,message);

    return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
  }

  /*
   *Decrypt assumes the image being used is of type .png, extracts the hidden text from an image
   *@param path   The path (folder) containing the image to extract the message from
   *@param name The name of the image to extract the message from
   *@param type integer representing either basic or advanced encoding
   */
  public String decode(String path, String name)
  {
    byte[] decode;
    try
    {
      //user space is necessary for decrypting
      BufferedImage image  = user_space(getImage(image_path(path,name,"png")));
      decode = decode_text(image);
      // decode = decode_text(get_byte_data(image));
      // System.out.println("decode over");
      return(new String(decode));
    }
    catch(Exception e)
    {
      // System.out.println("I am here");
      // System.out.println(e.toString());
      // System.out.println()
      JOptionPane.showMessageDialog(null,
        "There is no hidden message in this image!","Error",
        JOptionPane.ERROR_MESSAGE);
      return "";
    }
  }

  /*
   *Returns the complete path of a file, in the form: path\name.ext
   *@param path   The path (folder) of the file
   *@param name The name of the file
   *@param ext	  The extension of the file
   *@return A String representing the complete path of a file
   */
  private String image_path(String path, String name, String ext)
  {
    return path + "/" + name + "." + ext;
  }

  /*
   *Get method to return an image file
   *@param f The complete path name of the image.
   *@return A BufferedImage of the supplied file path
   *@see	Steganography.image_path
   */
  private BufferedImage getImage(String f)
  {
    BufferedImage 	image	= null;
    File 		file 	= new File(f);

    try
    {
      image = ImageIO.read(file);
    }
    catch(Exception ex)
    {
      JOptionPane.showMessageDialog(null,
        "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
    }
    return image;
  }

  /*
   *Set method to save an image file
   *@param image The image file to save
   *@param file	  File  to save the image to
   *@param ext	  The extension and thus format of the file to be saved
   *@return Returns true if the save is succesful
   */
  private boolean setImage(BufferedImage image, File file, String ext)
  {
    try
    {
      file.delete(); //delete resources used by the File
      ImageIO.write(image,ext,file);
      return true;
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,
        "File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  /*
   *Handles the addition of text into an image
   *@param image The image to add hidden text to
   *@param text	 The text to hide in the image
   *@return Returns the image with the text embedded in it
   */
  private BufferedImage add_text(BufferedImage image, String text)
  {
    //convert all items to byte arrays: image, message, message length
    // byte img[] = get_byte_data(image);
    byte msg[] = text.getBytes();
    byte len[] = bit_conversion(msg.length);
    BufferedImage picture = image;
    try
    {
      SudokuGrid grid = new SudokuGrid();
      grid.fillGrid();
      grid.subtractOne();
      grid.printGrid();
      int[][] sudokuGrid = grid.returnGrid();
      // int[][] sudokuGrid = {{2, 8, 4, 3, 0, 1, 5, 7, 6},
      //                       {3, 5, 7, 4, 2, 6, 0, 8, 1},
      //                       {0, 6, 1, 5, 8, 7, 2, 4, 3},
      //                       {1, 4, 8, 2, 7, 5, 6, 3, 0},
      //                       {6, 0, 2, 1, 3, 8, 4, 5, 7},
      //                       {5, 7, 3, 0, 6, 4, 8, 1, 2},
      //                       {7, 1, 6, 8, 4, 0, 3, 2, 5},
      //                       {8, 2, 0, 7, 5, 3, 1, 6, 4},
      //                       {4, 3, 5, 6, 1, 2, 7, 0, 8}};
      int[] RGBimg = imageToRGB(image);
      int[] alpha = getAlpha(image);

      // int height = ;
      // int weight = ;

      byte img[] = get_byte_data(picture);
      // System.out.println(Arrays.toString(img));
      // encode_text(img, len,  0); //0 first positiong
      // sudokuEncoding(picture, msg, sudokuGrid);
      encode_text(img, returnOneDimension(sudokuGrid), (img.length) - (81*8)); //4 bytes of space for length: 4bytes*8bit = 32 bits
      lengthEncoding(picture, msg, sudokuGrid);
      messageEncoding(picture, msg, sudokuGrid);

    }
    catch(Exception e)
    {
      // System.out.println(e.toString());
      JOptionPane.showMessageDialog(null,
      "Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
    }
    return image;
  }

  /*
   *Creates a user space version of a Buffered Image, for editing and saving bytes
   *@param image The image to put into user space, removes compression interferences
   *@return The user space version of the supplied image
   */
  private BufferedImage user_space(BufferedImage image)
  {
    //create new_img with the attributes of image
    BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
    Graphics2D	graphics = new_img.createGraphics();
    graphics.drawRenderedImage(image, null);
    graphics.dispose(); //release all allocated memory for this image
    return new_img;
  }

  /*
   *Gets the byte array of an image
   *@param image The image to get byte data from
   *@return Returns the byte array of the image supplied
   *@see Raster
   *@see WritableRaster
   *@see DataBufferByte
   */
  private byte[] get_byte_data(BufferedImage image)
  {
    WritableRaster raster   = image.getRaster();
    DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
    return buffer.getData();
  }

  /*
   *Gernerates proper byte format of an integer
   *@param i The integer to convert
   *@return Returns a byte[4] array converting the supplied integer into bytes
   */
  private byte[] bit_conversion(int i)
  {
    //originally integers (ints) cast into bytes
    //byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
    //byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
    //byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
    //byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);

    //only using 4 bytes
    byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
    byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
    byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
    byte byte0 = (byte)((i & 0x000000FF)	   );
    //{0,0,0,byte0} is equivalent, since all shifts >=8 will be 0
    return(new byte[]{byte3,byte2,byte1,byte0});
  }

  /*
   *Encode an array of bytes into another array of bytes at a supplied offset
   *@param image	 Array of data representing an image
   *@param addition Array of data to add to the supplied image data array
   *@param offset	  The offset into the image array to add the addition data
   *@return Returns data Array of merged image and addition data
   */
  private byte[] encode_text(byte[] image, byte[] addition, int offset)
  {
    //check that the data + offset will fit in the image
    if(addition.length + offset > image.length)
    {
      throw new IllegalArgumentException("File not long enough!");
    }
    //loop through each addition byte
    for(int i=0; i<addition.length; ++i)
    {
      //loop through the 8 bits of each byte
      int add = addition[i];
      for(int bit=7; bit>=0; --bit, ++offset) //ensure the new offset value carries on through both loops
      {
        //assign an integer to b, shifted by bit spaces AND 1
        //a single bit of the current byte
        int b = (add >>> bit) & 1;
        //assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
        //changes the last bit of the byte in the image to be the bit of addition
        image[offset] = (byte)((image[offset] & 0xFE) | b );
      }
    }
    return image;
  }

  private BufferedImage lengthEncoding(BufferedImage img, byte[] addition, int[][] sudokuGrid){

    int height = img.getHeight();
    int width = img.getWidth();
    byte[] image = get_byte_data(img);
    String bits = "";

    // String lengthBits = convertToBin(addition.length);
    for (int i = 0; i < 45-8; i++ ){
      bits += "0";
    }
    bits += convertToBin(addition.length);
    // System.out.println(addition.length);
    // System.out.println(bits);

    int[] Sk = new int[15];
    int bitCounter = 0;
    for (int i = 0; i < Sk.length; i++){
      // System.out.println(i);
      Sk[i] += Integer.parseInt(bits.charAt(bitCounter++)+"") * 4;
      Sk[i] += Integer.parseInt(bits.charAt(bitCounter++)+"") * 2;
      Sk[i] += Integer.parseInt(bits.charAt(bitCounter++)+"") * 1;
    }

    // System.out.println(Arrays.toString(Sk));
    // System.out.println(Arrays.toString(addition));

    int[] RGB = imageToRGB(img);
    if (bits.length() > RGB.length * 4.5){
      // System.out.println("Exception is thrown");
      throw new IllegalArgumentException("File not long enough!");
    }

    int RgbCounter = 0;
    for (int j = 0; j < Sk.length; j++){
      // System.out.println(j);
      // System.out.println(j);
      PixelPair first = new PixelPair(RGB[RgbCounter], RGB[RgbCounter + 1]);

      int pix = first.getX() % 9;
      int piy = first.getY() % 9;

      int si = Sk[j];

      int[] CEH = new int[9];
      int[] CEV = new int[9];
      int[][] CEB = new int[3][3];

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

        for (int k = 0; k < 3; k++){
          CEB[posy][posx] = sudokuGrid[piy][pix];
          posx++;
          pix = (pix+1) % 9;

        }
        posy++;
        piy = (piy+1) % 9;
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
          SQD = (Math.abs(SQX) + Math.abs(SQY));
          break;
        }
        if (i == CEB.length - 1){
          SQD = Integer.MAX_VALUE;
        }
      }

      if (Math.abs(DH) <= Math.abs(DV) && Math.abs(DH) <= Math.abs(SQD)){
        first.setX(first.getX() + DH);
      }
      else if (Math.abs(DV) <= Math.abs(DH) && Math.abs(DV) <= Math.abs(SQD)){
        first.setY(first.getY() + DV);
      }
      else{
        first.setX(first.getX() + SQX);
        first.setY(first.getY() + SQY);
      }

      // System.out.println(first.toString());

      if (first.getX() < 0 || first.getX() > 255){
        if (first.getX() < 0){
          first.setX(first.getX() + 9);
        }
        else{
          first.setX(first.getX() - 9);
        }
      }

      if (first.getY() < 0 || first.getY() > 255){
        if (first.getY() < 0){
          first.setY(first.getY() + 9);
        }
        else{
          first.setY(first.getY() - 9);
        }
      }

      RGB[RgbCounter] = first.getX();
      RGB[RgbCounter+1] = first.getY();

      RgbCounter += 2;
    }

    // System.out.println("Embedding over");

    return RGBtoImage(img, height, width,RGB, getAlpha(img));

  }

  private BufferedImage messageEncoding(BufferedImage img, byte[] addition, int[][] sudokuGrid){

    int height = img.getHeight();
    int width = img.getWidth();
    byte[] image = get_byte_data(img);
    String bits = "";
    for (int i = 0; i < addition.length; i++ ){
      bits += convertToBin(addition[i]);
    }

    // System.out.println("Length of string is: " + bits.length());
    // System.out.println("The length of the message is: " + addition.length);

    int bitCount = (addition.length * 8) % 3;
    int zeroOffset = bitCount == 0 ? 0 : 3 - bitCount;
    for (int i = 1; i <= zeroOffset; i++){
      bits += "0";
    }

    int[] Sk = new int[bits.length() / 3];
    int bitCounter = 0;
    for (int i = 0; i < Sk.length; i++){
      Sk[i] += Integer.parseInt(bits.charAt(bitCounter++)+"") * 4;
      Sk[i] += Integer.parseInt(bits.charAt(bitCounter++)+"") * 2;
      Sk[i] += Integer.parseInt(bits.charAt(bitCounter++)+"") * 1;
    }

    // System.out.println(Arrays.toString(Sk));
    // System.out.println(Arrays.toString(addition));

    int[] RGB = imageToRGB(img);
    if (bits.length() > RGB.length * 4.5){
      // System.out.println("Exception is thrown");
      throw new IllegalArgumentException("File not long enough!");
    }

    int RgbCounter = 30;
    for (int j = 0; j < Sk.length; j++){
      // System.out.println(j);
      PixelPair first = new PixelPair(RGB[RgbCounter], RGB[RgbCounter + 1]);

      int pix = first.getX() % 9;
      int piy = first.getY() % 9;

      int si = Sk[j];

      int[] CEH = new int[9];
      int[] CEV = new int[9];
      int[][] CEB = new int[3][3];

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

        for (int k = 0; k < 3; k++){
          CEB[posy][posx] = sudokuGrid[piy][pix];
          posx++;
          pix = (pix+1) % 9;

        }
        posy++;
        piy = (piy+1) % 9;
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
          SQD = (Math.abs(SQX) + Math.abs(SQY));
          break;
        }
        if (i == CEB.length - 1){
          SQD = Integer.MAX_VALUE;
        }
      }

      if (Math.abs(DH) <= Math.abs(DV) && Math.abs(DH) <= Math.abs(SQD)){
        first.setX(first.getX() + DH);
      }
      else if (Math.abs(DV) <= Math.abs(DH) && Math.abs(DV) <= Math.abs(SQD)){
        first.setY(first.getY() + DV);
      }
      else{
        first.setX(first.getX() + SQX);
        first.setY(first.getY() + SQY);
      }

      // System.out.println(first.toString());

      if (first.getX() < 0 || first.getX() > 255){
        if (first.getX() < 0){
          first.setX(first.getX() + 9);
        }
        else{
          first.setX(first.getX() - 9);
        }
      }

      if (first.getY() < 0 || first.getY() > 255){
        if (first.getY() < 0){
          first.setY(first.getY() + 9);
        }
        else{
          first.setY(first.getY() - 9);
        }
      }

      RGB[RgbCounter] = first.getX();
      RGB[RgbCounter+1] = first.getY();

      RgbCounter += 2;
    }

    // System.out.println("Embedding over");

    return RGBtoImage(img, height, width,RGB, getAlpha(img));

  }

  private int[] imageToRGB(BufferedImage image){
    // int[] imageRGB = new int[image.length * 3];
    int height = image.getHeight();
    int width = image.getWidth();

    int[] imageRGB = new int[height*width * 3];

    int counter = 0;
    for (int i = 0; i < height; i++){
      for (int j = 0; j < width; j++){
        int pixel = image.getRGB(j,i);
        imageRGB[counter++] = (pixel >> 16) & 0xff;
        imageRGB[counter++] = (pixel >> 8) & 0xff;
        imageRGB[counter++] = (pixel & 0xff);
      }
    }

    return imageRGB;

  }

  private int[] getAlpha(BufferedImage image){
    int height = image.getHeight();
    int width = image.getWidth();
    int[] alpha = new int[height*width];

    // for (int i = 0; i < image.length; i++){
    //   alpha[i] = ((image[i] >> 24) & 0xff);
    // }
    int counter = 0;
    for (int i = 0; i < height; i++){
      for (int j = 0; j < width; j++){
        int pixel = image.getRGB(j,i);
        alpha[counter++] = (pixel >> 24) & 0xff;
      }
    }

    return alpha;
  }

  private BufferedImage RGBtoImage(BufferedImage img, int height, int width,int[] RGB, int[] alpha){
    // byte[] image = new byte[RGB.length / 3];

    int[] image = new int[height*width];

    int count = 0;
    for (int i = 0; i < image.length; i++ ){
      // System.out.println(((alpha[i] << 24) | (RGB[count]<<16) | (RGB[count += 1]<<8) | RGB[count += 1]));
      image[i] = ((alpha[i] << 24) | (RGB[count]<<16) | (RGB[count += 1]<<8) | RGB[count += 1]);
      // if (((alpha[i] << 24) | (RGB[count]<<16) | (RGB[count += 1]<<8) | RGB[count += 1]) != image[i]){
      //   System.out.println("Here");
      // }
      count++;
    }

    count = 0;
    for (int i = 0; i < height; i++){
      for (int j = 0; j < width; j++){
        img.setRGB(j,i,image[count++]);
      }
    }

    // System.out.println()
    // System.out.println("RGBtoImage loop over");
    return img;
  }

  /*
   *Retrieves hidden text from an image
   *@param image Array of data, representing an image
   *@return Array of data which contains the hidden text
   */
  private byte[] decode_text(BufferedImage img)
  {
    byte[] image = get_byte_data(img);
    // int length = 0;
    int height = img.getHeight();
    int width = img.getWidth();
    int offset  = (image.length) - (81*8);
    //loop through 32 bytes of data to determine text length
    // for(int i=0; i<32; ++i) //i=24 will also work, as only the 4th byte contains real data
    // {
    //   // System.out.println(i);
    //   length = (length << 1) | (image[i] & 1);
    // }
    // System.out.println(length);




    int[] result = new int[81];

    //loop through each byte of text
    for(int b=0; b< 81; ++b )
    {
      // System.out.println(b);
      //loop through each bit within a byte of text
      for(int i=0; i<8; ++i, ++offset)
      {
        //assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
        result[b] = ((result[b] << 1) | (image[offset] & 1));
      }
    }

    int[][] grid = new int[9][9];

    int count = 0;
    for (int i = 0; i < 9; i++ ){
      // System.out.println(i);
      grid[i] = Arrays.copyOfRange(result,count, count + 9);
      // System.out.println(Arrays.toString(grid[i]));
      count += 9;

    }

    // for (int i = 0; i<9; i++)
    // {
    //     for (int j = 0; j<9; j++)
    //         System.out.print(grid[i][j] + " ");
    //     System.out.println();
    // }
    // System.out.println();

    int[] RGB = imageToRGB(img);
    int counter = 0;
    int Si;

    String lengthBits = "";

    int[] Sk = new int[15];
    // int SkLength = (((length*8) + 0) /3);
    for (int i = 0; i < Sk.length; i++){
      int pix = RGB[counter++] % 9;
      int piy = RGB[counter++] % 9;
      Sk[i] = grid[piy][pix];
      // System.out.println(Sk[i]);
      Si = grid[piy][pix];
      lengthBits += convertToBin(Si).substring(5,8);
    }

    //May need to change this
    int length = Integer.parseInt(lengthBits,2);

    // System.out.println(length);

    return decodeMsg(img,grid,length);
  }



  private byte[] decodeMsg(BufferedImage img, int[][] grid, int length){

    // byte[] image = get_byte_data(img);



    int[] RGB = imageToRGB(img);
    int counter = 30;
    int Si;


    String result = "";

    int bitCount = (length * 8) % 3;
    int zeroOffset = bitCount == 0 ? 0 : 3 - bitCount;

    // System.out.println("Here: " + (((length*8) + zeroOffset) /3));
    int SkLength = (((length*8) + zeroOffset) /3);
    int[] Sk = new int[SkLength];
    for (int i = 0; i < SkLength; i++){
      int pix = RGB[counter++] % 9;
      int piy = RGB[counter++] % 9;
      Sk[i] = grid[piy][pix];
      Si = grid[piy][pix];
      result += convertToBin(Si).substring(5,8);
    }

    result = result.substring(0, result.length() - zeroOffset);

    byte[] output = new byte[length];
    counter = 0;
    for (int i = 0; i < length; i++){
      // output[i] = Byte.parseByte(result.substring(counter, counter + 8));
      int value = Integer.parseInt(result.substring(counter, counter + 8),2);
      // System.out.println(value);
      output[i] = (byte) value;
      // System.out.println(output[i]);
      counter += 8;
    }
    // System.out.println(Arrays.toString(output));

    return output;

  }

  private String convertToBin(int number){
		return String.format("%8s", Integer.toBinaryString(number & 0xFF)).replace(' ', '0');
	}


  private int linearSearch(int[] array, int value){

    for (int i = 0; i < array.length; i++){
      if (array[i] == value){
        return i;
      }
    }
    return -1;
  }

  private byte[] returnOneDimension(int[][] grid){
    byte[] output = new byte[81];
    int count = 0;
    for (int i = 0; i < 9; i++){
      for (int j = 0; j < 9; j++){

        output[count++] = (byte) grid[i][j];

      }
    }
    return output;
  }
}
