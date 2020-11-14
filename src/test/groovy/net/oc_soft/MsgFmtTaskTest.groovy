package net.oc_soft

import static org.junit.jupiter.api.Assertions.assertTrue


import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
/**
 * msgfmt task test
 */
public class MsgFmtTaskTest {
        
    static def getMessagesContents() {
        return '''
# Language en translations for Mine sweeper package.
# Copyright (C) 2019 OC software
# This file is distributed under the same license as the Mine sweeper package.
# <toshi@oc-soft.net>, 2019.
#
msgid ""
msgstr ""
"Project-Id-Version: Mine sweeper 0.4.0\\n"
"Report-Msgid-Bugs-To: toshi@oc-soft.net\\n"
"POT-Creation-Date: 2020-09-03 07:50+0900\\n"
"PO-Revision-Date: 2019-12-08 14:55+0900\\n"
"Last-Translator: toshi@oc-soft.net\\n"
"Language-Team: Language en\\n"
"Language: en\\n"
"MIME-Version: 1.0\\n"
"Content-Type: text/plain; charset=UTF-8\\n"
"Content-Transfer-Encoding: 8bit\\n"

msgid "Loading..."
msgstr "Loading..."

msgid "Game over"
msgstr "Game over"

msgid "Play again"
msgstr "Play again"

msgid "About this Project"
msgstr "About this Project"

msgid "Initial Setting"
msgstr "Initial Setting"
'''
    }

    @Test
    void canRunTask(@TempDir File testDir) {

        def prjDir = new File(testDir, 'build/test/msgfmt')

        def i18nSrcDir = new File(prjDir, 'src/i18n/en/LC_MESSAGES') 
        prjDir.mkdirs()
        i18nSrcDir.mkdirs()

        def messageFile = new File(i18nSrcDir, 'messages.po')
        messageFile.text = messagesContents

        def prjBuilder = ProjectBuilder.builder()
        def project = prjBuilder.withProjectDir(prjDir).build()
        def tasks = project.tasks
        tasks.create('msgfmt', MsgFmtTask)
        def msgfmt = project.tasks.getByName('msgfmt')
        def output = new File(msgfmt.dstDir, 'en/LC_MESSAGES/messages.mo')
        output.delete()

        def actions = msgfmt.actions
        msgfmt.actions.each {
            it.execute(msgfmt)
        }
        assertTrue(output.exists()) 
        output.delete()
        messageFile.delete()
    }
}
// vi: se ts=4 sw=4 et:
