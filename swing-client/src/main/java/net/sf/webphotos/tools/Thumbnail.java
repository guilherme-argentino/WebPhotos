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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import org.apache.commons.configuration.Configuration;

import net.sf.webphotos.util.Util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.sf.webphotos.BancoImagem;

/**
 * Molda diferentes tamanhos de dimensão para as imagens.
 * Controla os tamanhos máximos de cada thumb e redimensiona o tamanho das
 * fotos originais baseado nesses valores máximos.
 */
public final class Thumbnail {

    // tam máximo de cada thumb
    private static int t1, t2, t3, t4;
    // marca d´agua e texto aplicado ao thumbnail 4
    private static String marcadagua, mdTeste;
    private static String texto;
    private static int mdPosicao, mdMargem, mdTransparencia;
    private static String txFamilia;
    private static int txTamanho, txPosicao, txMargem, txEstilo;
    private static Color txCorFrente, txCorFundo;

    /**
     * Busca no arquivo de configuração, classe
     * {@link net.sf.webphotos.util.Config Config}, os tamnahos dos 4 thumbs
     * e seta esses valores nas variáveis desta classe.
     * Testa se o usuário setou valores de marca d'água e texto para o thumb4,
     * caso afirmativo, busca os valores necessários no arquivo de configuração.
     */
    private static void inicializar() {

        // le as configurações do usuário
        Configuration c = Util.getConfig();

        // tamanhos de thumbnails
        t1 = c.getInt("thumbnail1");
        t2 = c.getInt("thumbnail2");
        t3 = c.getInt("thumbnail3");
        t4 = c.getInt("thumbnail4");

        // usuario setou marca d'agua para thumbnail 4 ?
        // TODO: melhorar teste para captação destes parametros
        try {
            marcadagua = c.getString("marcadagua");
            mdPosicao = c.getInt("marcadagua.posicao");
            mdMargem = c.getInt("marcadagua.margem");
            mdTransparencia = c.getInt("marcadagua.transparencia");
            mdTeste = c.getString("marcadagua.teste");
        } catch (Exception ex) {
            ex.printStackTrace(Util.err);
        }

        // usuário setou texto para o thumbnail 4 ?
        try {
            texto = c.getString("texto");
            txPosicao = c.getInt("texto.posicao");
            txMargem = c.getInt("texto.margem");
            txTamanho = c.getInt("texto.tamanho");
            txEstilo = c.getInt("texto.estilo");
            txFamilia = c.getString("texto.familia");

            String[] aux = c.getStringArray("texto.corFrente");
            txCorFrente = new Color(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
            aux = c.getStringArray("texto.corFundo");
            txCorFundo = new Color(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
        } catch (Exception ex) {
            ex.printStackTrace(Util.err);
        }

    }

    /**
     * Cria thumbs para as imagens.
     * Testa se já existem valores setados para o thumb,
     * se não existir chama o método
     * {@link net.sf.webphotos.Thumbnail#inicializar() inicializar}
     * para setar seus valores.
     * Abre o arquivo de imagem passado como parâmetro
     * e checa se é uma foto válida.
     * Obtém o tamanho original da imagem, checa se está
     * no formato paisagem ou retrato e utiliza o método
     * {@link java.awt.Images#getScaledInstance(int,int,int) getScaledInstance}
     * para calcular os thumbs.
     * Ao final, salva as imagens.
     * @param caminhoCompletoImagem Caminho da imagem.
     */
    public static void makeThumbs(String caminhoCompletoImagem) {

        String diretorio = "", arquivo = "";
        if (t1 == 0) {
            inicializar();
        }

        try {
            File f = new File(caminhoCompletoImagem);
            if (!f.isFile() || !f.canRead()) {
                Util.out.println("Erro no caminho do arquivo " + caminhoCompletoImagem);
                return;
            }

            // Foto em alta corrompida 
            if (getFormatName(f) == null) {
                Util.err.println("Foto Corrompida");
                return;
            } else {
                Util.out.println("Foto Ok!");
            }

            diretorio = f.getParent();
            arquivo = f.getName();

            ImageIcon ii = new ImageIcon(f.getCanonicalPath());
            Image i = ii.getImage();

            Image tumb1 = null, tumb2 = null, tumb3 = null, tumb4 = null;

            // obtém o tamanho da imagem original
            int iWidth = i.getWidth(null);
            int iHeight = i.getHeight(null);
            int w = 0, h = 0;

            if (iWidth > iHeight) {
                tumb1 = i.getScaledInstance(t1, (t1 * iHeight) / iWidth, Image.SCALE_SMOOTH);
                tumb2 = i.getScaledInstance(t2, (t2 * iHeight) / iWidth, Image.SCALE_SMOOTH);
                tumb3 = i.getScaledInstance(t3, (t3 * iHeight) / iWidth, Image.SCALE_SMOOTH);
                tumb4 = i.getScaledInstance(t4, (t4 * iHeight) / iWidth, Image.SCALE_SMOOTH);
                w = t4;
                h = (t4 * iHeight) / iWidth;
            } else {
                tumb1 = i.getScaledInstance((t1 * iWidth) / iHeight, t1, Image.SCALE_SMOOTH);
                tumb2 = i.getScaledInstance((t2 * iWidth) / iHeight, t2, Image.SCALE_SMOOTH);
                tumb3 = i.getScaledInstance((t3 * iWidth) / iHeight, t3, Image.SCALE_SMOOTH);
                tumb4 = i.getScaledInstance((t4 * iWidth) / iHeight, t4, Image.SCALE_SMOOTH);
                w = (t4 * iWidth) / iHeight;
                h = t4;
            }

            tumb4 = estampar(tumb4);

            Util.log("Salvando Imagens");

            save(tumb1, diretorio + File.separator + "_a" + arquivo);
            save(tumb2, diretorio + File.separator + "_b" + arquivo);
            save(tumb3, diretorio + File.separator + "_c" + arquivo);
            save(tumb4, diretorio + File.separator + "_d" + arquivo);

        } catch (Exception e) {
            e.printStackTrace(Util.err);
            Util.err.println(e.getMessage());
        }
    }

    private static Image estampar(Image im) {
        try {
            Image temp = new ImageIcon(im).getImage();

            BufferedImage buf = new BufferedImage(temp.getWidth(null),
                    temp.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = (Graphics2D) buf.getGraphics();

            g2.drawImage(temp, 0, 0, null);
            g2.setBackground(Color.BLUE);

            Dimension dimensaoFoto = new Dimension(im.getWidth(null), im.getHeight(null));

            // aplicar texto
            if (texto != null) {
                Util.out.println("aplicando texto " + texto);

                Font fonte = new Font(txFamilia, txEstilo, txTamanho);
                g2.setFont(fonte);
                FontMetrics fm = g2.getFontMetrics(fonte);
                Dimension dimensaoTexto = new Dimension(fm.stringWidth(texto), fm.getHeight());
                Point posTexto = calculaPosicao(dimensaoFoto, dimensaoTexto, txMargem, txPosicao);

                g2.setPaint(txCorFundo);
                g2.drawString(texto, (int) posTexto.getX() + 1, (int) posTexto.getY() + 1 + fm.getHeight());
                g2.setPaint(txCorFrente);
                g2.drawString(texto, (int) posTexto.getX(), (int) posTexto.getY() + fm.getHeight());
            }

            // aplicar marca d´agua
            if (marcadagua != null) {
                Image marca = new ImageIcon(marcadagua).getImage();
                int rule = AlphaComposite.SRC_OVER;
                float alpha = (float) mdTransparencia / 100;
                Point pos = calculaPosicao(dimensaoFoto,
                        new Dimension(marca.getWidth(null), marca.getHeight(null)),
                        mdMargem, mdPosicao);

                g2.setComposite(AlphaComposite.getInstance(rule, alpha));
                g2.drawImage(marca, (int) pos.getX(), (int) pos.getY(), null);
            }

            g2.dispose();
            //return (Image) buf;
            return Toolkit.getDefaultToolkit().createImage(buf.getSource());
        } catch (Exception e) {
            return im;
        }
    }

    private static boolean save(Image thumbnail, String nmArquivo) {
        try {
            // This code ensures that all the
            // pixels in the image are loaded.
            Image temp = new ImageIcon(thumbnail).getImage();

            // Create the buffered image.
            BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                    temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // Copy image to buffered image.
            Graphics g = bufferedImage.createGraphics();

            // Clear background and paint the image.
            g.setColor(Color.white);
            g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
            g.drawImage(temp, 0, 0, null);
            g.dispose();

            // write the jpeg to a file
            File file = new File(nmArquivo);
            // Recria o arquivo se existir
            if (file.exists()) {
                Util.out.println("Redefinindo a Imagem: " + nmArquivo);
                file.delete();
                file = new File(nmArquivo);
            }
            FileOutputStream out = new FileOutputStream(file);

            // encodes image as a JPEG data stream
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            com.sun.image.codec.jpeg.JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);

            // writeParam = new JPEGImageWriteParam(null);
            // writeParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            //writeParam.setProgressiveMode(JPEGImageWriteParam.MODE_DEFAULT);
            param.setQuality(1.0f, true);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bufferedImage);
            return true;
        } catch (IOException ioex) {
            ioex.printStackTrace(Util.err);
            return false;
        }
    }

    private static Point calculaPosicao(Dimension dimensaoExt,
            Dimension dimensaoInt, int margem, int posicao) {
        int x = 0, y = 0;
        // Posição da marca d'agua
        // 1---2---3 y1
        // 4---5---6 y2
        // 7---8---9 y3
        // x1  x2  x3
        int x1 = margem;
        int x2 = (int) ((float) (dimensaoExt.width - dimensaoInt.width) / 2);
        int x3 = dimensaoExt.width - dimensaoInt.width - margem;

        int y1 = margem;
        int y2 = (int) ((float) (dimensaoExt.height - dimensaoInt.height) / 2);
        int y3 = dimensaoExt.height - dimensaoInt.height - margem;

        if (posicao == 1) {
            x = x1;
            y = y1;
        } else if (posicao == 2) {
            x = x2;
            y = y1;
        } else if (posicao == 3) {
            x = x3;
            y = y1;
        } else if (posicao == 4) {
            x = x1;
            y = y2;
        } else if (posicao == 5) {
            x = x2;
            y = y2;
        } else if (posicao == 6) {
            x = x3;
            y = y2;
        } else if (posicao == 7) {
            x = x1;
            y = y3;
        } else if (posicao == 8) {
            x = x2;
            y = y3;
        } else if (posicao == 9) {
            x = x3;
            y = y3;
        }

        return new Point(x, y);
    }

    /**
     * Returns the format of the image in the file 'f'.
     * Returns null if the format is not known.
     * @param f An java.io.File
     * @return An String containing the type of Image or NULL
     */
    private static String getFormatInFile(File f) {
        return getFormatName(f);
    }

    // Returns the format of the image in the input stream 'is'.
    // Returns null if the format is not known.
    private static String getFormatFromStream(java.io.InputStream is) {
        return getFormatName(is);
    }

    // Returns the format name of the image in the object 'o'.
    // 'o' can be either a File or InputStream object.
    // Returns null if the format is not known.
    private static String getFormatName(Object o) {
        try {
            // Create an image input stream on the image
            ImageInputStream iis = ImageIO.createImageInputStream(o);

            // Find all image readers that recognize the image format
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                // No readers found
                return null;
            }

            // Use the first reader
            ImageReader reader = iter.next();

            // Close stream
            iis.close();

            // Return the format name
            return reader.getFormatName();
        } catch (Exception e) {
            // The image could not be read
            return null;
        }
    }

