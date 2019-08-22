// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.IOException;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.util.Arrays;


public class ZipFileTest {

	public static void main(String[] args) {

		try {
			FileOutputStream fos = new FileOutputStream("atest.zip");
      // FileOutputStream output = new FileOutputStream("output.zip");
      // ZipOutputStream zoutput = new ZipOutputStream(output);
			ZipOutputStream zos = new ZipOutputStream(fos);

			// String file1Name = "../test.txt";
			// String file2Name = "../TestFiles/Test1.png";
			// String file3Name = "../TestFiles/Test2.jpg";

			String file1Name = "random1.txt";
			String file2Name = "random.png";

			addToZipFile(file1Name, zos);
			addToZipFile(file2Name, zos);
			// addToZipFile(file3Name, zos);

      byte[] originalContentBytes = readBytesFromAFile(new File("atest.zip"));

      // File.WriteAllBytes("random.zip", originalContentBytes);
			// System.out.println(Arrays.toString(originalContentBytes));

			String bits = "";
			for (int i = 0; i < originalContentBytes.length; i++ ){
				bits += convertToBin(originalContentBytes[i]);
			}

			System.out.println((originalContentBytes.length * 8) % 3);

			int bitCount = (originalContentBytes.length * 8) % 3;
			int zeroOffset = bitCount == 0 ? 0 : 3 - bitCount;

			for (int i = 1; i <= zeroOffset; i++){
				bits += "0";
			}

			System.out.println(bits);

			// ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(originalContentBytes));
			// ZipEntry entry = null;
			// // int	counter = 0;
			// while ((entry = zipStream.getNextEntry()) != null) {
			//
			//     String entryName = entry.getName();
			// 		System.out.println(entryName);
			// 		FileOutputStream out = null;
			// 		if (entryName.equals("random1.txt")){
			// 			out = new FileOutputStream("test1.txt");
			// 		}
			// 		else if(entryName.equals("random.png")){
			// 			out = new FileOutputStream("googleIcon.png");
			// 		}
			// 		else{
			// 			out = new FileOutputStream(entryName);
			// 		}
			//
			// 		// counter++;
			//     byte[] byteBuff = new byte[4096];
			//     int bytesRead = 0;
			//     while ((bytesRead = zipStream.read(byteBuff)) != -1)
			//     {
			//         out.write(byteBuff, 0, bytesRead);
			//     }
			//
			//     out.close();
			//     zipStream.closeEntry();
			// }
			// zipStream.close();


			zos.close();
			fos.close();



		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		// System.out.println()

		System.out.println(convertToBin((byte) -42));
		System.out.println(convertToBin((byte) 342));

	}

	public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

		System.out.println("Writing '" + fileName + "' to zip file");

		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	public static String convertToBin(Byte number){
		return String.format("%8s", Integer.toBinaryString(number & 0xFF)).replace(' ', '0');
	}

  public static byte[] readBytesFromAFile(File file) {
    int start = 0;
    int length = 1024;
    int offset = -1;
    byte[] buffer = new byte[length];
    try {
        //convert the file content into a byte array
        FileInputStream fileInuptStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                fileInuptStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while ((offset = bufferedInputStream.read(buffer, start, length)) != -1) {
            byteArrayOutputStream.write(buffer, start, offset);
        }

        bufferedInputStream.close();
        byteArrayOutputStream.flush();
        buffer = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
    } catch (FileNotFoundException fileNotFoundException) {
        fileNotFoundException.printStackTrace();
    } catch (IOException ioException) {
        ioException.printStackTrace();
    }

    return buffer;
}

}
