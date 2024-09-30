package nl.avisi.structurizr.site.generatr.site.views

import kotlinx.html.FlowOrPhrasingContent
import kotlinx.html.a
import nl.avisi.structurizr.site.generatr.site.model.LinkViewModel

fun FlowOrPhrasingContent.link(viewModel: LinkViewModel, classes: String = "", menuLinkDest: String = "info") {
    var linkDestination = viewModel.relativeHref
    if (menuLinkDest.equals("context", ignoreCase = true)) {
        linkDestination = viewModel.relativeHref + "context"
    }
    a(
        href = linkDestination,
        classes = "$classes ${if (viewModel.active) "is-active" else ""}".trim()
    ) {
        +viewModel.title
    }
}
