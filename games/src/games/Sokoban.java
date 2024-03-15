package games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Sokoban extends Window{
  public Board board = new Board();
  public Sokoban(){
    super("Sokoban", 1000, 1000);
    board.loadStringArray(lev3);
  }
  public void paintComponent(Graphics g){
    G.fillBack(g);
    board.show(g);
  }
  public void keyPressed(KeyEvent ke){
    int vk = ke.getKeyCode();
    if(vk == KeyEvent.VK_LEFT){board.go(G.LEFT);}
    if(vk == KeyEvent.VK_RIGHT){board.go(G.RIGHT);}
    if(vk == KeyEvent.VK_UP){board.go(G.UP);}
    if(vk == KeyEvent.VK_DOWN){board.go(G.DOWN);}
    repaint();
  }
  public void keyTyped(KeyEvent ke){
    char c = ke.getKeyChar();
    if(c == ' '){board.reload();}
    if(c == 0x0D || c == 0x0A){board.nextPuzzle();}
    repaint();
  }
  public static void main(String[] args){
    PANEL = new Sokoban();
    Window.launch();
  }

  //------------------------Board--------------------------//
  public static class Board{
    public static String[][] puzzles = {lev1, lev2, lev3};
    public int currPuzzle = 0;
    public static final int N = 25;
    public static String boardStates = " WPCGgE";
    // W = Wall, P = Person, C = Container, G = Goal, g = , E = Error
    public static Color[] colors = {Color.WHITE, Color.DARK_GRAY, Color.GREEN, Color.ORANGE,
                                    Color.CYAN, Color.BLUE, Color.RED};
    public static final int xM = 50, yM = 50, W = 40;
    public static G.V dest = new G.V();

    public char[][] b = new char[N][N];
    public G.V person = new G.V();
    public Board(){clear();}
    public void nextPuzzle(){
      currPuzzle = (currPuzzle + 1) % puzzles.length;
      reload();
    }
    public void reload(){
      clear();
      loadStringArray(puzzles[currPuzzle]);
    }
    public char ch(G.V v){return b[v.x][v.y];}
    public void set(G.V v, char c){b[v.x][v.y] = c;}
    public static boolean onGoal = false;    // Tracks if player is on goal square
    public void movePerson(){    // Doesn't test for valid destination, assuming it's tested elsewhere - go method
      Boolean res = ch(dest) == 'G';    // Set up the goal state
      set(person, onGoal ? 'G' : ' ');
      set(dest, 'P');
      person.set(dest.x, dest.y);
      onGoal = res;
    }
    public void go(G.V dir){
      dest.set(person);
      dest.add(dir);
      if(ch(dest) == 'W' || ch(dest) == 'E'){return;}
      if(ch(dest) == ' ' || ch(dest) == 'G'){movePerson();return;}
      if(ch(dest) == 'C' || ch(dest) == 'g'){
        // Attempting to move container
        dest.add(dir);    // box's destination
        if(ch(dest) != ' ' && ch(dest) != 'G'){return;}    // cannot move container
        // If we can move container
        set(dest, ch(dest) == 'G' ? 'g' : 'C');
        dest.set(person);
        dest.add(dir);
        set(dest, ch(dest) == 'g' ? 'G' : ' ');
        movePerson();
      }
    }
    public void show(Graphics g){
      for(int r = 0; r < N; r++){
        for(int c = 0; c < N; c++){
          int ndx = boardStates.indexOf(b[c][r]);
          g.setColor(colors[ndx]);
          g.fillRect(xM + c*W, yM + r*W, W, W);
        }
      }
    }
    public void clear(){
      for(int i = 0; i < N; i++){
        for(int j = 0; j < N; j++){
          b[i][j] = ' ';
        }
      }
    }
    public void loadStringArray(String[] a){
      person.set(0, 0);
      for(int r=0; r < a.length; r++){
        String s = a[r];
        for(int c=0; c < s.length(); c++){
          char ch = s.charAt(c);
          b[c][r] = (boardStates.indexOf(ch) > -1) ? ch : 'E';
          if(ch == 'P' && person.x == 0){
            person.x = c;
            person.y = r;
          }
        }
      }
    }
  }

  public static String[] lev1 = {
      "  WWWWW",
      "WWW   W",
      "WGPC  W",
      "WWW CGW",
      "WGWWC WW",
      "W W G WW",
      "WC gCCGW",
      "W   G  W",
      "WWWWWWWW"
  };
  public static String[] lev2 = {
      "    WWWWW",
      "    W   W",
      "    WC  W",
      "  WWW  CWW",
      "  W  C C W",
      "WWW W WW W   WWWWWW",
      "W   W WW WWWWW  GGW",
      "W C  C          GGW",
      "WWWWW WWW WPWW  GGW",
      "    W     WWWWWWWWW",
      "    WWWWWWWW       "
  };
  public static String[] lev3 = {
      "WWWWWWWWWWWW  ",
      "WGG  W     WWW",
      "WGG  W C  C  W",
      "WGG  WCWWWW  W",
      "WGG    P WW  W",
      "WGG  W W  C WW",
      "WWWWWW WWC C W",
      "  W C  C C C W",
      "  W    W     W",
      "  WWWWWWWWWWWW"
  };
}
