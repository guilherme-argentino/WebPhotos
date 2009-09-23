package webfotos.gui;

import javax.swing.*;
import java.awt.Container;
import webfotos.acao.AcaoToolbar;
import webfotos.util.Util;

/**
 * <PRE>
 * Base para gera��o de Bot�es iconificados
 * (um s�mbolo visual no lugar de uma palavra para identificar o bot�o)
 * 
 * Atualmente ele � base para quatro bot�es da tela principal do WebFotos abaixo do
 * espa�o usado para por legenda e cr�dito.
 * </PRE>
 */
public class BotaoIcone extends JButton {
    private static final Action acaoToolbar=new AcaoToolbar();
    private String prefixoIcon;
    
    /**
     * Constante que define a largura do Bot�o-�cone
     */
    public static final int TAMANHOX = 24;
    /**
     * Constante que define a altura do Bot�o-�cone
     */
    public static final int TAMANHOY = 24;
    
    /**
     * Par�metro que define se o bot�o ira trabalhar com suas bordas de forma personalizada.
     */
    protected static boolean botaoPersonalizado = Util.getConfig().getBoolean("BotoesPersonalizados");

    /**
     * Construtor Padr�o do Bot�o-�cone.
     */
    public BotaoIcone() {

        this.setActionCommand(getPrefixoIcon());
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
     * Construtor do Bot�o-�cone que recebe todos os par�metros de trabalho.
     * @param _cp ContentPanel da janela que o �cone est� inserido.
     * @param _tooltip Texto que � apresentado quando o mouse passa sobre o bot�o.
     * @param _prefixoIcon Prefixo do nome dos 3 �cones que ser�o usados nas bordas personalizadas.
     * @see BotaoIcone#setPrefixoIcon(java.lang.String)
     */
    public BotaoIcone(Container _cp, String _tooltip, String _prefixoIcon) {

        this.setPrefixoIcon(_prefixoIcon);
        this.setRolloverEnabled(true);
        if(botaoPersonalizado) {
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setFocusPainted(false);
        }
        this.setToolTipText(_tooltip);
        this.addActionListener(getAcaoToolbar());
        _cp.add(this);

        this.setBounds(getX(), getY(), TAMANHOX, TAMANHOY);

    }

    /**
     * Retorna o Objeto do tipo {@link webfotos.acao.AcaoToolbar} respons�vel pelas a��es que todos os bot�es
     * ir�o acessar.
     * @return Objeto {@link webfotos.acao.AcaoToolbar} atualmente em uso.
     */
    public static Action getAcaoToolbar() {
        return acaoToolbar;
    }

    /**
     * Retorna o Prefixo dos �cones em uso.
     * @return String em uso.
     */
    public String getPrefixoIcon() {
        return prefixoIcon;
    }

    /**
     * <PRE>
     * Prefixo do nome dos 3 �cones que ser�o usados nas bordas personalizadas.
     * Exemplo:
     * 
     * Com o prefixo icone1, temos:
     * 
     * * icone1.gif - Padr�o. apresentado durante toda a execu��o.
     * * icone1over.gif - Apresentado quando o mouse passa sobre o bot�o.
     * * icone1press.gif - Apresentado quando o mouse � pressionado.
     * </PRE>
     * @param _prefixoIcon A string que ser� usada.
     */
    public void setPrefixoIcon(String _prefixoIcon) {
        this.prefixoIcon = _prefixoIcon;
        this.setIcon(new ImageIcon(getClass().getResource("/icons/" + getPrefixoIcon() + ".gif")));
        this.setActionCommand(getPrefixoIcon());
        if(botaoPersonalizado) {
            this.setRolloverIcon(new ImageIcon(getClass().getResource("/icons/" + getPrefixoIcon() + "over.gif")));
            this.setPressedIcon(new ImageIcon(getClass().getResource("/icons/" + getPrefixoIcon() + "press.gif")));
        }
    }
}