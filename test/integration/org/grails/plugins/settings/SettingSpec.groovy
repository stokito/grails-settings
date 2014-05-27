package org.grails.plugins.settings

import grails.plugin.fixtures.Fixture
import grails.plugin.fixtures.FixtureLoader
import grails.plugin.spock.IntegrationSpec

class SettingSpec extends IntegrationSpec {
    FixtureLoader fixtureLoader

    void 'valueFor()'() {
        given:
        Fixture fixture = fixtureLoader.load {
            build {
                settingInt(Setting, code: 'settingInt', type: 'integer', value: 1)
                settingStr(Setting, code: 'settingStr', type: 'string', value: 'string value')
                settingDec(Setting, code: 'settingDec', type: 'decimal', value: 1.5)
                settingDate(Setting, code: 'settingDate', type: 'date', value: '2000-01-31')
            }
        }
        expect:
        Setting.valueFor('settingInt') == 1
        Setting.valueFor('settingStr') == 'string value'
        Setting.valueFor('settingDec') == 1.5
        Date settingDate = Setting.valueFor('settingDate')
        settingDate.date == 31
        settingDate.month == 0
        settingDate.year + 1900 == 2000
    }

    void 'valueFor() with default'() {
        when:
        def setting = Setting.valueFor('setting1', 'default value')
        then:
        setting == 'default value'
    }
}
