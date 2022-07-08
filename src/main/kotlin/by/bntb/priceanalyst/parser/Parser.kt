package by.bntb.priceanalyst.parser

interface Parser<T> {
    fun parse(document: String): Collection<T>
}