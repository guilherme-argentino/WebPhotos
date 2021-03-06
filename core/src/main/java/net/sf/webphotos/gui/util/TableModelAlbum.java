/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.webphotos.gui.util;

// Modelo de tabela para bases de dados com suporte a cursores rolantes (MYSQL)
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import net.sf.webphotos.dao.jpa.AlbumDAO;
import net.sf.webphotos.model.AlbumVO;
import net.sf.webphotos.util.ApplicationContextResource;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

/**
 * Gera o modelo da tabela de albuns.
 */
public class TableModelAlbum extends AbstractTableModel implements TableModel {

    private static final long serialVersionUID = 8393087620197315052L;
    private static final TableModelAlbum instancia = new TableModelAlbum();
    private String ultimoSQL;

    private List<Object[]> tableData = null;
    private List<String> tableHead = null;
    private List<Class> tableHeadClazz = null;
    private static final Logger log = Logger.getLogger(TableModelAlbum.class);
    private static final AlbumDAO albunsDAO = (AlbumDAO) ApplicationContextResource.getBean("albunsDAO");

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
     * Repassa para a fun��o
     * {@link java.util.TableModelAlbum#update(String) update(String sql)}
     * enviando a variavel �ltimoSQL como parametro.
     */
    public void update() {
        update(ultimoSQL);
    }

    /**
     * Executa um update no banco. Caso ocorra algum problema, o sistema tenta
     * reconectar ao Banco de Dados. Recebe uma vari�vel para realizar um
     * comando no banco.
     *
     * @param sql Comando de sql.
     */
    public synchronized void update(String sql) {
        try {
            ultimoSQL = sql;
            log.debug("Executando - " + ultimoSQL);
            final Query createNativeQuery = albunsDAO.createNativeQuery(ultimoSQL);
            tableData = createNativeQuery.getResultList();
            
            List<AlbumVO> parsedTableData = albunsDAO.createTypedNativeQuery(ultimoSQL, "AlbumMappingSQL1Xml").getResultList();

            /**
             * WORKARROUND : I didn't find another way to get column name from
             * Native Queries
             */
            Connection cnn = albunsDAO.getSession().connection();
            Statement st = cnn.createStatement();
            ResultSet rs = st.executeQuery(ultimoSQL);
            
            tableHead = new ArrayList<>();
            tableHeadClazz = new ArrayList<>();
            
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableHead.add(rs.getMetaData().getColumnName(i));
                tableHeadClazz.add(Class.forName(rs.getMetaData().getColumnClassName(i)));
            }
        } catch (HibernateException | SQLException | ClassNotFoundException ex) {
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
        } finally {
            fireTableDataChanged();
        }
    }

    /**
     * Retorna o nome de uma coluna.
     *
     * @param col N�mero da coluna.
     * @return Retorna o nome da coluna.
     */
    @Override
    public String getColumnName(int col) {
        try {
            if (tableData == null && ultimoSQL != null) {
                update();
            }
            return tableHead.get(col);
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
            if (tableData == null && ultimoSQL != null) {
                update();
            }
            return tableHead.size();
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
            if (tableData == null && ultimoSQL != null) {
                update();
            }
            return tableData.size();
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
            if (tableData == null && ultimoSQL != null) {
                update();
            }
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
            if (tableData == null && ultimoSQL != null) {
                update();
            }
            return tableHeadClazz.get(column);
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
        update();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
