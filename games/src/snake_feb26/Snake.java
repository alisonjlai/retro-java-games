package snake_feb26;

import games.Window;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;

public class Snake extends Window implements ActionListener {
  public static Timer timer;
  public static Color cFood = Color.GREEN, cSnake = Color.BLUE, cBad = Color.RED;
  public static Cell food = new Cell();
  public static Cell crash = null;
  public static Cell.List snake = new Cell.List();
  public Snake() {
    super("Snake", 1000, 700);
    startGame();
    timer = new Timer(200, this);
    timer.start();
  }

  public void startGame() {
    snake.stop();
    snake.iHead = 0;
    snake.clear();
    snake.growList();
    food.randomLoc();
    crash = null;
  }

  public static void moveSnake(){
    if (crash != null){return;}
    snake.move();
    Cell head = snake.head();
    if (head.hits(food)){snake.growList(); food.randomLoc(); return;}
    if (!head.inbounds()){crash = head; snake.stop(); return;}
    if (snake.hits(head)){crash = head; snake.stop(); return;}
  }

  public void paintComponent(Graphics g) {
    G.fillBack(g);

    g.setColor(cSnake); snake.show(g);
    g.setColor(cFood); food.show(g);
    Cell.drawBoundary(g);
    if (crash != null){g.setColor(cBad); crash.show(g);}
  }

  public void keyPressed(KeyEvent ke){
    int vk = ke.getKeyCode();
    if(vk == KeyEvent.VK_LEFT){snake.direction = G.LEFT;}
    if(vk == KeyEvent.VK_RIGHT){snake.direction = G.RIGHT;}
    if(vk == KeyEvent.VK_UP){snake.direction = G.UP;}
    if(vk == KeyEvent.VK_DOWN){snake.direction = G.DOWN;}
    if(vk == KeyEvent.VK_SPACE){startGame();}
//    if(vk == KeyEvent.VK_A){snake.growList();}
    repaint();
  }
  public static void main(String[] args){
    PANEL = new Snake();
    Window.launch();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    moveSnake();
    repaint();
  }

  public static class Cell extends G.V implements I.Show{
    public static final int xM = 35, yM = 35, nX = 30, nY = 20, W = 30;
    public Cell(){randomLoc();}
    public Cell(Cell c){set(c);}
    public void randomLoc(){x = G.rnd(nX); y = G.rnd(nY);}
    public boolean hits(Cell c) {return c.x == x && c.y == y;}
    public boolean inbounds(){return x >= 0 && x < nX && y >= 0 && y < nY;}
    public static void drawBoundary(Graphics g){
      int xMax = xM + nX*W, yMax = yM + nY*W;
      g.setColor(Color.BLACK);
      g.drawLine(xM, yM, xM, yMax);
      g.drawLine(xM, yM, xMax, yM);
      g.drawLine(xMax, yM, xMax, yMax);
      g.drawLine(xM, yMax, xMax, yMax);
    }
    public void show(Graphics g){g.fillRect(xM + x*W, yM + y*W, W, W);}


    public static class List extends ArrayList<Cell>{
      public static G.V STOPPED = new G.V(0, 0);
      public G.V direction = STOPPED;
      public int iHead = 0;
      public void show(Graphics g){for(Cell c : this){c.show(g);}}
      public void move(){
        if(direction == STOPPED){return;}
        int iTail = (iHead + 1) % size();
        Cell tail = get(iTail);
        tail.set(get(iHead));
        tail.add(direction);
        iHead = iTail;
      }
      public Cell head(){return get(iHead);}
      public void stop(){direction = STOPPED;}
      public boolean hits(Cell t){    // t for target
        for(Cell c : this){
          if (c != t && c.hits(t)){return true;}
        }
        return false;
      }
      public void growList(){
        Cell cell = (size() == 0) ? new Cell() : new Cell(get(0));
        add(cell);
      }

    }

  }


}
