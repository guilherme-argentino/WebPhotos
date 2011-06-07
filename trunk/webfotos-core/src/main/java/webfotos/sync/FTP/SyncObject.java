/*
 * FTP.java
 *
 * Created on 16 de Maio de 2006, 17:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.sync.FTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamListener;

import net.sf.webphotos.Album;
import net.sf.webphotos.BancoImagem;
import net.sf.webphotos.Foto;
import webfotos.sync.Sync;
import webfotos.sync.SyncEvent;
import webfotos.sync.SyncException;
import webfotos.sync.SyncListener;
import webfotos.util.Arquivo;
import webfotos.util.CacheFTP;
import webfotos.util.ComandoFTP;
import webfotos.util.Util;

/**
 * Interface entre o sistema e o envio de arquivos.
 * Herda a classe FTPClient e implementa (sobrescreve)
 * os métodos da interface Sync.
 * Possui métodos de transferência de arquivos, mudança de diretório,
 * criação de subdiretório, conexão e desconexão ao FTP e carregamento
 * de arquivos de FTP.
 * @author guilherme
 */
public class SyncObject extends FTPClient implements Sync {
    
    private int reply;

    private ArrayList<Arquivo> listaArquivos=new ArrayList<Arquivo>();
    private String ftpRoot = null;
    private Vector listFTP;
    private File albunsRoot=Util.getAlbunsRoot();
    
    private String usuario;
    private char[] senha;
    private long totalBytes=0;
    private long transmitido=0;
    private boolean enviarAltaResolucao;
    
    private SyncListener syncListener=null;
    private CopyStreamListener copyStreamListener=null;

    private String ftpHost;

    private int ftpPort;

    private int retry;

    /** Cria uma nova instância de FTP */
    public SyncObject() {
        super();
        enviarAltaResolucao = Util.getProperty("enviarAltaResolucao").equals("true");
    }
    
    /**
     * Método baseado no storeFile da classe FTPClient, do pacote commons/net.
     * Acrescenta um controle de processamento para saber quanto foi enviado/recebido.
     * 
     * @param streamOrigem Arquivo de origem
     * @param streamDestino Local de destino
     * @param streamSize Tamanho do arquivo
     * @throws java.io.IOException Problemas na leitura e escrita dos dados.
     */
    public void transferFile
            (InputStream streamOrigem, OutputStream streamDestino, long streamSize)
            throws  IOException {
        
        org.apache.commons.net.io.Util.copyStream(
            streamOrigem,
            streamDestino,
            getBufferSize(),
            streamSize,
            copyStreamListener,
            true);

        streamDestino.flush();
        streamOrigem.close();
        streamDestino.close();

        completePendingCommand();        
    }
    
    /**
     * Muda de diretório.
     * criando o diretório quando não existir.
     * TODO: Colocar o Util.log para trabalhar no fluxo de execução.
     * @param diretorioFilho Diretório que deve ser acessado.
     * @throws webfotos.sync.SyncException Erro de sincronização.
     * @throws java.io.IOException Erro de comunicação entre os dados.
     */
    public void cd(String diretorioFilho) throws IOException, SyncException {
        changeWorkingDirectory(getSyncFolder());
        Util.out.println(printWorkingDirectory());
        changeWorkingDirectory(diretorioFilho);
        if(getReplyCode() != FTPReply.CODE_250) {
            Util.log(getSyncFolder() + File.separator + diretorioFilho + " não existe..criando");
            if(! makeDirectory(diretorioFilho)) {
                throw new SyncException("[FTP.cd]/ERRO: não foi possível criar diretório " + diretorioFilho + " verifique suas permissões com o provedor");
            }
        }
        pwd();
        Util.log("ok cd " + printWorkingDirectory());        
    }

    /**
     * Cria um novo subdiretório no servidor FTP, no diretório atual
     * (se um pathname relativo é dado) ou onde especificado (se um pathname
     * absoluto é dado). Esta é uma versão recurssiva que cria os diretórios
     * somente quando são precisos.
     * @param pathName O nome do diretório a ser criado.
     * @return True se completou com sucesso, ou false caso não.
     * @exception IOException Se um erro de I/O ocorrer enquanto está enviando
     * comando para o servidor ou recebendo resposta dele.
     */
    @Override
    public boolean makeDirectory(String pathName) throws IOException {
        if(pathName.startsWith("/")) {
            changeWorkingDirectory("/");
            pathName = pathName.substring(1);
        }
        Util.out.println(super.printWorkingDirectory());
        String[] dirs = pathName.split("/");
        for(String dir : dirs) {
            if(!super.printWorkingDirectory().endsWith(dir)) {
                super.changeWorkingDirectory(dir);
                if(!FTPReply.isPositiveCompletion(super.getReplyCode())) {
                    super.makeDirectory(dir);
                    super.changeWorkingDirectory(dir);
                    if(!FTPReply.isPositiveCompletion(super.getReplyCode())) {
                        return false;
                    }
                }
            }
        }
        return (getReplyCode() == FTPReply.CODE_250 || getReplyCode() == FTPReply.CODE_250);
    }

