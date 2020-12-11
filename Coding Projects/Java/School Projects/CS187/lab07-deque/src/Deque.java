import java.util.NoSuchElementException;

public interface Deque<T> {
    public void addToFront(T element);
    public T removeFront() throws NoSuchElementException;
    public T first() throws NoSuchElementException;
    public void addToRear(T element);
    public T removeRear() throws NoSuchElementException;
    public T last()  throws NoSuchElementException;
    public boolean isEmpty();
}