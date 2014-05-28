class SettingsGrailsPlugin {
    def version = "2.0.1"
    String grailsVersion = '2.2.3 > *'
    def dependsOn = [:]
    def author = "Paul Fernley"
    def authorEmail = "paul@pfernley.orangehome.co.uk"
    def title = "Application settings (global constants or variables) plugin"
    def description = '''\
Allows for the creation, maintenance and use of system-wide application settings
(global constants) stored in a database. The values of the various settings can
be of type String, Integer, BigDecimal or Date. The usual List, Show, Edit and
Create views are included and, after installing the plugin, will be available at
a URL such as http://example.com/myApp/setting/list etc. These views assume you are
using a layout called 'main'.
'''
    def documentation = "http://grails.org/plugin/settings"
    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"
    // Any additional developers beyond the author specified above.
    def developers = [
            [ name: "Paul Fernley", email: "paul@pfernley.orangehome.co.uk" ],
            [ name: "Stéphane Prohaszka" ],
            [ name: "Sergey  Ponomarev", email: "stokito@gmail.com" ]
    ]
    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GITHUB", url: "https://github.com/sprohaszka/grails-settings/issues" ]
    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/sprohaszka/grails-settings" ]
}
