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

import java.util.Collection;

/**
 * Exception thrown when calling get on a {@link FutureResult} or {@link FutureAction} that is not
 * complete.  The FutureResult which is not complete is included in the exception. If the result is
 * not available due to some unresolved dependency on another FutureResult then the nested cause
 * may contain another IncompleteResultException.
 * 
 * @author Dean Povey
 *
 */
public class IncompleteResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private final Future<?> future;
    
    public IncompleteResultException(Future<?> future, String message) {
        super(message);
        this.future = future;
    }
    
    public IncompleteResultException(Future<?> future) {
        this.future = future;
    }

    public IncompleteResultException(Future<?> future, Throwable cause) {
        this(future);
        initCause(cause);
    }
    
    public IncompleteResultException(Future<?> future, String message, Throwable cause) {     
        this(future, message);
        initCause(cause);
    }

    public Future<?> getFuture() {
        return future;
    }    
}
