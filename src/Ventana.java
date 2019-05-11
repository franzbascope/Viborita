
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Ventana extends JFrame {


     
    public mapa getPanel() {
        return panel;
    }

    public void setPanel(mapa panel) {
        this.panel = panel;
    }

    private mapa panel;

    public Ventana() {
        this.setSize(700, 700);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        panel = new mapa();
        this.add(panel);
        this.validate();
        this.addKeyListener(panel);
    }

    public void setHilo(Thread hilo) {
        panel.setHilo(hilo);
    }
}
