package brunobarros2093.github.com.Nirvana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LyricAnalyzer {
    // Método para obter a lista de URLs das músicas a partir de letras.mus.br
    public static List<String> getSongsUrls(String baseUrl) {
        List<String> songUrls = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(baseUrl).get();
            // Seleciona os links das músicas (ajustar o seletor conforme a estrutura do
            // site)
            Elements songLinks = doc.select("li.songList-table-row.--song a div.lyric-original"); // Exemplo baseado em
                                                                                                  // letras.mus.br
            for (Element link : songLinks) {
                String songUrl = "https://www.letras.mus.br" + link.attr("href");
                songUrls.add(songUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songUrls;
    }

    // Método para obter a letra da música e contar as ocorrências de palavras
    // específicas
    public static int[] getLyricsAndCountWords(String url, String... words) {
        try {
            // Conecta à página da música
            Document doc = Jsoup.connect(url).get();

            // Seleciona a div com a classe "lyric-original" que contém a letra da música
            Element lyricsDiv = doc.selectFirst("div.lyric-original");

            if (lyricsDiv != null) {
                String lyrics = lyricsDiv.text().toLowerCase(); // Converte o texto para minúsculas para contagem

                // Inicializa o array para contar as ocorrências de cada palavra
                int[] counts = new int[words.length];

                // Conta as ocorrências de cada palavra
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].toLowerCase();
                    counts[i] = lyrics.split("\\b" + word + "\\b").length - 1; // Usa expressão regular para contagem
                                                                               // precisa
                }

                return counts; // Retorna o array com as contagens
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se a letra não for encontrada
    }
}