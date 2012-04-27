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
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import net.sf.webphotos.gui.util.Login;
import net.sf.webphotos.util.Util;

import com.sun.rowset.JdbcRowSetImpl;

/**
 * A classe BancoImagem manipula dados das imagens atrav�s da conex�o com banco de dados.
 * Classe do tipo Singleton, � permitido apenas uma inst�ncia da classe.
 * O objeto � acess�vel unicamente atrav�s da classe.
 * Mant�m uma conexao permanente com banco de dados.
 */
public class BancoImagem {
    
    private static Logger log = Logger.getLogger(BancoImagem.class);
    
    private static final BancoImagem instancia=new BancoImagem();

    private static String titulo;
    private static String descricao;
    private static int categoriaID;

    private static String url;
    private static String driver;
    private static Connection conn;
    private static File albunsRoot;

    // informa��es sobre uso da ponte www
    private static boolean utilizarPonteWWW;
    private static String webServer;
    private static String chaveCripto;

    // usu�rio principal do sistema
    private static String usuario=null;
    private static char[] senha=null;

    // caso o usu�rio ftp seja diferente...
    private static String usuarioFTP=null;
    private static char[] senhaFTP=null;

    private static Login login;
    private static RowSet rSet;

    // inicializa o banco de dados
    private BancoImagem() {
        Configuration c = Util.getConfig();
        log.info("inicializando banco de imagem...");
        webServer=c.getString("enderecoWWW");
        chaveCripto=c.getString("chaveCripto");

        if(webServer!=null && chaveCripto!=null) {
            log.info("utilizando ponte http");
            utilizarPonteWWW=true;
        }
    }

    /**
     * Retorna o objeto BancoImagem instanciado na pr�pria classe.
     * @return Retorna o objeto BancoImagem.
     */
    public static BancoImagem getBancoImagem() { return instancia; }

    /**
     * Recebe o ID de um alb�m e retorna o caminho do path local
     * @param albumID ID do alb�m.
     * @return Retorna o caminho do path local.
     */
    public static String getLocalPath(int albumID) {
        if(albunsRoot==null)
            albunsRoot=new File(Util.getConfig().getString("albunsRoot"));

        File localFile = new File(albunsRoot,Integer.toString(albumID));
        if(!localFile.isDirectory()) localFile.mkdirs();
        return localFile.getAbsolutePath();
    }

    /**
     * Configura a URL e o driver do DB.
     * @param dbUrl URL do DB.
     * @param dbDriver Driver do DB.
     * @throws java.lang.ClassNotFoundException Lan�a exce��o caso a classe espec�fica n�o seja encontrada.
     * @throws java.lang.InstantiationException Lan�a exce��o caso n�o permita a inst�ncia de um objeto da classe.
     * @throws java.lang.IllegalAccessException Lan�a exce��o se ocorrer um acesso qualquer e o n�vel de seguran�a n�o permitir.
     * @throws java.sql.SQLException Lan�a exce��o caso ocorra algum erro ao acessar o banco de dados.
     */
    public void configure(String dbUrl, String dbDriver)
            throws  ClassNotFoundException,
                    InstantiationException,
                    IllegalAccessException,
                    SQLException {
        url=dbUrl;
        driver=dbDriver;
        Class.forName(dbDriver).newInstance();
        log.info ("Driver " + dbDriver + " carregado");
    }

    /**
     * Retorna uma conex�o ao banco de dados. 
     * Testa se a conex�o j� esta aberta, caso positivo retorna a conex�o, caso contr�rio pede o login e faz a conex�o.
     * @return Retorna a conex�o com o banco de dados.
     * @throws java.sql.SQLException Lan�a exce��o caso ocorra algum erro ao acessar o banco de dados.
     * Mais detalhes, veja em {@link java.sql.DriverManager#getConnection(String, String, String) getConnection()}
     */
    public static Connection getConnection() throws SQLException {
        try {
            if(conn != null &&
                conn.isClosed() == false) {
                
                log.debug("Usando a conex�o existente");
                return conn;
            }
        } catch (AbstractMethodError amE) {
            log.warn("Error getting conection", amE);
        }
        // conex�o fechada...	
        if(usuario==null) login();
        conn=DriverManager.getConnection(url,usuario,(new String(senha)));
        log.debug("Usando uma nova conex�o");
        return conn;
    }

