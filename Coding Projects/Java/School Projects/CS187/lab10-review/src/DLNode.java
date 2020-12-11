public class DLNode<T> {
	
	public T data;
	public DLNode<T> next;
	public DLNode<T> prev;
	
	public DLNode(T element) {
		data = element;
	}
	
	public DLNode(T element, DLNode<T> n, DLNode<T> p) {
		data = element;
		next = n;
		prev = p;
	}
}