    /**
     * Conecta ao servidor FTP.
     * Retorna uma confirmação da conexão através de um boolean.
     * TODO: remontar a função para que use somente dados externos a classe
     * @return Valor lógico que confirma a conexão.
     */
    public boolean connect() {
        boolean conectado = false;
        
        ftpHost=Util.getProperty("servidorFTP");
        ftpPort=Util.getConfig().getInt("FTPport");
        
        String ftpProxyHost=Util.getProperty("FTPproxyHost");
        int ftpProxyPort;
        try {
            ftpProxyPort=Util.getConfig().getInt("FTPproxyPort");
        } catch (Exception e) {
            ftpProxyPort=0;
        }

        Util.log("Iniciando conexão com " + ftpHost);
        try {
            //TODO: Preparar o suporte a múltiplas línguas
            FTPClientConfig auxConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
            configure(auxConfig);
            Util.out.println("Timeout (antes): " + getDefaultTimeout());
            setDefaultTimeout(25000);
            Util.out.println("Timeout (depois): " + getDefaultTimeout());

            //TODO: Testar o acesso via Proxy
            //      usando System.getProperties().put()
            //      http://java.sun.com/j2se/1.5.0/docs/guide/net/properties.html
            if(ftpProxyHost.equals(null) && ftpProxyPort != 0) {
	            System.getProperties().put("ftp.proxyHost", ftpProxyHost);
	            System.getProperties().put("ftp.proxyPort", ftpProxyPort);
            }
            
            super.connect(ftpHost, ftpPort);
            reply = getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                disconnect("[FtpClient.connect]/ERRO: não foi possivel conectar");
                return false;
            }
            Util.log("ok " + ftpHost + " encontrado.. autenticando..");

            SyncEvent ev = null;
            if(syncListener != null) {
                ev = new SyncEvent(this);
            }
            reply = FTPReply.NOT_LOGGED_IN;
            do {
                
                if(syncListener != null && ev != null) {
                    syncListener.logonStarted(ev);
                }                
                Util.log("servidor: " + ftpHost + " em " + ftpRoot + "\nsolicitando conexão...");

                login(usuario, new String(senha));
                Util.log("usuário: " + usuario + " senha: ***");
                reply = getReplyCode();
                retry--;
                

            } while (reply != FTPReply.USER_LOGGED_IN && retry >=0);
            
            if(reply != FTPReply.USER_LOGGED_IN) {
                disconnect("[FtpClient.connect]/ERRO: login/senha incorreto.");
                return conectado;
            } else {
                // conexão bem sucedida... armazenamos o nome/login
                BancoImagem.getBancoImagem().setUserFTP(getUsuario());
                BancoImagem.getBancoImagem().setPasswordFTP(getSenha());
            }
            
            Util.log("ok conexão aceita..");
            // autenticação ok..

            setFileType(FTPClient.BINARY_FILE_TYPE);
            //ftp.enterRemotePassiveMode();
            // TODO: Achar uma alternativa para realizar o logging do FTP
            //ftp.setLogFile("ftp.log");

            // tenta ir ao diretório FTPRoot... caso não consiga, tenta criar
            changeWorkingDirectory(ftpRoot);
            if(getReplyCode() != FTPReply.CODE_250) {
                Util.log(ftpRoot + " não existe..criando");
                if(makeDirectory(ftpRoot)) {
                    Util.log("[FtpClient.connect]/ERRO: não foi possível criar diretório " + ftpRoot + " Retorno: " + reply);
                    disconnect("não foi possível criar diretório");
                    return conectado;
                }
                changeWorkingDirectory(ftpRoot);
                reply = getReplyCode();
                if(reply != FTPReply.CODE_250) {
                    disconnect("[FtpClient.connect]/ERRO: não foi possível entrar no diretório " + ftpRoot + " que foi recém criado.");
                    return conectado;
                }
            }
            conectado = true;
            getSyncListener().connected(new SyncEvent(this));
        } catch(Exception e) {
            conectado = false;
            e.printStackTrace();
            disconnect("[FtpClient.connect]/ERRO: não foi possivel manter esta conexão");
        }
        
