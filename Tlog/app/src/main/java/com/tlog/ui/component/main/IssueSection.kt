package com.tlog.ui.component.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tlog.ui.style.BodyTitle
import com.tlog.domain.model.share.LocalGuide


@Composable
fun IssueSection(
    localGuides: List<LocalGuide>,
    context: Context
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "ISSUE",
            style = BodyTitle,
            modifier = Modifier
                .padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        localGuides.forEach { issue ->
            IssueCard(
                issue = issue,
                context = context
            )
        }
    }
}
