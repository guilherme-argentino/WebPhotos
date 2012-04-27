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
package net.sf.webphotos.gui.util;

import net.sf.webphotos.sync.Sync;
import net.sf.webphotos.sync.SyncException;
import net.sf.webphotos.sync.SyncEvent;
import net.sf.webphotos.BancoImagem;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamException;
import org.apache.commons.net.io.CopyStreamAdapter;

import net.sf.webphotos.sync.FTP.SyncAdapter;
import net.sf.webphotos.util.legacy.Modal;
import net.sf.webphotos.util.Util;
import net.sf.webphotos.util.legacy.Arquivo;

import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.apache.log4j.Logger;
import net.sf.webphotos.util.ApplicationContextResource;


/**
 * Cliente FTP.
 * Cria uma interface para trabalhar com os comandos de FTP.
 * Implementa a classe Runnable, indica o uso de FtpClient por meio de thread.
 */
public class FtpClient extends JFrame implements Runnable {
    private static final int ONE_SECOND = 1000;
    
    private static final Logger log = Logger.getLogger(FtpClient.class);
    
    private String ftpRoot=Util.getProperty("FTPRoot");
    private static File albunsRoot=Util.getAlbunsRoot();
    private Container cp;
    private JScrollPane scrTabela;
    private JTable tabela;
    private JTextArea txtLog=new JTextArea();
    private JScrollPane scrLog=new JScrollPane(txtLog);
    private JProgressBar barra=new JProgressBar(0,100);
    private JProgressBar barraArquivoAtual=new JProgressBar(0,100);
    private JLabel lblServidor=new JLabel();
    private JLabel lblArquivos=new JLabel("",JLabel.RIGHT);
    private JLabel lblKbytes=new JLabel("0 Kb");
    private JLabel lblKbytesArquivoAtual=new JLabel("0 Kb");
    private JButton btFechar=new JButton("fechar");
    private FTPTabelModel modeloTabela;

    private Modal modal;

    private Sync ftp;
    private int reply;
    private int retry;
    private String usuario;
    private String ftpHost;
    private int ftpPort;
    private char[] senha;
    private long totalBytes=0;
    private long transmitido=0;

    private FTPFile remoteFiles[] = null;
    private FTPFile remoteFile = null;
    
    // devemos enviar para internet os originais ?
    private boolean enviarAltaResolucao;	

