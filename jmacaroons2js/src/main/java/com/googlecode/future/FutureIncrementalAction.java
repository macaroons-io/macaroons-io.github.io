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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;

/**
 * Future which wraps GWT's IncrementalCommand to protect against slow script warnings.
 * 
 * @author dpovey
 *
 * @param <T> Type of result to return.
 */
public abstract class FutureIncrementalAction<T> extends FutureAction<T> {
    
    public FutureIncrementalAction() { }

    public FutureIncrementalAction(String name) {
        super(name);        
    }

    @Override    
    public T result() {
        if (GWT.isClient()) {            
            if (isComplete()) return super.result();
            if (keepCallingRun()) {
                // We call run repeatedly until we either encounter an unresolved
                // dependency or we have a result set.
                DeferredCommand.addCommand(new IncrementalCommand() {                    
                    public boolean execute() {                        
                        trySuperResult();                    
                        return keepCallingRun(); 
                    }
                });               
            }            
            setStarted(true);
            throw new IncompleteResultException(this, "Deferred execution for " + this.getName());
        }
        
        // Emulate to allow use in non GWT unit tests.
        do { 
            trySuperResult(); 
        } while(keepCallingRun()) ;
        return super.result();
    }
    
    private boolean keepCallingRun() {
        return !isComplete() && !hasUnresolvedDependencies();
    }

    @Override
    protected boolean recallRunOnResultRequested() {
        return true;
    }
    
    private void trySuperResult() {        
        try {
            super.result();
        } catch (Throwable t) { /* Squash */ }
    }    
}
