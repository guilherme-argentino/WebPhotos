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
package net.sf.webphotos.util;

import java.util.Iterator;
import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author Guilherme
 */
public class UtilFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        Properties result = new Properties();
        Iterator iterator = Util.getConfig().getKeys();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            result.setProperty(key, Util.getConfig().getProperty(key).toString());
        }
        return result;
    }

    @Override
    public Class getObjectType() {
        return Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    
}
