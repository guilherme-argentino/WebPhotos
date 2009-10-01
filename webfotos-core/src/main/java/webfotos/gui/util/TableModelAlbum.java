package webfotos.gui.util;

// Modelo de tabela para bases de dados com suporte a cursores rolantes (MYSQL)
import com.sun.rowset.JdbcRowSetImpl;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;

import net.sf.webphotos.BancoImagem;

import javax.swing.table.AbstractTableModel;

//Implementando RowSet
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import org.apache.log4j.Logger;

/**
 * Gera o modelo da tabela de albuns.
 */
public class TableModelAlbum extends AbstractTableModel implements RowSetListener {

	private static final long serialVersionUID = 8393087620197315052L;

	private static final TableModelAlbum instancia = new TableModelAlbum();
    private String ultimoSQL;
    private RowSet rowSet = null;
    private static Logger log = Logger.getLogger(TableModelAlbum.class);

    // construtor
    private TableModelAlbum() {
        super();
    }

    /**
     * Retorna a instï¿½ncia da prï¿½pia classe.
     * @return Retorna um TableModelAlbum.
     */
    public static TableModelAlbum getModel() {
        return instancia;
    }

    /**
     * Repassa para a função {@link java.util.TableModelAlbum#update(String) update(String sql)} enviando a variï¿½vel últimoSQL como parï¿½metro.
     */
    public void update() {
        update(getUltimoSQL());
    }

