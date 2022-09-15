import java.util.*;
import java.util.stream.Collectors;

public class DispoCleaner {
  // ����������� ������� (�����������)
  String[] forbiddenSymbols = {"HB", "QB", "EB", "SELECT", "FANCY", "CM", "CV", "BUNCHES", "X", "ST", "MIX"};
  // ������
  String[] groups = {"ROSA ESTANDAR", "ROSA GARDEN", "ROSA TINTURADA", "CLAVEL", "ALSTROMERIA", "STOCK", "MINICLAVEL", "SPRAY ROSA", "OTROS", "LIMONIUM", "OZOTHAMNUS"};
  // ��������
  Map<String, String> mapOfTypos = new HashMap<String, String>() {{
    put("hn", "hb");
    put("MX", "MIX");
    put("CRASPEDIA", "");
    put("ALSTROEMERIA", " ALSTROMERIA ");
    put("DIANTHUS", "");
    put("RAMOS", "");
    put("DE", "");
    put("h", "high");
    put("Explore", "Explorer");
  }};

  public List<String> clearDispo(List<String> dispo) {
    List<String> newDispo = new ArrayList<>();

    for (String dispoLine : dispo) {
      String[] splitedDispoLines = dispoLine.toUpperCase().split("/|\\+| WITH | Y ");
      for (String splitedDispoLine : splitedDispoLines) {
        String line = clearLine(splitedDispoLine);
        if (!line.isEmpty()) {
          newDispo.add(line);
        }
      }
    }

    return newDispo;
  }

  private String clearLine(String line) {
    // ��������� �� ����� ���� � ���������, ��������� ������ �������
    line = line.replaceAll("[^A-Z-'-]|-", " ").trim().replaceAll("\\s+", " ");
    // ������������ ��������
    line = correctionOfTypos(line);
    // ������ ����������� �������
    line = Arrays.stream(line.split(" ")).filter(this::checkForbiddenSymbols).collect(Collectors.joining(" "));
    // ��������� ������
    line = deleteGroups(line);
    return line;
  }

  private String correctionOfTypos(String line) {
    String[] splitedLine = line.split(" ");
    for (int i = 0; i < splitedLine.length; i++) {
      for (Map.Entry<String, String> entry : mapOfTypos.entrySet()) {
        if (splitedLine[i].equals(entry.getKey().toUpperCase())) {
          splitedLine[i] = splitedLine[i].replaceAll(entry.getKey().toUpperCase(), entry.getValue().toUpperCase());
        }
      }
    }
    return Arrays.stream(splitedLine).collect(Collectors.joining(" "));
  }

  private boolean checkForbiddenSymbols(String line) {
    for (String forbiddenSymbol : forbiddenSymbols) {
      if (line.equals(forbiddenSymbol)) {
        return false;
      }
    }
    return true;
  }

  private String deleteGroups(String line) {
    for (String group : groups) {
      if (line.equals(group)) {
        return "";
      }
    }
    return line;
  }
}
