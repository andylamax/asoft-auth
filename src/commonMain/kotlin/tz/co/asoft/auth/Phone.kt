package tz.co.asoft.auth

inline class Phone(val value: String) {

    constructor(value: Number) : this(value.toString())

    val isValid get() = normalize().value.length == 12

    fun normalize(): Phone {
        var newValue = value
        if (newValue.startsWith("0")) {
            newValue = newValue.substring(1)
            return Phone(newValue).normalize()
        }

        if (newValue.startsWith("+")) {
            newValue = newValue.substring(1)
            return Phone(newValue).normalize()
        }

        if (newValue.length == 9) {
            newValue = "255$newValue"
        }
        return Phone(newValue)
    }

    companion object {
        val fakeProviderNumber = listOf(22, 61, 65, 67, 68, 71, 74, 75, 76, 78)
        val fake get() = "255" + fakeProviderNumber.random() + (1111111..9999999).random()
    }
}