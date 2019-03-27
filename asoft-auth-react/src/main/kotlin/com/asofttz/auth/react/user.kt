package com.asofttz.auth.react

import com.asofttz.auth.User
import com.asofttz.theme.Theme
import kotlinext.js.require
import kotlinx.css.*
import react.RBuilder
import styled.*

fun RBuilder.user(user: User, theme: Theme) = styledDiv {
    val pic = if (user.profilePic.isBlank()) require("self_logo.png") else user.profilePic
    css {
        backgroundColor = theme.backgroundColor.light.toColor()
        color = theme.text.onBackground.light.toColor()
        width = 15.em
        display = Display.flex
        alignItems = Align.center
    }
    styledDiv {
        css {
            flexBasis = FlexBasis("20%")
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            padding(0.1.em)
        }

        styledImg(alt = "User Picture", src = pic as String) {
            css {
                width = 100.pct - 0.2.em
            }
        }
    }

    styledDiv {
        css {
            flexBasis = FlexBasis("80%")
            display = Display.flex
            flexDirection = FlexDirection.column
            overflowX = Overflow.hidden
            fontSize = 0.9.em
        }

        styledSpan {
            css {
                whiteSpace = WhiteSpace.nowrap
            }
            +user.name
        }

        styledSpan {
            css {
                whiteSpace = WhiteSpace.nowrap
            }
            +user.emails[0]
        }

        styledSpan {
            css {
                whiteSpace = WhiteSpace.nowrap
            }
            +user.phones[0]
        }
    }
}