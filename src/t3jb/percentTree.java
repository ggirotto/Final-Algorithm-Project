package t3jb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
 
public class percentTree{
    
    private static GenTree T;

    public static List listaRaizes = new List();
    public static List listaFilhos = new List();
    public static ArrayList<GenTree> listaArvores = new ArrayList<>();
    
  public static void main(String args[]) throws Exception{
      PrintWriter writer;
      FileReader fileRead = new FileReader("what.txt");
      BufferedReader lerArq = new BufferedReader(fileRead);
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      
      while(lineBeingRead != null){
        String [] twoPointsSplit = lineBeingRead.split(":");
        
        // Retira espaços
        twoPointsSplit[0] = twoPointsSplit[0].split(" ")[0];
        twoPointsSplit[1] = twoPointsSplit[1].substring(1);
        listaRaizes.insert(twoPointsSplit[0]);
        //listaRaizes.insert(twoPointsSplit[0]);
        // Se o nodo raiz dessa subarvore já é filho de uma outra árvore
        if(listaFilhos.exist(twoPointsSplit[0])){
            String [] espaceSplit = twoPointsSplit[1].split(" ");
            for(GenTree arvore : listaArvores){
                if(arvore.exist(twoPointsSplit[0])){
                    for(int i=0; i<= espaceSplit.length-1; i++){
                        if(!(espaceSplit[i].matches("[0-9]+"))){
                           arvore.insert(twoPointsSplit[0], espaceSplit[i], Integer.parseInt(espaceSplit[i+1]));
                           //arvore.printH();
                        }
                    }
                    listaRaizes.remove(twoPointsSplit[0]);
                    listaFilhos.insert(twoPointsSplit[0]);
                }
            }
            lineBeingRead = lerArq.readLine();
            break;
        }else{
            String [] espaceSplit = twoPointsSplit[1].split(" ");
            T = new GenTree(twoPointsSplit[0],0);
            for(int i=0; i<= espaceSplit.length-1; i++){
                if(!(espaceSplit[i].matches("[0-9]+"))){
                    if(listaRaizes.exist(espaceSplit[i])){
                        for(GenTree arvore : listaArvores){
                            if(arvore.isRoot(arvore,espaceSplit[i])){
                                arvore.setValue(arvore.getRoot(arvore), Integer.parseInt(espaceSplit[i+1]));
                                T.appendThree(arvore.getRoot(arvore),T.getChildList(T));
                                listaRaizes.remove(espaceSplit[i]);
                                listaArvores.remove(arvore);
                                listaFilhos.insert(espaceSplit[i]);
                                break;
                            }
                        }
                        continue;
                    }
                    listaFilhos.insert(espaceSplit[i]);
                    T.insert(twoPointsSplit[0], espaceSplit[i], Integer.parseInt(espaceSplit[i+1]));
                }
            }
            //T.printH();
            listaArvores.add(T);
        }
        
        lineBeingRead = lerArq.readLine();
      }
      
      T = listaArvores.get(0);
      
      T.arrumaValores();
      
      //buildStructure(T.getName(T.getRoot(T)),100);
      
      //T.printDot();
      
      //System.out.println("Nodo Raiz: " + );
      System.out.println("Nodo com maior probabilidade de ocorrer: " + T.getBigProbability());
 
  }
 
}