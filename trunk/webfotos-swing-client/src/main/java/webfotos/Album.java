package webfotos;

import com.sun.rowset.JdbcRowSetImpl;
import java.io.File;

import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import webfotos.BancoImagem;
import webfotos.Foto;
import webfotos.util.CacheFTP;
import webfotos.util.Util;

/**
 * A classe Album mantém uma coleçao de fotos em um ArrayList de Foto, que pode ser manipulada através das funções da própria classe.
 * Classe do tipo Singleton, é permitido apenas uma instância da classe.
 * O objeto é acessível unicamente através da classe.
 * Também manipula dados dos IDs, nome do albúm, descrição, data de inserção e categoria.
 */
public class Album {
    private static final Album instanciaAlbum=new Album();
    /** variáveis do álbum */
    private int albumID=0;
    private int categoriaID=0;
    private int usuarioID=0;
    private String nmAlbum=null;
    private String descricao=null;
    private String dtInsercao=null;
    /** variáveis utilizadas nessa classe */
    private Collection<Foto> fotos=new ArrayList<Foto>();
    private String[][] categorias=null;
    private javax.sql.RowSet rowSet = BancoImagem.getRSet();
    
    private Logger log = Logger.getLogger(this.getClass().getName());

    private Album() {
        /** nunca usado publicamente (construtor private) */
    }

    /**
     * Retorna o objeto Album instanciado na própria classe.
     * @return Retorna um objeto Album.
     */
    public static Album getAlbum() { return instanciaAlbum; }

    /**
     * Seta um valor para o ID do albúm.
     * @param aID ID do álbum.
     */
    public void setAlbumID(int aID) { albumID=aID; }
    /**
     * Seta um valor para o ID do usuário.
     * @param uID ID do usuário.
     */
    public void setUsuarioID(int uID) { usuarioID=uID; }
    /**
     * Seta um valor para o ID de categoria.
     * @param cID ID da categoria.
     */
    public void setCategoriaID(int cID) { categoriaID=cID; }
    /**
     * Seta um valor para o nome do albúm.
     * @param nm Nome do albúm.
     */
    public void setNmAlbum(String nm) { nmAlbum=nm; }
    /**
     * Seta um valor para a descrição do albúm.
     * @param d Descrição do albúm.
     */
    public void setDescricao(String d) { descricao=d; }
    /**
     * Seta um valor para a data de inserção do albúm.
     * @param dt Data de inserção do albúm.
     */
    public void setDtInsercao(String dt) { dtInsercao=dt; }

    /**
     * Retorna o ID do albúm.
     * @return Retorna um ID.
     */
    public int getAlbumID() { return albumID; }
    /**
     * Retorna o ID do usuário.
     * @return Retorna um ID.
     */
    public int getUsuarioID() { return usuarioID; }
    /**
     * Retorna o ID da categoria.
     * @return Retorna um ID.
     */
    public int getCategoriaID() { return categoriaID; }
    /**
     * Retorna o nome do albúm.
     * @return Retorna um nome.
     */
    public String getNmAlbum() { return nmAlbum; }
    /**
     * Retorna a descrição do albúm.
     * @return Retorna uma descrição.
     */
    public String getDescricao() { return descricao; }
    /**
     * Retorna a data de inserção do albúm.
     * @return Retorna uma data.
     */
    public String getDtInsercao() { return dtInsercao; }

    /**
     * Retorna uma foto deste album ou null se não existir.
     * Faz a busca da foto através de um ID enviado como parâmetro.
     * @param fotoID ID da foto.
     * @return Retorna uma foto.
     */
    public Foto getFoto(int fotoID) {
        Iterator iter=fotos.iterator();

        while(iter.hasNext()) {
            Foto f=(Foto) iter.next();
            if (f.getFotoID()==fotoID) return f;
        }
        return null;
    }

    /**
     * Retorna uma foto deste album ou null se não existir.
     * Faz a busca da foto através do caminho do arquivo que foi enviado como parâmetro.
     * @param caminho Caminho do arquivo foto.
     * @return Retorna uma foto.
     */
    public Foto getFoto(String caminho) {
        Iterator iter=fotos.iterator();

        while(iter.hasNext()) {
            Foto f=(Foto) iter.next();
            if(caminho.equals(f.getCaminhoArquivo())) return f;
        }
        return null;
    }
    
    /**
     * Retorna toda a coleção encontrada no ArrayList fotos.
     * @return Retorna a coleção de fotos.
     */
    public Foto[] getFotos() {
        return fotos.toArray(new Foto[0]);
    }
    
    /**
     * Retorna uma matriz com as fotos e seus dados específicos.
     * Armazena para cada foto o seu ID ou caminho, legenda e crédito.
     * @return Retorna todas as fotos e seus valores específicos.
     */
    public Object[][] getFotosArray() {
        if(fotos==null) return null;
        Object[][] resultado=new Object[fotos.size()][3];
        Iterator iter=fotos.iterator();
        int ct=0;

        while(iter.hasNext()) {
            Foto f=(Foto) iter.next();
            if(f.getCaminhoArquivo().length() > 0) {
                // imagem acabou de ser adicionada... sem ID
                resultado[ct][0]=(Object) f.getCaminhoArquivo();
                resultado[ct][1]=(Object) f.getLegenda();
                resultado[ct][2]=(Object) f.getCreditoNome();
            } else {
                resultado[ct][0]=(Object) Integer.toString(f.getFotoID());
                resultado[ct][1]=(Object) f.getLegenda();
                resultado[ct][2]=(Object) f.getCreditoNome();
            }
            ct++;
        }
        return resultado;
    }

    /**
     * Retorna um vetor que armazenará os dados de ID, legenda e crédito da foto.
     * @return Retorna dados de ID, legenda e crédito da foto.
     */
    public String[] getFotosColunas() {
        return new String[] {"ID","Legenda","Credito"};
    }
    
    /**
     * Retorna um vetor que armazena as categorias.
     * Checa se categoria é diferente de null.
     * Caso afirmativo, armazena seus valores no vetor criado para retorno.
     * @return Retorna as categorias do albúm.
     */
    public String[] getCategoriasArray() {
        int tamanho;
        if(categorias==null) {
            try {
                log.info("Populando Categorias");
                populaCategorias();
                tamanho = categorias.length;
            } catch (SQLException ex) {
                log.error("Impossível popular categorias", ex);
                tamanho = 0;
            }
        } else {
            tamanho = categorias.length;
        }

        String[] nomesCategorias=new String[tamanho];
        for(int i=0; i < tamanho; i++)
            nomesCategorias[i]=categorias[i][1];

        return nomesCategorias;
    }
    
    /**
     * Retorna um índice da matriz categorias.
     * Faz a busca através de um nome enviado como parâmetro.
     * Caso não seja encontrado pelo nome, retorna o valor 0.
     * @param nomeCategoria Nome da categoria pesquisada.
     * @return Retorna um índice de posição.
     */
    public int getLstCategoriasIndex(String nomeCategoria) {
        for(int i=0; i < categorias.length; i++) {
            if(nomeCategoria.equals(categorias[i][1])) return i;
        }
        return 0;
    }

    /**
     * Retorna um índice da matriz categorias.
     * Faz a busca através de um ID enviado como parâmetro.
     * Caso não seja encontrado pelo ID, retorna o valor 0.
     * @param categoriaID ID da categoria pesquisada.
     * @return Retorna um índice de posição.
     */
    public int getLstCategoriasIndex(int categoriaID) {
        for(int i=0; i < categorias.length; i++) {
            if(categoriaID==Integer.parseInt(categorias[i][0])) return i;
        }
        return 0;
    }

