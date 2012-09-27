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

import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Guilherme
 */
public class CacheFTPTest extends MassaTesteClassesFTP {
    
    private static Logger log = Logger.getLogger(CacheFTPTest.class);
    private CacheFTP instancia;

    private Iterator iListaArq;
    private ComandoFTP cmdFTPAtual, cmdFTPNovo;
    private boolean achou;

    /**
     * {@inheritDoc}
     */
    public CacheFTPTest(String testName) {
        super(testName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUp() throws Exception {
        instancia = CacheFTP.getCache();
        super.setUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tearDown() throws Exception {
        instancia.clear();
        instancia.saveFile();
    }

    /**
     * Teste do método addCommand, da classe webfotos.util.CacheFTP.
     * Verifica se todos e apenas comandos válidos são inseridos.
     */
    public void testAddCommand() {
        log.info("addCommand");

        instancia.clear();

        /** 
         * Fase 1: tenta colocar primeiro os comandos reduntantes e depois 
         * sobrepõe as com os comandos genéricos (foto = 0).
         * testa em conjunto se comandos invalidos são inseridos
         */
        this.carregaComandos(comandosRedundantes);
        this.carregaComandos(comandosCertos);
        this.carregaComandos(comandosErrados);
        
        this.verificaPresenca();
        this.verificaAusencia();

        instancia.clear();

        /** 
         * Fase 2: tenta colocar primeiro os comandos certos e depois sobrepor
         * com as redundancias. Procurando saber se a sobreposição está errada.
         */
        this.carregaComandos(comandosCertos);
        this.carregaComandos(comandosRedundantes);
        
        this.verificaPresenca();
        this.verificaAusencia();
    }

    /**
     * Teste do método add, da classe webfotos.util.CacheFTP.
     */
    public void testAdd() {
        log.info("add");
        
        instancia.clear();

        /** Tenta acrescentar todos os comandos certos */
        for(int i = 0; i < comandosCertos.length; i++) {
            instancia.add(new ComandoFTP(comandosCertos[i][0], comandosCertos[i][1], comandosCertos[i][2]));
        }
        
        /** Tenta acrescentar todos os comandos errados */
        for(int i = 0; i < comandosErrados.length; i++) {
            instancia.add(new ComandoFTP(comandosCertos[i][0], comandosCertos[i][1], comandosCertos[i][2]));
        }
        
        this.verificaPresenca();
        this.verificaAusencia();
    }

    /**
     * Procura na lista e verifica se todos os comandos certos foram inseridos
     */
    private void verificaPresenca () {
        for(int i = 0; i < comandosCertos.length; i++) {
            iListaArq = instancia.iterator();
            achou = false;
            cmdFTPNovo = new ComandoFTP(comandosCertos[i][0], comandosCertos[i][1], comandosCertos[i][2]);
            while(iListaArq.hasNext()) {
                cmdFTPAtual = (ComandoFTP) iListaArq.next();
                achou = cmdFTPAtual.equals(cmdFTPNovo);
                if(achou) {
                    break;
                }
            }
            assertTrue("Não encontrou o Comando: " + cmdFTPNovo.toString(), achou);
        }
    }
    
    /**
     * Procura na lista e verifica se algum comando invalido foi inserido
     */
    private void verificaAusencia() {
        for(int i = 0; i < comandosErrados.length; i++) {
            iListaArq = instancia.iterator();
            achou = false;
            cmdFTPNovo = new ComandoFTP(comandosErrados[i][0], comandosErrados[i][1], comandosErrados[i][2]);
            while(iListaArq.hasNext()) {
                cmdFTPAtual = (ComandoFTP) iListaArq.next();
                achou = cmdFTPAtual.equals(cmdFTPNovo);
                if(achou) {
                    break;
                }
            }
            assertFalse("Encontrou o Comando Invalido: " + cmdFTPNovo.toString(), achou);
        }
    }
    
    /**
     * Tenta acrescentar todos os comandos da lista
     * @param arrayComandos Lista de comandos para execucao
     */
    private void carregaComandos(int[][] arrayComandos) {
        
        for(int i = 0; i < arrayComandos.length; i++) {
            instancia.addCommand(arrayComandos[i][0], arrayComandos[i][1], arrayComandos[i][2]);
        }
        
    }
}
