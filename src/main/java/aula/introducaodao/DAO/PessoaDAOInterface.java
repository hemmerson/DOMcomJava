package aula.introducaodao.DAO;

import aula.introducaodao.Modelo.Pessoa;

import java.util.List;

public interface PessoaDAOInterface extends AutoCloseable {
    public void inserir(Pessoa p) throws ErroDAO;
    public void deletar(Pessoa p) throws ErroDAO;
    public void deletar(int id) throws ErroDAO;
    public void editar(Pessoa p) throws ErroDAO;
    public Pessoa buscar(int id) throws ErroDAO;
    public List<Pessoa> buscar() throws ErroDAO;
    @Override
    void close() throws ErroDAO;
}
