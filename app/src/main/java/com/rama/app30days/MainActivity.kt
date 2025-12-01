package com.rama.app30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.rama.app30days.data.Data
import com.rama.app30days.model.DayIdea
import com.rama.app30days.ui.theme.App30DaysTheme
import com.rama.app30days.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App30DaysTheme(dynamicColor = false) {
                ThirtyDayApp()
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDayApp() {
    Surface(modifier = Modifier.fillMaxSize()) {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        val ideasList = Data.listOfIdeas

        Scaffold(
            topBar = { TitleBar(scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { paddingValues ->

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.ten_dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(dimensionResource(R.dimen.ten_dp)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(ideasList) { idea ->

                    val dayOfMonth = (ideasList.indexOf(idea).inc())

                    DayItem(dayOfMonth, idea)
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string._30_days_of_recipes_text),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}


@Composable
fun DayItem(
    dayOfMonth: Int,
    dayIdea: DayIdea,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val targetImageWeight = if (expanded) 0.4f else 0.7f
    val imageWeight by animateFloatAsState(targetValue = targetImageWeight)
    val infoWeight = 1f - imageWeight

    Card(
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.two_dp)),
        modifier = modifier
            .size(
                width = dimensionResource(R.dimen.five_hundred_dp),
                height = dimensionResource(R.dimen.four_hundred_dp)
            )
            .clickable { expanded = !expanded }
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CardImage(dayIdea, Modifier.weight(imageWeight))
            CardInformation(dayOfMonth, dayIdea, Modifier.weight(infoWeight), expanded)
        }
    }
}

@Composable
fun CardImage(
    dayIdea: DayIdea,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    bottomStart = dimensionResource(R.dimen.twelve_dp),
                    bottomEnd = dimensionResource(R.dimen.twelve_dp)
                )
            )
    ) {
        Image(
            painter = painterResource(dayIdea.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
private fun CardInformation(
    dayOfMonth: Int,
    dayIdea: DayIdea,
    modifier: Modifier = Modifier,
    expanded: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.twenty_dp))

    ) {
        CardHeadline(dayOfMonth, dayIdea)

        Spacer(Modifier.height(dimensionResource(R.dimen.ten_dp)))

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                Text(
                    text = stringResource(dayIdea.description),
                    style = Typography.bodyLarge,
                    overflow = TextOverflow.Visible
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = stringResource(dayIdea.reference),
                    style = Typography.labelSmall,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        if (!expanded) {
            Text(
                text = stringResource(dayIdea.reference),
                style = Typography.labelSmall,
                overflow = TextOverflow.Visible,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun CardHeadline(
    dayOfMonth: Int,
    dayIdea: DayIdea
) {
    Row {
        Text(
            text = stringResource(R.string.day_text, dayOfMonth),
            style = Typography.headlineMedium,
            overflow = TextOverflow.Visible
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.ten_dp)))

        Text(
            text = stringResource(dayIdea.title),
            style = Typography.headlineMedium,
            overflow = TextOverflow.Visible
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThirtyDayAppPreview() {
    App30DaysTheme(darkTheme = true) {
        ThirtyDayApp()
    }
}

@Preview(showBackground = true)
@Composable
fun DayItemLightPreview() {
    App30DaysTheme(darkTheme = false) {
        DayItem(1, Data.listOfIdeas.first())
    }
}

@Preview(showBackground = true)
@Composable
fun DayItemDarkPreview() {
    App30DaysTheme(darkTheme = true) {
        DayItem(1, Data.listOfIdeas.first())
    }
}