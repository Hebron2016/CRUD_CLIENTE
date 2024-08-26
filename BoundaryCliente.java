import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;


public class BoundaryCliente extends Application {
    private TextField txtCPF = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtLC = new TextField();
    private DatePicker txtDN = new DatePicker();

    private TableView<Cliente> table = new TableView<>();

    private ClienteControl control = null;

    public void start(Stage stage) throws ClienteException{
        try{
            control = new ClienteControl();
        }catch(ClienteException e){
            alert("Erro ao iniciar o control");
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane grid = new GridPane();

        Scene scn = new Scene (panePrincipal, 400, 200);

        panePrincipal.setTop(grid);
        panePrincipal.setCenter(table);

        grid.add(new Label("CPF: "), 0, 0);
        grid.add(txtCPF, 1,0);
        grid.add(new Label("Nome: "), 0, 1);
        grid.add(txtNome,1,1);
        grid.add(new Label("Email: "), 0,2);
        grid.add(txtEmail, 1,2);
        grid.add(new Label("Limite de crédito: "), 0,3);
        grid.add(txtLC, 1,3);
        grid.add(new Label("DataNascimento: "), 0,4);
        grid.add(txtDN, 1,4);

        Button btnAdicionar = new Button ("Adicionar");
        btnAdicionar.addEventFilter(ActionEvent.ANY,
            new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e){
                    try{
                        control.adicionar();
                    }catch(Exception err){
                        alert("Erro ao adicionar");
                    }
                }
            });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.addEventFilter(ActionEvent.ANY,
            new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e){
                    try{
                        control.pesquisarPorCPF();
                    } catch(Exception err){
                        alert("Erro ao pesquisar");
                    }
                }
            });
            grid.add(btnAdicionar, 0,5);
            grid.add(btnPesquisar, 1,5);
            
            createTableView();
            generateBindings();

            stage.setScene(scn);
            stage.show();
    }

    public void generateBindings() {
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(txtCPF.textProperty(), control.cpfProperty());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
        Bindings.bindBidirectional(txtLC.textProperty(), control.limiteCProperty(), converter);
        Bindings.bindBidirectional(txtDN.valueProperty(), control.dataNascProperty());
    }

    public void alert(String msg){
        Alert alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    public void createTableView() {
        TableColumn<Cliente, String> col1 = new TableColumn<>("CPF");
        col1.setCellValueFactory( new PropertyValueFactory<>("cpf"));
        TableColumn<Cliente, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<>("nome"));
        TableColumn<Cliente, String> col3 = new TableColumn<>("Email");
        col3.setCellValueFactory( new PropertyValueFactory<>("email"));
        TableColumn<Cliente, Double> col4 = new TableColumn<>("Limite Credito");
        col4.setCellValueFactory( new PropertyValueFactory<>("limiteCredito"));
        TableColumn<Cliente, String> col5 = new TableColumn<>("Data de Nascimento");
        col5.setCellValueFactory( new PropertyValueFactory<>("dtNasc"));

        TableColumn<Cliente, Void> colAcoes = 
        new TableColumn<>("Ações");
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>>
        callback = new Callback<>(){
            public TableCell<Cliente, Void> call(TableColumn<Cliente,Void> coluna){
                TableCell<Cliente, Void> tc = new TableCell<>(){
                    final Button btnExcluir = new Button("Excluir");
                    {
                        btnExcluir.setOnAction(event -> {
                            Cliente c1 = table.getItems().get(getIndex());
                            try{
                                control.excluir(c1.getCpf());
                            }catch(Exception err){
                                alert("Erro ao excluir o usuário");
                            }
                        });
                    }
                    public void updateItem(Void item, boolean empty){
                        super.updateItem(item,empty);
                        if(empty){
                            setGraphic(null);
                        }else{
                            setGraphic(btnExcluir);
                        }
                    }
                };
                return tc;
            }
        };
        table.getColumns().addAll(col1,col2,col3,col4,col5,colAcoes);
        table.setItems( control.getLista() );
        colAcoes.setCellFactory( callback );
    }
    public static void main(String args[]){
        Application.launch(BoundaryCliente.class, args);
    }
}
