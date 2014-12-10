public class SAP {
    private Digraph G;
    private int minPath;
    private int ca;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G){
       if(G == null)
           throw new NullPointerException("null arguments");
       this.G=G;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w){
       shortestPath(v,w);
       return minPath;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w){
       shortestPath(v,w);
       return ca;
   }
   
   private void shortestPath(int v, int w){
       BreadthFirstDirectedPaths bp1 = new  BreadthFirstDirectedPaths(G,v);
       BreadthFirstDirectedPaths bp2 = new  BreadthFirstDirectedPaths(G,w);
       
       minPath=Integer.MAX_VALUE;
       ca =-1;
       boolean notFound = true;
       for (int i=0;i<G.V();i++){
           if(bp1.hasPathTo(i) && bp2.hasPathTo(i)){
               int t = bp1.distTo(i)+bp2.distTo(i);
               if(t < minPath){
                   minPath = t;
                   ca = i;
                   notFound = false;
               }
           }
       }
       
       if(notFound){
           minPath = -1;
       }
       
       
   }
   
   private void shortestPath(Iterable<Integer> v, Iterable<Integer> w){
       BreadthFirstDirectedPaths bp1 = new  BreadthFirstDirectedPaths(G,v);
       BreadthFirstDirectedPaths bp2 = new  BreadthFirstDirectedPaths(G,w);
       
       minPath=Integer.MAX_VALUE;
       ca =-1;
       boolean notFound = true;
       
       for (int i=0;i<G.V();i++){
           if(bp1.hasPathTo(i) && bp2.hasPathTo(i)){
               int t = bp1.distTo(i)+bp2.distTo(i);
               if(t < minPath){
                   minPath = t;
                   ca = i;
                   notFound = false;
               }
           }
       }
       if(notFound){
           minPath = -1;
       }
       
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w){
       if(v == null || w == null)
           throw new NullPointerException("null arguments");
       shortestPath(v,w);
       return minPath;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
       if(v == null || w == null)
           throw new NullPointerException("null arguments");
       shortestPath(v,w);
       return ca;
   }

   // do unit testing of this class
   public static void main(String[] args){
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
       
       // horse and zebra
       //[28075, 46599, 46600, 49952, 68015], [82086]

   }
}
