import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Sudarshan
 *
 * @param <Item>
 *            Throw a NullPointerException if the client attempts to add a null
 *            item; throw a java.util.NoSuchElementException if the client
 *            attempts to sample or dequeue an item from an empty randomized
 *            queue; throw an UnsupportedOperationException if the client calls
 *            the remove() method in the iterator; throw a
 *            java.util.NoSuchElementException if the client calls the next()
 *            method in the iterator and there are no more items to return.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data; // array of items
    private int N; // number of elements on stack

    //@SuppressWarnings("unchecked")
    public RandomizedQueue() // construct an empty randomized queue
    {
        data = (Item[]) new Object[2];
    }

    public boolean isEmpty() // is the queue empty?
    {
        return N == 0;

    }

    public int size() // return the number of items on the queue
    {
        return N;

    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = data[i];
        }
        data = temp;
    }

    public void enqueue(Item item) // add the item
    {
        if (item == null) {
            throw new NullPointerException("null element is not allowed");
        }
        if (N == data.length)
            resize(2 * data.length); // double size of array if necessary
        if (N > 1) {
            int randLoc = StdRandom.uniform(N);
            Item temp = data[randLoc];
            data[randLoc] = item;
            data[N++] = temp; // add item
        } else {
            data[N++] = item;
        }

    }

    public Item dequeue() // delete and return a random item
    {
        if (isEmpty())
            throw new NoSuchElementException("underflow");
        Item item = data[N - 1];
        if (N > 1) {
            int randLoc = StdRandom.uniform(N);
            Item temp = data[randLoc];
            data[randLoc] = item;
            item = temp;
        }
       
        
        data[N - 1] = null; // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == data.length / 4)
            resize(data.length / 2);
        return item;

    }

    public Item sample() // return (but do not delete) a random item
    {
        if (isEmpty())
            throw new NoSuchElementException("underflow");
        int randLoc = StdRandom.uniform(N);
        return data[randLoc];

    }

    public Iterator<Item> iterator() // return an independent iterator over
                                     // items in random order
    {
        return new RandListIterator<Item>(data, N);
    }

    public static void main(String[] args) // unit testing
    {

    }
    
    class RandListIterator<E> implements Iterator<E> {

        private E[] data;
        private int cur;

        
        RandListIterator(E[] d, int size) {
            this.data = (E[]) new Object[size];
            int index = 0;
            for (int i = size - 1; i >= 0; i--) {
                data[index++] = d[i];
            }
            StdRandom.shuffle(data);
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
}

