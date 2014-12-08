import java.util.TreeMap;
import java.util.TreeSet;

public class WordNet {
    Digraph g ;
    TreeMap<String, Integer> synMap;
   // constructor takes the name of the two input files
    // throw java.lang.IllegalArgumentException if not rooted dag
   public WordNet(String synsets, String hypernyms){
       if(synsets == null || hypernyms == null)
           throw new NullPointerException("null arguments");
       
           synMap = new TreeMap<String, Integer>();
           In in = new In(synsets);
           int count =0;
           while (!StdIn.isEmpty()) {
               String line = StdIn.readLine();
               String[] vals = line.split(",");
               Integer id = Integer.parseInt(vals[0]);
               String[] syns = vals[1].split(" ");
               for(String s: syns){
                   synMap.put(s, id);
               }
               count++;
           }
           g=new Digraph(count);
           
           in = new In(hypernyms);
           while (!StdIn.isEmpty()) {
               String line = StdIn.readLine();
               String[] vals = line.split(",");
               
               int i =0;
               Integer id=0;
               for(String s: vals){
                   if(i==0){
                       id = Integer.parseInt(vals[0]);
                   }else{
                       Integer val = Integer.parseInt(vals[0]);
                       g.addEdge(val,id);
                   }
                   i++;
                  
               }
           }
           
          //g.
   }

   // returns all WordNet nouns
   public Iterable<String> nouns(){
       return synMap.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word){
       if(word == null)
           throw new NullPointerException("null arguments");
       return synMap.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB){
       if(nounA == null || nounB == null)
           throw new NullPointerException("null arguments");
       return 0;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB){
       if(nounA == null || nounB == null)
           throw new NullPointerException("null arguments");
       return null;
   }

   // do unit testing of this class
   public static void main(String[] args){
       
   }
}
