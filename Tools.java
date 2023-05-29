public class Tools {
     public Boolean curState(int[][] game) {
          //Check Column
          for(int i = 0; i < 7; i++) {
               for(int j = 0; j < 3; j++) {
                    int tok = game[i][j];
                    if ((tok == 2 || tok == 1) && (tok == game[i][j+1] && tok == game[i][j+2] && tok == game[i][j+3])) {
                         return true;
                    }
               }
          }
          //Check Rows
          for(int i = 0; i < 6; i++) {
               for(int j = 0; j < 4; j++) {
                    int tok = game[j][i];
                    if ((tok == 2 || tok == 1) && (tok == game[j+1][i] && tok == game[j+2][i] && tok == game[j+3][i])) {
                         return true;
                    }
               }
          }
          //Check diagonal
          for(int i = 0; i < 3; i++) {
               for(int j = 0; j < 4; j++) {
                    int tok = game[j][i];
                    if ((tok == 2 || tok == 1) && (tok == game[j+1][i+1] && tok == game[j+2][i+2] && tok == game[j+3][i+3])) {
                         return true;
                    }
               }
          }
          
          for(int i = 5; i > 2; i--) {
               for(int j = 0; j < 4; j++) {
                    int tok = game[j][i];
                    if ((tok == 2 || tok == 1) && (tok == game[j+1][i-1] && tok == game[j+2][i-2] && tok == game[j+3][i-3])) {
                         return true;
                    }
               }
          }
          return false;
     }

     public Boolean fullBoard(int[][] boardState) {
          for(int i = 0; i < 7; i++) {
               if (boardState[i][5] == 0) return false;
          }
          return true;
     }

     public int[][] clone2DArray(int[][] original) {
          int h = original.length;
          int w = original[0].length;

          int[][] copy = new int[h][w];

          for(int i = 0; i < h; i++) {
               for(int j = 0; j < w; j++) {
                    copy[i][j] = original[i][j];
               }
          }

          return copy;
     }
}
