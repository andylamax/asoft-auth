package tz.co.asoft.auth.tools.phone

class Phone {
    companion object {
        val fakeProviderNumber = listOf(22, 61, 65, 67, 68, 71, 74, 75, 76, 78)
        val fake get() = "255" + fakeProviderNumber.random() + (1111111..9999999).random()
    }
}