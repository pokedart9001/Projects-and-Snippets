import java.util.Scanner;

public class Converter {
	
	static String convert(String s, int radix1, int radix2) {
		s = s.replaceAll("\\W", "");
        try {
            s = Long.toString(Long.parseLong(s, radix1), radix2).toUpperCase();
        } catch(NumberFormatException nfe) {
            return "Invalid Input";
        }
		if (radix2 == 2) {
			while (s.length() % 4 != 0) {
				s = 0 + s;
			}
			s = s.replaceAll("(.{4})(?!$)", "$1 ");
		}
		return s;
	}
	
	public static String textToBin(String s) {
    
      String bin = "";
    
      for (char i: s.toCharArray()) {
      	bin += convert(Integer.toString((char)i), 10, 2).replaceAll(" ", "");
      }
    
      return bin.replaceAll("(.{8})(?!$)", "$1 ");
    }
	
	public static String binToText(String bin) {
		
		bin = bin.replaceAll("[^01]", "").replaceAll("(.{8})(?!$)", "$1-");
		String[] sArr = bin.split("-");
		String s = "";
		
		for (int i = 0; i < sArr.length; i++) {
			sArr[i] = convert(sArr[i], 2, 10);
			s += (char) Integer.parseInt(sArr[i]);
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a number: ");
		String n = input.nextLine();
		System.out.print("Enter current radix/base (2, 8, 10, or 16): ");
		int r1 = input.nextInt();
		while (r1 != 2 && r1 != 8 && r1 != 10 && r1 != 16) {
			System.out.print("\nInvalid radix/base\nEnter current radix/base (2, 8, 10, or 16): ");
			r1 = input.nextInt();
		}
		System.out.print("Enter new radix/base (2, 8, 10, or 16): ");
		int r2 = input.nextInt();
		while (r2 != 2 && r2 != 8 && r2 != 10 && r2 != 16) {
			System.out.print("\nInvalid radix/base\nEnter new radix/base (2, 8, 10, or 16): ");
			r2 = input.nextInt();
		}
		
		System.out.printf("\n%s\n", convert(n, r1, r2));
	}
}