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
 * Esta classe não parece estar concluída.
 * Efetua um teste de saída em XML e outro teste de saída com String.
 * Através dos métodos da classe Album, toXML() e toString(), os métodos dessa classe apenas criam um objeto Album para receber os dados do Album, e imprimem no formato desejado.
 * Não possui dados atributos e um de seus métodos não apresenta utilizações.
 * Não é uma classe viável.
 */
public class AcaoTeste extends AbstractAction {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2599435233748701194L;

	/**
     * Contrutor da classe.
     * Inicialmente vazio, pois a classe não possui atributos.
     */
    public AcaoTeste() {
		
    }
	
    /**
     * Instancia um objeto Album para receber os dados do albúm e faz um print no formato XML, através da função {@link webfotos.Album#toXML() toXML()} localizada em webfotos.Album.
     * @param e Evento da ação de teste.
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
     * Instancia um objeto Album para receber os dados do albúm e faz um print com uma String, através da função {@link webfotos.Album#toString() toString()} localizada em webfotos.Album.
     */
    public void mostraAlbum() {
            Album a=Album.getAlbum();
            Util.out.println ("Album:" + a.toString());
    }
	
}
