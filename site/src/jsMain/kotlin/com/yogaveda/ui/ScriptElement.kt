package com.yogaveda.ui

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.ElementBuilder
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLScriptElement

@Composable
fun Script(
    isAsync: Boolean = true,
    src: String? = null,
    attrs: AttrBuilderContext<HTMLScriptElement>? = null,
    content: ContentBuilder<HTMLScriptElement>? = null
) {
    TagElement(
        elementBuilder = Script,
        applyAttrs = {
            if (attrs != null) apply(attrs)
            if (src != null) attr("src", src)
            if(isAsync) attr("async", "")
        },
        content = content
    )
}

val Script: ElementBuilder<HTMLScriptElement> = ElementBuilderImplementation("script")

private open class ElementBuilderImplementation<TElement : Element>(private val tagName: String) : ElementBuilder<TElement> {
    private val el: Element by lazy { document.createElement(tagName) }
    @Suppress("UNCHECKED_CAST")
    override fun create(): TElement = el.cloneNode() as TElement
}


