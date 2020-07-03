import com.HammingHuffman.gui.GUI_Strings;
import com.HammingHuffman.gui.Main_Window;

import javax.swing.*;
import java.awt.*;


public class Main {

    public static void main(String[] args) {
        //1°Init Content Form
        Main_Window mainWindow= new Main_Window();

        //2°Init Frame where this content will go, with it's title
        JFrame frame = new JFrame(GUI_Strings.window_title);
        frame.pack();

        //3°Set content to the pane
        frame.setContentPane(mainWindow.getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set operation to close Window

        //4° Make it usable - show on the screen
        mainWindow.setCentered(frame);
        frame.setVisible(true);
    }


}


