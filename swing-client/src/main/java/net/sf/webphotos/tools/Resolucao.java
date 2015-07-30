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
package net.sf.webphotos.tools;

import java.awt.MediaTracker;
import java.io.File;
import java.sql.*;
import javax.swing.*;

public class Resolucao {

    public static void main(String args[]) {
        TelaResolucao f = new TelaResolucao();

        f.setVisible(true);
        f.iniciar();
    }
}

class TelaResolucao extends JFrame {

    private final JTextArea texto;
    private final JTextArea instrucao;
    private final JLabel contador;
    private final JLabel bons;
    private final JLabel maus = new JLabel("0");

    public TelaResolucao() {
        this.bons = new JLabel("0");
        this.contador = new JLabel("0/0");
        setSize(470, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        texto = new JTextArea();
        instrucao = new JTextArea();
        //texto.setLineWrap(true);
        //texto.setWrapStyleWord(true);
        JScrollPane scrTexto = new JScrollPane(texto);
        JScrollPane scrInstrucao = new JScrollPane(instrucao);

        maus.setBounds(3, 3, 150, 20);
        scrTexto.setBounds(3, 20, 450, 80);

        bons.setBounds(3, 110, 150, 20);
        scrInstrucao.setBounds(3, 130, 450, 200);

        contador.setBounds(3, 335, 150, 20);

        getContentPane().add(bons);
        getContentPane().add(maus);
        getContentPane().add(scrTexto);
        getContentPane().add(scrInstrucao);
        getContentPane().add(contador);

    }

    public void iniciar() {
        String url = "jdbc:mysql://192.168.0.2/saopaulosp";
        Connection conn;
        ResultSet rs1;
        ResultSet rs2;
        Statement st1;
        Statement st2;
        int albumID;
        int fotoID;
        ImageIcon foto;
        int altura, largura;
        int totalRegistros;
        int ct = 0;
        int ctB = 0;
        int ctM = 0;
        File f;
        String msgErro;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, "root", "");
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("select count(*) from fotos")) {
                rs.first();
                totalRegistros = rs.getInt(1);
            }

            st1 = conn.createStatement();
            rs1 = st1.executeQuery("select albumID from albuns order by albumID");

            while (rs1.next()) {
                albumID = rs1.getInt("albumID");
                st2 = conn.createStatement();
                rs2 = st2.executeQuery("select fotoID from fotos where altura=0 and altura=0 and albumID=" + albumID);
                while (rs2.next()) {
                    ct++;
                    contador.setText(ct + "/" + totalRegistros);
                    fotoID = rs2.getInt("fotoID");
                    String caminho = "F:/albuns_sp/" + albumID + "/" + fotoID + ".jpg";
                    foto = new ImageIcon(caminho);

                    if (foto.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        ctB++;
                        bons.setText("" + ctB);
                        altura = foto.getIconHeight();
                        largura = foto.getIconWidth();
                        caminho = "update fotos set largura=" + largura + ", altura=" + altura + " where fotoID=" + fotoID + ";\n";
                        instrucao.append(caminho);
                    } else {
                        ctM++;
                        maus.setText("" + ctM);
                        // que erro ocorreu ?
                        msgErro = "";
                        f = new File("F:/albuns_sp/" + albumID + "/" + fotoID + ".jpg");
                        if (!f.exists()) {
                            msgErro = " | arquivo não existe";
                        }
                        if (!f.isFile()) {
                            msgErro = msgErro + " | não é arquivo";
                        }
                        if (!f.canRead()) {
                            msgErro = msgErro + " | não pode ser lido";
                        }
                        caminho = albumID + "/" + fotoID + msgErro + "\n";
                        texto.append(caminho);
                    }
                }
                rs2.close();
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            texto.append("erro na leitura do driver " + e);
        }
    }
}