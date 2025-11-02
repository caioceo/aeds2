#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define TAM_MAX_CAMPO 500
#define TAM_MAX_ARRAY 100
#define TAM_MAX_LINHA 2000

typedef struct {
    int id;
    char nome[200];
    char dataLancamento[20];
    int proprietariosEstimados;
    float preco;
    char idiomasSuportados[TAM_MAX_ARRAY][100];
    int numIdiomas;
    int notaMetacritic;
    float notaUsuario;
    int conquistas;
    char publicadoras[TAM_MAX_ARRAY][100];
    int numPublicadoras;
    char desenvolvedoras[TAM_MAX_ARRAY][100];
    int numDesenvolvedoras;
    char categorias[TAM_MAX_ARRAY][100];
    int numCategorias;
    char generos[TAM_MAX_ARRAY][100];
    int numGeneros;
    char tags[TAM_MAX_ARRAY][100];
    int numTags;
} Game;


char* removerEspacos(char* str) {
    while(isspace((unsigned char)*str)) str++;
    if(*str == 0) return str;
    char* fim = str + strlen(str) - 1;
    while(fim > str && isspace((unsigned char)*fim)) fim--;
    fim[1] = '\0';
    return str;
}

void formatarData(char* entrada, char* saida) {
    char mes[20];
    int dia, ano;
    
    
    if (sscanf(entrada, "%s %d, %d", mes, &dia, &ano) == 3) {
        const char* meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int numeroMes = 0;
        for (int i = 0; i < 12; i++) {
            if (strncmp(mes, meses[i], 3) == 0) {
                numeroMes = i + 1;
                break;
            }
        }
        sprintf(saida, "%02d/%02d/%d", dia, numeroMes, ano);
    }
    
    else if (sscanf(entrada, "%s %d", mes, &ano) == 2) {
        const char* meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int numeroMes = 0;
        for (int i = 0; i < 12; i++) {
            if (strncmp(mes, meses[i], 3) == 0) {
                numeroMes = i + 1;
                break;
            }
        }
        sprintf(saida, "01/%02d/%d", numeroMes, ano);
    }
    // Tenta formato: "yyyy"
    else if (sscanf(entrada, "%d", &ano) == 1 && ano > 1900 && ano < 2100) {
        sprintf(saida, "01/01/%d", ano);
    }
    // Padrão
    else {
        strcpy(saida, "01/01/1900");
    }
}

void parseArray(char* campo, char arr[][100], int* contador) {
    *contador = 0;
    char* inicio = strchr(campo, '[');
    char* fim = strrchr(campo, ']');
    
    if (!inicio || !fim || inicio >= fim) return;
    
    inicio++;
    *fim = '\0';
    
    char temp[TAM_MAX_CAMPO];
    strcpy(temp, inicio);
    
    char* token = strtok(temp, ",");
    while (token && *contador < TAM_MAX_ARRAY) {
        token = removerEspacos(token);
        if (*token == '\'' || *token == '"') token++;
        int tamanho = strlen(token);
        if (tamanho > 0 && (token[tamanho-1] == '\'' || token[tamanho-1] == '"')) {
            token[tamanho-1] = '\0';
        }
        if (strlen(token) > 0) {
            strcpy(arr[*contador], token);
            (*contador)++;
        }
        token = strtok(NULL, ",");
    }
}

void parseListaSimples(char* campo, char arr[][100], int* contador) {
    *contador = 0;
    char temp[TAM_MAX_CAMPO];
    strcpy(temp, campo);
    
    char* token = strtok(temp, ",");
    while (token && *contador < TAM_MAX_ARRAY) {
        token = removerEspacos(token);
        if (strlen(token) > 0) {
            strcpy(arr[*contador], token);
            (*contador)++;
        }
        token = strtok(NULL, ",");
    }
}

void parseLinhaCSV(char* linha, char campos[][TAM_MAX_CAMPO], int* contadorCampos) {
    *contadorCampos = 0;
    bool dentroAspas = false;
    int posicaoCampo = 0;
    
    for (int i = 0; linha[i] != '\0' && *contadorCampos < 20; i++) {
        if (linha[i] == '"') {
            dentroAspas = !dentroAspas;
        } else if (linha[i] == ',' && !dentroAspas) {
            campos[*contadorCampos][posicaoCampo] = '\0';
            (*contadorCampos)++;
            posicaoCampo = 0;
        } else {
            campos[*contadorCampos][posicaoCampo++] = linha[i];
        }
    }
    campos[*contadorCampos][posicaoCampo] = '\0';
    (*contadorCampos)++;
}

Game* criarGame() {
    Game* jogo = (Game*)malloc(sizeof(Game));
    jogo->id = 0;
    strcpy(jogo->nome, "");
    strcpy(jogo->dataLancamento, "01/01/1900");
    jogo->proprietariosEstimados = 0;
    jogo->preco = 0.0;
    jogo->numIdiomas = 0;
    jogo->notaMetacritic = -1;
    jogo->notaUsuario = -1.0;
    jogo->conquistas = 0;
    jogo->numPublicadoras = 0;
    jogo->numDesenvolvedoras = 0;
    jogo->numCategorias = 0;
    jogo->numGeneros = 0;
    jogo->numTags = 0;
    return jogo;
}

Game* parseGame(char* linha) {
    char campos[20][TAM_MAX_CAMPO];
    int contadorCampos;
    parseLinhaCSV(linha, campos, &contadorCampos);
    
    if (contadorCampos < 14) return NULL;
    
    Game* jogo = criarGame();
    jogo->id = atoi(removerEspacos(campos[0]));
    strcpy(jogo->nome, removerEspacos(campos[1]));
    
    char dataTemp[100];
    strcpy(dataTemp, removerEspacos(campos[2]));
    formatarData(dataTemp, jogo->dataLancamento);
    
    jogo->proprietariosEstimados = atoi(removerEspacos(campos[3]));
    jogo->preco = atof(removerEspacos(campos[4]));
    
    parseArray(campos[5], jogo->idiomasSuportados, &jogo->numIdiomas);
    jogo->notaMetacritic = atoi(removerEspacos(campos[6]));
    
    char* notaUsuarioStr = removerEspacos(campos[7]);
    if (strlen(notaUsuarioStr) == 0 || strstr(notaUsuarioStr, "tbd") != NULL) {
        jogo->notaUsuario = -1.0;
    } else {
        jogo->notaUsuario = atof(notaUsuarioStr);
    }
    
    jogo->conquistas = atoi(removerEspacos(campos[8]));
    parseListaSimples(campos[9], jogo->publicadoras, &jogo->numPublicadoras);
    parseListaSimples(campos[10], jogo->desenvolvedoras, &jogo->numDesenvolvedoras);
    parseListaSimples(campos[11], jogo->categorias, &jogo->numCategorias);
    parseListaSimples(campos[12], jogo->generos, &jogo->numGeneros);
    parseListaSimples(campos[13], jogo->tags, &jogo->numTags);
    
    return jogo;
}

void imprimirArray(char arr[][100], int contador) {
    printf("[");
    for (int i = 0; i < contador; i++) {
        printf("%s", arr[i]);
        if (i < contador - 1) printf(", ");
    }
    printf("]");
}

void imprimirGame(Game* jogo) {
    if(jogo->preco == 0){
        printf("=> %d ## %s ## %s ## %d ## 0.0 ## ",
               jogo->id, jogo->nome, jogo->dataLancamento,
               jogo->proprietariosEstimados, jogo->preco);
    }
    else{
        printf("=> %d ## %s ## %s ## %d ## %.2f ## ",
               jogo->id, jogo->nome, jogo->dataLancamento,
               jogo->proprietariosEstimados, jogo->preco);

    }
    imprimirArray(jogo->idiomasSuportados, jogo->numIdiomas);
        printf(" ## %d ## %.1f ## %d ## ",
           jogo->notaMetacritic, jogo->notaUsuario, jogo->conquistas);
    
  imprimirArray(jogo->publicadoras, jogo->numPublicadoras);
    printf(" ## ");
    imprimirArray(jogo->desenvolvedoras, jogo->numDesenvolvedoras);
    printf(" ## ");
    imprimirArray(jogo->categorias, jogo->numCategorias);
    printf(" ## ");
    imprimirArray(jogo->generos, jogo->numGeneros);
    printf(" ## ");
    imprimirArray(jogo->tags, jogo->numTags);
    printf(" ##\n");
}

typedef struct Celula {
    Game* elemento;
    struct Celula* prox;
} Celula;

typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tamanho;
} Lista;

Celula* novaCelula(Game* elemento) {
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}

Lista* novaLista() {
    Lista* lista = (Lista*)malloc(sizeof(Lista));
    lista->primeiro = novaCelula(NULL);
    lista->ultimo = lista->primeiro;
    lista->tamanho = 0;
    return lista;
}

void inserirInicio(Lista* lista, Game* jogo) {
    Celula* tmp = novaCelula(jogo);
    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;
    if (lista->primeiro == lista->ultimo) {
        lista->ultimo = tmp;
    }
    lista->tamanho++;
}

void inserir(Lista* lista, Game* jogo, int posicao) {
    if (posicao < 0 || posicao > lista->tamanho) {
        return;
    }
    
    if (posicao == 0) {
        inserirInicio(lista, jogo);
    } else if (posicao == lista->tamanho) {
        lista->ultimo->prox = novaCelula(jogo);
        lista->ultimo = lista->ultimo->prox;
        lista->tamanho++;
    } else {
        Celula* i = lista->primeiro;
        for (int j = 0; j < posicao; j++, i = i->prox);
        
        Celula* tmp = novaCelula(jogo);
        tmp->prox = i->prox;
        i->prox = tmp;
        lista->tamanho++;
    }
}

