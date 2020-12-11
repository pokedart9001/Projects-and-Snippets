import java.io.*;
import java.util.Scanner;

public class CaesarCipher {
	
	private int key;

	public void setKey(int key)
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

		in.close();
		out.close();
	}
	
	public byte encrypt(byte b)
	{
		return (byte) (b + key);
	}
}