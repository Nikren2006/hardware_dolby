/*
 * Copyright (C) 2024-2025 Lunaris AOSP
 * SPDX-License-Identifier: Apache-2.0
 */

package com.miui.misound.domain.models

import com.miui.misound.data.AppInfo

sealed class AppProfileUiState {
    object Loading : AppProfileUiState()
    data class Success(
        val apps: List<AppInfo>,
        val appsWithProfiles: Map<String, Int>
    ) : AppProfileUiState()
    data class Error(val message: String) : AppProfileUiState()
}
