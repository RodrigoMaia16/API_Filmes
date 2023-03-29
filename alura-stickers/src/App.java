import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.lang.System;

public class App {
    public static void main(String[] args) throws Exception {      
        // Fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair os dados que nos interessam (título, Poster(img), classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body); 

        // Exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[1mTítulo: \u001b[m"+ "\u001b[33m" + filme.get("title") + "\u001b[m");
            System.out.println("\u001b[1mURL do Pôster: \u001b[m" + "\u001b[33m" + filme.get("image") + "\u001b[m");
            
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroClaquetes = (int) classificacao;
            System.out.println("\u001b[1mAvaliação: \u001b[m" + "\u001b[33m" + filme.get("imDbRating") + "\u001b[m");
            for (int n = 1; n <= numeroClaquetes; n++) {
                if (classificacao >= 9){
                    System.out.print("🤩");
                } else if (classificacao >=8 && classificacao < 9){
                    System.out.print("😄");
                } else if (classificacao > 6 && classificacao < 8){
                    System.out.print("🙂");
                } else if (classificacao >= 5 && classificacao <= 6){
                    System.out.print("😐");
                } else if (classificacao >= 3 && classificacao < 5){
                    System.out.print("😬");
                } else {
                    System.out.print("😴");
                }
            }
            System.out.println("\n");
        }

    }
}

// 3º desafio: não é legal a chave estar colada na url, por motivos de segurança, esconder a chave, colocar ela em um arquivo de config ou numa variavel de ambiente
// Colocar a chave da API do IMDB em algum lugar fora do código como um arquivo de configuração (p. ex, um arquivo .properties) ou uma variável de ambiente. Confira a resolução do desafio aqui