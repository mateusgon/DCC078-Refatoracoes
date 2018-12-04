package EscalonamentoDePedido;

import java.util.HashMap;
import java.util.Map;

public class FuncionarioFactory {

    public static Funcionario instanciaFuncionario(Integer codigoTipoPessoa) {
        Map<Integer, String> tipos = new HashMap<>();
        tipos.put(1, "ChefeEasy");
        tipos.put(2, "ChefeMedium");
        tipos.put(3, "ChefeHard");
        String clazzName = tipos.get(codigoTipoPessoa);
        Funcionario tipoPedido = (Funcionario) FuncionarioFactory.create(clazzName);
        return tipoPedido;

    }

    private static Funcionario create(String tipoFuncionario) {
        Funcionario tipoFuncionarioObject = null;
        String nomeClasse = "EscalonamentoDePedido." + tipoFuncionario;
        Class classe = null;
        Object objeto = null;
        try {
            classe = Class.forName(nomeClasse);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            return null;
        }
        if (!(objeto instanceof Funcionario)) {
            return null;
        }
        tipoFuncionarioObject = (Funcionario) objeto;
        return tipoFuncionarioObject;
    }
}
