package webfotos.util;

import java.util.*;
import java.io.*;
import webfotos.util.Util;

/**
 * Mantém uma lista (arquivo) com comandos FTP.
 * <PRE>
 * Formato: acao album foto.
 * Exemplos: 1 345 2233  - enviar foto 2233 do álbum 345.
 *           1 345 0     - enviar tudo do álbum 345 (excluindo a entrada anterior).
 * </PRE>
 */
public class CacheFTP extends ArrayList<ComandoFTP> {
    /**
     * Número de opção da ação UPLOAD.
     */
    public static final int UPLOAD=1;
    /**
     * Número de opção da ação DOWNLOAD.
     */
    public static final int DOWNLOAD=2;
    /**
     * Número de opção da ação DELETE.
     */
    public static final int DELETE=3;

    private static File arquivoControle=new File("CacheFTP.txt");	
    private static final CacheFTP instancia=new CacheFTP();

    private CacheFTP() {
        super();
        Util.log("Inicializando Cache FTP");
        Util.out.println();
        loadFile(); // lê o arquivo de cache
        sort();
    }

    /**
     * Retorna a instancia de CacheFTP feita na própria classe.
     * @return Retorna um objeto CacheFTP.
     */
    public static CacheFTP getCache() {
        return instancia;
    }
    
    /**
     * Recebe uma ação, um albúm e uma foto, e adiciona um comando de FTP no arquivo.
     * @param acao Tipo de ação.
     * @param album Albúm.
     * @param foto Foto.
     */
    public void addCommand(int acao, int album, int foto) {
        if(album==0) return;
        if(acao!=UPLOAD && acao!=DOWNLOAD && acao!=DELETE) return;

        if((this.add(new ComandoFTP(acao,album,foto))==true)) {
            sort();
            Util.log("Comando FTP adicionado. (" + acao + " " + album + " " + foto + ")");
        } else {
            Util.log("Comando FTP é redundante. Não foi adicionado");
        }
    }

    private void sort() {
        Collections.<ComandoFTP>sort((List<ComandoFTP>)this);
    }

    /**
     * Valida e adiciona um comando no arquivo através de um Object recebido como parâmetro.
     * Antes de adicionar, checa se o objeto é do tipo correto, se já existe algum objeto igual
     * na coleção e se a operação é válida.
     * @param a Objeto a ser adicionado ao arquivo.
     * @return Retorna uma confirmação.
     */
    public boolean add(ComandoFTP a) {
        ComandoFTP b = a;
        
        // se já existir objecto igual na colecao sai
        if(this.contains(a)) return false;

        int acao=b.getOperacao();
        int albumID=b.getAlbumID();
        int fotoID=b.getFotoID();

        // se não for nenhuma das operações válida sai
        if( ! (acao==UPLOAD) && 
            ! (acao==DOWNLOAD) && 
            ! (acao==DELETE) ) return false;

        // percorremos a coleçao aplicando as regras
        Iterator i=this.iterator();
        ComandoFTP l;

        // se fotoID==0, então procura e remove entradas menores desse albumID
        if(fotoID==0) {
            i=this.iterator();

            while(i.hasNext()) {
                l=(ComandoFTP) i.next();

                if( (l.getOperacao()==acao && l.getAlbumID()==albumID) ||
                    (acao==DELETE && l.getOperacao()==UPLOAD && l.getAlbumID()==albumID)
                    ) i.remove();

            }				
        }

        // se estamos adicionando, somente é valido quando não exista entradas
        // de álbum inteiro (fotoID="0")
        i=this.iterator();
        while(i.hasNext()) {
            l=(ComandoFTP) i.next();
            if(! l.recebe(b)) return false;
        }
        super.add(b);
        return true;
    }

    // carrega o arquivo texto como uma coleção de Arquivo
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
     * Checa se ele existe, caso não exista cria um.
     * Se o arquivo existir, testa se esta protegido contra gravação.
     * Ao final, vai concatenado na saida as linhas com os comandos de FTP.
     */
    public void saveFile() {

        BufferedWriter saida;
        Util.out.println ("escrevendo arquivo de Cache FTP: " + arquivoControle.getAbsolutePath());

        if(!arquivoControle.isFile()) {
            // arquivo não existe..criamos
            try {
                saida=new BufferedWriter(new FileWriter(arquivoControle));
                saida.write("# arquivo de envio / exclusão ftp - não deve ser alterado manualmente\r\n");
                saida.write("# 1-Upload (envio) 2-Download (recepção) 3-Delete (apagar)\r\n");				
                saida.flush();

            } catch (IOException e) {
                Util.log("[CacheFTP.getArquivoSaida]/ERRO: não foi possível criar ou escrever no novo arquivo de controle de FTP " + e.getMessage());
                return;
            }

        } else {
            // arquivo existe.. checamos
            if(!arquivoControle.canWrite()) {
                Util.log("[CacheFTP.getArquivoSaida]/ERRO: o arquivo está protegido contra gravação");
                return;
            }

            try {
                saida=new BufferedWriter(new FileWriter(arquivoControle,false));
            } catch (Exception e) {
                Util.log("[CacheFTP.getArquivoSaida]/ERRO: não foi possível abrir o arquivo para adição.");
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
