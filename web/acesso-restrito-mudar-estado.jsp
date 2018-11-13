<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Modifique o estado de um pedido </h1>
</div>
<div class="container">
    <form action="FrontController?action=MudarEstadoPost" method="post">

        <div class="form-group">
            <label for="usr"> Estado do pedido </label> <br/>
            <label for="usr"> O estado do pedido é: ${estadoAtual} </label>

            <select class="form-control" name="estado" id="estado">
                <c:forEach var="estados"  items="${estados}">
                    <option value="${estados}"> ${estados} </option>
                </c:forEach>
            </select>
        </div>

        <input type ="hidden" name="idChefe" value="${idChefe}">
        <input type ="hidden" name="idPed" value="${idPed}">
        <input class="btn btn-primary btn" type ="Submit" value="Enviar"/> 
        <input class="btn btn-primary btn" type ="Reset" value ="Limpar" /> 
    </form>
</div>
<%@include file="jspf/rodape.jspf" %>