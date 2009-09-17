/*
 * Arquivo.java
 *
 * Created on 16 de Maio de 2006, 17:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.util;

import java.io.File;
import java.util.Vector;

/**
 * Objeto que contém os dados do arquivo usados na tabela FtpClient
 * @author guilherme
 */
public class Arquivo {
    
    private String linhaComando="";
    private int acao=-1;
    private String nomeAcao="";
    private int albumID=-1;
    private int fotoID=-1;
    private String nmArquivo="";
    private long tamanho=0;
    private String status="";
    private File arqFoto;
    
    /**
     * Cria uma nova instância para o Arquivo
     */
    public Arquivo() {
    }
    
    
    /**
     * Verifica qual operação deverá ser feita com o arquivo e caso seja Upload ele enviará o arquivo para o local especificado
     * @param linha Linha de comando
     * @param operacao Tipo de ação a ser seguida
     * @param album Identificação do album
     * @param foto Identificação da foto
     * @param nomeArquivo Nome do arqvuio
     */
    public Arquivo(String linha, int operacao, int album, int foto, String nomeArquivo) {
        linhaComando=linha;
        acao=operacao;
        albumID=album;
        fotoID=foto;
        nmArquivo=nomeArquivo;
        status="??";

        if(operacao==CacheFTP.DELETE) nomeAcao="apagar";
        if(operacao==CacheFTP.DOWNLOAD) nomeAcao="receber";

        if(operacao==CacheFTP.UPLOAD) {
            nomeAcao="enviar";
            arqFoto=new File(Util.getAlbunsRoot(), albumID + File.separator +  nmArquivo);
            if(arqFoto.isFile() && arqFoto.canRead()) 
                tamanho=arqFoto.length();
            else {
                tamanho=0;
                try { this.finalize(); } catch (Throwable e) { }
            }
        } else {
            tamanho=0;
        }
    }
    
    /**
     * Verifica qual operação deverá ser acionada para o arquivo
     * @param linha Linha de comando
     * @param operacao Tipo de ação a ser seguida
     * @param album Identificação do album
     * @param foto Identificação da foto
     * @param nomeArquivo Nome do arqvuio
     * @param tam Tamanho do arquivo do arqvuio
     */
    public Arquivo(String linha, int operacao, int album, int foto, String nomeArquivo, long tam ) {
        linhaComando=linha;
        acao=operacao;
        albumID=album;
        fotoID=foto;
        nmArquivo=nomeArquivo;
        status="??";
        switch(operacao) {
            case CacheFTP.DELETE: nomeAcao="apagar"; break;
            case CacheFTP.DOWNLOAD: nomeAcao="receber"; break;
            case CacheFTP.UPLOAD: nomeAcao="enviar"; break;
        }
        tamanho=tam;
    }
    
    /**
     * Limpa os dados passados no Vector
     * @param _data dados da foto
     */
    public Arquivo(Vector _data) {
        Vector data = (Vector)_data.clone();
        status=data.remove(0).toString();
        nomeAcao=data.remove(0).toString();
        albumID=Integer.parseInt(data.remove(0).toString());
        fotoID=Integer.parseInt(data.remove(0).toString());
        nmArquivo=data.remove(0).toString();
        tamanho=Integer.parseInt(data.remove(0).toString());
        data = null;
    }
    
    /**
     * Retorna a variável status
     * @return status
     */
    public String getStatus() { return status; }
    /**
     * Retorna variável linhaComando
     * @return Linha de comando
     */
    public String getLinhaComando() { return linhaComando; }
    /**
     * Passa a variável valor para a variável status
     * @param valor valor usado
     */
    public void setStatus(String valor) { status=valor; }
    /**
     * Retorna a variável acao(indica qual operação deverá ser acionada)
     * @return retorna acao
     */
    public int getAcao() { return acao; }
    /**
     * Retorna variável  acao(indica qual operação deverá ser acionada)
     * @return retorna nome da ação
     */
    public String getNomeAcao() { return nomeAcao; }
    /**
     * Retorna variável albumID(identificação do album)
     * @return retorna id ao album
     */
    public int getAlbumID() { return albumID; }
    /**
     * Retorna variável fotoID
     * @return retorna id da foto
     */
    public int getFotoID() { return fotoID; }
    /**
     * Retorna variável nmArquivo(Nome do arquivo)
     * @return retorna nome do arquivo
     */
    public String getNomeArquivo() { return nmArquivo; }
    /**
     * Retorna variável tamanho(tamanho do arquivo no qual será utilizado)
     * @return retorna tamanho do arquivo
     */
    public long getTamanho() { return tamanho; }
    /**
     * Passa a variável valor para a variável tamanho
     * @param valor valor a ser usado
     */
    public void setTamanho(long valor) { tamanho = valor; }
    /**
     * Concatena e retorna as variáveis acao,albumID,fotoID e tamanho
     * @return Retorna as variáveis acao,albumID,fotoID e tamanho
     */
    public String toString() { return status + " " + acao + " " + albumID + " " + fotoID + " " + tamanho; }
}
