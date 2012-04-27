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

import net.sf.webphotos.dao.jpa.PhotoDAO;
import net.sf.webphotos.model.PhotoVO;
import java.awt.Dimension;
import java.awt.MediaTracker;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.ImageIcon;
import net.sf.webphotos.util.ApplicationContextResource;
import net.sf.webphotos.util.Util;


/**
 * A classe Photo armazena dados específicos de uma foto.
 * Dentre os dados estão ID da foto,ID do album e ID do crédito, legenda, crédito e resolução de tela.
 */
public class Photo {
    private int fotoID=0;
    private int albumID=-1;
    private int creditoID=0;
    private String legenda=null;
    private int largura=0;
    private int altura=0;
    
    @Deprecated
    private long tamanhoBytes=0;
    
    private String creditoNome="";
    private String caminhoArquivo="";
    private static PhotoDAO photosDAO = (PhotoDAO) ApplicationContextResource.getBean("photosDAO");

    private static String[][] creditos=null;
    
    public Photo(PhotoVO photoVO) {
        this(photoVO.getFotoid(), photoVO.getAlbum().getAlbumid(), photoVO.getLegenda(), photoVO.getCreditos().getCreditoid(),photoVO.getCreditos().getNome(), 0, 0, 0);
    }

    /**
     * Construtor da classe Photo.
     * Recebe e seta todos os dados da foto.
     * @param ID ID da foto.
     * @param albumID ID do albï¿½m.
     * @param fotoLegenda Legenda da foto.
     * @param fotoCreditoID ID do crï¿½dito.
     * @param fotoCreditoNome Crï¿½dito.
     * @param fotoLargura Largura da foto.
     * @param fotoAltura Altura da foto.
     */
    public Photo(int ID, int albumID, String fotoLegenda, int fotoCreditoID, String fotoCreditoNome, int fotoLargura, int fotoAltura, long tamanhoBytes) {
        this.fotoID         = ID;
        this.albumID        = albumID;
        this.legenda        = fotoLegenda;
        this.creditoID      = fotoCreditoID;
        this.creditoNome    = fotoCreditoNome;
        this.largura        = fotoLargura;
        this.altura         = fotoAltura;
        this.tamanhoBytes   = tamanhoBytes;
    }
    
    /**
     * Contrutor da classe Photo.
     * Recebe apenas nome do arquivo como parï¿½metro.
     * Seta a legenda com o valor vazio, e seta caminhoArquivo com o nome recebido como parï¿½metro.
     * Carrega a foto a partir do nome do arquivo, obtï¿½m medidas da foto e seta as variï¿½veis de largura e altura.
     * @param arquivo Nome ou caminho do arquivo.
     */
    public Photo(String arquivo) {
        legenda="";
        caminhoArquivo=arquivo;
        // obtem medidas da foto
        ImageIcon a=new ImageIcon(arquivo);

        if(a.getImageLoadStatus()==MediaTracker.COMPLETE) {
            largura=a.getIconWidth();
            altura=a.getIconHeight();
            tamanhoBytes=new File(arquivo).length();
        } else {
            Util.log("[Foto.Foto]/ERRO: " + arquivo + " Não pode ser lido");
        }
    }

    /**
     * Retorna o ID da foto.
     * @return Retorna um ID.
     */
    public int getFotoID() { return fotoID; }
    /**
     * Retorna o ID do crï¿½dito.
     * @return Retorna um ID.
     */
    public int getCreditoID() { return creditoID; }
    /**
     * Retorna o crï¿½dito da foto.
     * @return Retorna o crï¿½dito.
     */
    public String getCreditoNome() { return creditoNome; }
    /**
     * Retorna a legenda da foto.
     * @return Retorna a legenda.
     */
    public String getLegenda() { return legenda; }
    /**
     * Retorna o tamanho da largura da foto.
     * @return Retorna o valor da largura.
     */
    public int getLargura() { return largura; }
    /**
     * Retorna o tamanho da altura da foto.
     * @return Retorna o valor da altura.
     */
    public int getAltura() { return altura; }
    /**
     * Retorna uma resolução especï¿½fica com os valores de altura e largura.
     * Para entender melhor os conceitos de dimensão veja {@link java.awt.Dimension Dimension}
     * @return Retorna uma resolução.
     */
    public Dimension getResolucao() { return new Dimension(largura,altura); }
    /**
     * Retorna o nome ou o caminho do arquivo.
     * @return Retorna o caminho de um arquivo
     */
    public String getCaminhoArquivo() { return caminhoArquivo; }

