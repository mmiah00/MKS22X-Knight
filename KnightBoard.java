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
    if (Math.abs (xdir) > 2 || Math.abs (ydir) > 2) {
      throw new IllegalArgumentException ();
    }
    if (Math.abs (xdir) == Math.abs (ydir)) {
      return false;
    }
    if (x + xdir >= board[y].length || y + ydir >= board.length) {
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
  public boolean solve(int startingRow, int startingCol) {
    return true;
  }




}
