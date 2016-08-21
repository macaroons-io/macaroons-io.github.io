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

var m;

$("#btnCreate").click(function (event) {
  var location = $("#txtLocation").val();
  var identifier = $("#txtIdentifier").val();
  var secret = $("#txtSecret").val();
  m = com.github.nitram509.jmacaroons.MacaroonsBuilder.create(location, secret, identifier);
  $('#txtInspect').val(m.inspect());
});

$("#btnSerialize").click(function (event) {
  if (m) {
    $('#txtSerialized').val(m.serialize());
  }
});

$("#btnDeSerialize").click(function (event) {
  var serialized = $('#txtSerialized').val();
  m = com.github.nitram509.jmacaroons.MacaroonsBuilder.deserialize(serialized);
  $('#txtInspect').val(m.inspect());
});

$("#btnAddCaveat").click(function (event) {
  if (m) {
    var caveat = $("#txtCaveat").val();
    var secret = $("#txtSecret").val();
    var mb = com.github.nitram509.jmacaroons.MacaroonsBuilder.modify(m, secret);
    mb.add_first_party_caveat(caveat);
    m = mb.getMacaroon();
    $('#txtInspect').val(m.inspect());
  }
});

$("#btnVerify").click(function (event) {
  if (m) {
    var secret = $("#txtSecret").val();
    var v = new com.github.nitram509.jmacaroons.MacaroonsVerifier(m);
    alert("Valid = " + v.isValid(secret));
  }
});
