import java.util.*;
import java.io.*;

public class Database
{
	RandomAccessFile data;
	int maxIndex;

	public Database() throws FileNotFoundException
	{
		data = new RandomAccessFile("records.dat", "rw");
	}

	public void add(String name, double price, int quantity) throws IOException
	{
		data.seek(data.length());
		for (int i = 0; i < 30; i++)
			data.writeChar(Arrays.copyOf(name.toCharArray(), 30)[i]);
		data.writeDouble(price);
		data.writeInt(quantity);
	}

	public int findIndex(String name) throws IOException
	{
		for (int i = 0; i < data.length()/72; i++)
			if (getName(i).equals(name))
				return i;
		return -1;
	}

	public String getName(int index) throws IOException
	{
		StringBuilder sb = new StringBuilder();

		data.seek(index * 72);
		for (int i = 0; i < 30; i++)
			sb.append(data.readChar());

		return sb.toString();
	}

	public double getPrice(int index) throws IOException
	{
		data.seek(index * 72 + 60);
		return data.readDouble();
	}

	public int getQuantity(int index) throws IOException
	{
		data.seek(index * 72 + 68);
		return data.readInt();
	}

	public void editName(int index, String name) throws IOException
	{
		data.seek(index * 72);
		for (int i = 0; i < 30; i++)
			data.writeChar(Arrays.copyOf(name.toCharArray(), 30)[i]);
	}

	public void editPrice(int index, double price) throws IOException
	{
		data.seek(index * 72 + 60);
		data.writeDouble(price);
	}

	public void editQuantity(int index, int quantity) throws IOException
	{
		data.seek(index * 72 + 68);
		data.writeInt(quantity);
	}

	public void edit(int index, String name, double price, int quantity) throws IOException
	{
		editName(index, name);
		editPrice(index, price);
		editQuantity(index, quantity);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Database test = new Database();
		test.data.setLength(0);
		test.add("Coke", 1.99, 2);
		test.add("Pepsi", 2.99, 4);
		System.out.println("Before editing... \n");
		System.out.println("Name 1: " + test.getName(0));
		System.out.println("Price 1: $" + test.getPrice(0));
		System.out.println("Quantity 1: " + test.getQuantity(0));
		System.out.println();
		System.out.println("Name 2: " + test.getName(1));
		System.out.println("Price 2: $" + test.getPrice(1));
		System.out.println("Quantity 2: " + test.getQuantity(1));
		System.out.println();

		test.edit(0, "Doritos", 5.99, 5);
		test.edit(1, "Mountain Dew", 10.99, 3);
		System.out.println("After editing... \n");
		System.out.println("Name 1: " + test.getName(0));
		System.out.println("Price 1: $" + test.getPrice(0));
		System.out.println("Quantity 1: " + test.getQuantity(0));
		System.out.println();
		System.out.println("Name 2: " + test.getName(1));
		System.out.println("Price 2: $" + test.getPrice(1));
		System.out.println("Quantity 2: " + test.getQuantity(1));
		System.out.println();
	}
}