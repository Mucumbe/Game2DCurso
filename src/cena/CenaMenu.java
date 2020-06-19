package cena;

import JGames2D.JGLevel;
import JGames2D.JGSprite;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaMenu extends JGLevel {

    CaminhoURL url = new CaminhoURL();

    //ATRIBUTOS DA CENA DE MENU
    JGSprite titulo = null;
    JGSprite ponteiro = null;
    JGSprite[] botoes = new JGSprite[4];
    String[] nomes = {"btn_play", "btn_controls", "btn_credits", "btn_exit"};

    @Override
    public void execute() {

        //ACTUALIZA A POSICAO DO SPRITE DEPONTEIRO NA TELA A CADA INSTANTE
        ponteiro.position.setX(gameManager.inputManager.getMousePosition().getX());
        ponteiro.position.setY(gameManager.inputManager.getMousePosition().getY());

        //VERIFICA SE O PONTEIRO DE MOUSE ESTA SOBRE ALGUM DOS BOTTOES 
        for (byte indece = 0; indece < botoes.length; indece++) {
            if (ponteiro.collide(botoes[indece])) {
                //SE MOUSE SOBRE O BOTAO (ANIMACAO 1 - QUADRO AMARELO)
                botoes[indece].setCurrentAnimation(1);

                //SE OUVER UM CLICK NA TELA
                if (gameManager.inputManager.mouseClicked()) {
                    switch (indece) {

                        case 0:
                           gameManager.setCurrentLevel(CenaAbertura.GAME);
                           return;
                        case 1:
                            //CHAMA A TELA DE CONTROLES
                            gameManager.setCurrentLevel(CenaAbertura.CONTROLES);
                            return;
                            
                        case 2:
                            //CHAMA A TELA DE CREDITOS
                            gameManager.setCurrentLevel(CenaAbertura.CREDITOS);
                            return;
                        case 3:
                            //PROGRAMAR A SAIDA DO JOGO
                            gameManager.finish();
                            break;

                    }
                }
            } else {
                //SE MOUSE SOBRE O BOTAO (ANIMACAO 1 - QUADRO AZUL)
                botoes[indece].setCurrentAnimation(0);
            }
        }

        //SE A TECLA ESC FOR PRESIONADA, ENCERA O JOGO
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }

    }

    @Override
    public void init() {

        //LOP QUE REALIZA O CARREGAMENTO DOS BOTOES
        for (byte indece = 0; indece < botoes.length; indece++) {
            botoes[indece] = this.createSprite(url.criaURL("/Images/" + nomes[indece] + ".png"), 2, 1);
            botoes[indece].addAnimation(1, false, 0);
            botoes[indece].addAnimation(1, false, 1);
            botoes[indece].zoom.setXY(0.6, 0.6);
            botoes[indece].position.setX(gameManager.windowManager.width / 2);
            botoes[indece].position.setY(180 + 120 * indece);

        }

        //CARREGA O SPRITE DE TITULO
        titulo = this.createSprite(url.criaURL("/Images/gametitle.png"), 1, 1);
        titulo.position.setX(gameManager.windowManager.width / 2);
        titulo.position.setY(titulo.frameHeight / 2);

        //CARREGA O SPRITE DE TITULO
        ponteiro = this.createSprite(url.criaURL("/Images/mira.png"), 1, 1);

        gameManager.windowManager.setBackgroundColor(Color.GREEN);
    }

}
