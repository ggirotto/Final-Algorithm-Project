package t3jb;
import java.util.Scanner;

public class listTest {

  public static void main( String[] args ) {
    List L = new List();

    Scanner input = new Scanner( System.in );

    while ( input.hasNext() ) {
      String temp = input.next();
	
      if ( temp.equals( "quit" ) ) System.exit(0);
      if ( temp.matches( "[0-9]+" ) ) L.insert(temp);
      if ( temp.equals( "exist" ) ) System.out.println( L.exist( input.nextLine() ) );
      if ( temp.equals( "remove" ) ) L.remove( input.nextLine() );
      
      L.print();
    }
  }
}
