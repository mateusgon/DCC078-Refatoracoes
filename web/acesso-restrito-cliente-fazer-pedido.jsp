<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="jspf/cabecalho.jspf" %>
<div class="container text-center">
    <h1> Pedido </h1>
</div>
<div class="container">
    <form action="FrontController?action=FazerPedidoPost" method="post">

        <fieldset name="entrada[]">
            <c:forEach var="entradas"  items="${entradas}">
                Prato de Entrada: ${entradas.nome} - Valor: R$${entradas.valor}
                <div class="form-group">
                    <label>    <input type = "numero" name="entrada" value="${entradas.codigo}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="entrada" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>

        <fieldset name="principal[]">
            <c:forEach var="principais"  items="${principais}">
                Prato Principal: ${principais.nome} - Valor: R$${principais.valor}
                <div class="form-group">
                    <label>    <input type = "numero" name="principal" value="${principais.codigo}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="principal" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>

        <fieldset name="bebidas[]">
            <c:forEach var="bebidas"  items="${bebidas}">
                Bebida: ${bebidas.nome} - Valor: R$${bebidas.valor}
                <div class="form-group">
                    <label>    <input type = "numero" name="bebida" value="${bebidas.codigo}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="bebida" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>

        <fieldset name="sobremesas[]">
            <c:forEach var="sobremesas"  items="${sobremesas}">
                Bebida: ${sobremesas.nome} - Valor: R$${sobremesas.valor}
                <div class="form-group">
                    <label>    <input type = "numero" name="sobremesa" value="${sobremesas.codigo}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="sobremesa" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>

        <fieldset name="combos[]">
            <c:forEach var="combos"  items="${combos}">
                Produto: ${combos.nome} - Valor: R$${combos.valor} <a href="FrontController?action=VerCombo&id=${combos.codigo}&id2=${idRest}"> Ver itens do combo </a>
                <div class="form-group">
                    <label>    <input type = "numero" name="combos" value="${combos.codigo}" class="form-control" readonly/>     </label>
                    <label>    <input type = "numero" name="combos" value="0" class="form-control" required/>  </label>
                </div>
            </c:forEach>
        </fieldset>


        <div class="form-group">
            <label for="usr"> Forma de pagamento </label>
            <select class="form-control" name="pagamento" id="pagamento">
                <option value="1"> Cartão de crédito </option>
                <option value="2"> Cartão de débito </option>
                <option value="3"> Dinheiro </option>
            </select>
        </div>

        <input type ="hidden" name="idRest" value="${idRest}">
        <input type ="hidden" name="idUsr" value="${idUsuario}">
        <input class="btn btn-primary btn" type ="Submit" value="Fazer Pedido"/> 
        <input class="btn btn-primary btn" type ="Reset" value ="Limpar" /> 
    </form>
</div>
<%@include file="jspf/rodape.jspf" %>

