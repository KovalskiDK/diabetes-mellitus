package com.diabetes.giindex.data.update

data class UpdateInfo(
    val versionCode: Int,
    val versionName: String,
    val downloadUrl: String,
    val releaseNotes: String,
    val mandatory: Boolean = false,
    val minSupportedVersion: Int = 0,
    val releaseDate: String = ""
)

data class UpdateCheckResult(
    val hasUpdate: Boolean,
    val updateInfo: UpdateInfo? = null,
    val currentVersion: String = "",
    val error: String? = null
)
