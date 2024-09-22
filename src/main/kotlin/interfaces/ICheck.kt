package me.vaan.interfaces

interface ICheck<T> {

    /**
     * The implementation of this method is meant
     * to first check the value and then update it accordingly
     */
    fun check(key: String, entry: T) : Boolean

    /**
     * The implementation of this method is for
     * only checking the value and not updating it
     */
    fun softCheck(key: String, entry: T) : Boolean
}