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
package webfotos.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * Cria um visualizador de fotos numa janela popup.
 * Calcula as dimens�es da imagem e implementa a imagem na janela.
 */
public class Visualizador extends JDialog implements ActionListener {

    private Image foto;
    private int largura;
    private int altura;

    /**
     * Construtor da classe.
     * Abre a janela popup com a foto.
     * Recebe um frame para inclus�o, nome da foto e um t�tulo para a janela.
     * Testa se � necess�rio redimensionar a foto, caso positivo chama a fun��o calculaRedu��o.
     * Implementa um bot�o para fechar a janela e por �ltimo mostra a tela ao usu�rio.
     * @param caminhoImagem Caminho da foto.
     * @param frame Janela frame para alocar a foto.
     * @param titulo T�tulo da janela.
     */
    public Visualizador(String caminhoImagem, Frame frame, String titulo) {
        super(frame, titulo, true);
        Container cp = this.getContentPane();

        cp.setLayout(new BorderLayout());
        foto = new ImageIcon(caminhoImagem).getImage();
        largura = foto.getWidth(this);
        altura = foto.getHeight(this);

        // no caso de uma imagem muito grande, utiliza de redu��o
        // (por ex. usu�rio acabou de adicionar uma imagem ao album)
        if (largura > 550 || altura > 550) {
            Dimension d = calculaReducao(new Dimension(largura, altura), 550);
            largura = d.width;
            altura = d.height;
        }

        this.setSize(largura + 10, altura + 60);
        JButton btFechar = new JButton("fechar");
        cp.add(btFechar, BorderLayout.SOUTH);
        btFechar.addActionListener(this);
        this.setLocationRelativeTo(null);
        show();

    }

    /**
     * Faz uma redu��o de dimens�o da foto.
     * Recebe um valor de dimens�o e um tamanho m�ximo.
     * Faz um c�lculo baseado na divis�o da largura pela altura para saber se a imagem
     * est� na vertical ou na horizontal, ent�o calcula e retorna a nova dimens�o.
     * @param original Dimens�o original.
     * @param tamMaximo Tamanho m�ximo da nova dimens�o.
     * @return Retorna a nova dimens�o.
     */
    public static Dimension calculaReducao(Dimension original, int tamMaximo) {

        Dimension d = new Dimension();
        // propor��o
        double p = original.getWidth() / original.getHeight();

        // imagem horizontal
        if (p > 1) {
            d.width = tamMaximo;
            d.height = (int) (tamMaximo / p);

            // imagem vertical
        } else {
            d.width = (int) (tamMaximo * p);
            d.height = tamMaximo;
        }
        return d;
    }

    /**
     * Faz uma chamada a classe base e pinta todos os componentes.
     * Esse m�todo n�o � utilizado.
     * TODO: avaliar a funcionalidade deste m�todo.
     * @param g Contexto gr�fico.
     */
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        g.drawImage(foto, 5, 28, largura, altura, null);
    }

    /**
     * Desabilita a tela.
     * Desabilita sua visibilidade e encerra seus dados.
     * Esse m�todo n�o � utilizado.
     * TODO: avaliar a funcionalidade deste m�todo.
     * @param e Evento de a��o.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * M�todo principal.
     * Cria um objeto {@link java.awt.Dimension Dimension} e seta seus valores.
     * Logo ap�s utiliza o m�todo calculaRedu��o da pr�pria classe para reduzir a dimens�o original.
     * Ao final, imprime as duas dimens�es.
     * @param a args do m�todo main.
     */
    public static void main(String a[]) {
        Dimension dim = new Dimension(1154, 1772);
        Dimension novo = calculaReducao(dim, 550);
        System.out.println("original:" + dim.toString());
        System.out.println("reduzido:" + novo.toString());
    }
}
