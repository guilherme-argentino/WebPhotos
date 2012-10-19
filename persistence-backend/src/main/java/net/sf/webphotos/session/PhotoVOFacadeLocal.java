/*
 * Copyright 2012 Guilherme.
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
package net.sf.webphotos.session;

import java.util.List;
import javax.ejb.Local;
import net.sf.webphotos.model.PhotoVO;

/**
 *
 * @author Guilherme
 */
@Local
public interface PhotoVOFacadeLocal {

    void create(PhotoVO photoVO);

    void edit(PhotoVO photoVO);

    void remove(PhotoVO photoVO);

    PhotoVO find(Object id);

    List<PhotoVO> findAll();

    List<PhotoVO> findRange(int[] range);

    int count();
    
}
