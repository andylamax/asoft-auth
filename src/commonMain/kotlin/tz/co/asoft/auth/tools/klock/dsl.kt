package tz.co.asoft.auth.tools.klock

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

fun Number.asDateTime() = DateTime.fromUnix(this.toLong())

fun DateTimeTz.formated() = format("yyyy-MM-dd HH:mm:ss")

fun Number.asFormatedDate() = asDateTime().local.formated()

fun Number.asFormatedDateOnly() = asDateTime().local.format("yyyy-MM-dd")

fun Number.asFormatedTimeOnly() = asDateTime().local.format("HH:mm:ss")

fun Number.asFormated(dateFormat: String) = asDateTime().local.format(dateFormat)