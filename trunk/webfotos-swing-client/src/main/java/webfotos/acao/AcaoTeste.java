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
package webfotos.acao;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.webphotos.Album;
import webfotos.util.Util;

/**
 * Esta classe n�o parece estar conclu�da.
 * Efetua um teste de sa�da em XML e outro teste de sa�da com String.
 * Atrav�s dos m�todos da classe Album, toXML() e toString(), os m�todos dessa classe apenas criam um objeto Album para receber os dados do Album, e imprimem no formato desejado.
 * N�o possui dados atributos e um de seus m�todos n�o apresenta utiliza��es.
 * N�o � uma classe vi�vel.
 */
public class AcaoTeste extends AbstractAction {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2599435233748701194L;

	/**
     * Contrutor da classe.
     * Inicialmente vazio, pois a classe n�o possui atributos.
     */
    public AcaoTeste() {
		
    }
	
    /**
     * Instancia um objeto Album para receber os dados do alb�m e faz um print no formato XML, atrav�s da fun��o {@link webfotos.Album#toXML() toXML()} localizada em webfotos.Album.
     * @param e Evento da a��o de teste.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
            //Util.out.println ("acao teste");
            Album a=Album.getAlbum();
            Util.out.println (a.toXML());
            //CacheFTP.getControleFTP().toString();
            //mostraAlbum();
            //Util.out.println ("CacheFTP");
            //CacheFTP.getControleFTP().descarregarArquivo();
            //Util.out.println (CacheFTP.getControleFTP().toString());
    }
	
    /**
     * Instancia um objeto Album para receber os dados do alb�m e faz um print com uma String, atrav�s da fun��o {@link webfotos.Album#toString() toString()} localizada em webfotos.Album.
     */
    public void mostraAlbum() {
            Album a=Album.getAlbum();
            Util.out.println ("Album:" + a.toString());
    }
	
}
