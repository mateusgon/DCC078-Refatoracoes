<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@include file="jspf/cabecalho.jspf" %>
    <div class="container text-center">
        <h1> Cadastre um restaurante </h1>
    </div>
     <div class="container">
            <form action="FrontController?action=CadastrarRestaurantePost" method="post">
                <div class="form-group">
                    <label for="usr">Nome:</label>
                    <input name="nome" class="form-control" required>     
                </div>

                <div class="form-group">
                    <label for="usr">Nome Fantasia</label>
                    <input type="text" name="nomeFantasia" class="form-control" required>     
                </div>

                <div class="form-group">
                    <label for="usr">Sigla</label>
                    <input  type="text" name="sigla" class="form-control" required>     
                </div>
                
                <div class="form-group">
                    <label for="usr">Telefone:</label>
                    <input type = "phone" name="telefone" class="form-control" required>     
                </div>
                
                <div class="form-group">
                    <label for="usr">Endere�o:</label>
                    <input  type = "text"  name="endereco" class="form-control" required>     
                </div>
                
              
               
                

                <input class="btn btn-primary btn" type ="Submit" value="Enviar"/> 
                <input class="btn btn-primary btn" type ="Reset" value ="Limpar" /> 
            </form>
</div>
<%@include file="jspf/rodape.jspf" %>