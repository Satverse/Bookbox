package com.satverse.bookbox.ui.screens.settings.composables

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.satverse.bookbox.BuildConfig
import com.satverse.bookbox.R
import com.satverse.bookbox.others.Constants
import com.satverse.bookbox.ui.common.CustomTopAppBar
import com.satverse.bookbox.ui.theme.figeronaFont


@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            CustomTopAppBar(headerText = stringResource(id = R.string.about_header)) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            4.dp
                        )
                    ),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(148.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Image(
                                modifier = Modifier.size(120.dp),
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null
                            )
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 26.sp,
                        fontFamily = figeronaFont,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Version ${BuildConfig.VERSION_NAME}",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        fontFamily = figeronaFont,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.about_desc),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, end = 22.dp),
                        fontSize = 14.sp,
                        fontFamily = figeronaFont,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Row {
                            LinkButton(
                                text = "Github",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_github_logo)
                            ) {
                                openWebLink(context, Constants.REPO_URL)
                            }

                            LinkButton(
                                text = "Twitter",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_twitter_logo)
                            ) {
                                openWebLink(context, Constants.TWITTER_URL)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id = R.string.developed_by),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 12.dp),
                    fontSize = 16.sp,
                    fontFamily = figeronaFont,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                Card(
                    modifier = Modifier
                        .height(135.dp)
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            2.dp
                        )
                    ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://avatars.githubusercontent.com/u/72562562?v=4")
                                .error(R.drawable.github_pfp).build(),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(90.dp)
                                .clip(CircleShape),
                            contentDescription = null,
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.dev_name),
                                fontSize = 18.sp,
                                fontFamily = figeronaFont,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground,
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = stringResource(id = R.string.dev_username),
                                fontSize = 16.sp,
                                fontFamily = figeronaFont,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row {
                                LinkButton(
                                    text = "Github",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_github_logo)
                                ) {
                                    openWebLink(context, Constants.DEV_GITHUB_URL)
                                }

                                LinkButton(
                                    text = "Twitter",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_twitter_logo)
                                ) {
                                    openWebLink(context, Constants.DEV_TWITTER_URL)
                                }
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .height(135.dp)
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 14.dp, top = 14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            2.dp
                        )
                    ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://avatars.githubusercontent.com/u/127011451?v=4")
                                .error(R.drawable.github_pfp).build(),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(90.dp)
                                .clip(CircleShape),
                            contentDescription = null,
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.vivek_name),
                                fontSize = 18.sp,
                                fontFamily = figeronaFont,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground,
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = stringResource(id = R.string.vivek_username),
                                fontSize = 16.sp,
                                fontFamily = figeronaFont,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row {
                                LinkButton(
                                    text = "Github",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_github_logo)
                                ) {
                                    openWebLink(context, Constants.VIVEK_GITHUB_URL)
                                }

                                LinkButton(
                                    text = "Twitter",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_twitter_logo)
                                ) {
                                    openWebLink(context, Constants.VIVEK_TWITTER_URL)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun LinkButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(all = 4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(size = 18.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = text.uppercase(),
            fontWeight = FontWeight.Bold,
            fontFamily = figeronaFont,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}

fun openWebLink(context: Context, url: String) {
    val uri: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        context.startActivity(intent)
    } catch (exc: ActivityNotFoundException) {
        exc.printStackTrace()
    }
}

@Composable
@Preview(showBackground = true)
fun AboutScreenPreview() {
    AboutScreen(rememberNavController())
}