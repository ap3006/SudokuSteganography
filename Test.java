public class Test {
    public static String toBinary(String text) {
        StringBuilder sb = new StringBuilder();

        for (char character : text.toCharArray()) {
            sb.append(Integer.toBinaryString(character));
        }

        return sb.toString();

    }

    public static String toBase9(int number){
      String baseNine = "";
      int quotient = number;
      while (quotient > 9){
        baseNine += (quotient % 9);
        // System.out.println(quotient + " remainder " + (quotient % 9));
        // System.out.println()
        quotient /= 9;

        if (quotient < 9){
          baseNine += quotient;
        }
      }

      StringBuilder sb = new StringBuilder(baseNine).reverse();

      return sb.toString();
    }

    public static void main(String[] args){
      // System.out.println(toBinary("Hello"));
      // String test = "Hello";
      //
      // byte[] array = {123,43,123,32};
      //
      // for (int i = 0; i < array.length; i++){
      //   System.out.println(toBase9(array[i]));
      // }

      // String bits = "011010101010101010101010";
      //
      // int counter = 0;
      // for (int i = 0; i < (bits.length() / 8); i++){
      //   System.out.println(bits.substring(counter, counter + 8));
      //   counter += 8;
      // }

      int[] array = {1,2,3};

      int count = 0;

      System.out.println("" + array[count] + "+" + array[count++]);
      System.out.println(count);
      //
      // System.out.println(test.length());
      // System.out.println(test.getBytes().length);
      // System.out.println(toBase9(2572));

    }
}
