package com.plutoapps.qotes.ui.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.background
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.plutoapps.qotes.QotesApplication
import com.plutoapps.qotes.data.models.Qote

class MyAppWidget : GlanceAppWidget() {

    companion object {

        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(SMALL_SQUARE, HORIZONTAL_RECTANGLE, BIG_SQUARE)
    )


    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            MyContent()
        }
    }

    @Composable
    private fun MyContent(modifier: GlanceModifier = GlanceModifier) {

        var qote by remember {
            mutableStateOf<Qote?>(null)
        }

        LaunchedEffect(key1 = Key(5465), block = {
            qote = QotesApplication.userPreferencesRepository?.getTodaysQote()
        })

        Box(modifier= modifier.background(day = Color.White, night = Color.White)) {
            if(qote == null)
                Text(text = "...loading...")
                //CircularProgressIndicator()
            else
                LazyColumn(
                    modifier = modifier.fillMaxSize().padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    item { Text(text = "Qote", style =TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) }
                    item { Text(text = qote!!.quote, style =TextStyle(fontSize = 18.sp)) }


                }
        }

        //Text(text = if(qote == null) "loading" else qote!!.quote)
    }
}