package com.yogaveda.pages.yoga

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.components.YVDropDown
import com.yogaveda.styles.modifiers.YVButtonStyle
import com.yogaveda.styles.modifiers.getButtonModifier
import com.yogaveda.styles.modifiers.getInputTextModifier
import com.yogaveda.styles.templates.YogaMainTemplate
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants.HEADER_HEIGHT
import com.yogaveda.util.Id
import kotlinx.browser.document
import kotlinx.datetime.internal.JSJoda.DateTimeFormatter
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H6
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.w3c.dom.HTMLInputElement
import kotlin.js.Date

@Page("consult")
@Composable
fun ConsultPage() {
    YogaMainTemplate { ConsultForm() }
}

@Composable
fun ConsultForm() {

    var uiState by remember { mutableStateOf(ConsultFormUiState()) }

    Column(
        modifier = Modifier.fillMaxSize()
            .backgroundColor(YogaVedaTheme.Colors.BackgroundWhitish.rgb)
            .fillMaxWidth(80.percent)
            .padding(topBottom = 24.px),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(YogaVedaTheme.Colors.White.rgb)
                .margin(HEADER_HEIGHT.px)
        ) {
            Text(value = "Online Consultation with Khushboo Bharti")
            SpanText(
                modifier = Modifier.fillMaxSize(),
                text = "Please fill out the form below. You will be redirected to the payment page on form submission to pay the consultation fees."
            )
        }
        PersonalInformation(
            patientName = uiState.patientName,
            selectedGender = uiState.gender,
            birthDate = uiState.birthDate
        ) { newState ->
            uiState = uiState.copy(gender = newState.gender)
            println("Gender Selected - ${uiState.gender}")
        }
        ContactSection()
        MedicalHistorySection()
        Button(
            attrs = YVButtonStyle.toModifier()
                .then(getButtonModifier())
                .margin(bottom = 130.px, right = 24.px)
                .onClick {
                    //context.router.navigateTo("/contact", OpenLinkStrategy.SAME_WINDOW)
                    // First validate the fields
                }
                .toAttrs()
        ) {
            SpanText(text = "Select Date")
        }
    }
}

@Composable
fun PersonalInformation(
    patientName: String = "",
    selectedGender: Gender = Gender.UNDISCLOSED,
    birthDate: String = getFormattedDate(Date()),
    uiStateChanged: (ConsultFormUiState) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .backgroundColor(YogaVedaTheme.Colors.BackgroundWhitish.rgb)
                .fillMaxWidth(80.percent)
                .padding(topBottom = 24.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            H6 { Text(value = "Personal Information") }
            Input(
                type = InputType.Text,
                attrs = getInputTextModifier()
                    .id(Id.ConsultForm.firstNameInput)
                    .toAttrs {
                        attr("placeholder", "Full Name")
                        attr("value", patientName)
                    }
            )
            Row {
                // Dropdown
                Column {
                    YVDropDown(
                        Gender.entries,
                        selectedOption = selectedGender,
                        onOptionSelect = { selectedOption ->
                            uiStateChanged(ConsultFormUiState(gender = selectedOption))
                        }
                    )
                }
            }
            Row {
                Column {
                    Text("Birth Date")
                    Input(
                        type = InputType.Date,
                        attrs = getInputTextModifier()
                            .id(Id.ConsultForm.firstNameInput)
                            .toAttrs {
                                attr("value", birthDate)
                            }
                    )
                }
            }
            Row {
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.firstNameInput)
                        .toAttrs {
                            attr("placeholder", "Full Name")
                            attr("value", patientName)
                        }
                )
            }
        }
    }
}

@Composable
fun ContactSection() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .backgroundColor(YogaVedaTheme.Colors.BackgroundWhitish.rgb)
                .fillMaxWidth(80.percent)
                .padding(topBottom = 24.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            H6 { Text(value = "Address") }
            Input(
                type = InputType.Text,
                attrs = getInputTextModifier()
                    .id(Id.ConsultForm.addressLine1Input)
                    .margin(topBottom = 8.px)
                    .toAttrs {
                        attr("placeholder", "Address Line 1")
                        attr("value", "")
                    }
            )
            Input(
                type = InputType.Text,
                attrs = getInputTextModifier()
                    .id(Id.ConsultForm.addressLine2Input)
                    .margin(topBottom = 8.px)
                    .toAttrs {
                        attr("placeholder", "Address Line 2")
                        attr("value", "")
                    }
            )
            Row (
                modifier = Modifier
                    .margin(topBottom = 8.px),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.cityInput)
                        .margin(topBottom = 8.px)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "City")
                            attr("value", "")
                        }
                )
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.stateInput)
                        .margin(topBottom = 8.px)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "State/Province")
                            attr("value", "")
                        }
                )
            }
            Row (
                modifier = Modifier
                    .margin(topBottom = 8.px),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.zipInput)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "Zip Code")
                            attr("value", "")
                        }
                )
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.countryInput)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "Country")
                            attr("value", "")
                        }
                )
            }
        }
    }
}

