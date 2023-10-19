/**
 * Business service for CRUD operations on Widgets.
 * 
 * BusinessService defines the transaction boundaries for the database operations.
 * The commits and rollbacks are implemented in the TransactionManager.
 */

const TransactionManager = require('../dao/transaction-manager');
const ProductDao = require('../dao/product-dao');

class BusinessService {
    constructor() {
        this.transactionManager = new TransactionManager();
        this.dao = new ProductDao(this.transactionManager);
    }

    async queryForAllWidgets() {
        await this.transactionManager.startTransaction();
        try {
            const widgets = await this.dao.queryForAllWidgets();
            return widgets;
        }
        finally {
            await this.transactionManager.rollbackTransaction();
        }
    }

    async queryForWidget(id) {
        if (!id || id <= 0) {
            throw new Error(`invalid ID ${id}: must be greater than 0`);
        }
        await this.transactionManager.startTransaction();
        try {
            return await this.dao.queryForWidget(id);
        }
        finally {
            await this.transactionManager.rollbackTransaction();
        }
    }

    async createWidget(w) {
        if (!(w && w.id && w.description && w.price && w.gears && w.sprockets)) {
            throw new Error(`invalid widget ${w}`);
        }
        await this.transactionManager.startTransaction();
        try {
            const rowsAffected = await this.dao.createWidget(w);

            await this.transactionManager.commitTransaction();
            return rowsAffected;
        }
        catch (error) {
            await this.transactionManager.rollbackTransaction();
            throw error;
        }
    }

    async updateWidget(w) {
        if (!(w && w.id && w.description && w.price && w.gears && w.sprockets)) {
            throw new Error(`invalid widget ${w}`);
        }
        await this.transactionManager.startTransaction();
        try {
            const rowsAffected = await this.dao.updateWidget(w);

            await this.transactionManager.commitTransaction();
            return rowsAffected;
        }
        catch (error) {
            await this.transactionManager.rollbackTransaction();
            throw error;
        }
    }

    async deleteWidget(id) {
        if (!id || id <= 0) {
            throw new Error(`invalid ID ${id}: must be greater than 0`);
        }
        await this.transactionManager.startTransaction();
        try {
            const rowsAffected = await this.dao.deleteWidget(id);

            await this.transactionManager.commitTransaction();
            return rowsAffected;
        }
        catch (error) {
            await this.transactionManager.rollbackTransaction();
            throw error;
        }
    }
}

module.exports = BusinessService;
