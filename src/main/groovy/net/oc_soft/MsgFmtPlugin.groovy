package net.oc_soft

import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * The plugin to generate message object for gettext
 */
class MsgFmtPlugin implements Plugin<Project>  {
    

    /**
     * apply project
     */
    void apply(Project project) {
        if (project.tasks.findByPath('msgfmt') == null) {
            project.tasks.register('msgfmt', MsgFmtTask) 
        }
    }
}

// vi: se ts=4 sw=4 et:
