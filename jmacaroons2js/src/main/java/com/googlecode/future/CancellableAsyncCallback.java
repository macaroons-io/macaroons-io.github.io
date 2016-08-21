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
 * Callback that supports cancelling.
 * 
 * @author Dean Povey
 *
 * @param <T>
 */
public interface CancellableAsyncCallback<T> extends AsyncCallback<T> {
    /**
     * Called when an asynchronous call is cancelled.
     */
    void onCancel();
}
