package webfotos.gui;


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
}
