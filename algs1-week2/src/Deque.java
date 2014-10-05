import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 
 * @author Sudarshan
 *
 * @param <Item>
 * 
 *            Your deque implementation must support each deque operation in
 *            constant worst-case time and use space proportional to the number
 *            of items currently in the deque. Additionally, iterator
 *            implementation must support the operations next() and hasNext()
 *            (plus construction) in constant worst-case time and use a constant
 *            amount of extra space per iterator.
 */
public class Deque<Item> implements Iterable<Item> {

    private LinkedList<Item> data;

    public Deque() // construct an empty deque
    {
        data = new LinkedList<Item>();
    }

    public boolean isEmpty() // is the deque empty?
    {
        return data.isEmpty();
    }

    public int size() // return the number of items on the deque
    {
        return data.size();
    }

    /**
     * Throw a NullPointerException if the client attempts to add a null item;
     * 
     * @param item
     */
    public void addFirst(Item item) // insert the item at the front
    {
        if (item == null) {
            throw new NullPointerException(
                    "addFirst using null element is not allowed");
        }
        data.addFirst(item);
    }

    public void addLast(Item item) // insert the item at the end
    {
        if (item == null) {
            throw new NullPointerException(
                    "addFirst using null element is not allowed");
        }
        data.addLast(item);
    }

    /**
     * throw a java.util.NoSuchElementException if the client attempts to remove
     * an item from an empty deque;
     */
    public Item removeFirst() // delete and return the item at the front
    {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "attempt to remove element from empty queue");
        }
        return data.removeFirst();
    }

    public Item removeLast() // delete and return the item at the end
    {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "attempt to remove element from empty queue");
        }
        return data.removeLast();
    }

    /**
     * throw an UnsupportedOperationException if the client calls the remove()
     * method in the iterator; throw a java.util.NoSuchElementException if the
     * client calls the next() method in the iterator and there are no more
     * items to return.
     */
    @SuppressWarnings("unchecked")
    public Iterator<Item> iterator() // return an iterator over items in order
                                     // from front to end
    {
        return new ListIterator<Item>((Item[]) data.toArray());
    }

    public static void main(String[] args) // unit testing
    {

    }
}

class ListIterator<E> implements Iterator<E> {

    private E[] data;
    private int cur;

    ListIterator(E[] data) {
        this.data = data;
        cur = 0;
    }

    @Override
    public boolean hasNext() {
        return (cur < data.length);
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        return data[cur++];
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}