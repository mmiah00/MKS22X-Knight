import java.util.ArrayList;
import java.io.File;

public class KnightBoard {
  int[][] board;

  public KnightBoard (int startingRows, int startingCols) {
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException ();
    }
    board = new int [startingRows][startingCols];
    reset ();
  }

  private ArrayList <int[]> optimize (int y, int x) {
    /*
    for (int i= 0; i < moves.size (); i ++) {
      if (!moveKnight (y,x, moves.get(i)[0], moves.get(i)[1], 1)) {
        moves.remove (i);
      }
    }
    */
    ArrayList <int[]> moves = new ArrayList <int[]> (); //all possible moves from that spot
    if (moveKnight (y,x, 1, 2, 1)) {
      int[] one = {1,2};
      moves.add (one);
      removeKnight (y + 2, x + 1);
    }
    if (moveKnight (y,x, 1, -2, 1)) {
      int[] two = {1,-2};
      moves.add (two);
      removeKnight (y - 2, x + 1);
    }
    if (moveKnight (y, x, -1,2,1)) {
      int[] three = {-1,2};
      moves.add (three);
      removeKnight (y +2, x -1);
    }
    if (moveKnight (y, x, -1, -2,1)) {
      int[] four = {-1,-2};
      moves.add (four);
      removeKnight (y -2, x -1);
    }
    if (moveKnight (y, x, 2 ,1, 1)) {
      int[] five = {2,1};
      moves.add (five);
      removeKnight (y + 1, x + 2);
    }
    if (moveKnight (y,x, 2, -1, 1)) {
      int[] six = {2,-1};
      moves.add (six);
      removeKnight (y -1, x + 2);
    }
    if (moveKnight (y,x, -2, 1,1)) {
      int[] seven = {-2,1};
      moves.add (seven);
      removeKnight (y + 1, x - 2);
    }
    if (moveKnight (y,x, -2, -1,1)) {
      int[] eight = {-2,-1};
      moves.add (eight);
      removeKnight (y -1, x - 2);
    }
    return moves;

  }

  public boolean optimizedSolve (int startingRow, int startingCol) {
    if (startingRow < 0 || startingCol < 0 || startingRow > board.length || startingCol > board [startingRow].length) {
      throw new IllegalArgumentException ("startingRow and startingCol have to be within bounds");
    }
    if (illegalState ()) {
      throw new IllegalStateException ();
    }
    placeKnight (startingRow, startingCol,1);
    if (solveB (startingRow, startingCol, 2)) {
      return true;
    }
    else {
      this.reset ();
      return false;
    }
  }

  private boolean solveB (int y, int x, int num ) {
    if (num == board.length * board[y].length + 1) {
      return true;
    }
    ArrayList <int[]> outgoing = optimize (y, x); //mkaes list of possible moves
    for (int i = 0; i < outgoing.size (); i ++) {
      if (moveKnight (y, x, outgoing.get (i)[0], outgoing.get(i)[1], num)) {
        if (solveB (y + outgoing.get (i)[0], x + outgoing.get (i)[1], num + 1)) {
          return true;
        }
      }
    }
    return false;
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

  private boolean removeKnight (int x, int y) {
    if (y > board.length || x > board[y].length || y < 0 || x < 0) { //if it's not within bounds
      throw new IllegalArgumentException ();
    }
    if (board[y][x] <= 0) { //if the knight hasn't been there yet
      return false;
    }
    board[y][x] = 0;
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
    if (solvable (startingRow, startingCol, 2)) {
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
        else {
          removeKnight (x -2,y + 1);
        }
      }
      if (moveKnight (y,x,-2,-1,num)) { //left 2 up 1
        if (solvable (y - 1, x - 2, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x -2 ,y - 1);
        }
      }
      if (moveKnight (y,x,-1,2,num)) { //left 1 down 2
        if (solvable (y + 2, x - 1, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x -1 ,y + 2);
        }
      }
      if (moveKnight (y,x, - 1, - 2,num)) { //left 1 up 2
        if (solvable (y -  2, x - 1, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x - 1,y - 2);
        }
      }
      if (moveKnight (y,x, 1, 2,num)) { //right 1 down 2
        if (solvable (y + 2, x + 1, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x +1 ,y + 2);
        }
      }
      if (moveKnight (y,x, 1, -2,num)) { //right 1 up 2
        if (solvable (y - 2, x + 1, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x + 1,y -2 );
        }
      }
      if (moveKnight (y,x,2,1,num)) { //right 2 down 1
        if (solvable (y + 1, x + 2, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x+2,y + 1);
        }
      }
      if (moveKnight (y,x, 2, -1,num)) { //right 2 up 1
        if (solvable (y - 1, x + 2, num + 1)) { //checking from that location
          return true;
        }
        else {
          removeKnight (x + 2,y - 1);
        }
      }
      return false;
    }
  }

  public int countSolutions (int startingRow, int startingCol) {
    if (startingRow < 0 || startingCol < 0 || startingRow > board.length || startingCol > board [startingRow].length) {
      throw new IllegalArgumentException ("startingRow and startingCol have to be within bounds");
    }
    if (illegalState ()) {
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
      if (moveKnight (y,x,-2,1,num)) { //left 2 down 1
        ans += count (y + 1, x - 2, num + 1); //add num solutions from that spot
        removeKnight (x - 2, y + 1); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x,-2,-1,num)) { //left 2 up 1
        ans += count (y - 1, x - 2, num + 1); //add num solutions from that spot
        removeKnight (x - 2, y - 1); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x,-1,2,num)) { //left 1 down 2
        ans += count (y + 2, x - 1, num + 1); //add num solutions from that spot
        removeKnight (x - 1, y + 2); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x, - 1, - 2,num)) { //left 1 up 2
        ans += count (y - 2, x - 1, num + 1); //add num solutions from that spot
        removeKnight (x - 1, y - 2); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x, 1, 2,num)) { //right 1 down 2
        ans += count (y + 2, x + 1, num + 1); //add num solutions from that spot
        removeKnight (x + 1, y + 2); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x, 1, -2,num)) { //right 1 up 2
        ans += count (y -2, x + 1, num + 1); //add num solutions from that spot
        removeKnight (x + 1, y - 2); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x,2,1,num)) { //right 2 down 1
        ans += count (y + 1, x +2, num + 1); //add num solutions from that spot
        removeKnight (x + 2, y + 1); //remove from that spot to check other possibilities
      }
      if (moveKnight (y,x, 2, -1,num)) { //right 2 up 1
        ans += count (y - 1, x + 2, num + 1); //add num solutions from that spot
        removeKnight (x + 2, y -1);
      }
      return ans;
    }
  }

}
