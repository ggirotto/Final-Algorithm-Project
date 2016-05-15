package t3jb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
 
public class percentTree{
    
    private static GenTree T;

    public static List listaPossiveisRaizes = new List();
    public static List listaNaoRaizes = new List();
    
  public static void main(String args[]) throws Exception{
      PrintWriter writer;
      FileReader fileRead = new FileReader("casos/caso110");
      BufferedReader lerArq = new BufferedReader(fileRead);
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      
      while(lineBeingRead != null){
        String [] twoPointsSplit = lineBeingRead.split(":");
        
        // Retira espaços
        twoPointsSplit[0] = twoPointsSplit[0].split(" ")[0];
        twoPointsSplit[1] = twoPointsSplit[1].substring(1);
        
        listaPossiveisRaizes.insert(twoPointsSplit[0]);
        
        String [] espaceSplit = twoPointsSplit[1].split(" ");
        
        for(int i=0; i<= espaceSplit.length-1; i++){
            if(!(espaceSplit[i].matches("[0-9]+"))){
                if(listaPossiveisRaizes.exist(espaceSplit[i]))
                    listaPossiveisRaizes.remove(espaceSplit[i]);
                listaNaoRaizes.insert(espaceSplit[i]);
            }
                
        }
        
        lineBeingRead = lerArq.readLine();
      }
      
      for(int i=0; i<listaPossiveisRaizes.getSize(); i++){
        if(listaPossiveisRaizes.getSize() == 1) break;
        String iPossiveisRaizes = listaPossiveisRaizes.get(i);
        for(int j=0; j<listaNaoRaizes.getSize(); j++){
            String iNaoRaizes = listaNaoRaizes.get(j);
            if(iNaoRaizes.equals(iPossiveisRaizes)){
                listaPossiveisRaizes.remove(iNaoRaizes);
                i = i-1;
                break;
            }
        }
      }
      
      String raiz = listaPossiveisRaizes.get(0);
      
      T = new GenTree (raiz, 100);
      
      
      buildStructure(raiz,100);
      
      //T.printDot();
      
      System.out.println("Nodo Raiz: " + raiz);
      System.out.println("Nodo com maior probabilidade de ocorrer: " + T.getBigProbability());
 
  }
  
  private static void buildStructure(String append,int percent) throws IOException{
      FileReader fileRead = new FileReader("casos/caso110");
      BufferedReader lerArq = new BufferedReader(fileRead);
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      if(lineBeingRead == null) return;
      while(lineBeingRead != null){
        String [] twoPointsSplit = lineBeingRead.split(":");
        if(twoPointsSplit[0].substring(0,twoPointsSplit[0].indexOf(" ")).equals(append)){
            String [] spaceSplit = twoPointsSplit[1].substring(1).split(" ");
            for(int i=0; i<spaceSplit.length;){
                String pai = twoPointsSplit[0].substring(0,twoPointsSplit[0].indexOf(" "));
                String nome = spaceSplit[i];
                int valor = Integer.parseInt(spaceSplit[i+1]);
                int k = (int)(percent*((float)valor/100.0f));
                T.insert(pai,nome,k);
                i = i+2;
            }
            for(int i=0; i<spaceSplit.length;){
                buildStructure(spaceSplit[i],(int)(percent*((float)Integer.parseInt(spaceSplit[i+1])/100.0f)));
                i = i+2;
            }
            return;
        }
        
        lineBeingRead = lerArq.readLine();
      }
  }
 
}