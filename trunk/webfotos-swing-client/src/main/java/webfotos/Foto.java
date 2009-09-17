package webfotos;

import java.awt.Dimension;
import java.awt.MediaTracker;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;

import webfotos.util.Util;


/**
 * A classe Foto armazena dados espec�ficos de uma foto.
 * Dentre os dados est�o ID da foto,ID do alb�m e ID do cr�dito, legenda, cr�dito e resolu��o de tela.
 */
public class Foto {
    private int fotoID=0;
    private int albumID=-1;
    private int creditoID=0;
    private String legenda=null;
    private int largura=0;
    private int altura=0;
    private long tamanhoBytes=0;
    private String creditoNome="";
    private String caminhoArquivo="";
    private static javax.sql.RowSet rowSet = BancoImagem.getRSet();

    private static String[][] creditos=null;

    /**
     * Construtor da classe Foto.
     * Recebe e seta todos os dados da foto.
     * @param ID ID da foto.
     * @param albumID ID do alb�m.
     * @param fotoLegenda Legenda da foto.
     * @param fotoCreditoID ID do cr�dito.
     * @param fotoCreditoNome Cr�dito.
     * @param fotoLargura Largura da foto.
     * @param fotoAltura Altura da foto.
     */
    public Foto(int ID, int albumID, String fotoLegenda, int fotoCreditoID, String fotoCreditoNome, int fotoLargura, int fotoAltura, long tamanhoBytes) {
        this.fotoID         = ID;
        this.albumID        = albumID;
        this.legenda        = fotoLegenda;
        this.creditoID      = fotoCreditoID;
        this.creditoNome    = fotoCreditoNome;
        this.largura        = fotoLargura;
        this.altura         = fotoAltura;
        this.tamanhoBytes   = tamanhoBytes;
    }
    
    /**
     * Contrutor da classe Foto.
     * Recebe apenas nome do arquivo como par�metro.
     * Seta a legenda com o valor vazio, e seta caminhoArquivo com o nome recebido como par�metro.
     * Carrega a foto a partir do nome do arquivo, obt�m medidas da foto e seta as vari�veis de largura e altura.
     * @param arquivo Nome ou caminho do arquivo.
     */
    public Foto(String arquivo) {
        legenda="";
        caminhoArquivo=arquivo;
        // obtem medidas da foto
        ImageIcon a=new ImageIcon(arquivo);

        if(a.getImageLoadStatus()==MediaTracker.COMPLETE) {
            largura=a.getIconWidth();
            altura=a.getIconHeight();
            tamanhoBytes=new File(arquivo).length();
        } else {
            Util.log("[Foto.Foto]/ERRO: " + arquivo + " n�o pode ser lido");
        }
    }

    /**
     * Retorna o ID da foto.
     * @return Retorna um ID.
     */
    public int getFotoID() { return fotoID; }
    /**
     * Retorna o ID do cr�dito.
     * @return Retorna um ID.
     */
    public int getCreditoID() { return creditoID; }
    /**
     * Retorna o cr�dito da foto.
     * @return Retorna o cr�dito.
     */
    public String getCreditoNome() { return creditoNome; }
    /**
     * Retorna a legenda da foto.
     * @return Retorna a legenda.
     */
    public String getLegenda() { return legenda; }
    /**
     * Retorna o tamanho da largura da foto.
     * @return Retorna o valor da largura.
     */
    public int getLargura() { return largura; }
    /**
     * Retorna o tamanho da altura da foto.
     * @return Retorna o valor da altura.
     */
    public int getAltura() { return altura; }
    /**
     * Retorna uma resolu��o espec�fica com os valores de altura e largura.
     * Para entender melhor os conceitos de dimens�o veja {@link java.awt.Dimension Dimension}
     * @return Retorna uma resolu��o.
     */
    public Dimension getResolucao() { return new Dimension(largura,altura); }
    /**
     * Retorna o nome ou o caminho do arquivo.
     * @return Retorna o caminho de um arquivo
     */
    public String getCaminhoArquivo() { return caminhoArquivo; }

    /**
     * Seta o ID da foto.
     * @param f ID da foto.
     */
    public void setFotoID(int f) { fotoID=f; }
    /**
     * Seta o ID do cr�dito.
     * @param c ID do cr�dito.
     */
    public void setCreditoID(int c) { creditoID=c; }
    /**
     * Seta a legenda da foto.
     * @param l Legenda.
     */
    public void setLegenda(String l) { legenda=l; }
    /**
     * Seta o valor da largura da foto.
     * @param l Largura da foto.
     */
    public void setLargura(int l) { largura=l; }
    /**
     * Seta o valor da altura da foto.
     * @param a Altura da foto.
     */
    public void setAltura(int a) { altura=a; }
    /**
     * Seta o valor da resolu��o da foto.
     * @param r Dimens�o da foto.
     * Para entender melhor os conceitos de dimens�o veja {@link java.awt.Dimension Dimension}
     */
    public void setResolucao(Dimension r) {
        largura=r.width;
        altura=r.height;
    }
    /**
     * Seta o cr�dito da foto e completa o creditoID.
     * @param nome Cr�dito.
     */
    public void setCreditoNome(String nome) {
        creditoNome=nome;
        // pesquisa no array creditos e completa creditoID
        if(creditos != null) {
            for(int i=0; i < creditos.length; i++) {
                if(creditoNome.equals(creditos[i][1])) {
                    creditoID=Integer.parseInt(creditos[i][0]);
                    break;
                }
            }
        }
    }

