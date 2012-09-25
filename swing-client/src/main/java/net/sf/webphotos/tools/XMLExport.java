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
package net.sf.webphotos.tools;

import java.sql.*;

/**
 * Classe responsável por exportações no formato XML. Os dados são trabalhados
 * somente no método principal, com conexão ao banco.
 */
public class XMLExport {

    /**
     * Segue algumas rotinas para exportar ao formato XML. Primeiro executa uma
     * conexão com banco de dados, caso não realizado com sucesso, retorna
     * mensagem de erro. Após a conexão estabelecida, começa a gerar o xml.
     * Executa uma query para buscar os dados e os imprime. Essa última parte
     * não está finalizada.
     *
     * @param a Argumentos do método main.
     */
    public static void main(String a[]) {
        Connection conn = null;
        Statement st1;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("driver carregado");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.100.1/saopaulo", "root", "");
        } catch (Exception e) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("BancoImagem.erro: nao foi possivel carregar o driver do banco de dados");
            System.out.println("               erro: " + e.toString());
            System.out.println("-------------------------------------------------------------------");
            System.exit(0);
        }



        // começa a gerar o xml
        try {
            String sql1 = "select albumID, nmCategoria, nmAlbum, descricao, DATE_FORMAT(dtInsercao,'%d/%m/%y') as dtInsercao from albuns left join categorias using (categoriaID) limit 10";
            st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);
            while (rs1.next()) {
                System.out.println(rs1.getString("descricao"));
            }
            rs1.close();
            st1.close();
            conn.close();

        } catch (Exception ex) {
        }


    }
}