    /**
     * Seta o ID da foto.
     * @param f ID da foto.
     */
    public void setFotoID(int f) { fotoID=f; }
    /**
     * Seta o ID do crï¿½dito.
     * @param c ID do crï¿½dito.
     */
    public void setCreditoID(int c) { creditoID=c; }
    /**
     * Seta a legenda da foto.
     * @param l Legenda.
     */
    public void setLegenda(String l) { legenda=l; }
    /**
     * Seta o valor da largura da foto.
     * @param l Largura da foto.
     */
    public void setLargura(int l) { largura=l; }
    /**
     * Seta o valor da altura da foto.
     * @param a Altura da foto.
     */
    public void setAltura(int a) { altura=a; }
    /**
     * Seta o valor da resolução da foto.
     * @param r Dimensão da foto.
     * Para entender melhor os conceitos de dimensão veja {@link java.awt.Dimension Dimension}
     */
    public void setResolucao(Dimension r) {
        largura=r.width;
        altura=r.height;
    }
    /**
     * Seta o crï¿½dito da foto e completa o creditoID.
     * @param nome Crï¿½dito.
     */
    public void setCreditoNome(String nome) {
        creditoNome=nome;
        // pesquisa no array creditos e completa creditoID
        if(creditos != null) {
            for(int i=0; i < creditos.length; i++) {
                if(creditoNome.equals(creditos[i][1])) {
                    creditoID=Integer.parseInt(creditos[i][0]);
                    break;
                }
            }
        }
    }

    /**
     * Retorna um vetor com os valores de crï¿½dito.
     * Checa se o vetor jï¿½ possui valores, caso contrï¿½rio utiliza a função {@link webfotos.Photo#populaCreditos() populaCreditos()} completar os valores.
     * @return Retorna uma lista com os crï¿½ditos.
     */
    public static String[] getCreditosArray() {
        int tamanho;
        // executa a instrucao sql somente na primeira vez
        if(creditos == null) {
            try {
                Util.log("[Foto.getCreditosArray]/AVISO: Populando Crï¿½ditos");
                populaCreditos();
                tamanho = creditos.length;
            } catch (SQLException ex) {
                Util.log("[Foto.getCreditosArray]/ERRO: Impossível popular crï¿½ditos - " + ex);
                tamanho = 0;
            }
        } else {
            tamanho = creditos.length;
        }

        String[] nomesCreditos=new String[tamanho];
        for(int i=0; i < tamanho; i++)
            nomesCreditos[i]=creditos[i][1];

        return nomesCreditos;
    }
    
    /**
     * Retorna um ï¿½ndice da matriz crï¿½ditos dado um nome de crï¿½dito.
     * @param nomeCredito Crï¿½dito.
     * @return Retorna um ï¿½ndice numï¿½rico.
     */
    public static int getLstCreditosIndex(String nomeCredito) {
        for(int i=0; i < creditos.length; i++) {
            if(nomeCredito.compareTo(creditos[i][1])==0) return i;
        }

        return 0;
    }
   
    /**
     * Retorna o ID do crédito.
     * Faz a busca do ID na matriz credito através do nome especificado.
     * @param nomeCredito Crédito.
     * @return Retorna um ID numérico.
     */
    public static int getLstCreditosID(String nomeCredito) {
        for(int i=0; i < creditos.length; i++) {
            if(nomeCredito.equals(creditos[i][1])) return Integer.parseInt(creditos[i][0]);
        }
        return 0;
    }

