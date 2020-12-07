package br.com.felipeomachado.entities

import br.com.felipeomachado.db.ConverterTransactions
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime


class ConverterTransaction(id: EntityID<Long>?) : LongEntity(id!!) {
    companion object : LongEntityClass<ConverterTransaction>(ConverterTransactions)

    var userId by ConverterTransactions.userId
    var sourceCurrency by ConverterTransactions.sourceCurrency
    var sourceValue by ConverterTransactions.sourceValue
    var targetCurrency by ConverterTransactions.targetCurrency
    var targetValue by ConverterTransactions.targetValue
    var conversionRate by ConverterTransactions.conversionRate
    var dateTime: LocalDateTime by ConverterTransactions.dateTime





}