    /**
     * Retorna o ID dado um nome de categoria.
     * @param nomeCategoria Nome de categoria pesquisada.
     * @return Retorna um ID.
     */
    public int getLstCategoriasID(String nomeCategoria) {
        for(int i=0; i < categorias.length; i++) {
            if(nomeCategoria.equals(categorias[i][1])) return Integer.parseInt(categorias[i][0]);
        }
        return -1;
    }

    /**
     * Limpa o ArrayList fotos.
     * Seta o valor 0 para as variáveis numéricas e vazio para variáveis de tipo String.
     */
    public void clear() {
        albumID=0; usuarioID=0; categoriaID=0; nmAlbum=""; descricao="";
        dtInsercao="";
        if(fotos!=null) fotos.clear();
    }

    /**
     * Carrega um albúm no ArrayList fotos que anteriormente foi salvo no banco de dados.
     * Após limpar os valores em fotos, faz uma busca ao banco de dados para carregar informações do albúm especificado.
     * A comparação no banco de dados é feita através do ID do albúm, passado como parâmetro.
     * @param aID ID do albúm.
     */
    public void loadAlbum(int aID) {
        String sql;
        fotos.clear();

        sql="select usuarioID, categoriaID, nmalbum, descricao, DATE_FORMAT(DtInsercao, '%d/%m/%y') as dtinsercao from albuns where albumID=" + aID;

        try {
            /** Carrega as informações do álbum */
            rowSet.setCommand(sql);
            rowSet.execute();

            if(rowSet.next()) {
                albumID=aID;
                categoriaID=rowSet.getInt("categoriaID");
                usuarioID=rowSet.getInt("usuarioID");
                nmAlbum=rowSet.getString("nmalbum");
                descricao=rowSet.getString("descricao");
                dtInsercao=rowSet.getString("dtinsercao");
            }

            /**
             * carrega as fotos desse album
             * TODO: parametrizar esta busca
             */
            sql="select fotos.fotoID, fotos.legenda, fotos.creditoID, creditos.nome, fotos.largura, fotos.altura, fotos.tamanho from fotos left join creditos on fotos.creditoID=creditos.creditoID where albumID=" + aID;
            rowSet.setCommand(sql);
            rowSet.execute();
            rowSet.first();

            /** Popula a collection fotos */
            do {
                int fotoID=rowSet.getInt(1);
                String legenda=rowSet.getString(2);
                int creditoID=rowSet.getInt(3);
                String creditoNome=rowSet.getString(4);
                int largura=rowSet.getInt(5);
                int altura=rowSet.getInt(6);
                long tamanhoBytes=rowSet.getLong(7);
                fotos.add(new Foto(fotoID,albumID,legenda,creditoID,creditoNome,largura,altura,tamanhoBytes));
            } while (rowSet.next());

        } catch (java.sql.SQLException sqlE) {
            log.error("Ocorreu um erro durante a leitura do álbum no banco de dados", sqlE);
            int selecao = JOptionPane.showConfirmDialog(null,
                    "ERRO durante leitura do álbum no banco de dados.\n\nTentar Novamente?",
                    "Aviso!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (selecao == JOptionPane.YES_OPTION) {                    
                /** TODO: extrair para um método */
                try {
                    BancoImagem.setRSet(new JdbcRowSetImpl(BancoImagem.getConnection()));
                    this.rowSet = BancoImagem.getRSet();
                } catch (java.sql.SQLException sqlEi) {
                    log.error(sqlEi.toString(), sqlEi);
                } catch (Exception e) {
                    log.error("Inexperado", e);
                } finally {
                    loadAlbum(aID);
                }
            } else {
                System.exit(1);
            }
        } catch (Exception e) {
            log.error("Ocorreu um erro inexperado durante a leitura do álbum", e);
            JOptionPane.showMessageDialog(null, "ERRO inexperado durante leitura do álbum - " + e.getMessage(),"Erro!", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * Recebe um vetor com IDs de albúns a serem excluídos.
     * Adiciona os albúns no arquivo FTP, exclui os albúns do banco de dados e por último exclui os arquivos da pasta local ou rede.
     * @param albunsID IDs dos albúns.
     */
    public void excluirAlbuns(int[] albunsID) {
        Statement st=null;
        boolean sucesso=true;
        String sql;

        // passo 1 - adiciona o álbum no arquivoFTP
        for(int i=0; i < albunsID.length; i++)
            CacheFTP.getCache().addCommand(CacheFTP.DELETE, albunsID[i],0);

        // passo 2 - remover do banco de dados
        try {
            for(int i=0; i < albunsID.length; i++) {
                // todas as fotos desse album
                sql="select fotoID from fotos where albumID=" + albunsID[i];
                rowSet.setCommand(sql);
                rowSet.execute();
                while(rowSet.next()) {
                    int temp = rowSet.getInt("fotoID");
                    rowSet.deleteRow();
                }

                // apaga o album
                sql="select albumID from albuns where albumID=" + albunsID[i];
                rowSet.setCommand(sql);
                rowSet.execute();
                rowSet.first();
                albunsID[i] = rowSet.getInt("albumID");
                rowSet.deleteRow();
            }
            log.info("Exclusão do(s) álbun(s) e fotos no banco de dados efetuada com sucesso !");
        } catch (Exception e) {
            log.error("Houve um erro na exclusao de albuns no banco de dados", e);
            sucesso=false;
        } finally {
            try {
                if(st!=null) st.close();
            } catch (Exception e) {
                log.debug("Can't Close Statement", e);
            }
        }


        // passo 3 - remover os arquivos local ou rede
        for(int i=0; i < albunsID.length; i++) {
            File diretorio=new File(BancoImagem.getLocalPath(albunsID[i]));
            if(diretorio.isDirectory()==true) {
                // provavelmente tem fotos nesse diretorio
                String[] arquivos=diretorio.list();
                if(arquivos.length > 0) {
                    // apagamos os arquivos desse diretorio
                    for(int j=0; j < arquivos.length; j++) {
                        File arquivo=new File(BancoImagem.getLocalPath(albunsID[i]) + File.separator + arquivos[j]);
                        if(arquivo.delete()==true) {
                            log.info(BancoImagem.getLocalPath(albunsID[i]) + File.separator + arquivos[j] + " excluído com sucesso");
                        } else {
                            log.error("Erro na exclusão de " + BancoImagem.getLocalPath(albunsID[i]) + File.separator + arquivos[j]);
                            sucesso=false;
                        }
                        arquivo=null;
                    }
                }
                if(diretorio.delete()==true) {
                    log.info(BancoImagem.getLocalPath(albunsID[i]) + " excluído com sucesso");
                } else {
                    log.error("Erro na exclusão de " + BancoImagem.getLocalPath(albunsID[i]));
                    sucesso=false;
                }
            }
        } // fim for
        if(sucesso) {
            log.info("Álbum(ns) excluído(s) com sucesso.");
        } else {
            log.warn("Finalizando com erros na exclusão.");
        }
    }

    /**
     * Recebe uma lista com nomes de fotos e faz uma busca no ArrayList fotos, caso encontre, exclui a foto específica.
     * Essa função exclui fotos da coleção que ainda não estejam cadastradas no DB nem feito thumbnails (usuário adicionou e quer excluir essas fotos).
     * @param nomes Lista de nomes de fotos.
     */
    public void excluirFotos(String[] nomes) {
        String nome=null;
        Foto foto;

        for(int i=0; i < nomes.length; i++) {
            Iterator iter=fotos.iterator();
            nome=nomes[i];

            while(iter.hasNext()) {
                foto=(Foto) iter.next();
                if(nome.equals(foto.getCaminhoArquivo())) {
                    iter.remove();
                    break;
                }
            } // fim while
        } // fim for
    } // fim metodo

    /**
     * Recebe uma lista com IDs das fotos e faz uma busca para excluir as fotos específicas do DB, FTP e FS.
     * @param fotosID Lista de IDs de fotos.
     */
    public void excluirFotos(int[] fotosID) {
        Statement st=null;
        boolean sucesso=true;
        int aID=getAlbum().albumID;
        String sql;

        // passo 1 - adicionar o arquivo em ArquivoFTP
        for(int i=0; i < fotosID.length; i++)
            CacheFTP.getCache().addCommand(CacheFTP.DELETE,aID,fotosID[i]);

        // passo 2 - remover do banco de dados
        try {
            for(int i=0; i < fotosID.length; i++) {
                sql="select fotoID from fotos where fotoID=" + fotosID[i];
                rowSet.setCommand(sql);
                rowSet.execute();
                rowSet.first();
                fotosID[i] = rowSet.getInt("fotoID");
                rowSet.deleteRow();
            }
        } catch (Exception e) {
            log.error("Erro na exclusão no banco de dados ", e);
            sucesso=false;
        } finally {
            try {
                if(st!=null) st.close();
            } catch (Exception e) {
                log.warn("Can't close statement", e);
            }
        }

        // passo 3 - remover arquivos do fs (original e _a, _b, _c e _d)
        String[] prefixos={"","_a","_b","_c","_d"};
        String nomeArquivo;
        boolean encontrou;

        for(int i=0; i < fotosID.length; i++) {
            // pesquisa a foto na coleção deste álbum e remove da colecao
            encontrou=false;
            Iterator iter=fotos.iterator();
            while(iter.hasNext() && !encontrou) {
                Foto f=(Foto) iter.next();
                if(f.getFotoID()==fotosID[i]) {
                    encontrou=true;
                    iter.remove();
                }
            }
            iter=null;
            for(int j=0; j < prefixos.length; j++) {
                nomeArquivo=BancoImagem.getLocalPath(aID) + File.separator + prefixos[j] + fotosID[i] + ".jpg";
                File arqFoto=new File(nomeArquivo);

                if(arqFoto.isFile()) {
                    if(arqFoto.delete()) {
                        log.info(nomeArquivo + " excluído com sucesso");
                    } else {
                        log.error(nomeArquivo + " não pode ser excluído ");
                        sucesso=false;
                    }
                } else {
                    log.warn(nomeArquivo + " não é arquivo ou não existe");
                    sucesso=false;
                }
                arqFoto=null;
            }
        }

        //passo 4 - removendo do site remoto
        if(Util.getConfig().getBoolean("autoTransferir")) {
            Thread t=new Thread(new webfotos.gui.FtpClient());
            t.start();
        }


        if(sucesso) {
            log.info("Exclusão de fotos finalizada com sucesso.");
        } else {
            log.warn("Exclusão de fotos finalizada com erros.");
        }
    }

    /**
     * Inclui fotos na coleção.
     * Recebe uma lista de arquivos de fotos, que serão implantados no ArrayList fotos.
     * @param f Lista de arquivos.
     */
    public void adicionarFotos(File[] f) {
        if(f.length==0) return;

        for(int i=0; i < f.length; i++) {
            File novaFoto=f[i];
            fotos.add(new Foto(novaFoto.getAbsolutePath()));
        }
    }

    // popula a matriz de creditos, normalmente feito somente na primeira vez
    // ou na ocasião de um novo crédito adicionado ao banco de dados
    private void populaCategorias() throws SQLException {
        String sql=Util.getConfig().getString("sql2");

        rowSet.setCommand(sql);
        rowSet.execute();
        rowSet.last();
        categorias=new String[rowSet.getRow()][2];
        rowSet.first();

        int ct=0;
        do {
            categorias[ct][0]=rowSet.getObject(1).toString();
            categorias[ct][1]=rowSet.getString(2);
            ct++;
        } while(rowSet.next());

    }
	
    /**
     * Retorna a categoria específica.
     * Faz a busca na matriz categorias, comparando com o ID recebido como parãmetro.
     * @param categoriaID ID da categoria.
     * @return Retorna uma categoria.
     */
    public String getCategoria(int categoriaID) {
        int tamanho;
        if(categorias==null) {
            try {
                populaCategorias();
                tamanho = categorias.length;
            } catch (SQLException ex) {
                tamanho = 0;
            }
        } else {
            tamanho = categorias.length;
        }

        for(int i=0; i < tamanho; i++) {
            if(categorias[i][0].equals(Integer.toString(categoriaID))) return categorias[i][1];
        }
        return "categoria nao encontrada";		
    }

    /**
     * Retorna uma String contendo todos os dados do albúm.
     * @return Retorna dados do albúm.
     */
    @Override
    public String toString() {
        Iterator iter=fotos.iterator();

        String msg="--------------------------------------" +
                "\nalbumID    : " + albumID +
                "\nusuarioID  : " + usuarioID +
                "\ncategoriaID: " + categoriaID +
                "\nAlbum      : " + nmAlbum +
                "\nDescricao  : " + descricao +
                "\ndtInsercao : " + dtInsercao +
                "\nnum.fotos  : " + fotos.size() +
                "\n--------------------------------------";

        while(iter.hasNext()) {
            Foto f=(Foto) iter.next();
            msg=msg + "\n" + f.toString() + "\n--------------------------------------";
        }

        return msg;
    }
	
    /**
     * Retorna uma String contendo todos os dados do albúm no formato XML.
     * @return Retorna dados do albúm.
     */
    public String toXML() {

        String r="<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>" +
                "\n<album id=\"" + albumID + "\">" +
                "\n\t<titulo>" + nmAlbum + "</titulo>" +
                "\n\t<categoria id=\"" + categoriaID + "\">" + getCategoria(categoriaID) + "</categoria>" +
                "\n\t<descricao>" + descricao + "</descricao>" +
                "\n\t<data>" + dtInsercao + "</data>" +
                "\n" +
                "\n\t<fotos>";

        Iterator iter=fotos.iterator();
        while(iter.hasNext()) {
                Foto f=(Foto) iter.next();
                r+="\n\t\t<foto id=\"" + f.getFotoID() + "\">" +
                    "\n\t\t\t<legenda>" + f.getLegenda() + "</legenda>" +
                    "\n\t\t\t<credito>" + f.getCreditoNome() + "</credito>" +
                    "\n\t\t\t<altura>" + f.getAltura() + "</altura>" +
                    "\n\t\t\t<largura>" + f.getLargura() + "</largura>" +
                    "\n\t\t</foto>";
        }

        r+="\n\t</fotos>" +
            "\n</album>\n";

        return r;

    }
	
    /**
     * Retorna uma String contendo todos os dados do albúm no formato js.
     * @return Retorna dados do albúm.
     */
    public String toJavaScript() {

        String r="albumID=" + albumID + ";\n" +
                "categoria='" + getCategoria(categoriaID) + "';\n" +
                "titulo=" + Util.stringToHtm(nmAlbum) + ";\n" +
                "data='" + dtInsercao + "';\n" +
                "descricao=" + Util.stringToHtm(descricao) + ";\n\n" +			
                "fotos = new Array (";

        Iterator iter=fotos.iterator();
        String cc="";

        while(iter.hasNext()) {
                Foto f=(Foto) iter.next();
                r += cc + "\n\tnew Foto(" + f.getFotoID() + "," + Util.stringToHtm(f.getLegenda()) + ",'" + f.getCreditoNome() + "')";
                cc=",";
        }

        r+="\n\t);\n";

        return r;

    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton Object");
    }

}