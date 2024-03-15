package games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Action;
import javax.swing.Timer;

public class FlyingBoxes extends Window implements ActionListener {
  // static variables
  public static Random RND = new Random();
  public static Box.List boxes = new Box.List();
  public static Box bkg = new Box(Color.white, 0,0,5000,5000, 0, 0);
  static{bkg.v.x = 0; bkg.v.y = 0; bkg.acc.x = 0; bkg.acc.y =0;}    // static block, no name, static means it is always there, called by the class loader, keep the way it's set up
  public static Box dude = new Box(100,100,100,100);
  public static int xMax = 900, yMax = 700;
  public static Timer timer;



  // non-static/member variables

  // Constructors
  public FlyingBoxes() {
    super("Flying Boxes", 1000, 700);
    for(int i = 0; i < 100; i++){
      new Box(rnd(800), rnd(500), 100, 100);
    }
    timer = new Timer(30, this);    //millisecond
    timer.setInitialDelay(2000);    // 2s
    timer.start();
  }

  // member functions(non-static functions)
  public void paintComponent(Graphics g){
//    g.setColor(Color.white);
//    g.fillRect(0,0,5000,5000);
//    g.setColor(Color.red);
//    g.fillRect(100,100,100,100);    // hello world in graphics world
    bkg.show(g);
//    dude.show(g);
    boxes.show(g);
  }

  // static functions
  public static void main(String[] args){
    PANEL = new FlyingBoxes();
    Window.launch();
  }
  public static int rnd(int max){return RND.nextInt(max);}
  public static int rndV(){return rnd(20) - 10;}    // random velocity from -10 to 10

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }

  // nested class
  public static class Box{
    public Point loc, size;
    public Color color;
    public Point v;// = new Point(2,5);    //velocity
    public Point acc = new Point(0, 1);    // acceleration    // gravity
    public Box(Color c, int x, int y, int w, int h, int vx, int vy){
      color = c;
      loc = new Point(x, y);
      size = new Point(w, h);
      v = new Point(vx, vy);
    }
    public Box(int x, int y, int w, int h){
      color = new Color(rnd(225), rnd(225), rnd(225));
      loc = new Point(x, y);
      size = new Point(w, h);
      v = new Point(rndV(),rndV());
      boxes.add(this);
    }

    public void show(Graphics g){
      g.setColor(color);
      g.fillRect(loc.x, loc.y, size.x, size.y);
      move();    // side effect
    }
    public void move(){
      loc.x += v.x; loc.y += v.y;
      v.x += acc.x; v.y += acc.y;
      // bounce
      if(loc.y > yMax && v.y > 0){v.y = -v.y; v.y += 2;}
      if(loc.x > xMax && v.x > 0){v.x = -v.x;}
      if(loc.x < 0 && v.x < 0){v.x = -v.x;}
      if(shouldStop()){
        loc.y = yMax;
        v.y = 0;
        acc.y = 0;
      }
    }

    private boolean shouldStop() {return (Math.abs(loc.y - yMax) < 5 && Math.abs(v.y) < 3);}

    //-----------------------------------List-------------------------------------------//
    public static class List extends ArrayList<Box>{    // List of Boxes
      public void show(Graphics g){for(Box b: this){b.show(g);}}    // Show all boxes in the list

    }
  }
}