@Composable
fun MedicalHistorySection() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .backgroundColor(YogaVedaTheme.Colors.BackgroundWhitish.rgb)
                .fillMaxWidth(80.percent)
                .padding(topBottom = 24.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            H6 { Text(value = "Medical History") }
            Row (
                modifier = Modifier
                    .margin(topBottom = 8.px),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column {
                    Text("Are you currently taking any medications?")
                    Input(
                        type = InputType.Text,
                        attrs = getInputTextModifier()
                            .id(Id.ConsultForm.medicationsInput)
                            .margin(right = 8.px)
                            .toAttrs {
                                attr("placeholder", "Are you currently taking any medications?")
                                attr("value", "")
                            }
                    )
                }
                Column {
                    Text("Past Medical Conditions")
                    Input(
                        type = InputType.Text,
                        attrs = getInputTextModifier()
                            .id(Id.ConsultForm.pastMedicalHistoryInput)
                            .margin(right = 8.px)
                            .toAttrs {
                                attr("placeholder", "Past Medical Conditions")
                                attr("value", "")
                            }
                    )
                }
            }
            Row (
                modifier = Modifier
                    .margin(topBottom = 8.px),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.pastMedicalHistoryInput)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "Past Medical Conditions")
                            attr("value", "")
                        }
                )
            }
            TextArea(
                value = "",
                attrs = getInputTextModifier()
                    .id(Id.ConsultForm.presentComplaintsInput)
                    .margin(right = 8.px)
                    .toAttrs {
                        attr("placeholder", "Current Medical complaints")
                    }
            )
            Row (
                modifier = Modifier
                    .margin(topBottom = 8.px),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.bloodPressureInput)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "Blood Pressure")
                            attr("value", "")
                        }
                )
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.bloodSugarInput)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "Blood Sugar")
                            attr("value", "")
                        }
                )
                Input(
                    type = InputType.Text,
                    attrs = getInputTextModifier()
                        .id(Id.ConsultForm.pulseRateInput)
                        .margin(right = 8.px)
                        .toAttrs {
                            attr("placeholder", "Pulse")
                            attr("value", "")
                        }
                )
            }

            /*Div(
                attrs = Modifier
                    .toAttrs {
                        attr("class", "calendly-inline-widget")
                        attr("data-url", "https://calendly.com/b-khushboo13/30min")
                        attr("style", "min-width:320px;height:700px;")
                    }
            ) {
                // This Div element is the anchor element for the Calendar widget
            }*/

        }
    }
}

data class ConsultFormUiState(
    var patientName: String = "",
    var birthDate: String = getFormattedDate(Date()),
    var gender: Gender = Gender.UNDISCLOSED
) {
    fun reset() = this.copy(
        patientName = "",
        birthDate = Date(Date.now()).toLocaleDateString(arrayOf("IST")),
        gender = Gender.UNDISCLOSED
    )
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER,
    UNDISCLOSED
}

fun getFormattedDate(date: Date): String {
    val parsedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(date.toLocaleDateString())
    val formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(parsedDate)
    return formattedDate
}

fun validateForm() {
    if((document.getElementById(Id.ConsultForm.firstNameInput) as HTMLInputElement).value.isNotEmpty()) {
        println("Form is valid")
    } else {
        println("Form is invalid")
    }
}

/*
data class DateOptions (
    override var day: String? = "",
    override var era: String? = "",
    override var formatMatcher: String? = "",
    override var hour: String? = "",
    override var hour12: Boolean? = false,
    override var localeMatcher: String? = "Asia/Kolkata",
    override var minute: String? = "",
    override var month: String? = "",
    override var second: String? = "",
    override var timeZone: String? = "",
    override var timeZoneName: String? = "",
    override var weekday: String? = "",
    override var year: String? = ""
): Date.LocaleOptions {

    fun getUTCStandard(): Date.LocaleOptions {
        this.day = "2-digit"
        this.month = "2-digit"
        this.year = "numeric"
        return this
    }
}*/
