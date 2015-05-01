/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.webphotos;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.RowSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.webphotos.gui.util.Login;
import net.sf.webphotos.util.Util;
import org.apache.log4j.Logger;

/**
 * A classe BancoImagem manipula dados das imagens através da conexão com banco
 * de dados. Classe do tipo Singleton, é permitido apenas uma instância da
 * classe. O objeto é acessível unicamente através da classe. Mantém uma conexao
 * permanente com banco de dados.
 */
public class BancoImagem {

    private static Logger log = Logger.getLogger(BancoImagem.class);
    private static final BancoImagem instancia = new BancoImagem();
    private static String url;
    private static String driver;
    private static Connection conn;
    private static File albunsRoot;
    // usuário principal do sistema
    private static String usuario = null;
    private static char[] senha = null;
    // caso o usuário ftp seja diferente...
    private static String usuarioFTP = null;
    private static char[] senhaFTP = null;

    // inicializa o banco de dados
    private BancoImagem() {
        log.info("inicializando banco de imagem...");
    }

    /**
     * Retorna o objeto BancoImagem instanciado na própria classe.
     *
     * @return Retorna o objeto BancoImagem.
     */
    public static BancoImagem getBancoImagem() {
        return instancia;
    }

    /**
     * Recebe o ID de um albúm e retorna o caminho do path local
     *
     * @param albumID ID do albúm.
     * @return Retorna o caminho do path local.
     */
    public static String getLocalPath(int albumID) {
        if (albunsRoot == null) {
            albunsRoot = new File(Util.getConfig().getString("albunsRoot"));
        }

        File localFile = new File(albunsRoot, Integer.toString(albumID));
        if (!localFile.isDirectory()) {
            localFile.mkdirs();
        }
        return localFile.getAbsolutePath();
    }

    /**
     * Configura a URL e o driver do DB.
     *
     * @param dbUrl URL do DB.
     * @param dbDriver Driver do DB.
     * @throws java.lang.ClassNotFoundException Lança exceção caso a classe
     * específica não seja encontrada.
     * @throws java.lang.InstantiationException Lança exceção caso não permita a
     * instância de um objeto da classe.
     * @throws java.lang.IllegalAccessException Lança exceção se ocorrer um
     * acesso qualquer e o nível de segurança não permitir.
     * @throws java.sql.SQLException Lança exceção caso ocorra algum erro ao
     * acessar o banco de dados.
     */
    public void configure(String dbUrl, String dbDriver)
            throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            SQLException {
        url = dbUrl;
        driver = dbDriver;
        Class.forName(dbDriver).newInstance();
        log.info("Driver " + dbDriver + " carregado");
    }

    /**
     * Retorna uma conexão ao banco de dados. Testa se a conexão já esta aberta,
     * caso positivo retorna a conexão, caso contrário pede o login e faz a
     * conexão.
     *
     * @return Retorna a conexão com o banco de dados.
     * @throws java.sql.SQLException Lança exceção caso ocorra algum erro ao
     * acessar o banco de dados. Mais detalhes, veja em
     * {@link java.sql.DriverManager#getConnection(String, String, String) getConnection()}
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (conn != null
                    && conn.isClosed() == false) {

                log.debug("Usando a conexão existente");
                return conn;
            }
        } catch (AbstractMethodError amE) {
            log.warn("Error getting conection", amE);
        }
        // conexão fechada...	
        if (usuario == null) {
            login();
        }
        conn = DriverManager.getConnection(url, usuario, (new String(senha)));
        log.debug("Usando uma nova conexão");
        return conn;
    }

    /**
     * Fecha uma conexão com o banco de dados. Testa se a conexão esta aberta e
     * encerra a mesma.
     *
     * @throws java.sql.SQLException Lança exceção caso ocorra algum erro ao
     * acessar o banco de dados.
     */
    public static void closeConnection() throws SQLException {
        if (conn.isClosed() == false) {
            conn.close();
        }
        log.debug("Conexao ao banco de dados fechada");
    }

    /**
     * Retorna <I>true</I> caso o login seja efetuado ou <I>false</I> caso não.
     * Faz uso da função
     * {@link net.sf.webphotos.BancoImagem#login(String) login(String title)}
     * para obter o resultado.
     *
     * @return Retorno lógico para a operação de login.
     */
    public static boolean login() {
        return login("WebPhotos - Login BD");
    }

    /**
     * Inicia o login partir de um nome passado como parâmetro. Esse nome
     * realizará alteração na instancia da classe
     * {@link net.sf.webphotos.gui.util.Login Login}. Faz a comparação com o
     * banco de dados através do {@link javax.sql.RowSet RowSet} e retorna uma
     * variável lógica para informar se o login ocorreu com sucesso.
     *
     * @param title Título do login.
     * @return Retorno lógico para a operação de login.
     */
    public static boolean login(String title) {
        Login l = Login.getLogin(title);
        boolean conectado = false;

        do {
            l.show();
            if (l.getUser() == null) {
                //System.exit(0);
                throw new IllegalArgumentException("User equals null");
            }

            usuario = l.getUser();
            senha = l.getPassword();
            try {
                conn = DriverManager.getConnection(url, usuario, (new String(senha)));
                conectado = true;
            } catch (Exception e) {
                String msg = "Erro na conexão ao banco de dados";
                log.error(msg, e);
                JOptionPane.showMessageDialog(null, e.getMessage(), msg, JOptionPane.ERROR_MESSAGE);
            }
        } while (!conectado);

        Login.getTelaLogin().dispose();
        return true;
    }

    /**
     * Retorna o usuário.
     *
     * @return Retorna um usuário.
     */
    public String getUser() {
        return usuario;
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return Retorna uma senha.
     */
    public char[] getPassword() {
        return senha;
    }

    /**
     * Retorna o usuário de FTP.
     *
     * @return Retorna um usuário.
     */
    public String getUserFTP() {
        return usuarioFTP;
    }

    /**
     * Retorna a senha do usuário de FTP.
     *
     * @return Retorna uma senha.
     */
    public char[] getPasswordFTP() {
        return senhaFTP;
    }

    /**
     * Seta o usuário de FTP.
     *
     * @param u Usuário.
     */
    public void setUserFTP(String u) {
        usuarioFTP = u;
    }

    /**
     * Seta a senha do usuário de FTP.
     *
     * @param p Senha.
     */
    public void setPasswordFTP(char[] p) {
        senhaFTP = p;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton Object");
    }

    /**
     *
     */
    public static void loadUIManager() {
        String lookAndFeel = Util.getConfig().getString("UIManager.lookAndFeel");
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            log.warn("Caution: Theme not correctly configured");
        }
    }

    /**
     *
     * @throws IllegalAccessException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public static void loadDBDriver() throws IllegalAccessException, SQLException, ClassNotFoundException, InstantiationException {
        // obtém driver do db
        url = Util.getConfig().getString("jdbc.url");
        driver = Util.getConfig().getString("jdbc.driver");
        getBancoImagem().configure(url, driver);
    }
}
