import java.io.*;
import java. text.*;
import java.util.*;

class Game {
    private int id;
    private String name;
    private String releaseDate;
    private int estimatedOwners;
    private float price;
    private String[] supportedLanguages;
    private int metacriticScore;
    private float userScore;
    private int achievements;
    private String[] publishers;
    private String[] developers;
    private String[] categories;
    private String[] genres;
    private String[] tags;

    public Game() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.supportedLanguages = new String[0];
        this.metacriticScore = -1;
        this.userScore = -1.0f;
        this.achievements = 0;
        this.publishers = new String[0];
        this.developers = new String[0];
        this.categories = new String[0];
        this.genres = new String[0];
        this. tags = new String[0];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSupportedLanguages(String[] supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public void setAchievements(int achievements) {
        this. achievements = achievements;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public void setDevelopers(String[] developers) {
        this.developers = developers;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    private static String trimManual(String str) {
        if (str == null || str.length() == 0)
            return str;
        int inicio = 0;
        int fim = str.length() - 1;
        while (inicio <= fim && (str.charAt(inicio) == ' ' || str. charAt(inicio) == '\t' ||
                str.charAt(inicio) == '\n' || str. charAt(inicio) == '\r'))
            inicio++;
        while (fim >= inicio && (str.charAt(fim) == ' ' || str.charAt(fim) == '\t' ||
                str.charAt(fim) == '\n' || str.charAt(fim) == '\r'))
            fim--;
        if (inicio > fim)
            return "";
        return str.substring(inicio, fim + 1);
    }

    public static GameList lerCSV(String caminho) {
        GameList jogos = new GameList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(caminho));
            br.readLine();
            String linha;
            while ((linha = br. readLine()) != null) {
                try {
                    Game jogo = parseLinha(linha);
                    if (jogo != null)
                        jogos.add(jogo);
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
            }
        }
        return jogos;
    }

    private static Game parseLinha(String linha) {
        StringList campos = parseLinhaCSV(linha);
        if (campos.size() < 14)
            return null;
        Game jogo = new Game();
        jogo.setId(parseInt(campos.get(0)));
        jogo.setName(campos.get(1));
        jogo.setReleaseDate(formatarData(campos.get(2)));
        jogo.setEstimatedOwners(parseEstimatedOwners(campos.get(3)));
        jogo. setPrice(parsePreco(campos.get(4)));
        jogo.setSupportedLanguages(parseArray(campos.get(5)));
        jogo.setMetacriticScore(parseInt(campos.get(6)));
        jogo. setUserScore(parseUserScore(campos.get(7)));
        jogo.setAchievements(parseInt(campos.get(8)));
        jogo. setPublishers(parseListaSimples(campos.get(9)));
        jogo.setDevelopers(parseListaSimples(campos.get(10)));
        jogo.setCategories(parseListaSimples(campos.get(11)));
        jogo. setGenres(parseListaSimples(campos.get(12)));
        jogo.setTags(parseListaSimples(campos.get(13)));
        return jogo;
    }

    private static StringList parseLinhaCSV(String linha) {
        StringList campos = new StringList();
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;
        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"')
                dentroAspas = !dentroAspas;
            else if (c == ',' && ! dentroAspas) {
                campos.add(atual.toString());
                atual = new StringBuilder();
            } else
                atual.append(c);
        }
        campos.add(atual.toString());
        return campos;
    }

    private static int parseInt(String valor) {
        if (valor == null || valor.length() == 0)
            return -1;
        valor = trimManual(valor);
        if (valor. length() == 0)
            return -1;
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String formatarData(String dataStr) {
        if (dataStr == null || dataStr.length() == 0)
            return "01/01/1900";
        dataStr = trimManual(dataStr);
        if (dataStr. length() == 0)
            return "01/01/1900";
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            Date data = formatoEntrada.parse(dataStr);
            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSaida.format(data);
        } catch (ParseException e) {
            try {
                SimpleDateFormat formatoEntrada2 = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date data = formatoEntrada2.parse(dataStr);
                SimpleDateFormat formatoSaida = new SimpleDateFormat("MM/yyyy");
                return "01/" + formatoSaida.format(data);
            } catch (ParseException e2) {
                if (dataStr.matches("\\d{4}"))
                    return "01/01/" + dataStr;
                return "01/01/1900";
            }
        }
    }

    private static int parseEstimatedOwners(String valor) {
        if (valor == null || valor.length() == 0)
            return 0;
        valor = trimManual(valor);
        if (valor.length() == 0)
            return 0;
        StringBuilder limpo = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c >= '0' && c <= '9')
                limpo.append(c);
        }
        if (limpo.length() == 0)
            return 0;
        try {
            return Integer.parseInt(limpo.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static float parsePreco(String valor) {
        if (valor == null || valor.length() == 0)
            return 0.0f;
        valor = trimManual(valor);
        if (valor.length() == 0)
            return 0.0f;
        String valorMinusculo = valor.toLowerCase();
        if (valorMinusculo.indexOf("free") >= 0)
            return 0.0f;
        try {
            return Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private static String[] parseArray(String valor) {
        if (valor == null || valor.length() == 0)
            return new String[0];
        valor = trimManual(valor);
        if (valor.length() == 0)
            return new String[0];
        if (valor.indexOf('[') == -1 || valor.indexOf(']') == -1)
            return new String[0];
        int inicio = valor.indexOf('[');
        int fim = valor.lastIndexOf(']');
        if (inicio >= fim)
            return new String[0];
        String conteudo = valor.substring(inicio + 1, fim);
        conteudo = trimManual(conteudo);
        if (conteudo.length() == 0)
            return new String[0];
        StringList resultado = new StringList();
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;
        for (int i = 0; i < conteudo. length(); i++) {
            char c = conteudo.charAt(i);
            if (c == '\'' || c == '"')
                dentroAspas = !dentroAspas;
            else if (c == ',' && ! dentroAspas) {
                String item = trimManual(atual.toString());
                if (item.length() > 0)
                    resultado. add(item);
                atual = new StringBuilder();
            } else
                atual.append(c);
        }
        String ultimoItem = trimManual(atual.toString());
        if (ultimoItem.length() > 0)
            resultado.add(ultimoItem);
        return resultado. toArray();
    }

    private static String[] parseListaSimples(String valor) {
        if (valor == null || valor. length() == 0)
            return new String[0];
        valor = trimManual(valor);
        if (valor.length() == 0)
            return new String[0];
        StringList resultado = new StringList();
        StringBuilder atual = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c == ',') {
                String item = trimManual(atual.toString());
                if (item.length() > 0)
                    resultado.add(item);
                atual = new StringBuilder();
            } else
                atual.append(c);
        }
        String ultimoItem = trimManual(atual.toString());
        if (ultimoItem. length() > 0)
            resultado.add(ultimoItem);
        return resultado.toArray();
    }

    private static float parseUserScore(String valor) {
        if (valor == null || valor.length() == 0)
            return -1.0f;
        valor = trimManual(valor);
        if (valor.length() == 0)
            return -1.0f;
        String valorMinusculo = valor.toLowerCase();
        if (valorMinusculo.indexOf("tbd") >= 0)
            return -1.0f;
        try {
            return Float. parseFloat(valor);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=> ").append(id);
        sb.append(" ## ").append(name);
        sb.append(" ## ").append(releaseDate);
        sb. append(" ## ").append(estimatedOwners);
        sb. append(" ## ").append(price);
        sb.append(" ## ").append(arrayToString(supportedLanguages));
        sb.append(" ## ").append(metacriticScore);
        sb.append(" ## ").append(userScore);
        sb.append(" ## ").append(achievements);
        sb.append(" ## "). append(arrayToString(publishers));
        sb.append(" ## ").append(arrayToString(developers));
        sb.append(" ## ").append(arrayToString(categories));
        sb.append(" ## ").append(arrayToString(genres));
        sb.append(" ## ").append(arrayToString(tags));
        sb.append(" ##");
        return sb.toString();
    }

    private String arrayToString(String[] array) {
        if (array == null || array.length == 0)
            return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb. append(array[i]);
            if (i < array.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

class StringList {
    private String[] dados;
    private int tamanho;

    public StringList() {
        this.dados = new String[10];
        this.tamanho = 0;
    }

    public void add(String s) {
        if (tamanho >= dados.length) {
            String[] novosDados = new String[dados.length * 2];
            for (int i = 0; i < dados.length; i++)
                novosDados[i] = dados[i];
            dados = novosDados;
        }
        dados[tamanho++] = s;
    }

    public String get(int indice) {
        return (indice < 0 || indice >= tamanho) ? "" : dados[indice];
    }

    public int size() {
        return tamanho;
    }

    public String[] toArray() {
        String[] resultado = new String[tamanho];
        for (int i = 0; i < tamanho; i++)
            resultado[i] = dados[i];
        return resultado;
    }
}

class GameList {
    private Game[] dados;
    private int tamanho;

    public GameList() {
        this.dados = new Game[100];
        this.tamanho = 0;
    }

    public void add(Game g) {
        if (tamanho >= dados.length) {
            Game[] novosDados = new Game[dados.length * 2];
            for (int i = 0; i < dados.length; i++)
                novosDados[i] = dados[i];
            dados = novosDados;
        }
        dados[tamanho++] = g;
    }

    public Game get(int indice) {
        return (indice < 0 || indice >= tamanho) ? null : dados[indice];
    }

    public int size() {
        return tamanho;
    }
}

class NoArvore1 {
    public int chave;
    public NoArvore1 esq;
    public NoArvore1 dir;
    public NoArvore2 raizArvore2;

    public NoArvore1(int chave) {
        this.chave = chave;
        this.esq = null;
        this.dir = null;
        this.raizArvore2 = null;
    }
}

class NoArvore2 {
    public Game game;
    public NoArvore2 esq;
    public NoArvore2 dir;

    public NoArvore2(Game game) {
        this.game = game;
        this.esq = null;
        this.dir = null;
    }
}

class ArvoreDeArvores {
    private NoArvore1 raiz;
    private int comparacoes;

    public ArvoreDeArvores() {
        this.raiz = null;
        this.comparacoes = 0;
        construirPrimeiraArvore();
    }

    private void construirPrimeiraArvore() {
        int[] chaves = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        for (int i = 0; i < chaves.length; i++) {
            raiz = inserirArvore1(chaves[i], raiz);
        }
    }

    private NoArvore1 inserirArvore1(int chave, NoArvore1 no) {
        if (no == null) {
            return new NoArvore1(chave);
        }
        
        if (chave < no.chave) {
            no.esq = inserirArvore1(chave, no.esq);
        } else if (chave > no.chave) {
            no.dir = inserirArvore1(chave, no.dir);
        }
        
        return no;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public void resetComparacoes() {
        this.comparacoes = 0;
    }

    public void inserir(Game game) {
        int chaveArvore1 = game.getEstimatedOwners() % 15;
        NoArvore1 no1 = buscarNoArvore1(chaveArvore1, raiz);
        if (no1 != null) {
            no1.raizArvore2 = inserirArvore2(game, no1.raizArvore2);
        }
    }

    private NoArvore1 buscarNoArvore1(int chave, NoArvore1 no) {
        if (no == null) {
            return null;
        }
        if (chave == no.chave) {
            return no;
        } else if (chave < no.chave) {
            return buscarNoArvore1(chave, no.esq);
        } else {
            return buscarNoArvore1(chave, no.dir);
        }
    }

    private NoArvore2 inserirArvore2(Game game, NoArvore2 no) {
        if (no == null) {
            return new NoArvore2(game);
        }
        
        int cmp = game.getName().compareTo(no.game.getName());
        
        if (cmp < 0) {
            no.esq = inserirArvore2(game, no.esq);
        } else if (cmp > 0) {
            no.dir = inserirArvore2(game, no.dir);
        }
        
        return no;
    }

    public void pesquisar(String nome) {
        System.out.print("=> " + nome + " => raiz");
        resetComparacoes();
        boolean resultado = percorrerArvore1(nome, raiz);
        System.out.println(resultado ? " SIM" : " NAO");
    }

    private boolean percorrerArvore1(String nome, NoArvore1 no) {
        if (no == null) {
            return false;
        }
    
        boolean encontrado = pesquisarArvore2(nome, no.raizArvore2);
        if (encontrado) {
            return true;
        }
        
        System.out.print(" ESQ");
        encontrado = percorrerArvore1(nome, no.esq);
        if (encontrado) {
            return true;
        }
        
        System.out.print(" DIR");
        encontrado = percorrerArvore1(nome, no.dir);
        return encontrado;
    }

    private boolean pesquisarArvore2(String nome, NoArvore2 no) {
        if (no == null) {
            return false;
        }
        
        comparacoes++;
        int cmp = nome.compareTo(no.game.getName());
        
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            System.out.print(" esq");
            return pesquisarArvore2(nome, no.esq);
        } else {
            System.out.print(" dir");
            return pesquisarArvore2(nome, no.dir);
        }
    }
}

public class TP07Q02 {

    private static String trimManual(String str) {
        if (str == null || str.length() == 0)
            return str;
        int inicio = 0;
        int fim = str.length() - 1;
        while (inicio <= fim && (str.charAt(inicio) == ' ' || str.charAt(inicio) == '\t' ||
                str.charAt(inicio) == '\n' || str.charAt(inicio) == '\r'))
            inicio++;
        while (fim >= inicio && (str.charAt(fim) == ' ' || str. charAt(fim) == '\t' ||
                str.charAt(fim) == '\n' || str. charAt(fim) == '\r'))
            fim--;
        if (inicio > fim)
            return "";
        return str.substring(inicio, fim + 1);
    }

    public static Game buscarPorId(GameList jogos, int id) {
        for (int i = 0; i < jogos.size(); i++) {
            if (jogos.get(i). getId() == id)
                return jogos.get(i);
        }
        return null;
    }

    private static String obterCaminhoArquivo() {
        String sistemaOperacional = System.getProperty("os.name"). toLowerCase();
        if (sistemaOperacional.indexOf("win") >= 0) {
            return "games.csv";
        } else {
            return "/tmp/games.csv";
        }
    }

    public static void main(String[] args) {
        String matricula = "caioceo";
        String caminhoCSV = obterCaminhoArquivo();
        GameList todosJogos = Game.lerCSV(caminhoCSV);
        
        ArvoreDeArvores arvore = new ArvoreDeArvores();
        
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine();
            entrada = trimManual(entrada);
            
            if (entrada.equals("FIM"))
                break;
            
            try {
                int id = Integer. parseInt(entrada);
                Game jogo = buscarPorId(todosJogos, id);
                if (jogo != null) {
                    arvore.inserir(jogo);
                }
            } catch (NumberFormatException e) {
            }
        }
        
        long tempoInicio = System.currentTimeMillis();
        int totalComparacoes = 0;
        
        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine();
            entrada = trimManual(entrada);
            
            if (entrada.equals("FIM"))
                break;
            
            arvore.pesquisar(entrada);
            totalComparacoes += arvore.getComparacoes();
        }
        
        long tempoFim = System.currentTimeMillis();
        long tempoExecucao = tempoFim - tempoInicio;
        
        scanner.close();
        
        try {
            FileWriter escritor = new FileWriter(matricula + "_arvoreArvore.txt");
            escritor.write(matricula + "\t" + tempoExecucao + "ms\t" + totalComparacoes);
            escritor.close();
        } catch (IOException e) {
        }
    }
}