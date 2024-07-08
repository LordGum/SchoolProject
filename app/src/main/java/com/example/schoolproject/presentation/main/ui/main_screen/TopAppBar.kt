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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue

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
        targetValue = if (isShadowNeed) { 20.dp } else { 0.dp },
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
                        Text(
                            text = stringResource(R.string.underlable, doneTasks),
                            color = AppTheme.colorScheme.tertiary,
                            style = AppTheme.typography.body,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Icon(
                            painter = painterResource(
                                id = if (visibilityState) R.drawable.ic_visibility
                                else R.drawable.ic_visibility_off
                            ),
                            tint = Blue,
                            contentDescription = stringResource(R.string.visibility_button_desc),
                            modifier = Modifier
                                .clickable { onVisibilityIconClick() }
                                .padding(horizontal = 20.dp)
                        )
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
