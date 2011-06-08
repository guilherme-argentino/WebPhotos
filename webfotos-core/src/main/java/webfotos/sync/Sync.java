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
 * Interface que especifica como webfotos irá
 * sincronizar arquivos.
 * @author guilherme
 */
public interface Sync {

    /**
     * Retorna um InputStream para o arquivo específico.
     * @param arquivo Nome do arquivo.
     * @return Retorna um InputStream.
     * @throws java.io.IOException Erro durante transmissão de I/O.
     */
    InputStream retrieveFileStream(String arquivo) throws IOException;
    /**
     * Retorna um OutputStream para o arquivo específico.
     * @param arquivo Nome do arquivo.
     * @return Retorna um OutputStream.
     * @throws java.io.IOException Erro durante transmissão de I/O.
     */
    OutputStream storeFileStream(String arquivo) throws IOException;
    /**
     * Faz a transferência de um arquivo.
     * @param streamOrigem Arquivo de origem.
     * @param streamDestino Local de destino.
     * @param streamSize Tamanho do arquivo.
     * @throws java.io.IOException Problemas na leitura e escrita dos dados.
     */
    void transferFile(InputStream streamOrigem, OutputStream streamDestino, long streamSize) throws IOException;
    /**
     * Deleta um arquivo especificado pelos parãmetros.
     * @param string Nome do arquivo.
     * @return Retorna uma confirmação de exclusão.
     * @throws java.io.IOException Erro durante transmissão de I/O.
     */
    boolean deleteFile(String string) throws IOException;
    
    /**
     * Muda o diretório.
     * @param diretorioFilho Diretório que deve ser acessado.
     * @throws java.io.IOException Erro de sincronização.
     * @throws webfotos.sync.SyncException Erro de comunicação entre os dados.
     */
    void cd(String diretorioFilho) throws IOException, SyncException;
    /**
     * Cria um novo subdiretório no diretório utilizado.
     * @param pathName O nome do diretório a ser criado.
     * @throws java.io.IOException Se um erro de I/O ocorrer enquanto está sendo enviado
     * um comando ao servidor ou recebendo uma resposta do servidor.
     * @return Retorna <I>true</I> caso ocorra com sucesso.
     */
    boolean makeDirectory(String pathName) throws IOException;
    /**
     * Muda o diretório de trabalho baseado no novo passado como parâmetro.
     * @param pathName Novo diretório de trabalho.
     * @return Retorna uma confirmação.
     * @throws java.io.IOException Erro durante uma transmissão de I/O.
     */
    boolean changeWorkingDirectory(String pathName) throws IOException;
    /**
     * Retorna o nome do diretório de trabalho.
     * @return Retorna o diretório.
     * @throws java.io.IOException Erro durante transmissão de I/O.
     */
    String printWorkingDirectory() throws IOException;
    /**
     * Remove um diretório através de um ID de albúm recebido.
     * @param albumID ID do albúm.
     * @return Retorna uma confimação de exclusão do diretório.
     * @throws java.io.IOException Exceção durante transmissão de I/O.
     */
    boolean removeDirectory(String albumID) throws IOException;
    
    
    /**
     * Conecta ao FTP.
     * @return Retorna uma confirmação para a conexão.
     */
    boolean connect();
    /**
     * Desconecta do FTP e apresenta uma mensagem explicando o motivo.
     * @param msg Mensagem do motivo da desconexão.
     */
    void disconnect(String msg);
    
    /**
     * Seta um objeto CopyStreamListener.
     * TODO: esse método contem objetos específicos - retirá-lo.
     * @param copyStreamListener Objeto da classe CopyStreamListener.
     */
    void setCopyStreamListener(CopyStreamListener copyStreamListener);
    /**
     * Retorna um objeto CopyStreamListener.
     * TODO: esse método contem objetos específicos - retirá-lo.
     * @return Retorna CopyStreamListener.
     */
    CopyStreamListener getCopyStreamListener();
    
    /**
     * Seta o ouvinte syncListener.
     * @param listener Um listener de sincronização.
     */
    void setSyncListener(SyncListener listener);
    /**
     * Retorna o ouvinte syncListener.
     * @return Retorna um listener de sincronização.
     */
    SyncListener getSyncListener();
    
    /**
     * Determina qual caminho usar.
     * @param ftpRoot Parâmetro que recebe a informação.
     */
    void setSyncFolder(String ftpRoot);
    /**
     * Retorna o caminho que deve usar.
     * @return Mostra o caminho base.
     */
    String getSyncFolder();

    /**
     * Retorna o usuário.
     * @return Retorna um usuário.
     */
    String getUsuario();
    /**
     * Seta um nome de usuário.
     * @param usuario Usuário.
     */
    void setUsuario(String usuario);

    /**
     * Retorna uma senha de usuário.
     * @return Retorna uma senha.
     */
    char[] getSenha();
    /**
     * Seta uma senha de usuário.
     * @param senha Senha.
     */
    void setSenha(char[] senha);

    /**
     * Retorna uma lista de arquivos.
     * @throws java.io.IOException Erro durante transmissão de I/O.
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
     * Procure sua utilização em 
     * {@link webfotos.sync.FTP.SyncObject#loadSyncCache() loadSyncCache}().
     */
    void loadSyncCache();
    /**
     * Retorna a variável boolean enviarAltaResolução para especificar se serão
     * enviadas fotos originais ou não.
     * @return Retorna um valor lógico.
     */
    boolean isEnviarAltaResolucao();

}
