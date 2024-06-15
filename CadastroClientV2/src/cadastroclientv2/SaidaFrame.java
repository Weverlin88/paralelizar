package cadastroclientv2;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class SaidaFrame extends JDialog {
    public JTextArea texto;

    public SaidaFrame() {
        setBounds(100, 100, 450, 300);
        setModal(false);
        texto = new JTextArea();
        add(texto);
    }
}
