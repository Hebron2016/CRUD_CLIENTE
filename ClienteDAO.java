
import java.util.List;

public interface ClienteDAO {

    public void adicionar(Cliente c) throws ClienteException;
    public List<Cliente>pesquisarTodos() throws ClienteException;
    public List<Cliente>pesquisarPorCPF(String cpf) throws ClienteException;
    public void remover(String cpf) throws ClienteException;
    public void atualizar(String cpf, Cliente c) throws ClienteException;
    

}
