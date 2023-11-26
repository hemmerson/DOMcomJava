package aula.introducaodao.Controle;

import aula.introducaodao.DAO.ErroDAO;
import aula.introducaodao.DAO.PessoaDAOInterface;
import aula.introducaodao.DAO.PessoaDaoJdbc;
import aula.introducaodao.DAO.PessoaDaoXml;
import aula.introducaodao.Modelo.Pessoa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/inserir")
public class Inserir extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String nome = request.getParameter("nome");
        String tidade = request.getParameter("idade");
        String tipo = request.getParameter("tipo");
        if (tipo == null) tipo = "banco";
        String caminho = getServletContext().getRealPath("/WEB-INF/Pessoa.xml");
        if (nome != null && !nome.isBlank() && tidade != null && !tidade.isBlank()) {
            Pessoa p = new Pessoa(nome, Integer.parseInt(tidade));
            try {
                PessoaDAOInterface dao;
                if (tipo.equals("banco"))
                    dao = new PessoaDaoJdbc();
                else
                    dao = new PessoaDaoXml(caminho);
                dao.inserir(p);
                //response.getWriter().println(p);
                out.println("Cadastrado com sucesso.");
                dao.close();
            } catch (ErroDAO e) {
                Logger.getLogger(Inserir.class.getName()).log(Level.SEVERE, "Erro ao cadastrar", e);
                out.println("Erro ao tentar cadastrar");
            }

        } else
            out.println("Informe o nome e a idade");
    }
}
