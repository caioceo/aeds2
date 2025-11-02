Todos casos = O(n log(n))
Complexidade espacial = O(n);

Metodo mergeSort que define um meio e chama recursivamente esquerda até meio e meio+1 até direita;
Apos a chamada recursiva dos dois lados chama-se intercalar();
Intercalar cria dois subvetores [meio - esq + 1] e [dir - meio] e os popula com os elemento do vetor original;
Criar três variaveis de controle, para o sub vetor um, sub vetor 2 e o array original;
Um while que para quando a variavel de controle de um dos subvetor passar seu tamanho;
Dentro do while existe uma condição que verifica qual elemento dos subvetores é menor e o insere no vetor original;
Fora do while um if e else para ver qual subvetor ja foi totalmente utilizado e voltar a preencher o vetor original;

