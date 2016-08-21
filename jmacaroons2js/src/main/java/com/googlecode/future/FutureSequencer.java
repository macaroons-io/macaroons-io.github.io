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
 * A FutureResult that evaluates its dependent results in the order requested and
 * sets true when all are completed.   FutureSequencer differs from {@link FutureSynchronizer}
 * in that a failure of one of the dependent results will cause FutureSequence to also fail.
 * 
 * @author Dean Povey
 *
 */
public class FutureSequencer extends FutureAction<Boolean> {
   
    private List<Future<?>> resultsToSequence;
    
    public FutureSequencer(Future<?>...resultsToSequence) {
        this.resultsToSequence = asList(resultsToSequence);
    }

    public FutureSequencer(
            Collection<? extends Future<?>> resultsToSynchronizeWith) {
        this.resultsToSequence = new ArrayList<Future<?>>(resultsToSynchronizeWith);
    }

    public void run() {
        for (Future<?> result : resultsToSequence) {
            result.result();
        }
        returnResult(true);
    }

}
