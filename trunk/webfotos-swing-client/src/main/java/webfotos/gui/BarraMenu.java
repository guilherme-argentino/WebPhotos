package webfotos.gui;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import webfotos.acao.*;

public class BarraMenu extends JMenuBar {

    public BarraMenu() {

        JMenu mnArquivo = new JMenu("Arquivo");
        JMenu mnEditar = new JMenu("Editar");
        JMenu mnFerramentas = new JMenu("Ferramentas");

        this.add(mnArquivo);
        this.add(mnEditar);
        this.add(mnFerramentas);

        // Arquivo
        JMenuItem mnArquivoNovoAlbum = new JMenuItem("novo álbum");
        JMenuItem mnArquivoSair = new JMenuItem("sair");
        mnArquivo.add(mnArquivoNovoAlbum);
        mnArquivo.add(mnArquivoSair);

        // Editar
        JMenuItem mnEditarExportarAlbum = new JMenuItem("exportar álbum");
        JMenuItem mnEditarExcluirAlbum = new JMenuItem("excluir álbum (del)");

        mnEditar.add(mnEditarExportarAlbum);
        mnEditar.add(mnEditarExcluirAlbum);


        // Ferramentas
        JMenuItem mnFerramentasExportar = new JMenuItem("exportar banco de dados");
        JMenuItem mnFerramentasTestar = new JMenuItem("testar marca d'agua");
        JMenuItem mnFerramentasFTP = new JMenuItem("testar conexão FTP");
        mnFerramentas.add(mnFerramentasExportar);
        mnFerramentas.add(mnFerramentasTestar);
        mnFerramentas.add(mnFerramentasFTP);
    }
}
