
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@include file="jspf/cabecalho.jspf" %> 
<div class="container text-center"> 
    <h1> Cadastre um combo </h1> 
</div> 
<div class="container"> 
    <form action="FrontController?action=CadastrarComboPost" method="post"> 
        <div class="form-group"> 
            <label for="usr">Nome:</label> 
            <input name="nome" class="form-control" required>      
        </div> 

        <div class="form-group"> 
            <label for="usr">Valor:</label> 
            <input type = "text" name="valor" class="form-control" required>      
        </div> 

        <fieldset name="produtos[]">
            <c:forEach var="produtos"  items="${produtos}">
                Produto: ${produtos.nome} - Valor: R$${produtos.valor}
                <div class="form-group">
                    <label>    <input type = "numero" name="produtos" value="${produtos.produtocod}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="produtos" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>

        <fieldset name="combos[]">
            <c:forEach var="combos"  items="${combos}">
                Produto: ${combos.nome} - Valor: R$${combos.valor}
                <div class="form-group">
                    <label>    <input type = "numero" name="combos" value="${combos.codigo}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="combos" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>

        <input type ="hidden" name="idRest" value="${idRest}"> 
        <input class="btn btn-primary btn" type ="Submit" value="Enviar"/>  
        <input class="btn btn-primary btn" type ="Reset" value ="Limpar" />  
    </form> 
</div> 
<%@include file="jspf/rodape.jspf" %> 
