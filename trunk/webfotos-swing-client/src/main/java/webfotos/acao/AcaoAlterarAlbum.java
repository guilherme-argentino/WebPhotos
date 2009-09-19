package webfotos.acao;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import webfotos.Album;
import webfotos.BancoImagem;
import webfotos.Foto;
import webfotos.Thumbnail;
import webfotos.gui.PainelWebFotos;
import webfotos.util.CacheFTP;
import webfotos.gui.util.TableModelFoto;
import webfotos.gui.util.TableModelAlbum;
import webfotos.util.Util;
import javax.sql.RowSet;

/**
 * Altera ou cria alb�ns. Possui um construtor que recebe bot�es e tabelas de
 * alb�ns e fotos, um m�todo que idenfica a a��o obtida pelo evento e outro
 * m�todo que executa uma s�rie de passos para implementar as altera��es.
 */
public class AcaoAlterarAlbum extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 7297664420604720262L;
    JButton btAlterar, btNovo;
    JTable tbAlbuns, tbFotos;
    private RowSet rowSet = BancoImagem.getRSet();
    private boolean sucesso;

    /**
     * Contrutor da classe. Recebe como par�metro dois bot�es, um para altera��o
     * e o outro para implementa��o nova. Seta os valores dos bot�es da classe a
     * partir dos recebidos e seta as tabelas de alb�ns e fotos a partir de
     * m�todos get da classe {@link webfotos.gui.PainelWebFotos PainelWebFotos}.
     *
     * @param botaoNovo
     *            Bot�o para identificar a a��o de implementa��o novo.
     * @param botaoAlterar
     *            Bot�o para identificar a a��o de altera��o.
     */
    public AcaoAlterarAlbum(JButton botaoNovo, JButton botaoAlterar) {
        btAlterar = botaoAlterar;
        btNovo = botaoNovo;
        tbAlbuns = PainelWebFotos.getTbAlbuns();
        tbFotos = PainelWebFotos.getTbFotos();
    }

    /**
     * Identica qual a a��o que ocorreu. Recebe como par�metro um evento e
     * verifica qual tipo de a��o ocorreu por
     * {@link java.util.EventObject#getSource() getSource} e pelo
     * {@link javax.swing.AbstractButton#getActionCommand() getActionCommand}.
     * Se o usu�rio clicou em <I>novo</I> cria um novo �lbum, caso o texto do
     * bot�o seja <I>cancelar</I>, ent�o o usu�rio estar� cancelando a cria��o
     * de um novo alb�m e por �ltimo, caso seja <I>alterar</I>, efetuar� a
     * atualiza��o do �lbum, coletando os valores dos controles GUI, validando
     * os dados e atualizando o objeto.
     *
     * @param ev
     *            Evento de a��o.
     */
    public void actionPerformed(ActionEvent ev) {
        // usuario clicou em novo - criar novo �lbum
        if (ev.getSource() == btNovo && btNovo.getActionCommand() == PainelWebFotos.ACAO_NOVO) {
            PainelWebFotos.botaoNovo();
        } // caso o texto do bot�o seja "cancelar", ent�o o usu�rio est�
        // cancelando
        // a cria��o de um novo �lbum...
        else if (btAlterar.getActionCommand() == PainelWebFotos.ACAO_CANCELAR && ev.getSource() == btAlterar) {
            PainelWebFotos.botaoCancelar();
        } // atualiza o �lbum, coletando os valores dos controles GUI, validando
        // dados e atualizando o objeto Album
        else if ((btAlterar.getActionCommand() == PainelWebFotos.ACAO_ALTERAR && ev.getSource() == btAlterar) || (btNovo.getActionCommand() == PainelWebFotos.ACAO_FINALIZAR && ev.getSource() == btNovo)) {
            if (!PainelWebFotos.atualizaAlbum()) {
                return;
            }
            executaAlteracoes();
            if (ev.getSource() == btNovo) {
                PainelWebFotos.botaoFinalizar();
            }
        }
    }

    // executa alteracoes - alteracao ou criacao de novo album
    /**
     * M�todo respons�vel pelas altera��es ou cria��o de um novo alb�m. Primeiro
     * faz o registro do alb�m no banco de dados, checa se � necess�rio a
     * cria��o de um novo alb�m, criando um ID e atualizando o banco. Logo ap�s
     * registra as fotos no banco, todas as fotos s�o registradas novamente.
     * Fotos novas recebem um ID. Faz um INSERT no banco e atualiza novamente.
     * Move e renomeia os arquivos para o diret�rio do alb�m. Faz os Thumbs para
     * ajustar a dimens�o das fotos e adciona no FTP. Limpa a flag
     * CaminhoArquivo e apresenta as altera��es. E por �ltimo, executar o
     * sistema de envio por FTP.
     */
    public void executaAlteracoes() {

        Album album = Album.getAlbum();
        Foto[] fotos = album.getFotos();
        Foto f;
        int ultimoFotoID = -1;
        int albumID = album.getAlbumID();
        sucesso = true;

        PainelWebFotos.setCursorWait(true);

        albumID = recordAlbumData(album, albumID);

        sucesso = recordFotoData(fotos, ultimoFotoID, albumID);

        // PASSO 3 - Mover e renomear aquivos
        // //////////////////////////////////////////////////////////////////////
        String caminhoAlbum = Util.getFolder("albunsRoot").getPath() + File.separator + albumID;

        // checa a existencia do diretorio
        File diretorioAlbum = new File(caminhoAlbum);
        if (!diretorioAlbum.isDirectory()) {
            // diretorio n�o existe... criamos
            if (!diretorioAlbum.mkdir()) {
                Util.log("[AcaoAlterarAlbum.executaAlteracoes.7]/ERRO: diretorio " + caminhoAlbum + " n�o pode ser criado. abortando");
                return;
            }
        }

        // copia os arquivos
        for (int i = 0; i < fotos.length; i++) {
            f = fotos[i];
            // copia somente arquivos ainda n�o cadastrados
            if (f.getCaminhoArquivo().length() > 0) {
                try {
                    FileChannel canalOrigem = new FileInputStream(f.getCaminhoArquivo()).getChannel();
                    FileChannel canalDestino = new FileOutputStream(
                            caminhoAlbum + File.separator + f.getFotoID() + ".jpg").getChannel();
                    canalDestino.transferFrom(canalOrigem, 0, canalOrigem.size());
                    canalOrigem = null;
                    canalDestino = null;
                } catch (Exception e) {
                    Util.log("[AcaoAlterarAlbum.executaAlteracoes.8]/ERRO: " + e);
                    sucesso = false;
                }
            }
        }// fim for

        prepareThumbsAndFTP(fotos, albumID, caminhoAlbum);

        prepareExtraFiles(album, caminhoAlbum);

        fireChangesToGUI(fotos);

        dispatchAlbum();

        PainelWebFotos.setCursorWait(false);
    }

    /**
     * PASSO 4 - Fazer Thumbs e Adicionar em FTP
     * @param fotos
     * @param albumID
     * @param caminhoAlbum
     */
    private void prepareThumbsAndFTP(Foto[] fotos, int albumID,
            String caminhoAlbum) {
        Foto f;
        // PASSO 4 - Fazer Thumbs e Adicionar em FTP
        // //////////////////////////////////////////////////////////////////////
        for (int i = 0; i < fotos.length; i++) {
            f = fotos[i];
            String caminhoArquivo;

            // thumbs somente arquivos ainda n�o cadastrados
            if (f.getCaminhoArquivo().length() > 0) {
                caminhoArquivo = caminhoAlbum + File.separator + f.getFotoID() + ".jpg";
                Thumbnail.makeThumbs(caminhoArquivo);

                // adicionar em CacheFTP
                CacheFTP.getCache().addCommand(CacheFTP.UPLOAD, albumID,
                        f.getFotoID());
            }
        }
    }

    /**
     * PASSO 6 - Limpar a flag CaminhoArquivo e apresentar as altera��es
     *
     * @param fotos
     */
    private void fireChangesToGUI(Foto[] fotos) {
        Foto f;

        // PASSO 6 - Limpar a flag CaminhoArquivo e apresentar as altera��es
        // //////////////////////////////////////////////////////////////////////
        for (int i = 0; i < fotos.length; i++) {
            f = fotos[i];
            f.resetCaminhoArquivo();
        }

        try {
            // Refresh of tables
            TableModelFoto.getModel().update();
            TableModelFoto.getModel().fireTableDataChanged();
            TableModelAlbum.getModel().update();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        TableModelAlbum.getModel().fireTableDataChanged();
        ((javax.swing.table.AbstractTableModel) tbAlbuns.getModel()).fireTableDataChanged();
        ((javax.swing.table.AbstractTableModel) tbFotos.getModel()).fireTableDataChanged();

        PainelWebFotos.alteracaoFinalizada();
        if (sucesso) {
            Util.log("Opera��o finalizada com sucesso.");
        } else {
            Util.log("Opera��o finalizada com erros.");
        }
    }

    /**
     * PASSO 5 - Preparar os arquivos adicionais
     *
     * @param album
     * @param caminhoAlbum
     */
    private void prepareExtraFiles(Album album, String caminhoAlbum) {
        // escreve o arquivo javaScript e o XML
        try {
            FileWriter out = new FileWriter(caminhoAlbum + File.separator + album.getAlbumID() + ".js");
            out.write(album.toJavaScript());
            out.flush();
            out.close();

            out = new FileWriter(caminhoAlbum + File.separator + album.getAlbumID() + ".xml");
            out.write(album.toXML());
            out.flush();
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * PASSO 7 - Executar o sistema de envio por FTP
     */
    private void dispatchAlbum() {

        // TODO: Acertar uma rotina que pe�a confirma��o em alguns casos
        // //////////////////////////////////////////////////////////////////////
        boolean autoTransferir = Util.getConfig().getBoolean("autoTransferir");
        int retorno = 1;
        if (!autoTransferir) {
            retorno = JOptionPane.showConfirmDialog(
                    PainelWebFotos.getInstance(),
                    "Deseja publicar agora as fotos na internet?\n\nSE CLICAR EM \"N�O\" SER� NECESS�RIO ATIVAR A PUBLICA��O MANUALMENTE!",
                    "Publicar fotos?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
        }

        if (retorno == 0 || autoTransferir) {
            Thread t = new Thread(new webfotos.gui.FtpClient());
            t.start();
        }

    }

    /**
     * PASSO 2 - Registrar fotos no banco de dados todas as fotos s�o
     * registradas novamente. Fotos novas recebem um ID.
     *
     * @param fotos
     * @param ultimoFotoID
     * @param albumID
     * @return
     */
    public boolean recordFotoData(Foto[] fotos, int ultimoFotoID, int albumID) {
        Foto f;
        String nomeArquivo;
        String legenda;
        int fotoID;
        int creditoID;
        int altura;
        int largura;
        String sql;
        boolean sucesso = false;

        // PASSO 2 - Registrar fotos no banco de dados
        // todas as fotos s�o registradas novamente. Fotos novas recebem um ID
        // //////////////////////////////////////////////////////////////////////
        for (int i = 0; i < fotos.length; i++) {
            f = fotos[i];

            fotoID = f.getFotoID();
            creditoID = f.getCreditoID();
            legenda = f.getLegenda();
            altura = f.getAltura();
            largura = f.getLargura();
            nomeArquivo = f.getCaminhoArquivo();

            // INSERT para fotos ainda n�o cadastradas
            if (nomeArquivo.length() > 0) {
                if (ultimoFotoID < 0) {
                    try {
                        sql = "SELECT max(fotoID) FROM fotos";
                        rowSet.setCommand(sql);
                        rowSet.execute();
                        rowSet.first();
                        ultimoFotoID = rowSet.getInt(1);

                        sql = "SELECT fotoID FROM fotos ORDER BY albumID DESC LIMIT 1";
                        rowSet.setCommand(sql);
                        rowSet.execute();
                        fotoID = ++ultimoFotoID;
                        f.setFotoID(fotoID);

                        rowSet.moveToInsertRow();
                        rowSet.updateInt("fotoID", fotoID);
                        rowSet.insertRow();
                    } catch (Exception e) {
                        Util.log("[AcaoAlterarAlbum.recordFotoData]/ERRO: " + e);
                        e.printStackTrace(Util.err);
                        PainelWebFotos.setCursorWait(false);
                        sucesso = false;
                        return sucesso;
                    }
                }
            }
            // atualiza o banco de dados
            try {

                sql = "SELECT fotoID,albumID,creditoID,legenda,altura,largura FROM fotos WHERE fotoID=" + fotoID;

                rowSet.setCommand(sql);
                rowSet.execute();
                rowSet.first();
                fotoID = rowSet.getInt(1);

                rowSet.updateInt("albumID", albumID);
                rowSet.updateInt("creditoID", creditoID);
                rowSet.updateInt("altura", altura);
                rowSet.updateInt("largura", largura);
                rowSet.updateString("legenda", legenda);

                rowSet.updateRow();
                // BancoImagem.getBancoImagem().ponteWWW(sql);

            } catch (Exception e) {
                Util.log("[AcaoAlterarAlbum.recordFotoData]/ERRO: " + e);
                e.printStackTrace(Util.err);
                PainelWebFotos.setCursorWait(false);
                sucesso = false;
                return sucesso;
            }
        } // fim for

        // executa todas as instru��es SQL update e insert
        try {
            Util.log("finalizando altera��es em banco de dados");
            rowSet.refreshRow();
        } catch (Exception e) {
            Util.log("[AcaoAlterarAlbum.recordFotoData]/ERRO: " + e);
            e.printStackTrace(Util.err);
            sucesso = false;
        }
        return sucesso;
    }

    /**
     * PASSO 1 - Registrar o �lbum no banco de dados
     *
     * @param album
     * @param albumID
     * @return
     */
    public int recordAlbumData(Album album, int albumID) {
        String sql;

        // PASSO 1 - Registrar o �lbum no banco de dados
        // //////////////////////////////////////////////////////////////////////
        // converte a data do �lbum para ANSI
        String dtAnsi = "0000-00-00";
        try {
            SimpleDateFormat dataBR = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat dataAnsi = new SimpleDateFormat("yyyy-MM-dd");
            Date d = dataBR.parse(album.getDtInsercao());
            dtAnsi = dataAnsi.format(d);

        } catch (Exception e) {
            Util.out.println("[AcaoAlterarAlbum.recordAlbumData]/ERRO: " + e);
            PainelWebFotos.setCursorWait(false);
            return 0;
        }

        // Se precisar, criar um novo album
        if (albumID == 0) {
            // �lbum ainda n�o registrado obt�m um ID
            try {

                sql = "SELECT MAX(albumID) FROM albuns";
                rowSet.setCommand(sql);
                rowSet.execute();
                rowSet.first();
                albumID = rowSet.getInt(1);

                sql = "SELECT albumID, DtInsercao FROM albuns ORDER BY albumID DESC LIMIT 1";
                rowSet.setCommand(sql);
                rowSet.execute();
                album.setAlbumID(++albumID);
                rowSet.moveToInsertRow();
                rowSet.updateInt("albumID", albumID);
                rowSet.updateDate("DtInsercao", new java.sql.Date((new Date()).getTime()));
                rowSet.insertRow();

            } catch (Exception e) {
                Util.log("[AcaoAlterarAlbum.recordAlbumData]/ERRO: " + e);
                e.printStackTrace(Util.err);
                sucesso = false;
                PainelWebFotos.setCursorWait(false);
                return 0;
            }

        }
        // atualiza o banco de dados
        try {
            sql = "SELECT usuarioID,categoriaID,NmAlbum,Descricao,DtInsercao,albumID FROM albuns WHERE albumID=" + albumID;

            rowSet.setCommand(sql);
            rowSet.execute();
            rowSet.first();
            albumID = rowSet.getInt("albumID");

            rowSet.updateInt("categoriaID", album.getCategoriaID());
            rowSet.updateInt("usuarioID", album.getUsuarioID());
            rowSet.updateString("NmAlbum", album.getNmAlbum());
            rowSet.updateString("Descricao", album.getDescricao());
            rowSet.updateString("DtInsercao", dtAnsi);

            rowSet.updateRow();
            // BancoImagem.getBancoImagem().ponteWWW(sql);
        } catch (Exception e) {
            Util.log("[AcaoAlterarAlbum.recordAlbumData]/ERRO: " + e);
            e.printStackTrace(Util.err);
            PainelWebFotos.setCursorWait(false);
            return 0;
        }
        return albumID;
    }
}
