import GUI.GUI;
import bestroute.*;
import businesslogic.*;
import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Train Booking Application");
        GUI gui = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setContentPane(gui.panel1);
    }
}