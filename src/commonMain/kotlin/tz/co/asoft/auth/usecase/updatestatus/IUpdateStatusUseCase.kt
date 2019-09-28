package tz.co.asoft.auth.usecase.updatestatus

import tz.co.asoft.auth.User

interface IUpdateStatusUseCase {
    operator fun invoke(u: User, s: User.Status)
}