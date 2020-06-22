package cena;

import JGames2D.JGLayer;
import JGames2D.JGLevel;
import JGames2D.JGSprite;
import JGames2D.JGVector2D;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaGame extends JGLevel {

    CaminhoURL url = new CaminhoURL();
    //DECLARA UMA CAMADA
    JGLayer camada = null;
    JGSprite aviao = null;

    @Override
    public void execute() {

        // SEMPRE INICIA COM A VELOCIDADE DE DESLOCAMENTO HORIZONTAL 0
        camada.speed.setX(0);

        //SE O JOGADOR APERTAR ESC DURANTE O JOGO,VOLTA PARA CENA MENU
        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(CenaAbertura.MENU);
            return;
        }
        controlador();

    }

    @Override
    public void init() {

        //CARREGA O SPRITE DO JOGADOR
        aviao = this.createSprite(url.criaURL("/Images/spr_airplane.png"), 1, 4);
        aviao.addAnimation(10, true, 0, 2);
        aviao.position.setX(gameManager.windowManager.width / 2);
        aviao.position.setY(500);
        //CONFIGURA O FUNDO DA CENA DO GAME
        gameManager.windowManager.setBackgroundColor(Color.DARK_GRAY);

        //CRIA UMA CAMADA PARA CENA
        camada = this.createLayer(url.criaURL("/Images/spr_elements.png"),
                new JGVector2D(25, 19)//COLUNA LINHA
                ,
                 new JGVector2D(32, 32), true);

        //CONFIGURA OS BRICK DA CAMADA E SEUS RESPECTIVOS QUADROS DE TILESIMAGE
        camada.setFrameIndex(0, 1);
        camada.setFrameIndex(1, 2);
        camada.setFrameIndex(2, 3);
        camada.setFrameIndex(3, 2);

        for (int linha = 0; linha < 19; linha++) {
            for (int coluna = 0; coluna < 25; coluna++) {
                camada.setFrameIndex(linha, coluna, 2);
            }
            //CONFIGURA ALGUNS BRICKS ESPECIFICOS PARA NAO MOSTRAR SOMENTE O ACEANO
            camada.setFrameIndex(2, 7, 8);
            camada.setFrameIndex(6, 5, 9);
            camada.setFrameIndex(10, 3, 10);
            camada.setFrameIndex(16, 20, 9);
            camada.setFrameIndex(18, 10, 8);

            //CONFIGURA A VELOCIDADE DE DESLOCAMENTO DA CAMADA EM Y
            camada.speed.setY(5);
        }
    }

    private void controlador() {

        //CONTROLA A DIRECCAO DO JOGADOR EM X
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_LEFT)) {
            aviao.position.setX(aviao.position.getX() - 10);
        } else if (gameManager.inputManager.keyPressed(KeyEvent.VK_RIGHT)) {
            aviao.position.setX(aviao.position.getX() + 10);
        }
        //CONTROLA A DIRECCAO DO JOGADOR EM y
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_UP)) {
            aviao.position.setY(aviao.position.getY() - 10);
        } else if (gameManager.inputManager.keyPressed(KeyEvent.VK_DOWN)) {
            aviao.position.setY(aviao.position.getY() + 10);
        }

        //VERIFICA SE O SPRITE ULTRAPASSOI ALHUM LIMITE DA TELA EM X
        //E CORIGE O POSICIONAMENTO
        if (aviao.position.getX() < aviao.frameWidth / 2) {
            aviao.position.setX(aviao.frameWidth / 2);
        } else if (aviao.position.getX() > (gameManager.windowManager.width - aviao.frameWidth / 2)) {
            aviao.position.setX(gameManager.windowManager.width - aviao.frameWidth / 2);
        }
        //VERIFICA SE O SPRITE ULTRAPASSOI ALHUM LIMITE DA TELA EM Y
        //E CORIGE O POSICIONAMENTO
        if (aviao.position.getY() < aviao.frameHeight / 2 + 14) {
            aviao.position.setY(aviao.frameHeight / 2 + 14);
        } else if (aviao.position.getY() > (gameManager.windowManager.height - aviao.frameHeight / 2 + 4)) {
            aviao.position.setY(gameManager.windowManager.height - aviao.frameHeight / 2 + 4);
        }

    }

}
