public class Outcast {
   private WordNet w;
   public Outcast(WordNet wordnet)         // constructor takes a WordNet object
   {
       w = wordnet;
   }
   public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
   {
       
       long distMax = -1;
       String outcast = null;
       for(int i=0; i < nouns.length; i++){
           long dist = 0;
           for(int j=0; j < nouns.length ; j++){
               
               if(i != j){
                   dist += w.distance(nouns[i], nouns[j]); 
               }
               //System.out.printf("nA:%s nB:%s dist:%d idstMax:%d\n",nouns[i],nouns[j],dist,distMax);
           }
           //System.out.printf("nA:%s dist:%d idstMax:%d\n",nouns[i], dist,distMax);
           if(dist > distMax){
               distMax = dist;
               outcast = nouns[i];
           }
       }
       
       return outcast;
   }
   public static void main(String[] args)  // see test client below
   {
       WordNet wordnet = new WordNet(args[0], args[1]);
       Outcast outcast = new Outcast(wordnet);
       for (int t = 2; t < args.length; t++) {
           In in = new In(args[t]);
           String[] nouns = in.readAllStrings();
           StdOut.println(args[t] + ": " + outcast.outcast(nouns));
       }

       
   }
}
