package com.example.lib

import java.io.File

/**
 * Create by chenjunsheng on 2024/3/22
 */
open class ExportedExtension {

    // 是否支持写入主清单文件,默认false,用户手动决定
    var enableMainManifest: Boolean = false

    // 日志输出位置,会输出以下内容
    var outPutFile: File? = null

    // 对于intent-filter的匹配规则
    var ruleFile: File? = null
}