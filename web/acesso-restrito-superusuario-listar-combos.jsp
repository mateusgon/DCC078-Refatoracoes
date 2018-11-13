<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Lista de combos cadastrados </h1>
</div>
<div class="container">
    <table class="table table-bordered"  style="background-color: white">
        <thead>
            <tr>
                <th> Produto </th>
                <th> Valor </th>
                <th> Itens de Venda </th>
                <th> Desativar </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="combos"  items="${combos}">
                <tr>
                    <td>${combos.nome}</td>
                    <td>R$${combos.valor}</td>
                    <td><a href="FrontController?action=VerCombo&id=${combos.codigo}&id2=${idRest}"> Ver itens do combo </a></td>
                    <td><a href="FrontController?action=DesativarCombo&id=${combos.codigo}&id2=${idRest}"> Desativar </a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="jspf/rodape.jspf" %>