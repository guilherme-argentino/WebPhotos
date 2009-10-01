package webfotos.gui;

import webfotos.acao.AcaoPopup;
import webfotos.util.*;

import javax.swing.*;

/**
 * Cria um menu popup com os dados do albúm.
 * Monta a parte gráfica e puxa as ações da classe {@link webfotos.gui.AcaoPopMenu AcaoPopMenu}.
 * Possui utilizações nas classes PopupMenuTest em pacotes de teste e PainelPesquisa em pacotes de código-fonte.
 */
public class PopupMenu extends JPopupMenu {
    private String preSQL="select albumID as ID, categorias.nmcategoria as Categoria, nmalbum as Pauta, DATE_FORMAT(DtInsercao, '%d/%m/%y') as Data from albuns left join categorias using(categoriaID) ";

    /**
     * Construtor da classe.
     * Cria os itens do menu e envia o evento para a classe AcaoPopMenu que se encarregará de gerar o menu.
     */
    public PopupMenu() {
        String posSQL;
        int ct=1;

        while((posSQL=Util.getConfig().getString("relatorio" + ct++))!=null) {
            int pos=posSQL.indexOf("/");
            String nomeComando=posSQL.substring(0,pos).trim();
            String comando=posSQL.substring(pos+1).trim();

            JMenuItem menuItem=new JMenuItem(nomeComando, new ImageIcon(getClass().getResource("/icons/pontoazul.gif")));

            menuItem.addActionListener(new AcaoPopMenu(comando));
            this.add(menuItem);
        }

    }

    /**
     * Método principal.
     * Cria um objeto PopupMenu e um frame para armazenar esse menu.
     * Seta toda a configuração e chama a classe AcaoPopup para trabalhar o evento de clique do mouse.
     * @param a args do método main.
     */
    public static void main(String[] a) {
        PopupMenu t=new PopupMenu();
        JFrame f=new JFrame();
        f.getContentPane().setLayout(null);

        JButton bt=new JButton("teste");
        bt.setBounds(20,20,100,25);
        bt.addMouseListener(new AcaoPopup((JPopupMenu) t));
        f.getContentPane().add(bt);


        f.setSize(200,200);
        f.getContentPane().add(t);
        //t.show();
        f.setVisible(true);
    }

    /**
     * Retorna uma query de SQL.
     * @return Retorna uma query.
     */
    public String getPreSQL() {
        return preSQL;
    }

    /**
     * Seta uma nova query para a variável preSQL.
     * @param preSQL Query.
     */
    public void setPreSQL(String preSQL) {
        this.preSQL = preSQL;
    }

}