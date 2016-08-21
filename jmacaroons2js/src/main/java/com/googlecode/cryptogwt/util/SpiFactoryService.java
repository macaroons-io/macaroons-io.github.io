/*
 * Copyright 2016 Martin W. Kirst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.cryptogwt.util;


import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.List;
import java.util.Map;

/**
 * Service that supports creation of Service Provider Interfaces using an SpiFactory instance.
 *
 */
public class SpiFactoryService extends Provider.Service {

    private com.googlecode.cryptogwt.util.SpiFactory<?> factory;
    
    public SpiFactoryService(Provider provider, String type, String algorithm, 
            String className, List<String> aliases, Map<String, String> attributes,
            com.googlecode.cryptogwt.util.SpiFactory<?> factory) {
        super(provider, type, algorithm, className, aliases, attributes);
        this.factory = factory;
    }

    @Override
    public Object newInstance(Object constructorParameter)
            throws NoSuchAlgorithmException {
        return factory.create(constructorParameter);
    }    
}
