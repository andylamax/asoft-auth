package tz.co.asoft.auth.tools.klock

import tz.co.asoft.klock.DateTime
import tz.co.asoft.klock.DateTimeTz

private const val message = "Do use from tz.co.asoft.klock"

@Deprecated(message)
fun Number.asDateTime() = DateTime.fromUnix(this.toLong())

@Deprecated(message)
fun DateTimeTz.formated() = format("yyyy-MM-dd HH:mm:ss")

@Deprecated(message)
fun Number.asFormatedDate() = asDateTime().local.formated()

@Deprecated(message)
fun Number.asFormatedDateOnly() = asDateTime().local.format("yyyy-MM-dd")

@Deprecated(message)
fun Number.asFormatedTimeOnly() = asDateTime().local.format("HH:mm:ss")

@Deprecated(message)
fun Number.asFormated(dateFormat: String) = asDateTime().local.format(dateFormat)