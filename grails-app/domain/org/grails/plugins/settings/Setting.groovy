package org.grails.plugins.settings

import java.text.ParseException
import java.text.SimpleDateFormat

class Setting {
    String code
    String type
    String value
    Date dateCreated
    Date lastUpdated

    static mapping = {
        columns {
            code index: 'setting_code_idx'
        }
    }

    static constraints = {
        code(blank: false, size: 1..100, unique: true)
        type(blank: false, inList: ['string', 'integer', 'decimal', 'date'])
        value(blank: false, size: 1..100, validator: { val, obj ->
            Setting.decodeValue(obj.type, val) != null
        })
    }

    static valueFor(String code) {
        Setting setting = Setting.findByCode(code)
        return setting ? Setting.decodeValue(setting.type, setting.value) : null
    }

    static valueFor(String code, Object dflt) {
        def val = valueFor(code)
        return val != null ? val : dflt
    }

    @Deprecated
    static resetAll() {
    }

    @Deprecated
    static resetThis(String code) {
    }

    private static decodeValue(String type, String val) {
        if (val) {
            switch (type) {
                case "integer":
                    try {
                        return new Integer(val)
                    } catch (NumberFormatException ne) {
                    }
                    break
                case "decimal":
                    try {
                        return new BigDecimal(val)
                    } catch (NumberFormatException ne) {
                    }
                    break
                case "date":
                    try {
                        def fmt = val.length() == 10 ? 'yyyy-MM-dd' : 'yyyy-MM-dd HH:mm'
                        return new SimpleDateFormat(fmt, Locale.US).parse(val)
                    } catch (ParseException pe) {
                    }
                    break
                default:  // string
                    return val
                    break
            }
        }
        return null
    }

    @Deprecated
    static valSize(val) {
    }

    @Deprecated
    static statistics() {
    }
}