    /**
     * Fecha uma conex�o com o banco de dados.
     * Testa se a conex�o esta aberta e encerra a mesma.
     * @throws java.sql.SQLException Lan�a exce��o caso ocorra algum erro ao acessar o banco de dados.
     */
    public static void closeConnection() throws SQLException {
        if(conn.isClosed()==false) conn.close();
        log.debug ("Conexao ao banco de dados fechada");
    }

    /**
     * Retorna <I>true</I> caso o login seja efetuado ou <I>false</I> caso n�o.
     * Faz uso da fun��o {@link webfotos.BancoImagem#login(String) login(String title)} para obter o resultado.
     * @return Retorno l�gico para a opera��o de login.
     */
    public static boolean login() {
        return login("WebFotos - Login BD");
    }

    /**
     * Inicia o login partir de um nome passado como par�metro.
     * Esse nome realizar� altera��o na instancia da classe {@link webfotos.gui.util.Login Login}.
     * Faz a compara��o com o banco de dados atrav�s do {@link javax.sql.RowSet RowSet} e retorna uma vari�vel l�gica para informar se o login ocorreu com sucesso.
     * @param title T�tulo do login.
     * @return Retorno l�gico para a opera��o de login.
     */
    public static boolean login(String title) {
        Login l=Login.getLogin(title);
        boolean conectado=false;

        do {
            l.show();
            if(l.getUser()==null) System.exit(0);

            usuario=l.getUser();
            senha=l.getPassword();
            try {
                conn=DriverManager.getConnection(url,usuario,(new String(senha)));
                
                rSet = new JdbcRowSetImpl(conn);
                rSet.setReadOnly(false);
                rSet.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
                rSet.setConcurrency(ResultSet.CONCUR_UPDATABLE);
                ((JdbcRowSet)rSet).setAutoCommit(false);
                
                conectado=true;
            } catch (Exception e) {
                String msg = "Erro na conex�o ao banco de dados";
                log.error(msg, e);
                JOptionPane.showMessageDialog(null,e.getMessage(),msg,JOptionPane.ERROR_MESSAGE);
            }
        } while(!conectado);
        
        Login.getTelaLogin().dispose();
        return true;
    }

    /**
     * Retorna o usu�rio.
     * @return Retorna um usu�rio.
     */
    public String getUser() { return usuario; }
    
    /**
     * Retorna a senha do usu�rio.
     * @return Retorna uma senha.
     */
    public char[] getPassword() { return senha; }
    
    /**
     * Retorna o usu�rio de FTP.
     * @return Retorna um usu�rio.
     */
    public String getUserFTP() { return usuarioFTP; }
    
    /**
     * Retorna a senha do usu�rio de FTP.
     * @return Retorna uma senha.
     */
    public char[] getPasswordFTP() { return senhaFTP; }
    
    /**
     * Seta o usu�rio de FTP.
     * @param u Usu�rio.
     */
    public void setUserFTP(String u) { usuarioFTP=u; }
    
    /**
     * Seta a senha do usu�rio de FTP.
     * @param p Senha.
     */
    public void setPasswordFTP(char[] p) { senhaFTP=p; }
    
    /**
     * Retorna o {@link javax.sql.RowSet RowSet} rSet da instancia de BancoImagem.
     * @return Retorna o {@link javax.sql.RowSet RowSet} da inst�ncia.
     */
    public static RowSet getRSet() { return rSet; }

    /**
     * Altera o {@link javax.sql.RowSet RowSet} rSet da instancia de BancoImagem.
     * @param aRSet o novo {@link javax.sql.RowSet RowSet} da inst�ncia.
     */
    public static void setRSet(RowSet aRSet) {
        rSet = aRSet;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton Object");
    }

	public static void loadUIManager() {
	    String lookAndFeel = Util.getConfig().getString("UIManager.lookAndFeel");
	    try {
	        UIManager.setLookAndFeel(lookAndFeel);
	    } catch (Exception e) {
	        log.warn("Caution: Theme not correctly configured");
	        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    }
	}

	public static void loadDBDriver() throws IllegalAccessException, SQLException, ClassNotFoundException, InstantiationException {
	    // obt�m driver do db
	    url = Util.getConfig().getString("jdbc.url");
	    driver = Util.getConfig().getString("jdbc.driver");
	    getBancoImagem().configure(url, driver);
	}

}
