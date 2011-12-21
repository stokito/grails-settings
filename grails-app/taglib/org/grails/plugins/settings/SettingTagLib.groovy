package org.grails.plugins.settings

import org.grails.plugins.settings.*

class SettingTagLib {

    def settingService

    def settingHelpBalloons = {attrs, body ->
        if (settingService.hasPlugin("helpBalloons")) {
            out << g.helpBalloons(attrs)
        }
    }

    def settingHelpBalloon = {attrs, body ->
        if (settingService.hasPlugin("helpBalloons")) {
            out << g.helpBalloon(attrs)
        }
    }

    def settingCriteria = {attrs, body ->
        if (settingService.hasPlugin("criteria")) {
            out << """<div class="criteria">\n"""
            out << g.criteria(attrs)
            out << """</div>\n"""
        }
    }

    def settingPaginate = {attrs, body ->
        attrs.total = (settingService.hasPlugin("criteria") || settingService.hasPlugin("drilldown")) ? Setting.selectCount(session, params) : Setting.count()

        out << g.paginate(attrs)
    }

    def settingMenuButton = {attrs, body ->
        if (settingService.hasPlugin("menus")) {
            out << '<span class="menuButton">'
            out << g.link(class: "menu", controller: "menu", action: "display") {
                g.message(code: "menu.display", default: "Menu")
            }
            out << '</span>'
        }
    }

    def setting = {attrs, body ->
        def code = attrs.valueFor ?: attrs.value
        if (code) {
            def val = "${Setting.valueFor(code) ?: attrs.default}"
            switch (attrs.encodeAs?.toLowerCase()) {
                case 'html':
                    val = val.encodeAsHTML()
                    break
                case 'xml':
                    val = val.encodeAsXML()
                    break
                case 'json':
                    val = val.encodeAsJSON()
            }

            out << val
        }
    }
}
