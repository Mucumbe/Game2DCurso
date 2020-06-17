package cena;

import JGames2D.JGLevel;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaMenu extends JGLevel {

    @Override
    public void execute() {
        
        //SE A TECLA ESC FOR PRESIONADA, ENCERA O JOGO
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }

    }
    
    @Override
    public void init() {
        
        gameManager.windowManager.setBackgroundColor(Color.blue);
    }

}
