package org.jenkinsci.plugins.groovyshellserver.GroovyShellServer

def f=namespace(lib.FormTagLib)

set('instance', my)
f.section(title:_("Groovy Shell Server")) {
    f.entry(title:_("Port"), field: 'port') {
        f.number()
    }
}
