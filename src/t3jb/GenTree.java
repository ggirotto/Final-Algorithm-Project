package t3jb;

public class GenTree {

  private TreeNode root;

  private static class TreeNode {
    Node children;
    String name;
    double data;

    TreeNode( String newName, double newData ) {
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
  
  public TreeNode getRoot(GenTree t){
      return t.root;
  }
  
  public void setValue(TreeNode n, double value){
      n.data = value;
  }
  
  public String getName(TreeNode n){
      return n.name;
  }
  
  public Node getChildList(GenTree t){
      return t.root.children;
  }
  
  public boolean isRoot(GenTree t, String raiz){
      if(t.root.name.equals(raiz)) return true;
      return false;
  }
  
  public void appendThree(TreeNode n, Node listaFilhos){
      if(listaFilhos==null){
          listaFilhos = new Node(n);
          return;
      }
      while(listaFilhos.next != null) listaFilhos = listaFilhos.next;
      listaFilhos.next = new Node (n);
  }

  public GenTree( String s, double n ) { root = new TreeNode( s, n ); }

  private TreeNode find( TreeNode n, String val ) {
    if ( n == null ) return null;
    if ( n.name.equals(val) ) return n;
    Node f = n.Children();
    while ( f != null ) {
      TreeNode ax = find( f.Child(), val );
      if ( ax != null ) return ax;
      f = f.Next();
    }
    return null;
  }

  public void insert( String p, String s, double f) {
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
	System.out.println("<" + n.name + ">");
	//System.out.println(n.data);
	Node f = n.Children();
    while ( f != null ) {
      printH(f.Child(),tabs+1);
      f = f.Next();
    }
	for(int i=1;i<=tabs;i++) System.out.print("  ");
	System.out.println("</" + n.name + ">");
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
        System.out.println(n.name + " -> " + f.Child().name);
        printDot(f.Child());
        f = f.Next();
     }
  }
  
  public boolean exist(String name)
  {		
	if(root == null) return false;
	return exist(root,name);
  }

  private boolean exist(TreeNode n,String name)
  {
        boolean res = false;
	if(n.name.equals(name)) res = true;
	Node f=n.Children();
	while(f!=null)
	{
		boolean val = exist(f.Child(),name);
		if(val == true) res = true;	
		f=f.Next();		
	}
	return res;	
  }
  
  public String getBigProbability(){
    TreeNode result = getBigProbability(root);
    return result.name + " - " + result.data + "%"; 
  }
  
  private TreeNode getBigProbability(TreeNode n){
    if(n.children == null) return n;
    double percent = 0;
    TreeNode chosen = n;
    Node f = n.Children();
    while(f!=null){
        TreeNode aux = getBigProbability(f.child);
        if(aux.data > percent){
            percent = aux.data;
            chosen = aux;
        }
        f = f.next;
    }
    return chosen;
  }
  
  public void arrumaValores(){
      root.data = 100;
      Node f = root.children;
      while(f!=null){
          arrumaValores(f.child,100);
          f = f.next;
      }
  }
  
  private void arrumaValores(TreeNode n, double value){
      n.data = (double)(value*((float)n.data/100.0f));
      Node f = n.children;
      while(f != null){
          arrumaValores(f.child,n.data);
          f = f.next;
      }
      return;
  }

}

