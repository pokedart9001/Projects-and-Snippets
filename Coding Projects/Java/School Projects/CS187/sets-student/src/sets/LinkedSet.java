package sets;

import java.util.Iterator;

/**
 * A LinkedList-based implementation of Set
 */

/********************************************************
 * NOTE: Before you start, check the Set interface in
 * Set.java for detailed description of each method.
 *******************************************************/

/********************************************************
 * NOTE: for this project you must use linked lists
 * implemented by yourself. You are NOT ALLOWED to use
 * Java arrays of any type, or any Collection-based class 
 * such as ArrayList, Vector etc. You will receive a 0
 * if you use any of them.
 *******************************************************/

/********************************************************
 * NOTE: you are allowed to add new methods if necessary, but do NOT add new
 * files (as they will be ignored).
 *******************************************************/

public class LinkedSet<E> implements Set<E> {
	private LinkedNode<E> head = null;

	// Constructors
	public LinkedSet() {
	}

	public LinkedSet(E e) {
		this.head = new LinkedNode<E>(e, null);
	}

	private LinkedSet(LinkedNode<E> head) {
		this.head = head;
	}

	@Override
	@SuppressWarnings("unused")
	public int size() {
		int size = 0;
		for (E e : this)
			size++;
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedNodeIterator<E>(this.head);
	}

	@Override
	public boolean contains(Object o) {
		for (E e : this)
			if (e.equals(o))
				return true;
		return false;
	}

	@Override
	public boolean isSubset(Set<E> that) {
		for (E e : this)
			if (!that.contains(e))
				return false;
		return true;
	}

	@Override
	public boolean isSuperset(Set<E> that) {
		return that.isSubset(this);
	}

	@Override
	public Set<E> adjoin(E e) {
		return contains(e) ? this : new LinkedSet<E>(new LinkedNode<E>(e, head));
	}

	@Override
	public Set<E> union(Set<E> that) {
		Set<E> result = new LinkedSet<E>(head);
		for (E e : that)
			result = result.adjoin(e);
		return result;
	}

	@Override
	public Set<E> intersect(Set<E> that) {
		Set<E> result = new LinkedSet<E>();
		for (E e : this)
			if (that.contains(e))
				result = result.adjoin(e);
		return result;
	}

	@Override
	public Set<E> subtract(Set<E> that) {
		Set<E> result = new LinkedSet<E>(head);
		for (E e : that)
			result = result.remove(e);
		return result;
	}

	@Override
	public Set<E> remove(E e) {
		Set<E> result = new LinkedSet<E>();
		for (E item : this)
			if (!item.equals(e))
				result = result.adjoin(item);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Set)) {
			return false;
		}
		Set<E> that = (Set<E>) o;
		return this.isSubset(that) && that.isSubset(this);
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (E e : this) {
			result += e.hashCode();
		}
		return result;
	}
}
