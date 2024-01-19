package com.raphaelsilva.orgs.extensions

import java.security.MessageDigest

fun String.toHash(
    value: String = "SHA-256"
): String {
    return MessageDigest.getInstance(value).digest(this.toByteArray()).fold("") { str, byte ->
            str + "%02x".format(byte)
        }
}