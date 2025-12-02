    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>

    typedef struct{
        char nome[25];
        char cor[25];
        char tam;
    }Pessoa;

    int menorQue(Pessoa a, Pessoa b) {
        int c = strcmp(a.cor, b.cor);
        if (c != 0) return c < 0;

        if (a.tam != b.tam) return a.tam > b.tam;

        return strcmp(a.nome, b.nome) < 0;
    }

    void SelectionSort(Pessoa a[], int n){
        for (int i = 0; i < n-1; i++)
        {
            int menor = i;
            for (int j = i+1; j < n; j++)
            {
                if(menorQue(a[j], a[menor])){
                    menor = j;
                }
            }
            Pessoa tmp = a[i];
            a[i] = a[menor];
            a[menor] = tmp;
        }
        
    }

    faz um selection sort qualquer

    condição para troca de menor vai ser a função menorQue

    int main() {
        while(1){
            int n;
            scanf("%d",&n);
            if(n>60 || n<1){
                return 0;
            }
            getchar();
            Pessoa a[n];
            for (int i = 0; i < n; i++){
                char nome[25];
                char corTam[25];

                fgets(nome,25,stdin);
                nome[strcspn(nome, "\n")] = '\0';
                fgets(corTam,25,stdin);
                corTam[strcspn(corTam, "\n")] = '\0';

                strcpy(a[i].nome, nome);

                int tamIndex = strcspn(corTam, "PMG" );

                a[i].tam = corTam[tamIndex];

                for(int j=0; j<tamIndex; j++){
                    a[i].cor[j] = corTam[j];
                }

                a[i].cor[tamIndex] = '\0';
            }
            // ordenar por cor, depois por tam, depois por nome

            SelectionSort(a, n);

            for(int i = 0; i<n; i++){
                printf("%s %c %s\n", a[i].cor, a[i].tam, a[i].nome);
            }
            printf("\n");

        }
        return 0;
    }
    