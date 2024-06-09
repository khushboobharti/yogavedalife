package com.yogaveda.ui

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.ElementBuilder
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTableRowElement

@Composable
fun Script(
    isAsync: Boolean = true,
    src: String,
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: ContentBuilder<HTMLElement>? = null
) {
    TagElement(
        elementBuilder = Script,
        applyAttrs = {
            if (attrs != null) apply(attrs)
            attr("src", src)
            if(isAsync) attr("async", "")
        },
        content = content
    )
}

val Script: ElementBuilder<HTMLTableRowElement> = ElementBuilderImplementation("script")

private open class ElementBuilderImplementation<TElement : Element>(private val tagName: String) : ElementBuilder<TElement> {
    private val el: Element by lazy { document.createElement(tagName) }
    @Suppress("UNCHECKED_CAST")
    override fun create(): TElement = el.cloneNode() as TElement
}

