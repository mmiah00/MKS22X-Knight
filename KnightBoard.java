import java.util.ArrayList;
import java.io.File;

public class KnightBoard {
  int[][] board;
  ArrayList <Integer> xsols;
  ArrayList <Integer> ysols;
  public KnightBoard (int startingRows, int startingCols) {
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException ();
    }
    board = new int [startingRows][startingCols];
    reset ();
    xsols = new ArrayList <Integer> ();
    ysols = new ArrayList <Integer> ();
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
        if (board[y][x] > 0) {
          return false;
        }
      }
    }
    return true;
  }

  public String toString () {
    if (empty ()) { //if empty
      String ans = "";
      for (int y = 0; y < board.length; y ++) {
        for (int x = 0; x < board[y].length; x ++) {
          if (x == board[y].length -1 ) { //if at edge
            ans += "_\n";
          }
          else {
            ans += "_ ";
          }
        }
      }
      return ans;
    } //else
    String ans = "";
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        if (board[y][x] < 10) { //if less than one leave a space i front of the digit
          if (x == board[y].length - 1) { //if at edge
            ans += " " + board[y][x] + "\n";
          }
          else {
            ans += " " + board[y][x] + " ";
          }
        }
        else {
          if (x == board[y].length - 1) {
            ans += board[y][x] + "\n";
          }
          else {
            ans += board[y][x] + " ";
          }
        }
      }
    }
    return ans;
  }

  private void placeKnight (int y, int x, int num) {
    if (y >= board.length || y < 0 || x >= board[y].length || x < 0) {
      throw new IllegalArgumentException ();
    }
    board [y][x] = num;
  }

  public boolean moveKnight (int y, int x, int xdir, int ydir, int num) { //xdir is either +/- 1 or +/- 2 and ydir is either +/- 1 or +/- 2
    if (Math.abs (xdir) > 2 || Math.abs (ydir) > 2 || Math.abs (xdir) == Math.abs (ydir)) { //^^
      throw new IllegalArgumentException ();
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

  private boolean removeKnight (int x, int y) {
    if (y > board.length || x > board[y].length || y < 0 || x < 0) { //if it's not within bounds
      throw new IllegalArgumentException ();
    }
    if (board[y][x] <= 0) { //if the knight hasn't been there yet
      return false;
    }
    board[y][x] -= 1;
    return true;
  }

  private boolean illegalState () {
    for (int y = 0; y < board.length; y ++) {
      for (int x = 0; x < board[y].length; x ++) {
        if (board[y][x] < 0) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean solve(int startingRow, int startingCol) {
    if (startingRow < 0 || startingCol < 0 || startingRow > board.length || startingCol > board [startingRow].length) {
      throw new IllegalArgumentException ("startingRow and startingCol have to be within bounds");
    }
    if (illegalState ()) {
      throw new IllegalStateException ();
    }
    placeKnight (startingRow, startingCol,1);
    if (solvable (startingRow, startingCol, 1)) {
      return true;
    }
    else {
      this.reset ();
      return false;
    }

  }

  private boolean solvable (int y, int x, int num) {
    if (num == board.length * board[y].length + 1) { //if filled the whole board
      return true;
    }
    else {
      if (moveKnight (y,x,-2,1,num)) { //left 2 down 1
        if (solvable (y + 1, x - 2, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x,-2,-1,num)) { //left 2 up 1
        if (solvable (y - 1, x - 2, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x,-1,2,num)) { //left 1 down 2
        if (solvable (y + 2, x - 1, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x, - 1, - 2,num)) { //left 1 up 2
        if (solvable (y -  2, x - 1, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x, 1, 2,num)) { //right 1 down 2
        if (solvable (y + 2, x + 1, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x, 1, -2,num)) { //right 1 up 2
        if (solvable (y - 2, x + 1, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x,2,1,num)) { //right 2 down 1
        if (solvable (y + 1, x + 2, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      if (moveKnight (y,x, 2, -1,num)) { //right 2 up 1
        if (solvable (y - 1, x + 2, num + 1)) { //checking from that location
          return true;
        }
        removeKnight (x,y);
      }
      return false;
    }
  }

  public int countSolutions (int startingRow, int startingCol) {
    return count (startingRow, startingCol, 0 , 1);
  }

  private int count (int y, int x, int partSum, int num) {
    if (num == board.length * board[y].length + 1) {
      return partSum;
    }
    else {
      if (moveKnight (y,x,-2,1,num)) { //left 2 down 1
        return count (y + 1, x - 2, partSum + 1, num + 1);
      }
      if (moveKnight (y,x,-2,-1,num)) { //left 2 up 1
        return count (y - 1, x - 2, partSum + 1, num + 1);
      }
      if (moveKnight (y,x,-1,2,num)) { //left 1 down 2
        return count (y + 2, x - 1, partSum + 1, num + 1);
      }
      if (moveKnight (y,x, - 1, - 2,num)) { //left 1 up 2
        return count (y -  2, x - 1, partSum + 1, num + 1);
      }
      if (moveKnight (y,x, 1, 2,num)) { //right 1 down 2
        return count (y + 2, x + 1, partSum + 1, num + 1);
      }
      if (moveKnight (y,x, 1, -2,num)) { //right 1 up 2
        return count (y - 2, x + 1, partSum + 1, num + 1);
      }
      if (moveKnight (y,x,2,1,num)) { //right 2 down 1
        return count (y + 1, x + 2, partSum + 1, num + 1);
      }
      if (moveKnight (y,x, 2, -1,num)) { //right 2 up 1
        return count  (y - 1, x + 2, partSum + 1, num + 1);
      }
      return 0;
    }
  }

}
