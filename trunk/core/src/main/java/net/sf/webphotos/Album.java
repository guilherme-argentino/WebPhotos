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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import net.sf.webphotos.dao.jpa.AlbumDAO;
import net.sf.webphotos.model.AlbumVO;
import net.sf.webphotos.model.PhotoVO;
import net.sf.webphotos.util.ApplicationContextResource;
import net.sf.webphotos.util.Util;
import net.sf.webphotos.util.legacy.CacheFTP;
import org.apache.log4j.Logger;

/**
 * A classe Album mant�m uma cole�ao de fotos em um ArrayList de PhotoDTO, que pode
 * ser manipulada atrav�s das fun��es da pr�pria classe. Classe do tipo
 * Singleton, � permitido apenas uma inst�ncia da classe. O objeto � acess�vel
 * unicamente atrav�s da classe. Tamb�m manipula dados dos IDs, nome do alb�m,
 * descri��o, data de inser��o e categoria.
 */
public class Album {

    private static final Album instanciaAlbum = new Album();
    /**
     * vari�veis do �lbum
     */
    private int albumID = 0;
    private int categoriaID = 0;
    private int usuarioID = 0;
    private String nmAlbum = null;
    private String descricao = null;
    private String dtInsercao = null;
    /**
     * vari�veis utilizadas nessa classe
     */
    private Set<PhotoVO> fotos = new HashSet<PhotoVO>();
    /**
     *
     */
    private Set<PhotoDTO> fotosNovas = new HashSet<PhotoDTO>();
    private String[][] categorias = null;
    private Logger log = Logger.getLogger(this.getClass().getName());
    private static AlbumDAO albunsDAO = (AlbumDAO) ApplicationContextResource.getBean("albunsDAO");

    /**
     * nunca usado publicamente (construtor private)
     */
    private Album() {
    }

    /**
     * Retorna o objeto Album instanciado na pr�pria classe.
     *
     * @return Retorna um objeto Album.
     */
    public static Album getAlbum() {
        return instanciaAlbum;
    }

    /**
     * Seta um valor para o ID do alb�m.
     *
     * @param aID ID do �lbum.
     */
    public void setAlbumID(int aID) {
        albumID = aID;
    }

    /**
     * Seta um valor para o ID do usu�rio.
     *
     * @param uID ID do usu�rio.
     */
    public void setUsuarioID(int uID) {
        usuarioID = uID;
    }

    /**
     * Seta um valor para o ID de categoria.
     *
     * @param cID ID da categoria.
     */
    public void setCategoriaID(int cID) {
        categoriaID = cID;
    }

    /**
     * Seta um valor para o nome do alb�m.
     *
     * @param nm Nome do alb�m.
     */
    public void setNmAlbum(String nm) {
        nmAlbum = nm;
    }

    /**
     * Seta um valor para a descri��o do alb�m.
     *
     * @param d Descri��o do alb�m.
     */
    public void setDescricao(String d) {
        descricao = d;
    }

    /**
     * Seta um valor para a data de inser��o do alb�m.
     *
     * @param dt Data de inser��o do alb�m.
     */
    public void setDtInsercao(String dt) {
        dtInsercao = dt;
    }

    /**
     * Retorna o ID do alb�m.
     *
     * @return Retorna um ID.
     */
    public int getAlbumID() {
        return albumID;
    }

    /**
     * Retorna o ID do usu�rio.
     *
     * @return Retorna um ID.
     */
    public int getUsuarioID() {
        return usuarioID;
    }

    /**
     * Retorna o ID da categoria.
     *
     * @return Retorna um ID.
     */
    public int getCategoriaID() {
        return categoriaID;
    }

    /**
     * Retorna o nome do alb�m.
     *
     * @return Retorna um nome.
     */
    public String getNmAlbum() {
        return nmAlbum;
    }

    /**
     * Retorna a descri��o do alb�m.
     *
     * @return Retorna uma descri��o.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a data de inser��o do alb�m.
     *
     * @return Retorna uma data.
     */
    public String getDtInsercao() {
        return dtInsercao;
    }

    /**
     * Retorna uma foto deste album ou null se n�o existir. Faz a busca da foto
     * atrav�s de um ID enviado como par�metro.
     *
     * @param fotoID ID da foto.
     * @return Retorna uma foto.
     */
    public PhotoDTO getFoto(int fotoID) {
        Iterator<PhotoVO> iter = fotos.iterator();

        while (iter.hasNext()) {
            PhotoVO f = iter.next();
            if (f.getFotoid() == fotoID) {
                return new PhotoDTO(f);
            }
        }
        return null;
    }

