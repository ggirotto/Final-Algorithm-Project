package t3jb;

public class GenTree {

  private TreeNode root;

  private static class TreeNode {
    Node children;
    String name;
    int data;

    TreeNode( String newName, int newData ) {
      data = newData;
      name = newName;
      children = null;
    }

    Node Children( )      { return children; }
  }

  private static class Node {
    TreeNode child;
    Node next;

    Node( TreeNode n )    { child = n; next = null; }
    Node Next( )          { return next; }
    TreeNode Child( )     { return child; }
  }

  private Node append( Node n, TreeNode tnode ) {
    if ( n == null ) return new Node( tnode );
    n.next = append( n.next, tnode );
    return n;
  }

  public GenTree( String s, int n ) { root = new TreeNode( s, n ); }

  private TreeNode find( TreeNode n, int val ) {
    if ( n == null ) return null;
    if ( n.data == val ) return n;
    Node f = n.Children();
    while ( f != null ) {
      TreeNode ax = find( f.Child(), val );
      if ( ax != null ) return ax;
      f = f.Next();
    }
    return null;
  }

  public void insert( int p, String s, int f) {
    TreeNode n = find( root, p );
    if ( n == null ) return;
    n.children = append( n.children, new TreeNode( s, f ) );
  }

  public void printH( ) {
    printH ( root,0 );
	System.out.println();
  }

  private void printH( TreeNode n, int tabs ) {
	if(n==null) return;
	for(int i=1;i<=tabs;i++) System.out.print("  ");
	System.out.println("<" + n.data + ">");
	//System.out.println(n.data);
	Node f = n.Children();
    while ( f != null ) {
      printH(f.Child(),tabs+1);
      f = f.Next();
    }
	for(int i=1;i<=tabs;i++) System.out.print("  ");
	System.out.println("</" + n.data + ">");
	//System.out.print(")");    
  }
  
  public void printDot(){
     System.out.println("digraph G {\n\tgraph [layout=dot]");
     printDot(root);
     System.out.println("\n}");
  }
  
  private void printDot(TreeNode n){
     if(n == null) return;
     Node f = n.Children();
     while(f != null){
        System.out.println(n.data + " -> " + f.Child().data);
        printDot(f.Child());
        f = f.Next();
     }
  }
  
  public void buildStructure(String lineBeingRead){
      buildStructure(lineBeingRead,root);
  }
  
  private void buildStructure(String lineBeingRead, TreeNode append){
      
      if(lineBeingRead.contains(append.name)){
          String [] twoPointsSplit = lineBeingRead.split(":");
          String [] spaceSplit = twoPointsSplit[1].substring(1,twoPointsSplit.length).split(" ");
          for(int i=0; i<=spaceSplit.length; i++){
              TreeNode aux = new TreeNode(spaceSplit[i],Integer.parseInt(spaceSplit[i+1]));
              append(append.children,aux);
              i = i+2;
          }
      }
      
      
  }

}

