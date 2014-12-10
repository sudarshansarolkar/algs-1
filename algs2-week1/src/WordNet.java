import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordNet {
    private Digraph g ;
    private  TreeMap<String, Integer> synMap;
    private TreeMap<String, ArrayList<Integer>> synMap2;
    private TreeMap<Integer, String[]> rSynMap;
    private SAP sap;
   // constructor takes the name of the two input files
    // throw java.lang.IllegalArgumentException if not rooted dag
   public WordNet(String synsets, String hypernyms){
       if(synsets == null || hypernyms == null)
           throw new NullPointerException("null arguments");
       
           synMap = new TreeMap<String, Integer>();
           synMap2 = new TreeMap<String, ArrayList<Integer>>();
           rSynMap = new TreeMap<Integer, String[]>();
           In in = new In(synsets);
           int count =0;
           while (!in.isEmpty()) {
               String line = in.readLine();
               String[] vals = line.split(",");
               Integer id = Integer.parseInt(vals[0]);
               String[] syns = vals[1].split(" ");
               synMap.put(vals[2], id);
               ArrayList<Integer> lst = null;
               for(String s: syns){
                   lst = synMap2.get(s);
                   if(synMap2.containsKey(s)){
                       lst.add(id); 
                   } else {
                       lst = new ArrayList<Integer>();
                       lst.add(id);
                       synMap2.put(s,lst);
                   }
               }
               rSynMap.put(id, syns);
               count++;
           }
           g=new Digraph(count);
           
           in = new In(hypernyms);
           while (!in.isEmpty()) {
               String line = in.readLine();
               String[] vals = line.split(",");
               
               int i =0;
               Integer id=0;
               for(String s: vals){
                   if(i==0){
                       id = Integer.parseInt(s);
                   }else{
                       Integer val = Integer.parseInt(s);
                       g.addEdge(id,val);
                   }
                   i++;
                  
               }
           }
           sap =new SAP(g);
          //g.
   }

   // returns all WordNet nouns
   public Iterable<String> nouns(){
       return synMap2.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word){
       if(word == null)
           throw new NullPointerException("null arguments");
       return synMap2.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB){
       if(nounA == null || nounB == null)
           throw new NullPointerException("null arguments");
       
       //System.out.printf("length %s, %s\n",synMap2.get(nounA) ,synMap2.get(nounB));
       int ret = sap.length(synMap2.get(nounA) ,synMap2.get(nounB));
       if(ret == -1){
           ret = Integer.MAX_VALUE;
       }
       return ret;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB){
       if(nounA == null || nounB == null)
           throw new NullPointerException("null arguments");
       
       int ca =  sap.ancestor(synMap2.get(nounA) ,synMap2.get(nounB));
       return rSynMap.get(ca)[0];
   }

   // do unit testing of this class
   public static void main(String[] args){
       WordNet wn = new WordNet(args[0], args[1]);
       Digraph G = wn.g;
       SAP sap = wn.sap;
       /*
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
       */
       
       // horse and zebra
       //[28075, 46599, 46600, 49952, 68015], [82086]
       
       ArrayList<Integer> synA = new ArrayList<Integer>();
       ArrayList<Integer> synB = new ArrayList<Integer>();
       
       synA.add(28075);
       synA.add(46599);
       synA.add(46600);
       synA.add(49952);
       synA.add(68015);
       
       synB.add(82086);
       
       int length   = sap.length(46599, 82086);
       int ancestor = sap.ancestor(46599, 82086);
       StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       
       
       length   = sap.length(synA, synB);
       ancestor = sap.ancestor(synA, synB);
       StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       
       
   }
}
