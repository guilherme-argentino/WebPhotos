package webfotos.gui.util;


import javax.swing.table.*;

import net.sf.webphotos.Album;
import net.sf.webphotos.Foto;
import webfotos.gui.PainelWebFotos;

/**
 * Gera o modelo da tabela de fotos.
 */
public class TableModelFoto extends AbstractTableModel {
	
	private static final long serialVersionUID = -3797898104363613961L;
	
	private static final TableModelFoto instancia=new TableModelFoto();
    private Object[][] fotoTabela;
    private String[] fotoColunas;
    
    private TableModelFoto(){}
    
    /**
     * Retorna a instância da própria classe.
     * @return Retorna um TableModelFoto.
     */
    public static TableModelFoto getModel() {
        return instancia;
    }
    
    /**
     * Armazena os dados de fotos em duas variáveis da classe.
     * Na variável fotoTabela, as fotos e seus dados específicos.
     * E na variável fotoColunas somente os dados específicos.
     */
    public void update() {
        fotoTabela=Album.getAlbum().getFotosArray();
        fotoColunas=Album.getAlbum().getFotosColunas();
    }
    
    /**
     * Recebe um número referente a uma coluna e retorna o valor da coluna através do vetor fotoColunas.
     * @param column Número referente a coluna.
     * @return Retorna o valor contido na coluna.
     */
    @Override
    public String getColumnName(int column) { return fotoColunas[column]; }
    
    /**
     * Retorna o total de colunas, contando o número de posições no vetor fotoColunas.
     * @return Retorna o total de colunas.
     */
    public int getColumnCount() {
        if(fotoColunas==null) return 0;
        return fotoColunas.length;
    }
    /**
     * Retorna o total de linhas, contando o número de posições no vetor fotoTabela.
     * @return Retorna o total de linhas.
     */
    public int getRowCount() {
        if(fotoTabela==null) return 0;
        return fotoTabela.length;
    }
    
    /**
     * Busca um valor contido na matriz fotoTabela e retorna um Object.
     * Recebe como parâmetro um índice de linha e um de coluna para efetuar a procura.
     * @param line Número da linha.
     * @param column Número da coluna.
     * @return Retorna o valor encontrado em um Object.
     */
    public Object getValueAt(int line, int column) {
        return fotoTabela[line][column];
    }
    
    /**
     * Recebe um valor e os índices da matriz e seta esse valor na matriz fotoTabela.
     * Checa se a foto possui ID ou nome, depois testa se o valor é de legenda ou crédito e implanta na matriz fotoTabela.
     * @param value Valor a ser implantado.
     * @param line Número da linha.
     * @param column Número da coluna.
     */
    @Override
    public void setValueAt(Object value, int line, int column) {
        // testar para verificar se fotoID é um número ou um nome de arquivo
        int fotoID=0;
        String nomeFoto="";
        try {
            fotoID=Integer.parseInt(fotoTabela[line][0].toString());
        } catch(Exception e){
            nomeFoto=fotoTabela[line][0].toString();
        }
        
        // Qual campo está editando ?
        if(column==1) {
            // usuário está editando coluna legenda
            // atualiza o modelo
            fotoTabela[line][column]=value;
            // atualiza objeto foto
            if(fotoID > 0)
                Album.getAlbum().getFoto(fotoID).setLegenda((String) value);
            else
                Album.getAlbum().getFoto(nomeFoto).setLegenda((String) value);
            // ajusta o texto da legenda
            PainelWebFotos.getTxtLegenda().setText((String) value);
        } else if(column==2) {
            // usuário está editando coluna crédito (combobox)
            fotoTabela[line][column]=value;
            if(fotoID > 0)
                Album.getAlbum().getFoto(fotoID).setCreditoNome((String) value);
            else
                Album.getAlbum().getFoto(nomeFoto).setCreditoNome((String) value);
            int indice=Foto.getLstCreditosIndex((String) value);
            // soma 1 ao indice, pois o primeiro value é espaço vazio
            PainelWebFotos.getLstCreditos().setSelectedIndex(indice + 1);
        }
    }
    
    
    /**
     * Checa se o número de colunas é maior que zero e retorna <I>true</I>, caso contrário retorna <I>false</I>.
     * TODO: avaliar a funcionalidade desse método.
     * @param line Número da linha.
     * @param column Número da coluna.
     * @return Retorna um valor lógico.
     */
    @Override
    public boolean isCellEditable(int line, int column) {
        if(column > 0) return true;
        return false;
    }
    
    /**
     * Retorna a classe do objeto encontrado na matriz fotoTabela.
     * Busca a partir do valor do número da coluna recebido como parâmetro.
     * @param column Número da coluna.
     * @return Retorna uma classe.
     */
    @Override
    public Class<? extends Object> getColumnClass(int column) { return fotoTabela[0][column].getClass(); }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton Object");
    }

}