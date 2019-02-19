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

  public void reset () {
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

  public boolean moveKnight (int x, int y, int xdir, int ydir) { //xdir is either +/- 1 or +/- 2 and ydir is either +/- 1 or +/- 2
    if (Math.abs (xdir) > 2 || Math.abs (ydir) > 2 || y > board.length || x > board[y].length || y < 0 || x < 0) {
      throw new IllegalArgumentException ();
    }
    if (x + xdir >= board[y].length || y + ydir >= board.length) { //if it goes out of bounds
      return false;
    }
    if (board[y + ydir][x+xdir] != 0) { //if the knight has already been there
      return false;
    }
    if (Math.abs (xdir) == Math.abs (ydir)) { //the knight has to move 1 in one direction and 2 in another
      return false;
    }
    board[y][x] += 1;
    board [y + ydir] [x + xdir] += 1;
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
      for (int r = y; r < board.length; r ++) {
        for (int c = x; c < board[r].length; c ++) {
          if (moveKnight (y,x, -1, 2)) {
            if (solvable (y + 1, x + 1)) {
              return true;
            }
            removeKnight (y,x);
          }
          if (moveKnight (y,x, 1, 2)) {
            if (solvable (y + 1, x + 1)) {
              return true;
            }
            removeKnight (y,x);
          }
          if (moveKnight (y,x, -1, -2)) {
            if (solvable (y + 1, x + 1)) {
              return true;
            }
            removeKnight (y,x);
          }
          if (moveKnight (y,x, 1, -2)) {
            if (solvable (y + 1, x + 1)) {
              return true;
            }
            removeKnight (y,x);
          }
        }
      }
    }
    return false;
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
