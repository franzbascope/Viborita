
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class mapa extends JPanel implements KeyListener, Runnable {

    int puntos = 0;
    String puntaje = "0";
    int tiempo = 0;
    ArrayList<Pieza> vibora = new ArrayList<Pieza>();
    ArrayList<Pieza> comida = new ArrayList<Pieza>();
    ArrayList<Pieza> extra = new ArrayList<Pieza>();

    int tamano = 30;
    private int fila = 20;
    private int columna = 20;
    int[][] matriz = new int[fila][columna];
    private boolean parar = false;
    private Thread hilo;
    int contador = 0;

    public Thread getHilo() {
        return hilo;
    }

    public void setHilo(Thread hilo) {
        this.hilo = hilo;
    }
    boolean arriba = false;
    boolean abajo = false;
    boolean derecha = true;
    boolean izauierda = false;

    public mapa() {
        for (int i = 7; i > 0; i--) {
            Pieza p = new Pieza(10 + i, 10);
            vibora.add(p);
        }

        //this.cargarMatriz();
        //      t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        Dibujar(g);
        updateUI();
    }

    public void cargarMatriz() {
        int extrax;
        int extray;
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                matriz[i][j] = 0;
            }
        }
        for (int i = 0; i < vibora.size(); i++) {
            int posX = vibora.get(i).getPosX();
            int posY = vibora.get(i).getPosY();
            matriz[posX][posY] = 1;
        }
        for (int i = 0; i < comida.size(); i++) {
            int posX = comida.get(i).getPosX();
            int posY = comida.get(i).getPosY();
            matriz[posX][posY] = 2;
        }
        if (extra.size() == 1) {
            extrax = extra.get(0).getPosX();
            extray = extra.get(0).getPosY();
            matriz[extrax][extray] = 3;
        }
    }

    public void Dibujar(Graphics g) {
        if (parar == true) {
            g.setColor(Color.red);
            g.drawString("-----Game Over-----", 230, 20);
        }
        g.setColor(Color.red);
        g.drawString("Puntaje: " + puntaje, 80, 20);
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (matriz[i][j] == 1) {
                    g.setColor(Color.green);
                    g.fillRect(40 + i * tamano, tamano + j * tamano, tamano, tamano);
                } else if (matriz[i][j] == 2) {
                    g.setColor(Color.red);
                    g.fillRect(40 + i * tamano, tamano + j * tamano, tamano, tamano);
                } else if (matriz[i][j] == 3) {
                    g.setColor(Color.blue);
                    g.fillRect(40 + i * tamano, tamano + j * tamano, tamano, tamano);
                } else {
                    g.setColor(Color.black);
                    g.fillRect(40 + i * tamano, tamano + j * tamano, tamano, tamano);
                }
            }
        }
    }

    public void moverse(KeyEvent e) {
        // Thread.sleep(100);
        int key = e.getKeyCode();
        if (parar == true) {
            vibora.removeAll(vibora);
            for (int i = 4; i > 0; i--) {
                Pieza p = new Pieza(10 + i, 10);
                vibora.add(p);
                puntaje = " ";
                puntos = 0;
                comida.clear();
                extra.clear();
            }
            parar = false;
            derecha = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if (derecha == false) {
                izauierda = true;
                arriba = false;
                abajo = false;
                derecha = false;
            }
        }

        if (key == KeyEvent.VK_RIGHT) {

            if (izauierda == false) {
                derecha = true;
                izauierda = false;
                arriba = false;
                abajo = false;
            }

        }

        if (key == KeyEvent.VK_UP) {
            if (abajo == false) {
                arriba = true;
                izauierda = false;
                abajo = false;
                derecha = false;
            }

        }

        if (key == KeyEvent.VK_DOWN) {
            if (arriba == false) {
                abajo = true;
                izauierda = false;
                arriba = false;
                derecha = false;
            }

        }
    }
    private int nextra = 0;

    public void moverPieza() {
        int ncomida = 0;
        boolean paso = false;
        boolean paso2 = false;
        int nfruta = 0;
        int extra2 = 0;
        try {
            while (true) {
                extra2++;
                if (parar == false) {
                    nfruta++;
                    ncomida++;
                    tiempo++;
                    contador++;
                    nextra++;
                    intercambiar(arriba, abajo, izauierda, derecha);
                    Thread.sleep(100);
                    repaint();
                    if (contador == 40 && comida.isEmpty()) {
                        comida();
                        contador = 0;
                        nfruta = 0;
                        paso2 = true;
                    }
                    if (nextra == 100) {
                        extra();
                        nextra = 0;
                        extra2 = 0;
                        paso = true;
                    }
                    if (extra2 == 30 && paso == true) {
                        extra.clear();
                        extra2 = 0;
                        paso = false;
                    }
                    if (nfruta == 40 && paso2 == true) {
                        comida.clear();
                        nfruta = 0;
                        paso2 = false;
                        contador = 0;
                    }
                    comio();
                    extracomio();
                } else {
                    System.out.println("");
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("" + ex);
        }

    }

    public void eliminar() {
        extra.removeAll(extra);
    }

    public void comida() {
        int x = (int) (Math.random() * 19);
        int y = (int) (Math.random() * 19);
        Pieza p = new Pieza(x, y);
        comida.add(p);
    }

    public void extra() {
        int x = (int) (Math.random() * 19);
        int y = (int) (Math.random() * 19);
        Pieza p = new Pieza(x, y);
        extra.add(p);
    }

    public boolean comio() {
        int posX;
        int posY;
        int posX1;
        int posY1;
        posX = vibora.get(0).getPosX();
        posY = vibora.get(0).getPosY();
        if (comida.size() == 1) {
            posX1 = comida.get(0).getPosX();
            posY1 = comida.get(0).getPosY();
            if (posX == posX1 && posY == posY1) {
                puntos = puntos + 10;
             //   for (int i = 0; i < 100; i++) {
                    crecer();
                //}
                
                // crecer();
                //  crecer();
                puntaje = String.valueOf(puntos);
                comida.clear();
                contador = 0;
                return true;
            }
        }

        return false;
    }

    public boolean extracomio() {
        int posX;
        int posY;
        int posX1;
        int posY1;
        for (int i = 0; i < vibora.size(); i++) {
            posX = vibora.get(i).getPosX();
            posY = vibora.get(i).getPosY();
            if (extra.size() == 1) {
                posX1 = extra.get(0).getPosX();
                posY1 = extra.get(0).getPosY();
                if (posX == posX1 && posY == posY1) {
                    puntos = puntos + 30;
                    puntaje = String.valueOf(puntos);
                    extra.clear();
                    nextra = 0;
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.moverse(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void intercambiar(boolean arriba, boolean abajo, boolean izquierda, boolean derecha) {
        int posX;
        int posY;
        for (int i = vibora.size() - 2; i >= 0; i--) {
            posX = vibora.get(i).getPosX();
            posY = vibora.get(i).getPosY();
            vibora.get(i + 1).setPosX(posX);
            vibora.get(i + 1).setPosY(posY);
            if (i == 0 && derecha == true) {
                if (posX == columna - 1) {
                    posX = -1;
                }

                if (matriz[posX + 1][posY] == 1) {
                    parar = true;
                } else {
                    vibora.get(i).setPosX(posX + 1);
                    vibora.get(i).setPosY(posY);

                }

            } else if (i == 0 && izquierda == true) {
                if (posX == 0) {
                    posX = 20;
                }
                if (matriz[posX - 1][posY] == 1) {
                    parar = true;
                } else {
                    vibora.get(i).setPosX(posX - 1);
                    vibora.get(i).setPosY(posY);
                }
            } else if (i == 0 && arriba == true) {
                if (posY == 0) {
                    posY = 20;
                }
                if (matriz[posX][posY - 1] == 1) {
                    parar = true;
                } else {
                    vibora.get(i).setPosX(posX);
                    vibora.get(i).setPosY(posY - 1);
                }
            } else if (i == 0 && abajo == true) {
                if (posY == 19) {
                    posY = -1;
                }
                if (matriz[posX][posY + 1] == 1) {
                    parar = true;
                } else {
                    vibora.get(i).setPosX(posX);
                    vibora.get(i).setPosY(posY + 1);
                }
            }
            cargarMatriz();
        }
    }

    public void crecer() {
        int posX = vibora.get(vibora.size() - 1).getPosX();
        int posY = vibora.get(vibora.size() - 1).getPosY();
        Pieza p = new Pieza(posX, posY);
        vibora.add(p);
    }

    @Override
    public void run() {
        moverPieza();
        repaint();
    }
}
