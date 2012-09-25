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
package net.sf.webphotos;

import net.sf.webphotos.util.legacy.Photo;

/**
 *
 * @author Guilherme
 */
public class PhotoTest {

    /**
     * M�todo principal. Aplica o m�todo resize e imprime uma sa�da apresentando
     * quanto tempo levou para efetuar a opera��o.
     *
     * @param args args do m�todo main.
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Photo.resize("c:/teste/296.jpg", "c:/teste/thumb120.jpg", 120);
        System.out.println("done in " + (System.currentTimeMillis() - start) + " ms");
    }
    
}