void inserirFim(Lista* lista, Game* jogo) {
    lista->ultimo->prox = novaCelula(jogo);
    lista->ultimo = lista->ultimo->prox;
    lista->tamanho++;
}

Game* removerInicio(Lista* lista) {
    if (lista->primeiro == lista->ultimo) {
        return NULL;
    }
    
    Celula* tmp = lista->primeiro->prox;
    Game* resposta = tmp->elemento;
    lista->primeiro->prox = tmp->prox;
    
    if (tmp == lista->ultimo) {
        lista->ultimo = lista->primeiro;
    }
    
    free(tmp);
    lista->tamanho--;
    return resposta;
}

Game* remover(Lista* lista, int posicao) {
    if (lista->primeiro == lista->ultimo || posicao < 0 || posicao >= lista->tamanho) {
        return NULL;
    }
    
    Celula* i = lista->primeiro;
    for (int j = 0; j < posicao; j++, i = i->prox);
    
    Celula* tmp = i->prox;
    Game* resposta = tmp->elemento;
    i->prox = tmp->prox;
    
    if (tmp == lista->ultimo) {
        lista->ultimo = i;
    }
    
    free(tmp);
    lista->tamanho--;
    return resposta;
}

Game* removerFim(Lista* lista) {
    if (lista->primeiro == lista->ultimo) {
        return NULL;
    }
    
    Celula* i;
    for (i = lista->primeiro; i->prox != lista->ultimo; i = i->prox);
    
    Game* resposta = lista->ultimo->elemento;
    free(lista->ultimo);
    i->prox = NULL;
    lista->ultimo = i;
    lista->tamanho--;
    return resposta;
}

void mostrar(Lista* lista) {
    int posicao = 0;
    for (Celula* i = lista->primeiro->prox; i != NULL; i = i->prox) {
        printf("[%d] ", posicao++);
        imprimirGame(i->elemento);
    }
}

int main() {
    char linha[TAM_MAX_LINHA];
    Game* todosJogos[50000];
    int numJogos = 0;
    
    FILE* arquivo = fopen("/tmp/games.csv", "r");
    if (!arquivo) {
        return 1;
    }
    
    fgets(linha, TAM_MAX_LINHA, arquivo);
    
    while (fgets(linha, TAM_MAX_LINHA, arquivo)) {
        Game* jogo = parseGame(linha);
        if (jogo) {
            todosJogos[numJogos++] = jogo;
        }
    }
    fclose(arquivo);
    
    Lista* lista = novaLista();
    
    while (fgets(linha, TAM_MAX_LINHA, stdin)) {
        char* limpo = removerEspacos(linha);
        if (strcmp(limpo, "FIM") == 0) break;
        
        int id = atoi(limpo);
        for (int i = 0; i < numJogos; i++) {
            if (todosJogos[i]->id == id) {
                inserirFim(lista, todosJogos[i]);
                break;
            }
        }
    }
    
    int numOperacoes;
    scanf("%d", &numOperacoes);
    getchar();
    
    for (int i = 0; i < numOperacoes; i++) {
        fgets(linha, TAM_MAX_LINHA, stdin);
        char* comando = removerEspacos(linha);
        
        if (strncmp(comando, "II ", 3) == 0) {
            int id = atoi(comando + 3);
            for (int j = 0; j < numJogos; j++) {
                if (todosJogos[j]->id == id) {
                    inserirInicio(lista, todosJogos[j]);
                    break;
                }
            }
        } else if (strncmp(comando, "I* ", 3) == 0) {
            int posicao, id;
            sscanf(comando + 3, "%d %d", &posicao, &id);
            for (int j = 0; j < numJogos; j++) {
                if (todosJogos[j]->id == id) {
                    inserir(lista, todosJogos[j], posicao);
                    break;
                }
            }
        } else if (strncmp(comando, "IF ", 3) == 0) {
            int id = atoi(comando + 3);
            for (int j = 0; j < numJogos; j++) {
                if (todosJogos[j]->id == id) {
                    inserirFim(lista, todosJogos[j]);
                    break;
                }
            }
        } else if (strcmp(comando, "RI") == 0) {
            Game* removido = removerInicio(lista);
            if (removido) printf("(R) %s\n", removido->nome);
        } else if (strncmp(comando, "R* ", 3) == 0) {
            int posicao = atoi(comando + 3);
            Game* removido = remover(lista, posicao);
            if (removido) printf("(R) %s\n", removido->nome);
        } else if (strcmp(comando, "RF") == 0) {
            Game* removido = removerFim(lista);
            if (removido) printf("(R) %s\n", removido->nome);
        }
    }
    
    mostrar(lista);
    
    return 0;
}