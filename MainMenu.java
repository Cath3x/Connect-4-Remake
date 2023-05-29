import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainMenu {
     private JFrame frame;
     private JButton pvpStart;
     private JButton pveStart;
     private JPanel panel1;
     private JPanel panel2;
     private JLabel label;
     
     private int width = 250;
     private int height = 95;

     public void initializeWindow() {
          frame = new JFrame("Connect 4");
          frame.setSize(width, height);
          frame.setPreferredSize(new Dimension(width, height));

          panel1 = new JPanel();
          panel2 = new JPanel();

          pvpStart = new JButton("PvP");
          pveStart = new JButton("PvE");

          ActionListener listener = new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent a) {
                    Object origin = a.getSource();
                    GameCore newGame = new GameCore();
                    if (origin == pvpStart) {
                         newGame.initializeGame(1);
                         frame.dispose();
                    } else {
                         newGame.initializeGame(2);
                         frame.dispose();
                    }
               }
          };

          pvpStart.addActionListener(listener);
          pveStart.addActionListener(listener);

          label = new JLabel("Main Menu");

          panel1.add(pvpStart);
          panel1.add(pveStart);

          panel2.add(label);

          frame.add(panel2, BorderLayout.NORTH);
          frame.add(panel1, BorderLayout.SOUTH);
          frame.setResizable(false);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.pack();
          frame.setVisible(true);
     }
}