    /**
     * Construtor da classe.
     * Prepara a interface de sincronização.
     * Configura o sistema de sincronização e confere o valor de
     * enviarAltaResolução para enviar originais ou não.
     * Seta os valores dos listeners de sincronização implementando os
     * métodos da interface Sync.
     */
    public FtpClient() {
        cp=getContentPane();
        /**
         * Preparando a interface de sincronização
         */
        retry = 3;
        try {
            //TODO: transformar este acesso em parâmetro do sistema
            ftp = (Sync)ApplicationContextResource.getBean("syncObject");
            /**
             * Configuração do sistema de sincronização
             */
            ftp.setSyncFolder(ftpRoot);
            enviarAltaResolucao = ftp.isEnviarAltaResolucao();
            ftp.loadSyncCache();
        } catch (Exception ex) {
            Util.log("[FtpClient.run]/ERRO: Inexperado.");
            log.error(ex);
            System.exit(1);
        }
        
        // ajusta o valor de enviarAltaResolucao
        if (enviarAltaResolucao==false) {
            this.setTitle("Transmissão FTP - Sem Enviar Originais");
        } else {
            this.setTitle("Transmissão FTP - Enviando Originais");
        }
        txtLog.setFont(new Font("courier",Font.PLAIN,11));
        Util.setLoggingTextArea(txtLog);
        cp.setLayout(null);

        cp.add(lblArquivos);
        lblArquivos.setBounds(348,3,60,18);

        cp.add(lblServidor);
        lblServidor.setBounds(8,3,340,18);

        txtLog.setWrapStyleWord(true);
        txtLog.setLineWrap(true);
        cp.add(scrLog);
        scrLog.setBounds(8,127, 400,70);

        modeloTabela=new FTPTabelModel(ftp.getListaArquivos());
        tabela=new JTable(modeloTabela);

        cp.add(tabela);
        scrTabela=new JScrollPane(tabela);
        cp.add(scrTabela);
        scrTabela.setBounds(8,20,400,100);
        cp.validate();	

        // barraArquivoAtual, kbytes, botao
        cp.add(barraArquivoAtual);
        barraArquivoAtual.setStringPainted(true);
        barraArquivoAtual.setBounds(8,235,234,18);
        barraArquivoAtual.setToolTipText("Progresso do arquivo atual");

        cp.add(lblKbytesArquivoAtual);
        lblKbytesArquivoAtual.setBounds(246,235,58,18);

        // barra, kbytes, botao
        cp.add(barra);
        barra.setStringPainted(true);
        barra.setBounds(8,205,234,18);
        barra.setToolTipText("Progresso total");

        cp.add(lblKbytes);
        lblKbytes.setBounds(246,205,58,18);

        cp.add(btFechar);
        btFechar.setBounds(308,204,100,20);
        btFechar.setEnabled(false);	

        this.setSize(420,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(FtpClient.DO_NOTHING_ON_CLOSE);
        this.getContentPane().repaint();
        this.setVisible(true);

        modal=new Modal(this);
        this.addWindowFocusListener(modal);

        // ouvinte para o botão fechar
        btFechar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exit();
            }
        });
        
        // ouvinte para o fechamento convencional do JFrame
        // TODO: trocar o if para checagem de parâmetro
        if(1 == 2) {
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    exit();
                }
            });
        }

        ftp.setSyncListener(new SyncAdapter(){
            @Override
            public void connected(SyncEvent e) {
                lblServidor.setText(ftpHost + ":" + ftpPort + " / usuário: " + usuario);		
            }
            @Override
            public void logonStarted(SyncEvent event) {
                // Autenticacao	
                if(!event.isRetrying()){
                    if(BancoImagem.getBancoImagem().getUserFTP()!=null) {
                        // Vamos tentar conectar com a senha própria de FTP
                        usuario=BancoImagem.getBancoImagem().getUserFTP();
                        senha=BancoImagem.getBancoImagem().getPasswordFTP();
                    } else if(BancoImagem.getBancoImagem().getUser()!=null) {
                        // Ou o mesmo usuário/senha do banco de dados
                        usuario=BancoImagem.getBancoImagem().getUser();
                        senha=BancoImagem.getBancoImagem().getPassword();
                    } else {
                        showLogonDialog();
                    }
                } else {
                    showLogonDialog();
                }
                ftp.setUsuario(usuario);
                ftp.setSenha(senha);
            }
            @Override
            public void disconnected(SyncEvent e) {
                //TODO: repensar esse comando
                //Util.setLoggingTextArea(PainelWebFotos.getTxtLog());
                btFechar.setEnabled(true);
            }
            private void showLogonDialog() {
                // Ou requisitamos do usuário
                Login l=new Login("FTP " + ftpHost);
                removeWindowFocusListener(modal);
                l.show();
                addWindowFocusListener(modal);
                usuario=l.getUser();
                senha=l.getPassword();                
            }
        });
        
        ftp.setCopyStreamListener(new CopyStreamAdapter() {
            @Override
            public void bytesTransferred(long totalBytesTransferred,
                    int bytesTransferred,
                    long streamSize) {
                barraArquivoAtual.setValue((int)totalBytesTransferred);
                lblKbytesArquivoAtual.setText(Math.round((float)totalBytesTransferred/1024) + " Kb");
            }
        });
    }
    
    private void exit() {
        //TODO: repensar esse comando
        //Util.setLoggingTextArea(PainelWebFotos.getTxtLog());
        dispose();
    }

    /**
     * Executa o comando FTP.
     * Utiliza o método {@link webfotos.sync.Sync#loadSyncCache() loadSyncCache}()
     * para fazer o load do arquivos com os comandos de FTP.
     * Checa se existem comandos a executar, caso positivo, tenta a conexão
     * com FTP e executa os comandos (UPLOAD, DELETE ou DOWNLOAD).
     */
    public void run() {

        String acao = null;
        String albumID;
        String arquivo;
        long tamanho;

        String arqOrigem;
        String arqDestino;

        Object streamOrigem = null;
        Object streamDestino = null;

        String ultimoID="";
        int i, j = 0;

        String diretorioDownload = null;
        // loadSyncCache arquivo
        ftp.loadSyncCache();
        modeloTabela.refresh(ftp.getListaArquivos());
        modeloTabela.fireTableDataChanged();

        // tem algum comando à executar ?
        if(tabela.getRowCount()==0) {
            ftp.disconnect("Não há comandos ftp");
            return;
        }

        // tenta a conexao com FTP
        if(! ftp.connect()) return;
        preProcessBatch();
        modeloTabela.refresh(ftp.getListaArquivos());
        modeloTabela.fireTableDataChanged();
        barra.setMaximum(Integer.parseInt(Long.toString(totalBytes)));

        // executa os comandos
        for(i=0; i < tabela.getRowCount(); i++) {

            lblArquivos.setText(i+1 + " / " + tabela.getRowCount());
            tabela.setRowSelectionInterval(i,i);
            tabela.scrollRectToVisible(tabela.getCellRect(i+1,0,true));
            tabela.repaint();
            acao=tabela.getValueAt(i,1).toString();
            albumID=tabela.getValueAt(i,2).toString();
            arquivo=tabela.getValueAt(i,4).toString();

            // ajusta o diretório para /ftpRoot/albumID
            // recebe a lista de arquivos 
            if(!ultimoID.equals(albumID)) {
                Util.log ("Mudando para o diretório " + albumID);
                try {
                    ftp.cd(albumID);
                    remoteFiles = ftp.listFiles();
                    diretorioDownload = null;
                } catch (IOException ioE) {
                    Util.log("[FtpClient.run]/ERRO: comando não foi aceito ao listar o diretório " + albumID + " desconectando");
                    ftp.disconnect("não foi possivel entrar no diretorio");
                } catch (SyncException se) {
                    Util.log(se.getMessage());
                    ftp.disconnect("não foi possivel entrar no diretorio");
                }
            }

            // UPLOAD
            if(acao.equals("enviar")) {
                if(diretorioDownload==null)
                    diretorioDownload=albunsRoot.getAbsolutePath() + File.separator + albumID;
                arqOrigem = diretorioDownload + File.separator + arquivo;
                Util.out.println (arqOrigem + " -> " + arquivo);
                try {
                    streamOrigem = new FileInputStream(arqOrigem);
                    streamDestino = new BufferedOutputStream(ftp.storeFileStream(arquivo), ftp.getBufferSize());
                    this.transfereArquivo((InputStream)streamOrigem, (OutputStream)streamDestino, Long.parseLong(tabela.getValueAt(i,5).toString()));
                    tabela.setValueAt("ok",i,0);
                } catch (FileNotFoundException ioE) {
                    Util.log("[FtpClient.run]/AVISO: " + arqOrigem + " não foi encontrado.");
                    tabela.setValueAt("ok - ne",i,0);
                } catch (IOException ioE) {
                    Util.log("[FtpClient.run]/ERRO: erro na transmissão de " + arqOrigem);
                    ioE.printStackTrace(Util.out);
                    tabela.setValueAt("erro",i,0);
                } catch (Exception e) {
                    Util.err.println("Erro inexperado: " + e.getMessage());
                    e.printStackTrace(Util.out);
                    tabela.setValueAt("erro",i,0);
                } finally {
                    try { ftp.printWorkingDirectory(); } catch (IOException e) {}
                    try {
                        ((OutputStream)streamDestino).close();
                        ((InputStream)streamOrigem).close();
                    } catch (Exception e) {}                    
                }
                posTransfer(i);

            // DELETE
            } else if(acao.equals("apagar")) {

                // apaga o diretorio inteiro
                if(arquivo.equals("* todos")) {
                    try {
                        for(FTPFile remote : remoteFiles) {
                            ftp.deleteFile(remote.getName());
                            Util.log("Removendo arquivo remoto " + remote.getName());
                            transmitido+=remote.getSize();
                            Util.out.println("Processado " + transmitido + " de " + totalBytes);
                            barra.setValue((int)transmitido);
                            lblKbytes.setText(Math.round((float)transmitido/1024) + " Kb");
                        }

                        // Volta para o diretório principal
                        ftp.changeWorkingDirectory(ftpRoot);
                        // finalmente remove o diretorio
                        ftp.removeDirectory(albumID);
                        tabela.setValueAt("ok",i,0);

                    } catch (Exception e) {
                        tabela.setValueAt("erro",i,0);
                        log.error(e);
                    }
                // apaga somente uma foto
                } else {
                    for(FTPFile remote : remoteFiles) {
                        if(remote.getName().equals(arquivo)) {
                            remoteFile = remote;
                            break;
                        }
                    }
                    //remoteFile=RemoteFile.findRemoteFile(remoteFiles,arquivo);
                    if(remoteFile==null) {
                        tabela.setValueAt("ok - ne",i,0);
                    } else {
                        tabela.setValueAt(Long.toString(remoteFile.getSize()),i,5);
                        try {
                            ftp.deleteFile(arquivo);
                            tabela.setValueAt("ok",i,0);
                            
                            posTransfer(i);
                        } catch (Exception e) {
                            tabela.setValueAt("erro",i,0);					
                        }
                    }		
                }

            // DOWNLOAD - recebe os arquivos (pré listado e calculado)
            } else if(acao.equals("receber")) {
                try {
                    // cada vez que muda o diretório, a variável diretórioDownload é nula
                    if(diretorioDownload==null) {
                        diretorioDownload=albunsRoot.getAbsolutePath() + File.separator + albumID;
                        File temp=new File(diretorioDownload);

                        if(!temp.isDirectory()) {
                            temp.mkdir();
                            Util.log("Criando diretório " + diretorioDownload);
                        }
                        temp=null;
                    }
                    arqDestino=diretorioDownload + File.separator + arquivo;
                    Util.out.println (arquivo + " -> " + arqDestino);

                    streamOrigem =
                        new BufferedInputStream(
                            ftp.retrieveFileStream(arquivo),
                            ftp.getBufferSize());

                    streamDestino =
                        new FileOutputStream(arqDestino);

                    this.transfereArquivo((InputStream)streamOrigem, (OutputStream)streamDestino, Long.parseLong(tabela.getValueAt(i,5).toString()));
                    tabela.setValueAt("ok",i,0);

                    // calcula porcentagem e atualiza barra
                    posTransfer(i);

                } catch (IOException ioE) {
                    Util.err.println("Erro de transmissão: " + ioE.getMessage() + " " +
                            ((CopyStreamException)ioE).getIOException().getMessage());
                    tabela.setValueAt("erro",i,0);
                    log.error(ioE);
                } catch (Exception e) {
                    Util.err.println("Erro: ");
                    log.error(e);
                    tabela.setValueAt("erro",i,0);
                } finally {
                    try { ftp.printWorkingDirectory(); } catch (IOException e) {}
                    try {
                        ((InputStream)streamOrigem).close();
                        ((OutputStream)streamDestino).close();					
                    } catch (IOException e) {}
                }
            }// fim if

            ultimoID=albumID;
        } // fim for
        ftp.disconnect("transmissão terminada");
    }

    private void preProcessBatch() {
        String acao;
        Arquivo tmpArq;
        int i, j = 0;
        
        // calcula quantidade de bytes a transmitir (upload e download somente)
        for(i=0; i < tabela.getRowCount(); i++) {
            acao=tabela.getValueAt(i,1).toString();
            if(acao.equals("enviar")) {
                totalBytes+=Long.parseLong(tabela.getValueAt(i,5).toString());
            }
            if(acao.equals("receber")) {
                Util.log("Calculando bytes a serem recebidos (álbum " + tabela.getValueAt(i,2) + ")");
                tmpArq = (Arquivo)ftp.getListaArquivos().get(i);
                
                if(tmpArq.getNomeArquivo().equals("* todos")) {
                    // Baixando todo o Álbum
                    try {
                        Util.out.println("Convertendo: " + tmpArq.getAlbumID());
                        // remove o ítem atual e diminui 
                        // o marcador após a remoção
                        ftp.getListaArquivos().remove(i--);
                        ftp.cd(Integer.toString(tmpArq.getAlbumID()));
                        remoteFiles = ftp.listFiles();
                        for(FTPFile remote : remoteFiles) {
                            ftp.getListaArquivos().add(
                                    new Arquivo(tmpArq.getLinhaComando(),2,
                                        tmpArq.getAlbumID(),tmpArq.getFotoID(),remote.getName(),
                                        remote.getSize())
                                );
                            totalBytes+=remote.getSize();
                        } // fim for
                    } catch (SyncException se) {
                        Util.log(se.getMessage());
                        ftp.disconnect("não foi possivel entrar no diretorio");
                    } catch(Exception e) {
                        log.error(e);
                    }
                } else {
                    // Baixando um arquivo
                    try {
                        ftp.cd(Integer.toString(tmpArq.getAlbumID()));
                        remoteFiles = this.ftp.listFiles();
                        for(FTPFile remote : remoteFiles) {
                            if(tmpArq.getNomeArquivo().equals(remote.getName())) {
                                totalBytes+=remote.getSize();
                                tmpArq.setTamanho(remote.getSize());
                                break;
                            }
                        } // fim for
                    } catch (SyncException se) {
                        Util.log(se.getMessage());
                        ftp.disconnect("não foi possivel entrar no diretorio");
                    } catch(Exception e) {
                        log.error(e);
                    }
                }
            }
            if(acao.equals("apagar")) {                
                Util.log("Calculando bytes a serem apagados (álbum " + tabela.getValueAt(i,2) + ")");
                tmpArq = (Arquivo)ftp.getListaArquivos().get(i);
                if(tmpArq.getNomeArquivo().equals("* todos")) {
                    // Apagando todo o Álbum
                    try {
                        Util.out.println("Convertendo: " + tmpArq.getAlbumID());
                        ftp.cd(Integer.toString(tmpArq.getAlbumID()));
                        remoteFiles = this.ftp.listFiles();
                        for(FTPFile remote : remoteFiles) {
                            totalBytes+=remote.getSize();
                        }
                    } catch (SyncException se) {
                        Util.log(se.getMessage());
                        ftp.disconnect("não foi possivel entrar no diretorio");
                    } catch(Exception e) {
                        log.error(e);
                    }
                } else {
                    // Apagando um arquivo
                    try {
                        ftp.cd(Integer.toString(tmpArq.getAlbumID()));
                        remoteFiles = this.ftp.listFiles();
                        for(FTPFile remote : remoteFiles) {
                            if(tmpArq.getNomeArquivo().equals(remote.getName())) {
                                totalBytes+=remote.getSize();
                                tmpArq.setTamanho(remote.getSize());
                                break;
                            }
                        }
                    } catch (SyncException se) {
                        Util.log(se.getMessage());
                        ftp.disconnect("não foi possivel entrar no diretorio");
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
    }

    private void posTransfer(final int i) throws NumberFormatException {
        transmitido+=Long.parseLong(tabela.getValueAt(i,5).toString());
        Util.out.println("Processado " + transmitido + " de " + totalBytes);
        barra.setValue((int)transmitido);
        lblKbytes.setText(Math.round((float)transmitido/1024) + " Kb");
    }
    
    private void transfereArquivo
            (InputStream streamOrigem, OutputStream streamDestino, long streamSize)
            throws  CopyStreamException,
                    IOException {
        
        barraArquivoAtual.setValue(0);
        barraArquivoAtual.setMaximum((int)streamSize);
        lblKbytesArquivoAtual.setText("0 Kb");
        
        try {
            ftp.transferFile(streamOrigem,streamDestino,streamSize);
        } catch (IOException e) {
            try { streamOrigem.close(); streamDestino.close(); } catch (IOException f) {}
            throw e;
        }
        
    }
    
    /**
     * Execução separada do sistema.
     * Serve, principalmente, para sincronizar as pastas
     * entre o servidor e o cliente.
     * Obtém o driver do db e url, configura esses valores para o banco de 
     * imagens e mostra a tela de login.
     * Depois inicia uma thread de FtpClient.
     * @param args args do método main.
     */
    public static void main(String args[]) {
        try {
            Util.loadSocksProxy();
            BancoImagem.loadUIManager();
            BancoImagem.loadDBDriver();

            // mostra tela de login
            BancoImagem.login();

            Thread cl=new Thread(new FtpClient());
            cl.start();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

}