    /**
     * Faz um load no arquivo de configuração e chama o método
     * {@link net.sf.webphotos.Thumbnail#makeThumbs(String) makeThumbs} para fazer
     * thumbs de uma foto específica.
     * @param args args do método main.
     */
    public static void main(String[] args) {
        //makeThumbs("c:/temp/167.jpg");
        //makeThumbs("d:/bancoImagem/81/312.jpg");
        //makeThumbs("d:/bancoImagem/81/313.jpg");
        //makeThumbs("d:/bancoImagem/81/314.jpg");
        //makeThumbs("d:/bancoImagem/81/315.jpg");
        //makeThumbs("D:/webfotos/460/2072.jpg");
        //executaLote();

    }

    /**
     * Abre uma conexão com o banco de dados através da classe BancoImagem,
     * busca um lote de imagens e faz thumbs para todas as fotos.
     * Não possui utilizações.
     */
    public static void executaLote() {
        net.sf.webphotos.BancoImagem db = net.sf.webphotos.BancoImagem.getBancoImagem();

        try {
            db.configure("jdbc:mysql://mysql.iphotel.com.br/serra45", "com.mysql.jdbc.Driver");
            BancoImagem.login();
            java.sql.Connection conn = BancoImagem.getConnection();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery("select * from fotos");

            int albumID, fotoID;
            String caminho;

            while (rs.next()) {
                albumID = rs.getInt("albumID");
                fotoID = rs.getInt("fotoID");
                caminho = "d:/bancoImagem/" + albumID + "/" + fotoID + ".jpg";
                makeThumbs(caminho);
                Util.out.println(caminho);
            }

            rs.close();
            st.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace(Util.err);
        }
    }
}