    /**
     * Executa um update no banco. Caso ocorra algum problema, o sistema tenta
     * reconectar ao Banco de Dados.
     * Recebe uma variï¿½vel para realizar um comando no banco.
     * Executa a função atravï¿½s da biblioteca {@link java.sql.RowSet RowSet}.
     * Ao tï¿½rmino armazena a descrição de {@link java.sql.ResultSet#getMetaData() MetaData} em uma variï¿½vel.
     * @param sql Comando de sql.
     */
    public void update(String sql) {
        try {
            ultimoSQL = sql;
            rowSet.setCommand(ultimoSQL);
            log.debug("Executando - " + ultimoSQL);
            rowSet.execute();
        } catch (java.sql.SQLException sqlE) {
            log.error("Ocorreu um erro durante a leitura do ï¿½lbum no banco de dados", sqlE);
            int selecao = JOptionPane.showConfirmDialog(null,
                    "ERRO durante leitura do ï¿½lbum no banco de dados.\n\nTentar Novamente?",
                    "Aviso!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (selecao == JOptionPane.YES_OPTION) {
                /** TODO: extrair para um método */
                try {
                    BancoImagem.setRSet(new JdbcRowSetImpl(BancoImagem.getConnection()));
                    this.rowSet = BancoImagem.getRSet();
                } catch (java.sql.SQLException sqlEi) {
                    log.warn("Error during Database Reload", sqlEi);
                } catch (Exception e) {
                    log.error("Inexperado", e);
                } finally {
                    update(sql);
                }
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            log.error("Ocorreu um erro inexperado durante a leitura do ï¿½lbum", e);
            JOptionPane.showMessageDialog(null, "ERRO inexperado durante leitura do ï¿½lbum - " + e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Ocorreu um erro inexperado durante a leitura do ï¿½lbum", e);
        }
    }

    /**
     * Retorna o nome de uma coluna.
     * Faz a busca atravï¿½s de um nï¿½mero passado como parï¿½metro.
     * Busca a informação atravï¿½s do método {@link java.sql.ResultSet#getMetaData() getMetaData()}.
     * @param col Nï¿½mero da coluna.
     * @return Retorna o nome da coluna.
     */
    @Override
    public String getColumnName(int col) {
        try {
            ResultSetMetaData meta = rowSet.getMetaData();

            if (meta == null) {
                return null;
            }
            return meta.getColumnName(col + 1);
        } catch (SQLException e) {
            log.error("Error trying to get column name", e);
            return "Error";
        }
    }

    /**
     * Retorna o nï¿½mero de colunas.
     * Busca a informação atravï¿½s do método {@link java.sql.ResultSet#getMetaData() getMetaData()}.
     * @return Retorna o nï¿½mero de colunas.
     */
    public int getColumnCount() {
        try {
            ResultSetMetaData meta = rowSet.getMetaData();

            if (meta == null) {
                return 0;
            }
            return meta.getColumnCount();
        } catch (SQLException e) {
            log.error("Error trying to get column count", e);
            return 0;
        }
    }

    /**
     * Retorna o nï¿½mero de linhas.
     * Busca a informação atravï¿½s do método {@link java.sql.ResultSet#getRow() getRow()}.
     * @return Retorna o nï¿½mero de linhas.
     */
    public int getRowCount() {
        try {
            if (rowSet.last()) {
                return (rowSet.getRow());
            } else {
                return 0;
            }
        } catch (SQLException e) {
            log.error("Error trying to get row count", e);
            return 0;
        }
    }

    /**
     * Obtï¿½m o valor na tabela.
     * Faz a procura atravï¿½s da linha e coluna.
     * @param row Nï¿½mero da linha.
     * @param col Nï¿½mero da coluna.
     * @return Retorna o valor na tabela.
     */
    public Object getValueAt(int row, int col) {
        try {
            if (!rowSet.absolute(row + 1)) {
                return null;
            }
            return rowSet.getObject(col + 1);
        } catch (SQLException e) {
            log.error("Error trying to get value at (" + row + "," + col + ")", e);
            return null;
        }
    }

    /**
     * Retorna o valor false.
     * Recebe os valores numï¿½ricos da linha e coluna, porï¿½m Não os utiliza.
     * TODO: avaliar a funcionalidade desse método.
     * @param l Nï¿½mero da linha.
     * @param c Nï¿½mero da coluna.
     * @return Retorna <I>false</I>.
     */
    @Override
    public boolean isCellEditable(int l, int c) {
        log.debug("Coordinates(" + l + "," + c + ")");
        return false;
    }

    /**
     * Busca qual o tipo de uma coluna especï¿½fica e retorna sua classe.
     * Recebe um valor numï¿½rico para indicar a coluna e busca os dados atravï¿½s do método {@link java.sql.ResultSet#getMetaData() getMetaData()}.
     * @param column Nï¿½mero da coluna.
     * @return Retorna uma classe.
     */
    @Override
    public Class<?> getColumnClass(int column) {
        String cname;
        int type;

        try {
            ResultSetMetaData meta = rowSet.getMetaData();

            if (meta == null) {
                return null;
            }
            type = meta.getColumnType(column + 1);
        } catch (SQLException e) {
            log.warn("Error getting column class, returning SuperType information", e);
            return super.getColumnClass(column);
        }
        
        switch (type) {
            case Types.BIT: {
                cname = "java.lang.Boolean";
                break;
            }
            case Types.TINYINT: {
                cname = "java.lang.Byte";
                break;
            }
            case Types.SMALLINT: {
                cname = "java.lang.Short";
                break;
            }
            case Types.INTEGER: {
                cname = "java.lang.Integer";
                break;
            }
            case Types.BIGINT: {
                cname = "java.lang.Long";
                break;
            }
            case Types.FLOAT:
            case Types.REAL: {
                cname = "java.lang.Float";
                break;
            }
            case Types.DOUBLE: {
                cname = "java.lang.Double";
                break;
            }
            case Types.NUMERIC: {
                cname = "java.lang.Number";
                break;
            }
            case Types.DECIMAL: {
                cname = "java.math.BigDecimal";
                break;
            }
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR: {
                cname = "java.lang.String";
                break;
            }
            case Types.DATE: {
                cname = "java.sql.Date";
                break;
            }
            case Types.TIME: {
                cname = "java.sql.Time";
                break;
            }
            case Types.TIMESTAMP: {
                cname = "java.sql.Timestamp";
                break;
            }
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY: {
                cname = "byte[]";
                break;
            }
            case Types.OTHER:
            case Types.JAVA_OBJECT: {
                cname = "java.lang.Object";
                break;
            }
            case Types.CLOB: {
                cname = "java.sql.Clob";
                break;
            }
            case Types.BLOB: {
                cname = "java.ssql.Blob";
                break;
            }
            case Types.REF: {
                cname = "java.sql.Ref";
                break;
            }
            case Types.STRUCT: {
                cname = "java.sql.Struct";
                break;
            }
            default: {
                return super.getColumnClass(column);
            }
        }
        try {
            return Class.forName(cname);
        } catch (Exception e) {
            log.warn("Error getting column class, returning SuperType information", e);
            return super.getColumnClass(column);
        }
    }

    /**
     * Retorna o valor de ultimoSQL.
     * @return Retorna um comando SQL.
     */
    public String getUltimoSQL() {
        return ultimoSQL;
    }

    /**
     * Seta o valor de ultimoSQL.
     * @param ultimoSQL Comando de SQL.
     */
    public void setUltimoSQL(String ultimoSQL) {
        this.ultimoSQL = ultimoSQL;
    }

    /**
     * Notifica aos listenners que a estrutura da tabela foi modificada.
     * Apenas chama o método {@link javax.swing.table.AbstractTableModel#fireTableStructureChanged() fireTableStructureChanged()}.
     * @param event Evento de ação na tabela.
     */
    public void rowSetChanged(RowSetEvent event) {
        fireTableStructureChanged();
    }

    /**
     * Notifica que a estrutura da talela foi modificada, porï¿½m informa qual a função foi feita (insert, delete ou update).
     * @param event Evento de ação na tabela.
     */
    public void rowChanged(RowSetEvent event) {
        try {
            int row = rowSet.getRow();

            if (rowSet.rowDeleted()) {
                fireTableRowsDeleted(row, row);
            } else if (rowSet.rowInserted()) {
                fireTableRowsInserted(row, row);
            } else if (rowSet.rowUpdated()) {
                fireTableRowsUpdated(row, row);
            }
        } catch (Exception ex) {
            log.warn("Error detecting changes on Row! Event : [" + event.toString() + "]", ex);
        }
    }

    /**
     * Não possui corpo.
     * TODO: avaliar a exclusão dessa função.
     * @param event Evento.
     */
    public void cursorMoved(RowSetEvent event) {
    }

    /**
     * Retorna o objeto rowSet instanciado na classe.
     * @return Retorna um objeto de RowSet.
     */
    public RowSet getRowSet() {
        return rowSet;
    }

    /**
     * Seta novos valores para o objeto rowSet instanciado na classe.
     * @param rSet Objeto de RowSet.
     */
    public void setRowSet(RowSet rSet) {
        try {
            rowSet = new JdbcRowSetImpl(BancoImagem.getConnection());
            rowSet.setUrl(rSet.getUrl());
            rowSet.setUsername(rSet.getUsername());
            rowSet.setPassword(rSet.getPassword());
            rowSet.addRowSetListener(this);
            update();
        } catch (SQLException ex) {
            log.error("Error setting RowSet", ex);
        }
    }
}
