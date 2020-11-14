package net.oc_soft

import static org.junit.jupiter.api.Assertions.assertNotNull


import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
/**
 * msgfmt task test
 */
public class MsgFmtPluginTest {

    @Test
    void canRegisterTask(@TempDir File testDir) {

        def prjDir = new File(testDir, 'build/test/msgfmt')

        prjDir.mkdirs()

        def prjBuilder = ProjectBuilder.builder()
        def project = prjBuilder.withProjectDir(prjDir).build()
        (new MsgFmtPlugin()).apply(project) 

        assertNotNull(project.tasks.getByName('msgfmt'))

    }
}
// vi: se ts=4 sw=4 et:
