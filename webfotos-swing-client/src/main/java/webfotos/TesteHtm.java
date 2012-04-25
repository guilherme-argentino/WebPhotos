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
package webfotos;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Container;
import java.io.*;
import java.net.URL;
import javax.swing.filechooser.FileFilter;

import javax.swing.event.*;
import net.sf.webphotos.util.Util;

public class TesteHtm {
    public static void main(String[] arg) {
        JFrame tela=new TelaTesteHtm();	
    }
}

class TelaTesteHtm extends JFrame {
    public static final long serialVersionUID = 123L;
    private JTextField txtURL;
    private JEditorPane editor;

    public TelaTesteHtm() {
        super("Teste de leitura HTML");
        this.setSize(800,600);

        Container cp=this.getContentPane();	
        cp.setLayout(null);

        editor=new JEditorPane();
        editor.setEditable(false);
        editor.addHyperlinkListener(new MyHyperlinkListener());
        JScrollPane scrEditor=new JScrollPane(editor);
        scrEditor.setBounds(5,5,780,535);
        cp.add(scrEditor);

        JButton btAbrir=new JButton("abrir");
        btAbrir.setBounds(680,545,100,25);
        cp.add(btAbrir);

        txtURL=new JTextField();
        txtURL.setBounds(5,545,650,25);
        cp.add(txtURL);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);		

        setVisible(true);

        // ouvinte do botao
        btAbrir.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                carregarArquivo();					
            }
        });
    }

    private void carregarArquivo() {
        // primeiro ve se o usuário não digitou na caixa
        if(txtURL.getText().length() > 0) {
            // verifica se comeca com www
            Util.out.println ("acessando externo");
            if(! txtURL.getText().startsWith("www")) {
                JOptionPane.showMessageDialog(null,"endereço deve começar com www...",
                        "Errado",JOptionPane.OK_OPTION);
                txtURL.setText("");
                txtURL.requestFocus(true);
                return;
            }
            try {
                editor.setPage("http://" + txtURL.getText());
                return;
            } catch (Exception e) {
                Util.out.println ("Erro:" + e.toString());
            }

        }

        JFileChooser fc=new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new Filtro());
        fc.setDialogTitle("selecione o arquivo htm");
        fc.setApproveButtonText("Ok");
        int retornoFc=fc.showOpenDialog(null);

        if(retornoFc==JFileChooser.APPROVE_OPTION) {
            try {
                editor.setPage(new URL("file:" + fc.getSelectedFile()));
            } catch (Exception e) {
                Util.out.println ("Erro:" + e.toString());
            }
        }
    }
}


class Filtro extends FileFilter {
	String ext;
    // todos os diretorios e jpg
    public boolean accept(File f) {
        if (f.isDirectory()) return true;

		String nmArquivo=f.getName();
		int pos=nmArquivo.lastIndexOf(".");
		if(pos > 0 && pos < nmArquivo.length()-1)
			ext=nmArquivo.substring(pos+1).toLowerCase();
		
		if(ext.equals("htm") || ext.equals("html")) return true;
		
		return false;	

    }

    // descricao
    public String getDescription() {
        return "Imagens jpg";
    }
}

class MyHyperlinkListener implements HyperlinkListener {
    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane)evt.getSource();
            try {
                // Show the new page in the editor pane.
                pane.setPage(evt.getURL());
            } catch (IOException e) {
            }
        }
    }
}
