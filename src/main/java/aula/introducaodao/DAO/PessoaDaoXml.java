package aula.introducaodao.DAO;

import aula.introducaodao.Modelo.Pessoa;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDaoXml implements PessoaDAOInterface{
    private Document doc;
    private String caminho;
    public PessoaDaoXml(String caminho)throws ErroDAO
    {
        this.caminho=caminho;
        try {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder construtor = fabrica.newDocumentBuilder();
            doc = construtor.parse(caminho);
        }catch (ParserConfigurationException | SAXException |IOException e)
        {
            throw new ErroDAO(e);
        }
    }
    @Override
    public void inserir(Pessoa p) throws ErroDAO {
        p.setId(proximoSerial());
        Element noPessoa=doc.createElement("pessoa");

        Element noNome=doc.createElement("nome");
        Element noIdade=doc.createElement("idade");
        //noId.appendChild(doc.createTextNode(Integer.toString(p.getId())));
        noPessoa.setAttribute("id",Integer.toString(p.getId()));
        noPessoa.setIdAttribute("id",true);
        noNome.setTextContent(p.getNome());
        noIdade.setTextContent(Integer.toString(p.getIdade()));

        noPessoa.appendChild(noNome);
        noPessoa.appendChild(noIdade);
        doc.getDocumentElement().appendChild(noPessoa);
        serealizar();
    }

    @Override
    public void deletar(Pessoa p) throws ErroDAO {

    }

    @Override
    public void deletar(int id) throws ErroDAO {
        NodeList noPessoas = doc.getElementsByTagName("pessoa");
        int tam = noPessoas.getLength();
        for (int i = 0; i < tam; i++) {
            Element noPessoa = (Element) noPessoas.item(i);
            if (noPessoa.getAttribute("id").equals(""+id)){
                noPessoa.getParentNode().removeChild(noPessoa);
                serealizar();
            }
        }
    }

    @Override
    public void editar(Pessoa p) throws ErroDAO {
        NodeList noPessoas = doc.getElementsByTagName("pessoa");
        int tam = noPessoas.getLength();
        for (int i = 0; i < tam; i++) {
            Element noPessoa = (Element) noPessoas.item(i);
            if (noPessoa.getAttribute("id").equals(""+p.getId())){
                noPessoa.getElementsByTagName("nome").item(0).setTextContent(p.getNome());
                noPessoa.getElementsByTagName("idade").item(0).setTextContent(""+p.getIdade());
                serealizar();
            }
        }
    }

    @Override
    public Pessoa buscar(int id) throws ErroDAO {
        return null;
    }

    @Override
    public List<Pessoa> buscar() throws ErroDAO {
        List<Pessoa> pessoas=new ArrayList<>();
        NodeList noListaPessoas= doc.getElementsByTagName("pessoa");
        int tam=noListaPessoas.getLength();
        for(int i=0;i<tam;i++)
        {
            Element noPessoa=(Element) noListaPessoas.item(i);
            Pessoa p=new Pessoa();
            p.setId(Integer.parseInt(noPessoa.getAttribute("id")));
            p.setNome(noPessoa.getElementsByTagName("nome").item(0).getTextContent());
            p.setIdade(Integer.parseInt(noPessoa.getElementsByTagName("idade").item(0).getTextContent()));
            pessoas.add(p);
        }
        return  pessoas;
    }

    @Override
    public void close() throws ErroDAO {

    }
    private int proximoSerial(){
        Integer serial= Integer.parseInt(doc.getDocumentElement().getAttribute("serial"));
        serial++;
        doc.getDocumentElement().setAttribute("serial",serial.toString());
        return serial;
    }
    private void serealizar() throws ErroDAO {
        try {
            TransformerFactory fabrica = TransformerFactory.newInstance();
            Transformer transformador = fabrica.newTransformer();
            DOMSource fonte = new DOMSource(doc);
            StreamResult saida = new StreamResult(new File(caminho));
            transformador.transform(fonte, saida);
        }catch (TransformerException e)
        {
            throw new ErroDAO(e);
        }
    }
}
