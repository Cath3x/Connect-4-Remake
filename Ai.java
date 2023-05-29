import java.util.ArrayList;
import java.util.Arrays;

public class Ai {
     Tools toolBox = new Tools();

     public int[] miniMax(int[][] game, int depth, int alpha, int beta, int p) {
          ArrayList<Integer> moves = getmoves(game);
          Boolean isLeafNode = toolBox.fullBoard(game);
          int[] item = new int[2];

          if (isLeafNode || depth == 0) {
               Boolean win = toolBox.curState(game);
               if(win) {
                    if (p == 1) {
                         item[1] = -1000000000;
                         return item;
                    } else {
                         item[1] = 100000000;
                         return item;
                    }
               } else {
                    item[1] = scoreBoard(game);
                    return item;
               }
          }
          // Maximizing Player
          if(p == 2) {
               int betterScore = -100000;
               for(int i = 0; i < moves.size(); i++) {
                    int[][] boardCopy = toolBox.clone2DArray(game);
                    playPiece(boardCopy, p, moves.get(i));
                    int newScore = miniMax(boardCopy, depth-1, alpha, beta, 1)[1];

                    if (newScore > betterScore) {
                         item[0] = i;
                         betterScore = newScore;
                    }
                    if(betterScore > alpha) alpha = betterScore;
                    if (beta <= alpha) break;        
               }
               item[1] = betterScore;
               return item;
          } else {
               int betterScore = 100000;
               for(int i = 0; i < moves.size(); i++) {
                    int[][] boardCopy = toolBox.clone2DArray(game);
                    playPiece(boardCopy, p, moves.get(i));
                    int newScore = miniMax(boardCopy, depth-1, alpha, beta, 2)[1];

                    if (newScore < betterScore) {
                         betterScore = newScore;
                         item[0] = i;
                    }
                    if (beta < betterScore) beta = betterScore;
                    if (beta <= alpha) break;        
               }
               item[1] = betterScore;
               return item;
          }
     }

     

     private void playPiece(int[][] board, int player, int col){
          for(int i = 0; i < 6; i++) {
               if(board[col][i] == 0) {
                    board[col][i] = player; 
                    return;
               }
          }
     }
     private ArrayList<Integer> getmoves(int[][] board) {
          ArrayList<Integer> validmoves = new ArrayList<Integer>();
          for(int i = 0; i < 7; i++) {
               if (board[i][5] == 0) {
                    validmoves.add(i);
               }
          }
          return validmoves;
     }

     public void printBoard(int[][] game) {
          System.out.println("------------------------------------------");
          for (int i = 0; i < 7; i++) {
               System.out.println(Arrays.toString(game[i]));
          }
     }
     // Since AI is always going to be player 2
     // We only check for 1
     private int scoreBoard(int[][] game) {
          int totalScore = 0;
          ArrayList<Integer> slice;

          //Priotize tokens in the middle
          for(int i = 0; i < 6; i++) {
               if(game[3][i] == 2) totalScore += 200;
               if(game[3][i] == 1) totalScore -= 200;

               if(game[2][i] == 2 || game[4][i] == 2) totalScore += 150;
               if(game[2][i] == 1 || game[4][i] == 1) totalScore -= 150;

               if(game[1][i] == 2 || game[5][i] == 2) totalScore += 100;
               if(game[1][i] == 1 || game[5][i] == 1) totalScore -= 100;

               if(game[0][i] == 2 || game[6][i] == 2) totalScore += 50;
               if(game[0][i] == 1 || game[6][i] == 1) totalScore -= 50;
          }
           
          //row slices
          for(int i = 0; i < 6; i++) {
               slice = new ArrayList<Integer>();
               for(int j = 0; j < 7; j++) {
                    slice.add(game[j][i]);
               }
               totalScore += scoreBoardHelper(slice);
          }

          //Column
          for (int i = 0; i < 7; i++) {
               slice = new ArrayList<Integer>();
               for(int j = 0; j < 6; j++) {
                    slice.add(game[i][j]);
               }
               totalScore += scoreBoardHelper(slice);
          }

          //Diagonal Slice 1
          for(int i = 0; i < 3; i++) {
               slice = new ArrayList<Integer>();
               int z = 0;
               for(int j = 0; j < (6-i); j++) {
                    slice.add(game[z+j][i+j]);
               }
               totalScore += scoreBoardHelper(slice);
          }
          for(int i = 1; i < 4; i++) {
               slice = new ArrayList<Integer>();
               int z = 0;
               for(int j = 0; j < (7-i); j++) {
                    slice.add(game[i+j][z+j]);
               }  
               totalScore += scoreBoardHelper(slice);
          }

          //Diagonal Slices 2
          for(int i = 5; i > 2; i--) {
               slice = new ArrayList<Integer>();
               int z = 0;
               for(int j = 0; j < (6-i); j++) {
                    slice.add(game[i+j][z+j]);
               }
               totalScore += scoreBoardHelper(slice);
          }
          for(int i = 1; i < 4; i++) {
               slice = new ArrayList<Integer>();
               int z = 0;
               for(int j = 0; j < (7-i); j++) {
                    slice.add(game[i+j][z+j]);
               }  
               totalScore += scoreBoardHelper(slice);
          }
          return totalScore;
     }

     private int scoreBoardHelper(ArrayList<Integer> slice) {
          int score = 0;
          String s = "";
          for(int i = 0; i < slice.size(); i++) {
               s = s + slice.get(i);
          }
          
          // Prioritize center columns
          // Positive Scores
          if (s.contains("02220")) {
               score += 2000;
          }
          if(s.contains("12220") || s.contains("02221") || s.contains("12020")) {
               score += 1000;
          }
          if(s.contains("0220") || s.contains("02020")) {
               score += 1500;
          }
          if(s.contains("0221") || s.contains("1220")) {
               score += 500;
          }

          //Negative Scores
          if (s.contains("01110")) {
               score -= 2000;
          }
          if(s.contains("21110") || s.contains("01112") || s.contains("21010")) {
               score -= 1000;
          }
          if(s.contains("0110") || s.contains("01010")) {
               score -= 1500;
          }
          if(s.contains("0112") || s.contains("2110")) {
               score -= 500;
          }

          return score;
     }
}
