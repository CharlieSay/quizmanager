/**
 * bootbox.js 5.0.0
 *
 * http://bootboxjs.com/license.txt
 */
! function (t, e) {
    'use strict';
    'function' == typeof define && define.amd ? define(['jquery'], e) : 'object' == typeof exports ? module.exports = e(require('jquery')) : t.bootbox = e(t.jQuery)
}(this, function e(p, u) {
    'use strict';
    var r, n, i, l;
    Object.keys || (Object.keys = (r = Object.prototype.hasOwnProperty, n = !{
        toString: null
    }.propertyIsEnumerable('toString'), l = (i = ['toString', 'toLocaleString', 'valueOf', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'constructor']).length, function (t) {
        if ('function' != typeof t && ('object' != typeof t || null === t)) throw new TypeError('Object.keys called on non-object');
        var e, o, a = [];
        for (e in t) r.call(t, e) && a.push(e);
        if (n)
            for (o = 0; o < l; o++) r.call(t, i[o]) && a.push(i[o]);
        return a
    }));
    var d = {};
    d.VERSION = '5.0.0';
    var b = {},
        f = {
            dialog: "<div class=\"bootbox modal\" tabindex=\"-1\" role=\"dialog\" aria-hidden=\"true\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-body\"><div class=\"bootbox-body\"></div></div></div></div></div>",
            header: "<div class=\"modal-header\"><h5 class=\"modal-title\"></h5></div>",
            footer: '<div class="modal-footer"></div>',
            closeButton: '<button type="button" class="bootbox-close-button close" aria-hidden="true">×</button>',
            form: '<form class="bootbox-form"></form>',
            button: '<button type="button" class="btn"></button>',
            option: '<option></option>',
            promptMessage: '<div class="bootbox-prompt-message"></div>',
            inputs: {
                text: '<input class="bootbox-input bootbox-input-text form-control" autocomplete="off" type="text" />',
                textarea: '<textarea class="bootbox-input bootbox-input-textarea form-control"></textarea>',
                email: '<input class="bootbox-input bootbox-input-email form-control" autocomplete="off" type="email" />',
                select: '<select class="bootbox-input bootbox-input-select form-control"></select>',
                checkbox: '<div class="form-check checkbox"><label class="form-check-label"><input class="form-check-input bootbox-input bootbox-input-checkbox" type="checkbox" /></label></div>',
                radio: '<div class="form-check radio"><label class="form-check-label"><input class="form-check-input bootbox-input bootbox-input-radio" type="radio" name="bootbox-radio" /></label></div>',
                date: '<input class="bootbox-input bootbox-input-date form-control" autocomplete="off" type="date" />',
                time: '<input class="bootbox-input bootbox-input-time form-control" autocomplete="off" type="time" />',
                number: '<input class="bootbox-input bootbox-input-number form-control" autocomplete="off" type="number" />',
                password: '<input class="bootbox-input bootbox-input-password form-control" autocomplete="off" type="password" />',
                range: '<input class="bootbox-input bootbox-input-range form-control-range" autocomplete="off" type="range" />'
            }
        },
        m = {
            locale: 'en',
            backdrop: 'static',
            animate: !0,
            className: null,
            closeButton: !0,
            show: !0,
            container: 'body',
            value: '',
            inputType: 'text',
            swapButtonOrder: !1,
            centerVertical: !1,
            multiple: !1
        };

    function c(t, e, o) {
        return p.extend(!0, {}, t, function (t, e) {
            var o = t.length,
                a = {};
            if (o < 1 || 2 < o) throw new Error('Invalid argument length');
            return 2 === o || 'string' == typeof t[0] ? (a[e[0]] = t[0], a[e[1]] = t[1]) : a = t[0], a
        }(e, o))
    }

    function h(t, e, o, a) {
        var r;
        a && a[0] && (r = a[0].locale || m.locale, (a[0].swapButtonOrder || m.swapButtonOrder) && (e = e.reverse()));
        var n, i, l, s = {
            className: 'bootbox-' + t,
            buttons: function (t, e) {
                for (var o = {}, a = 0, r = t.length; a < r; a++) {
                    var n = t[a],
                        i = n.toLowerCase(),
                        l = n.toUpperCase();
                    o[i] = {
                        label: (s = l, c = e, void 0, p = b[c], p ? p[s] : b.en[s])
                    }
                }
                var s, c, p;
                return o
            }(e, r)
        };
        return n = c(s, a, o), l = {}, w(i = e, function (t, e) {
            l[e] = !0
        }), w(n.buttons, function (t) {
            if (l[t] === u) throw new Error('button key "' + t + '" is not allowed (options are ' + i.join(' ') + ')')
        }), n
    }

    function v(t) {
        return Object.keys(t).length
    }

    function w(t, o) {
        var a = 0;
        p.each(t, function (t, e) {
            o(t, e, a++)
        })
    }

    function y(t, e, o) {
        t.stopPropagation(), t.preventDefault(), p.isFunction(o) && !1 === o.call(e, t) || e.modal('hide')
    }

    function g(t) {
        return /([01][0-9]|2[0-3]):[0-5][0-9]?:[0-5][0-9]/.test(t)
    }

    function x(t) {
        return /(\d{4})-(\d{2})-(\d{2})/.test(t)
    }
    return d.locales = function (t) {
        return t ? b[t] : b
    }, d.addLocale = function (t, o) {
        return p.each(['OK', 'CANCEL', 'CONFIRM'], function (t, e) {
            if (!o[e]) throw new Error('Please supply a translation for "' + e + '"')
        }), b[t] = {
            OK: o.OK,
            CANCEL: o.CANCEL,
            CONFIRM: o.CONFIRM
        }, d
    }, d.removeLocale = function (t) {
        if ('en' === t) throw new Error('"en" is used as the default and fallback locale and cannot be removed.');
        return delete b[t], d
    }, d.setLocale = function (t) {
        return d.setDefaults('locale', t)
    }, d.setDefaults = function () {
        var t = {};
        return 2 === arguments.length ? t[arguments[0]] = arguments[1] : t = arguments[0], p.extend(m, t), d
    }, d.hideAll = function () {
        return p('.bootbox').modal('hide'), d
    }, d.init = function (t) {
        return e(t || p)
    }, d.dialog = function (t) {
        if (p.fn.modal === u) throw new Error("\"$.fn.modal\" is not defined; please double check you have included the Bootstrap JavaScript library. See http://getbootstrap.com/javascript/ for more details.");
        if (t = function (r) {
            var n, i;
            if ('object' != typeof r) throw new Error('Please supply an object of options');
            if (!r.message) throw new Error('"message" option must not be null or an empty string.');
            (r = p.extend({}, m, r)).buttons || (r.buttons = {});
            return n = r.buttons, i = v(n), w(n, function (t, e, o) {
                if (p.isFunction(e) && (e = n[t] = {
                    callback: e
                }), 'object' !== p.type(e)) throw new Error('button with key "' + t + '" must be an object');
                if (e.label || (e.label = t), !e.className) {
                    var a = !1;
                    a = r.swapButtonOrder ? 0 === o : o === i - 1, e.className = i <= 2 && a ? 'btn-primary' : 'btn-secondary btn-default'
                }
            }), r
        }(t), p.fn.modal.Constructor.VERSION) {
            t.fullBootstrapVersion = p.fn.modal.Constructor.VERSION;
            var e = t.fullBootstrapVersion.indexOf('.');
            t.bootstrap = t.fullBootstrapVersion.substring(0, e)
        } else t.bootstrap = '2', t.fullBootstrapVersion = '2.3.2', console.warn('Bootbox will *mostly* work with Bootstrap 2, but we do not officially support it. Please upgrade, if possible.');
        var o = p(f.dialog),
            a = o.find('.modal-dialog'),
            r = o.find('.modal-body'),
            n = p(f.header),
            i = p(f.footer),
            l = t.buttons,
            s = {
                onEscape: t.onEscape
            };
        if (r.find('.bootbox-body').html(t.message), 0 < v(t.buttons) && (w(l, function (t, e) {
            var o = p(f.button);
            switch (o.data('bb-handler', t), o.addClass(e.className), t) {
                case 'ok':
                case 'confirm':
                    o.addClass('bootbox-accept');
                    break;
                case 'cancel':
                    o.addClass('bootbox-cancel')
            }
            o.html(e.label), i.append(o), s[t] = e.callback
        }), r.after(i)), !0 === t.animate && o.addClass('fade'), t.className && o.addClass(t.className), t.size && (t.fullBootstrapVersion.substring(0, 3) < '3.1' && console.warn('"size" requires Bootstrap 3.1.0 or higher. You appear to be using ' + t.fullBootstrapVersion + '. Please upgrade to use this option.'), 'large' === t.size ? a.addClass('modal-lg') : 'small' === t.size && a.addClass('modal-sm')), t.title && (r.before(n), o.find('.modal-title').html(t.title)), t.closeButton) {
            var c = p(f.closeButton);
            t.title ? 3 < t.bootstrap ? o.find('.modal-header').append(c) : o.find('.modal-header').prepend(c) : c.prependTo(r)
        }
        return t.centerVertical && (t.fullBootstrapVersion < '4.0.0' && console.warn('"centerVertical" requires Bootstrap 4.0.0-beta.3 or higher. You appear to be using ' + t.fullBootstrapVersion + '. Please upgrade to use this option.'), a.addClass('modal-dialog-centered')), o.one('hide.bs.modal', function (t) {
            t.target === this && (o.off('escape.close.bb'), o.off('click'))
        }), o.one('hidden.bs.modal', function (t) {
            t.target === this && o.remove()
        }), o.one('shown.bs.modal', function () {
            o.find('.bootbox-accept:first').trigger('focus')
        }), 'static' !== t.backdrop && o.on('click.dismiss.bs.modal', function (t) {
            o.children('.modal-backdrop').length && (t.currentTarget = o.children('.modal-backdrop').get(0)), t.target === t.currentTarget && o.trigger('escape.close.bb')
        }), o.on('escape.close.bb', function (t) {
            s.onEscape && y(t, o, s.onEscape)
        }), o.on('click', '.modal-footer button:not(.disabled)', function (t) {
            var e = p(this).data('bb-handler');
            y(t, o, s[e])
        }), o.on('click', '.bootbox-close-button', function (t) {
            y(t, o, s.onEscape)
        }), o.on('keyup', function (t) {
            27 === t.which && o.trigger('escape.close.bb')
        }), p(t.container).append(o), o.modal({
            backdrop: !!t.backdrop && 'static',
            keyboard: !1,
            show: !1
        }), t.show && o.modal('show'), o
    }, d.alert = function () {
        var t;
        if ((t = h('alert', ['ok'], ['message', 'callback'], arguments)).callback && !p.isFunction(t.callback)) throw new Error('alert requires the "callback" property to be a function when provided');
        return t.buttons.ok.callback = t.onEscape = function () {
            return !p.isFunction(t.callback) || t.callback.call(this)
        }, d.dialog(t)
    }, d.confirm = function () {
        var t;
        if (t = h('confirm', ['cancel', 'confirm'], ['message', 'callback'], arguments), !p.isFunction(t.callback)) throw new Error('confirm requires a callback');
        return t.buttons.cancel.callback = t.onEscape = function () {
            return t.callback.call(this, !1)
        }, t.buttons.confirm.callback = function () {
            return t.callback.call(this, !0)
        }, d.dialog(t)
    }, d.prompt = function () {
        var r, e, t, n, o, a;
        if (t = p(f.form), (r = h('prompt', ['cancel', 'confirm'], ['title', 'callback'], arguments)).value || (r.value = m.value), r.inputType || (r.inputType = m.inputType), o = r.show === u ? m.show : r.show, r.show = !1, r.buttons.cancel.callback = r.onEscape = function () {
            return r.callback.call(this, null)
        }, r.buttons.confirm.callback = function () {
            var t;
            if ('checkbox' === r.inputType) t = n.find('input:checked').map(function () {
                return p(this).val()
            }).get();
            else if ('radio' === r.inputType) t = n.find('input:checked').val();
            else {
                if (n[0].checkValidity && !n[0].checkValidity()) return !1;
                t = 'select' === r.inputType && !0 === r.multiple ? n.find('option:selected').map(function () {
                    return p(this).val()
                }).get() : n.val()
            }
            return r.callback.call(this, t)
        }, !r.title) throw new Error('prompt requires a title');
        if (!p.isFunction(r.callback)) throw new Error('prompt requires a callback');
        if (!f.inputs[r.inputType]) throw new Error('Invalid prompt type');
        switch (n = p(f.inputs[r.inputType]), r.inputType) {
            case 'text':
            case 'textarea':
            case 'email':
            case 'password':
                n.val(r.value), r.placeholder && n.attr('placeholder', r.placeholder), r.pattern && n.attr('pattern', r.pattern), r.maxlength && n.attr('maxlength', r.maxlength), r.required && n.prop({
                    required: !0
                });
                break;
            case 'date':
            case 'time':
            case 'number':
            case 'range':
                if (n.val(r.value), r.placeholder && n.attr('placeholder', r.placeholder), r.pattern && n.attr('pattern', r.pattern), r.required && n.prop({
                    required: !0
                }), 'date' !== r.inputType && r.step) {
                    if (!('any' === r.step || !isNaN(r.step) && 0 < parseInt(r.step))) throw new Error('"step" must be a valid positive number or the value "any". See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#attr-step for more information.');
                    n.attr('step', r.step)
                }(function (t, e, o) {
                var a = !1,
                    r = !0,
                    n = !0;
                if ('date' === t) e === u || (r = x(e)) ? o === u || (n = x(o)) || console.warn('Browsers which natively support the "date" input type expect date values to be of the form "YYYY-MM-DD" (see ISO-8601 https://www.iso.org/iso-8601-date-and-time-format.html). Bootbox does not enforce this rule, but your max value may not be enforced by this browser.') : console.warn('Browsers which natively support the "date" input type expect date values to be of the form "YYYY-MM-DD" (see ISO-8601 https://www.iso.org/iso-8601-date-and-time-format.html). Bootbox does not enforce this rule, but your min value may not be enforced by this browser.');
                else if ('time' === t) {
                    if (e !== u && !(r = g(e))) throw new Error('"min" is not a valid time. See https://www.w3.org/TR/2012/WD-html-markup-20120315/datatypes.html#form.data.time for more information.');
                    if (o !== u && !(n = g(o))) throw new Error('"max" is not a valid time. See https://www.w3.org/TR/2012/WD-html-markup-20120315/datatypes.html#form.data.time for more information.')
                } else {
                    if (e !== u && isNaN(e)) throw new Error('"min" must be a valid number. See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#attr-min for more information.');
                    if (o !== u && isNaN(o)) throw new Error('"max" must be a valid number. See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#attr-max for more information.')
                }
                if (r && n) {
                    if (o <= e) throw new Error('"max" must be greater than "min". See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#attr-max for more information.');
                    a = !0
                }
                return a
            })(r.inputType, r.min, r.max) && (r.min !== u && n.attr('min', r.min), r.max !== u && n.attr('max', r.max));
                break;
            case 'select':
                var i = {};
                if (a = r.inputOptions || [], !p.isArray(a)) throw new Error('Please pass an array of input options');
                if (!a.length) throw new Error('prompt with "inputType" set to "select" requires at least one option');
                r.placeholder && n.attr('placeholder', r.placeholder), r.required && n.prop({
                    required: !0
                }), r.multiple && n.prop({
                    multiple: !0
                }), w(a, function (t, e) {
                    var o = n;
                    if (e.value === u || e.text === u) throw new Error('each option needs a "value" property and a "text" property');
                    e.group && (i[e.group] || (i[e.group] = p('<optgroup />').attr('label', e.group)), o = i[e.group]);
                    var a = p(f.option);
                    a.attr('value', e.value).text(e.text), o.append(a)
                }), w(i, function (t, e) {
                    n.append(e)
                }), n.val(r.value);
                break;
            case 'checkbox':
                var l = p.isArray(r.value) ? r.value : [r.value];
                if (!(a = r.inputOptions || []).length) throw new Error('prompt with "inputType" set to "checkbox" requires at least one option');
                n = p('<div class="bootbox-checkbox-list"></div>'), w(a, function (t, o) {
                    if (o.value === u || o.text === u) throw new Error('each option needs a "value" property and a "text" property');
                    var a = p(f.inputs[r.inputType]);
                    a.find('input').attr('value', o.value), a.find('label').append('\n' + o.text), w(l, function (t, e) {
                        e === o.value && a.find('input').prop('checked', !0)
                    }), n.append(a)
                });
                break;
            case 'radio':
                if (r.value !== u && p.isArray(r.value)) throw new Error('prompt with "inputType" set to "radio" requires a single, non-array value for "value"');
                if (!(a = r.inputOptions || []).length) throw new Error('prompt with "inputType" set to "radio" requires at least one option');
                n = p('<div class="bootbox-radiobutton-list"></div>');
                var s = !0;
                w(a, function (t, e) {
                    if (e.value === u || e.text === u) throw new Error('each option needs a "value" property and a "text" property');
                    var o = p(f.inputs[r.inputType]);
                    o.find('input').attr('value', e.value), o.find('label').append('\n' + e.text), r.value !== u && e.value === r.value && (o.find('input').prop('checked', !0), s = !1), n.append(o)
                }), s && n.find('input[type="radio"]').first().prop('checked', !0)
        }
        if (t.append(n), t.on('submit', function (t) {
            t.preventDefault(), t.stopPropagation(), e.find('.bootbox-accept').trigger('click')
        }), '' !== p.trim(r.message)) {
            var c = p(f.promptMessage).html(r.message);
            t.prepend(c), r.message = t
        } else r.message = t;
        return (e = d.dialog(r)).off('shown.bs.modal'), e.on('shown.bs.modal', function () {
            n.focus()
        }), !0 === o && e.modal('show'), e
    }, d.addLocale('en', {
        OK: 'OK',
        CANCEL: 'Cancel',
        CONFIRM: 'OK'
    }), d
});