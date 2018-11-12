<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Faça seu login no sistema </h1>
</div>

<div class="container text-center ">
    <div class="col-xs-3 col-centered" >
        <div class="card">
            <div class="card-body">
                <form action="FrontController?action=LoginPost" method="post">

                    <div class="form-group">
                        <label for="usr">Email:</label>
                        <input name="nome" class="form-control" required>     
                    </div>

                    <div class="form-group">
                        <label for="usr">Senha:</label>
                        <input type="password" name="password" class="form-control" required>     
                    </div>

                    <input type ="hidden" name="identificador" value="3"/>
                    <input class="btn btn-primary btn" type ="Submit" value="Enviar"/> 
                    <input class="btn btn-primary btn" type ="Reset" value ="Limpar" /> 
                </form>

            </div>
        </div>
    </div>
</div>

<%@include file="jspf/rodape.jspf" %>