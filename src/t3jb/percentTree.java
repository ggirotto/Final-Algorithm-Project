package t3jb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
 
public class percentTree{
    
    private static GenTree T;
    
    private static ArrayList<GenTree> listaArvores = new ArrayList<>();
    private static Set<String> listaRaizes = new TreeSet<>();
    private static Set<String> listaFilhos = new TreeSet<>();
    
  public static void main(String args[]) throws Exception{
      PrintWriter writer;
      FileReader fileRead = new FileReader("casos/caso112");
      BufferedReader lerArq = new BufferedReader(fileRead);
      
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      int cont = 1;
      
      // Começa o laço que irá ler o arquivo
      while(lineBeingRead != null){
        if(cont%1000 == 0) System.out.println("Estou lendo a linha: "+cont);
        String [] twoPointsSplit = lineBeingRead.split(":");
        
        // Retira espaços
        twoPointsSplit[0] = twoPointsSplit[0].trim();
        twoPointsSplit[1] = twoPointsSplit[1].trim();
        
        /*
            Assumimos que teremos que criar uma subárvore, então o nodo pai de todos
            é adicionado na lista de raizes. (não significa que teremos uma subárvore,
            isso descobriremos no if abaixo.
        */
        
        listaRaizes.add(twoPointsSplit[0]);
        
        /*
            Se o nodo raiz dessa subárvore já é filho de uma outra árvore, significa
            que não teremos que criar uma subárvore, e sim pindurar estes filhos com seus
            respectivos valores embaixo deste cara que já existe em alguma outra árvore
        */
        if(listaFilhos.contains(twoPointsSplit[0])){
            String [] espaceSplit = twoPointsSplit[1].split(" ");
            
            // Percorre a lista de árvores procurando pela árvore que tenha este cara como elemento
            for(GenTree arvore : listaArvores){
                if(arvore.exist(twoPointsSplit[0])){
                    
                    /* 
                        Para cada filho da linha sendo lida, adicionar ele e seu valor como filho
                        do nodo encontrado nesta árvore.
                    */
                    for(int i=0; i<= espaceSplit.length-1; i++){
                        if(!(espaceSplit[i].matches("[0-9]+"))){
                           arvore.insert(twoPointsSplit[0], espaceSplit[i], Integer.parseInt(espaceSplit[i+1]));
                        }
                    }
                    
                    /*
                        Assim, descobrimos que ele não é uma nova subárvore, pois ele já existe
                        Como tinhamos assumido que twoPointsSplit[0] ia ser a raiz de uma subárvore
                        e ja o adicionamos de cara na lista de raizes, agora devemos remover ele
                        desta lisa, e adicioná-lo na lista de filhos
                    */
                    listaRaizes.remove(twoPointsSplit[0]);
                    listaFilhos.add(twoPointsSplit[0]);
                }
            }
        }else{
            /*
                O nodo raiz desta subárvore não é filho de ninguém, portanto teremos
                que criar uma árvore colocando-o como raiz, e adicionar seus filhos
                a esta árvore recém criada
            */
            String [] espaceSplit = twoPointsSplit[1].split(" ");
            
            // Cria a árvore
            T = new GenTree(twoPointsSplit[0],0);
            
            for(int i=0; i<= espaceSplit.length-1; i++){
                if(!(espaceSplit[i].matches("[0-9]+"))){
                    
                    /*
                        Se o nodo que queremos pindurar nesta árvore recém criada está
                        na lista de raizes, isso que dizer que ele é a raiz de uma árvore
                        já existente, e que certamente possuirá uma estrutura com PELO MENOS
                        1 nivel de filhos.
                    */
                    if(listaRaizes.contains(espaceSplit[i])){
                        
                        /*
                            Neste caso, iremos percorrer a lista de árvores procurando
                            pela árvore que possua este cara como raiz.
                        */
                        for(GenTree arvore : listaArvores){
                            if(arvore.isRoot(arvore,espaceSplit[i])){
                                
                                /*
                                    Assim que achar a árvore cujo o cara é a raiz, seta o valor
                                    dele para o valor definido na linha (VIDE OBS1) e pindura
                                    esta árvore na nossa árvore recém criada
                                */
                                arvore.setValue(arvore.getRoot(arvore), Integer.parseInt(espaceSplit[i+1]));
                                T.appendThree(arvore.getRoot(arvore),T.getChildList(T));
                                
                                /*
                                    Como esta árvore foi pindurada na nova, o cara que era raiz dela
                                    deixou de ser raiz e virou filho da nova subárvore (logo deve ser
                                    removido da lista de raizes e adicionado na lista de filhos) esta
                                    árvore que foi pindurada deixou de ser uma árvore única e virou uma
                                    subárvore da recém criada (logo deve ser removida da lista de árvores)
                                */
                                listaRaizes.remove(espaceSplit[i]);
                                listaArvores.remove(arvore);
                                listaFilhos.add(espaceSplit[i]);
                                
                                // Sai do for(?)
                                break;
                            }
                        }
                        
                        // Continua o for
                        continue;
                    }else{
                        /*
                            Caso o filho analisado não seja raiz de uma árvore já existente,
                            insere ele com seu valor, como filho do nodo raiz desta nossa subárvore,
                            em outras palavras, continua montando a subárvore
                            (e adiciona ele na lista de filhos)
                        */
                        T.insert(twoPointsSplit[0], espaceSplit[i], Integer.parseInt(espaceSplit[i+1]));
                        listaFilhos.add(espaceSplit[i]);
                    }
                    
                }
            }
            // Adiciona a árvore criada na lista de árvores
            listaArvores.add(T);
        }
        
        lineBeingRead = lerArq.readLine();
        cont++;
      }
      
      /*
        No final da construção acima, a nossa lista de árvores deverá resultar com
        somente UMA árvore dentro, que será a árvore final montada de maneira correta
        a partir das informações do arquivo. Então pegamos ela e atribuimos ao nome T
      */
      T = listaArvores.get(0);
      
      /*
        Note que cada nodo da árvore contruída possui como dado, a porcentagem do dado
        do seu pai, ainda não calculada.
        Por exemplo, o nodo x possui dado 50 e seu filho y possui dado 30.
        Nosso dever agora é ajustar estes valores começando da raiz. O método arrumaValores()
        começa definido o valor da raiz como 100 (antes estava setado como 0, vide OBS1)
        e partir dela, cada filho deverá atualizar o seu dado para o valor do seu pai porcentagem
        do seu valor.
        Exemplo, o nodo x possui dado 15 e seu filho y possui dado 30, logo, o nodo y deverá atualziar
        o seu valor para 30% do valor 15.
      */
      T.arrumaValores();
      
      /*
        Com tudo pronto, podemos imprimir quem é a raiz de cada caso teste e o nodo com maior
        probabilidade de ser criado.
      */
      
      System.out.println("Nodo Raiz: " + T.getRootName(T));
      System.out.println("Nodo com maior probabilidade de ocorrer: " + T.getBigProbability());
 
  }
 
}