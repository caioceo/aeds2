import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public float getPrice() {
        return price;
    }

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public float getUserScore() {
        return userScore;
    }

    public int getAchievements() {
        return achievements;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public String[] getDevelopers() {
        return developers;
    }

    public String[] getCategories() {
        return categories;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getTags() {
        return tags;
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
        if (str == null || str.length() == 0) {
            return str;
        }
        
        int inicio = 0;
        int fim = str.length() - 1;
        
        while (inicio <= fim && (str.charAt(inicio) == ' ' || str.charAt(inicio) == '\t' || 
               str.charAt(inicio) == '\n' || str.charAt(inicio) == '\r')) {
            inicio++;
        }
        
        while (fim >= inicio && (str.charAt(fim) == ' ' || str.charAt(fim) == '\t' || 
               str.charAt(fim) == '\n' || str.charAt(fim) == '\r')) {
            fim--;
        }
        
        if (inicio > fim) {
            return "";
        }
        
        return str.substring(inicio, fim + 1);
    }

    public static List<Game> lerCSV(String caminhoArquivo) {
        List<Game> jogos = new ArrayList<Game>();
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(caminhoArquivo));
            String linha = br.readLine();
            
            while ((linha = br.readLine()) != null) {
                try {
                    Game jogo = parseLinha(linha);
                    if (jogo != null) {
                        jogos.add(jogo);
                    }
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return jogos;
    }

    private static Game parseLinha(String linha) {
        List<String> campos = parseLinhaCSV(linha);
        
        if (campos.size() < 14) {
            return null;
        }

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

    private static List<String> parseLinhaCSV(String linha) {
        List<String> campos = new ArrayList<String>();
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;
        
        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            
            if (c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                campos.add(atual.toString());
                atual = new StringBuilder();
            } else {
                atual.append(c);
            }
        }
        
        campos.add(atual.toString());
        return campos;
    }

    private static int parseInt(String valor) {
        if (valor == null || valor.length() == 0) {
            return -1;
        }
        
        valor = trimManual(valor);
        
        if (valor.length() == 0) {
            return -1;
        }
        
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String formatarData(String dataStr) {
        if (dataStr == null || dataStr.length() == 0) {
            return "01/01/1900";
        }
        
        dataStr = trimManual(dataStr);
        
        if (dataStr.length() == 0) {
            return "01/01/1900";
        }
        
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
                if (dataStr.matches("\\d{4}")) {
                    return "01/01/" + dataStr;
                }
                return "01/01/1900";
            }
        }
    }

    private static int parseEstimatedOwners(String valor) {
        if (valor == null || valor.length() == 0) {
            return 0;
        }
        
        valor = trimManual(valor);
        
        if (valor.length() == 0) {
            return 0;
        }
        
        StringBuilder limpo = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c >= '0' && c <= '9') {
                limpo.append(c);
            }
        }
        
        if (limpo.length() == 0) {
            return 0;
        }
        
        try {
            return Integer.parseInt(limpo.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static float parsePreco(String valor) {
        if (valor == null || valor.length() == 0) {
            return 0.0f;
        }
        
        valor = trimManual(valor);
        
        if (valor.length() == 0) {
            return 0.0f;
        }
        
        String valorMinusculo = valor.toLowerCase();
        if (valorMinusculo.indexOf("free") >= 0) {
            return 0.0f;
        }
        
        try {
            return Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private static String[] parseArray(String valor) {
        if (valor == null || valor.length() == 0) {
            return new String[0];
        }
        
        valor = trimManual(valor);
        
        if (valor.length() == 0) {
            return new String[0];
        }
        
        if (valor.indexOf('[') == -1 || valor.indexOf(']') == -1) {
            return new String[0];
        }
        
        int inicio = valor.indexOf('[');
        int fim = valor.lastIndexOf(']');
        
        if (inicio >= fim) {
            return new String[0];
        }
        
        String conteudo = valor.substring(inicio + 1, fim);
        conteudo = trimManual(conteudo);
        
        if (conteudo.length() == 0) {
            return new String[0];
        }
        
        List<String> resultado = new ArrayList<String>();
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;
        
        for (int i = 0; i < conteudo.length(); i++) {
            char c = conteudo.charAt(i);
            
            if (c == '\'' || c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                String item = trimManual(atual.toString());
                if (item.length() > 0) {
                    resultado.add(item);
                }
                atual = new StringBuilder();
            } else {
                atual.append(c);
            }
        }
        
        String ultimoItem = trimManual(atual.toString());
        if (ultimoItem.length() > 0) {
            resultado.add(ultimoItem);
        }
        
        String[] arrayResultado = new String[resultado.size()];
        for (int i = 0; i < resultado.size(); i++) {
            arrayResultado[i] = resultado.get(i);
        }
        
        return arrayResultado;
    }

    private static String[] parseListaSimples(String valor) {
        if (valor == null || valor.length() == 0) {
            return new String[0];
        }
        
        valor = trimManual(valor);
        
        if (valor.length() == 0) {
            return new String[0];
        }
        
        List<String> resultado = new ArrayList<String>();
        StringBuilder atual = new StringBuilder();
        
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            
            if (c == ',') {
                String item = trimManual(atual.toString());
                if (item.length() > 0) {
                    resultado.add(item);
                }
                atual = new StringBuilder();
            } else {
                atual.append(c);
            }
        }
        
        String ultimoItem = trimManual(atual.toString());
        if (ultimoItem.length() > 0) {
            resultado.add(ultimoItem);
        }
        
        String[] arrayResultado = new String[resultado.size()];
        for (int i = 0; i < resultado.size(); i++) {
            arrayResultado[i] = resultado.get(i);
        }
        
        return arrayResultado;
    }

    private static float parseUserScore(String valor) {
        if (valor == null || valor.length() == 0) {
            return -1.0f;
        }
        
        valor = trimManual(valor);
        
        if (valor.length() == 0) {
            return -1.0f;
        }
        
        String valorMinusculo = valor.toLowerCase();
        if (valorMinusculo.indexOf("tbd") >= 0) {
            return -1.0f;
        }
        
        try {
            return Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=> ").append(id);
        sb.append(" ## ").append(name);
        sb.append(" ## ").append(releaseDate);
        sb.append(" ## ").append(estimatedOwners);
        sb.append(" ## ").append(price);
        sb.append(" ## ").append(arrayToStringSimple(supportedLanguages));
        sb.append(" ## ").append(metacriticScore);
        sb.append(" ## ").append(userScore);
        sb.append(" ## ").append(achievements);
        sb.append(" ## ").append(arrayToStringSimple(publishers));
        sb.append(" ## ").append(arrayToStringSimple(developers));
        sb.append(" ## ").append(arrayToStringSimple(categories));
        sb.append(" ## ").append(arrayToStringSimple(genres));
        sb.append(" ## ").append(arrayToStringSimple(tags));
        sb.append(" ##");
        return sb.toString();
    }

    private String arrayToStringSimple(String[] array) {
        if (array == null || array.length == 0) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

class CelulaFila {
    public Game jogo;
    public CelulaFila proximo;
    
    public CelulaFila(Game jogo) {
        this.jogo = jogo;
        this.proximo = null;
    }
}

class FilaGame {
    private CelulaFila primeiro;
    private CelulaFila ultimo;
    private int len;
    
    public FilaGame() {
        primeiro = new CelulaFila(null);
        ultimo = primeiro;
        len = 0;
    }
    
    public boolean vazia() {
        return primeiro == ultimo;
    }
    
    public void enfileirar(Game jogo) {
        ultimo.proximo = new CelulaFila(jogo);
        ultimo = ultimo.proximo;
        len++;
    }
    
    public Game desenfileirar() {
        if (vazia()) {
            return null;
        }
        
        CelulaFila tmp = primeiro.proximo;
        Game jogo = tmp.jogo;
        primeiro.proximo = tmp.proximo;
        
        if (primeiro.proximo == null) {
            ultimo = primeiro;
        }
        
        len--;
        return jogo;
    }
    
    public void mostrar() {
        int posicao = 0;
        CelulaFila atual = primeiro.proximo;
        while (atual != null) {
            System.out.println("[" + posicao + "] " + atual.jogo.toString());
            atual = atual.proximo;
            posicao++;
        }
    }
    
    public int getLen() {
        return len;
    }
}

public class TP06Q04 {
    
    private static String trimManual(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        int inicio = 0;
        int fim = str.length() - 1;
        
        while (inicio <= fim && (str.charAt(inicio) == ' ' || str.charAt(inicio) == '\t' || 
               str.charAt(inicio) == '\n' || str.charAt(inicio) == '\r')) {
            inicio++;
        }
        
        while (fim >= inicio && (str.charAt(fim) == ' ' || str.charAt(fim) == '\t' || 
               str.charAt(fim) == '\n' || str.charAt(fim) == '\r')) {
            fim--;
        }
        
        if (inicio > fim) {
            return "";
        }
        
        return str.substring(inicio, fim + 1);
    }
    
    public static Game buscarPorId(List<Game> jogos, int id) {
        for (int i = 0; i < jogos.size(); i++) {
            if (jogos.get(i).getId() == id) {
                return jogos.get(i);
            }
        }
        return null;
    }
    
    private static String obterCaminhoArquivo() {
        String sistemaOperacional = System.getProperty("os.name").toLowerCase();
        
        if (sistemaOperacional.indexOf("win") >= 0) {
            return "games.csv";
        } else {
            return "/tmp/games.csv";
        }
    }
    
    public static void main(String[] args) {
        String caminhoArquivo = obterCaminhoArquivo();
        
        List<Game> todosJogos = Game.lerCSV(caminhoArquivo);
        FilaGame fila = new FilaGame();
        
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine();
            entrada = trimManual(entrada);
            
            if (entrada.equals("FIM")) {
                break;
            }
            
            try {
                int id = Integer.parseInt(entrada);
                Game jogo = buscarPorId(todosJogos, id);
                if (jogo != null) {
                    fila.enfileirar(jogo);
                }
            } catch (NumberFormatException e) {
            }
        }
        
        int numeroOperacoes = Integer.parseInt(trimManual(scanner.nextLine()));
        
        for (int i = 0; i < numeroOperacoes; i++) {
            String comando = scanner.nextLine();
            comando = trimManual(comando);
            
            if (comando.startsWith("I")) {
                String[] partes = comando.split(" ");
                if (partes.length > 1) {
                    int id = Integer.parseInt(trimManual(partes[1]));
                    Game jogo = buscarPorId(todosJogos, id);
                    if (jogo != null) {
                        fila.enfileirar(jogo);
                    }
                }
            } else if (comando.startsWith("R")) {
                Game removido = fila.desenfileirar();
                if (removido != null) {
                    System.out.println("(R) " + removido.getName());
                }
            }
        }
        
        fila.mostrar();
        
        scanner.close();
    }
}