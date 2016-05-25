package t3jb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
 
public class percentTree{
    
    private static GenTree T;
    
    /*
        HashMap que possui o seguinte formato: nomeRaiz -> árvore;
    */
    private static final Map <String, GenTree> listaRefs = new HashMap<>();
    
  public static void main(String args[]) throws Exception{
      
      long startTime = System.currentTimeMillis();
      FileReader fileRead = new FileReader("casos/caso215");
      BufferedReader lerArq = new BufferedReader(fileRead);
      
      String lineBeingRead = lerArq.readLine(); // lê a primeira linha
      int cont = 1;
      
      // Começa o laço que irá ler o arquivo
      
      while(lineBeingRead != null){
          
        // Imprime a linha (a fim de debugar)
        if(cont%1000 == 0) System.out.println("Linha: "+cont);
        
        String [] twoPointsSplit = lineBeingRead.split("(\\s)*:(\\s)*");
        
            /*
                Primeiro é necessário criar uma árvore colocando o nodo raiz
                desta subárvore como raiz, e adicionar seus filhos a esta
                árvore recém criada
            */
            String [] espaceSplit = twoPointsSplit[1].split(" ");
            
            // Cria a árvore
            T = new GenTree(twoPointsSplit[0],0);
            
            for(int i=0; i<= espaceSplit.length-1; i+=2){
                    
                    /*
                        Se o nodo que queremos pindurar nesta árvore recém criada está
                        na lista de raizes, isso que dizer que ele é a raiz de uma árvore
                        já existente, e que certamente possuirá uma estrutura com PELO MENOS
                        1 nivel de filhos.
                    */
                    if(listaRefs.get(espaceSplit[i])!=null){
                        
                        /*
                            Neste caso, pegaremos a árvore que possui este nodo como raiz
                        */
                        GenTree arvore = listaRefs.get(espaceSplit[i]);
                                
                                /*
                                    Agora, setamos o valor dele para o valor definido
                                    na linha (VIDE OBS1) e pindura esta árvore na nossa
                                    árvore recém criada
                                */
                                arvore.setValue(espaceSplit[i+1]);
                                T.appendTree(arvore);
                                
                                /*
                                    Como esta árvore foi pindurada na nova, o cara que era raiz dela
                                    deixou de ser raiz e virou filho da nova subárvore, logo, deve ser
                                    removido da lista de raizes. Esta árvore que foi pindurada deixou
                                    de ser uma árvore única e virou uma subárvore da recém criada, logo,
                                    deve ser removida da lista de referencias.
                                */
                                listaRefs.remove(espaceSplit[i]);
                    }else{
                        /*
                            Caso o filho analisado não seja raiz de uma árvore já existente,
                            insere ele com seu valor, como filho do nodo raiz desta nossa subárvore,
                            em outras palavras, continua montando a subárvore.
                        */
                        T.insert(twoPointsSplit[0], espaceSplit[i], Integer.parseInt(espaceSplit[i+1]));
                    }
            }
            // Adiciona a árvore criada no HashMap. A chave de acesso é o nome da raiz
            listaRefs.put(twoPointsSplit[0], T);
        
        lineBeingRead = lerArq.readLine(); // Lê a próxima linha
        cont++;
      }
      
      /*
        No final da construção acima, a nossa lista de referências deverá resultar com
        somente UMA árvore dentro, que será a árvore final montada de maneira correta
        a partir das informações do arquivo. Então pegamos ela e atribuimos ao nome T
      */
      for (GenTree tree: listaRefs.values()) {
        T = tree;
      }
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
      
      //T.printDot();
      T.arrumaValores();
      //T.printDot();
      
      /*
        Com tudo pronto, podemos imprimir quem é a raiz de cada caso teste e o nodo com maior
        probabilidade de ser criado.
      */
      
      long endTime = System.currentTimeMillis();
      System.out.println("Nodo Raiz: " + T.getRootName(T));
      System.out.println("Nodo com maior probabilidade de ocorrer- " + T.getBigProbability());
      String bigProb = T.getBigProbability();
      String result = bigProb.substring(bigProb.indexOf(" "), bigProb.indexOf(" Valor")).trim();
      System.out.println("Caminho até o nodo com maior probabilidade " + T.stepUntilNode(result));
      System.out.println("Tempo de execução: " + (endTime - startTime)/1000.0 + " segundos");
      
      /* OBS1:
        As linhas do arquivo teste possuem o seguinte formato:
        nomePai : nomeFilho1 valorFilho1 nomeFilho2 valorFilho2 ...
        Portanto, no momento no qual criamos uma subárvore, o pai, que será a raiz dessa subárvore
        não possui valor a ser atribuido. Então, por uma questão de escolha seu valor será setado como 0
        A questão é, se este nodo pai não for a raiz da árvore final, ele terá que ser pindurado em uma
        outra árvore em algum momento. Então no momento que pararmos em uma linha que diz que este nodo
        pai é filho da árvore desta linha, automaticamente o valor deste nodo pai (no momento 0) estará
        escrito logo após o seu nome. Agora basta, no momento que pindurarmos este pai junto com a sua
        estrutura na nova árvore, setarmos o seu valor para o indicado na linha.
      
        Ao final do processo inteiro, todos os nodos DEVEM possuir um valor positivo e diferente de 0,
        menos um: a raiz da árvore final. Como ela não é filho de ninguém, não possui nenhuma linha que
        referencia ela com o seu valor jutamente. Porém, por definição do trabalho, a raiz da árvore deve
        possuir valor 100, então basta na hora de arrumar os valores de árvore, setarmos antes de tudo o
        valor da raiz para 100, assim, todos os nodos terão valores positivos não nulos.
      */
  }
 
}