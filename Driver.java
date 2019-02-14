public class Driver {
  public static void main (String[] args) {
    KnightBoard testing = new KnightBoard (5,5);
    System.out.println (testing.toString ());
    System.out.println (testing.moveKnight (2,1, -1 , 2));
    System.out.println (testing.toString ());

  }
}
