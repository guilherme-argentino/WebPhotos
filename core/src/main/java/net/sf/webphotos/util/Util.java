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
package net.sf.webphotos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * Esta classe armazena alguns métodos de utilidade para o funcionamento de todo
 * o programa.
 * <PRE>
 * Exemplo: PrintStream out que desvia a saída padrão de texto.
 * </PRE>
 */
@SuppressWarnings("StaticNonFinalUsedInInitialization")
public class Util {

    private static final String WEBPHOTOS_USER_CONFIG = "webfotos.xml";
    public static final String WEBPHOTOS_DEFAULT_CONFIG = "webphotos.dat";
    private static Util instancia = new Util();
    /**
     * caixa de texto para dar saída ao log
     */
    private static JTextArea saida;
    private static File albunsRoot = null;
    private static CombinedConfiguration config;
    /**
     * Para desviar a saída padrão de texto (em produção).
     */
    public static PrintStream out;
    /**
     * Para desviar a saída padrão de texto (em produção).
     */
    public static PrintStream err;
    private static final Logger log;

    static {
        out = System.out;
        err = System.err;
        log = Logger.getLogger(Util.class);

        DefaultConfigurationBuilder configurationBuilder = new DefaultConfigurationBuilder();
        String userHome = "";
        try {
            configurationBuilder.setFileName("Configuration.xml");
            config = configurationBuilder.getConfiguration(true);
            userHome = config.getString("user.home");
        } catch (Exception e) {
            log.error("Can't load preferences");
            log.debug("Stack Trace : ", e);
            System.exit(-1);
        }
        configurationBuilder = new DefaultConfigurationBuilder();
        try {
            configurationBuilder.setFileName("SavedConfiguration.xml");
            final CombinedConfiguration configuration = configurationBuilder.getConfiguration(true);
            XMLConfiguration savedUserPrefs = new XMLConfiguration();
            savedUserPrefs.append(configuration);
            savedUserPrefs.setEncoding("ISO-8859-1");
            savedUserPrefs.save(new FileOutputStream(userHome + File.separatorChar + WEBPHOTOS_USER_CONFIG));
        } catch (Exception e) {
            log.warn("Can't save preferences");
            log.debug("Stack Trace : ", e);
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    private Util() {
    }

    /**
     * Retorna a instância da própria classe.
     *
     * @return Retorna a instância de Util.
     */
    public static Util getInstance() {
        return instancia;
    }

    /**
     * Retorna o diretório raiz de albuns. Checa se a variável albunsRoot já
     * possui o valor, caso não, busca o arquivo nas propriedades através do
     * método
     * {@link net.sf.webphotos.util.Util#getProperty(String) getProperty}(String
     * chave) e faz um teste para checar se é um diretório mesmo. Caso tudo
     * esteja correto, retorna o diretório.
     *
     * @return Retorna um diretório.
     */
    public static File getAlbunsRoot() {
        if (albunsRoot == null) {
            albunsRoot = new File(getProperty("albunsRoot"));
            if (!albunsRoot.isDirectory()) {
                StringBuilder errMsg = new StringBuilder();
                errMsg.append("O diretório fornecido no parâmetro albunsRoot (arquivo de configuração)\n");
                errMsg.append("não pode ser utilizado, ou não existe.\n");
                errMsg.append("O programa será encerrado.");
                JOptionPane.showMessageDialog(null, errMsg.toString(), "Erro no arquivo de configuração", JOptionPane.ERROR_MESSAGE);
                //throw new RuntimeException(errMsg.toString());
                System.exit(-1);
            }
        }
        return albunsRoot;
    }

    /**
     * Retorna o diretório raiz de albuns. Checa se a variável albunsRoot já
     * possui o valor, caso não, busca o arquivo nas propriedades através do
     * método
     * {@link net.sf.webphotos.util.Util#getProperty(String) getProperty}(String
     * chave) e faz um teste para checar se é um diretório mesmo. Caso tudo
     * esteja correto, retorna o diretório.
     *
     * @return Retorna um diretório.
     */
    public static File getFolder(String param) {
        File folder = new File(getProperty(param));
        if (!folder.isDirectory()) {
            StringBuilder errMsg = new StringBuilder();
            errMsg.append("O diretório fornecido no parâmetro albunsRoot (arquivo de configuração)\n");
            errMsg.append("não pode ser utilizado, ou não existe.\n");
            errMsg.append("O programa será encerrado.");
            JOptionPane.showMessageDialog(null, errMsg.toString(), "Erro no arquivo de configuração", JOptionPane.ERROR_MESSAGE);
            //throw new RuntimeException(errMsg.toString());
            System.exit(-1);
        }
        return folder;
    }

    /**
     * Trabalha o texto recebido para impressão do log. Se existir algum erro
     * contido no texto, separa o erro e imprime separado do resto da saída.
     * TODO: enviar o log para arquivo e um componente swing. Eliminar esse
     * método com o Log4J ou semelhante.
     *
     * @param texto Texto para impressão.
     */
    public static void log(String texto) {
        if (texto == null) {
            saida = null;
        }
        if (saida == null) {
            log.info("LOG: " + texto);
        } else {
            if (texto.startsWith("[")) {
                Util.err.println(texto);
                texto = texto.substring(texto.indexOf("/") + 1);
            }
            saida.append(texto);
            saida.append("\n");
            saida.setCaretPosition(saida.getText().length() - 1);
        }
    }

    /**
     * Retorna uma String que substitui alguns caracteres especiais em Java
     * pelos do formato HTM.
     *
     * @param valor Texto a ser formatado.
     * @return Retorna texto formatado em HTM.
     */
    public static String stringToHtm(String valor) {
        valor = valor.replaceAll("\n", "<br>");
        valor = valor.replaceAll("\"", "&quot;");
        valor = valor.replaceAll("\'", "&quot;");
        return "\'" + valor + "\'";
    }

    /**
     * Recebe um textarea e seta esse valor na variável saida.
     *
     * @param saidaGUI textarea para indicar a saída.
     */
    public static void setLoggingTextArea(JTextArea saidaGUI) {
        saida = saidaGUI;
    }

    /**
     * Retorna uma String contendo a propriedade. Testa se é necessário carregar
     * o arquivo de propriedades, então busca a propriedade no arquivo através
     * da variável passada como parâmetro.
     *
     * @param chave Propriedade.
     * @return Retorna o valor da propriedade.
     */
    public static String getProperty(String chave) {
        try {
            return (config.getString(chave) == null || config.getString(chave).equals("")) ? null : config.getString(chave);
        } catch (Exception e) {
            log.error("Error trying to get a property", e);
            return null;
        }
    }

    /**
     * Ajusta a largura das colunas do modelo.
     *
     * @param tabela Tabela que deseja ajustar as colunas.
     * @param parametros Tamanhos das colunas separadas por vírgula.
     */
    public static void ajustaLargura(JTable tabela, String parametros) {
        int temR = -1;
        TableColumnModel modeloColunas = tabela.getColumnModel();
        if (parametros == null) {
            return;
        }
        if (parametros.length() > 0) {
            StringTokenizer tok = new StringTokenizer(parametros, ",");
            int ct = 0;
            String l;
            while (tok.hasMoreTokens()) {
                l = tok.nextToken();
                try {
                    modeloColunas.getColumn(ct).setPreferredWidth(Integer.parseInt(l));
                } catch (NumberFormatException nE) {
                    if (l.equals("*")) {
                        log.info("Packing column " + ct);
                        packColumn(tabela, ct, 1);
                    } else if (l.equals("R")) {
                        temR = ct;
                    }
                } catch (Exception e) {
                }
                ct++;
            }

            if (temR > 0) {
                modeloColunas.getColumn(temR).setPreferredWidth(modeloColunas.getColumn(temR).getPreferredWidth() + tabela.getWidth() - modeloColunas.getTotalColumnWidth());
                log.debug("Tamanho da tabela: " + (modeloColunas.getColumn(temR).getPreferredWidth() + tabela.getWidth() - modeloColunas.getTotalColumnWidth()));
            }

            //Testes
            log.debug("Tamanho Total: " + modeloColunas.getTotalColumnWidth());
            log.debug("Tamanho da tabela: " + tabela.getWidth());
        }
    }

    /**
     * PackColumn sets the preferred width of the visible column specified by
     * vColIndex. The column will be just wide enough to show the column head
     * and the widest cell in the column. margin pixels are added to the left
     * and right (resulting in an additional width of 2*margin pixels).
     *
     * @param table The table you want to resize a column.
     * @param vColIndex The column number.
     * @param margin Extra spaces for each side of column.
     */
    public static void packColumn(JTable table, int vColIndex, int margin) {
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width;

        // Get width of column header
        javax.swing.table.TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        java.awt.Component comp = renderer.getTableCellRendererComponent(
                table, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;

        // Get maximum width of column data
        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(
                    table, table.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }

        // Add margin
        width += 2 * margin;

        // Set the width
        col.setPreferredWidth(width);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton Object");
    }

    public static void loadSocksProxy() {
        String socksHost = getConfig().getString("socks.host");
        int socksPort = 0;
        if (getConfig().containsKey("socks.port")) {
            socksPort = getConfig().getInt("socks.port");
        }
        //Prepara as conexões para usar Socks Proxy (se configurado)
        if (socksHost != null && !socksHost.equals("")) {
            System.getProperties().put("socksProxyHost", socksHost);
            if (socksPort > 0 && socksPort < 65534) {
                System.getProperties().put("socksProxyPort", socksPort);
            }
        }
    }
}
