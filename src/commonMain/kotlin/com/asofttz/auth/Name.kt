package com.asofttz.auth

inline class Name(private val value: String = "") {
    val full get() = value

    val first get() = value.split(" ").getOrElse(0) { "" }

    val middle: String
        get() {
            val names = value.split(" ")
            return if (names.size > 2) {
                names.subList(1, names.size - 1).joinToString(" ")
            } else {
                ""
            }
        }

    val last: String
        get() {
            val names = value.split(" ")
            return if (names.size > 1) {
                names.last()
            } else {
                ""
            }
        }

    val firstLast get() = "$first $last"

    fun formated() : String = value.toLowerCase().split(" ").joinToString(" ") { it.capitalize() }

    companion object {
        val fakeNames = arrayOf("Raiden", "Anderson", "Hanzo", "Lameck", "Hasashi", "Kenshi", "Takeda", "Jackson", "Sonya", "Tremor", "Kotal", "Khan", "Cassie", "Johnny", "Cage", "Kabal", "Enenra", "Cyrax", "Sektor", "Jean", "T'Challa", "T'Chaka", "Okoye", "Wakabi")
        val fakeName
            get() = if (arrayOf(2, 3).random() == 2) {
                "${fakeNames.random()} ${fakeNames.random()}"
            } else {
                "${fakeNames.random()} ${fakeNames.random()} ${fakeNames.random()}"
            }
    }
}

fun String.asName() = Name(this)