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


}
