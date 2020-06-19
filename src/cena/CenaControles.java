package cena;

import JGames2D.JGLevel;
import JGames2D.JGSprite;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaControles extends JGLevel {
    CaminhoURL url= new CaminhoURL();
    JGSprite controles=null;

    @Override
    public void execute() {
        //SE A TECLA ESC FOR PRESIONADA:VOLTA PARA cENA DE MENU
        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(CenaAbertura.MENU);
            return;
        }
    }

    @Override
    public void init() {
        
        gameManager.windowManager.setBackgroundColor(Color.blue);

        //CARREGA E POSICIONA O CONTROL DE SPRINT NA TELA
        controles = this.createSprite(url.criaURL("/Images/controls.png"), 1, 1);
        controles.position.setX(gameManager.windowManager.width / 2);
        controles.position.setY(gameManager.windowManager.height/2);
        controles.zoom.setXY(0.5, 0.5);

    }

}
