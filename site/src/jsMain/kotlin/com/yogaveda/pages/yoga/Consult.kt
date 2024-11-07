package com.yogaveda.pages.yoga

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onTouchEnd
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.navigation.RoutePrefix.Companion.set
import com.varabyte.kobweb.navigation.RoutePrefix.Companion.value
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.components.YVDropDown
import com.yogaveda.models.Category
import com.yogaveda.models.JsTheme
import com.yogaveda.pages.YogaPage
import com.yogaveda.pages.admin.CategoryDropdown
import com.yogaveda.pages.admin.CreatePageUiState
import com.yogaveda.styles.modifiers.getInputTextModifier
import com.yogaveda.styles.templates.YogaMainTemplate
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants.HEADER_HEIGHT
import com.yogaveda.util.Id
import kotlinx.datetime.format.DateTimeFormat
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
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
        PatientInformation(
            patientName = uiState.patientName,
            selectedGender = uiState.gender,
            birthDate = uiState.birthDate
        ) { newState ->
            uiState = uiState.copy(gender = newState.gender)
            println("Gender Selected - ${uiState.gender}")
        }
    }
}

@Composable
fun PatientInformation(
    patientName: String = "",
    selectedGender: Gender = Gender.UNDISCLOSED,
    birthDate: String = Date(Date.now()).toLocaleDateString(arrayOf("IST")),
    uiStateChanged: (ConsultFormUiState) -> Unit
) {
    Box (
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
            H1 { Text(value = "Personal Information") }
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
                            println("Gender Selected callback - $selectedOption")
                            //uiState.copy(gender = selectedOption)
                            uiStateChanged(ConsultFormUiState(gender = selectedOption))
                            //uiStateChanged()
                        }
                    )
                }
            }
            Row {
                Column {
                    Text("Birth Date")
                    println("local date - ${Date(Date.now()).toLocaleDateString(arrayOf("en-IN"), DateOptions().getUTCStandard())}")
                    Input(
                        type = InputType.Date,
                        attrs = getInputTextModifier()
                            .id(Id.ConsultForm.firstNameInput)
                            .toAttrs {
                                //attr("placeholder", Date(Date.now()).toLocaleDateString(arrayOf("IST")))
                                attr("value", birthDate)
                            }
                    )
                }
            }
        }
    }
}

data class ConsultFormUiState(
    var patientName: String = "",
    var birthDate: String = Date(Date.now()).toLocaleDateString(arrayOf("en-IN"), DateOptions().getUTCStandard()),
    var gender: Gender = Gender.UNDISCLOSED
) {
    fun reset() = this.copy(
        patientName = "",
        birthDate = Date(Date.now()).toLocaleDateString(arrayOf("IST"), DateOptions(day = "2-digit", )),
        gender = Gender.UNDISCLOSED
    )
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER,
    UNDISCLOSED
}

fun getFormattedDate(date: Date) {

    date.toLocaleDateString(arrayOf("en-IN"), DateOptions().getUTCStandard())
    //DateTimeFormat.formatAsKotlinBuilderDsl()
}

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
}