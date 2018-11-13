<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Histórico do pedido </h1>
</div>
<div class="container">
    <table class="table table-bordered"  style="background-color: white">
        <thead>
            <tr>
                <th> Estado </th>
                <th> Data </th>
                <th> Atual </th>
                <th> Reverter Pedidos </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="pedidos"  items="${memento}">
                <tr>
                    <td>${pedidos.nomeEstado}</td>
                    <td>${pedidos.dataModificacao}</td>
                    <td>${pedidos.atual}</td>
                    <td><a href="FrontController?action=ReverterPedido&id=${pedidos.mementoCod}"> Reverter </a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="jspf/rodape.jspf" %>