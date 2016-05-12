package t3jb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
 
public class percentTree{
    
    public static GenTree T;

    public static ArrayList<String> listaPossiveisRaizes = new ArrayList();
    public static ArrayList<String> listaNaoRaizes = new ArrayList();
    
  public static void main(String args[]) throws Exception{
      PrintWriter writer;
      FileReader fileRead = new FileReader("teste.txt");
      BufferedReader lerArq = new BufferedReader(fileRead);
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      
      while(lineBeingRead != null){
        String [] twoPointsSplit = lineBeingRead.split(":");
        
        // Retira espaços
        twoPointsSplit[0] = twoPointsSplit[0].substring(0,twoPointsSplit.length-1);
        twoPointsSplit[1] = twoPointsSplit[0].substring(1,twoPointsSplit.length);
        
        listaPossiveisRaizes.add(twoPointsSplit[0]);
        
        String [] espaceSplit = twoPointsSplit[1].split(" ");
        
        for(int i=0; i<= espaceSplit.length; i++){
            if(!(espaceSplit[i].matches("[0-9]+"))){
                if(listaPossiveisRaizes.contains(espaceSplit[i]))
                    listaPossiveisRaizes.remove(espaceSplit[i]);
                listaNaoRaizes.add(espaceSplit[i]);
            }
                
        }
        
      }
      
      for(String iPossiveisRaizes : listaPossiveisRaizes){
          for(String iNaoRaizes : listaNaoRaizes){
              if(iNaoRaizes.equals(iPossiveisRaizes)) listaPossiveisRaizes.remove(iPossiveisRaizes);
          }
      }
      
      String raiz = listaPossiveisRaizes.get(0);
      
      T = new GenTree (raiz, 100);
      
      
      
      
     
 
  }
  
  private static void buildStructure(TreeNode append) throws IOException{
      FileReader fileRead = new FileReader("teste.txt");
      BufferedReader lerArq = new BufferedReader(fileRead);
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      
      if(lineBeingRead.contains(append)){
          String [] twoPointsSplit = lineBeingRead.split(":");
          String [] spaceSplit = twoPointsSplit[1].substring(1,twoPointsSplit.length).split(" ");
          for(int i=0; i<=spaceSplit.length; i++){
              append
          }
      }
      
      
  }
 
}