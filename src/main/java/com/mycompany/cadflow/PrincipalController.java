package com.mycompany.cadflow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell; 
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.classes.Acolhido;
import model.classes.Pessoa;
import model.services.PessoaService; 
import model.services.RelatorioService; 
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import javafx.scene.chart.PieChart;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.geometry.Pos; 

public class PrincipalController implements Initializable {

    @FXML private Button btnCadastroPessoa;
    @FXML private Button btnEditar;
    @FXML private Button btnExcluir; 
    @FXML private Button btnFicha;
    @FXML private Button btnImprimir;
    @FXML private ImageView imgCadFlow;
    @FXML private Label txtTelaPrincipalCadFlow;

    @FXML private TableView<Pessoa> tableViewAcolhidos;
    @FXML private TableColumn<Pessoa, Integer> tableViewAcolhidosCodigo;
    @FXML private TableColumn<Pessoa, String> tableViewAcolhidosNome;
    
    
    @FXML private TableColumn<Pessoa, Integer> tableViewAcolhidosStatus;
    
    @FXML private ComboBox<String> cbFiltroTipo;
    @FXML private CheckBox chkMostrarInativos;
    
    @FXML private TextField txtPesquisa; 
    
    @FXML private Label lblNumAcolhidos;
    @FXML private Label lblNumFamiliares;
    @FXML private PieChart graficoSexo;
    
    @FXML private Button btnAjustarCapacidade;
    @FXML private Label lblVagasDisponiveis;
    
    private ObservableList<Pessoa> listaTabela;
    private PessoaService service = new PessoaService();
    private RelatorioService relatorioService = new RelatorioService(); 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabela();
        configurarBotoes();
        configurarFiltros();
        atualizarTabela(); 
        atualizarDashboard();
        
        tableViewAcolhidosCodigo.setStyle("-fx-alignment: CENTER;");
        tableViewAcolhidosCodigo.setPrefWidth(75);
        tableViewAcolhidosCodigo.setMinWidth(75);
        tableViewAcolhidosCodigo.setMaxWidth(75);
        
        tableViewAcolhidosStatus.setPrefWidth(100);
        tableViewAcolhidosStatus.setMinWidth(100);
        tableViewAcolhidosStatus.setMaxWidth(100);

        
        
        tableViewAcolhidosNome.prefWidthProperty().bind(
            tableViewAcolhidos.widthProperty().subtract(75 + 100 + 5) 
        );
        
        if (btnAjustarCapacidade != null) {
            btnAjustarCapacidade.setOnAction(e -> abrirAjusteCapacidade());
        }
        
