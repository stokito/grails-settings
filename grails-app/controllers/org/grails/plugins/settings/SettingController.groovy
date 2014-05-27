package org.grails.plugins.settings

import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.commons.GrailsDomainClass

class SettingController {
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST', reset: 'POST']
    static defaultAction = 'list'
    private static loaded = false
    def pluginManager
    def grailsApplication

    def list(int max, String sort) {
        if (!loaded) {
            if (pluginManager.hasGrailsPlugin('localizations')) {
                String test = message(code: 'setting.id', default: "_missing!")
                if (test == '_missing!') {
                    def loc = ((GrailsDomainClass) grailsApplication.getArtefact(DomainClassArtefactHandler.TYPE, 'org.grails.plugins.localization.Localization')).newInstance()
                    loc.loadPluginData("settings")
                }
            }
            loaded = true
        }
        params.max = max ? Math.min(max, Setting.valueFor('pagination.max', 50)) : Setting.valueFor('pagination.default', 20)
        params.sort = sort ?: 'code'
        List<Setting> lst
        if (pluginManager.hasGrailsPlugin('criteria') || pluginManager.hasGrailsPlugin('drilldowns')) {
            lst = Setting.selectList(session, params)
        } else {
            lst = Setting.list(params)
        }
        [settingList: lst]
    }

    def show(Long id) {
        Setting setting = Setting.get(id)
        if (!setting) {
            flash.message = "setting.not.found"
            flash.args = [id]
            flash.defaultMessage = "Setting not found with id ${id}"
            redirect(action: 'list')
        } else {
            return [setting: setting]
        }
    }

    def delete(Long id) {
        Setting setting = Setting.get(id)
        if (setting) {
            setting.delete()
            Setting.resetThis(setting.code)
            flash.message = "setting.deleted"
            flash.args = [id]
            flash.defaultMessage = "Setting ${id} deleted"
            redirect(action: 'list')
        } else {
            flash.message = "setting.not.found"
            flash.args = [id]
            flash.defaultMessage = "Setting not found with id ${id}"
            redirect(action: 'list')
        }
    }

    def edit(Long id) {
        Setting setting = Setting.get(id)
        if (!setting) {
            flash.message = "setting.not.found"
            flash.args = [id]
            flash.defaultMessage = "Setting not found with id ${id}"
            redirect(action: 'list')
        } else {
            return [setting: setting]
        }
    }

    def update(Long id) {
        Setting setting = Setting.get(id)
        if (setting) {
            String oldCode = setting.code
            setting.properties = params
            if (!setting.hasErrors() && setting.save()) {
                Setting.resetThis(oldCode)
                if (setting.code != oldCode) {
                    Setting.resetThis(setting.code)
                }
                flash.message = 'setting.updated'
                flash.args = [id]
                flash.defaultMessage = "Setting ${id} updated"
                redirect(action: 'show', id: id)
            } else {
                render(view: 'edit', model: [setting: setting])
            }
        } else {
            flash.message = "setting.not.found"
            flash.args = [id]
            flash.defaultMessage = "Setting not found with id ${id}"
            redirect(action: 'edit', id: id)
        }
    }

    def create() {
        Setting setting = new Setting(params)
        return ['setting': setting]
    }

    def save() {
        Setting setting = new Setting(params)
        if (!setting.hasErrors() && setting.save()) {
            Setting.resetThis(setting.code)
            flash.message = "setting.created"
            flash.args = ["${setting.id}"]
            flash.defaultMessage = "Setting ${setting.id} created"
            redirect(action: 'show', id: setting.id)
        } else {
            render(view: 'create', model: [setting: setting])
        }
    }

    def cache() {
        return [stats: Setting.statistics()]
    }

    def reset() {
        Setting.resetAll()
        redirect(action: 'cache')
    }
}