package co.cp.orderly.infrastructure.transactions.llt

interface LongRunningTransactionStep<T> {
    fun process(data: T)
    fun rollback(data: T)
}
