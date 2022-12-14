import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        String pathWriter = "src/res/dispo_new.txt";
        String pathReader = "src/res/dispo.txt";

        FileWriter fileWriter = new FileWriter(pathWriter);

        List<String> fileLines = Files.readAllLines(Paths.get(pathReader));
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.toUpperCase().split("/|\\+| WITH | Y ");
            for (String s : splitedText) {
                String line = changeLine(s);
                if (!line.isEmpty()) {
                    fileWriter.write(line);
                    fileWriter.write("\n");
                }
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }

    private static String changeLine(String s) {
        // удаляю всё кроме букв и пробелов, удаляю пробелы в начале и конце
        s = s.replaceAll("[^A-Z-'-]|-", " ").trim().replaceAll("\\s+"," ");
        // удаляю запрещенные символы
        s = Arrays.stream(s.split(" ")).filter(Main::conditionForbiddenSymbols).collect(Collectors.joining(" "));
        // удаляю группы
        s = conditionGroups(s);
        return s;
    }

    // Вопросы
    // 1. что такое G: было 1 HB G HIGH & YELLOW 50 25 0,35 стало G HIGH  YELLOW
    // 2. как называется сорт, надо оставлять !: было CHERRY OH! 50 25 ( ) 2 стало CHERRY OH
    // 3. gardens roses  WILD CITY - это два разных сорта? было 3 hb mix 40 50 60  gardens roses  WILD CITY стало GARDENS ROSES WILD CITY
    // 4. CRASPEDIA, DIANTHUS - это группы?, ROSE SPRAY - так написали вместо группы SPRAY ROSA?, ALSTROEMERIA - так написали вместо группы ALSTROMERIA?

    // Опечатки
    // 1. имели ввиду MIX, написали MX? было MX COLOR 1HB 40  /1HB 50 стало MX COLOR
    // 2. опечатка вместо PIUMA DARK BLUE -> PIUM DRAK BLUE
    // 3. было boulevar+hot explorer 1hbx50/60/70 записала как два отдельных сорта: BOULEVAR, HOT EXPLORER HBX (но оставлось HBX, потому что слитно написали)
    // 4. забыли слеш SHIMMER FULLMONTY
    // 5. вместо high они написали h
    // 6. вместо With написали WHIT -> WHITE OHARA WHIT FREEDOM
    // 7. вместо hb, hn => осталось HN GOTCHA

    private static boolean conditionForbiddenSymbols(String s) {
        // запрещенные символы
        String[] forbiddenSymbols = {"HB", "QB", "EB", "SELECT", "CM", "CV", "BUNCHES", "X", "ST", "DE", "RAMOS", "MIX"};
        for (String forbiddenSymbol : forbiddenSymbols) {
            if (s.equals(forbiddenSymbol)) {
                return false;
            }
        }
        return true;
    }

    private static String conditionGroups(String s) {
        // группы
        String[] groups = {"ROSA ESTANDAR", "ROSA GARDEN", "ROSA TINTURADA", "CLAVEL", "ALSTROMERIA", "STOCK", "MINICLAVEL", "SPRAY ROSA", "OTROS", "LIMONIUM", "OZOTHAMNUS"};
        for (String group : groups) {
            if (s.equals(group)) {
                return "";
            }
        }
        return s;
    }
}
