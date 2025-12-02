import java.io.*;
import java.text.*;
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
        this.tags = new String[0];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
        this.achievements = achievements;
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
        while (inicio <= fim && (str.charAt(inicio) == ' ' || str.charAt(inicio) == '\t' ||
                str.charAt(inicio) == '\n' || str.charAt(inicio) == '\r'))
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
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    Game jogo = parseLinha(linha);
                    if (jogo != null)
                        jogos.add(jogo);
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
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
        jogo.setPrice(parsePreco(campos.get(4)));
        jogo.setSupportedLanguages(parseArray(campos.get(5)));
        jogo.setMetacriticScore(parseInt(campos.get(6)));
        jogo.setUserScore(parseUserScore(campos.get(7)));
        jogo.setAchievements(parseInt(campos.get(8)));
        jogo.setPublishers(parseListaSimples(campos.get(9)));
        jogo.setDevelopers(parseListaSimples(campos.get(10)));
        jogo.setCategories(parseListaSimples(campos.get(11)));
        jogo.setGenres(parseListaSimples(campos.get(12)));
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
            else if (c == ',' && !dentroAspas) {
                campos.add(atual.toString());
                atual = new StringBuilder();
            } else
                atual.append(c);
        }
        campos.add(atual.toString());
        return campos;
    }

    private static int parseInt(String valor) {
        try {
            return Integer.parseInt(trimManual(valor));
        } catch (Exception e) {
            return -1;
        }
    }

    private static String formatarData(String dataStr) {
        dataStr = trimManual(dataStr);
        if (dataStr.isEmpty())
            return "01/01/1900";
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            Date data = formatoEntrada.parse(dataStr);
            return new SimpleDateFormat("dd/MM/yyyy").format(data);
        } catch (ParseException e) {
            try {
                SimpleDateFormat formatoEntrada2 = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date data = formatoEntrada2.parse(dataStr);
                return "01/" + new SimpleDateFormat("MM/yyyy").format(data);
            } catch (ParseException e2) {
                if (dataStr.matches("\\d{4}"))
                    return "01/01/" + dataStr;
                return "01/01/1900";
            }
        }
    }

    private static int parseEstimatedOwners(String valor) {
        StringBuilder limpo = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c >= '0' && c <= '9')
                limpo.append(c);
        }
        try {
            return limpo.length() > 0 ? Integer.parseInt(limpo.toString()) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private static float parsePreco(String valor) {
        valor = trimManual(valor);
        if (valor.toLowerCase().indexOf("free") >= 0)
            return 0.0f;
        try {
            return Float.parseFloat(valor);
        } catch (Exception e) {
            return 0.0f;
        }
    }

    private static String[] parseArray(String valor) {
        valor = trimManual(valor);
        if (valor.isEmpty() || valor.indexOf('[') == -1)
            return new String[0];
        int inicio = valor.indexOf('['), fim = valor.lastIndexOf(']');
        if (inicio >= fim)
            return new String[0];
        String conteudo = trimManual(valor.substring(inicio + 1, fim));
        if (conteudo.isEmpty())
            return new String[0];
        StringList resultado = new StringList();
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;
        for (int i = 0; i < conteudo.length(); i++) {
            char c = conteudo.charAt(i);
            if (c == '\'' || c == '"')
                dentroAspas = !dentroAspas;
            else if (c == ',' && !dentroAspas) {
                String item = trimManual(atual.toString());
                if (!item.isEmpty())
                    resultado.add(item);
                atual = new StringBuilder();
            } else
                atual.append(c);
        }
        String ultimoItem = trimManual(atual.toString());
        if (!ultimoItem.isEmpty())
            resultado.add(ultimoItem);
        return resultado.toArray();
    }

    private static String[] parseListaSimples(String valor) {
        valor = trimManual(valor);
        if (valor.isEmpty())
            return new String[0];
        StringList resultado = new StringList();
        StringBuilder atual = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c == ',') {
                String item = trimManual(atual.toString());
                if (!item.isEmpty())
                    resultado.add(item);
                atual = new StringBuilder();
            } else
                atual.append(c);
        }
        String ultimoItem = trimManual(atual.toString());
        if (!ultimoItem.isEmpty())
            resultado.add(ultimoItem);
        return resultado.toArray();
    }

    private static float parseUserScore(String valor) {
        valor = trimManual(valor);
        if (valor.isEmpty() || valor.toLowerCase().indexOf("tbd") >= 0)
            return -1.0f;
        try {
            return Float.parseFloat(valor);
        } catch (Exception e) {
            return -1.0f;
        }
    }

    public String toString() {
        return "=> " + id + " ## " + name + " ## " + releaseDate + " ## " + estimatedOwners + " ## " + price +
                " ## " + arrayToString(supportedLanguages) + " ## " + metacriticScore + " ## " + userScore +
                " ## " + achievements + " ## " + arrayToString(publishers) + " ## " + arrayToString(developers) +
                " ## " + arrayToString(categories) + " ## " + arrayToString(genres) + " ## " + arrayToString(tags)
                + " ##";
    }

    private String arrayToString(String[] array) {
        if (array == null || array.length == 0)
            return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
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

    public void add(Game jogo) {
        if (tamanho >= dados.length) {
            Game[] novosDados = new Game[dados.length * 2];
            for (int i = 0; i < dados.length; i++)
                novosDados[i] = dados[i];
            dados = novosDados;
        }
        dados[tamanho++] = jogo;
    }

    public Game get(int indice) {
        return (indice < 0 || indice >= tamanho) ? null : dados[indice];
    }

    public int size() {
        return tamanho;
    }
}

