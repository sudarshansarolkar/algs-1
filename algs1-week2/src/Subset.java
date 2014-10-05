public class Subset {
    public static void main(String[] args) // unit testing
    {
        int k = Integer.parseInt(args[0]);
        String line;
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            line = StdIn.readString();
            q.enqueue(line);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(q.dequeue());
        }
    }
}