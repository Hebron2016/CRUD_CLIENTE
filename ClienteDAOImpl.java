import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class ClienteDAOImpl implements ClienteDAO {
    private DBConnection dbConn = null;

    public ClienteDAOImpl() throws ClienteException{
        try{
            dbConn = DBConnection.getInstance();
        }catch(Exception e){
            throw new ClienteException( e );
        }
    }
    public void adicionar (Cliente c) throws ClienteException{
        try{
            Connection con = dbConn.getConnection();
            String sql ="""
                    INSERT INTO Cliente(cpf, nome, email, limiteCredito, dtNasc) VALUES (?,?,?,?,?)
                    """;
            PreparedStatement pstm = con.preparedStatement(sql);
            pstm.setString(1, c.getCpf());
            pstm.setString(2, c.getNome());
            pstm.setString(3, c.getEmail());
            pstm.setDouble(4, c.getLimiteCredito());
            pstm.setDate(5, java.sql.Date.valueOf(c.getDtNasc()));
            pstm.executeUpdate();
            con.close();
        }catch(Exception e){
            throw new ClienteException(e);
        }
    }
    public List<Cliente> pesquisarTodos()throws ClienteException{
        return pesquisarPorCPF("");
    }

    public List<Cliente> pesquisarPorNome(String cpf) throws ClienteException{
        try{
            List<Cliente> lista = new ArrayList<Cliente>();
            Connection con = dbConn.getConnection();
            String sql ="""
                    SELECT * FROM Cliente WHERE cpf ?
                    """;
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setString(1,"%"+ cpf+ "%");
                    ResultSet rs = pstm.executeQuery();
                    while (rs.next()){
                        Cliente c = new Cliente();
                        c.setCpf(rs.getString("cpf"));
                        c.setNome(rs.getString("nome"));
                        c.setEmail(rs.getString("email"));
                        c.setDtNasc(rs.getDate("dtNasc").toLocalDate());
                        lista.add(c);
                    }
                    con.close();
                    return lista;
        }catch(Exception e){
            throw new ClienteException( e );
        }
    }
    public void remover(String cpf) throws ClienteException{
        try{
            Connection con = dbConn.getConnection();
            String sql ="""
                    DELETE FROM Cliente WHERE cpf = ?
                """;
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1,cpf);
                pstm.executeUpdate();
                con.close();     
        }catch(Exception e){
            throw new ClienteException( e );
        }
    }
    public void atualizar(String cpf, Cliente c) throws ClienteException{
        try{
            Connection con = dbConn.getConnection();
            String sql = """
                    UPDATE Cliente SET nome = ?, email = ?, limiteCredito = ?, dtNasc = ?
                    WHERE cpf = ?
                    """;
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, c.getNome());
            pstm.setString(2, c.getEmail());
            pstm.setDouble(3, c.getLimiteCredito());
            pstm.setDate(4, java.sql.Date.valueOf(c.getDtNasc()));
            pstm.executeUpdate();
            con.close();
        }catch (Exception e){
            throw new ClienteException( e );
        }
    }
}

