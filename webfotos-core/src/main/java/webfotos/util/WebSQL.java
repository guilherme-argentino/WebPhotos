package webfotos.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.configuration.Configuration;

/**
 * Recebe uma query de consulta e transmite os dados com criptografia aplicada no método {@link webfotos.util.WebSQL#run() run}() da classe.
 * <I>Esta classe implementa a biblioteca Runnable para iniciar threads</I>.
 */
public class WebSQL implements Runnable {

    String webServer;
    String chave;
    String sqlPlain;
    
    /**
     * Instancia um objeto da classe {@link webfotos.util.Config Config} para obter informações da configuração.
     * Através desse objeto, pega e seta os valores do endereço do site, chave de criptografia.
     * E através do dado recebido como parâmetro, seta o valor de sqlPlain, que armazena uma query.
     * @param sql Query de consulta.
     */
    public WebSQL(String sql) {
        Configuration c = Util.getConfig();
        webServer = c.getString("enderecoWWW");
        chave = c.getString("chaveCripto");
        sqlPlain = sql;
    }

    /**
     * Realiza os procedimentos de transmissão dos dados.
     * Faz consultas ao banco de dados de maneira segura, usando métodos que criptografam os dados.
     * Ao receber os dados do banco, imprime na tela.
     * Este método é obrigratório pois a classe implementa {@link java.lang.Runnable Runnable}. Quer dizer que essa classe será chamada por uma thread e este método será invocado ao se iniciar a thread.
     */
    public void run() {
        try {
            String sqlCripto = Cripto.encripta(sqlPlain, chave);
            Util.log("transmitindo álbum para " + webServer + " (criptografada)");
            Util.log(sqlCripto);

            URL url = new URL(webServer);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);

            // envia o comando sql
            PrintWriter saida = new PrintWriter(urlConnection.getOutputStream());
            saida.println("var=" + sqlCripto);
            saida.close();

            // recebe a resposta
            BufferedReader entrada = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String resposta;

            while ((resposta = entrada.readLine()) != null) {
                System.out.println(resposta);
            }
            entrada.close();

        } catch (FileNotFoundException e) {
            Util.log("ERRO 404 - script não encontrado (" + webServer + ")");
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }


    }

    /**
     * Método principal.
     * Armazena uma query de consulta e inicia uma thread com a própria classe, enviando essa query como parâmetro.
     * @param t args do método main.
     */
    public static void main(String[] t) {
        String sql = "select albuns.albumID as ID, categorias.nmcategoria, albuns.nmalbum as Pauta, DATE_FORMAT(albuns.DtInsercao, '%d/%m/%y') as Data from albuns left join fotos using(albumID) left join creditos using(creditoID) left join categorias on albuns.categoriaID=categorias.categoriaID where creditos.nome='Divulgação'";

        Thread ponteSQL = new Thread(new WebSQL(sql));
        ponteSQL.start();
    }
}
