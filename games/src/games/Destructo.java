package games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.Timer;

public class Destructo extends Window implements ActionListener {
  public static Timer timer;    // Swing timer
  public static final int nC = 13, nR = 15;    // Column & Row
  public int [][] grid = new int[nC][nR];
  public static Color[] color = {Color.LIGHT_GRAY, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED, Color.PINK};
  public static Random RANDOM = new Random();    // Single constant random number generator

  public static int rnd(int k){return RANDOM.nextInt(k);}    // Wrapper function

  public Destructo() {
    super("Destructo Collapse", 1000, 700);
    rndColors(4);
    timer = new Timer(30, this);    // 30 frames/second
    timer.start();    // let the clock start ticking
    initRemaining();
  }

  public void createNewGame(){
    rndColors(3 + rnd(3));
    initRemaining();
  }


  // Output routine
  public void paintComponent(Graphics g){
    g.setColor(color[0]);
    g.fillRect(0, 0, 5000, 5000);
    showGrid(g);
    bubbleSort();
    if(slideCol()){xM += w / 2;}
    showRemaining(g);
  }
  // Input routine
  public void mousePressed(MouseEvent me){
    int x = me.getX(), y = me.getY();
    if(x < xM || y < yM){return;}
    int r = r(y), c = c(x);
    if(r < nR && c < nC){rcAction(r, c);}
    repaint();
  }

  public void keyPressed(KeyEvent ke){
    createNewGame();
    repaint();
  }

  public void rcAction(int r, int c){
//    grid[c][r] = 0;    // STUB - SOLVED!
    if(infectable(c, r)){infect(c, r, grid[c][r]);}
//    bubbleSort();
  }
  public boolean colIsEmpty(int c){
    for(int r = 0; r < nR; r++){
      if(grid[c][r] != 0){return false;}
    }
    return true;
  }
  public void swapCol(int c){    // c: non-empty, c-1 : empty
    for(int r = 0; r < nR; r++){
      grid[c-1][r] = grid[c][r];
      grid[c][r] = 0;
    }
  }
  public boolean slideCol(){
    boolean res = false;
    for(int c = 1; c < nC; c++){
      if(colIsEmpty(c-1) && !colIsEmpty(c)){
        swapCol(c);
        res = true;
      }
    }
    return res;
  }
  /**
   *
   * @param k
   *  sample output:
   *  2  4  1  3  4  4  4  2  3  1  4  3  1  4  2
   *  1  1  2  2  1  3  2  2  3  4  4  1  1  1  1
   *  1  1  4  4  1  1  3  3  3  2  3  2  1  2  2
   *  3  1  4  1  4  1  2  4  2  1  4  2  4  3  1
   *  3  2  2  4  1  4  2  4  4  2  2  3  4  4  1
   *  4  3  2  1  1  3  4  4  2  1  3  2  1  2  1
   *  3  1  3  2  3  2  3  1  2  4  4  1  1  1  1
   *  1  1  3  2  2  1  3  1  3  3  2  2  1  2  4
   *  3  4  3  3  3  1  3  4  1  4  4  2  3  2  4
   *  2  4  2  3  1  3  4  2  1  1  4  4  4  2  4
   *  4  2  1  4  2  4  2  1  1  4  4  2  2  2  4
   *  2  3  1  4  2  2  1  4  3  1  2  4  4  3  4
   *  2  3  2  4  1  2  1  1  1  1  1  2  3  3  4
   */
  public void rndColors(int k) {    // k: how many colors we want in our girds
    for (int c = 0; c < nC; c++) {
      for (int r = 0; r < nR; r++) {
        grid[c][r] = 1 + rnd(k);    // generate random numbers for each grid
        System.out.print(" " + grid[c][r] + " ");    // print VS. println
      }
      System.out.println();
    }
  }
  public void infect(int c, int r, int v){    // v : value
    if(grid[c][r] != v){return;}
    grid[c][r] = 0;
    bricksRemaining--;
    if(r > 0){infect(c, r-1, v);}
    if(c > 0){infect(c-1, r, v);}
    if(r < nR - 1){infect(c, r+1, v);}
    if(c < nC - 1){infect(c + 1, r, v);}
  }
  public boolean infectable(int c, int r){
    int v = grid[c][r];    // Fetch the value
    if (v == 0){return false;}
    if (r > 0){if (grid[c][r-1] == v) {return true;}}    // left neighbor
    if (c > 0){if (grid[c-1][r] == v) {return true;}}    // upper neighbor
    if (r < nR - 1){if (grid[c][r+1] == v) {return true;}}    // right neighbor
    if (c < nC - 1){if (grid[c+1][r] == v) {return true;}}    // lower neighbor
    return false;
  }
  public boolean bubble(int c){
    boolean res = false;
    for(int r = nR - 1; r > 0; r--){
      if(grid[c][r] == 0 && grid[c][r-1] != 0){
        grid[c][r] = grid[c][r-1];
        grid[c][r-1] = 0;
        res = true;
      }
    }
    return res;
  }
  public void bubbleSort(){
    for(int c = 0; c < nC; c++){
//      while(bubble(c)){}
      bubble(c);
    }
  }
  public int bricksRemaining;
  public void initRemaining(){
    bricksRemaining = nR * nC;
  }
  public void showRemaining(Graphics g){
    String str = "BricksRemaining: " + bricksRemaining;
    if(noMorePlays()){
      str += " GAME OVER!";
    }
    g.setColor(Color.black);
    g.drawString(str, 50, 25);
  }
  public Boolean noMorePlays(){
    for(int r = 0; r < nR; r++){
      for(int c = 0; c < nC; c++){
        if(infectable(c, r)){return false;}
      }
    }
    return true;
  }
  public int w = 50, h = 30;
  public int xM = 100, yM = 100;
  public int x(int c){return xM + c * w;}
  public int y(int r){return yM + r * h;}
  public int c(int x){return (x - xM) / w;}
  public int r(int y){return (y - yM) / h;}
    public void showGrid(Graphics g){
      for(int c = 0; c < nC; c++){
        for(int r = 0; r < nR; r++){
          g.setColor(color[grid[c][r]]);
          g.fillRect(x(c), y(r), w, h);
        }
    }
  }

  public static void main(String[] args){
    Window.PANEL = new Destructo();
    Window.launch();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
}
