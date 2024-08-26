import java.time.LocalDate;

public class Cliente{
       private String cpf;
       private String nome;
       private String email;
       private Double limiteCredito;
       private LocalDate dtNasc;
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getLimiteCredito() {
        return limiteCredito;
    }
    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
    public LocalDate getDtNasc() {
        return dtNasc;
    }
    public void setDtNasc(LocalDate dtNasc) {
        this.dtNasc = dtNasc;
    }
    
}