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
       data = new RGB[height][width];
       
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               data[i][j] = new RGB();
               data[i][j].R = (byte) picture.get(j, i).getRed();
               data[i][j].G = (byte) picture.get(j, i).getGreen();
               data[i][j].B = (byte) picture.get(j, i).getBlue();
           }
   }
   public Picture picture()                          // current picture
   {
	   if(isTransposted)
		   transpose();
	   
       Picture p = new Picture(width, height);
       
           for(int i =0; i<height;i++)
               for(int j =0; j<width;j++){
               Color color = new Color(data[i][j].R, data[i][j].G, data[i][j].B);
               p.set(j, i, color);
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
       
       temp = (data[y][x-1].R - data[y][x+1].R);
       delta_x += temp*temp;
       temp = (data[y][x-1].G - data[y][x+1].G);
       delta_x += temp*temp;
       temp = (data[y][x-1].B - data[y][x+1].B);
       delta_x += temp*temp;
       
       temp = (data[y-1][x].R - data[y+1][x].R);
       delta_x += temp*temp;
       temp = (data[y-1][x].G - data[y+1][x].G);
       delta_x += temp*temp;
       temp = (data[y-1][x].B - data[y+1][x].B);
       delta_x += temp*temp;
       
       return (delta_x + delta_y);
   }
   public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
   {
	   if(!isTransposted)
    	   transpose();
	   
	   int res[] = findVerticalSeam();
	   
	   return res;
   }
   public   int[] findVerticalSeam()                 // sequence of indices for vertical seam
   {
	   if(isTransposted)
    	   transpose();
	   
       PriorityQueue<Frontier> pq = new PriorityQueue<Frontier>(new Comparator<Frontier>() {
           public int compare(Frontier f1, Frontier f2) {
               return Double.compare(f1.cost,f2.cost);
           }
       });
       
       double cost[][] = new double[height][width];
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               cost[i][j] = Integer.MAX_VALUE;
       }
       
       
       for(int i =0; i<width;i++){
           Frontier f = new Frontier();
           f.cost = energy(i,0);
           cost[0][i] = f.cost;
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
           if(cost[f1.h][idx] > f1.cost ){
               cost[f1.h][idx] = f1.cost;
               pq.add(f1);
           }
           
           if(idx > 1){
               f1 = new Frontier();
               f1.cost = energy(idx - 1,f.h + 1);
               f1.path = (LinkedList<Integer>) f.path.clone();
               f1.path.add(idx - 1);
               f1.h = f.h + 1;
               if(cost[f1.h][idx] > f1.cost ){
                   cost[f1.h][idx] = f1.cost;
                   pq.add(f1);
               }
               
           }
           
           if(idx < width-1){
               f1 = new Frontier();
               f1.cost = energy(idx + 1,f.h + 1);
               f1.path = (LinkedList<Integer>) f.path.clone();
               f1.path.add(idx + 1);
               f1.h = f.h + 1;
               if(cost[f1.h][idx] > f1.cost ){
                   cost[f1.h][idx] = f1.cost;
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
       
       if(!isTransposted)
    	   transpose();
       
       if(seam.length !=  width)
           throw new IllegalArgumentException (String.format("seam.length (%d) !=  width (%d)",seam.length,width));
       
       
     removeVerticalSeam(seam);
     
       
   }
   public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
   {
       if(seam == null)
           throw new NullPointerException("Null reference to seam");
       
       if(isTransposted)
    	   transpose();
       
       if(seam.length !=  height)
           throw new IllegalArgumentException ("seam.length !=  height");
      
       
       
       for(int i =0; i<height;i++){
           RGB t[] = new RGB[width-1];
           	if(seam[i] > 0)
            System.arraycopy(data[i], 0, t, 0, seam[i]-1);
           	if(width-seam[i] > 0 )
            System.arraycopy(data[i], seam[i]+1, t, seam[i], width-1-seam[i]);
            data[i] = t;
        }
        width=width-1;
   }
   
   private void transpose(){
       
       RGB data_t[][] = new RGB[width][height];
       
       for(int i =0; i<height;i++)
           for(int j =0; j<width;j++){
               data_t[j][i] = data[i][j];
           }
       data = data_t;
       int t = height;
       height = width;
       width = t ;
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
