import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameCore {
     Ai AnAI;
     Tools toolBox = new Tools();

     private int[][] boardState = new int[7][6];
     private JLabel[][] boardImg = new JLabel[7][6];
     private int gamemode;

     private JLabel message;

     private int curPlayer = 0;

     private ActionListener ls;

     private JFrame game;

     private JPanel mainPanel;
     private JPanel menu;
     private JPanel moves;
     private JPanel board;

     private JButton mainMenu;
     private JButton c1, c2, c3, c4, c5, c6, c7;
     private JButton[] b = {c1, c2, c3, c4, c5, c6, c7};
     private JButton restart;

     public void initializeGame(int mode) {
          if (mode == 1) {
               gamemode = 1;
               initializeGameWindow();
          } else if (mode == 2){
               gamemode = 2;
               AnAI = new Ai();
               initializeGameWindow();
          }
     }

     public void initializeGameWindow() {
          //Clear board data of garbage values
          for(int i = 0; i < 7; i++) {
               for(int j = 0; j < 6; j++) {
                    boardState[i][j] =0;
               }
          } 
          // Assume human start first every time
          curPlayer = 1;
          
          // Action Listener for the buttons
          ls = new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent a) {
                    Object origin = a.getSource();
                    if (origin == mainMenu) {
                         MainMenu newMenu = new MainMenu();
                         newMenu.initializeWindow();
                         game.dispose();
                    } else {
                         int indexOf = -1;
                         for(int i = 0; i < b.length; i++) {
                              if (origin == b[i]) {
                                   indexOf = i;
                                   break;
                              }
                         }
                         if (indexOf >= 0) {
                              Boolean test = updateState(indexOf, curPlayer);

                              if(test == true) {
                                   gameRunner();
                              }
                         }
                    }
               }
          };

          //Frame for the Game
          game = new JFrame();
          game.setTitle("Connect 4");
          game.setSize(500, 500);
          game.setPreferredSize(new Dimension(700, 700));
          
          //Main Panel of the game
          mainPanel = new JPanel();
          mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

          //Panel that contain display and button to main menu
          menu = new JPanel();
          menu.setLayout(new BoxLayout(menu, BoxLayout.X_AXIS));
          mainMenu = new JButton("Main Menu");
          menu.setMaximumSize(new Dimension(700, 35));
          mainMenu.addActionListener(ls);
          
          mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
          menu.add(Box.createRigidArea(new Dimension(5, 35)));
          menu.add(mainMenu);
          message = new JLabel();
          message.setText("Player 1's turn");
          menu.setPreferredSize(new Dimension(700, 33));
          message.setAlignmentX(Component.RIGHT_ALIGNMENT);
          menu.add(Box.createRigidArea(new Dimension(20, 35)));
          menu.add(message);

          //Board Display Panel
          board = new JPanel();
          board.setLayout(new GridLayout(6, 7, 4, 4));
          board.setPreferredSize(new Dimension(700, 600));
          board.setBackground(Color.BLUE);

          //Panel containing buttons for the game
          moves = new JPanel();
          moves.setPreferredSize(new Dimension(700, 40));
          for (int i = 0; i < b.length; i++) {
               b[i] = new JButton();
               b[i].setPreferredSize(new Dimension(90, 20));
               b[i].addActionListener(ls);
               moves.add(b[i]);
          }

          initiateBoard();

          mainPanel.add(menu);
          mainPanel.add(board);
          mainPanel.add(moves);

          game.setResizable(false);
          game.add(mainPanel);
          game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          game.pack();
          game.setVisible(true);
     }
     
     private void initiateBoard() {
          for(int i = 0; i < 7; i++) {
               for(int j = 0; j < 6; j++) {
                    String aPath = "./Img/0.png";
                    BufferedImage anImg = null;
                    try {
                         anImg = ImageIO.read(new File(aPath));
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
                    ImageIcon ic = new ImageIcon(new ImageIcon(anImg).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
                    String name = i +""+ j;
                    JLabel pic = new JLabel();
                    pic.setIcon(ic);
                    pic.setName(name);

                    boardImg[i][j] = pic;
               }
          }
          for(int i = 5; i > -1; i--) {
               for (int j = 0; j < 7; j++) {
                    board.add(boardImg[j][i]);
               }
          } 
     }

     private boolean updateState(int col, int curP) {
          if (boardState[col][5] != 0) { 
               return false;
          } else {
               if (curP == 1 || curP == 2) {
                    int i;
                    for (i = 0; i < 6; i++) {
                         if (boardState[col][i] == 0) break;
                    }
                    boardState[col][i] = curPlayer;
                    String aPath = "./Img/" + curP + ".png";
                    BufferedImage anImg = null;
                    try {
                         anImg = ImageIO.read(new File(aPath));
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
                    ImageIcon ic = new ImageIcon(new ImageIcon(anImg).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
                    boardImg[col][i].setIcon(ic);
               }
          }
          return true;
     }

     private void gameRunner() {
          Boolean win = toolBox.curState(boardState);
          
          if(win != true) {
               if(gamemode == 1) {
                    if(curPlayer == 1) {
                         curPlayer = 2;
                         message.setText("Player 2's turn");
                    } else {
                         curPlayer = 1;
                         message.setText("Player 1's turn");
                    }
               } else if (gamemode == 2) {
                    if(curPlayer == 1) {
                         curPlayer = 2;
                         int min = -100000;
                         int max = 100000;
                         int[] pred = AnAI.miniMax(boardState, 5, min, max, 2);
                         b[pred[0]].doClick();
                    } else {
                         curPlayer = 1;
                    }
               }
          } else {
               restart = new JButton("Restart");
               ls = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent a) {
                         Object origin = a.getSource();
                         if (origin == restart) {
                              game.dispose();
                              initializeGameWindow();
                         }
                    }
               };
               restart.addActionListener(ls);

               if(gamemode == 1) {
                    if(curPlayer == 1) {
                         message.setText("Player 1 wins");
                    } else {
                         message.setText("Player 2 wins");
                    }
               }
               
               moves .removeAll();
               moves.add(restart);
               moves.revalidate();
               moves.repaint();
          }

     }

     public int[][] getBoardState() {
          return boardState;
     }

     public int getPlayer() {
          return curPlayer;
     }

     public void setPlayer(int p) {
          curPlayer = p;
     }

     public void setBoard(int [][] b) {
          boardState = b;
     }
}
