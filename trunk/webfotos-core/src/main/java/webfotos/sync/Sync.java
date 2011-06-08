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
/*
 * Sync.java
 *
 * Created on 23 de Maio de 2006, 11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.sync;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamListener;
import webfotos.util.Arquivo;

/**
 * Interface que especifica como webfotos ir�
 * sincronizar arquivos.
 * @author guilherme
 */
public interface Sync {

    /**
     * Retorna um InputStream para o arquivo espec�fico.
     * @param arquivo Nome do arquivo.
     * @return Retorna um InputStream.
     * @throws java.io.IOException Erro durante transmiss�o de I/O.
     */
    InputStream retrieveFileStream(String arquivo) throws IOException;
    /**
     * Retorna um OutputStream para o arquivo espec�fico.
     * @param arquivo Nome do arquivo.
     * @return Retorna um OutputStream.
     * @throws java.io.IOException Erro durante transmiss�o de I/O.
     */
    OutputStream storeFileStream(String arquivo) throws IOException;
    /**
     * Faz a transfer�ncia de um arquivo.
     * @param streamOrigem Arquivo de origem.
     * @param streamDestino Local de destino.
     * @param streamSize Tamanho do arquivo.
     * @throws java.io.IOException Problemas na leitura e escrita dos dados.
     */
    void transferFile(InputStream streamOrigem, OutputStream streamDestino, long streamSize) throws IOException;
    /**
     * Deleta um arquivo especificado pelos par�metros.
     * @param string Nome do arquivo.
     * @return Retorna uma confirma��o de exclus�o.
     * @throws java.io.IOException Erro durante transmiss�o de I/O.
     */
    boolean deleteFile(String string) throws IOException;
    
    /**
     * Muda o diret�rio.
     * @param diretorioFilho Diret�rio que deve ser acessado.
     * @throws java.io.IOException Erro de sincroniza��o.
     * @throws webfotos.sync.SyncException Erro de comunica��o entre os dados.
     */
    void cd(String diretorioFilho) throws IOException, SyncException;
    /**
     * Cria um novo subdiret�rio no diret�rio utilizado.
     * @param pathName O nome do diret�rio a ser criado.
     * @throws java.io.IOException Se um erro de I/O ocorrer enquanto est� sendo enviado
     * um comando ao servidor ou recebendo uma resposta do servidor.
     * @return Retorna <I>true</I> caso ocorra com sucesso.
     */
    boolean makeDirectory(String pathName) throws IOException;
    /**
     * Muda o diret�rio de trabalho baseado no novo passado como par�metro.
     * @param pathName Novo diret�rio de trabalho.
     * @return Retorna uma confirma��o.
     * @throws java.io.IOException Erro durante uma transmiss�o de I/O.
     */
    boolean changeWorkingDirectory(String pathName) throws IOException;
    /**
     * Retorna o nome do diret�rio de trabalho.
     * @return Retorna o diret�rio.
     * @throws java.io.IOException Erro durante transmiss�o de I/O.
     */
    String printWorkingDirectory() throws IOException;
    /**
     * Remove um diret�rio atrav�s de um ID de alb�m recebido.
     * @param albumID ID do alb�m.
     * @return Retorna uma confima��o de exclus�o do diret�rio.
     * @throws java.io.IOException Exce��o durante transmiss�o de I/O.
     */
    boolean removeDirectory(String albumID) throws IOException;
    
    
    /**
     * Conecta ao FTP.
     * @return Retorna uma confirma��o para a conex�o.
     */
    boolean connect();
    /**
     * Desconecta do FTP e apresenta uma mensagem explicando o motivo.
     * @param msg Mensagem do motivo da desconex�o.
     */
    void disconnect(String msg);
    
    /**
     * Seta um objeto CopyStreamListener.
     * TODO: esse m�todo contem objetos espec�ficos - retir�-lo.
     * @param copyStreamListener Objeto da classe CopyStreamListener.
     */
    void setCopyStreamListener(CopyStreamListener copyStreamListener);
    /**
     * Retorna um objeto CopyStreamListener.
     * TODO: esse m�todo contem objetos espec�ficos - retir�-lo.
     * @return Retorna CopyStreamListener.
     */
    CopyStreamListener getCopyStreamListener();
    
    /**
     * Seta o ouvinte syncListener.
     * @param listener Um listener de sincroniza��o.
     */
    void setSyncListener(SyncListener listener);
    /**
     * Retorna o ouvinte syncListener.
     * @return Retorna um listener de sincroniza��o.
     */
    SyncListener getSyncListener();
    
    /**
     * Determina qual caminho usar.
     * @param ftpRoot Par�metro que recebe a informa��o.
     */
    void setSyncFolder(String ftpRoot);
    /**
     * Retorna o caminho que deve usar.
     * @return Mostra o caminho base.
     */
    String getSyncFolder();

    /**
     * Retorna o usu�rio.
     * @return Retorna um usu�rio.
     */
    String getUsuario();
    /**
     * Seta um nome de usu�rio.
     * @param usuario Usu�rio.
     */
    void setUsuario(String usuario);

    /**
     * Retorna uma senha de usu�rio.
     * @return Retorna uma senha.
     */
    char[] getSenha();
    /**
     * Seta uma senha de usu�rio.
     * @param senha Senha.
     */
    void setSenha(char[] senha);

    /**
     * Retorna uma lista de arquivos.
     * @throws java.io.IOException Erro durante transmiss�o de I/O.
     * @return Retorna um vetor com os arquivos.
     */
    FTPFile[] listFiles() throws IOException;

    /**
     * Retorna o tamanho do Buffer.
     * @return Retorna um tamanho inteiro.
     */
    int getBufferSize();
    /**
     * Carrega uma linha de comando FTP.
     * @param linha Linha de comando FTP.
     */
    void loadSyncCacheLine(String linha);
    /**
     * Retorna uma lista de arquivos.
     * @return Retorna uma lista de arquivos.
     */
    ArrayList<Arquivo> getListaArquivos();

    /**
     * Procure sua utiliza��o em 
     * {@link webfotos.sync.FTP.SyncObject#loadSyncCache() loadSyncCache}().
     */
    void loadSyncCache();
    /**
     * Retorna a vari�vel boolean enviarAltaResolu��o para especificar se ser�o
     * enviadas fotos originais ou n�o.
     * @return Retorna um valor l�gico.
     */
    boolean isEnviarAltaResolucao();

}
