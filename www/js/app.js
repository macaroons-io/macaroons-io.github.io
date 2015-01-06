var app = {};

(function () {
  "use strict";

  app.build = {};
  app.build.macaroon = m.prop(null);

  app.controller = function () {
    app.vm.init();
  };

  app.view = function (ctrl) {
    var vm = app.vm;
    return [
      m("td", [
        m("input[type=text]", {
          placeholder: 'http://www.example.org',
          onchange: app.vm.updateAndMacaroon.bind(app.vm, m.withAttr("value", app.vm.location)),
          value: app.vm.location()
        })]),
      m("td", [
        m("input[type=text]", {
          placeholder: 'public identifier 0815',
          onchange: app.vm.updateAndMacaroon.bind(app.vm, m.withAttr("value", app.vm.identifier)),
          value: app.vm.identifier()
        })]),
      m("td", [
        m("input[type=text]", {
          placeholder: 'your private secret',
          onchange: app.vm.updateAndMacaroon.bind(app.vm, m.withAttr("value", app.vm.secret)),
          value: app.vm.secret()
        })
      ])
    ];
  };

  app.vm = (function () {
    var vm = {};
    vm.updateMacaroon = function() {
      if (vm.identifier() && vm.secret() && vm.location()) {
        var macaroon = com.github.nitram509.jmacaroons.MacaroonsBuilder.create(vm.location(), vm.secret(), vm.identifier());
        app.build.macaroon(macaroon);
      }
    };
    vm.init = function () {
      vm.location = m.prop(null);
      vm.identifier = m.prop(null);
      vm.secret = m.prop(null);
      vm.updateAndMacaroon = function (propertyBindingFn) {
        propertyBindingFn();
        vm.updateMacaroon();
      }
    };
    return vm;
  }());

  var buildinput = document.getElementById("build-input");

  m.module(buildinput, {controller: app.controller, view: app.view});

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