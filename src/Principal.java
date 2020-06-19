
import JGames2D.JGEngine;
import cena.CenaAbertura;

import cena.CenaControles;
import cena.CenaCreditos;
import cena.CenaGame;
import cena.CenaMenu;

public class Principal {

    //CONSTANTES PUBLICAS DAS CLASSE
    public static final byte ABERTURA   = 0;
    public static final byte MENU       = 1;
    public static final byte GAME       = 2;
    public static final byte CONTROLES  = 3;
    public static final byte CREDITOS   = 4;

    public static void main(String[] args) {

        //INSTANCIA UM OBJECTO DE MOTOR
        JGEngine motor = new JGEngine();

        //CONFIGURA AS OPCOES DE JANELA DE EXECUCAO DO GAME
        //ATRAVEZ DO GERENTE DA JANELA DO MOTOR
        motor.windowManager.setResolution(800, 600, 32);
        motor.windowManager.setfullScreen(false);

        //REGISTA UMA CENA AO MOTOR GRAFICO
        motor.addLevel(new CenaAbertura(ABERTURA, MENU, GAME, CONTROLES, CREDITOS)); //CODIGO 0
        motor.addLevel(new CenaMenu());     //CODIGO 1
        motor.addLevel(new CenaGame());     //CODIGO 2
        motor.addLevel(new CenaControles());//CODIGO 3
        motor.addLevel(new CenaCreditos());//CODIGO 4

        //INICIA A EXECUCAO DO MOTOR
        motor.start();
    }

}
