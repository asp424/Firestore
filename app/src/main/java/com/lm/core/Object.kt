package com.lm.core

interface Object<out T> {
    fun <U> map(mapper: Mapper.DataToUI<in T, U>): U
}