/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author Administrator
 */
public interface ConexionSQL {
    
    public static final String URL = "C:\\FruteriaDB\\FruteriaDB.sqlite";
    public static final String SELECT_ALL_CATEGORIAS = "SELECT * FROM categorias";
    public static final String SELECT_ALL_ITEMS = "SELECT items.item_id, items.name, items.brand, items.price, items.stock, "
            + "cat.categoria_id, cat.name AS cat_name FROM items INNER JOIN categorias AS cat ON cat.categoria_id = items.categoria_id";
    public static final String INSERT_ITEMS = "INSERT INTO items(name, categoria_id, brand, price, stock) VALUES(?,?,?,?,?)";
    public static final String DELETE_ITEMS = "DELETE FROM items WHERE item_id = ?";
    public static final String UPDATE_ITEMS = "UPDATE items SET name=?, categoria_id=?, brand=?, price=?, stock=? WHERE item_id = ?";
    public static final String SELECT_LAST_ID_SALES = "SELECT LAST_INSERT_ROWID() FROM sales";
    public static final String INSERT_SALES = "INSERT INTO SALES(subtotal, total, payment, change_payment) VALUES(?, ?, ?, ?)";
    public static final String INSERT_SALES_DETAILS = "INSERT INTO sales_details(sale_id, item_id, quantity, amount) VALUES(?, ?, ?, ?)";
}
