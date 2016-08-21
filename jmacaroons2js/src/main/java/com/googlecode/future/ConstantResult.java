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

package com.googlecode.future;

/**
 * Result of an operation that is constant but needs to be accessed as a FutureResult.
 * 
 * @author Dean Povey
 *
 * @param <T> Type of result
 */
public class ConstantResult<T> extends FutureResult<T> implements AutoFuture<T> {
     
    /**
     * Create a {@link ConstantResult} with the given value.
     * 
     * @param value value to set the result to.
     */
    public ConstantResult(String name, T value) {
        super(name);
        setResult(value);
    }
    
    /**
     * Create a {@link ConstantResult} with the given value.
     * 
     * @param value value to set the result to.
     */
    public ConstantResult(T value) {
        setResult(value);
    }       
    
    /**
     * Convenience factory method that can be used to create a {@link ConstantResult}
     * 
     * @param <T> Type of result
     * @param value value to set the result to
     * @return a ConstantResult containing the specified value.
     */
    public static <T> ConstantResult<T> constant(T value) {
        return new ConstantResult<T>(value); 
    }  
    
    /**
     * Convenience factory method that can be used to create a {@link ConstantResult}
     * 
     * @param <T> Type of result
     * @param value value to set the result to
     * @return a ConstantResult containing the specified value.
     */
    public static <T> ConstantResult<T> constant(String name, T value) {
        return new ConstantResult<T>(name, value); 
    }

    @Override
    protected String getFutureType() {
        return "ConstantResult";        
    }
    
    

}
