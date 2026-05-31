package com.mycompany.cadflow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.classes.Acolhido;
import model.classes.Familiar;
import model.classes.Pessoa;
import model.services.FamiliarService;
import model.services.PessoaService;

public class CadastrarAcolhidoController implements Initializable {

    private PessoaService pessoaService = new PessoaService();
    private FamiliarService familiarService = new FamiliarService();

    private int idEmEdicao = 0;
    private ObservableList<Familiar> listaFamiliares;

    @FXML
    private TabPane tabPanePrincipal;
    @FXML
    private Tab tabDadosAcolhido;
    @FXML
    private Tab tabFamiliares;
    @FXML
    private CheckBox chkEhAcolhido;

    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> cmbBoxDadosEstado;
    @FXML
    private ComboBox<String> cmbBoxDadosSexo;
    @FXML
    private DatePicker dateDadosNascimento;
    @FXML
    private TextField txtDadosNome;
    @FXML
    private TextField txtDadosNomeSocial;
    @FXML
    private TextField txtDadosCpf;
    @FXML
    private TextField txtDadosCor;
    @FXML
    private TextField txtDadosNacionalidade;
    @FXML
    private TextField txtDadosNaturalidade;
    @FXML
    private TextField txtDadosEstadoCivil;
    @FXML
    private TextField txtDadosProfissao;
    @FXML
    private TextField txtDadosEscolar;

    @FXML
    private TextField txtDadosRegistro;
    @FXML
    private TextArea txtDadosSaude;
    @FXML
    private TextArea txtDadosServicos;
    @FXML
    private TextArea txtDadosMedida;
    @FXML
    private TextArea txtDadosHistoricoRua;
    @FXML
    private TextArea txtDadosAvaliacao;

    @FXML
    private ComboBox<Pessoa> cbSelecionarParente;
    @FXML
    private TextField txtParentesco;
    @FXML
    private TextField txtOcupacaoFamiliar;
    @FXML
    private Button btnAdicionarFamiliar;
    @FXML
    private Button btnRemoverFamiliar;

    @FXML
    private TableView<Familiar> tvFamiliares;
    @FXML
    private TableColumn<Familiar, String> colFamNome;
    @FXML
    private TableColumn<Familiar, String> colFamParentesco;
    @FXML
    private TableColumn<Familiar, String> colFamOcupacao;

    @FXML
    private Tab tabAcolhimento;
    @FXML
    private Tab tabPlanoAcao;

    @FXML
    private DatePicker dateAcolhimentoData;
    @FXML
    private TextField txtAcolhimentoResponsavel;
    @FXML
    private TextField txtAcolhimentoContato;
    @FXML
    private TextField txtAcolhimentoResidia;
    @FXML
    private TextField txtAcolhimentoDetalhes;
    @FXML
    private TextField txtAcolhimentoMotivo;

    @FXML
    private TextField txtPlanoObjetivo;
    @FXML
    private TextField txtPlanoAcao;
    @FXML
    private TextField txtPlanoResponsaveis;
    @FXML
    private DatePicker datePlanoInicio;
    @FXML
    private DatePicker datePlanoFim;

    @FXML
    private TextArea txtObservacoes;

    @FXML
    private TextField txtDadosTelefone;
    @FXML
    private TextField txtDadosEndereco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarCombos();
        configurarLogicaAbas();
        configurarTabelaFamiliares();
        atualizarContextoFamiliares(false);
        configurarMascaras();

        if (btnSalvar != null) {
            btnSalvar.setOnAction(e -> onSalvar());
        }
        if (btnCancelar != null) {
            btnCancelar.setOnAction(e -> fecharJanela());
        }

