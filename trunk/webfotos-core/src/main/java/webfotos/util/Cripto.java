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
package webfotos.util;

import webfotos.util.*;
import java.util.*;

/**
 * Trabalha com criptografia em mensagens usando métodos específicos.
 */
public class Cripto {
    // variáveis da chave
    private static int[] chave;
    private static int MAX;
    private static int pos=0;
    private static javax.swing.JTextArea txt=new javax.swing.JTextArea();

    /**
     * Método principal. Faz um teste de criptografia.
     * Criptografa uma mensagem, imprime e logo em seguida decripta a mensagem.
     * @param t args do método main.
     */
    public static void main(String[] t) {
        javax.swing.JFrame f=new javax.swing.JFrame("teste");
        java.awt.Container c=f.getContentPane();

        c.add(txt);
        f.setSize(600,400);
        f.setVisible(true);

        String mensagem="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\"\'~`!@#$%^&*()_+|\\=-{}[]:;/?><,.";
        String especiais="áéíóúàèìòùÀÈÌÒÙÁÉÍÓÚÇçÜüËë";
        String sql="select albuns.albumID as ID, categorias.nmcategoria, albuns.nmalbum as Pauta, DATE_FORMAT(albuns.DtInsercao, '%d/%m/%y') as Data from albuns left join fotos using(albumID) left join creditos using(creditoID) left join categorias on albuns.categoriaID=categorias.categoriaID where creditos.nome='Divulgação'";
        String chave="teste";

        String msgPedro="São Paulo, Araçatuba";


        String r=encripta(msgPedro, chave);
        txt.append("resultado da criptografia:\n" + r);
        System.out.println ("resultado da criptografia:\n" + r);
        String m=decripta(r, chave);

        txt.append("\nresultado da decripto:\n" + m);
        //System.out.println ("\nresultado da decripto:\n" + m);
    }


    /**
     * Recebe uma mensagem e uma chave para criptografá-la.
     * Utiliza o método {@link java.net.URLEncoder#encode(String,String) encode(String s,String enc)} e o método {@link webfotos.util.Base64#encodeString(String) encodeString(String s)} da classe Base64.
     * @param mensagem Mensagem.
     * @param chave Chave de criptografia.
     * @return Retorna uma mensagem criptografada.
     */
    public static String encripta(String mensagem, String chave) {
        Random r=new Random();
        String byteLetra, byteSal, hexaTemperado;
        try { mensagem=java.net.URLEncoder.encode(mensagem,"ISO-8859-1");
        } catch (Exception e) {}		

        StringBuffer resultado=new StringBuffer(mensagem.length() * 2);

        inicializarChave(chave);

        for(int i=0; i < mensagem.length(); i++) {
            byteLetra=Integer.toBinaryString((int) mensagem.charAt(i));
            while(byteLetra.length() < 7)
                    byteLetra="0" + byteLetra;

            byteSal=byteLetra.substring(0,4) + r.nextInt(2) + byteLetra.substring(4);
            hexaTemperado=Integer.toHexString(temperar(Integer.parseInt(byteSal,2)));
            resultado.append((hexaTemperado.length() < 2) ? "0" + hexaTemperado : hexaTemperado);

            txt.append(mensagem.charAt(i) + " asc:" + (int) mensagem.charAt(i) + " bin:" + byteLetra + " sal:" + byteSal + " hexaTemp:" + hexaTemperado + "\n");
        }
        txt.append("Resultado antes do encode base64:\n" + resultado.toString() + "\n");
        return Base64.encodeString(resultado.toString());
    }

    /**
     * Recebe uma mensagem e uma chave para decriptá-la.
     * Utiliza o método {@link webfotos.util.Base64#decodeToString(String) decodeToString(String s)} da classe Base64 e o método {@link java.net.URLDecoder#decode(String,String) decode(String s,String enc)}.
     * @param mensagem Mensagem criptografada.
     * @param chave Chave de criptografia.
     * @return Retorna uma mensagem decriptada.
     */
    public static String decripta(String mensagem, String chave) {
        String m=Base64.decodeToString(mensagem);
        String byteHexa;
        StringBuffer resultado=new StringBuffer(m.length()/2);
        String binario, semSal;
        int ct=0;

        txt.append("Mensagem criptografada:\n" + mensagem + "\ndecodificando hexa:\n" + m);
        inicializarChave(chave);

        while(ct < m.length()) {
            byteHexa=m.substring(ct,ct+2);

            binario=Integer.toBinaryString(destemperar(Integer.parseInt(byteHexa,16)));

            while(binario.length() < 8)
                    binario="0" + binario;

            semSal=binario.substring(0,4) + binario.substring(5);

            resultado.append((char) Integer.parseInt(semSal,2));
            ct+=2;
            txt.append("hexa: " + byteHexa + " inteiro temperado:" + Integer.parseInt(byteHexa,16) + 
            " bin destemperado (com sal): " + binario + " bin sem sal:" + semSal + " caracter:" + (char) Integer.parseInt(semSal,2));
        }
        String r=resultado.toString();

        try { r=java.net.URLDecoder.decode(r,"ISO-8859-1");	    	
        } catch (Exception e) {}
        return r;

    }

    private static void inicializarChave(String sequencia) {
        chave=null;pos=0;MAX=0;

        try { sequencia=java.net.URLEncoder.encode(sequencia,"ISO-8859-1");	    
        } catch (Exception e) { }

        chave=new int[sequencia.length()];

        for(int i=0; i < sequencia.length(); i++)
            chave[i]=(int) sequencia.charAt(i);

        MAX=chave.length;
    }

    private static int temperar(int valor) {
        int soma=0;

        soma=valor + chave[pos];
        if(soma > 255) soma-=255;
        proximo();
        return soma;
    }

    private static int destemperar(int valor) {
        int soma=0;

        if(valor <= chave[pos]) {
                soma=valor + 255 - chave[pos];
        } else {
                soma=valor - chave[pos];
        }		
        proximo();
        return soma;		
    }

    private static void proximo() {
        pos++;
        if(pos >= MAX) pos=0;
    }

    /**
     * Imprime os caracteres do vetor chave. Relaciona caracteres e números.
     * Não possui utilizações.
     * TODO: avaliar sua funcionalidade.
     */
    public static void imprimirChave() {
        String r="";
        for(int i=0; i < chave.length; i++)
            System.out.println ((char) chave[i] + " -> " + chave[i]);
    }

}	

