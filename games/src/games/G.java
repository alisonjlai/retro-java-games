package games;

import javax.swing.*;

import static games.Destructo.RANDOM;
import static games.Destructo.color;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
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
  public static class VS{
    public V loc, size;
    public VS(int x, int y, int w, int h){
      loc = new V(x, y);
      size = new V(w, h);
    }
    public Boolean contains(int x, int y){
      return x > loc.x && y > loc.y && x < (loc.x + size.x) && y < (loc.y + size.y);
    }
    public void fill(Graphics g, Color c){
      g.setColor(c);
      g.fillRect(loc.x, loc.y, size.x, size.y);
    }
    public void draw(Graphics g, Color c){
      g.setColor(c);
      g.drawRect(loc.x, loc.y, size.x, size.y);
    }
//    public static void fillBackground(Graphics g){backgroundVS.fill(g, WHITE);}
  }

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

  //---------------------------------Button---------------------------------//
  public static abstract class Button {
    public abstract void act();
    public boolean enabled = true, bordered = true;
    public String text = "";
    public VS vs = new VS(0, 0, 0, 0);
    public int dyText = 0;
    public LookAndFeel lnf = new LookAndFeel();

    public Button(Button.List list, String str) {
      if (list != null) {list.add(this);}
      text = str;
    }
    public void show(Graphics g) {
      if (vs.size.x == 0) {setSize(g);}
      vs.fill(g, lnf.back);
      if (bordered) {vs.draw(g, lnf.border);}
      g.setColor(enabled? lnf.text : lnf.disabled);
      g.drawString(text, vs.loc.x + lnf.M.x, vs.loc.y + dyText);
    }

    public void setSize(Graphics g) {
      FontMetrics fm = g.getFontMetrics();
      vs.size.set(2 * lnf.M.x + fm.stringWidth(text), 2 * lnf.M.y + fm.getHeight());
      dyText = fm.getAscent() + lnf.M.y;
    }
    public boolean hit(int x, int y) {return vs.contains(x, y);}

    public void click() {if (enabled) {act();}}
    public void set(int x, int y) {vs.loc.set(x, y);}

    //--------------------------LookAndFeel-----------------------//
    public static class LookAndFeel {
      public static Color text = Color.black, back = Color.white,
                          border = Color.black, disabled = Color.GRAY;
      public static V M = new V(5, 3);
    }

    //--------------------------Button.list-----------------------//
    public static class List extends ArrayList<Button> {
      public Button hit(int x, int y) {
        for (Button b:this) {
          if (b.hit(x,y)) {return b;}
        }
        return null;
      }
      public boolean clicked(int x, int y) {
        Button b = hit(x, y);
        if (b == null) {return false;}
        b.click();
        return true;
      }
      public void show(Graphics g) {for (Button b : this) {b.show(g);}}
    }
  }
}
