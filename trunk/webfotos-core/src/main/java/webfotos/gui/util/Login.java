/*
 * Classe Login
 * koiti issayama 12/07/03
 * monta uma tela modal para armazenamento de usu�rio e senha
 */

package webfotos.gui.util;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import webfotos.util.Util;

/**
 * Respons�vel pelas fun��es relacionadas ao login do usu�rio.
 */
public final class Login {
    private static final Login instanciaLogin=new Login();
    private String usuario;
    private char[] senha;
    private boolean cancelado=false;


    // elementos GUI
    private JLabel lblNome=new JLabel("Nome:");
    private JTextField txtNome=new JTextField(8);
    private JLabel lblSenha=new JLabel("Senha:");
    private JPasswordField txtSenha=new JPasswordField(8);
    private JButton btOk=new JButton("Ok");
    private JButton btCancelar=new JButton("Cancelar");
    private static JDialog telaLogin;

    /**
     * Construtor da classe.
     * Recebe dois valores e usa um deles para setar o t�tulo da janela.
     * Logo ap�s, chama o m�todo startLogin().
     * @param title Object para setar o t�tulo.
     */
    public Login(Object title) {
        telaLogin.setTitle((String)title);
        this.startLogin();
    }

    /**
     * Construtor da classe.
     * Apenas chama o m�todo startLogin().
     */
    public Login() {
        this.startLogin();
    }

    /**
     * Apresenta tela modal de login, solicitando usu�rio e senha.
     * Trabalha os bot�es ok e cancelar.
     * Checa se o nome do usu�rio foi preenchido.
     * Desenha a tela de login, usando coordenadas.
     * Configura a caixa de di�logo e apresenta.
     */
    public void startLogin() {
        JDialog.setDefaultLookAndFeelDecorated(true);
        telaLogin=new JDialog((Frame) null, "Identifica��o", true);
        telaLogin.setResizable(false);

        // botoes Ok, Cancelar
        btOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // caso n�o tenha preenchido o nome, continua pedindo...
                if(txtNome.getText().length() == 0) return;
                usuario=txtNome.getText();
                senha=txtSenha.getPassword();
                telaLogin.setVisible(false);
                cancelado=false;
            }
        });

        // clicando em Cancelar, seta o usu�rio null e retorna
        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usuario=null;
                senha=null;
                telaLogin.setVisible(false);
                cancelado=true;
            }
        });

        // desenha a tela de login, usando coordenadas
        telaLogin.setSize(200,123);
        Container cp=telaLogin.getContentPane();
        cp.setLayout(null);

        cp.add(lblNome); cp.add(txtNome);
        cp.add(lblSenha); cp.add(txtSenha);

        cp.add(btCancelar); cp.add(btOk);

        lblNome.setBounds(8,8,40,20);
        txtNome.setBounds(55,8,129,20);
        lblSenha.setBounds(8,34,40,20);
        txtSenha.setBounds(55,34,129,20);

        btCancelar.setBounds(8,60,88,25);
        btOk.setBounds(104,60,80,25);

        // configura a caixa de di�logo e apresenta
        telaLogin.getRootPane().setDefaultButton(btOk);
        telaLogin.setLocationRelativeTo(null);

    }

    /**
     * Seta o t�tulo da tela de login e retorna a inst�ncia  da classe.
     * @param titulo T�tulo da tela.
     * @return Retorna a inst�ncia de Login.
     */
    public static Login getLogin(String titulo) { 
        telaLogin.setTitle(titulo);
        return instanciaLogin;
    }

    /**
     * Retorna a inst�ncia  da classe.
     * @return Retorna a inst�ncia de Login.
     */
    public static Login getLogin() { 
        return instanciaLogin;
    }

    /**
     * Retorna a vari�vel cancelado.
     * @return Retorna uma vari�vel l�gica.
     */
    public boolean cancelado() { return cancelado; }
    
    /**
     * Retorna o usu�rio de login.
     * @return Retorna um usu�rio.
     */
    public String getUser() { return usuario; }
    
    /**
     * Retorna a senha de login.
     * @return Retorna uma senha.
     */
    public char[] getPassword() { return senha; }
    
    /**
     * Mostra a tela sem anexar uma mensagem de erro.
     */
    public void show() { show(""); }
    
    /**
     * Mostra a tela anexando uma mensagem de erro.
     * @param msgErro Mensagem de erro.
     */
    public void show(String msgErro) {
        if(msgErro!=null && ! msgErro.equals("")) {
            JOptionPane.showMessageDialog(telaLogin, msgErro,"Erro", JOptionPane.ERROR_MESSAGE);
        }			
        txtSenha.setText("");
        txtSenha.setCaretPosition(0);

        String tempLogin = Util.getConfig().getString("autoPreencher.Login");
        String tempSenha = Util.getConfig().getString("autoPreencher.Pass");

        /** preenche com usuario se cadastrado */
        if(tempLogin.length() > 0 && !(txtNome.getText().length() > 0)) {
            txtNome.setText(tempLogin);
        }

        /** 
         * Preenche com senha se cadastrado e usu�rio for o 
         * mesmo que o autopreenchimento
         */
        if( tempSenha.length() > 0 && 
            txtNome.getText().equals(tempLogin)) {
            
            txtSenha.setText(tempSenha);
        } 
        
        usuario=null; senha=null;
        telaLogin.setVisible(true);		
    }

    /**
     * M�todo principal.
     * Instancia um objeto Login para teste, checa o usu�rio e imprime na tela usu�rio e senha.
     * @param a args do m�todo main.
     */
    public static void main(String a[]) {
        Login l=Login.getLogin("teste login");
        String usuario="";

        while(!usuario.equals("zz")) {
            l.show(null);
            usuario=l.getUser();
            System.out.println ("usuario: " + usuario + "\nsenha:" + new String(l.getPassword()));
        }
    }

    /**
     * Retorna a tela de login.
     * @return Retorna uma tela Dialog.
     */
    public static JDialog getTelaLogin() {
        return telaLogin;
    }
}

