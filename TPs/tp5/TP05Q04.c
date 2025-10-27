#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_FIELD_SIZE 4096
#define MAX_ARRAY_SIZE 100
#define MAX_STRING_SIZE 500
#define MAX_GAMES 2000

typedef struct {
    char items[MAX_ARRAY_SIZE][MAX_STRING_SIZE];
    int count;
} StringArray;

typedef struct {
    int id;
    char name[MAX_STRING_SIZE];
    char releaseDate[20];
    int estimatedOwners;
    float price;
    StringArray supportedLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    StringArray publishers;
    StringArray developers;
    StringArray categories;
    StringArray genres;
    StringArray tags;
} Game;

Game todosJogos[MAX_GAMES];
int totalJogos = 0;

Game jogosSelecionados[MAX_GAMES];
int numSelecionados = 0;

int numeroComparacoes = 0;
int numeroMovimentacoes = 0;

void trimManual(char *str) {
    if (str == NULL || strlen(str) == 0) {
        return;
    }
    
    int inicio = 0;
    int fim = strlen(str) - 1;
    
    while (inicio <= fim && (str[inicio] == ' ' || str[inicio] == '\t' || 
           str[inicio] == '\n' || str[inicio] == '\r')) {
        inicio++;
    }
    
    while (fim >= inicio && (str[fim] == ' ' || str[fim] == '\t' || 
           str[fim] == '\n' || str[fim] == '\r')) {
        fim--;
    }
    
    int len = fim - inicio + 1;
    if (len > 0) {
        memmove(str, str + inicio, len);
        str[len] = '\0';
    } else {
        str[0] = '\0';
    }
}

int convertMonth(const char *month) {
    const char *months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                           "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    for (int i = 0; i < 12; i++) {
        if (strncmp(month, months[i], 3) == 0) {
            return i + 1;
        }
    }
    return 1;
}

void formatDate(char *input, char *output) {
    if (input == NULL || strlen(input) == 0) {
        strcpy(output, "01/01/1900");
        return;
    }
    
    trimManual(input);
    
    if (strlen(input) == 0) {
        strcpy(output, "01/01/1900");
        return;
    }
    
    char month[20];
    int d, y, m;
    
    if (sscanf(input, "%s %d, %d", month, &d, &y) == 3) {
        m = convertMonth(month);
        sprintf(output, "%02d/%02d/%d", d, m, y);
        return;
    }
    
    if (sscanf(input, "%s %d", month, &y) == 2) {
        m = convertMonth(month);
        sprintf(output, "01/%02d/%d", m, y);
        return;
    }
    
    if (sscanf(input, "%d", &y) == 1 && y > 1000) {
        sprintf(output, "01/01/%d", y);
        return;
    }
    
    strcpy(output, "01/01/1900");
}

int parseInt(char *value) {
    if (value == NULL || strlen(value) == 0) {
        return -1;
    }
    
    trimManual(value);
    
    if (strlen(value) == 0) {
        return -1;
    }
    
    return atoi(value);
}

int parseEstimatedOwners(char *value) {
    if (value == NULL || strlen(value) == 0) {
        return 0;
    }
    
    trimManual(value);
    
    char cleaned[MAX_STRING_SIZE];
    int j = 0;
    
    for (size_t i = 0; i < strlen(value); i++) {
        if (value[i] >= '0' && value[i] <= '9') {
            cleaned[j++] = value[i];
        }
    }
    cleaned[j] = '\0';
    
    if (strlen(cleaned) == 0) {
        return 0;
    }
    
    return atoi(cleaned);
}

float parsePrice(char *value) {
    if (value == NULL || strlen(value) == 0) {
        return 0.0f;
    }
    
    trimManual(value);
    
    if (strlen(value) == 0) {
        return 0.0f;
    }
    
    char lower[MAX_STRING_SIZE];
    strcpy(lower, value);
    for (size_t i = 0; lower[i]; i++) {
        lower[i] = tolower(lower[i]);
    }
    
    if (strstr(lower, "free") != NULL) {
        return 0.0f;
    }
    
    return atof(value);
}

float parseUserScore(char *value) {
    if (value == NULL || strlen(value) == 0) {
        return -1.0f;
    }
    
    trimManual(value);
    
    if (strlen(value) == 0) {
        return -1.0f;
    }
    
    char lower[MAX_STRING_SIZE];
    strcpy(lower, value);
    for (size_t i = 0; lower[i]; i++) {
        lower[i] = tolower(lower[i]);
    }
    
    if (strstr(lower, "tbd") != NULL) {
        return -1.0f;
    }
    
    return atof(value);
}

