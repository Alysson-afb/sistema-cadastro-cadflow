package com.mycompany.cadflow;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.services.LoginService;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Label txtCadastroNovoAcolhedor;

    @FXML
    private Label txtEsqueciMinhaSenha;

    @FXML
    private PasswordField PasswordLoginSenha;

    @FXML
    private TextField txtLoginUsuario;

    @FXML
    private ImageView imgCadFlow;

    @FXML
    private Label statusLabel;

    private LoginService authService;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        javafx.application.Platform.runLater(() -> {
            verificarPrimeiroAcesso();
        });

        this.authService = new LoginService();
        
        txtCadastroNovoAcolhedor.setOnMouseClicked(e -> {
            abrirJanelaAuxiliar("CadastroAcolhedor.fxml", "Novo Usuário");
        });

        
        txtEsqueciMinhaSenha.setOnMouseClicked(e -> {
            abrirJanelaAuxiliar("EsqueciMinhaSenha.fxml", "Redefinir Senha");
        });

        btnLogin.setOnAction((ActionEvent t) -> {

            String username = txtLoginUsuario.getText();
            String password = PasswordLoginSenha.getText();

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Usuário e senha são obrigatórios.");
                return;
            }

            try {
                boolean isValid = authService.validateUser(username, password);

                if (isValid) {
                    btnLogin.setDisable(true);
                    statusLabel.setText("Login bem-sucedido!");
                    statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);

                    PauseTransition pause = new PauseTransition(Duration.seconds(1));

                    pause.setOnFinished(e -> {
                        try {
                            loadMainScene();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                            statusLabel.setText("Erro ao carregar a tela.");
                        }
                    });

                    pause.play();

                } else {
                    
                    statusLabel.setText("Usuário ou senha inválidos.");
                    PasswordLoginSenha.clear(); 
                }

            } catch (SQLException e) {
                
                statusLabel.setText("Erro ao conectar com o banco. Tente novamente.");
                e.printStackTrace();
            }

        });

    }
    
    private void abrirJanelaAuxiliar(String fxml, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadMainScene() throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Painel Principal - CadFlow");
        stage.centerOnScreen();
    }
    
    private void verificarPrimeiroAcesso() {
        try {
            if (authService.isSistemaVazio()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bem-vindo ao CadFlow");
                alert.setHeaderText("Configuração Inicial");
                alert.setContentText("Nenhum usuário encontrado.\nVamos cadastrar o primeiro Administrador do sistema.");
                alert.showAndWait();
                abrirCadastroAdmin();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void abrirCadastroAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroAcolhedor.fxml"));
            Parent root = loader.load();
            
            CadastroAcolhedorController controller = loader.getController();
            controller.setModoPrimeiroAcesso(true);

            Stage stage = new Stage();
            stage.setTitle("Cadastrar Administrador");
            stage.setScene(new Scene(root));
            
            stage.showAndWait();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
