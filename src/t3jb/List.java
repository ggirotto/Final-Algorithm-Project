package t3jb;
public class List{

	// ReferÃªncia para  o primeiro elemento
  private Node first;
  private int size;

  /*
   Node
   A lista usa esta classe interna. Cada nodo contÃ©m
   uma informaÃ§Ã£o e referÃªncia para o prÃ³ximo nodo.
  */
  private static class Node {
    Node next;
    String data;

    Node( String newData ) {
      data = newData;
      next = null;
    }

  }

  public List() {
    first = null;
  }

  public void insert( String data ) {
    Node p = new Node( data );
    p.next = first;
    first = p;
    size++;
  }

public void remove(String word){
      if(first == null) return;
      if(first.data.equals(word)){
          first = first.next;
          size--;
          return;
      }
      Node p = first;
      while(p.next != null){
          if(p.next.data.equals(word)){
              p.next = p.next.next;
              size--;
              return;
          }
          p = p.next;
      }

  }

public String get(int pos){
	return get(pos, first);
}

private String get(int pos, Node p){
	if(p == null){
		System.out.println("Posição inexistente");
		System.exit(1);
	}
	if(pos==0){
		String val = p.data;
		return val;
	}
	if(pos==1){
		String val = p.next.data;
		return val;
	}
	return get(pos-1,p.next);
}

public int getSize(){ return size; }

public boolean exist(String val){
	return exist(val, first);
}

private boolean exist(String val, Node p){
	if(p == null) return false;
	if(p.data.equals(val)) return true;
	return exist(val, p.next);
}

 public void print(){
      System.out.print("[ ");
      print( first );
      System.out.println("]");
  }

  private void print(Node aux){
      if( aux == null ) return;
      System.out.print( aux.data + " ");
      print( aux.next );
  }

}
