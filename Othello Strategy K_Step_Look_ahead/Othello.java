import java.io.*;
import java.util.*;


class Pair{
    int row;
    int col;
    public Pair(int i,int j){
        row=i;
        col=j;
    }
}

public class Othello {
    int turn;
    int winner;
    int board[][];
    //add required class variables here

    public Othello(int[][] board__) throws Exception {
        
        winner = 99; //Garbage value
        turn =0; // Black starts the game

        board=board__;
    }


    public static List<Pair> generateValidMoves(int[][] board,int turn){
        List<Pair>ans=new ArrayList<>();

        int[][] directions=new int[][]{{0,1},{1,0},{-1,0},{0,-1},{1,-1},{-1,1},{-1,-1},{1,1}};

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                if(board[i][j]==-1){
                    //check out all 8 directions if the cell is empty
                    for(int[] drxn:directions){
                     
              
                        int r = i + drxn[0];
                        int c = j + drxn[1];

                        if (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] != -1 && board[r][c] != turn) {
                            int opp = (turn == 0) ? 1 : 0;
                            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == opp) {
                                r += drxn[0];
                                c += drxn[1];
                            }
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == turn) {
                                ans.add(new Pair(i, j)); // Valid move found
                                break; // No need to check further directions
                            }
                    }
                }
            
            }
            }
        }
        return ans;
    }
    public  boolean isBoardFull(){

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j]==-1){
                    return false;
                }
            }
        }
        return true;
    }


    public static int boardScore(int[][] board) {
        /*return num_black_tiles - num_white_tiles 

         *  Black wants to maximise this function, and white wants to minimize it.
        */
        int num_blacks=0;
        int num_whites=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j]==0){
                    num_blacks++;
                }
                else if(board[i][j]==1){
                    num_whites++;
                }
            }
        }
       
        return num_blacks-num_whites;
        

      
    }

    public static int[][] board_after_move(int[][] board, Pair p, int turn){
        int[][] copy = new int[board.length][8];
        for (int i = 0; i < board.length; i++) {
            for(int j=0;j<8;j++){
                copy[i][j]=board[i][j];
            }
        }

        int[][] directions=new int[][]{{0,1},{1,0},{-1,0},{0,-1},{1,-1},{-1,1},{-1,-1},{1,1}};
        copy[p.row][p.col]=turn;
        int i=p.row;
        int j=p.col;
        List<int[]>valid_drxns=new ArrayList<>();
        for(int[] drxn:directions){
            int r=i+drxn[0];
            int c=j+drxn[1];
  
            //bound checking
            if(r>=0&&r<8&&c>=0&&c<8){
                if(copy[r][c]==-1||copy[r][c]==turn){
                    continue;
                }
                else{
                    int opp=(turn==0)?1:0;
                    while(r>=0&&r<8&&c>=0&&c<8&&copy[r][c]==opp){
                        r=r+drxn[0];
                        c=c+drxn[1];
                    }
                    if (r >= 0 && r < 8 && c >= 0 && c < 8 && copy[r][c] == turn) {
                        valid_drxns.add(drxn); // Add the initial empty cell as a valid move
                    }
                }
            }

        }

        for(int[] d: valid_drxns){
            int r=i+d[0];
            int c=j+d[1];

            int opp=(turn==0)?1:0;
            while(r>=0&&r<8&&c>=0&&c<8&&copy[r][c]==opp){
                copy[r][c]=turn;
                r=r+d[0];
                c=c+d[1];
                
            }
        }
        return copy;

    }

    public static int k_step_look_ahead( int k, int turn , int[][] board , int curr_depth , Pair final_move){
        
        if(curr_depth==k){
            int score=boardScore(board);
            return score;
        }
        int final_score=(turn==0) ? Integer.MIN_VALUE: Integer.MAX_VALUE;
        List<Pair>moves=generateValidMoves(board, turn);
        if (moves.isEmpty()) {
            int temp_score = k_step_look_ahead(k, 1 - turn, board, curr_depth + 1, final_move);
            return temp_score;
        }
       
            
        for(Pair p: moves){
            int[][] newBoard=board_after_move(board,p,turn);
            int temp_score=k_step_look_ahead(k, 1-turn, newBoard, curr_depth+1,final_move);
            if(turn==0){
                if(temp_score>final_score){
                    final_score=temp_score;
                    if (curr_depth == 0) {
                        final_move.row = p.row;
                        final_move.col = p.col;
                    }
                }
            }
            else{
                if(temp_score<final_score){
                    final_score=temp_score;
                    if (curr_depth == 0) {
                        final_move.row = p.row;
                        final_move.col = p.col;
                    }
                }
            }
            
        }

        
       
        return final_score  ;
    }
    public int bestMove(int k) {
        /*  build a Minimax tree of depth k (current board being at depth 0),
         * for the current player (siginified by the variable turn), and propagate scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j; return i*8+j
         * In case of ties, return the smallest integer value representing the tile with best score.
        */
        int curr_depth=0;
        int[][] copy=getBoardCopy();
        Pair p=new Pair(-1, -1);
        int ans=k_step_look_ahead( k, turn, copy,curr_depth,p);
        System.out.println("Player "+ turn +" places on "+"("+p.row+ "," + p.col + ")");
        if(p.row==-1 && p.col==-1){
            System.out.println("No valid moves for " + turn  + ", passing the chance");
        }
        return 8*p.row+p.col;
    }

    public ArrayList<Integer> fullGame(int k) {
        /* Complete this function to compute and execute the best move for each player starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and the turn
         * at each step. In the end, modify the winner variable as required.
         */
        ArrayList<Integer> ans =new ArrayList<>();
        while (!isBoardFull()&&(generateValidMoves(board, 0).size()!=0||generateValidMoves(board, 1).size()!=0)) {
            int bestMove=bestMove(4);
            int i=bestMove/8;
            int j=bestMove%8;
            if(i!=-1&&j!=-1){
                board=board_after_move(board, new Pair(i,j), turn);
                ans.add(bestMove);
            }
            turn = 1- turn;
        }
       
        
        winner= boardScore(board)>0 ?0:1;
        if(boardScore(board)==0){
            winner=-1;
        }
        if(isBoardFull()){
            System.out.println("Board is full "+ getWinner() + " is the winner");
        }

        return ans;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for(int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }
    public static void main(String[] args) throws Exception {
 
   // Replace with your input file path
        int[][] board = {
            { -1, -1, -1, -1, -1, -1, -1, -1 },
            { -1, -1, -1, -1, -1, -1, -1, -1 },
            { -1, -1, -1,  0,  1, -1, -1, -1 },
            { -1, -1, -1,  0,  1, -1, -1, -1 },
            { -1, -1, -1,  0,  1, -1, -1, -1 },
            { -1, -1, -1, -1, -1, -1, -1, -1 },
            { -1, -1, -1, -1, -1, -1, -1, -1 },
            { -1, -1, -1, -1, -1, -1, -1, -1 }
        };
        Othello game = new Othello(board);

     
        System.out.println(game.fullGame(2));
        System.out.println(game.getWinner());
    }
}
