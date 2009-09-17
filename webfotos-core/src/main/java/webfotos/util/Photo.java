package webfotos.util;
import javax.swing.ImageIcon;
import com.sun.image.codec.jpeg.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.*;
//import javax.media.jai.*;
import java.awt.*;

/**
 * Responsável pelas funções relacionadas a alterações de fotos.
 * Armazena um método para reajustar o tamanho da foto.
 */
public class Photo {
    /**
     * Redimensiona o tamanho da imagem, alterando o arquivo.
     * @param original Nome do arquivo original.
     * @param resized Nome do arquivo redimensionado.
     * @param maxSize Tamanho máximo.
     */    
    public static void resize(String original, String resized, int maxSize) {
        try{
            File originalFile = new File(original);
            ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
            Image i = ii.getImage();
            Image resizedImage = null;
            int iWidth = i.getWidth(null);
            int iHeight = i.getHeight(null);
            if (iWidth > iHeight) {
                    resizedImage = i.getScaledInstance(maxSize,(maxSize*iHeight)/iWidth,Image.SCALE_SMOOTH);
            } else {
                    resizedImage = i.getScaledInstance((maxSize*iWidth)/iHeight,maxSize,Image.SCALE_SMOOTH);
            }
            // This code ensures that all the
            // pixels in the image are loaded.
            Image temp = new ImageIcon(resizedImage).getImage();

            // Create the buffered image.
            BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // Copy image to buffered image.
            Graphics g = bufferedImage.createGraphics();
            // Clear background and paint the image.
            g.setColor(Color.white);
            g.fillRect(0, 0, temp.getWidth(null),temp.getHeight(null));
            g.drawImage(temp, 0, 0, null);
            g.dispose();

            // sharpen
            float[] sharpenArray = { 0, -1, 0, -1, 5, -1, 0, -1, 0 };
            Kernel kernel = new Kernel(3, 3, sharpenArray);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            bufferedImage = cOp.filter(bufferedImage, null);

            /* write the jpeg to a file */
            File file = new File(resized);
            FileOutputStream out = new FileOutputStream(file);

            /* encodes image as a JPEG data stream */
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            com.sun.image.codec.jpeg.JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);

            // writeParam = new JPEGImageWriteParam(null);
            // writeParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            // writeParam.setProgressiveMode(JPEGImageWriteParam.MODE_DEFAULT);

            param.setQuality(0.7f, true);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bufferedImage);

        } catch (Exception e) {
                System.out.println(e.getMessage());
        }

    }

    /**
     * Método principal.
     * Aplica o método resize e imprime uma saída apresentando quanto tempo levou para efetuar a operação.
     * @param args args do método main.
     */
    public static void main(String [] args) {
        long start = System.currentTimeMillis();
        resize("c:/teste/296.jpg","c:/teste/thumb120.jpg",120);
        System.out.println("done in " + (System.currentTimeMillis() - start) + " ms");
    }
}
 
 
