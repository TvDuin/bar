package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thomas on 11-5-2015.
 */
public class Gui {
    public static void main(String args[]) {
        PanelServing panel = new PanelServing();
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setVisible(true);
    }

}
