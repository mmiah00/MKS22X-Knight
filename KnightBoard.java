import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

public class KnightBoard {
  private int [][] board;
  private static int [][] moves = { {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                                    {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

  public KnightBoard (int startingRows, int startingCols) {
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException ();
    }
    board = new int [startingRows][startingCols];
    reset ();
  }

  private void reset () {
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        board[y][x] = 0;
      }
    }
  }

  private boolean empty () {
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        if (board[y][x] != 0) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean onBoard (int y, int x) {
    return ( y >= 0 && x >= 0 && y < board.length && x < board[y].length && board[y][x] == 0);
  }

  private void placeKnight (int y, int x, int num) {
    if (y >= board.length || y < 0 || x >= board[y].length || x < 0) {
      throw new IllegalArgumentException ();
    }
    board [y][x] = num;
  }

  private void removeKnight (int y, int x){
    if (y > board.length || y < 0 || x > board[y].length || x < 0) {
      throw new IllegalArgumentException ();
    }
    board[y][x] = 0;
  }

  private boolean solveSlow (int y, int x) {
    if (!empty ()) {
      throw new IllegalStateException ();
    }
    if (y > board.length || y < 0 || x > board[y].length || x < 0) {
      throw new IllegalArgumentException ();
    }
    board[y][x] = 1;
    return solvable (y,x, 2);
  }

  private boolean solvable (int y, int x, int num){
    if (num == board.length * board[0].length) {
      return true;
    }
    else {
      for (int i = 0; i < moves.length ; i ++) {
        if (onBoard (y + moves[i][0], x + moves[i][1])) {
          board [y + moves[i][0]] [x + moves[i][1]] = num;
          if (solvable (y + moves[i][0], x + moves[i][1], num + 1)) {
            return true;
          }
          removeKnight (y + moves[i][0], x + moves[i][1]);
        }
      }
      return false;
    }
  }

  public boolean solve (int startingRow, int startingCol) {
    if (!empty ()) {
      throw new IllegalStateException ();
    }
    if (!onBoard (startingRow, startingCol)) {
      throw new IllegalArgumentException ();
    }
    board[startingRow][startingCol] = 1;
    return solveH (startingRow, startingCol, 2);
  }

  private boolean solveH (int y, int x, int num) {
    if (num == board.length * board[y].length) {
      return true;
    }
    else {
      for (int i = 0; i < moves.length; i ++) {
        int[] optimal = leastMoves (y,x); //coordinate with the least possible moves
        if (optimal [0] == 1) {
          placeKnight (optimal [1], optimal [2], num);
          if (solveH (optimal[1], optimal [2], num + 1)) {
            return true;
          }
        }
      }
      return false;
    }
  }

  private int[] leastMoves (int y, int x) { //returns coordinates of the spot from y,x that has the least amount of outgoing moves
    int[] ans = new int [3];
    int min = 8; //8 being highest possible moves
    for (int i = 0; i < moves.length; i ++) {
      if (onBoard (y + moves[i][0], x + moves[i][1])) {
        int movesHere = numberofMoves (y + moves[i][0], x + moves[i][1]);
        if (movesHere < min) { //finding spot with lowest number of moves from that spot
          min = movesHere;
          ans [0] = 1; //move is possible (number of outgoing moves != 0 )
          ans[1] = y + moves[i][0]; // new y coordinate
          ans [2] = x + moves[i][1]; //new x coordinate
        }
      }
    }
    return ans;
  }

  private int numberofMoves (int y, int x) { //return number of moves from that spot
    int ans = 0;
    for (int i = 0; i < moves.length; i ++) {
      if (onBoard (y+ moves[i][0], x + moves[i][1])) {
        ans ++;
      }
    }
    return ans;
  }

  private boolean moveKnight (int y, int x, int xdir, int ydir, int num) { //xdir is either +/- 1 or +/- 2 and ydir is either +/- 1 or +/- 2
    if (Math.abs (xdir) > 2 || Math.abs (ydir) > 2 || Math.abs (xdir) == Math.abs (ydir)) { //^^
      return false;
    }

    if (y >= board.length || y < 0 || x >= board[y].length || x < 0) { //out of bounds
      throw new IllegalArgumentException ();
    }
    if (y + ydir >= board.length || y + ydir < 0 || x + xdir >= board[y].length || x + xdir < 0) { //if it goes out of bounds
      return false;
    }
    if (board[y + ydir][x+xdir] != 0) { //if the knight has already been there
      return false;
    }
    placeKnight (y + ydir, x + xdir, num);
    return true;
  }

  public int countSolutions (int startingRow, int startingCol) {
    if (startingRow < 0 || startingCol < 0 || startingRow > board.length || startingCol > board [startingRow].length) {
      throw new IllegalArgumentException ("startingRow and startingCol have to be within bounds");
    }
    if (!empty ()) {
      throw new IllegalStateException ();
    }
    placeKnight (startingRow, startingCol,1);
    return count (startingRow, startingCol, 2);
  }

  private int count (int y, int x, int num) {
    int ans = 0;
    if (num == board.length * board[y].length + 1) {
      return 1; //if fills board, thats one solution
    }
    else {
      for (int i = 0; i < moves.length; i ++) {
        if (moveKnight (y, x, moves[i][0], moves[i][1], num)) {
          ans += count (y + moves[i][1], x + moves[i][0], num + 1);
          removeKnight (y + moves[i][1], x + moves[i][0]);
        }
      }
      return ans;
    }
  }

  public String toString () {
    String ans = "";
    if (empty ()) {
      for (int r = 0; r < board.length; r ++) {
        for (int c = 0;  c < board[r].length; c ++) {
          ans += "_ ";
          if (c == board[r].length - 1) {
            ans +="\n";
          }
        }
      }
      return ans;
    }

    for (int r = 0 ; r < board.length; r ++ ) {
      for (int c = 0; c < board[r].length; c ++) {
        int now = board[r][c];
        if (now == 0) {
          if (board.length * board[r].length < 10) {
            ans += " " + (board.length * board[r].length) + " ";
          }
          else {
            ans += (board.length * board[r].length) + " ";
          }
        }
        else {
          if (now < 10) {
            ans += " "+now + " ";
          }
          else {
            ans += now +" ";
          }
        }
        if (c == board[r].length -1){
          ans += "\n";
        }
      }
    }
    return ans;
  }

  public static void main (String[] args) {
    KnightBoard test = new KnightBoard (5,4);
    System.out.println (test.toString ());
    test.solve (0,0);
    System.out.println (test.toString ());
  }


}

/*
private ArrayList <int[]> possibleMoves (int y, int x) { //returns an arrayList of all the possible moves from that spot
  ArrayList <int[]> ans = new ArrayList <int[]> ();
  for (int i = 0; i < moves.length; i ++) {
    int[] cors = new int [2];
    if (onBoard (y + moves[i][0], x + moves[i][1])) {
      cors [0] = moves[i][0];
      cors [1] = moves[i][1];
      ans.add (cors);
    }
  }
  return ans ;
}

private void numberMoves () {
  for (int y = 0; y < board.length; y ++) {
    for (int x = 0; x < board[y].length; x ++) {
      numMoves [y][x] = numberofMoves (y,x);
    }
  }
}
*/
