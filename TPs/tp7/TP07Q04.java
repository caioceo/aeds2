import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;

class Utilitarios {

    public int converterParaInteiro(String texto) {
        if (texto == null || texto.isEmpty()) return 0;
        int valor = 0;
        for (int idx = 0; idx < texto.length(); idx++) {
            char caractere = texto.charAt(idx);
            if (Character.isDigit(caractere)) valor = valor * 10 + (caractere - '0');
        }
        return valor;
    }

    public int contarPalavras(String texto) {
        if (texto == null || texto.isEmpty()) return 0;
        int contador = 1;
        for (int idx = 0; idx < texto.length(); idx++) {
            if (texto.charAt(idx) == ',') contador++;
        }
        return contador;
    }

    public int buscaBinaria(RegistroJogo[] jogos, int chave, int inicio, int fim) {
        if (inicio > fim) return -1;
        int centro = (inicio + fim) / 2;
        if (jogos[centro].obterIdentificador() == chave) return centro;
        else if (jogos[centro].obterIdentificador() > chave)
            return buscaBinaria(jogos, chave, inicio, centro - 1);
        else
            return buscaBinaria(jogos, chave, centro + 1, fim);
    }

    public String ajustarData(String data) {
        if (data == null || data.isEmpty()) return "01/01/0000";
        boolean temVirgula = false;
        for (int idx = 0; idx < data.length(); idx++) {
            if (data.charAt(idx) == ',') temVirgula = true;
        }
        if (!temVirgula) {
            StringBuilder builder = new StringBuilder();
            for (int idx = 0; idx < data.length(); idx++) {
                builder.append(data.charAt(idx));
                if (data.charAt(idx) == ' ') builder.append("01, ");
            }
            data = builder.toString();
        }
        String mes = data.substring(0, 3);
        switch (mes) {
            case "Dec": mes = "12"; break;
            case "Nov": mes = "11"; break;
            case "Oct": mes = "10"; break;
            case "Sep": mes = "09"; break;
            case "Aug": mes = "08"; break;
            case "Jul": mes = "07"; break;
            case "Jun": mes = "06"; break;
            case "May": mes = "05"; break;
            case "Apr": mes = "04"; break;
            case "Mar": mes = "03"; break;
            case "Feb": mes = "02"; break;
            case "Jan": mes = "01"; break;
            default: mes = "01"; break;
        }
        StringBuilder dataCompleta = new StringBuilder();
        for (int idx = 4; idx < data.length(); idx++) {
            if (data.charAt(idx) == ',') {
                dataCompleta.append("/" + mes + "/");
                idx += 2;
            }
            dataCompleta.append(data.charAt(idx));
        }
        String dataFormatada = dataCompleta.toString();
        if (dataFormatada.charAt(1) == '/') dataFormatada = "0" + dataFormatada;
        return dataFormatada;
    }

    public String[] dividirPalavras(String texto, int qtd) {
        String[] resultado = new String[qtd];
        for (int idx = 0, pos = 0; idx < qtd && pos < texto.length(); idx++) {
            boolean continuar = true;
            StringBuilder construtor = new StringBuilder();
            for (; continuar && pos < texto.length(); pos++) {
                if (texto.charAt(pos) == ',') {
                    continuar = false;
                    if (pos + 1 < texto.length() && texto.charAt(pos + 1) == ' ') pos++;
                } else if (texto.charAt(pos) != ']' && texto.charAt(pos) != '[') {
                    construtor.append(texto.charAt(pos));
                }
            }
            if (construtor.length() > 1 && construtor.charAt(0) == '\'') {
                construtor.deleteCharAt(0);
                if (construtor.charAt(construtor.length() - 1) == '\'') construtor.deleteCharAt(construtor.length() - 1);
            }
            resultado[idx] = construtor.toString();
        }
        return resultado;
    }

    private void trocar(int pos1, int pos2, RegistroJogo[] jogos) {
        RegistroJogo aux = jogos[pos1];
        jogos[pos1] = jogos[pos2];
        jogos[pos2] = aux;
    }

