package cena;

import JGames2D.JGLevel;
import JGames2D.JGTimer;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class CenaAbertura extends JGLevel {

    //DECLARA UMA VARIAVEL PARA UM OBJECTO DE TEMPO
    JGTimer tempo = null;

    @Override
    public void execute() {
        //BLOCO ONDE SERA IMPLEMENTADO A LOGICA DA CENA
        //É CHAMADO N VEZES POR SEGUNDO
        //FPS-FRAMES PER SECOND-QUADROS POR SEGUNDO

        //ACTUALIZACAO DO TEMPO
        tempo.update();

        //TESTA SE O INTERVALO DO TEMPO JA ACABO
        if (tempo.isTimeEnded()) {
            gameManager.setCurrentLevel(1);
            return;
        }

        //SE O BOTAO DO MOUSE FOR CLICLADO, PASSA PARA PROXIMA CENA
        if (gameManager.inputManager.mousePressed()) {
            gameManager.setCurrentLevel(1);
            return;
        }

        //SE A TECLA DE ESPACO FOI PRESIONADA E CHAMA A SENA DE MENU
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)) {
            gameManager.setCurrentLevel(1);
            return;
        }

        //SE A TECLA ESC FOR PRESIONADA, ENCERA O JOGO
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }
    }

    @Override
    public void init() {
        //CONFIGURA A COR DE FUNDO DESTA SENA
        gameManager.windowManager.setBackgroundColor(Color.yellow);

        //INICIA O OBJECTO DE TEMPO PARA MARCAR 3 SEGUNDOS
        tempo = new JGTimer(3000);

        //BLOCO DE CODIGO CHAMADO POR UMA CENA E É ESCOLHIDA
        //PARA SER MOSTRADO NA JANELA
        //CHAMADA UMA UNICA VEZ NO MOMENTO EM QUE A CENA INICIA
        //SERVE PARA O CARREGAMENTO DOS RECURSOS DA SENA
    }

}
