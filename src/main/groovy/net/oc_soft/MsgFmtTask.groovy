package net.oc_soft

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles

import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.Internal


/**
 * message formats task
 */
public class MsgFmtTask extends DefaultTask {

    /**
     * source direcory
     */
    private File srcDir 
    
    /**
     * destination directory
     */
    private File dstDir

    {
        srcDir = defaultSrcDir
        dstDir = defaultDstDir 
    }
    /**
     * constructor
     */
    public MsgFmtTask() {
    } 
    
    /**
     * get source directory
     */
    @InputDirectory
    def getSrcDir() {
        return srcDir
    }

    /**
     * set source directory
     */
    void setSrcDir(def srcDir) {
        this.srcDir = srcDir
    }

    /**
     * get destination directory
     */
    @OutputDirectory
    def getDstDir() {
        return dstDir
    }

    /**
     * set destination directory
     */
    void setDstDir(def dstDir) {
        this.dstDir = dstDir
    }
     
    /**
     * get default source directory
     */
    @Internal
    def getDefaultSrcDir() {
        return project.file("${project.projectDir}/src/i18n")
    }
    /**
     * default destitnation directory
     */
    @Internal
    def getDefaultDstDir() {
        return project.file("${project.projectDir}/i18n")
    }

    /**
     * source and target directory mapping
     */
    @Internal
    def getSourceTargetMapping() {
        def result = [:]

         project.fileTree(srcDir).visit { 
            if (it.file.file && it.file.parentFile != srcDir) {
                def rootPath = srcDir.absolutePath
                def partialPath = 
                    it.file.absolutePath.substring rootPath.length() + 1
                def targetRelDir = partialPath.substring(0,
                    partialPath.length () - it.file.name.length())
                result[partialPath] = [
                    'targetDir': targetRelDir, 
                    'srcFile': it.file]
            }
        }
        return result
    }

    /**
     * generate output file
     */
    def generateOutputFile(def relTargetDir, File srcFile) {
        def targetDir = this.dstDir
        def destDir = new File(targetDir, relTargetDir)
        def fileName = srcFile.name
        def extIdx = fileName.lastIndexOf('.')
        if (extIdx > 0) {
            fileName = fileName.substring(0, extIdx)
        }
        return new File(destDir, fileName + '.mo')
    } 
 
    /**
     * source files
     */
    @InputFiles
    def getSourceFiles() {
        def result = []
        sourceTargetMapping.each {
            result.add(it.value.srcFile)
        } 
        return result
    }

    /**
     * output files
     */
    @OutputFiles
    def getOutputFiles() {
        def result = []
        def targetDir = this.dstDir
        sourceTargetMapping.each {
            def outputFile = generateOutputFile(
                it.value.targetDir, it.value.srcFile)
            result.add(outputFile)
        }
        return result 
    }

    /**
     * do generate message object.
     */
    @TaskAction
    public void run() {

        def col = sourceTargetMapping
        def targetDir = this.dstDir

        col.each {
            def destDir = new File(targetDir, it.value.targetDir)
            project.mkdir destDir
            def outputFile = generateOutputFile(
                it.value.targetDir, it.value.srcFile)
            def srcPath = it.value.srcFile
            def execRes = project.exec {
                executable = 'msgfmt'
                args '-o', outputFile, srcPath    
            }
            execRes.assertNormalExitValue()
        }
    }
}

// vi: se ts=4 sw=4 et:
