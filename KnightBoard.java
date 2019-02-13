public class KnightBoard {
  int[][] board;
  public KnightBoard (int startingRows, int startingCols) {
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException ();
    }
    board = new int [startingRows][startingCols];
    reset ();
  }

  public void reset () {
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        board[y][x] = 0;
      }
    }
  }

  public String toString () {
    String ans = "";
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        if (board[y][x] < 10) {
          if (x == board[y].length - 1) {
            ans += " " + x + "\n";
          }
          ans += " " + x + " ";
        }
        else {
          if (x == board[y].length - 1) {
            ans += x + "\n";
          }
          ans += x + " ";
        }
      }
    }
    return ans;
  }

  private boolean moveKnight (int x, int y, int xdir, int ydir) { //xdir is either +/- 1 or +/- 2 and ydir is either +/- 1 or +/- 2
    if (Math.abs (xdir) > 2 || Math.abs (ydir) > 2 || y > board.length || x > board[y].length || y < 0 || x < 0) {
      throw new IllegalArgumentException ();
    }
    if (Math.abs (xdir) == Math.abs (ydir)) { //the knight has to move 1 in one direction and 2 in another
      return false;
    }
    if (x + xdir >= board[y].length || y + ydir >= board.length) { //if it goes out of bounds
      return false;
    }
    board [y + ydir] [x + xdir] += 1;
    return true;
  }

  /*
  Modifies the board by labeling the moves from 1 (at startingRow,startingCol) up to the area of the board in proper knight move steps.
@throws IllegalStateException when the board contains non-zero values.
@throws IllegalArgumentException when either parameter is negative
 or out of bounds.
@returns true when the board is solvable from the specified starting position
*/

  private boolean illegalState () {
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        if (board[y][x] < 0) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean solve(int startingRow, int startingCol) {
    if (startingRow < 0 || startingCol < 0 || startingRow > board.length || startingCol > board [startingRow].length) {
      throw new IllegalArgumentException ();
    }
    if (illegalState ()) {
      throw new IllegalStateException ();
    }
    if (solvable (startingRow, startingCol)) {
      return true;
    }
    else {
      this.reset ();
      return false;
    }

  }

  private boolean solvable (int y, int x) {
    if (y >= board.length || x >= board[y].length) {
      return true;
    }
    else {
      return false; 
    }
  }

/*
  private boolean solvable (int col) {
    if (col >= board[0].length) { //if reaches the end of the board
      return true; //return true
    }
    else {
      for (int r = 0; r < board.length; r ++) { //go through each row
        if (addQueen (col,r)) { //if you can add the queen
          if (solvable (col + 1)) { //check next column
            return true;
          }
          removeQueen (col,r); //remove queen afterwards
        }
      }
    }
    return false; //else return false
  }
*/




}
