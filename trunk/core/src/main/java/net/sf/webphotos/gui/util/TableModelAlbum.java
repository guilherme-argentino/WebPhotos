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
package net.sf.webphotos.gui.util;

// Modelo de tabela para bases de dados com suporte a cursores rolantes (MYSQL)
import java.util.List;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import net.sf.webphotos.dao.jpa.AlbumDAO;
import net.sf.webphotos.util.ApplicationContextResource;
import org.apache.log4j.Logger;

/**
 * Gera o modelo da tabela de albuns.
 */
public class TableModelAlbum extends AbstractTableModel implements RowSetListener {

    private static final long serialVersionUID = 8393087620197315052L;
    private static final TableModelAlbum instancia = new TableModelAlbum();
    private String ultimoSQL;
    
    private List<Object[]> tableData = null;
    private static Logger log = Logger.getLogger(TableModelAlbum.class);
    private static AlbumDAO albunsDAO = (AlbumDAO) ApplicationContextResource.getBean("albunsDAO");

    // construtor
    private TableModelAlbum() {
        super();
    }

    /**
     * Retorna a inst�ncia da pr�pria classe.
     *
     * @return Retorna um TableModelAlbum.
     */
    public static TableModelAlbum getModel() {
        return instancia;
    }

    /**
     * Repassa para a fun��o {@link java.util.TableModelAlbum#update(String) update(String sql)}
     * enviando a variavel �ltimoSQL como parametro.
     */
    public void update() {
        update(getUltimoSQL());
    }

    /**
     * Executa um update no banco. Caso ocorra algum problema, o sistema tenta
     * reconectar ao Banco de Dados. Recebe uma vari�vel para realizar um
     * comando no banco.
     *
     * @param sql Comando de sql.
     */
    public void update(String sql) {
        try {
            ultimoSQL = sql;
            log.debug("Executando - " + ultimoSQL);
            tableData = albunsDAO.findByNativeQuery(ultimoSQL);
        } catch (Exception ex) {
            log.error("Ocorreu um erro durante a leitura do �lbum no banco de dados", ex);
            int selecao = JOptionPane.showConfirmDialog(null,
                    "ERRO durante leitura do �lbum no banco de dados.\n\nTentar Novamente?",
                    "Aviso!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (selecao == JOptionPane.YES_OPTION) {
                update(sql);
            } else {
                log.error("Ocorreu um erro inexperado durante a leitura do �lbum", ex);
                JOptionPane.showMessageDialog(null, "ERRO inexperado durante leitura do �lbum - " + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Ocorreu um erro inexperado durante a leitura do �lbum", ex);
            }
        }
    }

    /**
     * Retorna o nome de uma coluna.
     * @param col N�mero da coluna.
     * @return Retorna o nome da coluna.
     */
    @Override
    public String getColumnName(int col) {
        try {
            return albunsDAO.createNativeQuery(ultimoSQL).getParameter(col).getName();
        } catch (Exception e) {
            log.error("Error trying to get column name", e);
            return "Error";
        }
    }

    /**
     * Retorna o n�mero de colunas.
     *
     * @return Retorna o numero de colunas.
     */
    @Override
    public int getColumnCount() {
        try {
            return albunsDAO.createNativeQuery(ultimoSQL).getParameters().size();
        } catch (Exception e) {
            log.error("Error trying to get column count", e);
            return 0;
        }
    }

    /**
     * Retorna o n�mero de linhas.
     *
     * @return Retorna o n�mero de linhas.
     */
    @Override
    public int getRowCount() {
        try {
            return albunsDAO.createNativeQuery(ultimoSQL).getResultList().size();
        } catch (Exception e) {
            log.error("Error trying to get row count", e);
            return 0;
        }
    }

    /**
     * Obt�m o valor na tabela. Faz a procura atrav�s da linha e coluna.
     *
     * @param row N�mero da linha.
     * @param col N�mero da coluna.
     * @return Retorna o valor na tabela.
     */
    @Override
    public Object getValueAt(int row, int col) {
        try {
            return tableData.get(row)[col];
        } catch (Exception e) {
            log.error("Error trying to get value at (" + row + "," + col + ")", e);
            return null;
        }
    }

    /**
     * Retorna o valor false. Recebe os valores num�ricos da linha e coluna,
     * por�m N�o os utiliza. TODO: avaliar a funcionalidade desse m�todo.
     *
     * @param l N�mero da linha.
     * @param c N�mero da coluna.
     * @return Retorna <I>false</I>.
     */
    @Override
    public boolean isCellEditable(int l, int c) {
        log.debug("Coordinates(" + l + "," + c + ")");
        return false;
    }

    /**
     * Busca qual o tipo de uma coluna especifica e retorna sua classe. Recebe
     * um valor numerico para indicar a coluna e busca os dados atraves do
     * m�todo {@link java.sql.ResultSet#getMetaData() getMetaData()}.
     *
     * @param column Numero da coluna.
     * @return Retorna uma classe.
     */
    @Override
    public Class<?> getColumnClass(int column) {
        try {
            return albunsDAO.createNativeQuery(ultimoSQL).getParameter(column).getParameterType();
        } catch (Exception e) {
            log.warn("Error getting column class, returning SuperType information", e);
            return super.getColumnClass(column);
        }
    }

    /**
     * Retorna o valor de ultimoSQL.
     *
     * @return Retorna um comando SQL.
     */
    public String getUltimoSQL() {
        return ultimoSQL;
    }

    /**
     * Seta o valor de ultimoSQL.
     *
     * @param ultimoSQL Comando de SQL.
     */
    public void setUltimoSQL(String ultimoSQL) {
        this.ultimoSQL = ultimoSQL;
    }

    /**
     * Notifica aos listenners que a estrutura da tabela foi modificada. Apenas
     * chama o m�todo {@link javax.swing.table.AbstractTableModel#fireTableStructureChanged() fireTableStructureChanged()}.
     *
     * @param event Evento de a��o na tabela.
     */
    @Override
    public void rowSetChanged(RowSetEvent event) {
        fireTableStructureChanged();
    }

    /**
     * Notifica que a estrutura da tabela foi modificada, por�m informa qual a
     * fun��o foi feita (insert, delete ou update).
     *
     * @param event Evento de a��o na tabela.
     */
    @Override
    public void rowChanged(RowSetEvent event) {
        fireTableDataChanged();
    }

    /**
     * N�o possui corpo. TODO: avaliar a exclus�o dessa fun��o.
     *
     * @param event Evento.
     */
    @Override
    public void cursorMoved(RowSetEvent event) {
    }
}