        return conectado;
        
    }
    
    /**
     * Desconecta do servidor FTP e apresenta uma mensagem de log.
     * @param msg Mensagem de desconexão.
     */
    public void disconnect(String msg) {
        try {
            Util.log("Desconectando (" + msg + ") ok");
            super.disconnect();			
        } catch (Exception e) {
            Util.log("Erro ao tentar desconectar.");
        } finally {
            Util.log(msg);
            try {
                // ao finalizar, verificar se houve erros
                // arquivos com status <> "ok -..." serão re-enfileirados
                Iterator iter = listFTP.iterator();
                while(iter.hasNext()) {
                    Arquivo a = new Arquivo((Vector) iter.next());
                    //Arquivo a=(Arquivo) iter.next();
                    if(! a.getStatus().startsWith("ok")) {
                        CacheFTP.getCache().addCommand(a.getAcao(), a.getAlbumID(), a.getFotoID());
                    }
                }// fim while
            } catch (Exception e) {}
            // Dispara o evento de desconnectado
            if(syncListener != null) {
                syncListener.disconnected(new SyncEvent(this));
            }
        }		
    }
    
    /**
     * Faz um load no ArrayList CacheFTP, faz uma busca por iteração, identifica
     * e carrega as linhas de comandos na seguinte ordem:
     * DELETE, UPLOAD e DOWNLOAD.
     * Carrega esses comandos através do método
     * {@link webfotos.sync.FTP.SyncObject#loadSyncCacheLine() loadSyncCacheLine}().
     * Por último, após completo o load, limpa a lista do CacheFTP.
     */
    public void loadSyncCache() {

        Iterator<ComandoFTP> i = CacheFTP.getCache().iterator();
        String linha;
        Util.out.println ("Numero de linhas: " + CacheFTP.getCache().toString());
        // primeiro delete		
        while(i.hasNext()) {
            linha=i.next().toString();
            if(linha.startsWith("3")) loadSyncCacheLine(linha);
        }
        // depois upload
        i=CacheFTP.getCache().iterator();
        while(i.hasNext()) {
            linha=i.next().toString();
            if(linha.startsWith("1")) loadSyncCacheLine(linha);
        }
        // depois download
        i=CacheFTP.getCache().iterator();
        while(i.hasNext()) {
            linha=i.next().toString();
            if(linha.startsWith("2")) loadSyncCacheLine(linha);
        }

        // limpa o CacheFTP
        CacheFTP.getCache().clear();
        
    }	

    /**
     * Recebe uma linha com comando de FTP (DELETE, DOWNLOAD ou UPLOAD),
     * processa o tipo "acao albumID foto"
     * e a carrega em cima do ArrayList listaArquivos, que contém dados de
     * {@link webfotos.util.Arquivo Arquivo}.
     * @param linha Linha de comando FTP.
     */
    public void loadSyncCacheLine(String linha) {
        StringTokenizer tok=new StringTokenizer(linha);
        int acao=-1;
        int albumID=-1;
        int fotoID=-1;

        Util.out.println ("carrega: " + linha);

        if(tok.countTokens()==3) {
            acao=Integer.parseInt(tok.nextToken());
            albumID=Integer.parseInt(tok.nextToken());
            fotoID=Integer.parseInt(tok.nextToken());
        } else {
            // houve um erro...
            Util.out.println ("erro: " + linha);
            return;
        }

        // obtem uma lista do álbum (todos os arquivos)
        File f=new File(getAlbunsRoot(), Integer.toString(albumID));
        String[] ls=f.list();

        switch(acao) {
            // Apagar
            case CacheFTP.DELETE:
            // Receber
            case CacheFTP.DOWNLOAD:
                if(fotoID==0) {
                    // O álbum inteiro
                    listaArquivos.add(new Arquivo(linha,acao,albumID,0,"* todos"));
                } else {
                    // Uma foto
                    listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_a" + fotoID + ".jpg"));
                    listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_b" + fotoID + ".jpg"));
                    listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_c" + fotoID + ".jpg"));
                    listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_d" + fotoID + ".jpg"));
                    if(isEnviarAltaResolucao() == true) {
                        listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,fotoID + ".jpg"));
                        listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,fotoID + ".zip"));
                    }
                    listaArquivos.add(new Arquivo(linha,acao,albumID,0,albumID + ".xml"));                
                    listaArquivos.add(new Arquivo(linha,acao,albumID,0,albumID + ".js"));
                }
            break;
            // Enviar
            case CacheFTP.UPLOAD:
                if(fotoID==0) {
                    // O álbum inteiro
                    Album.getAlbum().loadAlbum(albumID);
                    for(Foto atual: Album.getAlbum().getFotos()) {
                        fotoID = atual.getFotoID();
                        listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_a" + fotoID + ".jpg"));
                        listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_b" + fotoID + ".jpg"));
                        listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_c" + fotoID + ".jpg"));
                        listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,"_d" + fotoID + ".jpg"));
                        if(isEnviarAltaResolucao() == true) {
                            listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,fotoID + ".jpg"));
                            listaArquivos.add(new Arquivo(linha,acao,albumID,fotoID,fotoID + ".zip"));
                        }
                    }
                    listaArquivos.add(new Arquivo(linha,acao,albumID,0,albumID + ".xml"));                
                    listaArquivos.add(new Arquivo(linha,acao,albumID,0,albumID + ".js"));
                } else {
                    // Uma foto
                    Util.out.println ("Upload alta: " + isEnviarAltaResolucao());
                    for(String fileName : ls) {
                        if( (fileName.startsWith("_") && fileName.toLowerCase().endsWith(fotoID + ".jpg")) ||
                            (isEnviarAltaResolucao() && fileName.toLowerCase().endsWith(fotoID + ".zip")) ||
                            (isEnviarAltaResolucao() && fileName.toLowerCase().endsWith(fotoID + ".jpg")) ){

                            Arquivo a=new Arquivo(linha, acao, albumID, fotoID, fileName);
                            listaArquivos.add(a);
                        }
                    } // fim for
                }
            break;
        }
    }

    /**
     * Retorna o caminho que deve usar
     * @return Mostra o caminho base
     */
    public String getSyncFolder() {
        return ftpRoot;
    }

    /**
     * Determina qual caminho usar
     * @param ftpRoot Parâmetro que recebe a informação
     */
    public void setSyncFolder(String ftpRoot) {
        this.ftpRoot = ftpRoot;
    }

    /**
     * Retorna o ouvinte syncListener.
     * @return Retorna um listener de sincronização.
     */
    public SyncListener getSyncListener() {
        return syncListener;
    }

    /**
     * Seta o ouvinte syncListener.
     * @param listener Um listener de sincronização.
     */
    public void setSyncListener(SyncListener listener) {
        this.syncListener = listener;
    }

    /**
     * Retorna o objeto copyStreamListener.
     * @return Retorna copyStreamListener.
     */
    public CopyStreamListener getCopyStreamListener() {
        return copyStreamListener;
    }

    /**
     * Seta o objeto copyStreamListener.
     * @param copyStreamListener Objeto da classe CopyStreamListener.
     */
    public void setCopyStreamListener(CopyStreamListener copyStreamListener) {
        this.copyStreamListener = copyStreamListener;
    }

    /**
     * Retorna o usuário.
     * @return Retorna um usuário.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Seta um nome para usuário.
     * @param usuario Usuário.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha do usuário.
     * @return Retorna uma senha.
     */
    public char[] getSenha() {
        return senha;
    }

    /**
     * Seta uma senha para o usuário.
     * @param senha Senha do usuário.
     */
    public void setSenha(char[] senha) {
        this.senha = senha;
    }

    /**
     * Retorna o ArrayList listaArquivos.
     * @return Retorna listaArquivos.
     */
    public ArrayList<Arquivo> getListaArquivos() {
        return listaArquivos;
    }

    /**
     * Seta uma lista para a variável listaArquivos.
     * @param _listaArquivos Lista de arquivos.
     */
    public void setListaArquivos(ArrayList<Arquivo> _listaArquivos) {
        this.listaArquivos = _listaArquivos;
    }

    /**
     * Retorna o valor de enviarAltaResolucao.
     * Especifica se serão enviadas ou não, as imagens originais.
     * @return Retorna um valor lógico.
     */
    public boolean isEnviarAltaResolucao() {
        return enviarAltaResolucao;
    }

    /**
     * Retorna o diretório raiz de albúns.
     * TODO: Já existe um método igual a esse na classe Util.
     * @return Retorna um diretório.
     */
    public File getAlbunsRoot() {
        return albunsRoot;
    }
    
}