        btnEditar.setOnAction((t) -> {
            
            Pessoa selecionada = tableViewAcolhidos.getSelectionModel().getSelectedItem();
            
            if (selecionada != null) {
                
                abrirJanelaCadastro(selecionada); 
            } else {
                
                System.out.println("Ninguém selecionado.");
            }
        });
    }
    
     private void abrirAjusteCapacidade() {
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Configuração");
        dialog.setHeaderText("Capacidade Total do Abrigo");
        dialog.setContentText("Digite o novo número total de vagas:");

        
        Optional<String> result = dialog.showAndWait();

        
        result.ifPresent(numero -> {
            try {
                int novaCapacidade = Integer.parseInt(numero);
                
                
                boolean sucesso = service.atualizarCapacidadeMaxima(novaCapacidade);
                
                if (sucesso) {
                    atualizarDashboard(); 
                } else {
                    System.out.println("Erro ao salvar capacidade.");
                }
            } catch (NumberFormatException ex) {
                
                System.out.println("Número inválido.");
            }
        });
    }

    public void atualizarDashboard() {
        
        Map<String, Integer> dados = service.getDadosDashboard();
        
        
        
        int capacidadeMaxima = service.getCapacidadeMaxima(); 
        
        
        System.out.println("DEBUG: Capacidade vinda do banco: " + capacidadeMaxima);
        

        int totalAcolhidos = dados.get("totalAcolhidos");
        int totalFamiliares = dados.get("totalFamiliares");

        
        if (lblNumAcolhidos != null) lblNumAcolhidos.setText(String.valueOf(totalAcolhidos));
        if (lblNumFamiliares != null) lblNumFamiliares.setText(String.valueOf(totalFamiliares));

        
        if (lblVagasDisponiveis != null) {
            int vagasRestantes = capacidadeMaxima - totalAcolhidos;
            
            
            System.out.println("DEBUG: Conta: " + capacidadeMaxima + " - " + totalAcolhidos + " = " + vagasRestantes);

            if (vagasRestantes < 0) vagasRestantes = 0;
            
            lblVagasDisponiveis.setText(String.valueOf(vagasRestantes));
            
            
            if (vagasRestantes == 0) {
                lblVagasDisponiveis.setStyle("-fx-text-fill: #FF5555;"); 
            } else {
                lblVagasDisponiveis.setStyle("-fx-text-fill: white;");   
            }
        }

        
        if (graficoSexo != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Masculino", dados.get("M")),
                new PieChart.Data("Feminino", dados.get("F")),
                new PieChart.Data("Outros", dados.get("O"))
            );
            graficoSexo.setData(pieData);
        }
    }
    
    private void configurarFiltros() {
        
        cbFiltroTipo.getItems().addAll("Todos", "Acolhidos", "Familiares");
        cbFiltroTipo.getSelectionModel().selectFirst(); 
        
        
        cbFiltroTipo.setOnAction(e -> atualizarTabela());
        chkMostrarInativos.setOnAction(e -> atualizarTabela());
    }
    
    private void configurarTabela() {
        
        
        tableViewAcolhidosCodigo.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
        tableViewAcolhidosNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        
        tableViewAcolhidosStatus.setCellValueFactory(new PropertyValueFactory<>("statusAcolhido"));

        
        tableViewAcolhidosStatus.setCellFactory(column -> new TableCell<Pessoa, Integer>() {
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        
        
        setAlignment(Pos.CENTER); 

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item == 1 ? "Sim" : "Não");
            
            
            if (item == 1) {
                
                setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                
                setStyle("-fx-text-fill: gray;");
            }
        }
    }
});
    }

    private void configurarBotoes() {
        btnCadastroPessoa.setOnAction((t) -> {
            abrirJanelaCadastro(null); 
        });
        
        
        btnEditar.setOnAction((t) -> {
            Pessoa selecionada = tableViewAcolhidos.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                
                
                System.out.println("Selecionado para editar: " + selecionada.getNome());
            } else {
                System.out.println("Ninguém selecionado.");
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            Pessoa selecionada = tableViewAcolhidos.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Arquivar Registro");
                alert.setHeaderText("Desativar: " + selecionada.getNome());
                alert.setContentText("O registro será arquivado e oculto, mas não excluído. Continuar?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    
                    service.desativar(selecionada.getIdPessoa());
                    atualizarTabela();
                    atualizarDashboard();
                }
            } else {
                 
            }
        });
        
        btnImprimir.setOnAction((t) -> {
            Pessoa selecionada = tableViewAcolhidos.getSelectionModel().getSelectedItem();
            
            if (selecionada != null) {
                
                if (selecionada.getStatusAcolhido() == 1) {
                    
                    
                    Acolhido acolhidoCompleto = service.buscarPorId(selecionada.getIdPessoa());
                    
                    
                    relatorioService.gerarFichaAcolhido(acolhidoCompleto);
                    
                } else {
                    
                    
                    System.out.println("Impressão disponível apenas para Acolhidos neste momento.");
                }
            } else {
                 
                 System.out.println("Selecione um acolhido na tabela.");
            }
        });
        
        btnFicha.setOnAction((t) -> {
            Pessoa selecionada = tableViewAcolhidos.getSelectionModel().getSelectedItem();
            
            if (selecionada != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastrarAcolhido.fxml"));
                    Parent parent = loader.load();

                    CadastrarAcolhidoController controller = loader.getController();

                    controller.carregarAcolhidoParaEdicao(selecionada.getIdPessoa());

                    controller.ativarModoLeitura();
                    
                    // 4. Abre a janela
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setTitle("Ficha de: " + selecionada.getNome());
                    stage.setScene(scene);
                    
                    stage.setMinWidth(900);
                    stage.setMinHeight(700);
                    
                    stage.showAndWait();
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Selecione alguém na tabela.");
            }
        });
    }

    private void abrirJanelaCadastro(Pessoa pessoaParaEditar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastrarAcolhido.fxml"));
            Parent parent = loader.load();
            
            
            if (pessoaParaEditar != null) {
                
                CadastrarAcolhidoController controller = loader.getController();
                
                controller.carregarAcolhidoParaEdicao(pessoaParaEditar.getIdPessoa());
            }
            
            
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            
            stage.setMinWidth(800);  
            stage.setMinHeight(800); 
            
            stage.setTitle(pessoaParaEditar == null ? "Novo Cadastro" : "Editar Cadastro");
            stage.setScene(scene);
            stage.showAndWait(); 
            
            atualizarTabela();
            atualizarDashboard();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void atualizarTabela() {
        
        int tipo = 0; 
        String selecao = cbFiltroTipo.getSelectionModel().getSelectedItem();
        if ("Acolhidos".equals(selecao)) tipo = 1;
        if ("Familiares".equals(selecao)) tipo = 2;
        boolean mostrarInativos = chkMostrarInativos.isSelected();

        listaTabela = FXCollections.observableArrayList(service.filtrar(tipo, mostrarInativos));

        
        FilteredList<Pessoa> dadosFiltrados = new FilteredList<>(listaTabela, p -> true);

        
        
        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            dadosFiltrados.setPredicate(pessoa -> {
                
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String textoDigitado = newValue.toLowerCase();

                
                if (pessoa.getNome().toLowerCase().contains(textoDigitado)) {
                    return true; 
                }
                
                
                String buscaApenasNumeros = textoDigitado.replaceAll("[^0-9]", "");
                if (!buscaApenasNumeros.isEmpty() && pessoa.getCpf() != null) {
                    if (pessoa.getCpf().contains(buscaApenasNumeros)) {
                        return true; 
                    }
                }
                
                
                if (String.valueOf(pessoa.getIdPessoa()).contains(textoDigitado)) {
                    return true;
                }

                return false; 
            });
        });

        
        SortedList<Pessoa> dadosOrdenados = new SortedList<>(dadosFiltrados);

        
        dadosOrdenados.comparatorProperty().bind(tableViewAcolhidos.comparatorProperty());

        
        tableViewAcolhidos.setItems(dadosOrdenados);
        
        System.out.println("Tabela atualizada e busca ativada.");
    }
}