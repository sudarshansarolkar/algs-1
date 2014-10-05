import java.util.Iterator;
import java.util.LinkedList;
//import java.util.List;
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

    // private LinkedList<Item> data;
    private MyNode<Item> head;
    private MyNode<Item> tail;
    //private MyNode<Item> cur;
    private int size;

    public Deque() // construct an empty deque
    {
        // data = new LinkedList<Item>();
        size = 0;
    }

    public boolean isEmpty() // is the deque empty?
    {
        // return data.isEmpty();
        return (size == 0);
    }

    public int size() // return the number of items on the deque
    {
        return size;
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
        // data.addFirst(item);
        if (head == null) {
            head = new MyNode<Item>(item, null, null);
            head.setPtr();
            tail = head;
        } else {
            MyNode<Item> tmpHead = head;
            head = new MyNode<Item>(item, tmpHead, null);
            head.setPtr();
        }
        size++;
    }

    public void addLast(Item item) // insert the item at the end
    {
        if (item == null) {
            throw new NullPointerException(
                    "addFirst using null element is not allowed");
        }
        // data.addLast(item);
        if (head == null) {
            head = new MyNode<Item>(item, null, null);
            head.setPtr();
            tail = head;
        } else {

            MyNode<Item> tmpTail = tail;
            tail = new MyNode<Item>(item, null, tmpTail);
            tail.setPtr();
        }
        size++;
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
        // return data.removeFirst();
        Item d = null;
        if (head == tail) {
            d = head.data;
            head = null;
            tail = null;
        } else {
            MyNode<Item> tmpHead = head;
            head = tmpHead.next;
            tmpHead.next.prev = null;
            tmpHead.next = null;
            tmpHead.prev = null;
    
            d = tmpHead.data;
            tmpHead.data = null;
            tmpHead = null;
        }
        size--;
        return d;
    }

    public Item removeLast() // delete and return the item at the end
    {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "attempt to remove element from empty queue");
        }
        // return data.removeLast();
        Item d = null;
        if (head == tail) {
                d = head.data;
                head = null;
                tail = null;
        } else {
            MyNode<Item> tmpTail = tail;
            tail = tmpTail.prev;
            tmpTail.next = null;
            tmpTail.prev.next = null;
            tmpTail.prev = null;
    
            d = tmpTail.data;
            tmpTail.data = null;
            tmpTail = null;
        }
        size--;
        return d;
    }

    /**
     * throw an UnsupportedOperationException if the client calls the remove()
     * method in the iterator; throw a java.util.NoSuchElementException if the
     * client calls the next() method in the iterator and there are no more
     * items to return.
     */

    public Iterator<Item> iterator() // return an iterator over items in order
                                     // from front to end
    {
        return new ListIterator<Item>(head);
    }

    /*
    private void printList() {
        Iterator<Item> qe = this.iterator();
        while (qe.hasNext()) {
            System.out.print(" "+qe.next()); 
        }
        System.out.println();
    }*/
    
    public static void main(String[] args) // unit testing
    {
        Deque<Integer> q = new Deque<Integer>();
        LinkedList<Integer> r = new LinkedList<Integer>();
        
        for (int i = 0; i < 10000; i++) {
            int x = StdRandom.uniform(4);
            if (q.size == 0 || x == 0) {
                q.addFirst(i);
                r.addFirst(i);
            } else if (x == 1) {
                q.addLast(i);
                r.addLast(i);
            } else if (x == 2) {
                q.removeFirst();
                r.removeFirst();
            } else if (x == 3) {
                q.removeLast();
                r.removeLast();
            }
            
            // System.out.println("x="+x+" i="+i);
            // q.printList();
        }
        
        
        Iterator<Integer> qe = q.iterator();
        Iterator<Integer> re = r.iterator();
        /*
        while (re.hasNext()) {
            System.out.print(" "+re.next()); 
        }
        System.out.println();
        while (qe.hasNext()) {
            System.out.print(" "+qe.next()); 
        }
        System.out.println();
        
        
        qe = q.iterator();
        re = r.iterator();
        */
        
        while (re.hasNext() && qe.hasNext()) {
            int qei = qe.next();
            int rei = re.next();
            if (qei != rei) {
                System.out.println("Error qei="+qei +" rei="+rei);
            }
        }

        if (re.hasNext() != qe.hasNext()) {
            System.out.println("Error hasNext");
        }

    }

    private class MyNode<E> {
        private E data;
        private MyNode<E> next;
        private MyNode<E> prev;

        MyNode(E d, MyNode<E> n, MyNode<E> p) {
            data = d;
            next = n;
            prev = p;
        }

        public void setPtr() {
            if (next != null) {
                next.prev = this;
            }
            if (prev != null) {
                prev.next = this;
            }
        }
    }

    private class ListIterator<E> implements Iterator<E> {

        private MyNode<E> cur;

        ListIterator(MyNode<E> h) {
            cur = h;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            E data = cur.data;
            cur = cur.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /*
     * private class ListIterator<E> implements Iterator<E> {
     * 
     * private Iterator<E> it;
     * 
     * ListIterator(LinkedList<E> data) { it = data.listIterator(); }
     * 
     * @Override public boolean hasNext() { return it.hasNext(); }
     * 
     * @Override public E next() { if (!hasNext()) { throw new
     * java.util.NoSuchElementException(); } return it.next(); }
     * 
     * public void remove() { throw new UnsupportedOperationException(); }
     * 
     * } /* private class ListIterator<E> implements Iterator<E> {
     * 
     * private E[] data; private int cur;
     * 
     * ListIterator(E[] data) { this.data = data; cur = 0; }
     * 
     * @Override public boolean hasNext() { return (cur < data.length); }
     * 
     * @Override public E next() { if (!hasNext()) { throw new
     * java.util.NoSuchElementException(); } return data[cur++]; }
     * 
     * public void remove() { throw new UnsupportedOperationException(); }
     * 
     * }
     */
}
