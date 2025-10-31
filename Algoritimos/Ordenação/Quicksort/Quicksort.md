Conquistar e Dividir
Caso médio e melhor caso = O(n log(n))
Pior caso = O(n²)

Metodo partição sempre tem o pivo a direita, ordena o array atual e retorna a posiçã correta do pivo;
Esse pivo retornado sera correspondente a esq e dir (pivo+1, pivot-1) para as proximas chamadas de quicksort;
Condição de existencia do metodo quicksort = esq < dir;
