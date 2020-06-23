package cena;

import JGames2D.JGLayer;
import JGames2D.JGLevel;
import JGames2D.JGSprite;
import JGames2D.JGTimer;
import JGames2D.JGVector2D;
import controladores.CaminhoURL;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class CenaGame extends JGLevel {

    CaminhoURL url = new CaminhoURL();
    //DECLARA UMA CAMADA
    JGLayer camada = null;
    JGSprite aviao = null;

    ArrayList<JGSprite> vetTiros = null;
    ArrayList<JGSprite> vetInimigos = null;

    Random sorteio = new Random();

    JGTimer tempoInimigo = null;
    JGTimer tempoTiro = null;

    //METODO QUE ACTUALIZA A POSICAO DOS INIMIGOS
    private void actualizaInimigos() {

        //LOOP QUE PASSA POR TODOS OS INIMIGOS DO VECTOR
        for (JGSprite inimigo : vetInimigos) {
            
            if (inimigo.position.getY() > gameManager.windowManager.height + inimigo.frameHeight / 2) {
                inimigo.visible = false;
            } else {
                inimigo.position.setY(inimigo.position.getY() + 10);
                inimigo.position.setX(inimigo.position.getX()
                        + 10 * inimigo.direction.getX());
            }
        }
    }

    //METODO UTILIZADO PARA CRIAR UM NOVO INIMIGO NA TELA
    private void criaInimigo() {

        //ACTUALIZA O TEMPO DO INIMIGO
        tempoInimigo.update();

        //SE O INTERVALO DE TEMPO AINDA NAO ACABO,INTEROPO O METODO E RETORNA
        if (tempoInimigo.isTimeEnded() == false) {
            return;
        }

        //SE PASSOU PELO IF, DEIXA CRIAR UM NOVO INIMIGO MAIS RENICIE O TEMPO
        tempoInimigo.restart();

        //DECLARA A VARIAVEL DO SPRITE DO INIMIGO
        JGSprite novoInimigo = null;

        //TENTA REAPROVAITAR ALGUM INIMIGO QUE ESTEJA COM A VISIBILIDADE FALSA
        for (JGSprite inimigo : vetInimigos) {
            if (inimigo.visible == false) {
                novoInimigo = inimigo;
                novoInimigo.visible = true;
                break;
            }
        }
        if (novoInimigo == null) {
            novoInimigo = createSprite(url.criaURL("/Images/spr_enemy.png"), 1, 4);
            novoInimigo.addAnimation(10, true, 0, 2);
            vetInimigos.add(novoInimigo);
        }
        //REALIZAR O POSSICIONAMENTO DO INIMIGO
        novoInimigo.position.setY(-novoInimigo.frameHeight / 2);

        //REALIZA SORTEIO DE UM VALOR ALEATORIO ENTRE 0, 1 OU 2
        int posicao = sorteio.nextInt(3);

        //POSICIONA O NOVO INIMIGO EM X, DEPENDENDO DO VALOR SORTEADO
        //CONFIGURA A DIRECAO DE MOVIMENTO EM X
        if (posicao == 0) {
            novoInimigo.position.setX(novoInimigo.frameWidth / 2);
            novoInimigo.direction.setX(1);
        } else if (posicao == 1) {
            novoInimigo.position.setX(gameManager.windowManager.width / 2);
            novoInimigo.direction.setX(0);
        } else if (posicao == 2) {
            novoInimigo.position.setX(gameManager.windowManager.width - novoInimigo.frameWidth / 2);
            novoInimigo.direction.setX(-1);
        }
        //System.out.println(vetInimigos.size());

    }

    //METODO UTILIZADO PARA ACTUALIZAR A POSICAO DO TIRO
    private void actualizaTitos() {

        //FOR EACH EM JAVA - ACTUALIZA A POSICAO Y DE CADA TIRO ADICIONADO NO VECTOR
        for (JGSprite tiros : vetTiros) {

            //VERIFICA SE O TIRO SAIO DA JANELA(PARTE SUPERIO)
            //SE JA SAIU TORNA O TIRO INVISIVEL
            if (tiros.position.getY() < -tiros.frameHeight / 2) {
                tiros.visible = false;
            } else {
                tiros.position.setY(tiros.position.getY() - 10);
            }
        }
    }

    //METODO PARA CRIACAO DE NOVO TIRO
    private void criaTiro() {
        JGSprite novoTiro = null;

        //VERIFICA SE EXISTE ALGUM SPRTE COM VISIBILIDADE FALSA E REAPROVEITA
        for (JGSprite tiro : vetTiros) {

            if (tiro.visible == false) {
                novoTiro = tiro;
                novoTiro.visible = true;
                break;
            }
        }
        //NAO FOI POSSIVEL RECICLAR NINGUEM
        if (novoTiro == null) {
            novoTiro = createSprite(url.criaURL("/Images/spr_shot.png"), 1, 1);
            vetTiros.add(novoTiro);
        }

        novoTiro.position.setX(aviao.position.getX());
        novoTiro.position.setY(aviao.position.getY()
                - aviao.frameHeight / 2
                - novoTiro.frameHeight - 2);

        //System.out.println(vetTiros.size());
    }

    @Override
    public void execute() {

        // SEMPRE INICIA COM A VELOCIDADE DE DESLOCAMENTO HORIZONTAL 0
        camada.speed.setX(0);

        //SE O JOGADOR APERTAR ESC DURANTE O JOGO,VOLTA PARA CENA MENU
        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(CenaAbertura.MENU);
            return;
        }
        //METODO QUE CONTROLA OS MOVIMENTOS DO JOGADOR
        controlador();
        //CHA O METOD QUE ACTUALIZA A POSICA DOS TIROS
        actualizaTitos();

        //CHAMA O METODO QUE CRIA INIMIGO
        criaInimigo();
        //CHAMA O METODO QUE ACTUALIZA INIMIGO
        actualizaInimigos();
    }

    @Override
    public void init() {

        //CRIA O OBJECTO QUE MARCA O TEMPO NECESSARIO PARA NOVO TIRO
        tempoTiro = new JGTimer(500);
        tempoInimigo = new JGTimer(1900);

        //CARREGA O SPRITE CONTENDO A IMAGEM DO TIRO
        createSprite(url.criaURL("/Images/spr_shot.png"), 1, 1).visible = false;
        createSprite(url.criaURL("/Images/spr_enemy.png"), 1, 4).visible = false;

        //INSTANCIA O VETOR DE SPRITE PARA ARMAZENAR TIROS
        vetTiros = new ArrayList<JGSprite>();
        vetInimigos = new ArrayList<JGSprite>();

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

        //ACTUALIZA O TEMPO DECORRIDO
        tempoTiro.update();

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)) {

            if (tempoTiro.isTimeEnded()) {
                criaTiro();
                tempoTiro.restart();
            }

        }
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
