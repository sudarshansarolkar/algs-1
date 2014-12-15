import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class SeamCarver {
   private RGB data [][]; 
  
   private int height;
   private int width;
   private boolean isTransposted;
   
   public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
   {
       height = picture.height();
       width = picture.width();
       data = new RGB[width][height];
       
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               data[j][i] = new RGB();
               data[j][i].R = (byte) picture.get(j, i).getRed();
               data[j][i].G = (byte) picture.get(j, i).getGreen();
               data[j][i].B = (byte) picture.get(j, i).getBlue();
           }
   }
   public Picture picture()                          // current picture
   {
       Picture p = new Picture(width, height);
       
           for(int i =0; i<height;i++)
               for(int j =0; j<width;j++){
               Color color = new Color(data[j][i].R, data[j][i].G, data[j][i].B);
               p.set(i, j, color);
           }
       return p;   
   }
   public     int width()                            // width of current picture
   {
       return width;
   }
   public     int height()                           // height of current picture
   {
       return height;
   }
   public  double energy(int x, int y)               // energy of pixel at column x and row y
   {
       if(x == 0 || x == width-1 || y==0 || y == height -1)
           return 195075;
       
       // x(x, y), and x(x, y) are the absolute value in differences of red, green, 
       // and blue components between pixel d(x+1, y) and pixel d(x-1, y)
       
       double delta_x= 0;
       double delta_y= 0;
       double temp = 0;
       
       temp = (data[x-1][y].R - data[x+1][y].R);
       delta_x += temp*temp;
       temp = (data[x-1][y].G - data[x+1][y].G);
       delta_x += temp*temp;
       temp = (data[x-1][y].B - data[x+1][y].B);
       delta_x += temp*temp;
       
       temp = (data[x][y-1].R - data[x][y+1].R);
       delta_x += temp*temp;
       temp = (data[x][y-1].G - data[x][y+1].G);
       delta_x += temp*temp;
       temp = (data[x][y+1].B - data[x][y+1].B);
       delta_x += temp*temp;
       
       return (delta_x + delta_y);
   }
   public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
   {
       PriorityQueue<Frontier> pq = new PriorityQueue<Frontier>(new Comparator<Frontier>() {
           public int compare(Frontier f1, Frontier f2) {
               return Double.compare(f1.cost,f2.cost);
           }
       });
       
       double cost[][] = new double[width][height];
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               cost[j][i] = Integer.MAX_VALUE;
           }
       for(int i =0; i<height;i++){
           Frontier f = new Frontier();
           f.cost = energy(0,i);
           cost[0][i] = f.cost;
           f.path.add(i);
           pq.add(f);
       }
       
       int w=0;
       while(w < width-1 && !pq.isEmpty()){
           Frontier f = pq.poll();
           int idx = f.path.getLast();
           
           Frontier f1 = new Frontier();
           f1.cost = energy(f.h+1,idx);
           f1.path = (LinkedList<Integer>) f.path.clone();
           f1.path.add(idx);
           
           if(f.h + 1 > w){
               w = f.h + 1;
               
           } 
           f1.h = f.h + 1;
           if(cost[f1.h][idx] > f1.cost ){
               pq.add(f1);
               cost[f1.h][idx] = f1.cost;
           }
           
           if(idx > 1){
               f1 = new Frontier();
               f1.cost = energy(f.h + 1, idx - 1);
               f1.path = (LinkedList<Integer>) f.path.clone();
               f1.path.add(idx - 1);
               f1.h = f.h + 1;
               if(cost[f1.h][idx - 1] > f1.cost ){
                   cost[f1.h][idx - 1] = f1.cost;
                   pq.add(f1);
               }
           }
           
           if(idx < height-1){
               f1 = new Frontier();
               f1.cost = energy(f.h + 1, idx + 1);
               f1.path = (LinkedList<Integer>) f.path.clone();
               f1.path.add(idx + 1);
               f1.h = f.h + 1;
               if(cost[f1.h][idx + 1] > f1.cost ){
                   cost[f1.h][idx + 1] = f1.cost;
                   pq.add(f1);
               }
           }

       }
       
       Frontier f = pq.poll();
       while(f.h < width -1 ){
           f = pq.poll();
       }
       
       int res[] = new int[f.path.size()];
       
       int index = 0;
       for(int i : f.path){
           res[index++] = i;
       }
       
       return res;
   }
   public   int[] findVerticalSeam()                 // sequence of indices for vertical seam
   {
       
       PriorityQueue<Frontier> pq = new PriorityQueue<Frontier>(new Comparator<Frontier>() {
           public int compare(Frontier f1, Frontier f2) {
               return Double.compare(f1.cost,f2.cost);
           }
       });
       
       double cost[][] = new double[width][height];
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               cost[j][i] = Integer.MAX_VALUE;
       }
       
       
       for(int i =0; i<height;i++){
           Frontier f = new Frontier();
           f.cost = energy(i,0);
           cost[i][0] = f.cost;
           f.path.add(i);
           pq.add(f);
       }
       
       int h=0;
       while(h < height-1){
           Frontier f = pq.poll();
           int idx = f.path.getLast();
           
           Frontier f1 = new Frontier();
           f1.cost = energy(idx,f.h+1);
           f1.path = (LinkedList<Integer>) f.path.clone();
           f1.path.add(idx);
           
           if(f.h + 1 > h){
               h = f.h + 1;
           }
           f1.h = f.h + 1;
           if(cost[idx][f1.h] > f1.cost ){
               cost[idx][f1.h] = f1.cost;
               pq.add(f1);
           }
           
           if(idx > 1){
               f1 = new Frontier();
               f1.cost = energy(idx - 1,f.h + 1);
               f1.path = (LinkedList<Integer>) f.path.clone();
               f1.path.add(idx - 1);
               f1.h = f.h + 1;
               if(cost[idx][f1.h] > f1.cost ){
                   cost[idx][f1.h] = f1.cost;
                   pq.add(f1);
               }
               
           }
           
           if(idx < width-1){
               f1 = new Frontier();
               f1.cost = energy(idx + 1,f.h + 1);
               f1.path = (LinkedList<Integer>) f.path.clone();
               f1.path.add(idx + 1);
               f1.h = f.h + 1;
               if(cost[idx][f1.h] > f1.cost ){
                   cost[idx][f1.h] = f1.cost;
                   pq.add(f1);
               }
               
           }

       }
       
       Frontier f = pq.poll();
       while(f.h < height -1 ){
           f = pq.poll();
       }
       int res[] = new int[f.path.size()];
       
       int index = 0;
       for(int i : f.path){
           res[index++] = i;
       }
       
       return res;
   }
   public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
   {
       if(seam == null)
           throw new NullPointerException("Null reference to seam");
       
       if(seam.length !=  width-1)
           throw new IllegalArgumentException ("seam.length !=  width");
       
     transpose();
     removeVerticalSeam(seam);
     transpose();
       
   }
   public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
   {
       if(seam == null)
           throw new NullPointerException("Null reference to seam");
       if(seam.length !=  width-1)
           throw new IllegalArgumentException ("seam.length !=  height");
       
       for(int i =0; i<width;i++){
           RGB t[] = new RGB[height-1];
            System.arraycopy(data[i], 0, t, 0, seam[i]-1);
            System.arraycopy(data[i], seam[i]+1, t, seam[i], height-seam[i]);
            data[i] = t;
        }
        height=height-1;
   }
   
   private void transpose(){
       
       RGB data_t [][] = new RGB[height][width];
       
       data_t = new RGB[height][width];
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               data_t[i][j] = data[j][i];
           }
       data = data_t;
       
       if(isTransposted){
           isTransposted = false;
       } else {
           isTransposted = true;
       }
   }
   
      
   private class RGB{
       private byte R;
       private byte G;
       private byte B;
       
   }
   private class Frontier{
       private LinkedList<Integer> path = new LinkedList<Integer>();
       private int h;
       private double cost;
   }
   
}
