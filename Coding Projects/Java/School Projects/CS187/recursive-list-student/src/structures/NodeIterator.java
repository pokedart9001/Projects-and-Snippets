package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NodeIterator<E> implements Iterator<E>
{

	Node<E> node;

	public NodeIterator(Node<E> head)
	{
		node = head;
	}

	@Override
	public boolean hasNext()
	{
		return node != null;
	}

	@Override
	public E next()
	{
		if (!hasNext())
			throw new NoSuchElementException();
		E element = node.data;
		node = node.link;
		return element;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}