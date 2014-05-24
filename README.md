# What is it ?

This is an update version of the official [Grails settings plugin](http://www.grails.org/plugin/settings) done by Paul Fernley.

# Settings Plugin

## Description

Allows for the creation, maintenance and use of system-wide application settings (global constants) stored in a database.
The values of the various settings can be of type `String`, `Integer`, `BigDecimal` or `Date`.
The usual List, Show, Edit and Create views are included and, after installing the plugin, will be available at a URL such as `http://myServer/myApp/setting/list` etc. 
These views assume you are using a layout called `main`.

## Installation

Execute the following from your application directory:
```
grails install-plugin settings
```

The plugin creates one domain called `Setting`. It also copies a properties file called `settings.properties` to the `i18n` directory of your application overwriting any file of the same name. 
After installation, the `Setting` table in your database should have a unique index on the `code` column, but since Hibernate may or may not create this index, you are advised to check it exists otherwise performance may suffer. 
Settings are held in memory in a 'least recently used cache' for fast repeated access.

The default maximum cache size is 8kb, but memory is only used as is needed. If you wish to alter the maximum size (amount of memory) used by the cache, you may do so by making an entry similar to the following in your Config.groovy file:
```groovy
settings.cache.size.kb = 32
```

The above example `Config.groovy` entry increases the cache size to 32kb. Setting the cache size to zero disables caching with a consequent increase in databases activity. You can check the cache statistics using a URL such as: http://myServer/myApp/setting/cache. Note that you may have to refresh your browser window to see the most up to date statistics.


## Usage

The components of the plugin are in a package called org.grails.plugins.settings and any class that wishes to access the components directly must include the following:
```
import org.grails.plugins.settings.*
```

Settings are accessed by codes such as `pagination.max`, `pagination.default` and so forth. 
Programatically, the values can be accessed using `Setting.valueFor("pagination.max")` or `Setting.valueFor("pagination.max", 50)` where the latter example allows for a default if the code cannot be found. 
Without a default, the system will return a null if a given code is not found.

Dates must be entered in to the Create and Edit views in the strict format of `yyyy-MM-dd`, or `yyyy-MM-dd hh:mm` if a time is required, in order to pass validation.

The list action of the controller included with the plugin uses the following code to defeat denial-of-service attacks:

```groovy
params.max = (params.max && params.max.toInteger() > 0) ?
  Math.min(params.max.toInteger(), Setting.valueFor("pagination.max", 50)) :
  Setting.valueFor("pagination.default", 20)
```

and will therefore work with or without you defining the `pagination.max` and/or the `pagination.default` settings in your own application.

The plugin also provides a tag which can be used as follows:
```gsp
<g:setting valueFor="pagination.max" default="50" encodeAs="HTML"/>
```

Note that both the `default` and `encodeAs` attributes are optional but also be aware that the value is not HTML encoded by default.


## Compatibility

This plugin was written using Java version 1.6u17 and Grails version 1.2.0.

If you have the [help-balloons plugin](http://grails.org/plugin/help-balloons), on-screen help will be available when creating or editing settings, 
otherwise you will have to read the help texts in the `settings.properties` file to determine what is expected in each field. 
If you have the [criteria plugin](http://grails.org/plugin/criteria), the list page will allow selection criteria to be applied. 
If you have the [menus plugin](http://grails.org/plugin/menus) installed, a 'return to menu' button will be placed after the 'home' button on all settings pages. 
If you have the [localizations plugin](http://grails.org/plugin/localizations) all texts used by the settings plugin will be internationalized via the database, 
otherwise the `settings.properties` resource bundle in your `i18n` directory will be used.

## History
* Version 1.4. (2010-05-12) Update to Grails v1.3
* Version 1.3. (2009-12-30) Update to Grails v1.2
* Version 1.2. (2009-05-03) Added setting tag for ease of use in GSPs
* Version 1.1. (2009-04-01) Update to Grails v1.1 and packages
* Version 1.0. (2008-11-18) Initial release