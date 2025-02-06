package com.yogaveda.api

import com.razorpay.Order
import com.razorpay.RazorpayClient
import com.razorpay.RazorpayException
import com.razorpay.Utils
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import org.json.JSONObject

@Api(routeOverride = "createRazorpayOrder")
fun createRazorpayOrder(ctx: ApiContext) {
    /* Live Mode
    val apiKey = "rzp_live_QOgS2rIb8RLXKj"
    val apiSecret = "VMcRknu34kcntpAj95YFkBMK"
    */

    // Test Mode
    val apiKey = "rzp_test_Xt2GfFjFx1vZkx"
    val apiSecret = "DQOi795VlhSk8TahK3THnbV6"


    val razorpayClient = RazorpayClient(apiKey, apiSecret)

    val amount = ctx.req.params["amount"].toString().toInt()
    val currency = ctx.req.params["currency"].toString()

    ctx.logger.debug("Amount: $amount, Currency: $currency")

    val orderRequest = JSONObject().apply {
        put("amount", amount) // Amount in paise (e.g., 50000 paise = ₹500)
        put("currency", currency)
        put("receipt", "order_receipt_12345")
        put("payment_capture", 1) // Auto-capture payment
    }

    try {
        val order: Order = razorpayClient.orders.create(orderRequest)

        ctx.logger.debug("Order - $order")

        ctx.res.setBodyText(order.toString())
        ctx.res.status = 200
    } catch (e: RazorpayException) {
        ctx.res.setBodyText("Error creating order: ${e.message}")
        ctx.res.status = 500
    }
}


@Api(routeOverride = "verifyPayment")
fun verifyPayment(ctx: ApiContext) {
    val apiKey = "rzp_test_Xt2GfFjFx1vZkx"
    val apiSecret = "DQOi795VlhSk8TahK3THnbV6"

    val razorpayClient = RazorpayClient(apiKey, apiSecret)

    val paymentId = ctx.req.params["payment_id"] ?: ""
    val orderId = ctx.req.params["order_id"] ?: ""
    val signature = ctx.req.params["signature"] ?: ""

    val isValidSignature = Utils.verifyPaymentSignature(
        JSONObject(mapOf(
            "razorpay_payment_id" to paymentId,
            "razorpay_order_id" to orderId,
            "razorpay_signature" to signature
        )),
        apiSecret
    )

    if (isValidSignature) {
        ctx.res.setBodyText("Payment verified successfully!")
        ctx.res.status = 200
    } else {
        ctx.res.setBodyText("Payment verification failed!")
        ctx.res.status = 400
    }
}