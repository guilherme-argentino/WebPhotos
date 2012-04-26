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
package net.sf.webphotos.gui;

import net.sf.webphotos.util.Util;
import net.sf.webphotos.action.AcaoPopup;

import javax.swing.*;

/**
 * Cria um menu popup com os dados do alb�m.
 * Monta a parte gr�fica e puxa as a��es da classe {@link webfotos.gui.AcaoPopMenu AcaoPopMenu}.
 * Possui utiliza��es nas classes PopupMenuTest em pacotes de teste e PainelPesquisa em pacotes de c�digo-fonte.
 */
public class PopupMenu extends JPopupMenu {
    private String preSQL="select albumID as ID, categorias.nmcategoria as Categoria, nmalbum as Pauta, DATE_FORMAT(DtInsercao, '%d/%m/%y') as Data from albuns left join categorias using(categoriaID) ";

    /**
     * Construtor da classe.
     * Cria os itens do menu e envia o evento para a classe AcaoPopMenu que se encarregar� de gerar o menu.
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
     * M�todo principal.
     * Cria um objeto PopupMenu e um frame para armazenar esse menu.
     * Seta toda a configura��o e chama a classe AcaoPopup para trabalhar o evento de clique do mouse.
     * @param a args do m�todo main.
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
     * Seta uma nova query para a vari�vel preSQL.
     * @param preSQL Query.
     */
    public void setPreSQL(String preSQL) {
        this.preSQL = preSQL;
    }

}