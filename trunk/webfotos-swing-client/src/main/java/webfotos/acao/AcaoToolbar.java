package webfotos.acao;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import org.apache.log4j.Logger;
import webfotos.gui.FtpClient;
import webfotos.gui.PainelWebFotos;


/**
 * Identifica uma ação obtida ao clique de um dos botões da barra de ferramentas localizada no canto inferior direito do painel principal.
 * Na classe {@link webfotos.gui.BotaoIcone BotaoIcone}, onde será realizada uma instância de AcaoToolbar, será transmitido o evento para posteriormente implementar a ação do botão nesta classe.
 */
public class AcaoToolbar extends AbstractAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5232557211589840659L;
	
	private Logger log = Logger.getLogger(this.getClass());
	
    /**
     * Construtor da classe.
     * Inicialmente vazio, pois a classe não possui atributos.
     */
    public AcaoToolbar() {
    }

    /**
     * Armazena o nome setado em ActionCommand numa String.
     * Executa testes para checar qual o comando desejado.
     * Caso clique no primeiro botão, inicia uma thread de FtpClient.
     * Se for um dos botões do meio, executa o método {@link webfotos.gui.PainelWebFotos#marcaAlbunsFTP(int) marcaAlbunsFTP(int comando)} e marca para download ou upload.
     * E caso seja o último botão, adiciona fotos.
     * @param e Evento da ação
     */
    public void actionPerformed(ActionEvent e) {
        String comando=e.getActionCommand();

        if(comando.equals("ftp")) {
            Thread t=new Thread(new FtpClient());
            t.start();
        } else if(comando.equals("down")) {
            PainelWebFotos.marcaAlbunsFTP(2);
        } else if(comando.equals("up")) {
            PainelWebFotos.marcaAlbunsFTP(1);
        } else if(comando.equals("camera")) {
            log.debug ("adicionar fotos");
        }		
    }

}
