package com.mycompany.cadflow;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.services.LoginService;

public class EsqueciMinhaSenhaController implements Initializable {

    @FXML private TextField txtUsuarioRecuperar; 
    @FXML private PasswordField txtNovaSenha;    
    @FXML private PasswordField txtSenhaUsuarioAdm; 
    @FXML private Button btnSalvarNovaSenha;

    private LoginService loginService = new LoginService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSalvarNovaSenha.setOnAction(e -> onResetarSenha());
    }

    private void onResetarSenha() {
        String usuarioAlvo = txtUsuarioRecuperar.getText();
        String novaSenha = txtNovaSenha.getText();
        String senhaAdm = txtSenhaUsuarioAdm.getText();

        try {
            
            if (loginService.autorizarAdmin(senhaAdm)) {
                
                
                boolean sucesso = loginService.alterarSenha(usuarioAlvo, novaSenha);
                
                if (sucesso) {
                    mostrarAlerta("Sucesso", "Senha alterada com sucesso!");
                    fecharJanela();
                } else {
                    mostrarAlerta("Erro", "Usuário não encontrado.");
                }
            } else {
                mostrarAlerta("Acesso Negado", "Senha do Administrador incorreta.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     private void fecharJanela() {
        
        Stage stage = (Stage) btnSalvarNovaSenha.getScene().getWindow();
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
