package TPs.tp4;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    // Construtor padrão
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

    // Construtor com parâmetros
    public Game(int id, String name, String releaseDate, int estimatedOwners, float price,
                String[] supportedLanguages, int metacriticScore, float userScore, int achievements,
                String[] publishers, String[] developers, String[] categories, String[] genres, String[] tags) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.developers = developers;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Método trim manual
    private static String trimManual(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        int start = 0;
        int end = str.length() - 1;
        
        while (start <= end && (str.charAt(start) == ' ' || str.charAt(start) == '\t' || 
               str.charAt(start) == '\n' || str.charAt(start) == '\r')) {
            start++;
        }
        
        while (end >= start && (str.charAt(end) == ' ' || str.charAt(end) == '\t' || 
               str.charAt(end) == '\n' || str.charAt(end) == '\r')) {
            end--;
        }
        
        if (start > end) {
            return "";
        }
        
        return str.substring(start, end + 1);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(String[] supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public float getUserScore() {
        return userScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public String[] getDevelopers() {
        return developers;
    }

    public void setDevelopers(String[] developers) {
        this.developers = developers;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    // Método para obter o caminho correto do arquivo
    private static String getFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.indexOf("win") >= 0) {
            return "games.csv";
        } else {
            return "/tmp/games.csv";
        }
    }

    // Método para ler o CSV e criar objetos Game
    public static List<Game> readCSV(String filePath) {
        List<Game> games = new ArrayList<Game>();
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine(); // Pula o cabeçalho
            
            while ((line = br.readLine()) != null) {
                try {
                    Game game = parseLine(line);
                    if (game != null) {
                        games.add(game);
                    }
                } catch (Exception e) {
                    // Ignora erros silenciosamente
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
        
        return games;
    }

    // Método para fazer o parse de uma linha do CSV
    private static Game parseLine(String line) {
        List<String> fields = parseCSVLine(line);
        
        if (fields.size() < 14) {
            return null;
        }

        Game game = new Game();
        
        // AppID
        game.setId(parseInt(fields.get(0)));
        
        // Name
        game.setName(fields.get(1));
        
        // Release date
        game.setReleaseDate(formatDate(fields.get(2)));
        
        // Estimated owners
        game.setEstimatedOwners(parseEstimatedOwners(fields.get(3)));
        
        // Price
        game.setPrice(parsePrice(fields.get(4)));
        
        // Supported languages
        game.setSupportedLanguages(parseArray(fields.get(5)));
        
        // Metacritic score
        game.setMetacriticScore(parseInt(fields.get(6)));
        
        // User score
        game.setUserScore(parseUserScore(fields.get(7)));
        
        // Achievements
        game.setAchievements(parseInt(fields.get(8)));
        
        // Publishers
        game.setPublishers(parseSimpleList(fields.get(9)));
        
        // Developers
        game.setDevelopers(parseSimpleList(fields.get(10)));
        
        // Categories
        game.setCategories(parseSimpleList(fields.get(11)));
        
        // Genres
        game.setGenres(parseSimpleList(fields.get(12)));
        
        // Tags
        game.setTags(parseSimpleList(fields.get(13)));
        
        return game;
    }

    // Método para fazer o parse de uma linha CSV
    private static List<String> parseCSVLine(String line) {
        List<String> fields = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        fields.add(current.toString());
        return fields;
    }

    // Método para converter inteiro
    private static int parseInt(String value) {
        if (value == null || value.length() == 0) {
            return -1;
        }
        
        value = trimManual(value);
        
        if (value.length() == 0) {
            return -1;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Método para formatar data
    private static String formatDate(String dateStr) {
        if (dateStr == null || dateStr.length() == 0) {
            return "01/01/1900";
        }
        
        dateStr = trimManual(dateStr);
        
        if (dateStr.length() == 0) {
            return "01/01/1900";
        }
        
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            return outputFormat.format(date);
        } catch (ParseException e) {
            try {
                SimpleDateFormat inputFormat2 = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date date = inputFormat2.parse(dateStr);
                SimpleDateFormat outputFormat = new SimpleDateFormat("MM/yyyy");
                return "01/" + outputFormat.format(date);
            } catch (ParseException e2) {
                if (dateStr.matches("\\d{4}")) {
                    return "01/01/" + dateStr;
                }
                return "01/01/1900";
            }
        }
    }

    // Método para fazer parse do número estimado de jogadores
    private static int parseEstimatedOwners(String value) {
        if (value == null || value.length() == 0) {
            return 0;
        }
        
        value = trimManual(value);
        
        if (value.length() == 0) {
            return 0;
        }
        
        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c >= '0' && c <= '9') {
                cleaned.append(c);
            }
        }
        
        if (cleaned.length() == 0) {
            return 0;
        }
        
        try {
            return Integer.parseInt(cleaned.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Método para fazer parse do preço
    private static float parsePrice(String value) {
        if (value == null || value.length() == 0) {
            return 0.0f;
        }
        
        value = trimManual(value);
        
        if (value.length() == 0) {
            return 0.0f;
        }
        
        String valueLower = value.toLowerCase();
        if (valueLower.indexOf("free") >= 0) {
            return 0.0f;
        }
        
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    // Método para fazer parse de arrays entre colchetes com aspas simples
    private static String[] parseArray(String value) {
        if (value == null || value.length() == 0) {
            return new String[0];
        }
        
        value = trimManual(value);
        
        if (value.length() == 0) {
            return new String[0];
        }
        
        if (value.indexOf('[') == -1 || value.indexOf(']') == -1) {
            return new String[0];
        }
        
        int start = value.indexOf('[');
        int end = value.lastIndexOf(']');
        
        if (start >= end) {
            return new String[0];
        }
        
        String content = value.substring(start + 1, end);
        content = trimManual(content);
        
        if (content.length() == 0) {
            return new String[0];
        }
        
        List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean inQuote = false;
        
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            
            if (c == '\'' || c == '"') {
                inQuote = !inQuote;
            } else if (c == ',' && !inQuote) {
                String item = trimManual(current.toString());
                if (item.length() > 0) {
                    result.add(item);
                }
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        String lastItem = trimManual(current.toString());
        if (lastItem.length() > 0) {
            result.add(lastItem);
        }
        
        String[] arrayResult = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arrayResult[i] = result.get(i);
        }
        
        return arrayResult;
    }

    // Método para fazer parse de listas simples (separadas por vírgula)
    private static String[] parseSimpleList(String value) {
        if (value == null || value.length() == 0) {
            return new String[0];
        }
        
        value = trimManual(value);
        
        if (value.length() == 0) {
            return new String[0];
        }
        
        List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            
            if (c == ',') {
                String item = trimManual(current.toString());
                if (item.length() > 0) {
                    result.add(item);
                }
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        String lastItem = trimManual(current.toString());
        if (lastItem.length() > 0) {
            result.add(lastItem);
        }
        
        String[] arrayResult = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arrayResult[i] = result.get(i);
        }
        
        return arrayResult;
    }

    // Método para fazer parse do user score
    private static float parseUserScore(String value) {
        if (value == null || value.length() == 0) {
            return -1.0f;
        }
        
        value = trimManual(value);
        
        if (value.length() == 0) {
            return -1.0f;
        }
        
        String valueLower = value.toLowerCase();
        if (valueLower.indexOf("tbd") >= 0) {
            return -1.0f;
        }
        
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    // Método para buscar um jogo por ID
    public static Game findById(List<Game> games, int id) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId() == id) {
                return games.get(i);
            }
        }
        return null;
    }

    // Método toString no formato esperado
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

    // Método auxiliar para converter array em string no formato [item1, item2, item3]
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

    // Método main
    public static void main(String[] args) {
        String filePath = getFilePath();
        
        if (args.length > 0) {
            filePath = args[0];
        }
        
        // Carregar todos os jogos do CSV
        List<Game> games = Game.readCSV(filePath);
        
        // Ler entrada do usuário
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        
        try {
            while (true) {
                input = reader.readLine();
                
                if (input == null) {
                    break;
                }
                
                input = trimManual(input);
                
                if (input.equals("FIM")) {
                    break;
                }
                
                try {
                    int id = Integer.parseInt(input);
                    Game game = findById(games, id);
                    
                    if (game != null) {
                        System.out.println(game.toString());
                    }
                } catch (NumberFormatException e) {
                    // Ignora entradas inválidas
                }
            }
            
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}