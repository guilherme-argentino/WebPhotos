package webfotos.util;

/*
 UPLOAD=1 DOWNLOAD=2 DELETE=3;
 */

/**
 * Cria um comando de FTP.
 * Comando esse armazenado posteriormente no {@link webfotos.util.CacheFTP CacheFTP}.
 */
public class ComandoFTP implements Comparable<ComandoFTP> {
    private int operacao=-1;
    private int albumID=-1;
    private int fotoID=-1;

    /**
     * Construtor da classe.
     * Recebe os par�metros necess�rios para constru��o de um comando FTP (a��o, alb�m, foto).
     * Seta as vari�veis de dentro da classe com os valores recebidos.
     * @param acao Op��o do tipo de a��o.
     * @param album Alb�m.
     * @param foto Foto.
     */
    public ComandoFTP(int acao, int album, int foto) {
        operacao=acao; albumID=album; fotoID=foto;
    }
    
    /**
     * Recebe um Object e compara com os valores armazenados na classe.
     * Caso positivo retorna <I>true</I>, caso contr�rio <I>false</I>.
     * @param o comandoFTP a ser comparado.
     * @return Retorna uma vari�vel l�gica.
     */
    public boolean equals(Object o) {
        ComandoFTP obj;
        try {
            obj = (ComandoFTP) o;
        } catch (ClassCastException ccE) {
            return false;
        }
        if( obj.getOperacao()==operacao &&
            obj.getAlbumID()==albumID &&
            obj.getFotoID()==fotoID ) return true;
        return false;
    }
    
    /**
     * Faz um teste l�gico para retornar uma vari�vel l�gica.
     * � utilizado na classe {@link webfotos.util.CacheFTP CacheFTP} no m�todo
     * {@link webfotos.util.CacheFTP#add(Object) add(Object a)}
     * TODO: Avaliar se esse m�todo � realmente necess�rio para o c�digo.
     * @param outra Object com dados do comando para compara��o
     * @return Retorna um valor l�gico.
     */
    public boolean recebe(ComandoFTP outra) {
        // o objeto "outra" pode entrar na colecao ?
        // n�o pode de fotoID=0...
        // um objeto "apagar 100 0" � maior que um "apagar 100 20"
        // j� que o primeiro engloba o segundo
        if( outra.getOperacao()==operacao &&
            outra.getAlbumID()==albumID &&
            fotoID==0 ) return false;
        return true;
    }

    /**
     * Interface de ordena��o (sort) para agrupar as opera��es (uploads, 
     * downloads, e deletes). Confere primeiro pela opera��o, se forem
     * id�nticas, confere pelo alb�m, se forem id�nticos tamb�m checa pela foto.
     * @param outro Comando para compara��o.
     * @return Retorna um valor num�rico para compara��o.
     */
    public int compareTo(ComandoFTP c) {
        // opera��es diferentes
        if(this.getOperacao()!=c.getOperacao()) {
            return this.getOperacao() - c.getOperacao();
        }
        // opera��o igual. Verifica se album � diferente
        else if(this.getAlbumID()!=c.getAlbumID()) {
            return this.getAlbumID() - c.getAlbumID();
        }
        // opera��o igual, �lbum igual. Ordena pela foto
        else {
            return this.getFotoID() - c.getFotoID();		
        }
    }

    /**
     * Retorna o valor num�rico da opera��o.
     * @return Retorna a opera��o.
     */
    public int getOperacao() { return operacao; }
    /**
     * Retorna o valor num�rico do alb�m (ID).
     * @return Retorna o alb�m.
     */
    public int getAlbumID() { return albumID; }
    /**
     * Retorna o valor num�rico da foto (ID).
     * @return Retorna a foto.
     */
    public int getFotoID() { return fotoID; }
    /**
     * Retorna uma String contendo os dados da classe agrupados.
     * Reuni os n�meros da opera��o, do alb�m e da foto.
     * @return Retorna dados do comandoFTP agrupados.
     */
    public String toString() { return operacao + " " + albumID + " " + fotoID; }

}