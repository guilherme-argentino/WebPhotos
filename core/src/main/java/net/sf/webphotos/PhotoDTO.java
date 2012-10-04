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

import java.awt.Dimension;
import java.awt.MediaTracker;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.ImageIcon;
import net.sf.webphotos.dao.jpa.PhotoDAO;
import net.sf.webphotos.entity.IsCredits;
import net.sf.webphotos.entity.PhotoEntity;
import net.sf.webphotos.model.CreditsVO;
import net.sf.webphotos.model.PhotoVO;
import net.sf.webphotos.util.ApplicationContextResource;
import net.sf.webphotos.util.Util;

/**
 * A classe PhotoDTO armazena dados específicos de uma foto. Dentre os dados estão
 * ID da foto,ID do album e ID do crédito, legenda, crédito e resolução de tela.
 */
public class PhotoDTO extends PhotoEntity {

    private int fotoID = 0;
    private int albumID = -1;
    private int creditoID = 0;
    private String legenda = null;
    private int largura = 0;
    private int altura = 0;
    @Deprecated
    private long tamanhoBytes = 0;
    private String creditoNome = "";
    private String caminhoArquivo = "";
    private static PhotoDAO photosDAO = (PhotoDAO) ApplicationContextResource.getBean("photosDAO");
    private static String[][] creditos = null;

    public PhotoDTO(PhotoVO photoVO) {
        this(photoVO.getFotoid(), photoVO.getAlbum().getAlbumid(), photoVO.getLegenda(), photoVO.getCreditos().getCreditoid(), photoVO.getCreditos().getNome(), 0, 0, 0);
    }

    /**
     * Construtor da classe PhotoDTO. Recebe e seta todos os dados da foto.
     *
     * @param ID ID da foto.
     * @param albumID ID do album.
     * @param fotoLegenda Legenda da foto.
     * @param fotoCreditoID ID do créito.
     * @param fotoCreditoNome Crédito.
     * @param fotoLargura Largura da foto.
     * @param fotoAltura Altura da foto.
     */
    public PhotoDTO(int ID, int albumID, String fotoLegenda, int fotoCreditoID, String fotoCreditoNome, int fotoLargura, int fotoAltura, long tamanhoBytes) {
        this.fotoID = ID;
        this.albumID = albumID;
        this.legenda = fotoLegenda;
        this.creditoID = fotoCreditoID;
        this.creditoNome = fotoCreditoNome;
        this.largura = fotoLargura;
        this.altura = fotoAltura;
        this.tamanhoBytes = tamanhoBytes;
    }

    /**
     * Contrutor da classe PhotoDTO. Recebe apenas nome do arquivo como
     * parametro. Seta a legenda com o valor vazio, e seta caminhoArquivo com
     * o nome recebido como parametro. Carrega a foto a partir do nome do
     * arquivo, obtem medidas da foto e seta as variaveis de largura e
     * altura.
     *
     * @param arquivo Nome ou caminho do arquivo.
     */
    public PhotoDTO(String arquivo) {
        legenda = "";
        caminhoArquivo = arquivo;
        // obtem medidas da foto
        ImageIcon a = new ImageIcon(arquivo);

        if (a.getImageLoadStatus() == MediaTracker.COMPLETE) {
            largura = a.getIconWidth();
            altura = a.getIconHeight();
            tamanhoBytes = new File(arquivo).length();
        } else {
            Util.log("[Foto.Foto]/ERRO: " + arquivo + " Não pode ser lido");
        }
    }

    /**
     * Retorna o ID da foto.
     *
     * @return Retorna um ID.
     */
    public int getFotoID() {
        return fotoID;
    }

    /**
     * Retorna o ID do crédito.
     *
     * @return Retorna um ID.
     */
    public int getCreditoID() {
        return creditoID;
    }

    /**
     * Retorna o crédito da foto.
     *
     * @return Retorna o crédito.
     */
    public String getCreditoNome() {
        return creditoNome;
    }

    /**
     * Retorna a legenda da foto.
     *
     * @return Retorna a legenda.
     */
    @Override
    public String getLegenda() {
        return legenda;
    }

    /**
     * Retorna o tamanho da largura da foto.
     *
     * @return Retorna o valor da largura.
     */
    public int getLargura() {
        return largura;
    }

    /**
     * Retorna o tamanho da altura da foto.
     *
     * @return Retorna o valor da altura.
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Retorna uma resolução especifica com os valores de altura e largura.
     * Para entender melhor os conceitos de dimensão veja {@link java.awt.Dimension Dimension}
     *
     * @return Retorna uma resolução.
     */
    public Dimension getResolucao() {
        return new Dimension(largura, altura);
    }

    /**
     * Retorna o nome ou o caminho do arquivo.
     *
     * @return Retorna o caminho de um arquivo
     */
    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    /**
     * Seta o ID da foto.
     *
     * @param f ID da foto.
     */
    public void setFotoID(int f) {
        fotoID = f;
    }

    /**
     * Seta o ID do credito.
     *
     * @param c ID do credito.
     */
    public void setCreditoID(int c) {
        creditoID = c;
    }

    /**
     * Seta a legenda da foto.
     *
     * @param l Legenda.
     */
    public void setLegenda(String l) {
        legenda = l;
    }

    /**
     * Seta o valor da largura da foto.
     *
     * @param l Largura da foto.
     */
    public void setLargura(int l) {
        largura = l;
    }

    /**
     * Seta o valor da altura da foto.
     *
     * @param a Altura da foto.
     */
    public void setAltura(int a) {
        altura = a;
    }

