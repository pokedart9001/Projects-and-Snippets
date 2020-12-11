import java.util.*;
import java.io.*;

public class LetterFrequencies
{
	File in;
	Scanner reader;
	FileWriter writer;
	char[] alphabet;
	int[] freqs;

	public LetterFrequencies(File in) throws FileNotFoundException, IOException
	{
		this.in = in;
		reader = new Scanner(in);
		writer = new FileWriter("letter_frequencies.txt");
		freqs = new int[26];
		alphabet = WordList.alphabet;
		for (int i = 65; i < 91; i++)
			alphabet[i-65] = (char)i;
	}

	public void generateOutput() throws FileNotFoundException, IOException
	{
		while (reader.hasNextLine())
			for (char c : reader.nextLine().toCharArray())
				for (int i = 0; i < 26; i++)
					if (c == alphabet[i] || c == alphabet[i] + 32)
						freqs[i]++;

		writer.write("Letter Frequencies in " + in.getName() + ":\n\n");
		for (int i = 0 ; i < 26; i++)
			writer.write((char)(alphabet[i]) + " - " + percent(freqs[i]) + "\n");

		reader.close();
		writer.close();
	}

	public String percent(int val)
	{
		int sum = 0;
		for (int i : freqs)
			sum += i;

		return String.format("%.01f", (float)val / sum * 100) + "%";
	}
}