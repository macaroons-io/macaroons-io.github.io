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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * A Future that returns true when one or more other results have values. If one
 * or more of the synchronized results is cancelled then the FutureSynchronizer will be
 * cancelled, however if one or more results fails with an exception then that exception
 * will not be propagated to the FutureSynchronizer instance and it will still succeed.
 * 
 * @author Dean Povey
 *
 */
public class FutureSynchronizer extends FutureAction<Boolean> {
   
    private List<Future<?>> resultsToSynchronizeWith;
    
    public FutureSynchronizer(Future<?>...resultsToSynchronizeWith) {
        this.resultsToSynchronizeWith = asList(resultsToSynchronizeWith);
    }

    public FutureSynchronizer(
            Collection<? extends Future<?>> resultsToSynchronizeWith) {
        this.resultsToSynchronizeWith = new ArrayList<Future<?>>(resultsToSynchronizeWith);
    }

    public void run() {
        for (Future<?> result : resultsToSynchronizeWith) {
            result.start();
        }
        
        for (Future<?> result : resultsToSynchronizeWith) {                
            if (!result.isComplete()) {
                try {
                    result.result();
                } catch(IncompleteResultException e) {
                    throw e;
                } catch(CancelledException e) {
                    throw e;
                } catch(Throwable t) {
                    assert result.isFailure();
                    // Squash
                }
            }
        }
        returnResult(true);
    }

}