    /**
     * Seta o valor da resolução da foto.
     *
     * @param r Dimensão da foto. Para entender melhor os conceitos de dimensão
     * veja {@link java.awt.Dimension Dimension}
     */
    public void setResolucao(Dimension r) {
        largura = r.width;
        altura = r.height;
    }

    /**
     * Seta o credito da foto e completa o creditoID.
     *
     * @param nome Credito.
     */
    public void setCreditoNome(String nome) {
        creditoNome = nome;
        // pesquisa no array creditos e completa creditoID
        if (creditos != null) {
            for (int i = 0; i < creditos.length; i++) {
                if (creditoNome.equals(creditos[i][1])) {
                    creditoID = Integer.parseInt(creditos[i][0]);
                    break;
                }
            }
        }
    }

    /**
     * Retorna um vetor com os valores de credito. Checa se o vetor ja
     * possui valores, caso contrario utiliza a função {@link net.sf.webphotos.PhotoDTO#populaCreditos() populaCreditos()}
     * completar os valores.
     *
     * @return Retorna uma lista com os creditos.
     */
    public static String[] getCreditosArray() {
        int tamanho;
        // executa a instrucao sql somente na primeira vez
        if (creditos == null) {
            try {
                Util.log("[Foto.getCreditosArray]/AVISO: Populando Créditos");
                populaCreditos();
                tamanho = creditos.length;
            } catch (SQLException ex) {
                Util.log("[Foto.getCreditosArray]/ERRO: Impossível popular Créditos - " + ex);
                tamanho = 0;
            }
        } else {
            tamanho = creditos.length;
        }

        String[] nomesCreditos = new String[tamanho];
        for (int i = 0; i < tamanho; i++) {
            nomesCreditos[i] = creditos[i][1];
        }

        return nomesCreditos;
    }

    /**
     * Retorna um índice da matriz créditos dado um nome de crédito.
     *
     * @param nomeCredito Crédito.
     * @return Retorna um índice numérico.
     */
    public static int getLstCreditosIndex(String nomeCredito) {
        for (int i = 0; i < creditos.length; i++) {
            if (nomeCredito.compareTo(creditos[i][1]) == 0) {
                return i;
            }
        }

        return 0;
    }

    /**
     * Retorna o ID do crédito. Faz a busca do ID na matriz credito através do
     * nome especificado.
     *
     * @param nomeCredito Crédito.
     * @return Retorna um ID numérico.
     */
    public static int getLstCreditosID(String nomeCredito) {
        for (int i = 0; i < creditos.length; i++) {
            if (nomeCredito.equals(creditos[i][1])) {
                return Integer.parseInt(creditos[i][0]);
            }
        }
        return 0;
    }

    /**
     * Busca no banco de dados, os valores para setar a matriz creditos
     *
     * @throws java.sql.SQLException Lança exceção caso ocorra algum erro no
     * acesso ao banco de dados.
     */
    public static void populaCreditos() throws SQLException {
        String sql = Util.getConfig().getString("sql7");

        List<Object[]> tableData = photosDAO.findByNativeQuery(sql);

        creditos = new String[tableData.size()][2];

        int ct = 0;
        for (Object[] objects : tableData) {
            creditos[ct][0] = objects[0].toString();
            creditos[ct][1] = objects[1].toString();
            ct++;
        }

    }

    /**
     * Retorna todos os valores das variaveis de PhotoDTO em uma unica String.
     *
     * @return Retorna os valores da foto.
     */
    @Override
    public String toString() {
        return "fotoID: " + fotoID + "\ncreditoID: " + creditoID + "\ncreditoNome: " + creditoNome + "\nlargura: " + largura + "\naltura: " + altura + "\narquivo: " + caminhoArquivo + "\nlegenda: " + legenda;
    }

    /**
     * Limpa o valor da variavel caminhoArquivo. Seta com o campo vazio.
     */
    public void resetCaminhoArquivo() {
        caminhoArquivo = "";
    }

    /**
     * Faz a atualização dos dados da foto. Checa se a foto já possui cadastro,
     * caso Não possui faz inclusão e faz a atualização. Caso ja possua
     * cadastro, so atualiza os dados.
     *
     * @throws java.lang.Exception Lança qualquer tipo de exceção que possa
     * interromper o fluxo da função.
     */
    public void atualizaFoto() throws Exception {
        int ultimoFotoID = -1;
        String sql;
        Statement st = null;

        // INSERT para fotos ainda Não cadastradas
        if (caminhoArquivo.length() > 0) {
            try {
                sql = "select max(fotoID) from fotos";
                Integer tableData = (Integer) photosDAO.createNativeQuery(sql).getSingleResult();

                ultimoFotoID = tableData.intValue();
            } catch (Exception ex) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + ex);
            }
            setFotoID(ultimoFotoID);


            try {
                PhotoVO photoVO = new PhotoVO(fotoID, albumID, legenda, creditoID, largura, altura);
                photosDAO.save(photoVO);
            } catch (Exception e) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + e);
                throw e;
            }

            // UPDATE para fotos já cadastradas
        } else {
            try {
                PhotoVO photoVO = photosDAO.findBy(fotoID);
                photoVO.setLegenda(legenda);
                photosDAO.save(photoVO);
            } catch (Exception e) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + e);
                throw e;
            }
        }

        try {
            st.close();
        } catch (Exception e) {
        }
    }

    /**
     * Retorna o ID do album.
     *
     * @return Retorna um ID.
     */
    public int getAlbumID() {
        return albumID;
    }

    /**
     * Seta o valor do ID do album
     *
     * @param albumID ID do album.
     */
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    @Override
    public String getKey() {
        return this.caminhoArquivo;
    }

    @Override
    public IsCredits getCreditos() {
        return new CreditsVO(this.creditoNome);
    }
}
