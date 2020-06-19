package cena;

import JGames2D.JGLevel;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaGame extends JGLevel {

    @Override
    public void execute() {
        //SE O JOGADOR APERTAR ESC DURANTE O JOGO,VOLTA PARA CENA MENU
        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(CenaAbertura.MENU);
            return;
        }

    }

    @Override
    public void init() {
        
        //CONFIGURA O FUNDO DA CENA DO GAME
        gameManager.windowManager.setBackgroundColor(Color.DARK_GRAY);

    }

}
