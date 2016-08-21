/*
 * Copyright 2014 Martin W. Kirst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var app = {};

(function () {
  "use strict";

  //app.build = {};
  //app.build.macaroon = m.prop(null);

  app.controller = function () {
    app.vm.init();
  };

  app.view = {};
  app.view.build = function (ctrl) {
    var vm = app.vm;
    return [
      m("a", {name: "build"}, [
        m("h1", "Build")
      ]),
      m("h5", "Basic input data"),
      m("div", [
        m("table", {"border": "0", "cellpadding": "0", "cellspacing": "0"}, [
          m("tr", [
            m("th", {"class": "small"}, "Location"),
            m("th", {"class": "small"}, "Identifier"),
            m("th", {"class": "small"}, "Secret")
          ]),
          m("tr", [
            m("td", [
              m("input[type=text]", {
                placeholder: 'http://www.example.org',
                onchange: vm.updateAndMacaroon.bind(vm, m.withAttr("value", vm.location)),
                value: vm.location()
              })]),
            m("td", [
              m("input[type=text]", {
                placeholder: 'public identifier 0815',
                onchange: vm.updateAndMacaroon.bind(vm, m.withAttr("value", vm.identifier)),
                value: vm.identifier()
              })]),
            m("td", [
              m("input[type=text]", {
                placeholder: 'your private secret',
                onchange: vm.updateAndMacaroon.bind(vm, m.withAttr("value", vm.secret)),
                value: vm.secret()
              })
            ])
          ]),
          m("tr", [
            m("td", {"colspan": "2"}),
            m("td", {"colspan": "1", "style": "text-align:left"}, [
              m("button", {
                onclick: vm.updateMacaroon.bind(vm),
                type: "button",
                disabled: (vm.btn_create_disabled() ? "disabled" : null)
              }, "Create")
            ])
          ])
        ])
      ]),
      m("h5", "Macaroon technical details"),
      m("div", [
        m("textarea", {"style": "width: 100%", "rows": "10", "readonly": "readonly"}, vm.macaroon_details())
      ]),
      m("h5", "Macaroon serialized"),
      m("div", [
        m("textarea", {"style": "width: 100%", "rows": "2", "readonly": "readonly", onchange:m.withAttr("value", vm.macaroon_serialized)}, vm.macaroon_serialized()),
        m("button", {
          "data-clipboard-text": "",
          "title": "Click to copy me.",
          "style": "display: none;",
          "rows": "10",
          "readonly": "readonly"
        }, "Copy to Clipboard")
      ])
    ];
  };

  app.view.verify = function (ctrl) {
    var vm = app.vm.verify;
    return [
      m("a", {name: "verify"}, [
        m("h1", "Verify")
      ]),
      m("h5", "Input Macaroon"),
      m("div", [
        m("textarea", {
          "style": "width: 100%",
          "rows": "2",
          "placeholder": "Add your serialized macaroon here ..."
        }, vm.macaroon_serialized()),
        m("button", {onclick: vm.doDeSerialize}, "Deserialize")
      ]),
      m("h5", "Macaroon technical details"),
      m("div", [
        m("textarea", {"style": "width: 100%", "rows": "10", "readonly": "readonly"}, vm.macaroon_serialized()),
        m("input[type=text]", {
          placeholder: 'your private secret',
          onchange: m.withAttr("value", vm.secret),
          value: vm.secret()
        }),
        m("button", {onclick: vm.doVerify}, "Verify"),
        m("img", {
          src: "www/img/dwcheckyes.png",
          alt: "Verified OK",
          style: "position: relative; top:-75px; float: right; display: none"
        }),
        m("img", {
          src: "www/img/dwcheckno.png",
          alt: "Verified BAD",
          style: "position: relative; top:-75px; float: right; display: none"
        })
      ])
    ]
  };

  app.vm = (function () {
    var vm = {};
    vm.updateMacaroon = function () {
      var create = vm.identifier() && vm.secret() && vm.location();
      vm.btn_create_disabled(!create);
      if (create) {
        var macaroon = com.github.nitram509.jmacaroons.MacaroonsBuilder.create(vm.location(), vm.secret(), vm.identifier());
        vm.macaroon_details(macaroon.inspect());
        vm.macaroon_serialized(macaroon.serialize());
      }
    };
    vm.init = function () {
      vm.location = m.prop(null);
      vm.identifier = m.prop(null);
      vm.secret = m.prop(null);
      vm.macaroon_details = m.prop(null);
      vm.macaroon_serialized = m.prop(null);
      vm.btn_create_disabled = m.prop(true);
      vm.updateAndMacaroon = function (propertyBindingFn) {
        propertyBindingFn();
        vm.updateMacaroon();
      };

      vm.verify = {};
      vm.verify.secret = m.prop(null);
      vm.verify.macaroon_serialized = m.prop(null);
      vm.verify.macaroon_details = m.prop(null);
      vm.verify.doDeSerialize = function () {
        console.log("doDeSerialize");
      };
      vm.verify.doVerify = function () {
        console.log("verify");
      }
    };
    return vm;
  }());

  var view_build = document.getElementById("view-build");
  m.module(view_build, {controller: app.controller, view: app.view.build});

  var view_verify = document.getElementById("view-verify");
  m.module(view_verify, {controller: app.controller, view: app.view.verify});

  //var m;
  //var mv;
  //
  //function doCreateMacaroon() {
  //  var location = $("#txtLocation").val();
  //  var identifier = $("#txtIdentifier").val();
  //  var secret = $("#txtSecret").val();
  //  if (location && identifier && secret) {
  //    m = com.github.nitram509.jmacaroons.MacaroonsBuilder.create(location, secret, identifier);
  //    $('#txtDetails').text(m.inspect());
  //    $('#txtSerialized').text(m.serialize());
  //    $('#copy-serialized').attr('data-clipboard-text', m.serialize()).show("fast");
  //  }
  //}
  //
  //$("#btnCreate").click(function (event) {
  //  doCreateMacaroon();
  //});
  //
  //$("#btnAddCaveat").click(function (event) {
  //  if (m) {
  //    var caveat = $("#txtCaveat").val();
  //    var secret = $("#txtSecret").val();
  //    var mb = com.github.nitram509.jmacaroons.MacaroonsBuilder.modify(m, secret);
  //    mb.add_first_party_caveat(caveat);
  //    m = mb.getMacaroon();
  //    $('#txtInspect').text(m.inspect());
  //  }
  //});
  //
  //function doDeSerialize() {
  //  var serialized = $('#txtSerialized_verify').val();
  //  mv = com.github.nitram509.jmacaroons.MacaroonsBuilder.deserialize(serialized);
  //  $('#txtDetails_verify').text(mv.inspect());
  //}
  //
  //$("#btnDeSerialize").click(function (event) {
  //  doDeSerialize();
  //});
  //
  //$('#txtSerialized_verify').on('input propertychange', function (event) {
  //  doDeSerialize();
  //});
  //
  //function doVerifyAndUpdateUI() {
  //  if (mv) {
  //    var secret = $("#txtSecret_verify").val();
  //    var v = new com.github.nitram509.jmacaroons.MacaroonsVerifier(mv);
  //    if (v.isValid(secret)) {
  //      $("#imgVerified").show('fast');
  //      $("#imgNotOk").hide('fast');
  //    } else {
  //      $("#imgVerified").hide('fast');
  //      $("#imgNotOk").show('fast');
  //    }
  //  }
  //}
  //
  //$("#btnVerify").click(function (event) {
  //  doVerifyAndUpdateUI();
  //});
  //
  //$("#txtSecret_verify").on('input propertychange', function (event) {
  //  doVerifyAndUpdateUI();
  //});
  //
  //var serializedClipboard = new ZeroClipboard(document.getElementById("copy-serialized"));
  //serializedClipboard.on("ready", function (readyEvent) {
  //  /* nothing to do */
  //});
  //
  //function enableCreateButton(event) {
  //  var location = $("#txtLocation").val() || "";
  //  var identifier = $("#txtIdentifier").val() || "";
  //  var secret = $("#txtSecret").val() || "";
  //  if (location.length > 0 && identifier.length > 0 && secret.length > 0) {
  //    $('#btnCreate')
  //        .removeAttr('disabled')
  //        .attr('title', 'Create a macaroon');
  //  } else {
  //    $('#btnCreate')
  //        .attr('disabled', 'disabled')
  //        .attr('title', 'Please, fill all data fields first.');
  //  }
  //}
  //
  //$('#txtLocation,#txtIdentifier,#txtSecret').on('input propertychange', function (event) {
  //  enableCreateButton(event);
  //  doCreateMacaroon();
  //});

})();