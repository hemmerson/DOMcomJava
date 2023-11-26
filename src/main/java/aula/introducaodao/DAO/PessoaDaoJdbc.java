package aula.introducaodao.DAO;

import aula.introducaodao.Modelo.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDaoJdbc implements PessoaDAOInterface{
    Connection con;
    public PessoaDaoJdbc() throws ErroDAO{
        con= FabricaConexao.pegaConexao();
    }
    @Override
    public void inserir(Pessoa p) throws ErroDAO {
        try {
            PreparedStatement stm=con.prepareStatement("insert into pessoa(nome,idade) values(?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1,p.getNome());
            stm.setInt(2,p.getIdade());
            stm.executeUpdate();
            ResultSet rs=stm.getGeneratedKeys();
            if(rs.next()){
                p.setId(rs.getInt(1));
            }
            stm.close();
        } catch (SQLException e) {
            throw new ErroDAO(e);
        }
    }

    @Override
    public void deletar(Pessoa p) throws ErroDAO {
        deletar(p.getId());
    }

    @Override
    public void deletar(int id) throws ErroDAO {
        String sql = "delete from pessoa where id=?";
        try (PreparedStatement pstm=con.prepareStatement(sql)){
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new ErroDAO(e);
        }
    }

    @Override
    public void editar(Pessoa p) throws ErroDAO {
        String sql = "update pessoa set nome=?,idade=? where id=?";
        try (PreparedStatement pstm=con.prepareStatement(sql)){
            pstm.setString(1,p.getNome());
            pstm.setInt(2, p.getIdade());
            pstm.setInt(3,p.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new ErroDAO(e);
        }
    }

    @Override
    public Pessoa buscar(int id) throws ErroDAO {
        try {
            PreparedStatement stm=con.prepareStatement("select * from pessoa where id=?");
            stm.setInt(1,id);
            ResultSet rs=stm.executeQuery();
            if (rs.next()){
                Pessoa p=new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setIdade(rs.getInt("idade"));
                return p;
            }
            else
                return null;
        } catch (SQLException e) {
            throw new ErroDAO(e);
        }
    }

    @Override
    public List<Pessoa> buscar() throws ErroDAO {
        List<Pessoa> pessoas=new ArrayList();
        try {
            PreparedStatement stm=con.prepareStatement("select * from pessoa");
            ResultSet rs=stm.executeQuery();
            while (rs.next()){
                Pessoa p=new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setIdade(rs.getInt("idade"));
                pessoas.add(p);
            }
        } catch (SQLException e) {
            throw new ErroDAO(e);
        }
        return pessoas;
    }

    @Override
    public void close() throws ErroDAO {
        try {
            con.close();
        }catch (Exception e)
        {
            throw new ErroDAO(e);
        }
    }
}
