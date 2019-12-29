package tz.co.asoft.auth.usecase.permissions

import tz.co.asoft.auth.User

fun User.hasPermit(perm: String): Boolean {
    if (permits.contains(":dev")) {
        return true
    }
    return permits.indexOfFirst { it == perm } >= 0
}

fun User.hasPermits(perms: Array<String>) = hasPermits(perms.toSet())

fun User.hasPermits(perms: Collection<String>): Boolean {
    var hasPerms = false
    perms.forEach { perm ->
        hasPerms = hasPermit(perm)
        if (hasPerms)
            return true
    }
    return hasPerms
}