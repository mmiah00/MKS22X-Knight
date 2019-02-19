public class Driver {
  public static void main (String[] args) {
    KnightBoard testing = new KnightBoard (5,5);
    for (int y = 0; y < 5; y ++) {
      for (int x = 0; x < 5; x ++) {
        System.out.println (testing.solve (y,x)); 
      }
    }
  }
}