        if (btnAdicionarFamiliar != null) {
            btnAdicionarFamiliar.setOnAction(e -> onAdicionarFamiliar());
        }
        if (btnRemoverFamiliar != null) {
            btnRemoverFamiliar.setOnAction(e -> onRemoverFamiliar());
        }
    }

    private void carregarCombos() {
        cmbBoxDadosSexo.getItems().addAll("M", "F", "O");
        cmbBoxDadosEstado.getItems().addAll(
                "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP", "SE", "TO"
        );
    }

    private void carregarPessoasNoCombo() {

        List<Pessoa> todas = pessoaService.getAll();
        cbSelecionarParente.setItems(FXCollections.observableArrayList(todas));
    }

    private void configuringTabelaFamiliares() {

        colFamNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeParente()));
        colFamParentesco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParentesco()));
        colFamOcupacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOcupacao()));
    }

    private void configurarLogicaAbas() {

        tabPanePrincipal.getTabs().removeAll(tabDadosAcolhido, tabFamiliares, tabAcolhimento, tabPlanoAcao);

        chkEhAcolhido.selectedProperty().addListener((obs, antigo, novo) -> {
            atualizarContextoFamiliares(novo);
            if (novo) {
                if (!tabPanePrincipal.getTabs().contains(tabDadosAcolhido)) {
                    tabPanePrincipal.getTabs().add(tabDadosAcolhido);
                }
                if (!tabPanePrincipal.getTabs().contains(tabFamiliares)) {
                    tabPanePrincipal.getTabs().add(tabFamiliares);
                }
                if (!tabPanePrincipal.getTabs().contains(tabAcolhimento)) {
                    tabPanePrincipal.getTabs().add(tabAcolhimento);
                }
                if (!tabPanePrincipal.getTabs().contains(tabPlanoAcao)) {
                    tabPanePrincipal.getTabs().add(tabPlanoAcao);
                }
            } else {
                tabPanePrincipal.getTabs().removeAll(tabDadosAcolhido, tabFamiliares, tabAcolhimento, tabPlanoAcao);
            }
        });
    }

    private void atualizarContextoFamiliares(boolean ehAcolhido) {
        cbSelecionarParente.setItems(FXCollections.observableArrayList());

        if (ehAcolhido) {

            tabFamiliares.setText("Familiares deste Acolhido");
            cbSelecionarParente.setPromptText("Selecione o Parente...");

            List<Pessoa> todas = pessoaService.getAll();
            cbSelecionarParente.setItems(FXCollections.observableArrayList(todas));

        } else {

            tabFamiliares.setText("Acolhidos Vinculados");
            cbSelecionarParente.setPromptText("Selecione o Acolhido...");

            List<Pessoa> soAcolhidos = pessoaService.filtrar(1, false);
            cbSelecionarParente.setItems(FXCollections.observableArrayList(soAcolhidos));
        }

        if (idEmEdicao > 0) {
            atualizarListaFamiliares();
        }
    }

    public void carregarAcolhidoParaEdicao(int id) {
        this.idEmEdicao = id;

        Acolhido a = pessoaService.buscarPorId(id);

        if (a != null) {

            txtDadosNome.setText(a.getNome());
            txtDadosNomeSocial.setText(a.getNomeSocial());
            txtDadosCpf.setText(a.getCpf());
            dateDadosNascimento.setValue(a.getDataNascimento());
            cmbBoxDadosSexo.setValue(a.getSexo());
            txtDadosCor.setText(a.getCor());
            txtDadosNacionalidade.setText(a.getNacionalidade());
            txtDadosNaturalidade.setText(a.getNaturalidade());
            txtDadosEstadoCivil.setText(a.getEstadoCivil());
            txtDadosProfissao.setText(a.getProfissao());
            txtDadosEscolar.setText(a.getEscolaridade());
            cmbBoxDadosEstado.setValue(a.getEstadoUF());
            txtDadosTelefone.setText(a.getTelefone());
            txtDadosEndereco.setText(a.getEnderecoAtual());

            if (a.getStatusAcolhido() == 1) {
                chkEhAcolhido.setSelected(true);

                txtObservacoes.setText(a.getObservacoes());
                txtDadosRegistro.setText(a.getRegistroCartorio());
                txtDadosSaude.setText(a.getInfoSaude());
                txtDadosServicos.setText(a.getServicosAcessados());
                txtDadosMedida.setText(a.getMedidaProtetiva());
                txtDadosHistoricoRua.setText(a.getHistoricoRua());
                txtDadosAvaliacao.setText(a.getAvaliacaoInterdisciplinar());
                dateAcolhimentoData.setValue(a.getDataEntrada());
                txtAcolhimentoResponsavel.setText(a.getResponsavelAcolhimento());
                txtAcolhimentoContato.setText(a.getContatoResponsavel());
                txtAcolhimentoResidia.setText(a.getResidiaCom());
                txtAcolhimentoDetalhes.setText(a.getDetalhesAcolhimento());
                txtAcolhimentoMotivo.setText(a.getMotivoAcolhimento());

                txtPlanoObjetivo.setText(a.getPlanoObjetivo());
                txtPlanoAcao.setText(a.getPlanoAcao());
                txtPlanoResponsaveis.setText(a.getPlanoResponsaveis());
                datePlanoInicio.setValue(a.getPlanoPrazoInicio());
                datePlanoFim.setValue(a.getPlanoPrazoFim());

                boolean ehAcolhido = (a.getStatusAcolhido() == 1);
                chkEhAcolhido.setSelected(ehAcolhido);

                atualizarListaFamiliares();
            } else {
                chkEhAcolhido.setSelected(false);
            }
        }
    }

    private void atualizarListaFamiliares() {
        if (idEmEdicao > 0) {
            List<Familiar> lista;

            if (chkEhAcolhido.isSelected()) {

                lista = familiarService.listarPorAcolhido(idEmEdicao);
            } else {

                lista = familiarService.listarAcolhidosDoParente(idEmEdicao);
            }

            listaFamiliares = FXCollections.observableArrayList(lista);
            tvFamiliares.setItems(listaFamiliares);
        }
    }

    @FXML
    public void onSalvar() {
        if (txtDadosNome.getText().isEmpty()) {
            mostrarAlerta("Erro", "O Nome é obrigatório.");
            return;
        }

        try {

            Acolhido obj = new Acolhido();

            if (idEmEdicao > 0) {
                obj.setIdPessoa(idEmEdicao);
            }
            obj.setTelefone(txtDadosTelefone.getText());
            obj.setEnderecoAtual(txtDadosEndereco.getText());
            obj.setNome(txtDadosNome.getText());
            obj.setNomeSocial(txtDadosNomeSocial.getText());
            obj.setCpf(txtDadosCpf.getText());
            obj.setDataNascimento(dateDadosNascimento.getValue());
            obj.setSexo(cmbBoxDadosSexo.getValue());
            obj.setCor(txtDadosCor.getText());
            obj.setNacionalidade(txtDadosNacionalidade.getText());
            obj.setNaturalidade(txtDadosNaturalidade.getText());
            obj.setEstadoCivil(txtDadosEstadoCivil.getText());
            obj.setProfissao(txtDadosProfissao.getText());
            obj.setEscolaridade(txtDadosEscolar.getText());
            obj.setEstadoUF(cmbBoxDadosEstado.getValue());
            String cpfLimpo = txtDadosCpf.getText().replaceAll("[^0-9]", "");
            obj.setCpf(cpfLimpo);

            boolean ehAcolhido = chkEhAcolhido.isSelected();

            if (ehAcolhido) {
                obj.setObservacoes(txtObservacoes.getText());
                obj.setRegistroCartorio(txtDadosRegistro.getText());
                obj.setInfoSaude(txtDadosSaude.getText());
                obj.setServicosAcessados(txtDadosServicos.getText());
                obj.setMedidaProtetiva(txtDadosMedida.getText());
                obj.setHistoricoRua(txtDadosHistoricoRua.getText());
                obj.setAvaliacaoInterdisciplinar(txtDadosAvaliacao.getText());
                obj.setDataEntrada(dateAcolhimentoData.getValue());
                obj.setDataEntrada(dateAcolhimentoData.getValue());

                obj.setResponsavelAcolhimento(txtAcolhimentoResponsavel.getText());
                obj.setContatoResponsavel(txtAcolhimentoContato.getText());
                obj.setResidiaCom(txtAcolhimentoResidia.getText());
                obj.setDetalhesAcolhimento(txtAcolhimentoDetalhes.getText());
                obj.setMotivoAcolhimento(txtAcolhimentoMotivo.getText());

                obj.setPlanoObjetivo(txtPlanoObjetivo.getText());
                obj.setPlanoAcao(txtPlanoAcao.getText());
                obj.setPlanoResponsaveis(txtPlanoResponsaveis.getText());
                obj.setPlanoPrazoInicio(datePlanoInicio.getValue());
                obj.setPlanoPrazoFim(datePlanoFim.getValue());

                String telLimpo = txtAcolhimentoContato.getText().replaceAll("[^0-9]", "");
                obj.setContatoResponsavel(telLimpo);
            }

            boolean sucesso;

            if (idEmEdicao > 0) {

                sucesso = pessoaService.atualizar(obj, ehAcolhido);
            } else {

                if (ehAcolhido) {
                    sucesso = pessoaService.inserirAcolhido(obj);
                } else {
                    sucesso = pessoaService.inserirPessoa(obj);
                }
            }

            if (sucesso) {
                mostrarAlerta("Sucesso", "Dados salvos com sucesso!");
                fecharJanela();
            } else {
                mostrarAlerta("Erro", "Erro ao salvar no banco.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro Crítico", e.getMessage());
        }
    }

    private void onAdicionarFamiliar() {
        if (idEmEdicao == 0) {
            mostrarAlerta("Atenção", "Salve o cadastro atual antes de criar vínculos.");
            return;
        }

        Pessoa selecionadoNoCombo = cbSelecionarParente.getValue();
        String parentesco = txtParentesco.getText();
        String ocupacao = txtOcupacaoFamiliar.getText();

        if (selecionadoNoCombo == null || parentesco.isEmpty()) {
            mostrarAlerta("Erro", "Selecione a pessoa e informe o parentesco.");
            return;
        }

        if (selecionadoNoCombo.getIdPessoa() == idEmEdicao) {
            mostrarAlerta("Erro", "Não pode vincular a si mesmo.");
            return;
        }

        boolean sucesso;
        boolean estouEditandoAcolhido = chkEhAcolhido.isSelected();

        if (estouEditandoAcolhido) {

            sucesso = familiarService.adicionarVinculo(idEmEdicao, selecionadoNoCombo.getIdPessoa(), parentesco, ocupacao);
        } else {

            sucesso = familiarService.adicionarVinculo(selecionadoNoCombo.getIdPessoa(), idEmEdicao, parentesco, ocupacao);
        }

        if (sucesso) {
            atualizarListaFamiliares();
            txtParentesco.clear();
            txtOcupacaoFamiliar.clear();
            cbSelecionarParente.getSelectionModel().clearSelection();
        } else {
            mostrarAlerta("Erro", "Falha ao vincular.");
        }
    }

    private void onRemoverFamiliar() {
        Familiar selecionado = tvFamiliares.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            boolean sucesso = familiarService.removerVinculo(selecionado.getIdVinculo());
            if (sucesso) {
                atualizarListaFamiliares();
            } else {
                mostrarAlerta("Erro", "Erro ao remover vínculo.");
            }
        } else {
            mostrarAlerta("Aviso", "Selecione um familiar na tabela para remover.");
        }
    }

    private void configurarTabelaFamiliares() {
        colFamNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeParente()));
        colFamParentesco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParentesco()));
        colFamOcupacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOcupacao()));
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtDadosNome.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void configurarMascaras() {

        txtDadosCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }

            String digitos = newValue.replaceAll("[^0-9]", "");

            if (digitos.length() > 11) {
                digitos = digitos.substring(0, 11);
            }

            StringBuilder formatado = new StringBuilder();

            for (int i = 0; i < digitos.length(); i++) {
                if (i == 3 || i == 6) {
                    formatado.append(".");
                }
                if (i == 9) {
                    formatado.append("-");
                }
                formatado.append(digitos.charAt(i));
            }

            if (!newValue.equals(formatado.toString())) {
                txtDadosCpf.setText(formatado.toString());
                txtDadosCpf.positionCaret(formatado.length());
            }
        });

        if (txtAcolhimentoContato != null) {
            txtAcolhimentoContato.textProperty().addListener((observable, oldValue, newValue) -> {
                String digitos = newValue.replaceAll("[^0-9]", "");
                if (digitos.length() > 11) {
                    digitos = digitos.substring(0, 11);
                }

                StringBuilder fmt = new StringBuilder();
                if (digitos.length() > 0) {
                    fmt.append("(");
                }

                for (int i = 0; i < digitos.length(); i++) {
                    if (i == 2) {
                        fmt.append(") ");
                    }
                    if (i == 7) {
                        fmt.append("-");
                    }
                    fmt.append(digitos.charAt(i));
                }

                if (!newValue.equals(fmt.toString())) {
                    txtAcolhimentoContato.setText(fmt.toString());
                    txtAcolhimentoContato.positionCaret(fmt.length());
                }
            });
        }
    }

    public void ativarModoLeitura() {
        if (btnSalvar != null) {
            btnSalvar.setVisible(false);
        }
        if (btnAdicionarFamiliar != null) {
            btnAdicionarFamiliar.setVisible(false);
        }
        if (btnRemoverFamiliar != null) {
            btnRemoverFamiliar.setVisible(false);
        }
        if (chkEhAcolhido != null) {
            chkEhAcolhido.setDisable(true);
        }
        if (btnCancelar != null) {
            btnCancelar.setText("Fechar");
        }

        bloquearEdicao(txtDadosNome);
        bloquearEdicao(txtDadosCpf);
        bloquearEdicao(txtDadosSaude);
        bloquearEdicao(txtDadosHistoricoRua);
        bloquearEdicao(txtDadosAvaliacao);
        bloquearEdicao(txtAcolhimentoDetalhes);
        bloquearEdicao(txtAcolhimentoMotivo);
        bloquearEdicao(txtPlanoAcao);
        dateDadosNascimento.setDisable(true);
        cmbBoxDadosSexo.setDisable(true);
        cmbBoxDadosEstado.setDisable(true);
        dateAcolhimentoData.setDisable(true);
    }

    private void bloquearEdicao(javafx.scene.control.TextInputControl campo) {
        if (campo != null) {
            campo.setEditable(false);
            campo.setStyle("-fx-opacity: 1; -fx-background-color: #f4f4f4;");
        }
    }

}
