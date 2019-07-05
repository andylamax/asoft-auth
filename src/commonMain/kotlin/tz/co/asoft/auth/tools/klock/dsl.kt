package tz.co.asoft.auth.tools.klock

import com.soywiz.klock.DateTime

fun Number.asDateTime() = DateTime.fromUnix(this.toLong())

fun DateTime.formated() = format("yyyy-MM-dd HH:mm:ss")

fun Number.asFormatedDate() = asDateTime().formated()