    /**
     * Busca no banco de dados, os valores para setar a matriz creditos
     * @throws java.sql.SQLException Lança exceção caso ocorra algum erro no acesso ao banco de dados.
     */
    public static void populaCreditos() throws SQLException {
        String sql=Util.getConfig().getString("sql7");
        
        List<Object[]> tableData = photosDAO.findByNativeQuery(sql);

        creditos=new String[tableData.size()][2];
        
        int ct=0;
        for (Object[] objects : tableData) {
            creditos[ct][0]=objects[1].toString();
            creditos[ct][1]=objects[2].toString();
            ct++;
        }

    }

    /**
     * Retorna todos os valores das variï¿½veis de Photo em uma ï¿½nica String.
     * @return Retorna os valores da foto.
     */
    @Override
    public String toString() {
        return "fotoID: " + fotoID + "\ncreditoID: " + creditoID + "\ncreditoNome: " + creditoNome + "\nlargura: " + largura + "\naltura: " + altura + "\narquivo: " + caminhoArquivo + "\nlegenda: " + legenda;
    }
    
    /**
     * Limpa o valor da variï¿½vel caminhoArquivo.
     * Seta com o campo vazio.
     */
    public void resetCaminhoArquivo() { caminhoArquivo=""; }
    
    /**
     * Faz a atualização dos dados da foto.
     * Checa se a foto já possui cadastro, caso Não possui faz inclusão e faz a atualização. Caso jï¿½ possua cadastro, sï¿½ atualiza os dados.
     * @throws java.lang.Exception Lança qualquer tipo de exceção que possa interromper o fluxo da função.
     */
    public void atualizaFoto() throws Exception {
        int ultimoFotoID=-1;
        String sql;
        Statement st = null;
        
        // INSERT para fotos ainda Não cadastradas
        if(caminhoArquivo.length() > 0) {
           try {
                sql="select max(fotoID) from fotos";
                Integer tableData = (Integer) photosDAO.createNativeQuery(sql).getSingleResult();

                ultimoFotoID=tableData.intValue();
            } catch (Exception ex) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + ex);
            }
            setFotoID(ultimoFotoID);

            try {
//                rowSet.moveToInsertRow();
//                rowSet.updateInt("fotoID", ++ultimoFotoID);
//                rowSet.updateInt("albumID", this.albumID);
//                rowSet.updateInt("creditoID", this.creditoID);
//                rowSet.updateString("legenda", this.legenda);
//                rowSet.updateInt("altura", this.altura);
//                rowSet.updateInt("largura", this.largura);
//                rowSet.updateLong("tamanho", this.tamanhoBytes);
//                
//                rowSet.insertRow();
            } catch (Exception e) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + e);
                throw e;
            }

        // UPDATE para fotos já cadastradas
        } else {
            sql="SELECT creditoID, legenda, altura, largura, tamanho FROM fotos WHERE fotoID=" + fotoID;
            try {
//                rowSet.setCommand(sql);
//                rowSet.execute();
//                rowSet.first();
//                
//                fotoID = rowSet.getInt("fotoID");
//                
//                rowSet.updateInt("fotoID", this.fotoID);
//                rowSet.updateInt("albumID", this.albumID);
//                rowSet.updateInt("creditoID", this.creditoID);
//                rowSet.updateString("legenda", this.legenda);
//                rowSet.updateInt("altura", this.altura);
//                rowSet.updateInt("largura", this.largura);
//                rowSet.updateLong("tamanho", this.tamanhoBytes);
//                
//                rowSet.updateRow();
                
            } catch (Exception e) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + e);
                throw e;
            }
        }
        
        try {
            st.close();
        } catch (Exception e) {}
    }

    /**
     * Retorna o ID do albï¿½m.
     * @return Retorna um ID.
     */
    public int getAlbumID() {
        return albumID;
    }

    /**
     * Seta o valor do ID do albï¿½m
     * @param albumID ID do albï¿½m.
     */
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
}
