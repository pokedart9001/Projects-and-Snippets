class Node<T>
{
	public T info;
	public Node<T> link;

	public Node(T in, Node<T> li)
	{
		info = in;
		link = li;
	}
}

class LinkedList<T extends Comparable<T>>
{

	protected Node<T> head = null;

	public LinkedList<T> add(T elem)
	{
		head = new Node<T>(elem, head);
		return this;
	}

	public void print()
	{
		for (Node<T> node = head; node != null; node = node.link)
		{
			System.out.print(node.info + " ");
		}
		System.out.println("");
	}

	public T maxValue()
	{
		if (head == null)
			return null;
		T max = head.info;
		for (Node<T> node = head; node != null; node = node.link)
			if (node.info.compareTo(max) > 0)
				max = node.info;
		return max;
	}

	public void threshold(T thres)
	{
		for (Node<T> node = head; node != null; node = node.link)
			if (node.link != null && node.link.info.compareTo(thres) >= 0)
				node.link = node.link.link;
		if (head.info.compareTo(thres) >= 0)
				head = head.link;
	}
}

public class Lab08
{
	public static void main(String args[])
	{

		LinkedList<Integer> list = new LinkedList<Integer>();
		System.out.println(list.maxValue()); // should be null
		list.add(20).add(30).add(10);
		System.out.println(list.maxValue()); // should be 30
		list.threshold(40);
		list.print(); // should print out all elements
		list.threshold(30);
		list.print(); // should print out 10 20
		list.threshold(10);
		list.print(); // should print nothing
	}
}