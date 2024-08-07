package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import okhttp3.internal.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    doneTasks: Int = 0,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    ),
    visibilityState: Boolean = false,
    onVisibilityIconClick: () -> Unit = {}
) {
    val isTittleExpand = if (scrollBehavior.state.collapsedFraction < 0.8f) true else false
    val fontSize = animateFloatAsState(
        targetValue = if (isTittleExpand) AppTheme.typography.largeTitle.fontSize.value
        else AppTheme.typography.title.fontSize.value,
        label = "TitleFontSize"
    )
    val lineHeight = animateFloatAsState(
        targetValue = if (isTittleExpand) AppTheme.typography.largeTitle.lineHeight.value
        else AppTheme.typography.title.lineHeight.value,
        label = "TitleLineHeight"
    )
    val isShadowNeed = scrollBehavior.state.contentOffset < 0
    val elevation = animateDpAsState(
        targetValue = if (isShadowNeed) {
            20.dp
        } else {
            0.dp
        },
        label = "elevation"
    )

    LargeTopAppBar(
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = animateDpAsState(
                            targetValue = if (isTittleExpand) 44.dp else 12.dp,
                            label = "padding animation"
                        ).value
                    )
            ) {
                Text(
                    text = stringResource(R.string.title),
                    color = AppTheme.colorScheme.primary,
                    style = AppTheme.typography.largeTitle.copy(
                        fontSize = fontSize.value.sp,
                        lineHeight = lineHeight.value.sp
                    )
                )
                AnimatedVisibility(
                    visible = isTittleExpand,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val context = LocalContext.current
                        Text(
                            text = stringResource(R.string.underlable, doneTasks),
                            color = AppTheme.colorScheme.tertiary,
                            style = AppTheme.typography.title,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .semantics {
                                    contentDescription = when (doneTasks) {
                                        0 -> context.getString(R.string.under_lable_0)
                                        1 -> context.getString(R.string.under_lable_1)
                                        else -> {
                                            val ost = doneTasks % 10
                                            when (ost) {
                                                2, 3, 4 -> format(
                                                    context.getString(R.string.under_lable_2_3_4),
                                                    doneTasks
                                                )

                                                else -> format(
                                                    context.getString(R.string.under_lable_else),
                                                    doneTasks
                                                )
                                            }
                                        }
                                    }
                                }
                        )

                        IconButton(
                            onClick = { onVisibilityIconClick() },
                            modifier = Modifier
                                .clickable(
                                    onClick = { onVisibilityIconClick() },
                                    onClickLabel = if (visibilityState) stringResource(R.string.off_done_tasks)
                                    else stringResource(R.string.on_done_tasks)
                                )
                                .padding(horizontal = 20.dp)
                                .size(24.dp)
                                .semantics {
                                    role = Role.Button
                                }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    id = if (visibilityState) R.drawable.ic_visibility
                                    else R.drawable.ic_visibility_off
                                ),
                                tint = Blue,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = AppTheme.colorScheme.backPrimary,
            scrolledContainerColor = AppTheme.colorScheme.backPrimary
        ),
        modifier = Modifier.graphicsLayer {
            this.shadowElevation = elevation.value.toPx()
        }
    )
}
