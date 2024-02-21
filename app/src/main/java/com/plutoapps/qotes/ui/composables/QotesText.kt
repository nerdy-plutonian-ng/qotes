package com.plutoapps.qotes.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoapps.qotes.data.models.Qote

@Composable
fun QoteText(modifier: Modifier = Modifier, qote: Qote) {
    val quoteMarksStyle = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 48.sp)
    val quoteStyle = SpanStyle(fontWeight = FontWeight.SemiBold, fontSize = 24.sp,)
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        PulseRing()
        PulseRing(isSecondary = false,isSmall = false)
        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                buildAnnotatedString {
                    withStyle(style = quoteMarksStyle) {
                        append("“")
                    }
                    withStyle(style = quoteStyle,) {
                        append(qote.quote)
                    }
                    withStyle(style = quoteMarksStyle) {
                        append("”")
                    }
                }, textAlign = TextAlign.Center, lineHeight = 48.sp
            )
            Spacer(modifier = modifier.height(8.dp))
            Text("- ${qote.author}", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = modifier.height(16.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QoteTextPreview() {
    QoteText(
        qote = Qote(quote = "This is a quote. It is of moderate length and very inspiring.", author = "John Doe", category = "happiness",id= "Id", date = "1706186888349"))

}