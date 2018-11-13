package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Mensagem;

public class MensagemDAO {

    private static final MensagemDAO instance = new MensagemDAO();
    private PreparedStatement insereMensagem;
    private PreparedStatement buscaMensagem;
    private PreparedStatement excluirMensagem;

    public static MensagemDAO getInstance() {
        return instance;
    }

    private MensagemDAO() {

    }

    public void saveMensagem(Mensagem mensagem) throws ClassNotFoundException, SQLException {
        insereMensagem = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into mensagem (mensagem, pessoacod) values (?, ?)");
        insereMensagem.clearParameters();
        insereMensagem.setString(1, mensagem.getMensagem());
        insereMensagem.setInt(2, mensagem.getIdReceptor());
        insereMensagem.execute();
    }

    public List<Mensagem> searchMensagem(Integer pessoacod) throws ClassNotFoundException, SQLException {
        List<Mensagem> mensagens = new ArrayList<>();
        buscaMensagem = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from mensagem where pessoacod = ?");
        buscaMensagem.clearParameters();
        buscaMensagem.setInt(1, pessoacod);
        ResultSet resultado = buscaMensagem.executeQuery();
        while (resultado.next()) {
            Mensagem mensagem = new Mensagem();
            mensagem = mensagem.setIdMensagem(resultado.getInt("mensagemcod")).setIdReceptor(resultado.getInt("pessoacod")).setMensagem(resultado.getString("mensagem"));
            mensagens.add(mensagem);
        }
        return mensagens;
    }
}
