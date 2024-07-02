
import logic.*;

import util.*;


public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        StartWindow startWindow = new StartWindow(game);
        String text;

        if (game.startGame()) text = "You win!";
        else text = "You lose!";
        EndWindow endWindow = new EndWindow(text);
    }
}