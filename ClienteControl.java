import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.DoubleProperty;
import java.time.LocalDate;
import javafx.collections.ObservableList;

public class ClienteControl {
    private ObservableList<Cliente> lista = 
    FXCollections.observableArrayList();

    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private DoubleProperty limiteCredito = new SimpleDoubleProperty(0.0);
    private ObjectProperty<LocalDate> dtNasc = 
        new SimpleObjectProperty(LocalDate.now());

    private ClienteDAO clienteDao = null;

    public ClienteControl()throws ClienteException{
        try {
            clienteDao = new ClienteDAOImpl();
        } catch (Exception e) {
            throw new ClienteException(e);
        }
    }

    public void excluir(String cpf) throws ClienteException{
        clienteDao.remover(cpf);
    }

    public void adicionar() throws ClienteException{
        Cliente c = new Cliente();
        c.setCpf(cpf.get());
        c.setNome(nome.get());
        c.setEmail(email.get());
        c.setLimiteCredito(limiteCredito.get());
        c.setDtNasc(dtNasc.get());
        clienteDao.adicionar(c);
    }

    public void pesquisarPorCPF() throws ClienteException{
        lista.clear();
        lista.addAll(clienteDao.pesquisarPorCPF(cpf.get()));
    }

    public ObservableList<Cliente> getLista(){
        return lista;
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public DoubleProperty limiteCProperty() {
        return limiteCredito;
    }

    public ObjectProperty<LocalDate> dataNascProperty() {
        return dtNasc;
    }


    
}
