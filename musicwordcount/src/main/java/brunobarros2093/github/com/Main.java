package brunobarros2093.github.com;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import brunobarros2093.github.com.Nirvana.LyricAnalyzer;

public class Main {
  public static void main(String[] args) {
    try {
      // Conecta à página de músicas mais acessadas do Nirvana
      Document doc = Jsoup.connect("https://www.letras.mus.br/nirvana/mais_acessadas.html").get();

      // Seleciona os elementos <li> com as classes "songList-table-row --song" que
      // contêm um <a> dentro
      Elements songLinks = doc.select("li.songList-table-row.--song a");

      int totalGunCount = 0;
      int totalGunsCount = 0;
      int noOccurrenceCount = 0;

      // Itera sobre os links encontrados
      for (Element link : songLinks) {
        String songUrl = "https://www.letras.mus.br" + link.attr("href"); // URL completa da música
        String songTitle = link.text(); // Título da música

        System.out.println("Título: " + songTitle);
        System.out.println("URL: " + songUrl);

        // Acessa a página da música para capturar a letra e contar as ocorrências de
        // "gun" e "guns"
        int[] counts = LyricAnalyzer.getLyricsAndCountWords(songUrl, "gun", "guns");

        if (counts != null) {
          int gunCount = counts[0];
          int gunsCount = counts[1];

          if (gunCount == 0 && gunsCount == 0) {
            noOccurrenceCount++;
          }

          totalGunCount += gunCount;
          totalGunsCount += gunsCount;

          System.out.println("Ocorrências de 'gun': " + gunCount);
          System.out.println("Ocorrências de 'guns': " + gunsCount);
        } else {
          System.out.println("Letra não encontrada.");
        }

        System.out.println("--------------------------------------------------");
      }

      // Exibe os totais
      System.out.println("Total de vezes que 'gun' aparece: " + totalGunCount);
      System.out.println("Total de vezes que 'guns' aparece: " + totalGunsCount);
      System.out.println("Quantidade de músicas sem ocorrência de 'gun' ou 'guns': " + noOccurrenceCount);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}