package co.cp.orderly.infrastructure.transactions.llt

enum class LongRunningTransactionState {
    STARTED, FAILED, SUCCEEDED, PROCESSING, COMPENSATING, COMPENSATED
}
