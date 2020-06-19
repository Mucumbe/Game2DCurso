package cena;

import JGames2D.JGLevel;
import JGames2D.JGSprite;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaMenu extends JGLevel {
    CaminhoURL url= new CaminhoURL();


    //ATRIBUTOS DA CENA DE MENU
    JGSprite titulo=null;
    
    @Override
    public void execute() {

        //SE A TECLA ESC FOR PRESIONADA, ENCERA O JOGO
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }

    }

    @Override
    public void init() {
        
        //CARREGA O SPRITE DE TITULO
        titulo=this.createSprite(url.criaURL("/Images/gametitle.png"), 1, 1);
        titulo.position.setX(gameManager.windowManager.width/2);
        titulo.position.setY(titulo.imageHeight/2);
                
        gameManager.windowManager.setBackgroundColor(Color.yellow);
    }

}
