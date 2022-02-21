
import control.Controller;
import control.GameController;
import dungeon.Game;
import dungeon.TheDungeon;
import dungeon.TheGame;
import dungeon.ThePlayer;
import model.Model;
import model.TheModel;
import view.TheGameView;

import java.io.StringReader;

/**
 * Class of controller.
 */
public class Driver {

  /**
   * The driver class of the program.
   *
   * @param args String args input
   */
  public static void main(String[] args) {

    //define the input source
    Readable input = new StringReader("g");

    //define the output sink
    Appendable output = new StringBuilder();

    if (args.length == 0) {
      System.out.println("Default args[]");
      args = "10 10 30 0.50f 0.25f false".split(" ");
    }
    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    int ic = Integer.parseInt(args[2]);
    float p = Float.parseFloat(args[3]);
    float d = Float.parseFloat(args[4]);
    boolean w = Boolean.parseBoolean(args[5]);

    Game game = new TheGame(new ThePlayer(), new TheDungeon(rows, cols, ic, p, d, w));

    //create a model
    Model m = new TheModel(game);
    //initiate a game
    m.startAGame();

    //build the view (in model for now)
    TheGameView v = new TheGameView(m);

    //create a controller
    Controller c = new GameController(m, v);

  }

}

