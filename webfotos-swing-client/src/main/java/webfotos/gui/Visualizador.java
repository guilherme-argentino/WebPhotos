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
 * Calcula as dimensões da imagem e implementa a imagem na janela.
 */
public class Visualizador extends JDialog implements ActionListener {

    private Image foto;
    private int largura;
    private int altura;

    /**
     * Construtor da classe.
     * Abre a janela popup com a foto.
     * Recebe um frame para inclusão, nome da foto e um título para a janela.
     * Testa se é necessário redimensionar a foto, caso positivo chama a função calculaRedução.
     * Implementa um botão para fechar a janela e por último mostra a tela ao usuário.
     * @param caminhoImagem Caminho da foto.
     * @param frame Janela frame para alocar a foto.
     * @param titulo Título da janela.
     */
    public Visualizador(String caminhoImagem, Frame frame, String titulo) {
        super(frame, titulo, true);
        Container cp = this.getContentPane();

        cp.setLayout(new BorderLayout());
        foto = new ImageIcon(caminhoImagem).getImage();
        largura = foto.getWidth(this);
        altura = foto.getHeight(this);

        // no caso de uma imagem muito grande, utiliza de redução
        // (por ex. usuário acabou de adicionar uma imagem ao album)
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
     * Faz uma redução de dimensão da foto.
     * Recebe um valor de dimensão e um tamanho máximo.
     * Faz um cálculo baseado na divisão da largura pela altura para saber se a imagem
     * está na vertical ou na horizontal, então calcula e retorna a nova dimensão.
     * @param original Dimensão original.
     * @param tamMaximo Tamanho máximo da nova dimensão.
     * @return Retorna a nova dimensão.
     */
    public static Dimension calculaReducao(Dimension original, int tamMaximo) {

        Dimension d = new Dimension();
        // proporção
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
     * Esse método não é utilizado.
     * TODO: avaliar a funcionalidade deste método.
     * @param g Contexto gráfico.
     */
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        g.drawImage(foto, 5, 28, largura, altura, null);
    }

    /**
     * Desabilita a tela.
     * Desabilita sua visibilidade e encerra seus dados.
     * Esse método não é utilizado.
     * TODO: avaliar a funcionalidade deste método.
     * @param e Evento de ação.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Método principal.
     * Cria um objeto {@link java.awt.Dimension Dimension} e seta seus valores.
     * Logo após utiliza o método calculaRedução da própria classe para reduzir a dimensão original.
     * Ao final, imprime as duas dimensões.
     * @param a args do método main.
     */
    public static void main(String a[]) {
        Dimension dim = new Dimension(1154, 1772);
        Dimension novo = calculaReducao(dim, 550);
        System.out.println("original:" + dim.toString());
        System.out.println("reduzido:" + novo.toString());
    }
}
