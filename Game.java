//Paul Torre
//Section: 03
//Main application object
import java.awt.Rectangle;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Game {

  public enum GameState {Title, Play, Quit, Win, HighScores}; //enums are like integers. Except I
                                             //rename 0 to be Title, 1 to be
                                             //Play, and 2 to be Quit. You can
                                             //have as many state as you want.

  GameState gameState; //Store the state of the game

  //////////////////////////////////////////////////////////////////////////////
  //SPECIFIC TO THIS APPLICATION. YOU MAY OR MAY NOT NEED THESE
  char key;
  double mouseX;
  double mouseY;

  int time = 60;

  private String user = "";

  private Ship ship;

  private String[] scoreArr = new String[15];

  private Bullet[] bulletArr = new Bullet[100];

  private Font font1 = new Font("Courier New", Font.BOLD, 40);
  private Font font2 = new Font("Courier New", Font.BOLD, 18);

  private String sprite = "sprite.gif";
  private String bee = "bee.jpg";

  private int win = 40;
  private int score = 0;

  private Sprite[] arr = new Sprite[40];
  //////////////////////////////////////////////////////////////////////////////

  //Constructor. Initialize instance variables.
  //Remember you can add whatever parameters you want
  public Game() {
    gameState = GameState.Title;
    mouseX = mouseY = Double.POSITIVE_INFINITY;

    ship = new Ship();

    int x = 5;
    int y = 55;

    for(int i=0; i<bulletArr.length; i++)
      bulletArr[i] = null;

    for(int i=0; i<arr.length; i++){    //initialize sprite array with 40 sprites with grid positioning.
      String pic;
      if(i%2==1) pic = "bee.jpg";
      else pic = "sprite.gif";

      arr[i] = new Sprite(x, y, pic);
      x+=10;

      if(i%10==0&&i!=0){
        x=5;
        y+=10;
      }
    }
  }

  //Run. This is like the real main.
  //Basic game loop (update, clear, draw, show)
  public void run() throws FileNotFoundException {
    initialize();
    getScores();

    while(gameState != GameState.Quit) {
      //update the game
      processInput();

      //draw the game
      clear();
      draw();
      show();
    }
    outputScores();
  }

  //Initialization of the GUI for the game
  //Setup GUI canvas, scale, and double buffering
  public void initialize() {
    StdDraw.setCanvasSize(700, 700);
    StdDraw.setXscale(0, 100);
    StdDraw.setYscale(0, 100);
    StdDraw.enableDoubleBuffering();
  }

  //Process mouse and key presses
  //Note how to use mouse here.
  //Note how to base the mouse/key pressing on a gameState and to allow the
  //gameState to change!
  public void processInput() {
    if(StdDraw.mousePressed()) {
      mouseX = StdDraw.mouseX();
      mouseY = StdDraw.mouseY();
      switch(gameState) {
        case Title:
          break;
        case Play:
          mouseX = StdDraw.mouseX();
          mouseY = StdDraw.mouseY();
          break;
        case Win:
          if(mouseX >= 40 && mouseX <= 60 && mouseY >= 20 && mouseY <= 40)
            gameState = GameState.Quit;
          break; 
        case HighScores:
          if(mouseX >= 20 && mouseX <= 40 && mouseY >= 70 && mouseY <= 90)
            gameState = GameState.Title;
          if(mouseX >= 60 && mouseX <= 80 && mouseY >= 70 && mouseY <= 90)
            gameState = GameState.Play;
          break;       
        default:
          break;
      }
    }
    if(StdDraw.hasNextKeyTyped()) {
      if(gameState == GameState.Play) {
        key = StdDraw.nextKeyTyped();
        if(key == 'q' || key == 'Q')
          gameState = GameState.Quit;
        if(key == 'v')
          gameState = GameState.HighScores;
      }
      if(gameState == GameState.Title)
        gameState = GameState.Play;
      if(gameState == GameState.Win)
        user += String.valueOf(StdDraw.nextKeyTyped());
    }
  }

  //Prints new file of all scores
  public void outputScores() throws FileNotFoundException {
    addNewScore(score);
    PrintWriter out = new PrintWriter("scores.txt");
    int i = 0;
    while(scoreArr[i]!=null){
      out.println(scoreArr[i]);
      i++;
    }
    out.flush();
    out.close();
  }

  //Adds new score to array
  public void addNewScore(int score) {
    for(int i=0; i<scoreArr.length; i++) {
      if(scoreArr[i]==null) {
        scoreArr[i] = user + "      " + String.valueOf(score);
        break;
      }
    }
  }
    
  //Clear resets the graphics. Also add any background image. Note that with a
  //switch on the game state you can vary the background.
  public void clear() {
    StdDraw.clear();
  }

  //Draw. Based on the gameState call an appropriate draw function.
  public void draw() throws FileNotFoundException {
    switch(gameState) {
      case Title:
        drawTitle();
        break;
      case Play:
        drawPlay();
        break;
      case Win:
        drawWin();
        break;
      case HighScores:
        drawHighScores();
        break;  
      default:
        break;
    }
  }

  //Show and pause for 16 milliseconds. Aiming for 60 FPS.
  public void show() {
    StdDraw.show();
    StdDraw.pause(16);
  }

  //Title screen draw
  public void drawTitle() {
    ////////////////////////////////////////////////////////////////////////////
    //Specific to this application
    double x = (int)(Math.random()*101);
    double y = (int)(Math.random()*101);
    int dirx = 1;
    int diry = 1;
    while(!StdDraw.hasNextKeyTyped()){
      //Spaceship flying in background
      if(y+1>100){ 
        y -= 1;
        diry *= -1;
      }
      else y += diry * 1;
      if(y-1<0){ 
        y += 1;
        diry *= -1;
      }
      else y += diry * 1;
      if(x+1>100) {
        x-= 1;
        dirx *= -1;
      }
      else x += dirx * 1;
      if(x-1<0) {
        x+= 1;
        dirx *= -1;
      }
      else x += dirx * 1;

      StdDraw.clear(StdDraw.BLACK);
      StdDraw.setPenColor(StdDraw.WHITE);
      
      StdDraw.picture(x, y, ship.getPic(), 10, 10);
      StdDraw.picture(20, 45, bee, 20, 20);
      StdDraw.picture(40, 45, sprite, 20, 20);
      StdDraw.picture(60, 45, bee, 20, 20);
      StdDraw.picture(80, 45, sprite, 20, 20);
      StdDraw.setFont(font1);
      StdDraw.text(50, 60, "Aliens Shoot 'Em Up");
      StdDraw.setFont(font2);
      StdDraw.text(50, 30, "PRESS ANY KEY TO START");
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.filledSquare(50, 80, 10);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.text(50, 80, "High Scores");
      StdDraw.text(50, 75, "Press 'V'");

      StdDraw.show();
      StdDraw.pause(100);
    ////////////////////////////////////////////////////////////////////////////
    }
  }

  //Display points added when enemy is hit
  public void animateHit(int x, int y) {
    StdDraw.text(x, y, String.valueOf(time*100));
  }

  //Shoots a bullet
  public void shoot() {
    Bullet bullet = new Bullet(ship.getX(), ship.getY()+5);
    //find empty array spot
    for(int i=0; i<bulletArr.length; i++){
      if(bulletArr[i]== null){
        bulletArr[i] = bullet;
        break;
      }
    }
  }

  //Put scores in array
  public void getScores() throws FileNotFoundException {
    Scanner in = new Scanner(new File("scores.txt"));
    for(int i=0; i<scoreArr.length; i++){
      if(in.hasNextLine())
        scoreArr[i] = in.nextLine();            
    }
  }

  //Scores screen, uses file input
  public void drawHighScores() throws FileNotFoundException {
    getScores();
    StdDraw.clear(StdDraw.BLACK);
    StdDraw.text(50, 65, "Name  Score");
    for(int i=0; i<scoreArr.length; i++)
      if(scoreArr[i]!=null)
        StdDraw.text(50, 60-i*2, scoreArr[i]);

    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.filledSquare(30, 80, 10);
    StdDraw.setPenColor(StdDraw.GREEN);
    StdDraw.text(30, 80, "Back to Menu");

    StdDraw.setPenColor(StdDraw.RED); 
    StdDraw.filledSquare(70, 80, 10);
    StdDraw.setPenColor(StdDraw.GREEN);
    StdDraw.text(70, 80, "Play Game"); 
  }

  //Win Screen
  public void drawWin() {
    StdDraw.clear(StdDraw.BLACK);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setFont(font1);
    StdDraw.text(50, 60, "You Win !!!");
    StdDraw.setPenColor(StdDraw.GREEN);
    StdDraw.text(50, 80, "Score: " + String.valueOf(score));
    StdDraw.filledSquare(50, 30, 10);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setFont(font2);
    StdDraw.text(50, 30, "Quit");

    StdDraw.text(20, 20, "Enter Your Name: " + user);
  }

  //Game play draw
  public void drawPlay() {
    ////////////////////////////////////////////////////////////////////////////
    StdDraw.clear(StdDraw.BLACK);
    StdDraw.setPenColor(StdDraw.WHITE);

    //process movement
    switch (key) {
        case 'j':
          if(ship.getX()>5){
            ship.moveX(-5);
            key = '0';
            break;
          }
          break;
        case 'l':
          if(ship.getX()<95){
            ship.moveX(5);
            key = '0';
            break;
          }
          break;  
        case ' ':
          shoot();
          key = '0';
          break;  
      }

    //draw ship location
    StdDraw.picture(ship.getX(), ship.getY(), ship.getPic(), ship.getWidth(), ship.getHeight());

    //draw array of enemies
    for(int i=0; i<arr.length; i++) {
      if(arr[i]!=null)
        StdDraw.picture(arr[i].getX(), arr[i].getY(), arr[i].getPic(), arr[i].getWidth(), arr[i].getHeight());
    }

    //for each bullet, move it
    for(int i=0; i<bulletArr.length; i++) {
      if(bulletArr[i]!=null){
        bulletArr[i].move();
        if(bulletArr[i].getY()==1)
          bulletArr[i] = null;
        StdDraw.filledCircle(bulletArr[i].getX(), bulletArr[i].getY(), 0.5);
      }
    }

    //see if bullet hit enemy
    for(int i=0; i<arr.length; i++) {
      for(int j=0; j<bulletArr.length; j++) {
        if(arr[i]!=null&&bulletArr[j]!=null){
          if(arr[i].bounds().intersects(bulletArr[j].bounds())){
            animateHit(arr[i].getX(), arr[i].getY());
            bulletArr[j] = null;
            arr[i] = null;
            win--;
            score+=100*time;
          }
        }
      }
    }

    //when all enemies are gone
    if(win==1)
      gameState = gameState.Win;
  
    //bonus timer counts down until it is 1
    if (time>1)
      time -= 1;

    StdDraw.text(40, 90, "Score: " + String.valueOf(score));
  
    StdDraw.textRight(100, 5, String.valueOf(time));

    ////////////////////////////////////////////////////////////////////////////
  }
}
