//Paul Torre
//Section: 03
import java.io.FileNotFoundException;

// Basic main function.
// Keep main simple. Do not overload it with much.
// Recall that you can always have the Game constructor take args[0] or even all
// of args as a parameter.
public class GameMain {
  public static void main(String[] args) throws FileNotFoundException {
    Game g = new Game(); //Make a new application (mine is just called Game)
    g.run();             //Run the application
    System.exit(0);      //Clean exit after finished execution
  }
}
