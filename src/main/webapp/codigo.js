function ajax(recurso,dados,funcao,metodo)
{
    const xhr=new XMLHttpRequest();
    xhr.onreadystatechange=funcao;
    if(metodo.toLowerCase()==="get") {
        recurso += "?"+ dados;
        xhr.open(metodo, recurso);
        xhr.send();
    }else if(metodo.toLowerCase()==="post")
    {
        xhr.open(metodo, recurso);
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xhr.send(dados);
    }
}
onload=()=>
{
    limpaCampos();
    buscaPessoas();
}
function buscaPessoas(){
    ajax("buscar",`tipo=${document.querySelector("input:checked").value}`,function (){
        if(this.readyState==4 && this.status==200)
        {
            mostrarDados(this.responseXML)
        }
    },"get");
}
function mostrarDados(doc)
{
    const pessoas=doc.documentElement.getElementsByTagName("pessoa");
    let texto="";
    for(let p of pessoas)
    {

        const id=p.getElementsByTagName("id")[0].textContent;
        const nome = p.getElementsByTagName("nome")[0].textContent
        const idade = p.getElementsByTagName("idade")[0].textContent

        texto+=`<tr>
                <td>${id}</td>
                <td>${nome}</td>
                <td>${idade}</td>
                <td><button onclick="preencheEditar(${id}, '${nome}', ${idade})">Editar</button></td>
                <td><button onclick="deletar(${id})">Deletar</button></td>
           </tr>`;
    }
    document.querySelector("tbody").innerHTML=texto;
}

function limpaCampos(){
    document.getElementById("id").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("idade").value = "";
    document.getElementById("btCadastrar").value = "Cadastrar";
    document.getElementById("btCadastrar").onclick=(evento)=>{
        evento.preventDefault();
        const nome=document.getElementById("nome").value;
        const idade=document.getElementById("idade").value;
        const tipo=document.querySelector("input:checked").value;
        dados=`nome=${nome}&idade=${idade}&tipo=${tipo}`;
        ajax("inserir",dados,function (){
            if(this.readyState==4 && this.status==200)
            {
                buscaPessoas();
                alert(this.responseText);
            }
        },"post");
        limpaCampos();
    }
}

function deletar(id){
    const tipo=document.querySelector("input:checked").value;
    dados=`id=${id}&tipo=${tipo}`;
    ajax("deletar",dados,function (){
        if(this.readyState==4 && this.status==200)
        {
            buscaPessoas();
            alert(this.responseText);
        }
    },"get");
}

function preencheEditar(id,nome,idade){
    document.getElementById('id').value = id;
    document.getElementById('nome').value = nome;
    document.getElementById('idade').value = idade;
    document.getElementById("btCadastrar").value = "Editar";
    editar();
}

function editar(){
    document.getElementById("btCadastrar").onclick=(evento)=>{
        evento.preventDefault();
        const id = document.getElementById("id").value;
        const nome=document.getElementById("nome").value;
        const idade=document.getElementById("idade").value;
        const tipo=document.querySelector("input:checked").value;
        dados=`id=${id}&nome=${nome}&idade=${idade}&tipo=${tipo}`;
        ajax("editar",dados,function (){
            if(this.readyState==4 && this.status==200)
            {
                buscaPessoas();
                alert(this.responseText);
            }
        },"post");
        limpaCampos();
    }
}