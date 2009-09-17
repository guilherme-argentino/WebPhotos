package webfotos.util;

import java.util.*;
import java.io.*;
import webfotos.util.Util;

/**
 * Mant�m uma lista (arquivo) com comandos FTP.
 * <PRE>
 * Formato: acao album foto.
 * Exemplos: 1 345 2233  - enviar foto 2233 do �lbum 345.
 *           1 345 0     - enviar tudo do �lbum 345 (excluindo a entrada anterior).
 * </PRE>
 */
public class CacheFTP extends ArrayList<ComandoFTP> {
    /**
     * N�mero de op��o da a��o UPLOAD.
     */
    public static final int UPLOAD=1;
    /**
     * N�mero de op��o da a��o DOWNLOAD.
     */
    public static final int DOWNLOAD=2;
    /**
     * N�mero de op��o da a��o DELETE.
     */
    public static final int DELETE=3;

    private static File arquivoControle=new File("CacheFTP.txt");	
    private static final CacheFTP instancia=new CacheFTP();

    private CacheFTP() {
        super();
        Util.log("Inicializando Cache FTP");
        Util.out.println();
        loadFile(); // l� o arquivo de cache
        sort();
    }

    /**
     * Retorna a instancia de CacheFTP feita na pr�pria classe.
     * @return Retorna um objeto CacheFTP.
     */
    public static CacheFTP getCache() {
        return instancia;
    }
    
    /**
     * Recebe uma a��o, um alb�m e uma foto, e adiciona um comando de FTP no arquivo.
     * @param acao Tipo de a��o.
     * @param album Alb�m.
     * @param foto Foto.
     */
    public void addCommand(int acao, int album, int foto) {
        if(album==0) return;
        if(acao!=UPLOAD && acao!=DOWNLOAD && acao!=DELETE) return;

        if((this.add(new ComandoFTP(acao,album,foto))==true)) {
            sort();
            Util.log("Comando FTP adicionado. (" + acao + " " + album + " " + foto + ")");
        } else {
            Util.log("Comando FTP � redundante. N�o foi adicionado");
        }
    }

    private void sort() {
        Collections.<ComandoFTP>sort((List<ComandoFTP>)this);
    }

    /**
     * Valida e adiciona um comando no arquivo atrav�s de um Object recebido como par�metro.
     * Antes de adicionar, checa se o objeto � do tipo correto, se j� existe algum objeto igual
     * na cole��o e se a opera��o � v�lida.
     * @param a Objeto a ser adicionado ao arquivo.
     * @return Retorna uma confirma��o.
     */
    public boolean add(ComandoFTP a) {
        ComandoFTP b = a;
        
        // se j� existir objecto igual na colecao sai
        if(this.contains(a)) return false;

        int acao=b.getOperacao();
        int albumID=b.getAlbumID();
        int fotoID=b.getFotoID();

        // se n�o for nenhuma das opera��es v�lida sai
        if( ! (acao==UPLOAD) && 
            ! (acao==DOWNLOAD) && 
            ! (acao==DELETE) ) return false;

        // percorremos a cole�ao aplicando as regras
        Iterator i=this.iterator();
        ComandoFTP l;

        // se fotoID==0, ent�o procura e remove entradas menores desse albumID
        if(fotoID==0) {
            i=this.iterator();

            while(i.hasNext()) {
                l=(ComandoFTP) i.next();

                if( (l.getOperacao()==acao && l.getAlbumID()==albumID) ||
                    (acao==DELETE && l.getOperacao()==UPLOAD && l.getAlbumID()==albumID)
                    ) i.remove();

            }				
        }

        // se estamos adicionando, somente � valido quando n�o exista entradas
        // de �lbum inteiro (fotoID="0")
        i=this.iterator();
        while(i.hasNext()) {
            l=(ComandoFTP) i.next();
            if(! l.recebe(b)) return false;
        }
        super.add(b);
        return true;
    }

