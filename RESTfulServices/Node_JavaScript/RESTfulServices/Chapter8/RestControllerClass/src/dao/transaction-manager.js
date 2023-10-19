/**
 * TransactionManager implements the commit and rollback operations for 
 * database transactions. 
 */

const dbUtils = require('../dao/db-utils');

class TransactionManager {
    constructor() {
    }

    async startTransaction() {
        await this.openConnection();
    }

    async openConnection() {
        this.connection = await dbUtils.getConnection();
    }

    async commitTransaction() {
        try {
            await this.connection.commit();
        }
        finally {
            await this.closeConnection()
        }
    }

    async rollbackTransaction() {
        try {
            await this.connection.rollback();
        }
        finally {
            await this.closeConnection()
        }
    }

    async closeConnection() {
        try {
            await dbUtils.closeConnection(this.connection);
        }
        catch (error) {
            console.error(`exception while closing database connection: ${error}`);
            throw error;
        }
    }

    async shutdown() {
        await this.closeConnection();
    }
}

module.exports = TransactionManager;