    /**
     * Retorna um vetor com os valores de cr�dito.
     * Checa se o vetor j� possui valores, caso contr�rio utiliza a fun��o {@link webfotos.Foto#populaCreditos() populaCreditos()} completar os valores.
     * @return Retorna uma lista com os cr�ditos.
     */
    public static String[] getCreditosArray() {
        int tamanho;
        // executa a instrucao sql somente na primeira vez
        if(creditos == null) {
            try {
                Util.log("[Foto.getCreditosArray]/AVISO: Populando Cr�ditos");
                populaCreditos();
                tamanho = creditos.length;
            } catch (SQLException ex) {
                Util.log("[Foto.getCreditosArray]/ERRO: Imposs�vel popular cr�ditos - " + ex);
                tamanho = 0;
            }
        } else {
            tamanho = creditos.length;
        }

        String[] nomesCreditos=new String[tamanho];
        for(int i=0; i < tamanho; i++)
            nomesCreditos[i]=creditos[i][1];

        return nomesCreditos;
    }
    
    /**
     * Retorna um �ndice da matriz cr�ditos dado um nome de cr�dito.
     * @param nomeCredito Cr�dito.
     * @return Retorna um �ndice num�rico.
     */
    public static int getLstCreditosIndex(String nomeCredito) {
        for(int i=0; i < creditos.length; i++) {
            if(nomeCredito.compareTo(creditos[i][1])==0) return i;
        }

        return 0;
    }
   
    /**
     * Retorna o ID do cr�dito.
     * Faz a busca do ID na matriz credito atrav�s do nome especificado.
     * @param nomeCredito Cr�dito.
     * @return Retorna um ID num�rico.
     */
    public static int getLstCreditosID(String nomeCredito) {
        for(int i=0; i < creditos.length; i++) {
            if(nomeCredito.equals(creditos[i][1])) return Integer.parseInt(creditos[i][0]);
        }
        return 0;
    }

    /**
     * Busca no banco de dados, os valores para setar a matriz creditos
     * @throws java.sql.SQLException Lan�a exce��o caso ocorra algum erro no acesso ao banco de dados.
     */
    public static void populaCreditos() throws SQLException {
        String sql=Util.getConfig().getString("sql7");

        rowSet.setCommand(sql);
        rowSet.execute();
        rowSet.last();
        creditos=new String[rowSet.getRow()][2];
        rowSet.first();

        int ct=0;
        do {
            creditos[ct][0]=rowSet.getObject(1).toString();
            creditos[ct][1]=rowSet.getString(2);
            ct++;
        } while(rowSet.next());

    }

    /**
     * Retorna todos os valores das vari�veis de Foto em uma �nica String.
     * @return Retorna os valores da foto.
     */
    public String toString() {
        return "fotoID: " + fotoID + "\ncreditoID: " + creditoID + "\ncreditoNome: " + creditoNome + "\nlargura: " + largura + "\naltura: " + altura + "\narquivo: " + caminhoArquivo + "\nlegenda: " + legenda;
    }
    
    /**
     * Limpa o valor da vari�vel caminhoArquivo.
     * Seta com o campo vazio.
     */
    public void resetCaminhoArquivo() { caminhoArquivo=""; }
    
    /**
     * Faz a atualiza��o dos dados da foto.
     * Checa se a foto j� possui cadastro, caso n�o possui faz inclus�o e faz a atualiza��o. Caso j� possua cadastro, s� atualiza os dados.
     * @throws java.lang.Exception Lan�a qualquer tipo de exce��o que possa interromper o fluxo da fun��o.
     */
    public void atualizaFoto() throws Exception {
        int ultimoFotoID=-1;
        String sql;
        Statement st = null;
        
        // INSERT para fotos ainda n�o cadastradas
        if(caminhoArquivo.length() > 0) {
           try {
                sql="select max(fotoID) from fotos";
                rowSet.setCommand(sql);
                rowSet.execute();
                rowSet.first();
                ultimoFotoID=rowSet.getInt(1);
            } catch (Exception ex) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + ex);
            }
            setFotoID(ultimoFotoID);

            try {
                rowSet.moveToInsertRow();
                rowSet.updateInt("fotoID", ++ultimoFotoID);
                rowSet.updateInt("albumID", this.albumID);
                rowSet.updateInt("creditoID", this.creditoID);
                rowSet.updateString("legenda", this.legenda);
                rowSet.updateInt("altura", this.altura);
                rowSet.updateInt("largura", this.largura);
                rowSet.updateLong("tamanho", this.tamanhoBytes);
                
                rowSet.insertRow();
            } catch (Exception e) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + e);
                throw e;
            }

        // UPDATE para fotos j� cadastradas
        } else {
            sql="SELECT creditoID, legenda, altura, largura, tamanho FROM fotos WHERE fotoID=" + fotoID;
            try {
                rowSet.setCommand(sql);
                rowSet.execute();
                rowSet.first();
                
                fotoID = rowSet.getInt("fotoID");
                
                rowSet.updateInt("fotoID", this.fotoID);
                rowSet.updateInt("albumID", this.albumID);
                rowSet.updateInt("creditoID", this.creditoID);
                rowSet.updateString("legenda", this.legenda);
                rowSet.updateInt("altura", this.altura);
                rowSet.updateInt("largura", this.largura);
                rowSet.updateLong("tamanho", this.tamanhoBytes);
                
                rowSet.updateRow();
                
            } catch (Exception e) {
                Util.log("[Foto.atualizaFoto]/ERRO: " + e);
                throw e;
            }
        }
        
        try {
            st.close();
        } catch (Exception e) {}
    }

    /**
     * Retorna o ID do alb�m.
     * @return Retorna um ID.
     */
    public int getAlbumID() {
        return albumID;
    }

    /**
     * Seta o valor do ID do alb�m
     * @param albumID ID do alb�m.
     */
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
}
