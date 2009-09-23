package webfotos.gui;

import javax.swing.*;
import java.awt.Container;
import webfotos.acao.AcaoToolbar;
import webfotos.util.Util;

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
     * Construtor do Botão-Ícone que recebe todos os parâmetros de trabalho.
     * @param _cp ContentPanel da janela que o ícone está inserido.
     * @param _tooltip Texto que é apresentado quando o mouse passa sobre o botão.
     * @param _prefixoIcon Prefixo do nome dos 3 Ícones que serão usados nas bordas personalizadas.
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
     * Retorna o Objeto do tipo {@link webfotos.acao.AcaoToolbar} responsável pelas ações que todos os botões
     * irão acessar.
     * @return Objeto {@link webfotos.acao.AcaoToolbar} atualmente em uso.
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
     * @param _prefixoIcon A string que será usada.
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