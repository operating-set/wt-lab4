package com.application.service.creator;

import com.application.dao.Transaction;

public abstract class BaseCreator {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
