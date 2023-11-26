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
import java.util.List;

@WebServlet("/buscar")
public class Buscar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out=response.getWriter();
        String tipo = request.getParameter("tipo");
        String caminho = getServletContext().getRealPath("/WEB-INF/Pessoa.xml");
        if (tipo == null) tipo = "xml";
            try {
                PessoaDAOInterface dao;
                if(tipo.equals("banco"))
                    dao=new PessoaDaoJdbc();
                else
                    dao=new PessoaDaoXml(caminho);
                List<Pessoa> pessoas=dao.buscar();
                out.println("<pessoas>");
                for (Pessoa p:pessoas)
                {
                    out.println("<pessoa>");
                    out.println("<id>"+p.getId()+"</id>");
                    out.println("<nome>"+p.getNome()+"</nome>");
                    out.println("<idade>"+p.getIdade()+"</idade>");
                    out.println("</pessoa>");
                }
                out.println("</pessoas>");
                dao.close();
        } catch (ErroDAO e) {
            out.println("<erro>Erro ao tentar criar o DAO</erro>");
        }
    }
}
