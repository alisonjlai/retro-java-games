//package games;
//
//import games.G.V;
//import games.G.VS;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//
//public class Cryptogram extends Window{
//  static Font font = new Font("Verdana", Font.BOLD, 20);
//  public static final int XM = 50, YM = 50;      // margins
//  public static final int LINE_GAP = 10, W = 20, H = 45, D_CODE = 18, D_GUESS = 40;
//  public static G.V SPACE = new G.V(W, 0),
//                    START = new G.V(XM, YM),
//                    NL = new G.V(0, LINE_GAP + H);    // New Line
//  public static Cell.List cells = new Cell.List();    // static: we only want one cellsList
//  public static final int EOL = 850;    // End Of Line
//
//  public Cryptogram() {
//    super("Cryptogram", 1000, 400);
//    loadQuote("NOW IS THE TIME FOR ALL GOOD MEN TO COME TO THE AID OF THE PARTY");
//    /**
//    Cell c = new Cell(Pair.alpha[0]);    // alpha[0] -> 'a'
//    new Cell(Pair.alpha[3]);    // alpha[3] -> 'd'
//    c.p.guess = 'Z';
//    Cell.newLine();
//    new Cell(Pair.alpha[0]);
//    Cell.selected = c;
//     */
//  }
//  public void loadQuote(String q){
//    Cell.init(); Pair.init();
//    for (int i = 0; i < q.length(); i++){
//      char c = q.charAt(i);
//      int iAlpha = c - 'A';
//      if (c >= 'A' && c <= 'Z'){new Cell(Pair.alpha[iAlpha]);}
//      else{
//        Cell.space();
//        if (Cell.nextLoc.x > EOL){Cell.newLine();}
//      }
//    }
//  }
//
//  public void paintComponent(Graphics g){
//    G.VS.fillBackground(g);
//    g.setFont(font);
//    cells.show(g);
//  }
//  public void mouseClicked(MouseEvent me){
//    int x = me.getX(), y = me.getY();
//    Cell.selected = cells.hit(x, y);
//    repaint();
//  }
//  public void keyTyped(KeyEvent ke){
//    char c =  ke.getKeyChar();
//    if(c >= 'a' && c < 'z'){
//      c = (char)(c - 'a' + 'A');    // Convert c to uppercase
//    }
//    if(Cell.selected != null){
//      Cell.selected.p.setGuess((c >= 'A' && c <= 'Z') ? c : ' ');
//    }
//    repaint();
//  }
//
//  public void keyPressed(KeyEvent ke){
//    int vk = ke.getKeyCode();    // Virtual Key
//    if (Cell.selected != null){
//      System.out.println(Cell.seleceted.ndx);    // Debug
//      if (vk == KeyEvent.VK_LEFT){Cell.select(Cell.selected.ndx - 1);}
//      if (vk == KeyEvent.VK_RIGHT){Cell.select(Cell.selected.ndx + 1);}
//    }
//  }
//  public static void main(String[] args){
//    PANEL = new Cryptogram();
//    Window.launch();
//  }
//  //---------------------------------Pair---------------------------------//
//  public static class Pair{
//    public static Pair[] alpha = new Pair[26];
//    // nameless function
//    static{
//      for(int i = 0; i < 26; i++){
//        // Letter Arithmetic
//        // Casting
//        alpha[i] = new Pair((char)('A' + i));    // name a type in a parenthesis -> Java is checking for you whether it's legal
//        // Java does strict type-checking
//      }
//    }
//    public static void init(){
//      for (int i = 0; i < 26; i++){
//        Pair p = alpha[i];
//        p.guess = ' ';
//        Pair x = alpha[G.rnd(26)];
//        char c = p.code;
//        p.code = x.code;
//        x.code = c;
//      }
//    }
//    public char actual, code, guess = ' ';
//    private Pair(char c) {actual = c; code = c;}
//    public void setGuess(char c){
//      for (int i = 0; i < 26; i++){
//        Pair p = alpha[i];
//        if (p.guess == c){p.guess = ' ';}
//      }
//      guess = c;
//    }
//  }
//  //---------------------------------Cell---------------------------------//
//  public static class Cell{
//    public static G.V SIZE = new G.V(15, 30);
//    public static G.V nextLoc = new G.V(START),
//                      nextLine = new G.V(START);
//    public static G.VS selectedVS = new VS(0, 0, W, H);    // how big is the selected cell is
//    public G.V loc = new G.V(0, 0);
//    public Pair p;
//    public int ndx;    // Index
//    public Cell selected = null;
//    public Cell(Pair pair) {
//      p = pair;
//      loc = new G.V(nextLoc);
//      space();
//      ndx = cells.size();
//      cells.add(this);
//    }
//    public static void init(){
//      nextLoc.set(START);
//      nextLine.set(START);
//      cells.clear();
//    }
//    public static void select(int n){
//      if (n >= 0 && n < cells.size()){
//        selected = cells.get(n);
//        System.out.println("Selecting: " + n);    // Debug
//      }
//    }
//    public boolean hit(int x, int y){
//      selectedVS.loc.set(loc);
//      return selectedVS.contains(x, y);
//    }
//    public void show(Graphics g){
//      if(this == Cell.selected){selectedVS.loc.set(loc); selectedVS.draw(g, Color.RED);}
//      g.setColor(Color.black);
//      g.drawString("" + p.code, loc.x, loc.y + D_CODE);    // d for delta
//      g.drawString("" + p.guess, loc.x, loc.y + D_GUESS);
//    }
//    public static void newLine(){nextLine.add(NL); nextLoc.set(nextLine);}
//    public static void space(){nextLoc.add(SPACE); }
//    //---------------------------------List---------------------------------//
//    public static class List extends ArrayList<Cell> {
//      public void show(Graphics g){
//        for(Cell c : this){c.show(g);}
//      }
//      public Cell hit(int x, int y){
//        for(Cell c : this){if(c.hit(x, y)){return c;}}
//        return null;
//      }
//    }
//  }
//}
//
