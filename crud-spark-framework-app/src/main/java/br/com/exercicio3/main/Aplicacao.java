package br.com.exercicio3.main;

import br.com.exercicio3.dao.ProdutoDAO;
import br.com.exercicio3.model.Produto;
import static spark.Spark.*;
import java.math.BigDecimal;

public class Aplicacao {

    private static ProdutoDAO produtoDAO = new ProdutoDAO();

    public static void main(String[] args) {
        // Define a porta em que o servidor irá rodar
        port(4567);

        // Configura o local dos arquivos estáticos (nosso form.html)
        staticFiles.location("/public");

        // --- DEFINIÇÃO DAS ROTAS ---

        // Rota para exibir o formulário de inserção (redireciona para o arquivo estático)
        get("/produto/inserir", (request, response) -> {
            response.redirect("/html/form.html");
            return null;
        });

        // Rota para processar o formulário de inserção (método POST)
        post("/produto/inserir", (request, response) -> {
            Produto produto = new Produto();
            produto.setNome(request.queryParams("nome"));
            // Converte os dados do formulário (String) para os tipos corretos
            produto.setPreco(new BigDecimal(request.queryParams("preco")));
            produto.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));

            produtoDAO.inserir(produto);
            response.redirect("/produto/listar"); // Redireciona para a lista após inserir
            return null;
        });

        // Rota para listar todos os produtos
        get("/produto/listar", (request, response) -> {
            // Gera o HTML da tabela de produtos dinamicamente
            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Lista de Produtos</title>");
            html.append("<style>table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; }</style>");
            html.append("</head><body><h1>Produtos Cadastrados</h1>");
            html.append("<a href='/produto/inserir'>+ Adicionar Novo Produto</a><br><br>");
            html.append("<table><tr><th>ID</th><th>Nome</th><th>Preço</th><th>Qtd</th><th>Ações</th></tr>");

            for (Produto p : produtoDAO.listar()) {
                html.append("<tr>");
                html.append("<td>").append(p.getId()).append("</td>");
                html.append("<td>").append(p.getNome()).append("</td>");
                html.append("<td>").append(p.getPreco()).append("</td>");
                html.append("<td>").append(p.getQuantidade()).append("</td>");
                // Links para atualizar e excluir
                html.append("<td><a href='/produto/atualizar/").append(p.getId()).append("'>Atualizar</a> | ");
                html.append("<a href='/produto/excluir/").append(p.getId()).append("' onclick='return confirm(\"Tem certeza?\");'>Excluir</a></td>");
                html.append("</tr>");
            }

            html.append("</table></body></html>");
            return html.toString();
        });
        
        // Rota para exibir o formulário de atualização pré-preenchido
        get("/produto/atualizar/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produto = produtoDAO.getById(id);

            if (produto == null) {
                return "Produto não encontrado!";
            }
            
            // Gera o formulário HTML dinamicamente com os valores do produto
            String form = "<html><body><h1>Atualizar Produto</h1>"
                        + "<form action='/produto/atualizar/" + id + "' method='post'>"
                        + "Nome: <input type='text' name='nome' value='" + produto.getNome() + "'><br>"
                        + "Preço: <input type='number' step='0.01' name='preco' value='" + produto.getPreco() + "'><br>"
                        + "Qtd: <input type='number' name='quantidade' value='" + produto.getQuantidade() + "'><br>"
                        + "<input type='submit' value='Atualizar'>"
                        + "</form></body></html>";
            return form;
        });

        // Rota para processar a atualização de um produto
        post("/produto/atualizar/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Produto produto = produtoDAO.getById(id);
            produto.setNome(request.queryParams("nome"));
            produto.setPreco(new BigDecimal(request.queryParams("preco")));
            produto.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));
            
            produtoDAO.atualizar(produto);
            response.redirect("/produto/listar");
            return null;
        });

        // Rota para excluir um produto
        get("/produto/excluir/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            produtoDAO.excluir(id);
            response.redirect("/produto/listar");
            return null;
        });
    }
}