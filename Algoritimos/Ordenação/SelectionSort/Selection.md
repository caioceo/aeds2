Força bruta
Todos os casos  =  O(n²) 

O for de fora representa a posição em ordenação (i) e para quando i < len -1 (penultimo indice);
Antes do for de dentro fazemos int menor = i;
O for de dentro tem condição de parada quando j < len, ou seja, valor maximo = len-1 (ultimo indice);
O segundo for tem condição de que se arr[j] < arr[menor] -> menor = j;
Menor vai atualizando e j vai incrementando com o for;