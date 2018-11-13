<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Cardápio </h1>
</div>
<div class="container">

    <div class="form-group">
        <label for="usr"> Prato de Entrada </label>
        <select class="form-control" name="entrada" id="entrada">
            <c:forEach var="entradas" items="${entradas}">
                <option value="${entradas.codigo}"> ${entradas.nome} R$${entradas.valor} </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="usr"> Prato Principal </label>
        <select class="form-control" name="principal" id="principal">
            <c:forEach var="principais" items="${principais}">
                <option value="${principais.codigo}"> ${principais.nome} R$${principais.valor} </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="usr"> Bebidas </label>
        <select class="form-control" name="bebida" id="bebida">
            <c:forEach var="bebidas" items="${bebidas}">
                <option value="${bebidas.codigo}"> ${bebidas.nome} R$${bebidas.valor}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="usr"> Sobremesas </label>
        <select class="form-control" name="sobremesa" id="sobremesa">
            <c:forEach var="sobremesas" items="${sobremesas}">
                <option value="${sobremesas.codigo}"> ${sobremesas.nome} R$${sobremesas.valor} </option>
            </c:forEach>
        </select>
    </div>

    <table class="table table-bordered"  style="background-color: white">
        <thead>
            <tr>
                <th> Produto </th>
                <th> Valor </th>
                <th> Itens de Venda </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="combos"  items="${combos}">
                <tr>
                    <td>${combos.nome}</td>
                    <td>R$${combos.valor}</td>
                    <td><a href="FrontController?action=VerCombo&id=${combos.codigo}&id2=${idRest}"> Ver itens do combo </a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>
<%@include file="jspf/rodape.jspf" %>