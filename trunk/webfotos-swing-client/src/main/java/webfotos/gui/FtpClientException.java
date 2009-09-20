package webfotos.gui;

/**
 * Esta classe checa uma exce��o no FtpClient.
 * Herda a classe {@link java.lang.Exception Exception}.
 * Envia dados para classe base atrav�s do construtor.
 */
public class FtpClientException extends Exception {

    /**
     * Esse m�todo apenas repassa a informa��o.
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
