package games;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Skunk extends Window{
    public static G.Button.List cmds = new G.Button.List();  // commands
    public static String AiName = "Archie";
    public static G.Button PASS = new G.Button(cmds, "Pass") {   // abstract
        public void act() {pass();}
    };
    public static G.Button ROLL = new G.Button(cmds, "Roll") {   // abstract
        public void act() {roll();}
    };

    public static G.Button AGAIN = new G.Button(cmds, "Again") {   // abstract
        public void act() {again();}
    };

    static {
        PASS.set(100, 100);
        ROLL.set(150, 100);
        // PASS.enabled = false;
    }

    public static int M = 0, E = 0, H = 0; //scores of mine, enemy's, hand

    public static boolean myTurn = true;
    public static int D1, D2; // dice values
    public static int xM = 50, yM = 50;   // margins
    public Skunk() {
        super("Skunk", 1000, 700);  //open a window called "skunk"
        again();
    }

    public static void pass() {
    }

    public static void roll() {rollDice(); analyzeDice();}

    public static void rollDice() {
        D1 = 1 + G.rnd(6);
        D2 = 1 + G.rnd(6);
    }

    public static void analyzeDice() {

    }

    public static void showRoll(Graphics g) {
        g.setColor(Color.BLACK);
        String str = playerName + " roll: " + D1 + ", " + D2 + skunkMessage;
//        g.drawString("Dice roll: " + D1 + ", " + D2, xM, yM);
        g.drawString(str, xM, yM);
    }

    public static String playerName() {
        return myTurn ? "Your" : AiName + "'s";
    }

    public static void showScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(scoreString(),xM, yM + 40);
    }

    public static String scoreString() {
        return "Hand: " + H + "  Your score: " + M + "  " + AiName + "'s score: " + E; // Cia, Ibm
    }

    public static void again() {
        M = 0; E = 0; H = 0;
        myTurn = (G.rnd(2) == 0);
        PASS.set(100, 100);
        ROLL.set(150, 100);
        AGAIN.set(-100, -100);
        roll();
    }

    public void paintComponent(Graphics g) {
        G.fillBack(g);
        cmds.show(g);
        showRoll(g);
        showScore(g);
    }

    public void mousePressed(MouseEvent me) {
        int x = me.getX(), y = me.getY();
        if (cmds.clicked(x, y)) {
            repaint();
        }
    }

    public static void main(String[] args) {
        PANEL = new Skunk();
        Window.launch();
    }


}
