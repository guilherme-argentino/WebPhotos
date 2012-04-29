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
package net.sf.webphotos.gui.component;

import javax.swing.*;
import java.awt.Container;
import net.sf.webphotos.action.AcaoToolbar;
import net.sf.webphotos.util.Util;

/**
 * <PRE>
 * Base para geração de Botões iconificados
 * (um símbolo visual no lugar de uma palavra para identificar o botão)
 * 
 * Atualmente ele é base para quatro botões da tela principal do WebFotos abaixo do
 * espaço usado para por legenda e crédito.
 * </PRE>
 */
public class BotaoIcone extends JButton {
    private static final Action acaoToolbar=new AcaoToolbar();
    private String prefixoIcon;
    
    /**
     * Constante que define a largura do Botão-Ícone
     */
    public static final int TAMANHOX = 24;
    /**
     * Constante que define a altura do Botão-Ícone
     */
    public static final int TAMANHOY = 24;
    
    /**
     * Parâmetro que define se o botão ira trabalhar com suas bordas de forma personalizada.
     */
    protected static boolean botaoPersonalizado = Util.getConfig().getBoolean("BotoesPersonalizados");

    /**
     * Construtor Padrão do Botão-Ícone.
     */
    public BotaoIcone() {

        this.setActionCommand(prefixoIcon);
        this.setRolloverEnabled(true);
        this.addActionListener(getAcaoToolbar());

        if(botaoPersonalizado) {
            this.setContentAreaFilled(false);		
            this.setBorderPainted(false);
            this.setFocusPainted(false);
        }

        this.setBounds(getX(), getY(), TAMANHOX, TAMANHOY);

    }

    /**
     * Retorna o Objeto do tipo {@link net.sf.webphotos.acao.AcaoToolbar} responsável pelas ações que todos os botões
     * irão acessar.
     * @return Objeto {@link net.sf.webphotos.acao.AcaoToolbar} atualmente em uso.
     */
    public static Action getAcaoToolbar() {
        return acaoToolbar;
    }

    /**
     * Retorna o Prefixo dos ícones em uso.
     * @return String em uso.
     */
    public String getPrefixoIcon() {
        return prefixoIcon;
    }

    /**
     * <PRE>
     * Prefixo do nome dos 3 Ícones que serão usados nas bordas personalizadas.
     * Exemplo:
     * 
     * Com o prefixo icone1, temos:
     * 
     * * icone1.gif - Padrão. apresentado durante toda a execução.
     * * icone1over.gif - Apresentado quando o mouse passa sobre o botão.
     * * icone1press.gif - Apresentado quando o mouse é pressionado.
     * </PRE>
     * @param prefixoIcon A string que será usada.
     */
    public void setPrefixoIcon(String prefixoIcon) {
        this.prefixoIcon = prefixoIcon;
        this.setIcon(new ImageIcon(getClass().getResource("/icons/" + getPrefixoIcon() + ".gif")));
        this.setActionCommand(getPrefixoIcon());
        if(botaoPersonalizado) {
            this.setRolloverIcon(new ImageIcon(getClass().getResource("/icons/" + getPrefixoIcon() + "over.gif")));
            this.setPressedIcon(new ImageIcon(getClass().getResource("/icons/" + getPrefixoIcon() + "press.gif")));
        }
    }
}