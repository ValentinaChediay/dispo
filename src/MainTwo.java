import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainTwo {
  public static void main(String[] args) throws IOException {
    String pathReader = "src/res/dispo.txt";
    List<String> fileLines = Files.readAllLines(Paths.get(pathReader));

    DispoCleaner dispoCleaner = new DispoCleaner();
    List<String> newDispo = dispoCleaner.clearDispo(fileLines);

    for(String s : newDispo) {
      System.out.println(s);
    }
  }
}
