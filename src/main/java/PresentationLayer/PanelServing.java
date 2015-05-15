package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Maarten on 11-5-2015.
 */
<<<<<<< HEAD
public class PanelServing extends JPanel {
=======
public class PanelServing extends JFrame{
>>>>>>> 38be585eabadb00b5112219c927344ab0d5cdb78
    private JTable table1;
    private JTable table2;
    private JButton bestellingenButton;
    private JButton tafelsButton;

<<<<<<< HEAD


    private void createUIComponents() {
        // TODO: place custom component creation code here
       /* PanelServing panelServing = new PanelServing();
        panelServing.setLayout(new GridLayout(4,7));
        panelServing.add(table1);
        panelServing.add(table2);
        panelServing.add(bestellingenButton);
        panelServing.add(tafelsButton);*/

=======
    public PanelServing() {
        super("Hartige Hap - Bar");
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(1280, 720);
        super.setVisible(true);
    }

    private void createUIComponents() {
>>>>>>> 38be585eabadb00b5112219c927344ab0d5cdb78
    }
}
