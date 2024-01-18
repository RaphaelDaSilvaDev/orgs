package com.raphaelsilva.orgs.exeptions

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler

class CoroutineException {
    companion object{
    fun handler(context: Context): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { coroutineContext, throwable ->
            Toast.makeText(context, "Ocorreu um Problema", Toast.LENGTH_LONG).show()
        }
    }
    }
}