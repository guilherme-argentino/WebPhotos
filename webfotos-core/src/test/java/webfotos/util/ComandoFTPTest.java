/*
 * ComandoFTPTest.java
 * JUnit based test
 *
 * Created on 17 de Maio de 2007, 10:48
 */

package webfotos.util;

import junit.framework.*;

/**
 *
 * @author guilherme
 */
public class ComandoFTPTest extends MassaTesteClassesFTP {
    
    public ComandoFTPTest(String testName) {
        super(testName);
    }

    /**
     * Teste do método equals, da classe webfotos.util.ComandoFTP.
     */
    public void testEquals() {
        System.out.println("equals");
        
        for(int i = 0; i < comandosCertos.length; i++) {
            ComandoFTP teste1 = new ComandoFTP(comandosCertos[i][0], comandosCertos[i][1], comandosCertos[i][2]);
            ComandoFTP teste2 = new ComandoFTP(comandosCertos[i][0], comandosCertos[i][1], comandosCertos[i][2]);
            assertTrue(teste1.equals(teste2));
        }
        
    }

    /**
     * Teste do método recebe, da classe webfotos.util.ComandoFTP.
     */
    public void testRecebe() {
        System.out.println("recebe");
        
        ComandoFTP teste1 = null, teste2 = null;
        
        for(int i = 0; i < comandosRedundantes.length; i++) {
            boolean passou = false, naoPassou = false;
            teste1 = new ComandoFTP(comandosRedundantes[i][0], comandosRedundantes[i][1], comandosRedundantes[i][2]);
            for(int j = 0; i < comandosCertos.length; i++) {
                teste2 = new ComandoFTP(comandosCertos[j][0], comandosCertos[j][1], comandosCertos[j][2]);
                if(teste2.recebe(teste1)) {
                    passou = true;
                    break;
                }
                if(teste1.recebe(teste2)) {
                    naoPassou = true;
                    break;
                }
            }
            assertTrue("Ignorou operações válidas", passou);
            assertTrue("O inverso foi aceito: " + teste2.toString() + " recebeu " + teste1.toString(), passou);
        }
    }
}
