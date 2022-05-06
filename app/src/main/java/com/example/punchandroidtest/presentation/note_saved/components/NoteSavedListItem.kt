package com.example.punchandroidtest.presentation.note_saved.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.punchandroidtest.R
import com.example.punchandroidtest.common.formatNumber
import com.example.punchandroidtest.domain.model.Mars
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer


@OptIn(ExperimentalCoilApi::class)
@Composable
fun NoteSavedListItem(
    mars: Mars
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var painter = rememberImagePainter(
            data = mars.imageSource.value
        )
        if(painter.state is ImagePainter.State.Error) {
            painter = rememberImagePainter(
                data = R.drawable.broken_image
            )
        }
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(120.dp, 120.dp)
                .placeholder(
                    visible = painter.state is ImagePainter.State.Loading,
                    highlight = PlaceholderHighlight.shimmer(),
                )
        )
        Column(modifier = Modifier.weight(1f).padding(10.dp).fillMaxHeight().align(CenterVertically)) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.body1.fontSize,
                        )
                    ) {
                        append("Price: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.W900,
                        )
                    ) {
                        append(mars.price.value.formatNumber())
                    }
                },
            )

            Text(
                text = mars.type.value,
                color = Color.Green,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}