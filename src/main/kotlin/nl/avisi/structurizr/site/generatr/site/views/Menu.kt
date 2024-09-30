package nl.avisi.structurizr.site.generatr.site.views

import kotlinx.html.*
import nl.avisi.structurizr.site.generatr.site.model.LinkViewModel
import nl.avisi.structurizr.site.generatr.site.model.MenuNodeViewModel
import nl.avisi.structurizr.site.generatr.site.model.MenuViewModel

fun DIV.menu(viewModel: MenuViewModel, nestGroups: Boolean, menuLinkDest: String) {
    aside(classes = "menu p-3") {
        generalSection(viewModel.generalItems)
        softwareSystemsSection(viewModel, nestGroups, menuLinkDest)
    }
}

private fun ASIDE.generalSection(items: List<LinkViewModel>) {
    p(classes = "menu-label") { +"General" }
    menuItemLinks(items, "")
}

private fun ASIDE.softwareSystemsSection(viewModel: MenuViewModel, nestGroups: Boolean, menuLinkDest: String) {
    p(classes = "menu-label") { +"Software systems" }
    if (nestGroups) {
        ul(classes = "listree menu-list has-site-branding") {
            buildHtmlTree(viewModel.softwareSystemNodes(), viewModel, menuLinkDest).invoke(this)
        }
    } else {
        menuItemLinks(viewModel.softwareSystemItems, menuLinkDest)
    }
}

private fun ASIDE.menuItemLinks(items: List<LinkViewModel>, menuLinkDest: String) {
    ul(classes = "menu-list has-site-branding") {
        li {
            items.forEach {
                link(it,"",menuLinkDest)
            }
        }
    }
}

private fun buildHtmlTree(node: MenuNodeViewModel, viewModel: MenuViewModel, menuLinkDest: String): UL.() -> Unit = {
    if (node.name.isNotEmpty() && node.children.isEmpty()) {
        val itemLink = viewModel.softwareSystemItems.find { it.title == node.name }
        li {
            if (itemLink != null) {
                link(itemLink,"", menuLinkDest)
            }
        }
    }

    if (node.name.isNotEmpty() && node.children.isNotEmpty()) {
        li {
            div(classes = "listree-submenu-heading") {
                +node.name
            }
            ul(classes = "listree-submenu-items") {
                for (child in node.children) {
                    buildHtmlTree(child, viewModel, menuLinkDest).invoke(this)
                }
            }
        }
    }

    if (node.name.isEmpty() && node.children.isNotEmpty()) {
        ul(classes = "listree-submenu-items") {
            for (child in node.children) {
                buildHtmlTree(child, viewModel, menuLinkDest).invoke(this)
            }
        }
    }
}
