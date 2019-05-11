
import java.awt.event.KeyListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author franz
 */
public class Consola {

    public static void main(String[] args) {
        Ventana v=new Ventana();
        Thread hilo=new Thread(v.getPanel());
        v.setHilo(hilo);
        hilo.start();
    
    }

}
