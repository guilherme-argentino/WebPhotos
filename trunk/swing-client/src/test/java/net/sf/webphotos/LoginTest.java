/*
 * Copyright 2012 Guilherme.
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

import net.sf.webphotos.gui.util.Login;

/**
 *
 * @author Guilherme
 */
public class LoginTest {

    /**
     * M�todo principal.
     * Instancia um objeto Login para teste, checa o usu�rio e imprime na tela usu�rio e senha.
     * @param a args do m�todo main.
     */
    public static void main(String[] a) {
        Login l = Login.getLogin("teste login");
        String usuario = "";
        while (!usuario.equals("zz")) {
            l.show(null);
            usuario = l.getUser();
            System.out.println("usuario: " + usuario + "\nsenha:" + new String(l.getPassword()));
        }
    }
    
}
