package br.com.felipeomachado.entities.response

class ConverterTransactionResponse(
    val transactionId: Long,
    val userId: Long,
    val sourceCurrency : String,
    val sourceValue: Double,
    val targetCurrency: String,
    val targetValue: Double,
    val conversionRate: Double,
    val dateTime: String
)

{
    override fun toString(): String {
        return "ConverterTransactionResponse(transactionId=$transactionId, userId=$userId, sourceCurrency='$sourceCurrency', sourceValue=$sourceValue, targetCurrency='$targetCurrency', targetValue=$targetValue, conversionRate=$conversionRate, dateTime='$dateTime')"
    }
}
