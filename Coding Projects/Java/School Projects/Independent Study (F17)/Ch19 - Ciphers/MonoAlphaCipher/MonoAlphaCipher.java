import java.util.*;
import java.io.*;

public class MonoAlphaCipher
{
	String keyword;
	char[] alphabet, reference;
	Scanner reader;
	FileWriter writer;

	public MonoAlphaCipher(String keyword)
	{
		setKeyword(keyword);
	}

	public void setKeyword(String keyword)
	{
		this.keyword = removeDuplicates(keyword.toUpperCase().replaceAll("\\s", ""));
		populateReferences();
	}

	private String removeDuplicates(String keyword)
	{
		return new String(removeDuplicates(keyword.toCharArray()));
	}

	private char[] removeDuplicates(char[] chars)
	{
		Set<Character> present = new HashSet<>();
		int len = 0;
		for (char c : chars)
			if (present.add(c))
				chars[len++] = c;

		return Arrays.copyOf(chars, len);
	}

	private void populateReferences()
	{
		char[] chars = new char[keyword.length() + 26];
		alphabet = new char[26];

		int len = 0;
		for (char c : keyword.toCharArray())
			chars[len++] = c;
		for (char c = 'Z'; c >= 'A'; c--)
		{
			chars[len++] = c;
			alphabet[c-65] = c;
		}

		reference = removeDuplicates(chars);
	}

	private char change(char in, boolean decrypt)
	{
		char[] before;
		char[] after;
		if (decrypt)
		{
			before = reference;
			after = alphabet;
		}
		else
		{
			before = alphabet;
			after = reference;
		}

		for (int i = 0; i < before.length; i++)
		{
			if (before[i] == in)
				return after[i];
			if (before[i] + 32 == in)
				return (char)(after[i] + 32);
		}

		return in;
	}

	public void encrypt(String in, String out, boolean decrypt) throws FileNotFoundException, IOException
	{
		reader = new Scanner(new File(in));
		writer = new FileWriter(new File(out));
		
		while (reader.hasNextLine())
		{
			for (char c : reader.nextLine().toCharArray())
				writer.write(change(c, decrypt));
			writer.write('\n');
		}

		reader.close();
		writer.close();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		if (args.length < 4 || !args[0].equals("-k"))
		{
			System.out.println("Error! Invalid/Insufficient arguments.");
			System.out.println("Usage: java MonoAlphaCipher -k <keyword> <input file name> <output file name> [-d].");
			System.exit(0);
		}

		new MonoAlphaCipher(args[1]).encrypt(args[2], args[3], args.length > 4 && args[4].equals("-d"));
	}
}