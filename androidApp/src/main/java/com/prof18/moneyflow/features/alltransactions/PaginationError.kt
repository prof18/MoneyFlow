package com.prof18.moneyflow.features.alltransactions

import com.prof18.moneyflow.domain.entities.MoneyFlowError

internal data class PaginationError(
    val moneyFlowError: MoneyFlowError,
) : Throwable()
