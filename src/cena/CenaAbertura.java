package cena;

import JGames2D.JGLevel;
import JGames2D.JGSprite;
import JGames2D.JGTimer;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaAbertura extends JGLevel {

    public static final byte ABERTURA   = 0;
    public static final byte MENU       = 1;
    public static final byte GAME       = 2;
    public static final byte CONTROLES  = 3;
    public static final byte CREDITOS   = 4;

    //DECLARA UMA VARIAVEL PARA UM OBJECTO DE TEMPO
    JGTimer tempo = null;
    JGSprite empresa = null;
    int estado = 0;
    //INSTACIA DE CLASSE COM CONTROLES DA CENAABERTURA
    CaminhoURL abtCtl = new CaminhoURL();

   

    @Override
    public void execute() {
        //BLOCO ONDE SERA IMPLEMENTADO A LOGICA DA CENA
        //É CHAMADO N VEZES POR SEGUNDO
        //FPS-FRAMES PER SECOND-QUADROS POR SEGUNDO

        if (estado == 0) {
            //DIMINUI O ZOOM DA IMAGEM A CADA FRAME
            empresa.zoom.setX(empresa.zoom.getX() - 0.05);
            empresa.zoom.setY(empresa.zoom.getY() - 0.05);

            //ANIMACAO DO ZOOM CHEGOU AO FINAL, CHAMA PROXIMA TELA
            if (empresa.zoom.getX() <= 0.5) 
                estado=1;
        } else {
            //ACTUALIZACAO DO TEMPO
            tempo.update();

            //TESTA SE O INTERVALO DO TEMPO JA ACABO
            if (tempo.isTimeEnded()) {
                gameManager.setCurrentLevel(MENU);
                return;
            }
        }

        //SE O BOTAO DO MOUSE FOR CLICLADO, PASSA PARA PROXIMA CENA
        if (gameManager.inputManager.mousePressed()) {
            gameManager.setCurrentLevel(MENU);
            return;
        }

        //SE A TECLA DE ESPACO FOI PRESIONADA E CHAMA A SENA DE MENU
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)) {
            gameManager.setCurrentLevel(MENU);
            return;
        }

        //SE A TECLA ESC FOR PRESIONADA, ENCERA O JOGO
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }
    }

    @Override
    public void init() {

        //REALIZA O CAREGAMENTO DO SPRITE
        empresa = this.createSprite(abtCtl.criaURL("/Images/spr_produtors.png"), 1, 1);
        empresa.position.setX(gameManager.windowManager.width / 2);
        empresa.position.setY(gameManager.windowManager.height / 2);
        empresa.zoom.setXY(2, 2);

        //CONFIGURA A COR DE FUNDO DESTA SENA
        gameManager.windowManager.setBackgroundColor(Color.yellow);

        //INICIA O OBJECTO DE TEMPO PARA MARCAR 3 SEGUNDOS
        tempo = new JGTimer(2000);

        //BLOCO DE CODIGO CHAMADO POR UMA CENA E É ESCOLHIDA
        //PARA SER MOSTRADO NA JANELA
        //CHAMADA UMA UNICA VEZ NO MOMENTO EM QUE A CENA INICIA
        //SERVE PARA O CARREGAMENTO DOS RECURSOS DA SENA
    }

}
