//******************************************************************************
// Main.java
// 
// A simple text game with rooms, character, NPCs, monsters and a GUI console
//
//******************************************************************************

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        C.io.setTitle("Console");
        C.io.setInfoTitle("Info");
        Game game = new Game();
        game.runGame();
    }
}
