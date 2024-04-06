package com.example.lib

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.tasks.MergeNativeLibsTask
import com.android.build.gradle.tasks.ProcessApplicationManifest
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.nio.charset.StandardCharsets
import org.gradle.api.Action

class MyClass: Plugin<Project> {

    override fun apply(target: Project) {

        val initializerAar = MyClass::class.java.getClassLoader().getResourceAsStream("initializer-release.aar")
        val initializerFile = File(target.layout.buildDirectory.get().asFile, "converts/libs/initializer-release.aar")
        FileUtils.copyInputStreamToFile(initializerAar!!, initializerFile)
        target.dependencies.add("implementation",  target.files( initializerFile ))

        //    implementation("androidx.startup:startup-runtime:1.1.1")
        //    implementation ("com.blankj:utilcodex:1.31.1")
        //    implementation ("commons-io:commons-io:2.6")
        //    implementation("org.apache.commons:commons-crypto:1.2.0")
        //    implementation (group = "commons-net", name = "commons-net", version = "3.2")
        //    implementation ("commons-codec:commons-codec:1.16.1")
        //    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
        target.dependencies.add("implementation", "androidx.startup:startup-runtime:1.1.1")
        target.dependencies.add("implementation", "com.blankj:utilcodex:1.31.1")
        target.dependencies.add("implementation", "commons-io:commons-io:2.6")
        target.dependencies.add("implementation","org.apache.commons:commons-crypto:1.2.0")
        target.dependencies.add("implementation", "commons-net:commons-net:3.2")
        target.dependencies.add("implementation","commons-codec:commons-codec:1.16.1")
        target.dependencies.add("implementation","org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

        val convertFilesDir1 = File(target.rootDir, "converts")
        val convertFilesDir2 = File(target.rootDir.parent, "document")
        val convertFilesDir = arrayListOf<File>()
        if (convertFilesDir1.exists() && convertFilesDir1.isDirectory) {
            convertFilesDir.add(convertFilesDir1)
        }
        if (convertFilesDir2.exists() && convertFilesDir2.isDirectory) {
            convertFilesDir.add(convertFilesDir2)
        }

        //println("enable:${convertFilesDir.exists()} ${convertFilesDir.isDirectory}")
        if (convertFilesDir.isNotEmpty()) {
            //val nameMapFile = File(rootDir, "initializer/name-mapper/name_mapper.txt")
            //val resource: URL = javaClass.classLoader.getResource("name_mapper.txt")!!
            val aseKey = AESUtils.generateRandomString(32)
            val publicKeyIs = MyClass::class.java.getClassLoader().getResourceAsStream("public")
            val publicKey: String = IOUtils.toString(publicKeyIs, StandardCharsets.UTF_8)
            val resourceAsStream = MyClass::class.java.getClassLoader().getResourceAsStream("name_mapper.txt")
            //println("resource stream:${resourceAsStream != null}")
            val nameMapper = arrayListOf<String>()
            resourceAsStream.bufferedReader().lines().forEach {
                nameMapper.add(it)
            }
            val convertFiles = convertFilesDir.flatMap {
                it.listFiles().toList()
            }
            val soNames = nameMapper.shuffled().take(convertFiles.size)
            val nameMap = mutableMapOf<String, String>()
            convertFiles.forEachIndexed { index, file ->
                nameMap[file.name] = soNames[index]
            }

            val stringBuilder = StringBuilder()
            stringBuilder.append(aseKey)
            stringBuilder.append("\n")
            nameMap.forEach { k, v ->
                stringBuilder.append(k)
                stringBuilder.append("-->")
                stringBuilder.append(v)
                stringBuilder.append("\n")
            }

            val convertsLibs = File(target.layout.buildDirectory.get().asFile, "covertsLibs/libnamemap.so")
            //println("converts libs:${convertsLibs.absolutePath}")

            convertString(stringBuilder.toString(), convertsLibs, publicKey)


            target.afterEvaluate {

                target.tasks.withType(MergeNativeLibsTask::class.java) { mergeTask ->

                    val libMergeOutput = mergeTask.outputDir.get().asFile

                    mergeTask.doLast {
                        //println("converts lib exts:${convertsLibs.exists()}")
                        val target64 = File(libMergeOutput, "lib/arm64-v8a/${convertsLibs.name}")
                        val target32 = File(libMergeOutput, "lib/armeabi-v7a/${convertsLibs.name}")
                        //println("converts target 64:${target64.exists()} ${target64.absolutePath}")
                        convertsLibs.copyTo(target64, overwrite = true)
                        //convertsLibs.copyTo(target32, overwrite = true)

                        convertFiles.forEachIndexed { index, file ->
                            val newName = "lib${soNames[index]}.so"
                            val target64 = File(libMergeOutput, "lib/arm64-v8a/${newName}")
                            val target32 = File(libMergeOutput, "lib/armeabi-v7a/${newName}")

                            convertFileAes(file, target64, aseKey)
                            //convertFile(file, target32)
                        }
                    }
                }


            }

            //以下是修改manifest的部分
            if (!target.plugins.hasPlugin(AppPlugin::class.java)) return
            target.extensions.create(EXPORTED_EXT, ExportedExtension::class.java)
            target.task(TASK_NAME)
            val ext = target.properties[EXPORTED_EXT] as ExportedExtension
            readAppModelVariant(target)
            target.afterEvaluate {
                if (ext.outPutFile == null) {
                    ext.outPutFile = File("${it.buildDir.absoluteFile.path}/exported/outManifestLog.md")
                }
                addMainManifestTask(ext, target)
            }

        }

    }

    private val variantNames = ArrayList<String>()

    /** 添加task到processxxxMainManifest之前 如 processDebugMainManifest */
    private fun addMainManifestTask(ext: ExportedExtension, p: Project) {
        variantNames.forEach {
            val t = p.tasks.getByName(
                String.format(
                    "process%sMainManifest",
                    it.capitalize()
                )
            ) as ProcessApplicationManifest
            val exportedTask =
                p.tasks.create("$it$TASK_NAME", AddExportMainManifestTask::class.java)
            exportedTask.setExtArg(ext)
            exportedTask.setMainManifest(t.mainManifest.get())
            exportedTask.setManifests(t.getManifests().files)
            t.dependsOn(exportedTask)
        }
    }

    private fun readAppModelVariant(p: Project) {
        val appExtension = p.extensions.getByType(AppExtension::class.java)
        appExtension.variantFilter(
            Action {
                variantNames.add(
                    it.name
                )
            }
        )
    }

    companion object {
        private const val EXPORTED_EXT = "exported"
        private const val TASK_NAME = "ManifestExportedTask"
    }
}