    // carrega o arquivo texto como uma cole��o de Arquivo
    private void loadFile() {
        String linha;

        // carregamos cada linha do arquivo CacheFTP.txt para dentro da col modelo
        if(arquivoControle.isFile() && arquivoControle.canRead()) {
            try {
                Util.out.println ("load file: " + arquivoControle.getCanonicalPath());
                StringTokenizer tok;
                BufferedReader entrada=new BufferedReader(new FileReader(arquivoControle));	
                while((linha=entrada.readLine()) != null) {
                    if(!linha.startsWith("#")) {
                        Util.out.println ("processando linha:" + linha);
                        tok=new StringTokenizer(linha);										
                        if(tok.countTokens()==3)
                            add(new ComandoFTP(
                                Integer.parseInt(tok.nextToken()),
                                Integer.parseInt(tok.nextToken()),
                                Integer.parseInt(tok.nextToken())
                                ));
                        tok=null;
                    }
                } // fim while
                entrada.close(); entrada=null;
            } catch (IOException e) {
                Util.log("[CacheFTP.loadFile]/ERRO:" + e.getMessage());
            }
        }		
    }

    /**
     * Grava o arquivo.
     * Checa se ele existe, caso n�o exista cria um.
     * Se o arquivo existir, testa se esta protegido contra grava��o.
     * Ao final, vai concatenado na saida as linhas com os comandos de FTP.
     */
    public void saveFile() {

        BufferedWriter saida;
        Util.out.println ("escrevendo arquivo de Cache FTP: " + arquivoControle.getAbsolutePath());

        if(!arquivoControle.isFile()) {
            // arquivo n�o existe..criamos
            try {
                saida=new BufferedWriter(new FileWriter(arquivoControle));
                saida.write("# arquivo de envio / exclus�o ftp - n�o deve ser alterado manualmente\r\n");
                saida.write("# 1-Upload (envio) 2-Download (recep��o) 3-Delete (apagar)\r\n");				
                saida.flush();

            } catch (IOException e) {
                Util.log("[CacheFTP.getArquivoSaida]/ERRO: n�o foi poss�vel criar ou escrever no novo arquivo de controle de FTP " + e.getMessage());
                return;
            }

        } else {
            // arquivo existe.. checamos
            if(!arquivoControle.canWrite()) {
                Util.log("[CacheFTP.getArquivoSaida]/ERRO: o arquivo est� protegido contra grava��o");
                return;
            }

            try {
                saida=new BufferedWriter(new FileWriter(arquivoControle,false));
            } catch (Exception e) {
                Util.log("[CacheFTP.getArquivoSaida]/ERRO: n�o foi poss�vel abrir o arquivo para adi��o.");
                return;
            }
        }

        // Arquivo aberto...
        Iterator i=this.iterator();
        ComandoFTP l;

        while(i.hasNext()) {
            l=(ComandoFTP) i.next();
            try {
                saida.write(l.getOperacao() + " " + l.getAlbumID() + " " + l.getFotoID() + "\r\n");	
            } catch (Exception e) {
                Util.log("[CacheFTP.saveFile.1]/ERRO: " + e.getMessage());
            }			
        }
        try {
            saida.flush();
            saida.close();
        } catch (Exception e) {
            Util.log("[CacheFTP.saveFile.2]/ERRO: " + e.getMessage());
        }
        saida=null; l=null; i=null;
    }

    /**
     * Retorna uma String que armazena todos os comandos FTP do arquivo no formato de uma lista.
     * @return Retorna os comandos FTP.
     */
    public String toString() {
        Iterator i=this.iterator();
        ComandoFTP l;
        String t=this.size() + " comando(s)\n-------------------------------------\n";		

        while(i.hasNext()) {
            l=(ComandoFTP) i.next();
            t+=l.getOperacao() + " " + l.getAlbumID() + " " + l.getFotoID() + "\n";			
        }
        return t + "-------------------------------------\n";
    }

} // fim da classe ControleFTP
