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
package net.sf.webphotos.entity;

import java.util.Comparator;

/**
 *
 * @author Guilherme
 */
public interface IsPhoto extends Comparable<IsPhoto> {
    
    public String getKey();

    public IsCredits getCreditos();
    
    public String getLegenda();
    
    public static final Comparator<IsPhoto> BY_KEY_ASCENDING = new Comparator<IsPhoto>() {

        @Override
        public int compare(IsPhoto photoOne, IsPhoto photoTwo) {
            return photoOne.getKey().compareTo(photoTwo.getKey());
        }
        
    };
    
    public static final Comparator<IsPhoto> BY_KEY_DESCENDING = new Comparator<IsPhoto>() {

        @Override
        public int compare(IsPhoto photoOne, IsPhoto photoTwo) {
            return photoTwo.getKey().compareTo(photoOne.getKey());
        }
        
    };
    
}
