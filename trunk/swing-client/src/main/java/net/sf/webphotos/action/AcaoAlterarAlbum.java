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
package net.sf.webphotos.action;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSet;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.sf.webphotos.Album;
import net.sf.webphotos.BancoImagem;
import net.sf.webphotos.PhotoDTO;
import net.sf.webphotos.dao.jpa.AlbumDAO;
import net.sf.webphotos.gui.PainelWebFotos;
import net.sf.webphotos.gui.util.TableModelAlbum;
import net.sf.webphotos.gui.util.TableModelFoto;
import net.sf.webphotos.model.AlbumVO;
import net.sf.webphotos.model.CategoryVO;
import net.sf.webphotos.model.PhotoVO;
import net.sf.webphotos.tools.Thumbnail;
import net.sf.webphotos.util.ApplicationContextResource;
import net.sf.webphotos.util.Util;
import net.sf.webphotos.util.legacy.CacheFTP;

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
    private AlbumDAO albumDAO = (AlbumDAO) ApplicationContextResource.getBean("albunsDAO");
    private boolean sucesso;

    /**
     * Contrutor da classe. Recebe como par�metro dois bot�es, um para altera��o
     * e o outro para implementa��o nova. Seta os valores dos bot�es da classe a
     * partir dos recebidos e seta as tabelas de alb�ns e fotos a partir de
     * m�todos get da classe
     * {@link net.sf.webphotos.gui.PainelWebFotos PainelWebFotos}.
     *
     * @param botaoNovo Bot�o para identificar a a��o de implementa��o novo.
     * @param botaoAlterar Bot�o para identificar a a��o de altera��o.
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
     * @param ev Evento de a��o.
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        // usuario clicou em novo - criar novo �lbum
        if (ev.getSource() == btNovo && btNovo.getActionCommand().equals(PainelWebFotos.ACAO_NOVO)) {
            PainelWebFotos.botaoNovo();
        } // caso o texto do bot�o seja "cancelar", ent�o o usu�rio est�
        // cancelando
        // a cria��o de um novo �lbum...
        else if (btAlterar.getActionCommand().equals(PainelWebFotos.ACAO_CANCELAR) && ev.getSource() == btAlterar) {
            PainelWebFotos.botaoCancelar();
        } // atualiza o �lbum, coletando os valores dos controles GUI, validando
        // dados e atualizando o objeto Album
        else if ((btAlterar.getActionCommand().equals(PainelWebFotos.ACAO_ALTERAR) && ev.getSource() == btAlterar) || (btNovo.getActionCommand().equals(PainelWebFotos.ACAO_FINALIZAR) && ev.getSource() == btNovo)) {
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
        PhotoDTO[] fotos = album.getFotos();
        PhotoDTO f;
        int albumID = album.getAlbumID();
        sucesso = true;

        PainelWebFotos.setCursorWait(true);
        
        AlbumVO albumVO = null;

        // PASSO 1 - Carregar dados no BD
        // //////////////////////////////////////////////////////////////////////
        try {
            final HashSet<PhotoDTO> newHashSet = Sets.newHashSet(fotos);
            final Collection<PhotoVO> transformedCollection = Collections2.transform(newHashSet, PhotoDTO.FROM_PHOTODTO_PHOTOVO);
            final HashSet<PhotoVO> photosVO = new HashSet<PhotoVO>(transformedCollection);
            albumVO = AlbumVO.builder(albumID)
                    .withAlbumName(album.getNmAlbum())
                    .withDescription(album.getDescricao())
                    .withCreationDate(parseDate(album.getDtInsercao()))
                    .withCategory(new CategoryVO(album.getCategoriaID(), album.getCategoria(album.getCategoriaID())))
                    .withPhotos(photosVO)
                    .build();
            albumDAO.save(albumVO);
            
            albumID = albumVO.getAlbumid();
            album.setAlbumID(albumID);
            
            sucesso = true;
        } catch (Exception ex) {
            Logger.getLogger(AcaoAlterarAlbum.class.getName()).log(Level.SEVERE, null, ex);
            sucesso = false;
        }

        // PASSO 2 - Mover e renomear aquivos
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
        for (Iterator<PhotoVO> it = albumVO.getPhotos().iterator(); it.hasNext();) {
            PhotoVO photoVO = it.next();
            // copia somente arquivos ainda n�o cadastrados
            if (photoVO.getCaminhoArquivo().length() > 0) {
                try {
                    FileChannel canalOrigem = new FileInputStream(photoVO.getCaminhoArquivo()).getChannel();
                    FileChannel canalDestino = new FileOutputStream(
                            caminhoAlbum + File.separator + photoVO.getId() + ".jpg").getChannel();
                    canalDestino.transferFrom(canalOrigem, 0, canalOrigem.size());
                } catch (Exception e) {
                    Util.log("[AcaoAlterarAlbum.executaAlteracoes.8]/ERRO: " + e);
                    sucesso = false;
                }
            }
        }// fim for

        prepareThumbsAndFTP(albumVO, caminhoAlbum);

        prepareExtraFiles(album, caminhoAlbum);

        fireChangesToGUI(fotos);

        dispatchAlbum();

        PainelWebFotos.setCursorWait(false);
    }

    /**
     * PASSO 3 - Fazer Thumbs e Adicionar em FTP
     *
     * @param fotos
     * @param albumID
     * @param caminhoAlbum
     */
    private void prepareThumbsAndFTP(AlbumVO albumVO, String caminhoAlbum) {
        Set<PhotoVO> photoVOs = albumVO.getPhotos();
        
        for (Iterator<PhotoVO> it = photoVOs.iterator(); it.hasNext();) {
            PhotoVO photoVO = it.next();

            String caminhoArquivo;

            // thumbs somente arquivos ainda n�o cadastrados
            if (photoVO.getCaminhoArquivo().length() > 0) {
                caminhoArquivo = caminhoAlbum + File.separator + photoVO.getId() + ".jpg";
                Thumbnail.makeThumbs(caminhoArquivo);

                // adicionar em CacheFTP
                CacheFTP.getCache().addCommand(CacheFTP.UPLOAD, albumVO.getAlbumid(),
                        photoVO.getId());
            }
        }
    }

    /**
     * PASSO 4 - Preparar os arquivos adicionais
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
            ex.printStackTrace(Util.err);
        }
    }

    /**
     * PASSO 5 - Limpar a flag CaminhoArquivo e apresentar as altera��es
     *
     * @param fotos
     */
    private void fireChangesToGUI(PhotoDTO[] fotos) {
        PhotoDTO f;

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
            ex.printStackTrace(Util.err);
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
     * PASSO 6 - Executar o sistema de envio por FTP
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
            Thread t = new Thread(new net.sf.webphotos.gui.util.FtpClient());
            t.start();
        }

    }

    private Date parseDate(final String dtInsercao) throws ParseException {
        SimpleDateFormat dataBR = new SimpleDateFormat("dd/MM/yy");
        Date dateParsed = dataBR.parse(dtInsercao);
        return dateParsed;
    }
}