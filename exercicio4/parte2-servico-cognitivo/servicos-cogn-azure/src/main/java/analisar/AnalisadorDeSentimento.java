package analisar;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.TextSentiment;
import com.azure.core.credential.AzureKeyCredential;

public class AnalisadorDeSentimento {

    // SUBSTITUA PELOS SEUS DADOS OBTIDOS NO PORTAL AZURE
    private static final String CHAVE_COGNITIVA = "8cHEVxUrSbO0YchTdia0TzWGkA2KSjFFGvp5cVWookZ8MzW9yc2VJQQJ99BJACZoyfiXJ3w3AAAaACOGF50W";
    private static final String PONTO_DE_EXTREMIDADE = "https://servico-cognitivo-karen-ti2-ex4.cognitiveservices.azure.com/";

    public static void main(String[] args) {
        // Cria um cliente para se conectar ao serviço
        TextAnalyticsClient client = new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(CHAVE_COGNITIVA))
                .endpoint(PONTO_DE_EXTREMIDADE)
                .buildClient();

        // Texto a ser analisado (simulando o envio de um texto de perfil)
        String textoParaAnalise = "Estou muito feliz com os resultados do projeto e otimista com o futuro. "
                                + "A equipe trabalhou de forma colaborativa e eficiente. Foi uma experiência incrível!";

        System.out.println("Analisando o texto: \"" + textoParaAnalise + "\"");

        // Chama a API de análise de sentimento
        DocumentSentiment documentSentiment = client.analyzeSentiment(textoParaAnalise, "pt-BR");

        // Exibe os resultados
        TextSentiment sentimentoGeral = documentSentiment.getSentiment();
        System.out.println("-------------------------------------------");
        System.out.println("Sentimento Geral Detectado: " + sentimentoGeral);
        System.out.printf("Scores de Confiança:%n\tPositivo=%.4f%n\tNegativo=%.4f%n\tNeutro=%.4f%n",
                documentSentiment.getConfidenceScores().getPositive(),
                documentSentiment.getConfidenceScores().getNegative(),
                documentSentiment.getConfidenceScores().getNeutral());
        System.out.println("-------------------------------------------");
    }
}