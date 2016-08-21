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

package com.github.nitram509.jmacaroons.util;

import java.io.UnsupportedEncodingException;

import static com.github.nitram509.jmacaroons.MacaroonsConstants.IDENTIFIER_CHARSET;

public class StringUtil {

  public static byte[] getBytes(String s) {
    if (s == null) {
      return new byte[0];
    }
    try {
      return s.getBytes(IDENTIFIER_CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String asString(byte[] bytes) {
    try {
      return new String(bytes, IDENTIFIER_CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String asString(byte[] bytes, int offset, int length) {
    try {
      return new String(bytes, offset, length, IDENTIFIER_CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

  }
}
