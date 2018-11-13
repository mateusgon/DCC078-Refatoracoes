<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Combo ${combo.nome} </h1>
</div>
<div class="container">
    <table class="table table-bordered"  style="background-color: white">
        <thead>
            <tr>
                <th> Produto </th>
                <th> Valor </th>
                <th> Quantidade </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="combo"  items="${combo.itens}">
                <tr>
                    <td>${combo.nome}</td>
                    <td>R$${combo.valor}</td>
                    <td>${combo.quantidade}</td>
                </tr>
            </c:forEach>
            <c:forEach var="combos"  items="${combos}">
                <tr>
                    <td> ${combos.nome} </td>
                    <td><a href="FrontController?action=VerCombo&id=${combos.codigo}&id2=${idRest}"> Ver itens do combo </a></td>
                    <td> ${combos.quantidade} </td>
                </tr>
            </c:forEach>   
        </tbody>
    </table>

</div>
<%@include file="jspf/rodape.jspf" %>