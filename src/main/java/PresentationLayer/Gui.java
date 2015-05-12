package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thomas on 11-5-2015.
 */
public class Gui {
    public static void main(String args[]) {
<<<<<<< HEAD
        PanelServing panel = new PanelServing();
        JFrame frame = new JFrame();
        frame.add(panel);
=======
        JFrame frame = new JFrame("Hartige Hap - Bar");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PanelServing panelServing = new PanelServing();
        frame.add(panelServing);
>>>>>>> 38be585eabadb00b5112219c927344ab0d5cdb78
        frame.setVisible(true);
    }

}
