package aula.introducaodao.Controle;

import aula.introducaodao.DAO.ErroDAO;
import aula.introducaodao.DAO.PessoaDAOInterface;
import aula.introducaodao.DAO.PessoaDaoJdbc;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pesquisar")
public class Pesquisar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PessoaDAOInterface dao=new PessoaDaoJdbc()){

           response.getWriter().println(dao.buscar(10));

        } catch (ErroDAO e) {
            throw new RuntimeException(e);
        }

    }
}
