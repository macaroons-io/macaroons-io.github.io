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

package com.github.nitram509.jmacaroons.gwt;

import com.google.gwt.core.client.EntryPoint;
import org.timepedia.exporter.client.ExporterUtil;

public class MacaroonModuleEntryPoint implements EntryPoint {

  public void onModuleLoad() {
    ExporterUtil.exportAll();
    onJMacaroonLoaded();
  }

  private native void onJMacaroonLoaded() /*-{
    if ($wnd.onJMacaroonLoaded) $wnd.onJMacaroonLoaded();
  }-*/;

}