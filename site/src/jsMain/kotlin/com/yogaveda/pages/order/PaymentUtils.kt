package com.yogaveda.pages.order

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject


suspend fun initiatePayment(amount: Int, currency: String) {

    return try {
        //val result = window.http.get("createRazorpayOrder").decodeToString()
        val result = window.api.tryGet(
            apiPath = "createRazorpayOrder?amount=$amount&currency=$currency"
        )?.decodeToString()

        val order = Json.parseToJsonElement(result?: "{}").jsonObject

        console.log("Order: $order")

        if(!order.containsKey("id")) throw Exception("Network Error")

        val orderId = order["id"].toString()
        //val amount = order["amount"].toString().toInt()
        //val currency = order["currency"].toString()

        openRazorpayPayment(
            orderId = orderId,
            key = "rzp_test_Xt2GfFjFx1vZkx",
            amount = amount,
            currency = currency,
            name = "Yogaveda",
            description = "Test Payment",
            prefillEmail = "studioyogaveda@gmail.com",
            prefillContact = "9999999999"
        )
    } catch (e: Exception) {
        console.log(e.message)
    }
}


fun openRazorpayPayment(orderId: String, key: String, amount: Int, currency: String, name: String, description: String, prefillEmail: String, prefillContact: String) {
    val options = js("{}")
    options.key = key
    options.amount = amount
    options.currency = currency
    options.order_id = orderId.trim('"')
    options.name = name
    options.description = description
    options.prefill = js("{}")
    options.prefill.email = prefillEmail
    options.prefill.contact = prefillContact
    options.handler = { response: dynamic ->
        window.alert("Payment successful! Payment ID: ${response.razorpay_payment_id}")
    }
    options.modal = js("{}")
    options.modal.ondismiss = {
        window.alert("Payment closed")
    }

    console.log("Order ID: ${options.order_id}")
    console.log("Options: ${JSON.stringify(options)}")

    val razorpay = js("new Razorpay(options)")
    razorpay.open()
}

fun verifyPayment() {
    // Verify the payment from server
}