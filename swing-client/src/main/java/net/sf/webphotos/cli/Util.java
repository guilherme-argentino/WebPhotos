/*
 * Copyright 2015 Guilherme.
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
package net.sf.webphotos.cli;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme
 */
public final class Util {

    /**
     * rebuild directory structure for default webphotos data
     *
     * @param diretorioAdicionarFotosParm the value of
     * diretorioAdicionarFotosParm
     */
    public static void recreateDirs(String diretorioAdicionarFotosParm) {
        File diretorioAdicionarFotos = new File(net.sf.webphotos.util.Util.getProperty(diretorioAdicionarFotosParm));
        if (diretorioAdicionarFotos.exists()) {
            try {
                delete(diretorioAdicionarFotos);
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        diretorioAdicionarFotos.mkdirs();
    }

    public static void delete(File file) throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    private Util() {
    }

    /**
     * TODO: organize all cli utilities here
     * @param args 
     */
    public static void main(String[] args) {
        System.getProperties().list(System.out);
        net.sf.webphotos.cli.Util.recreateDirs("diretorioAdicionarFotos");
        net.sf.webphotos.cli.Util.recreateDirs("albunsRoot");
    }
}
