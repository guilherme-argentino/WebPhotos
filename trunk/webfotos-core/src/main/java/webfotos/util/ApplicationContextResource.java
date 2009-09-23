/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package webfotos.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Guilhe
 */
public class ApplicationContextResource {

    private static ApplicationContext applicationContext;

    static {
        String[] configLocations = {"classpath*:applicationContext.xml", "applicationContext.xml"};
        applicationContext = new ClassPathXmlApplicationContext(configLocations);
    }

    private ApplicationContextResource() {

    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

}
