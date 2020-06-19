package cena;

import JGames2D.JGLevel;
import JGames2D.JGSprite;
import JGames2D.JGVector2D;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaCreditos extends JGLevel {

    CaminhoURL url = new CaminhoURL();
    JGSprite creditos = null;

    @Override
    public void execute() {

        //SE O MOVIMENTO PROGRAMADO TERMINOU, VOLTA PARA TELA DE MENU
        if (creditos.isMoveEnded()) {
            gameManager.setCurrentLevel(CenaAbertura.MENU);
            return;
        }

        //VERIFICA SE A TECLA ESC FOI PRESIONADA E VOLTA PARA O MENU
        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(CenaAbertura.MENU);
            return;
        }
    }

    @Override
    public void init() {
        
        gameManager.windowManager.setBackgroundColor(Color.BLUE);

        creditos = this.createSprite(url.criaURL("/Images/spr_credits.png"), 1, 1);
        creditos.position.setX(gameManager.windowManager.width / 2);
        creditos.position.setY(gameManager.windowManager.height + creditos.frameHeight);

        //MOVIMENTA O SPRITE PARA NOVA POSICAO EM 8 SEGUNDOS
        creditos.moveTo(8000, new JGVector2D(creditos.position.getX(), -creditos.frameHeight / 2));

    }

}
