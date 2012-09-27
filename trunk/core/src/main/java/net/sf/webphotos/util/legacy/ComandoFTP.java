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
package net.sf.webphotos.util.legacy;

/**
 * Cria um comando de FTP.
 * Comando esse armazenado posteriormente no {@link net.sf.webphotos.util.CacheFTP CacheFTP}.
 *
 * UPLOAD=1 DOWNLOAD=2 DELETE=3;
 */
public class ComandoFTP implements Comparable<ComandoFTP> {

    private int operacao = -1;
    private int albumID = -1;
    private int fotoID = -1;

    /**
     * Construtor da classe.
     * Recebe os parâmetros necessários para construção de um comando FTP (ação, albúm, foto).
     * Seta as variáveis de dentro da classe com os valores recebidos.
     * @param acao Opção do tipo de ação.
     * @param album Albúm.
     * @param foto Foto.
     */
    public ComandoFTP(int acao, int album, int foto) {
        operacao = acao;
        albumID = album;
        fotoID = foto;
    }

    /**
     * Recebe um Object e compara com os valores armazenados na classe.
     * Caso positivo retorna <I>true</I>, caso contrário <I>false</I>.
     * @param o comandoFTP a ser comparado.
     * @return Retorna uma variável lógica.
     */
    @Override
    public boolean equals(Object o) {
        ComandoFTP obj;
        try {
            obj = (ComandoFTP) o;
        } catch (ClassCastException ccE) {
            return false;
        }
        if (obj.getOperacao() == operacao
                && obj.getAlbumID() == albumID
                && obj.getFotoID() == fotoID) {
            return true;
        }
        return false;
    }

    /**
     * Faz um teste lógico para retornar uma variável lógica.
     * É utilizado na classe {@link net.sf.webphotos.util.CacheFTP CacheFTP} no método
     * {@link net.sf.webphotos.util.CacheFTP#add(Object) add(Object a)}
     * TODO: Avaliar se esse método é realmente necessário para o código.
     * @param outra Object com dados do comando para comparação
     * @return Retorna um valor lógico.
     */
    public boolean recebe(ComandoFTP outra) {
        // o objeto "outra" pode entrar na colecao ?
        // não pode de fotoID=0...
        // um objeto "apagar 100 0" é maior que um "apagar 100 20"
        // já que o primeiro engloba o segundo
        if (outra.getOperacao() == operacao
                && outra.getAlbumID() == albumID
                && fotoID == 0) {
            return false;
        }
        return true;
    }

    /**
     * Interface de ordenação (sort) para agrupar as operações (uploads, 
     * downloads, e deletes). Confere primeiro pela operação, se forem
     * idênticas, confere pelo albúm, se forem idênticos também checa pela foto.
     * @param outro Comando para comparação.
     * @return Retorna um valor numérico para comparação.
     */
    @Override
    public int compareTo(ComandoFTP c) {
        // operações diferentes
        if (this.getOperacao() != c.getOperacao()) {
            return this.getOperacao() - c.getOperacao();
        } // operação igual. Verifica se album é diferente
        else if (this.getAlbumID() != c.getAlbumID()) {
            return this.getAlbumID() - c.getAlbumID();
        } // operação igual, álbum igual. Ordena pela foto
        else {
            return this.getFotoID() - c.getFotoID();
        }
    }

    /**
     * Retorna o valor numérico da operação.
     * @return Retorna a operação.
     */
    public int getOperacao() {
        return operacao;
    }

    /**
     * Retorna o valor numérico do albúm (ID).
     * @return Retorna o albúm.
     */
    public int getAlbumID() {
        return albumID;
    }

    /**
     * Retorna o valor numérico da foto (ID).
     * @return Retorna a foto.
     */
    public int getFotoID() {
        return fotoID;
    }

    /**
     * Retorna uma String contendo os dados da classe agrupados.
     * Reuni os números da operação, do albúm e da foto.
     * @return Retorna dados do comandoFTP agrupados.
     */
    @Override
    public String toString() {
        return operacao + " " + albumID + " " + fotoID;
    }
}
