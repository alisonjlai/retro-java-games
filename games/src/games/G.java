package games;

import static games.Destructo.RANDOM;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Graphics Helper Class
 */

public class G {
  public static V LEFT = new V(-1, 0);
  public static V RIGHT = new V(1, 0);
  public static V UP = new V(0, -1);
  public static V DOWN = new V(0, 1);

//  public static Random RANDOM = new Random();
  public static int rnd(int k){return RANDOM.nextInt(k);}
//  public static Color WHITE = Color.WHITE;
//  public static VS backgroundVS = new VS(0, 0, 5000,5000);

  //---------------------------------V---------------------------------//
  // Vector
//  public static class V{
//    public int x, y;
//
//    public V(int x, int y) {set(x, y);}
//    public V(V v){set(v.x, v.y);}
//    public void set(int x, int y){this.x = x; this.y = y;}
//    public void add(V v) {x += v.x; y += v.y;}
//    public void set(V v){set(v.x, v.y);}
//  }

  //---------------------------------VS---------------------------------//
  // Vector, size
//  public static class VS{
//    public V loc, size;
//    public VS(int x, int y, int w, int h){
//      loc = new V(x, y);
//      size = new V(w, h);
//    }
//    public Boolean contains(int x, int y){
//      return x > loc.x && y > loc.y && x < (loc.x + size.x) && y < (loc.y + size.y);
//    }
//    public void fill(Graphics g, Color c){
//      g.setColor(c);
//      g.fillRect(loc.x, loc.y, size.x, size.y);
//    }
//    public void draw(Graphics g, Color c){
//      g.setColor(c);
//      g.drawRect(loc.x, loc.y, size.x, size.y);
//    }
//    public static void fillBackground(Graphics g){backgroundVS.fill(g, WHITE);}
//  }
  public static void fillBack(Graphics g){
    g.setColor(Color.white);
    g.fillRect(0,0,5000,5000);
  }
  public static class V{
    public int x, y;
    public V(){}
    public V(int x, int y){set(x, y);}
    public void set(int x, int y){
      this.x = x;
      this.y = y;
    }
    public void set(V v){
      x = v.x;
      y = v.y;
    }
    public void add(V v){
      x += v.x;
      y += v.y;
    }
  }

}
