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
  }

  private static class Node {
    TreeNode child;
    Node next;

    Node( TreeNode n )    { child = n; next = null; }
  }

  private Node append( Node n, TreeNode tnode ) {
    if ( n == null ) return new Node( tnode );
    n.next = append( n.next, tnode );
    return n;
  }
  
  public TreeNode getRoot(GenTree t){
      return t.root;
  }
  
  public String getRootName(GenTree T){
      return T.root.name;
  }
  
  public void setValue(TreeNode n, int value){
      n.data = value;
  }
  
  public Node getChildList(GenTree t){
      return t.root.children;
  }
  
  public boolean isRoot(GenTree t, String raiz){
      return t.root.name.equals(raiz);
  }
  
  public void appendThree(TreeNode n, Node listaFilhos){
      if(listaFilhos==null){
          listaFilhos = new Node(n);
          this.root.children = listaFilhos;
          return;
      }
      while(listaFilhos.next != null) listaFilhos = listaFilhos.next;
      listaFilhos.next = new Node (n);
      this.root.children = listaFilhos;
  }

  public GenTree( String s, int n ) { root = new TreeNode( s, n ); }

  private TreeNode find( TreeNode n, String val ) {
    if ( n == null ) return null;
    if ( n.name.equals(val) ) return n;
    Node f = n.children;
    while ( f != null ) {
      TreeNode ax = find( f.child, val );
      if ( ax != null ) return ax;
      f = f.next;
    }
    return null;
  }

  public void insert( String p, String s, int f) {
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
	Node f = n.children;
    while ( f != null ) {
      printH(f.child,tabs+1);
      f = f.next;
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
     Node f = n.children;
     while(f != null){
        System.out.println(n.name + " -> " + f.child.name);
        printDot(f.child);
        f = f.next;
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
	Node f=n.children;
	while(f!=null)
	{
		boolean val = exist(f.child,name);
		if(val == true) res = true;	
		f=f.next;		
	}
	return res;	
  }
  
  public String getBigProbability(){
    TreeNode result = getBigProbability(root);
    return result.name + " - " + result.data + "%"; 
  }
  
  private TreeNode getBigProbability(TreeNode n){
    if(n.children == null) return n;
    int percent = 0;
    TreeNode chosen = n;
    Node f = n.children;
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
  
  private void arrumaValores(TreeNode n, int value){
      n.data = (int)(value*((float)n.data/100.0f));
      Node f = n.children;
      while(f != null){
          arrumaValores(f.child,n.data);
          f = f.next;
      }
  }

}