    public void ordenarRapido(int esquerda, int direita, RegistroJogo[] jogos) {
        int indiceLow = esquerda, indiceHigh = direita;
        int pivoValor = jogos[(esquerda + direita) / 2].obterIdentificador();
        while (indiceLow <= indiceHigh) {
            while (jogos[indiceLow].obterIdentificador() < pivoValor) indiceLow++;
            while (jogos[indiceHigh].obterIdentificador() > pivoValor) indiceHigh--;
            if (indiceLow <= indiceHigh) {
                trocar(indiceLow, indiceHigh, jogos);
                indiceLow++;
                indiceHigh--;
            }
        }
        if (esquerda < indiceHigh) ordenarRapido(esquerda, indiceHigh, jogos);
        if (indiceLow < direita) ordenarRapido(indiceLow, direita, jogos);
    }

    public float converterParaFloat(String texto) {
        if (texto == null || texto.isEmpty() || texto.equals("tbd")) return 0f;
        texto = texto.replace(',', '.').trim();
        try {
            return Float.parseFloat(texto);
        } catch (Exception erro) {
            return 0f;
        }
    }
}


class Jogo extends Utilitarios {
    private int id;
    private String name;
    private String releaseDate;
    private int estimatedOwners;
    private float price;
    private String[] suppportedLanguages;
    private int metacriticScore;
    private float userScore;
    private int achievements;
    private String[] publishers;
    private String[] developers;
    private String[] categories;
    private String[] genres;
    private String[] tags;
    private String linha;

    public Jogo(String linha) {
        this.linha = linha;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRelaseDate() { return releaseDate; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRelaseDate(String releaseDate) { this.releaseDate = ajustarData(releaseDate); }
    public void setEstimatedOwners(int estimatedOwners) { this.estimatedOwners = estimatedOwners; }
    public void setPrice(float price) { this.price = price; }
    public void setSuppportedLanguages(String[] suppportedLanguages, int tamanho) { this.suppportedLanguages = suppportedLanguages; }
    public void setMetacriticScore(int metacriticScore) { this.metacriticScore = metacriticScore; }
    public void setUserScore(float userScore) { this.userScore = userScore; }
    public void setAchievements(int achievements) { this.achievements = achievements; }
    public void setPublishers(String[] publishers, int tamanho) { this.publishers = publishers; }
    public void setDevelopers(String[] developers, int tamanho) { this.developers = developers; }
    public void setCategories(String[] categories, int tamanho) { this.categories = categories; }
    public void setGenres(String[] genres, int tamanho) { this.genres = genres; }
    public void setTags(String[] tags, int tamanho) { this.tags = tags; }

    public void imprimir() {
        System.out.print("=> " + id + " ## " + name + " ## " + releaseDate + " ## " +
                estimatedOwners + " ## " + price + " ## ");
        imprimirArray(suppportedLanguages);
        System.out.print(" ## " + metacriticScore + " ## " + userScore + " ## " +
                achievements + " ## ");
        imprimirArray(publishers);
        System.out.print(" ## ");
        imprimirArray(developers);
        System.out.print(" ## ");
        imprimirArray(categories);
        System.out.print(" ## ");
        imprimirArray(genres);
        System.out.print(" ## ");
        imprimirArray(tags);
        System.out.println(" ##");
    }

    private void imprimirArray(String[] array) {
        System.out.print("[");
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i]);
                if (i < array.length - 1) System.out.print(", ");
            }
        }
        System.out.print("]");
    }
}

class RegistroJogo extends Jogo {
    public RegistroJogo proximo;  
    private String conteudo;

    public RegistroJogo(String texto) {
        super(texto);
        this.conteudo = texto;
        this.proximo = null;
    }

    public int obterIdentificador() {
        return getId();
    }

    public void processarDados() {
        analisarLinha(conteudo);
    }

    public void atribuirCampo(int indice, String valor) {
        int quantidade = contarPalavras(valor);
        switch (indice) {
            case 13: setTags(dividirPalavras(valor, quantidade), quantidade); break;
            case 12: setGenres(dividirPalavras(valor, quantidade), quantidade); break;
            case 11: setCategories(dividirPalavras(valor, quantidade), quantidade); break;
            case 10: setDevelopers(dividirPalavras(valor, quantidade), quantidade); break;
            case 9: setPublishers(dividirPalavras(valor, quantidade), quantidade); break;
            case 8: setAchievements(converterParaInteiro(valor)); break;
            case 7: setUserScore(converterParaFloat(valor)); break;
            case 6: setMetacriticScore(converterParaInteiro(valor)); break;
            case 5: setSuppportedLanguages(dividirPalavras(valor, quantidade), quantidade); break;
            case 4: setPrice(converterParaFloat(valor)); break;
            case 3: setEstimatedOwners(converterParaInteiro(valor)); break;
            case 2: setRelaseDate(valor); break;
            case 1: setName(valor); break;
            case 0: setId(converterParaInteiro(valor)); break;
        }
    }

