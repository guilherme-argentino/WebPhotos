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