    /**
     * Retorna uma foto deste album ou null se n�o existir. Faz a busca da foto
     * atrav�s do caminho do arquivo que foi enviado como par�metro.
     *
     * @param caminho Caminho do arquivo foto.
     * @return Retorna uma foto.
     */
    public PhotoDTO getFoto(String caminho) {
        Iterator<PhotoDTO> iter = fotosNovas.iterator();

        while (iter.hasNext()) {
            PhotoDTO f = iter.next();
            if (caminho.equals(f.getCaminhoArquivo())) {
                return f;
            }
        }
        return null;
    }

    /**
     * Retorna toda a cole��o encontrada no ArrayList fotos.
     *
     * @return Retorna a cole��o de fotos.
     */
    public PhotoDTO[] getFotos() {
        return fotos.toArray(new PhotoDTO[fotos.size()]);
    }

    /**
     * Retorna uma matriz com as fotos e seus dados espec�ficos. Armazena para
     * cada foto o seu ID ou caminho, legenda e cr�dito.
     *
     * @return Retorna todas as fotos e seus valores espec�ficos.
     */
    public Object[][] getFotosArray() {
        if (fotos == null && fotosNovas == null) {
            return null;
        }
        Object[][] resultado = new Object[fotos.size()][3];
        Iterator<PhotoVO> iter = fotos.iterator();
        int ct = 0;

        while (iter.hasNext()) {
            PhotoVO f = iter.next();
            if (f.getCaminhoArquivo().length() > 0) {
                // imagem acabou de ser adicionada... sem ID
                resultado[ct][0] = (Object) f.getCaminhoArquivo();
                resultado[ct][1] = (Object) f.getLegenda();
                resultado[ct][2] = (Object) f.getCreditos().getNome();
            } else {
                resultado[ct][0] = (Object) Integer.toString(f.getFotoid());
                resultado[ct][1] = (Object) f.getLegenda();
                resultado[ct][2] = (Object) f.getCreditos().getNome();
            }
            ct++;
        }
        return resultado;
    }

    /**
     * Retorna um vetor que armazenar� os dados de ID, legenda e cr�dito da
     * foto.
     *
     * @return Retorna dados de ID, legenda e cr�dito da foto.
     */
    public String[] getFotosColunas() {
        return new String[]{"ID", "Legenda", "Credito"};
    }

    public String[] getCategoriasArray() {
        return getCategoriasArray(Boolean.FALSE);
    }

    /**
     * Retorna um vetor que armazena as categorias. Checa se categoria �
     * diferente de null. Caso afirmativo, armazena seus valores no vetor criado
     * para retorno.
     *
     * @return Retorna as categorias do alb�m.
     */
    public String[] getCategoriasArray(Boolean force) {
        int tamanho;
        if (categorias == null || force == true) {
            try {
                log.info("Populando Categorias");
                populaCategorias();
                tamanho = categorias.length;
            } catch (SQLException ex) {
                log.error("Imposs�vel popular categorias", ex);
                tamanho = 0;
            }
        } else {
            tamanho = categorias.length;
        }

        String[] nomesCategorias = new String[tamanho];
        for (int i = 0; i < tamanho; i++) {
            nomesCategorias[i] = categorias[i][1];
        }

        return nomesCategorias;
    }

