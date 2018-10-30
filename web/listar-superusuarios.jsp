<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Lista de super-usu�rios cadastrados por restaurante </h1>
</div>
<div class="container">
    <table class="table table-bordered"  style="background-color: white">
        <thead>
            <tr>
                <th> Restaurante </th>
                <th> Super-usu�rio </th>
                <th> Editar Super-Usu�rio </th>
                <th> Excluir Super-Usu�rio </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="restaurantes"  items="${restaurantes}">
                <tr>
                    <c:forEach var="superusuarios" items="${restaurantes.superUsuarios}">
                        <td>${restaurantes.nome}</td>
                        <td>${superusuarios.nome}</td>
                        <td><a href="FrontController?action=#&id=${restaurantes.restaurantecod}&id2=${superusuarios.pessoaCod}"> Editar  </a></td>
                        <td><a href="FrontController?action=#&id=${restaurantes.restaurantecod}&id2=${superusuarios.pessoaCod}"> Excluir</a></td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="jspf/rodape.jspf" %>