    private void analisarLinha(String texto) {
        int campo = 0;
        for (int posicao = 0; posicao < texto.length(); campo++) {
            StringBuilder construtor = new StringBuilder();
            boolean continuar = true;
            int dentroAspas = 0;
            for (; posicao < texto.length() && continuar; posicao++) {
                if (texto.charAt(posicao) == '"') dentroAspas = 1 - dentroAspas;
                else if (texto.charAt(posicao) == ',' && dentroAspas == 0) continuar = false;
                else construtor.append(texto.charAt(posicao));
            }
            atribuirCampo(campo, construtor.toString());
        }
    }
}

class NodoAN {
  public boolean cor;
  public RegistroJogo dado;
  public NodoAN esquerda, direita;

  public NodoAN(RegistroJogo dado, boolean cor, NodoAN esquerda, NodoAN direita) {
    this.cor = cor;
    this.dado = dado;
    this.esquerda = esquerda;
    this.direita = direita;
  }

  public NodoAN(RegistroJogo dado, boolean cor) {
    this(dado, cor, null, null);
  }

  public NodoAN(RegistroJogo dado) {
    this(dado, false, null, null);
  }
}

class ArvoreAlviNegra {

    private NodoAN raiz;
    public int comp = 0;

    public ArvoreAlviNegra() {
        raiz = null;
    }

    private NodoAN girarEsquerda(NodoAN nodo) {
        NodoAN filhoDir = nodo.direita;
        NodoAN neto = filhoDir.esquerda;

        filhoDir.esquerda = nodo;
        nodo.direita = neto;

        return filhoDir;
    }

    private NodoAN girarDireita(NodoAN nodo) {
        NodoAN filhoEsq = nodo.esquerda;
        NodoAN neto = filhoEsq.direita;

        filhoEsq.direita = nodo;
        nodo.esquerda = neto;

        return filhoEsq;
    }

    private NodoAN girarEsquerdaDireita(NodoAN nodo) {
        nodo.esquerda = girarEsquerda(nodo.esquerda);
        return girarDireita(nodo);
    }

    private NodoAN girarDireitaEsquerda(NodoAN nodo) {
        nodo.direita = girarDireita(nodo.direita);
        return girarEsquerda(nodo);
    }

    public void buscar(String chave) {
        System.out.print("raiz ");
        if (buscar(chave, raiz)) {
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }
    }

    private boolean buscar(String chave, NodoAN no) {
        boolean encontrado = false;

        if (no != null) {
            String nomeAtual = no.dado.getName();

            if (chave.compareTo(nomeAtual) == 0) {
                encontrado = true;
                comp++;
            } else if (nomeAtual.compareTo(chave) < 0) {
                System.out.print("dir ");
                encontrado = buscar(chave, no.direita);
                comp++;
            } else {
                System.out.print("esq ");
                encontrado = buscar(chave, no.esquerda);
                comp++;
            }
        }
        return encontrado;
    }

    private void ajustarBalanceamento(NodoAN bisavo, NodoAN avo, NodoAN pai, NodoAN atual) {
        if (pai.cor == true) {

            if (pai.dado.getName().compareTo(avo.dado.getName()) < 0) {
                if (atual.dado.getName().compareTo(pai.dado.getName()) >= 0)
                    avo = girarEsquerdaDireita(avo);
                else
                    avo = girarDireita(avo);
            } else {
                if (atual.dado.getName().compareTo(pai.dado.getName()) <= 0)
                    avo = girarDireitaEsquerda(avo);
                else
                    avo = girarEsquerda(avo);
            }

            if (bisavo == null) raiz = avo;
            else if (avo.dado.getName().compareTo(bisavo.dado.getName()) >= 0)
                bisavo.direita = avo;
            else
                bisavo.esquerda = avo;

            avo.cor = false;
            avo.esquerda.cor = avo.direita.cor = true;
        }
    }

