#include <stdio.h>
#include <string.h>

int main() {
    int n, m;
    char linha[10];
    
    while(scanf("%s", linha) != EOF) {
        if(strcmp(linha, "FIM") == 0) {
            break;
        }
        
        n = atoi(linha);
        scanf("%d", &m);
        
        int tabuleiro[n][m];
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                scanf("%d", &tabuleiro[i][j]);
            }
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(tabuleiro[i][j] == 1) {
                    printf("9");
                } else {
                    int contador = 0;
                    
                    if(i > 0 && tabuleiro[i-1][j] == 1) {
                        contador++;
                    }
                    if(i < n-1 && tabuleiro[i+1][j] == 1) {
                        contador++;
                    }
                    if(j > 0 && tabuleiro[i][j-1] == 1) {
                        contador++;
                    }
                    if(j < m-1 && tabuleiro[i][j+1] == 1) {
                        contador++;
                    }
                    
                    printf("%d", contador);
                }
            }
            printf("\n");
        }
    }
    
    return 0;
}