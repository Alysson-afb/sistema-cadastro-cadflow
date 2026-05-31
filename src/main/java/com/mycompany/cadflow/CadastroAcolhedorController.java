package com.mycompany.cadflow;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.classes.Acolhido;
import model.services.LoginService;

public class CadastroAcolhedorController implements Initializable {

    @FXML
    private TextField txtNovoUsuario;
    @FXML
    private PasswordField txtSenhaNovoUsuario;
    @FXML
    private PasswordField txtSenhaUsuarioAdm;
    @FXML
    private Label lblSenhaAdmin;
    @FXML
    private Button btnNovoUsuario;

    private boolean modoPrimeiroAcesso = false;

    private LoginService loginService = new LoginService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnNovoUsuario.setOnAction(e -> onCadastrar());
    }

    public void setModoPrimeiroAcesso(boolean ativar) {
        this.modoPrimeiroAcesso = ativar;

        if (ativar) {
            txtSenhaUsuarioAdm.setVisible(false);
            txtSenhaUsuarioAdm.setManaged(false);
            lblSenhaAdmin.setVisible(false);

            btnNovoUsuario.setText("Criar Administrador");
        }
    }

    private void onCadastrar() {
        String novoUser = txtNovoUsuario.getText();
        String novaSenha = txtSenhaNovoUsuario.getText();
        String senhaAdm = txtSenhaUsuarioAdm.getText();

        if (novoUser.isEmpty() || novaSenha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha o Nome e a Senha do novo usuário.");
            return; // STOP
        }

        try {
            if (modoPrimeiroAcesso) {
                boolean sucesso = loginService.cadastrarProfissional(novoUser, novaSenha, "Administrador");
                
                if (sucesso) {
                    mostrarAlerta("Bem-vindo!", "Administrador criado com sucesso! Faça login.");
                    fecharJanela();
                    return;
                } else {
                    mostrarAlerta("Erro", "Falha ao criar Administrador.");
                    return;
                }
            }

            if (senhaAdm.isEmpty()) {
                mostrarAlerta("Erro", "A senha do Administrador é obrigatória.");
                return;
            }

            if (loginService.autorizarAdmin(senhaAdm)) {
                boolean sucesso = loginService.cadastrarProfissional(novoUser, novaSenha, "Colaborador");
                
                if (sucesso) {
                    mostrarAlerta("Sucesso", "Usuário cadastrado com sucesso!");
                    fecharJanela();
                    return;
                }
            } else {
                mostrarAlerta("Acesso Negado", "Senha do Administrador incorreta.");
            }

        } catch (SQLException ex) {
            mostrarAlerta("Erro", "Erro no banco: " + ex.getMessage());
        }
    }

    private void fecharJanela() {

        Stage stage = (Stage) btnNovoUsuario.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
