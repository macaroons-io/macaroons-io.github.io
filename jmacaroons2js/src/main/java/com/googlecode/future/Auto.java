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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Converts any future to an AutoFuture, ie. a future that is always evaluated.
 * 
 * @author Dean Povey
 *
 * @param <T>
 */
public class Auto<T> implements AutoFuture<T> {
    
    private Future<T> future;

    public static <T> AutoFuture<T> auto(Future<T> future) {
        return new Auto<T>(future);
    }
    
    private Auto(Future<T> future) {
        this.future = future;
        future.start();
    }

    public void cancel() {
        future.cancel();
    }

    public void start() {
        future.start();
    }
    
    public void eval() {
        start();
    }

    public T result() throws IncompleteResultException, ExecutionException {
        return future.result();
    }

    public void addCallback(AsyncCallback<T> callback) {
        future.addCallback(callback);
    }

    public Throwable exception() {
        return future.exception();
    }

    public boolean isCancelled() {
        return future.isCancelled();
    }

    public boolean isComplete() {
        return future.isComplete();
    }

    public boolean isFailure() {
        return future.isFailure();
    }

    public boolean isSuccessful() {
        return future.isSuccessful();
    }

    public void setResult(T value) {
        future.setResult(value);
    }

    public void setEmpty() {
        future.setEmpty();
    }
    
    public void failWithException(Throwable t) {
        future.failWithException(t);
    }

    public CancellableAsyncCallback<T> callback() {
        return future.callback();
    }

    public void setName(String name) {
        this.future.setName(name);
    }
    
    @Override
    public String toString() {
        return this.future.toString(); 
    }

    public String getName() {
        return "<Auto>" + this.future.getName();
    }
}
