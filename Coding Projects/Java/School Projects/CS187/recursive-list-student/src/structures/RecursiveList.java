package structures;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T>
{
	private int size;
	private Node<T> head;

	public RecursiveList()
	{
		head = null;
		size = 0;
	}

	@Override
	public Iterator<T> iterator()
	{
		return new NodeIterator<T>(head);
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public ListInterface<T> insertFirst(T elem)
	{
		if (elem == null)
			throw new NullPointerException();
		head = new Node<T>(elem, head);
		size++;
		return this;
	}

	@Override
	public ListInterface<T> insertLast(T elem)
	{
		if (elem == null)
			throw new NullPointerException();
		if (head == null)
			head = new Node<T>(elem);
		else
			getLastNode(head).link = new Node<T>(elem);
		size++;
		return this;
	}

	private Node<T> getLastNode(Node<T> node)
	{
		return node.link == null ? node : getLastNode(node.link);
	}

	@Override
	public ListInterface<T> insertAt(int index, T elem)
	{
		if (elem == null)
			throw new NullPointerException();
		if (index == 0)
			return insertFirst(elem);
		Node<T> node = getNodeAt(index - 1, head);
		node.link = new Node<T>(elem, node.link);
		size++;
		return this;
	}

	private Node<T> getNodeAt(int index, Node<T> node)
	{
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return index == 0 ? node : getNodeAt(--index, node.link);
	}

	@Override
	public T removeFirst()
	{
		T element = getFirst();
		head = head.link;
		size--;
		return element;
	}

	@Override
	public T removeLast()
	{
		if (isEmpty())
			throw new IllegalStateException();
		return removeAt(size - 1);
	}

	@Override
	public T removeAt(int i)
	{
		if (i < 0 || i >= size)
			throw new IndexOutOfBoundsException();
		if (head.link == null || i == 0)
			return removeFirst();
		Node<T> before = getNodeAt(i - 1, head);
		T element = before.link.data;
		before.link = before.link.link;
		size--;
		return element;
	}

	@Override
	public T getFirst()
	{
		if (isEmpty())
			throw new IllegalStateException();
		return head.data;
	}

	@Override
	public T getLast()
	{
		if (isEmpty())
			throw new IllegalStateException();
		return getLastNode(head).data;
	}

	@Override
	public T get(int i)
	{
		return getNodeAt(i, head).data;
	}

	@Override
	public boolean remove(T elem)
	{
		int index = indexOf(elem);
		if (index != -1)
		{
			removeAt(index);
			return true;
		}
		return false;
	}

	@Override
	public int indexOf(T elem)
	{
		if (elem == null)
			throw new NullPointerException();
		return indexOf(elem, head, 0);
	}

	private int indexOf(T elem, Node<T> node, int index)
	{
		return node == null ? -1 : node.data.equals(elem) ? index : indexOf(elem, node.link, ++index);
	}

	@Override
	public boolean isEmpty()
	{
		return head == null;
	}
}

class Node<T>
{
	public T data;
	public Node<T> link;

	public Node(T data, Node<T> link)
	{
		this.data = data;
		this.link = link;
	}

	public Node(T data)
	{
		this(data, null);
	}
}