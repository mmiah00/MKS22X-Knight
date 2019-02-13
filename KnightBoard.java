public class KnightBoard {
  int[][] board;
  int startx,starty;
  public KnightBoard (int startingRows, int startingCols) {
    startx = startingCols;
    starty = startingRows;
    reset ();
  }

  public void reset () {
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        board[y][x] = 0;
      }
    }
  }
}