void parseArray(char *value, StringArray *array) {
    array->count = 0;
    
    if (value == NULL || strlen(value) == 0) {
        return;
    }
    
    trimManual(value);
    
    if (strlen(value) == 0) {
        return;
    }
    
    char *start = strchr(value, '[');
    char *end = strrchr(value, ']');
    
    if (start == NULL || end == NULL || start >= end) {
        return;
    }
    
    int len = end - start - 1;
    if (len <= 0) return;
    
    char content[MAX_FIELD_SIZE];
    strncpy(content, start + 1, len);
    content[len] = '\0';
    
    trimManual(content);
    
    if (strlen(content) == 0) {
        return;
    }
    
    char current[MAX_STRING_SIZE];
    int idx = 0;
    int inQuote = 0;
    
    for (size_t i = 0; i < strlen(content); i++) {
        char c = content[i];
        
        if (c == '\'' || c == '"') {
            inQuote = !inQuote;
        } else if (c == ',' && !inQuote) {
            current[idx] = '\0';
            trimManual(current);
            if (strlen(current) > 0 && array->count < MAX_ARRAY_SIZE) {
                strcpy(array->items[array->count], current);
                array->count++;
            }
            idx = 0;
        } else {
            if (idx < MAX_STRING_SIZE - 1) {
                current[idx++] = c;
            }
        }
    }
    
    current[idx] = '\0';
    trimManual(current);
    if (strlen(current) > 0 && array->count < MAX_ARRAY_SIZE) {
        strcpy(array->items[array->count], current);
        array->count++;
    }
}

void parseSimpleList(char *value, StringArray *array) {
    array->count = 0;
    
    if (value == NULL || strlen(value) == 0) {
        return;
    }
    
    trimManual(value);
    
    if (strlen(value) == 0) {
        return;
    }
    
    char current[MAX_STRING_SIZE];
    int idx = 0;
    
    for (size_t i = 0; i <= strlen(value); i++) {
        if (value[i] == ',' || value[i] == '\0') {
            current[idx] = '\0';
            trimManual(current);
            if (strlen(current) > 0 && array->count < MAX_ARRAY_SIZE) {
                strcpy(array->items[array->count], current);
                array->count++;
            }
            idx = 0;
        } else {
            if (idx < MAX_STRING_SIZE - 1) {
                current[idx++] = value[i];
            }
        }
    }
}

int parseCSVLine(char *line, char fields[][MAX_FIELD_SIZE]) {
    int fieldCount = 0;
    int inQuotes = 0;
    int idx = 0;
    
    for (size_t i = 0; i <= strlen(line); i++) {
        char c = line[i];
        
        if (c == '"') {
            inQuotes = !inQuotes;
        } else if ((c == ',' || c == '\0') && !inQuotes) {
            fields[fieldCount][idx] = '\0';
            fieldCount++;
            idx = 0;
            if (fieldCount >= 20) break;
        } else {
            if (idx < MAX_FIELD_SIZE - 1) {
                fields[fieldCount][idx++] = c;
            }
        }
    }
    
    return fieldCount;
}

void initGame(Game *game) {
    game->id = 0;
    strcpy(game->name, "");
    strcpy(game->releaseDate, "");
    game->estimatedOwners = 0;
    game->price = 0.0f;
    game->supportedLanguages.count = 0;
    game->metacriticScore = -1;
    game->userScore = -1.0f;
    game->achievements = 0;
    game->publishers.count = 0;
    game->developers.count = 0;
    game->categories.count = 0;
    game->genres.count = 0;
    game->tags.count = 0;
}

int parseLine(char *line, Game *game) {
    char fields[20][MAX_FIELD_SIZE];
    int fieldCount = parseCSVLine(line, fields);
    
    if (fieldCount < 14) {
        return 0;
    }
    
    initGame(game);
    
    game->id = parseInt(fields[0]);
    trimManual(fields[1]);
    strcpy(game->name, fields[1]);
    formatDate(fields[2], game->releaseDate);
    game->estimatedOwners = parseEstimatedOwners(fields[3]);
    game->price = parsePrice(fields[4]);
    parseArray(fields[5], &game->supportedLanguages);
    game->metacriticScore = parseInt(fields[6]);
    game->userScore = parseUserScore(fields[7]);
    game->achievements = parseInt(fields[8]);
    parseSimpleList(fields[9], &game->publishers);
    parseSimpleList(fields[10], &game->developers);
    parseSimpleList(fields[11], &game->categories);
    parseSimpleList(fields[12], &game->genres);
    parseSimpleList(fields[13], &game->tags);
    
    return 1;
}

int readCSV(const char *filePath) {
    FILE *file = fopen(filePath, "r");
    if (file == NULL) {
        fprintf(stderr, "Erro ao abrir arquivo: %s\n", filePath);
        return 0;
    }
    
    char line[MAX_FIELD_SIZE * 2];
    totalJogos = 0;
    
    fgets(line, sizeof(line), file);
    
    while (fgets(line, sizeof(line), file) != NULL && totalJogos < MAX_GAMES) {
        line[strcspn(line, "\n")] = '\0';
        
        if (parseLine(line, &todosJogos[totalJogos])) {
            totalJogos++;
        }
    }
    
    fclose(file);
    return totalJogos;
}

