import java.io.*;
import java.util.Scanner;

public class CaesarCipher {
	
	private int key;

	public CaesarCipher(int key)
	{
		this.key = key;
	}

	public void encryptStream(InputStream in, OutputStream out) throws IOException
	{
		boolean done = false;
		while (!done)
		{
			int next = in.read();
			if (next == -1)
				done = true;
		else
			{
				byte b = (byte) next;
				byte c = encrypt(b);
				out.write(c);
			}
		}
	}
	
	public byte encrypt(byte b)
	{
		return (byte) (b + key);
	}

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		try
		{
			System.out.print("Input file: ");
			String inFile = in.next();
			System.out.print("Output file: ");
			String outFile = in.next();
			System.out.print("Encryption key: ");
			int key = in.nextInt();
			InputStream inStream = new FileInputStream(inFile);
			OutputStream outStream = new FileOutputStream(outFile);
			CaesarCipher cipher = new CaesarCipher(key);
			cipher.encryptStream(inStream, outStream);
			inStream.close();
			outStream.close();
		}
		catch (IOException exception)
		{
			System.out.println("Error processing file: " + exception);
		}
	}
}