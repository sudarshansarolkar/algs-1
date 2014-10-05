import static org.junit.Assert.*;

import org.junit.Test;


public class RandomizedQueueTest {

    
    @Test
    public void checkRandomnessOfDequeue() {
        fun2(3000, 3, 'C');
        fun2(5000, 5, 'A');
        fun2(8000, 8, 'B');
        fun2(10000, 10, 'H');
    }
    
    private void fun2 (int r, int nc, char s) {
        // Enqueue strings A to C and call dequeue() until C is dequeued;
        char[] letters = {'A','B','C','D','E','F','G','H','I','J'};
        RandomizedQueue instance = new RandomizedQueue();
        int[] results = new int[nc];
        for (int i = 0; i < r; i++) {
            
            for (int j = 0; j < nc; j++)
                instance.enqueue(letters[j]);
            
            for (int j = 0; j < nc; j++)
                if (s == (char)instance.dequeue())
                    results[j]++;
        }
        
        // Print results
        System.out.print("             ");
        for (int i = 0; i<nc; i++)
            System.out.printf("\t%d",i+1);
        
        System.out.print("\nobserved freq");
        int sumNc = 0;
        for (int i = 0; i<nc; i++) {
            System.out.printf("\t%d",results[i]);
            sumNc += results[i];
        }
        
        System.out.printf("\t%d\nexpected freq", sumNc);
        for (int i = 0; i<nc; i++)
            System.out.printf("\t%d",r/nc);
        
        System.out.printf("\t%d\n\n", r);
    }
}
