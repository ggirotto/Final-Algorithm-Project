package t3jb;

public class GenTree {

  private TreeNode root;
  
  private static class TreeNode {
    Node children;
    String name;
    double data;

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
  
  // Retorna o nome do nodo raiz
  public String getRootName(GenTree T){
      return T.root.name;
  }
  
  // Seta um valor para a raiz da árvore
  public void setValue(String value){
      root.data = Float.parseFloat(value);
  }
  
  // Verifica se a string enviada é igual ao nome da raiz
  public boolean isRoot(String raiz){
      return root.name.equals(raiz);
  }
  
  // Pindura uma árvore já existente nessa árvore
  public void appendTree(GenTree n){
      root.children = append(root.children,n.root);
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
  
  // Imprime em formato para passar para o programa que cria árvores
  public void printDot(){
     System.out.println("digraph G {\n\tgraph [layout=dot]");
     printDot(root);
     System.out.println("\n}");
  }
  
  private void printDot(TreeNode n){
     if(n == null) return;
     Node f = n.children;
     while(f != null){
        System.out.println(n.name + "(" + n.data + ")" + " -> " + f.child.name + "(" + f.child.data + ")");
        printDot(f.child);
        f = f.next;
     }
  }
  
  // Imprime nome -> data
  public void printData(){
     printData(root);
  }
  
  private void printData(TreeNode n){
     if(n == null) return;
     Node f = n.children;
     while(f != null){
        System.out.println(n.name + " -> " + n.data);
        printData(f.child);
        f = f.next;
     }
  }
  
  // Resgata a folha com maior probabilidade de acontecer
  public String getBigProbability(){
    TreeNode resultado = getBigProbability(root);
    return "Nome: " + resultado.name + "  Valor: " + resultado.data;
  }
  
  private TreeNode getBigProbability(TreeNode n){
      if(n.children == null) return n;
      Node f = n.children;
      TreeNode save = new TreeNode("compare",0);
      TreeNode leaf;
      while(f!=null){
          leaf = getBigProbability(f.child);
          if(leaf.data > save.data) save = leaf;
          f = f.next;
      }
      return save;
      
  }
  
  // Arruma os valores da árvore
  public void arrumaValores(){
      root.data = 100;
      arrumaValores(root,100);
  }
  
  private void arrumaValores(TreeNode n, double value){
      n.data = (n.data*value)/100;
      Node f = n.children;
      while(f != null){
          arrumaValores(f.child,n.data);
          f = f.next;
      }
  }
  
  public String stepUntilNode(String nodeName){
      return stepUntilNode(root,nodeName," " + root.name);
  }
  
  private String stepUntilNode(TreeNode n, String nodeName, String rode){
      String result = "";
      if(n.name.equals(nodeName)) {
          rode += " -> "+n.name;
          return rode;
      }
      Node f = n.children;
      while(f!=null){
          if(rode.contains(" "+n.name))
            result = stepUntilNode(f.child,nodeName,rode);
          else
            result = stepUntilNode(f.child,nodeName,rode+=" -> "+n.name);
          if(!(result.equals(""))) return result;
          f = f.next;
      }
      return result;
  }

}

