package PresentationLayer;

import javax.swing.*;

/**
 * Created by Maarten on 11-5-2015.
 */
public class PanelServing extends JFrame{
    private JTable table1;
    private JTable table2;
    private JButton bestellingenButton;
    private JButton tafelsButton;

    public PanelServing() {
        super("Hartige Hap - Bar");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1280, 720);
        super.setVisible(true);
    }

    private void createUIComponents() {
    }
}
