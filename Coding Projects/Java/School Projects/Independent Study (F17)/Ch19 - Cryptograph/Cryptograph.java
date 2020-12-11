import java.util.*;
import java.io.*;

public class Cryptograph
{
	File file;

	public Cryptograph(String pathName) throws FileNotFoundException, IOException
	{
		file = new File(pathName);
	}

	public void analyze() throws FileNotFoundException, IOException
	{
		new LetterFrequencies(file).generateOutput();
	}

	public void bruteForce(int range) throws FileNotFoundException, IOException
	{
		CaesarCipher cipher = new CaesarCipher();
		new File("brute_force").mkdir();
		for (int i = -range; i <= range; i++)
		{
			if (i == 0) continue;
			FileInputStream inStream = new FileInputStream(file);
			FileOutputStream outStream = new FileOutputStream(String.format("brute_force/%d.txt", i + range));
			cipher.setKey(i);
			cipher.encryptStream(inStream, outStream);

			FileReader reader = new FileReader(String.format("brute_force/%d.txt", i + range));
			StringBuilder output = new StringBuilder();
			while (reader.ready())
			{
				output.append(reader.read());
			}
			String result = output.toString().toUpperCase();
			for (String word : WordList.wordList)
			{
				if (result.contains(word))
				{
					System.out.println(String.format("Match found in %d.txt: %s", i + range, word));
				}
			}
		}
	}

	public void substitute(char substitute, char replace) throws FileNotFoundException, IOException
	{
		File out = new File("substituted.txt");
		FileReader reader = new FileReader(file);
		FileWriter writer = new FileWriter(out);

		for (int c = reader.read(); c > -1; c = reader.read())
		{
			if ((char)c == substitute)
				writer.write(replace);
			else if ((char)c == substitute - 65)
				writer.write(replace - 65);
			else
				writer.write((char)c);
			System.out.println(c);
		}

		reader.close();
		writer.close();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Scanner input = new Scanner(System.in);
		int option;
		char[] alphabet = WordList.alphabet;

		System.out.println("Welcome to the Cryptographer's Toolkit for decrypting monoalphabetic ciphers!\n");

		System.out.print("Enter the name of the file you wish to decrypt: ");
		Cryptograph toolkit = new Cryptograph(input.nextLine());

		System.out.println("What would you like to do?");
		System.out.println("[1] - Analyze Letter Frequencies\n[2] - Brute Force Caesar Cipher\n[3] - Substitute Characters\n");

		while (true)
		{
			do
			{
				option = 0;
				System.out.print("Enter a number (1-3): ");
				option = input.nextInt(); input.nextLine();
			}
			while (option != 1 && option != 2 && option != 3);

			System.out.println();

			switch(option)
			{
				case 1: toolkit.analyze(); break;
				case 2: System.out.print("Enter a range number: "); toolkit.bruteForce(input.nextInt()); input.nextLine(); break;
				case 3:
					for (char c : alphabet)
					{
						System.out.print(String.format("Enter a character substitute for '%c': ", c));
						char in = input.nextLine().toLowerCase().charAt(0);
						toolkit.substitute(c, !((int)in >= 97 && (int)in <= 122) ? '?' : in);
					}
					System.out.println();
					break;
				default: System.exit(0);
			}
		}
	}
}