    /**
     * Retorna um �ndice da matriz categorias. Faz a busca atrav�s de um nome
     * enviado como par�metro. Caso n�o seja encontrado pelo nome, retorna o
     * valor 0.
     *
     * @param nomeCategoria Nome da categoria pesquisada.
     * @return Retorna um �ndice de posi��o.
     */
    public int getLstCategoriasIndex(String nomeCategoria) {
        for (int i = 0; i < categorias.length; i++) {
            if (nomeCategoria.equals(categorias[i][1])) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Retorna um �ndice da matriz categorias. Faz a busca atrav�s de um ID
     * enviado como par�metro. Caso n�o seja encontrado pelo ID, retorna o valor
     * 0.
     *
     * @param categoriaID ID da categoria pesquisada.
     * @return Retorna um �ndice de posi��o.
     */
    public int getLstCategoriasIndex(int categoriaID) {
        for (int i = 0; i < categorias.length; i++) {
            if (categoriaID == Integer.parseInt(categorias[i][0])) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Retorna o ID dado um nome de categoria.
     *
     * @param nomeCategoria Nome de categoria pesquisada.
     * @return Retorna um ID.
     */
    public int getLstCategoriasID(String nomeCategoria) {
        for (int i = 0; i < categorias.length; i++) {
            if (nomeCategoria.equals(categorias[i][1])) {
                return Integer.parseInt(categorias[i][0]);
            }
        }
        return -1;
    }

    /**
     * Limpa o ArrayList fotos. Seta o valor 0 para as vari�veis num�ricas e
     * vazio para vari�veis de tipo String.
     */
    public void clear() {
        albumID = 0;
        usuarioID = 0;
        categoriaID = 0;
        nmAlbum = "";
        descricao = "";
        dtInsercao = "";
        if (fotos != null) {
            fotos.clear();
        }
    }

    /**
     * Carrega um alb�m no ArrayList fotos que anteriormente foi salvo no banco
     * de dados. Ap�s limpar os valores em fotos, faz uma busca ao banco de
     * dados para carregar informa��es do alb�m especificado. A compara��o no
     * banco de dados � feita atrav�s do ID do alb�m, passado como par�metro.
     *
     * @param aID ID do alb�m.
     */
    public void loadAlbum(int aID) {

        fotos.clear();

        AlbumVO album = ((AlbumDAO) ApplicationContextResource.getBean("albunsDAO")).findBy(aID);

        if (album != null) {
            albumID = aID;
            categoriaID = album.getCategoriasVO().getCategoriaID();
            usuarioID = 0;
            nmAlbum = album.getNmalbum();
            descricao = album.getDescricao();
            dtInsercao = new SimpleDateFormat("dd/MM/yyyy").format(album.getDtInsercao());
        }

        try {

            /**
             * Popula a collection fotos
             */
            fotos = album.getPhotos();

        } catch (Exception e) {
            log.error("Ocorreu um erro durante a leitura do �lbum no banco de dados", e);
            int selecao = JOptionPane.showConfirmDialog(null,
                    "ERRO durante leitura do �lbum no banco de dados.\n\nTentar Novamente?",
                    "Aviso!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (selecao == JOptionPane.YES_OPTION) {
                /**
                 * TODO: extrair para um m�todo
                 */
                loadAlbum(aID);

            } else {
                log.error("Ocorreu um erro inexperado durante a leitura do �lbum", e);
                JOptionPane.showMessageDialog(null, "ERRO inexperado durante leitura do �lbum - " + e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Recebe um vetor com IDs de alb�ns a serem exclu�dos. Adiciona os alb�ns
     * no arquivo FTP, exclui os alb�ns do banco de dados e por �ltimo exclui os
     * arquivos da pasta local ou rede.
     *
     * @param albunsID IDs dos alb�ns.
     */
    public void excluirAlbuns(int[] albunsID) {
        Statement st = null;
        boolean sucesso = true;

        // passo 1 - adiciona o �lbum no arquivoFTP
        for (int i = 0; i < albunsID.length; i++) {
            CacheFTP.getCache().addCommand(CacheFTP.DELETE, albunsID[i], 0);
        }

        // passo 2 - remover do banco de dados
        try {
            for (int i = 0; i < albunsID.length; i++) {
                albunsDAO.remove(albunsDAO.findBy(i));
            }
            log.info("Exclus�o dos �lbuns e fotos no banco de dados efetuada com sucesso !");
        } catch (Exception e) {
            log.error("Houve um erro na exclusao de albuns no banco de dados", e);
            sucesso = false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (Exception e) {
                log.debug("Can't Close Statement", e);
            }
        }


        // passo 3 - remover os arquivos local ou rede
        for (int i = 0; i < albunsID.length; i++) {
            File diretorio = new File(BancoImagem.getLocalPath(albunsID[i]));
            if (diretorio.isDirectory() == true) {
                // provavelmente tem fotos nesse diretorio
                String[] arquivos = diretorio.list();
                if (arquivos.length > 0) {
                    // apagamos os arquivos desse diretorio
                    for (int j = 0; j < arquivos.length; j++) {
                        File arquivo = new File(BancoImagem.getLocalPath(albunsID[i]) + File.separator + arquivos[j]);
                        if (arquivo.delete() == true) {
                            log.info(BancoImagem.getLocalPath(albunsID[i]) + File.separator + arquivos[j] + " exclu�do com sucesso");
                        } else {
                            log.error("Erro na exclus�o de " + BancoImagem.getLocalPath(albunsID[i]) + File.separator + arquivos[j]);
                            sucesso = false;
                        }
                    }
                }
                if (diretorio.delete() == true) {
                    log.info(BancoImagem.getLocalPath(albunsID[i]) + " exclu�do com sucesso");
                } else {
                    log.error("Erro na exclus�o de " + BancoImagem.getLocalPath(albunsID[i]));
                    sucesso = false;
                }
            }
        } // fim for
        if (sucesso) {
            log.info("�lbum(ns) exclu�do(s) com sucesso.");
        } else {
            log.warn("Finalizando com erros na exclus�o.");
        }
    }

    /**
     * Recebe uma lista com nomes de fotos e faz uma busca no ArrayList fotos,
     * caso encontre, exclui a foto espec�fica. Essa fun��o exclui fotos da
     * cole��o que ainda n�o estejam cadastradas no DB nem feito thumbnails
     * (usu�rio adicionou e quer excluir essas fotos).
     *
     * @param nomes Lista de nomes de fotos.
     */
    public void excluirFotos(String[] nomes) {
        String nome;
        PhotoVO foto;

        for (int i = 0; i < nomes.length; i++) {
            Iterator<PhotoVO> iter = fotos.iterator();
            nome = nomes[i];

            while (iter.hasNext()) {
                foto = iter.next();
                if (nome.equals(foto.getCaminhoArquivo())) {
                    iter.remove();
                    break;
                }
            } // fim while
        } // fim for
    } // fim metodo

    /**
     * Recebe uma lista com IDs das fotos e faz uma busca para excluir as fotos
     * espec�ficas do DB, FTP e FS.
     *
     * @param fotosID Lista de IDs de fotos.
     */
    public void excluirFotos(int[] fotosID) {
        Statement st = null;
        boolean sucesso = true;
        int aID = getAlbum().albumID;
        AlbumVO albumVO = albunsDAO.findBy(aID);
        String sql;

        // passo 1 - adicionar o arquivo em ArquivoFTP
        for (int i = 0; i < fotosID.length; i++) {
            CacheFTP.getCache().addCommand(CacheFTP.DELETE, aID, fotosID[i]);
        }

        // passo 2 - remover do banco de dados
        try {
            for (int i = 0; i < fotosID.length; i++) {
                albumVO.getPhotos().remove(albumVO.getPhotoBy(fotosID[i]));
            }
            albunsDAO.getEntityManager().merge(albumVO);
        } catch (Exception e) {
            log.error("Erro na exclus�o no banco de dados ", e);
            sucesso = false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (Exception e) {
                log.warn("Can't close statement", e);
            }
        }

        // passo 3 - remover arquivos do fs (original e _a, _b, _c e _d)
        String[] prefixos = {"", "_a", "_b", "_c", "_d"};
        String nomeArquivo;
        boolean encontrou;

        for (int i = 0; i < fotosID.length; i++) {
            // pesquisa a foto na cole��o deste �lbum e remove da colecao
            encontrou = false;
            Iterator<PhotoVO> iter = fotos.iterator();
            while (iter.hasNext() && !encontrou) {
                PhotoVO f = iter.next();
                if (f.getFotoid() == fotosID[i]) {
                    encontrou = true;
                    iter.remove();
                }
            }

            for (int j = 0; j < prefixos.length; j++) {
                nomeArquivo = BancoImagem.getLocalPath(aID) + File.separator + prefixos[j] + fotosID[i] + ".jpg";
                File arqFoto = new File(nomeArquivo);

                if (arqFoto.isFile()) {
                    if (arqFoto.delete()) {
                        log.info(nomeArquivo + " exclu�do com sucesso");
                    } else {
                        log.error(nomeArquivo + " n�o pode ser exclu�do ");
                        sucesso = false;
                    }
                } else {
                    log.warn(nomeArquivo + " n�o � arquivo ou n�o existe");
                    sucesso = false;
                }
            }
        }

        //passo 4 - removendo do site remoto
        if (Util.getConfig().getBoolean("autoTransferir")) {
            Thread t = new Thread(new net.sf.webphotos.gui.util.FtpClient());
            t.start();
        }


        if (sucesso) {
            log.info("Exclus�o de fotos finalizada com sucesso.");
        } else {
            log.warn("Exclus�o de fotos finalizada com erros.");
        }
    }

    /**
     * Inclui fotos na cole��o. Recebe uma lista de arquivos de fotos, que ser�o
     * implantados no ArrayList fotos.
     *
     * @param f Lista de arquivos.
     */
    public void adicionarFotos(File[] f) {
        if (f.length == 0) {
            return;
        }

        for (int i = 0; i < f.length; i++) {
            File novaFoto = f[i];
            fotosNovas.add(new PhotoDTO(novaFoto.getAbsolutePath()));
        }
    }

    // popula a matriz de creditos, normalmente feito somente na primeira vez
    // ou na ocasi�o de um novo cr�dito adicionado ao banco de dados
    private void populaCategorias() throws SQLException {
        String sql = Util.getConfig().getString("sql2");

        List<Object[]> tableData = albunsDAO.findByNativeQuery(sql);

        categorias = new String[tableData.size()][2];

        int ct = 0;
        for (Object[] objects : tableData) {
            categorias[ct][0] = objects[0].toString();
            categorias[ct][1] = objects[1].toString();
            ct++;
        }
    }

    /**
     * Retorna a categoria espec�fica. Faz a busca na matriz categorias,
     * comparando com o ID recebido como par�metro.
     *
     * @param categoriaID ID da categoria.
     * @return Retorna uma categoria.
     */
    public String getCategoria(int categoriaID) {
        int tamanho;
        if (categorias == null) {
            try {
                populaCategorias();
                tamanho = categorias.length;
            } catch (SQLException ex) {
                tamanho = 0;
            }
        } else {
            tamanho = categorias.length;
        }

        for (int i = 0; i < tamanho; i++) {
            if (categorias[i][0].equals(Integer.toString(categoriaID))) {
                return categorias[i][1];
            }
        }
        return "categoria nao encontrada";
    }

    /**
     * Retorna uma String contendo todos os dados do alb�m.
     *
     * @return Retorna dados do alb�m.
     */
    @Override
    public String toString() {
        Iterator<PhotoVO> iter = fotos.iterator();

        String msg = "--------------------------------------"
                + "\nalbumID    : " + albumID
                + "\nusuarioID  : " + usuarioID
                + "\ncategoriaID: " + categoriaID
                + "\nAlbum      : " + nmAlbum
                + "\nDescricao  : " + descricao
                + "\ndtInsercao : " + dtInsercao
                + "\nnum.fotos  : " + fotos.size()
                + "\n--------------------------------------";

        while (iter.hasNext()) {
            PhotoVO f = iter.next();
            msg = msg + "\n" + f.toString() + "\n--------------------------------------";
        }

        return msg;
    }

    /**
     * Retorna uma String contendo todos os dados do alb�m no formato XML.
     *
     * @return Retorna dados do alb�m.
     */
    public String toXML() {

        String r = "<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>"
                + "\n<album id=\"" + albumID + "\">"
                + "\n\t<titulo>" + nmAlbum + "</titulo>"
                + "\n\t<categoria id=\"" + categoriaID + "\">" + getCategoria(categoriaID) + "</categoria>"
                + "\n\t<descricao>" + descricao + "</descricao>"
                + "\n\t<data>" + dtInsercao + "</data>"
                + "\n"
                + "\n\t<fotos>";

        Iterator<PhotoVO> iter = fotos.iterator();
        while (iter.hasNext()) {
            PhotoVO f = iter.next();
            r += "\n\t\t<foto id=\"" + f.getFotoid() + "\">"
                    + "\n\t\t\t<legenda>" + f.getLegenda() + "</legenda>"
                    + "\n\t\t\t<credito>" + f.getCreditos().getNome() + "</credito>"
                    + "\n\t\t\t<altura>" + f.getAltura() + "</altura>"
                    + "\n\t\t\t<largura>" + f.getLargura() + "</largura>"
                    + "\n\t\t</foto>";
        }

        r += "\n\t</fotos>"
                + "\n</album>\n";

        return r;

    }

    /**
     * Retorna uma String contendo todos os dados do alb�m no formato js.
     *
     * @return Retorna dados do alb�m.
     */
    public String toJavaScript() {

        String r = "albumID=" + albumID + ";\n"
                + "categoria='" + getCategoria(categoriaID) + "';\n"
                + "titulo=" + Util.stringToHtm(nmAlbum) + ";\n"
                + "data='" + dtInsercao + "';\n"
                + "descricao=" + Util.stringToHtm(descricao) + ";\n\n"
                + "fotos = new Array (";

        Iterator<PhotoVO> iter = fotos.iterator();
        String cc = "";

        while (iter.hasNext()) {
            PhotoVO f = iter.next();
            r += cc + "\n\tnew Foto(" + f.getFotoid() + "," + Util.stringToHtm(f.getLegenda()) + ",'" + f.getCreditos().getNome() + "')";
            cc = ",";
        }

        r += "\n\t);\n";

        return r;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton Object");
    }
}
