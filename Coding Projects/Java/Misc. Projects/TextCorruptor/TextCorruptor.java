import java.util.Scanner;
import java.util.ArrayList;

public class TextCorruptor
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String text;

		System.out.print("Enter text: ");
		text = input.nextLine();
		System.out.println();

		System.out.println(corrupt(text, " ", " h ", " H ", " *h* ", " *H* ", " **h** ", " **H** ", " ***h*** ", " ***H*** "));
	}

	public static String corrupt(String text, String... inserts)
	{
		StringBuilder sb = new StringBuilder(text);
		for (int i = 1; i < sb.length(); i++)
		{
			String insert = (int)(Math.random() * 3) == 0 ? "~~" + inserts[randomRange(0, inserts.length)] + "~~ " : "";
			sb.insert(i, insert);
			i += (int)(Math.random() * 5) == 0 ? insert.length() + 1 : insert.length();
		}
		return "~~" + sb.toString() + "~~";
	}

	public static int randomRange(int a, int b)
	{
		int start = Math.min(a, b);
		int end = Math.max(a, b);
		return start + (int)(Math.random() * (end - start));
	}
}