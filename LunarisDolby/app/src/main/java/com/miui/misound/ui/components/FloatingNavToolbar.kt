/*
 * Copyright (C) 2024-2025 Lunaris AOSP
 * SPDX-License-Identifier: Apache-2.0
 */

package com.miui.misound.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.miui.misound.R
import com.miui.misound.utils.*

@Composable
fun FloatingNavToolbar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = rememberHapticFeedback()
    val scope = rememberCoroutineScope()
    
    val isHomeSelected = currentRoute == "settings"
    val isEqualizerSelected = currentRoute == "equalizer"
    val isAdvancedSelected = currentRoute == "advanced"
    
    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val onContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 16.dp
                )
                .shadow(
                    elevation = 16.dp,
                    shape = MaterialTheme.shapes.extraLarge,
                    ambientColor = Color.Black.copy(alpha = 0.4f),
                    spotColor = Color.Black.copy(alpha = 0.5f)
                ),
            shape = MaterialTheme.shapes.extraLarge,
            color = containerColor,
            contentColor = onContainerColor
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                NavToolbarItem(
                    icon = Icons.Default.Home,
                    label = stringResource(R.string.home),
                    selected = isHomeSelected,
                    primaryColor = primaryColor,
                    onPrimaryColor = onPrimaryColor,
                    containerColor = containerColor,
                    onContainerColor = onContainerColor,
                    onClick = {
                        scope.launch {
                            haptic.performHaptic(HapticFeedbackHelper.HapticIntensity.CLICK)
                        }
                        onNavigate("settings")
                    }
                )
                
                NavToolbarItem(
                    icon = Icons.Default.GraphicEq,
                    label = stringResource(R.string.equalizer),
                    selected = isEqualizerSelected,
                    isEqualizer = true,
                    primaryColor = primaryColor,
                    onPrimaryColor = onPrimaryColor,
                    containerColor = containerColor,
                    onContainerColor = onContainerColor,
                    onClick = {
                        scope.launch {
                            haptic.performHaptic(HapticFeedbackHelper.HapticIntensity.CLICK)
                        }
                        onNavigate("equalizer")
                    }
                )
                
                NavToolbarItem(
                    icon = Icons.Default.Settings,
                    label = stringResource(R.string.advanced),
                    selected = isAdvancedSelected,
                    primaryColor = primaryColor,
                    onPrimaryColor = onPrimaryColor,
                    containerColor = containerColor,
                    onContainerColor = onContainerColor,
                    onClick = {
                        scope.launch {
                            haptic.performHaptic(HapticFeedbackHelper.HapticIntensity.CLICK)
                        }
                        onNavigate("advanced")
                    }
                )
            }
        }
    }
}

@Composable
private fun NavToolbarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    primaryColor: Color,
    onPrimaryColor: Color,
    containerColor: Color,
    onContainerColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEqualizer: Boolean = false
) {
    val currentSelectionKey = remember(selected) { selected }
    
    Surface(
        checked = selected,
        onCheckedChange = { onClick() },
        modifier = modifier
            .height(56.dp)
            .padding(horizontal = 4.dp),
        shape = CircleShape,
        color = if (selected) primaryColor else containerColor,
        contentColor = if (selected) onPrimaryColor else onContainerColor,
        tonalElevation = if (selected) 2.dp else 0.dp,
        shadowElevation = if (selected) 2.dp else 0.dp,
        clickable = true,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 300)
                )
        ) {
            key(currentSelectionKey) {
                Crossfade(
                    targetState = isEqualizer,
                    animationSpec = tween(durationMillis = 400),
                    label = "icon_transition_$label"
                ) { isEq ->
                    if (isEq) {
                        AnimatedEqualizerIconDynamic(
                            color = if (selected) onPrimaryColor else onContainerColor,
                            size = 24.dp
                        )
                    } else {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            
            AnimatedVisibility(
                visible = selected,
                enter = expandHorizontally(
                    animationSpec = tween(durationMillis = 300),
                    expandFrom = Alignment.Start
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = shrinkHorizontally(
                    animationSpec = tween(durationMillis = 300),
                    shrinkTowards = Alignment.Start
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 300)
                ),
                label = "text_visibility_$label"
            ) {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
