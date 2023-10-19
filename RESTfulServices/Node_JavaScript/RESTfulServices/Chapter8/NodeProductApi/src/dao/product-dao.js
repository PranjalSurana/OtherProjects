/**
 * DAO for CRUD operations on Widgets in an Oracle database.
 *
 * See https://oracle.github.io/node-oracledb/
 * See https://www.oracle.com/database/technologies/appdev/quickstartnodeonprem.html
 * See https://node-oracledb.readthedocs.io/en/latest/user_guide/introduction.html
 * See http://oracle.github.io/node-oracledb/doc/api.html
 */

const dbUtils = require('./db-utils');
const Widget = require('../model/widget');

class ProductDao {
    // connectionProvider could be a TransactionManager
    constructor(connectionProvider) {  
        this.connectionProvider = connectionProvider;
    }

    async queryForAllWidgets() {
        const sql = `
            select id, description, price, gears, sprockets 
              from widgets
             order by id
        `;
        const widgets = [];

        const result = await this.connectionProvider.connection.execute(
                                    sql, {}, dbUtils.executeOpts);

        const rs = result.resultSet;
        let row;
        while ((row = await rs.getRow())) {
            const widget = new Widget(row.ID, row.DESCRIPTION, row.PRICE,  
                                      row.GEARS, row.SPROCKETS);
            widgets.push(widget);
        }
        await rs.close();

        return widgets;
    }

    async queryForWidget(id) {
        const sql = `
            select id, description, price, gears, sprockets 
              from widgets
             where id = :id
        `;

        const result = await this.connectionProvider.connection.execute(
                                    sql, [id], dbUtils.executeOpts);

        const rs = result.resultSet;
        let row = await rs.getRow();
        if (row) {
            const widget = new Widget(row.ID, row.DESCRIPTION, row.PRICE, 
                                      row.GEARS, row.SPROCKETS);
            return widget;
        }
        await rs.close();
    }

    async createWidget(w) {
        const sql = `
            insert into widgets (id, description, price, gears, sprockets) 
                    values(:id, :descr, :price, :gears, :sprockets)
        `;
        const bindParamValues = {
            id: w.id,
            descr: w.description,
            price: w.price,
            gears: w.gears,
            sprockets: w.sprockets
        };

        const result = await this.connectionProvider.connection.execute(
                                    sql, bindParamValues, dbUtils.executeOpts);
        return result.rowsAffected;
    }

    async updateWidget(w) {
        const sql = `
            update widgets 
               set description = :descr, 
                   price = :price, 
                   gears = :gears, 
                   sprockets = :sprockets
             where id = :id
        `;

        const bindParamValues = {
            id: w.id,
            descr: w.description,
            price: w.price,
            gears: w.gears,
            sprockets: w.sprockets
        };

        const result = await this.connectionProvider.connection.execute(
                                    sql, bindParamValues, dbUtils.executeOpts);
        return result.rowsAffected;
    }

    async deleteWidget(id) {
        const sql = `
           delete 
             from widgets 
            where id = :id
        `;

        const result = await this.connectionProvider.connection.execute(
                                    sql, [id], dbUtils.executeOpts);

        return result.rowsAffected;
    }

    async shutdown() {
    }
}

module.exports = ProductDao;