    public void adicionar(RegistroJogo item) {
        if (raiz == null) {
            raiz = new NodoAN(item);
        } else if (raiz.esquerda == null && raiz.direita == null) {
            if (item.getName().compareTo(raiz.dado.getName()) >= 0) {
                raiz.direita = new NodoAN(item);
            } else {
                raiz.esquerda = new NodoAN(item);
            }
        } else if (raiz.esquerda == null) {
            if (item.getName().compareTo(raiz.dado.getName()) >= 0) {
                if (item.getName().compareTo(raiz.direita.dado.getName()) >= 0) {
                    raiz.esquerda = new NodoAN(raiz.dado);
                    raiz.dado = raiz.direita.dado;
                    raiz.direita.dado = item;
                } else {
                    raiz.esquerda = new NodoAN(raiz.dado);
                    raiz.dado = item;
                }
            } else {
                raiz.esquerda = new NodoAN(item);
            }
            raiz.esquerda.cor = raiz.direita.cor = false;

        } else if (raiz.direita == null) {
            if (item.getName().compareTo(raiz.dado.getName()) <= 0) {
                if (item.getName().compareTo(raiz.esquerda.dado.getName()) <= 0) {
                    raiz.direita = new NodoAN(raiz.dado);
                    raiz.dado = raiz.esquerda.dado;
                    raiz.esquerda.dado = item;
                } else {
                    raiz.direita = new NodoAN(raiz.dado);
                    raiz.dado = item;
                }
            } else {
                raiz.direita = new NodoAN(item);
            }
            raiz.esquerda.cor = raiz.direita.cor = false;

        } else {
            adicionarRecursivo(item, null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void adicionarRecursivo(RegistroJogo item, NodoAN bisavo, NodoAN avo, NodoAN pai, NodoAN atual) {
        if (atual == null) {
            if (item.getName().compareTo(pai.dado.getName()) >= 0) {
                atual = pai.direita = new NodoAN(item, true);
            } else {
                atual = pai.esquerda = new NodoAN(item, true);
            }
            if (pai.cor == true) ajustarBalanceamento(bisavo, avo, pai, atual);

        } else {
            // split 4-no
            if (atual.esquerda != null && atual.direita != null && atual.esquerda.cor && atual.direita.cor) {
                atual.cor = true;
                atual.esquerda.cor = atual.direita.cor = false;

                if (pai != null && pai.cor == true) ajustarBalanceamento(bisavo, avo, pai, atual);
            }

            if (item.getName().compareTo(atual.dado.getName()) >= 0) {
                adicionarRecursivo(item, avo, pai, atual, atual.direita);
            } else if (item.getName().compareTo(atual.dado.getName()) < 0) {
                adicionarRecursivo(item, avo, pai, atual, atual.esquerda);
            }
        }
    }
}

public class TP07Q04 {
    public static void main(String[] args) throws FileNotFoundException {
        File arquivo = new File("/tmp/games.csv");
        Scanner leitorArquivo = new Scanner(arquivo);
        Scanner entrada = new Scanner(System.in);

        if (leitorArquivo.hasNextLine()) leitorArquivo.nextLine();

        ArvoreAlviNegra arvore = new ArvoreAlviNegra();
        RegistroJogo[] vetorJogos = new RegistroJogo[1850];
        Utilitarios utilitarios = new Utilitarios();
        int contador = 0;

        while (leitorArquivo.hasNextLine()) {
            String linhaArquivo = leitorArquivo.nextLine();
            vetorJogos[contador] = new RegistroJogo(linhaArquivo);
            vetorJogos[contador].processarDados();
            contador++;
        }

        utilitarios.ordenarRapido(0, contador - 1, vetorJogos);

        String input = entrada.nextLine();
        while (!input.equals("FIM")) {
            int idBuscado = utilitarios.converterParaInteiro(input);
            int posicao = utilitarios.buscaBinaria(vetorJogos, idBuscado, 0, contador - 1);
            if (posicao != -1) arvore.adicionar(vetorJogos[posicao]);
            input = entrada.nextLine();
        }
        long tempoInicial = System.nanoTime();
        input = entrada.nextLine();
        while (!input.equals("FIM")) {
            System.out.print(input +  ": =>");
            arvore.buscar(input);
            input = entrada.nextLine();
        }
        long tempoFinal = System.nanoTime();
        double duracao = (tempoFinal - tempoInicial) / 1_000_000.0;

        try {
            FileWriter escritor = new FileWriter("878672_binaria.txt");
            escritor.write("878672\t" + arvore.comp + "\t"  + String.format("%.3f", duracao));
            escritor.close();
        } catch (IOException erro) {
            System.out.println("Erro ao criar arquivo de log: " + erro.getMessage());
        }
        entrada.close();
        leitorArquivo.close();

    }
}
