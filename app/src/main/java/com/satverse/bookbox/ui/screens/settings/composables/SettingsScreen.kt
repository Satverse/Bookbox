    package com.satverse.bookbox.ui.screens.settings.composables

    import android.annotation.SuppressLint
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.selection.selectable
    import androidx.compose.foundation.selection.selectableGroup
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.ExperimentalMaterialApi
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.ExperimentalComposeUiApi
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.semantics.Role
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import coil.annotation.ExperimentalCoilApi
    import com.satverse.bookbox.BuildConfig
    import com.satverse.bookbox.MainActivity
    import com.satverse.bookbox.R
    import com.satverse.bookbox.ui.common.CustomTopAppBar
    import com.satverse.bookbox.ui.navigation.Screens
    import com.satverse.bookbox.ui.theme.figeronaFont
    import com.satverse.bookbox.utils.PreferenceUtil
    import com.satverse.bookbox.utils.getActivity

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    @Composable
    fun SettingsScreen(navController: NavController) {
        val context = LocalContext.current
        val viewModel = (context.getActivity() as MainActivity).settingsViewModel

        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = Modifier.padding(bottom = 70.dp),
            snackbarHost = { SnackbarHost(snackBarHostState) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CustomTopAppBar(
                    headerText = stringResource(id = R.string.settings_header),
                    iconRes = R.drawable.ic_nav_settings
                )
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    SettingsCard()
                    GeneralOptionsUI()
                    InformationUI(navController = navController)
                }
            }
        }
    }

    @Composable
    @ExperimentalMaterial3Api
    fun SettingsCard() {
        Card(
            modifier = Modifier
                .height(150.dp)
                .padding(10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${stringResource(id = R.string.app_name)} ${stringResource(id = R.string.app_desc)}",
                        fontFamily = figeronaFont,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = stringResource(id = R.string.made_by),
                        fontFamily = figeronaFont,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(horizontal = 30.dp),
                    ) {
                        Text(
                            text = "Version - ${BuildConfig.VERSION_NAME}",
                            fontFamily = figeronaFont,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .height(90.dp)
                        .width(90.dp)
                        .clip(CircleShape)
                        //  .padding(10.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(110.dp)
                    )
                }
            }
        }
    }

    @ExperimentalMaterial3Api
    @Composable
    fun GeneralOptionsUI() {
        val internalReaderValue = when (PreferenceUtil.getBoolean(
            PreferenceUtil.INTERNAL_READER_BOOL, true
        )) {
            true -> "Internal Reader"
            false -> "External Reader"
        }
        val internalReaderDialog = remember { mutableStateOf(false) }
        val radioOptions = listOf("Internal Reader", "External Reader")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(internalReaderValue) }

        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .padding(top = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.general_settings_header),
                fontFamily = figeronaFont,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            SettingItem(icon = R.drawable.ic_settings_reader,
                mainText = stringResource(id = R.string.default_reader_setting),
                subText = internalReaderValue,
                onClick = { internalReaderDialog.value = true })
        }

        if (internalReaderDialog.value) {
            AlertDialog(onDismissRequest = {
                internalReaderDialog.value = false
            }, title = {
                Text(
                    text = stringResource(id = R.string.default_reader_dialog_title),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }, text = {
                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    radioOptions.forEach { text ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.inversePrimary,
                                    disabledSelectedColor = Color.Black,
                                    disabledUnselectedColor = Color.Black
                                ),
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = figeronaFont
                            )
                        }
                    }
                }
            }, confirmButton = {
                TextButton(onClick = {
                    internalReaderDialog.value = false

                    when (selectedOption) {
                        "External Reader" -> {
                            PreferenceUtil.putBoolean(PreferenceUtil.INTERNAL_READER_BOOL, false)
                        }

                        "Internal Reader" -> {
                            PreferenceUtil.putBoolean(PreferenceUtil.INTERNAL_READER_BOOL, true)
                        }
                    }
                }) {
                    Text(stringResource(id = R.string.dialog_confirm_button))
                }
            }, dismissButton = {
                TextButton(onClick = {
                    internalReaderDialog.value = false
                }) {
                    Text(stringResource(id = R.string.cancel))
                }
            })
        }
    }

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    @Composable
    fun InformationUI(navController: NavController) {
        Box {
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.miscellaneous_setting_header),
                    fontFamily = figeronaFont,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                SettingItem(icon = R.drawable.ic_settings_license,
                    mainText = stringResource(id = R.string.license_setting),
                    subText = stringResource(id = R.string.license_setting_desc),
                    onClick = { navController.navigate(Screens.OSLScreen.route) })
                SettingItem(
                    icon = R.drawable.ic_settings_about,
                    mainText = stringResource(id = R.string.about_setting),
                    subText = stringResource(id = R.string.about_setting_desc),
                    onClick = { navController.navigate(Screens.AboutScreen.route) }
                )
                SettingItem(
                    icon = R.drawable.ic_settings_privacy_policy,
                    mainText = stringResource(id = R.string.settings_privacy_policy),
                    subText = stringResource(id = R.string.settings_privacy_policy_desc),
                    onClick = { navController.navigate(Screens.PrivacyPolicy.route) }
                )
            }
        }

    }

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    @Composable
    @Preview
    fun SettingsScreenPreview() {
        SettingsScreen(rememberNavController())
    }