Game* findById(int id) {
    for (int i = 0; i < totalJogos; i++) {
        if (todosJogos[i].id == id) {
            return &todosJogos[i];
        }
    }
    return NULL;
}

void copyGame(Game *destino, Game *origem) {
    *destino = *origem;
}

int compararData(const char *data1, const char *data2) {
    int d1, m1, a1, d2, m2, a2;
    
    sscanf(data1, "%d/%d/%d", &d1, &m1, &a1);
    sscanf(data2, "%d/%d/%d", &d2, &m2, &a2);
    
    if (a1 != a2) return a1 - a2;
    if (m1 != m2) return m1 - m2;
    return d1 - d2;
}

int compararJogos(Game *a, Game *b) {
    numeroComparacoes++;
    int cmpData = compararData(a->releaseDate, b->releaseDate);
    if (cmpData != 0) {
        return cmpData;
    }
    return a->id - b->id;
}

void trocar(Game *a, Game *b) {
    Game temp;
    copyGame(&temp, a);
    copyGame(a, b);
    copyGame(b, &temp);
    numeroMovimentacoes += 3;
}

int particionar(Game array[], int esquerda, int direita) {
    int meio = (esquerda + direita) / 2;
    Game pivo = array[meio];
    int i = esquerda - 1;
    int j = direita + 1;
    
    while (1) {
        do {
            i++;
        } while (compararJogos(&array[i], &pivo) < 0);
        
        do {
            j--;
        } while (compararJogos(&array[j], &pivo) > 0);
        
        if (i >= j) {
            return j;
        }
        
        trocar(&array[i], &array[j]);
    }
}

void quicksortRecursivo(Game array[], int esquerda, int direita) {
    if (esquerda < direita) {
        int indicePivo = particionar(array, esquerda, direita);
        quicksortRecursivo(array, esquerda, indicePivo);
        quicksortRecursivo(array, indicePivo + 1, direita);
    }
}

void quicksort(Game array[], int tamanho) {
    quicksortRecursivo(array, 0, tamanho - 1);
}

void printStringArray(StringArray *array) {
    printf("[");
    for (int i = 0; i < array->count; i++) {
        printf("%s", array->items[i]);
        if (i < array->count - 1) {
            printf(", ");
        }
    }
    printf("]");
}

void printGame(Game *game) {
    printf("=> %d", game->id);
    printf(" ## %s", game->name);
    printf(" ## %s", game->releaseDate);
    printf(" ## %d", game->estimatedOwners);
    printf(" ## %.2f", game->price);
    printf(" ## ");
    printStringArray(&game->supportedLanguages);
    printf(" ## %d", game->metacriticScore);
    printf(" ## %.1f", game->userScore);
    printf(" ## %d", game->achievements);
    printf(" ## ");
    printStringArray(&game->publishers);
    printf(" ## ");
    printStringArray(&game->developers);
    printf(" ## ");
    printStringArray(&game->categories);
    printf(" ## ");
    printStringArray(&game->genres);
    printf(" ## ");
    printStringArray(&game->tags);
    printf(" ##\n");
}

void escreverLog(const char *matricula, int comparacoes, int movimentacoes, double tempoExecucao) {
    char nomeArquivo[100];
    sprintf(nomeArquivo, "%s_quicksort.txt", matricula);
    
    FILE *file = fopen(nomeArquivo, "w");
    if (file != NULL) {
        fprintf(file, "%s\t%d\t%d\t%.0fms", matricula, comparacoes, movimentacoes, tempoExecucao);
        fclose(file);
    }
}

const char* getFilePath() {
    #ifdef _WIN32
        return "games.csv";
    #else
        return "/tmp/games.csv";
    #endif
}

int main() {
    const char *matricula = "caioceo";
    const char *filePath = getFilePath();
    
    int count = readCSV(filePath);
    
    if (count == 0) {
        fprintf(stderr, "Nenhum jogo foi carregado\n");
        return 1;
    }
    
    char input[100];
    
    while (1) {
        if (fgets(input, sizeof(input), stdin) == NULL) {
            break;
        }
        
        input[strcspn(input, "\n")] = '\0';
        trimManual(input);
        
        if (strcmp(input, "FIM") == 0) {
            break;
        }
        
        int id = atoi(input);
        Game *jogo = findById(id);
        
        if (jogo != NULL && numSelecionados < MAX_GAMES) {
            copyGame(&jogosSelecionados[numSelecionados], jogo);
            numSelecionados++;
        }
    }
    
    clock_t inicio = clock();
    
    quicksort(jogosSelecionados, numSelecionados);
    
    clock_t fim = clock();
    double tempoExecucao = ((double)(fim - inicio) / CLOCKS_PER_SEC) * 1000.0;
    
    for (int i = 0; i < numSelecionados; i++) {
        printGame(&jogosSelecionados[i]);
    }
    
    escreverLog(matricula, numeroComparacoes, numeroMovimentacoes, tempoExecucao);
    
    return 0;
}