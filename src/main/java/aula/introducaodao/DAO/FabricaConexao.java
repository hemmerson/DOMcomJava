package aula.introducaodao.DAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    public static Connection pegaConexao() throws ErroDAO{
        /*try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost/aula","aluno","aluno");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ErroDAO("Erro na Fabrica de conexão: "+e);
        }*/
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource)ic.lookup("ResourceAula");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new ErroDAO(e);
        }
    }
}
