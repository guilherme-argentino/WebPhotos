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
package net.sf.webphotos.gui;

/**
 * Esta classe checa uma exceção no FtpClient.
 * Herda a classe {@link java.lang.Exception Exception}.
 * Envia dados para classe base através do construtor.
 */
public class FtpClientException extends Exception {

    /**
     * Esse método apenas repassa a informação.
     * Recebe uma mensagem de texto e repassa para a classe base.
     * @param msg Mensagem.
     */
    public FtpClientException(String msg) {
        super(msg);
    }

    public FtpClientException(Throwable cause) {
        super(cause);
    }

    public FtpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpClientException() {
    }
}