class CelulaPilha {
    public Game jogo;
    public CelulaPilha proximo;

    public CelulaPilha(Game jogo) {
        this.jogo = jogo;
        this.proximo = null;
    }
}

class PilhaGame {
    private CelulaPilha topo;

    public PilhaGame() {
        topo = null;
    }

    public boolean vazia() {
        return topo == null;
    }

    public void empilhar(Game jogo) {
        CelulaPilha novaCelula = new CelulaPilha(jogo);
        novaCelula.proximo = topo;
        topo = novaCelula;
    }

    public Game desempilhar() {
        if (vazia())
            return null;
        Game jogo = topo.jogo;
        topo = topo.proximo;
        return jogo;
    }

    public void mostrar() {
        mostrarRecursivo(topo, contarElementos() - 1);
    }

    private int contarElementos() {
        int contador = 0;
        CelulaPilha atual = topo;
        while (atual != null) {
            contador++;
            atual = atual.proximo;
        }
        return contador;
    }

    private void mostrarRecursivo(CelulaPilha celula, int posicao) {
        if (celula != null) {
            mostrarRecursivo(celula.proximo, posicao - 1);
            System.out.println("[" + posicao + "] " + celula.jogo.toString());
        }
    }
}

public class TP06Q03 {

    private static String trimManual(String str) {
        if (str == null || str.length() == 0)
            return str;
        int inicio = 0;
        int fim = str.length() - 1;
        while (inicio <= fim && (str.charAt(inicio) == ' ' || str.charAt(inicio) == '\t' ||
                str.charAt(inicio) == '\n' || str.charAt(inicio) == '\r'))
            inicio++;
        while (fim >= inicio && (str.charAt(fim) == ' ' || str.charAt(fim) == '\t' ||
                str.charAt(fim) == '\n' || str.charAt(fim) == '\r'))
            fim--;
        if (inicio > fim)
            return "";
        return str.substring(inicio, fim + 1);
    }

    public static Game buscarPorId(GameList jogos, int id) {
        for (int i = 0; i < jogos.size(); i++) {
            if (jogos.get(i).getId() == id)
                return jogos.get(i);
        }
        return null;
    }

    public static void main(String[] args) {
        String caminho = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0 ? "games.csv"
                : "/tmp/games.csv";
        GameList todosJogos = Game.lerCSV(caminho);
        PilhaGame pilha = new PilhaGame();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String entrada = trimManual(scanner.nextLine());
            if (entrada.equals("FIM"))
                break;
            try {
                int id = Integer.parseInt(entrada);
                Game jogo = buscarPorId(todosJogos, id);
                if (jogo != null)
                    pilha.empilhar(jogo);
            } catch (NumberFormatException e) {
            }
        }

        int numeroOperacoes = Integer.parseInt(trimManual(scanner.nextLine()));

        for (int i = 0; i < numeroOperacoes; i++) {
            String comando = trimManual(scanner.nextLine());
            if (comando.startsWith("I")) {
                String[] partes = comando.split(" ");
                if (partes.length > 1) {
                    int id = Integer.parseInt(trimManual(partes[1]));
                    Game jogo = buscarPorId(todosJogos, id);
                    if (jogo != null)
                        pilha.empilhar(jogo);
                }
            } else if (comando.startsWith("R")) {
                Game removido = pilha.desempilhar();
                if (removido != null)
                    System.out.println("(R) " + removido.getName());
            }
        }

        pilha.mostrar();
        scanner.close();
    }
}