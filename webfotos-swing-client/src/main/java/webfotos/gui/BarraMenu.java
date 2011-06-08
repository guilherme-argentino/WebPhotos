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
