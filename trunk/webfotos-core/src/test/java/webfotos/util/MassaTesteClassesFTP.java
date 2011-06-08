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
package webfotos.util;

import junit.framework.TestCase;
import org.junit.Before;

/**
 * Classe criada para alimentar de dados suas subclasses. Para usá-la, é necessário
 * extendê-la no lugar da classe TestCase para, além de alcançar a classe TestCase,
 * ter a disposição os dados nela contidos.
 * @author guilherme
 */
public abstract class MassaTesteClassesFTP extends TestCase {
    
    /**
     * Array contendo todos os comandos invalidos, previstos na aplicação.
     */
    protected int[][] comandosErrados;
    
    /**
     * Comandos que podem ser inseridos na aplicação e devem estar no resultado final.
     */
    protected int[][] comandosCertos;
    
    /**
     * Array de comandos válidos, mas redundantes. tendo sua versão original
     * contemplada no no array <I>comandosCertos</I>.
     */
    protected int[][] comandosRedundantes;
    
    /**
     * Cria uma nova instância de MassaTesteClassesFTP.
     * @param testName Nome do teste realizado
     */
    public MassaTesteClassesFTP(String testName) {
        super(testName);
        comandosErrados = new int[3][3];
        comandosCertos = new int[9][3];
        comandosRedundantes = new int[2][3];
    }
    
    /**
     * Alimenta os arrays com seus dados antes de realizar os testes.
     * @throws java.lang.Exception Lança para o programa de teste tratar todas as excessões nessa etapa.
     */
    @Before
    protected void setUp() throws Exception {
        
        /** Alimentando os comandos errados */
        comandosErrados[0][0] = 0;
        comandosErrados[0][1] = 0;
        comandosErrados[0][2] = 0;
        
        comandosErrados[1][0] = -1;
        comandosErrados[1][1] = -2;
        comandosErrados[1][2] = -3;

        comandosErrados[2][0] = 4;
        comandosErrados[2][1] = 5;
        comandosErrados[2][2] = 6;

        /** Alimentando os comandos certos */
        comandosCertos[0][0] = 1;
        comandosCertos[0][1] = 1;
        comandosCertos[0][2] = 1;
        
        comandosCertos[1][0] = 1;
        comandosCertos[1][1] = 34;
        comandosCertos[1][2] = 42;

        comandosCertos[2][0] = 2;
        comandosCertos[2][1] = 34;
        comandosCertos[2][2] = 23;

        comandosCertos[3][0] = 3;
        comandosCertos[3][1] = 34;
        comandosCertos[3][2] = 45;
        
        comandosCertos[4][0] = 1;
        comandosCertos[4][1] = 32;
        comandosCertos[4][2] = 423;

        comandosCertos[5][0] = 2;
        comandosCertos[5][1] = 34;
        comandosCertos[5][2] = 24;

        comandosCertos[6][0] = 1;
        comandosCertos[6][1] = 34;
        comandosCertos[6][2] = 23;
        
        comandosCertos[7][0] = 2;
        comandosCertos[7][1] = 33;
        comandosCertos[7][2] = 0;

        comandosCertos[8][0] = 1;
        comandosCertos[8][1] = 35;
        comandosCertos[8][2] = 0;

        /** Alimentando os comandos redundantes */
        comandosRedundantes[0][0] = 2;
        comandosRedundantes[0][1] = 33;
        comandosRedundantes[0][2] = 223;

        comandosRedundantes[1][0] = 1;
        comandosRedundantes[1][1] = 35;
        comandosRedundantes[1][2] = 4;
    }
}
