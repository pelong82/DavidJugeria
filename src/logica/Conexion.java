/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Item;
import modelo.Sale;
import modelo.SaleDetail;


/**
 *
 * @author Administrator
 */
public class Conexion implements ConexionSQL{
    
    private Connection conexion;
    private PreparedStatement prdsItemsAll;
    private PreparedStatement prdsCategoriaAll;
    private PreparedStatement prdsItemInsert;
    private PreparedStatement prdstItemDelete;
    private PreparedStatement prdstItemUpdate;
    private PreparedStatement prdstItemLastId;
    private PreparedStatement prdstSaleInsert;
    private PreparedStatement prdstSaleDetailInsert;
    private int lastIdItem;
    
    
    public Conexion(){
        conectar();
        lastIdItem = 0;
    }
    
    /**
     * Cierra la Conexion con
     * la DB
     */
    public void close(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Carga el driver para la conexion
     * con SQLITE
     */
    private void conectar(){
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:"+URL);
            System.out.println("Conexion Establecida....");
            makeSQL();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Elimina un item
     * @param id id del item a eliminar
     * @return regresa true si tuvo exito
     */
    public boolean deleteItem(int id){
        try {
            prdstItemDelete.setInt(1, id);
            boolean execute = prdstItemDelete.execute();
            return !execute;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No Se Puede Eliminar El Producto", "Conexion", JOptionPane.ERROR_MESSAGE);
            return true;
        }
    }
    
    
    /**
     * Recupera las categorias de la DB
     * @return ArrayList
     */
    public ArrayList<Categoria> getCategorias(){
        try {
            ResultSet resQuery = prdsCategoriaAll.executeQuery();
            ArrayList<Categoria> categorias = new ArrayList<>();
            while(resQuery.next()){
                Categoria cat  = new Categoria();
                cat.setId(resQuery.getInt("categoria_id"));
                cat.setNombre(resQuery.getString("name"));
                categorias.add(cat);
            }
            return categorias;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Imposible Obtener Las Categorias", "Conexion", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
    }
    
    /**
     * Recupera todos los items de la
     * BD
     * @return ArrayList con los items
     */
    public ArrayList<Item> getItems(){
        try {
            ResultSet resQuery = prdsItemsAll.executeQuery();
            ArrayList<Item> items = new ArrayList<>();
            while(resQuery.next()){
                Item item = new Item();
                item.setId(resQuery.getInt("item_id"));
                item.setName(resQuery.getString("name"));
                item.setStock(resQuery.getInt("stock"));
                item.setMarca(resQuery.getString("brand"));
                item.setPrecio(resQuery.getDouble("price"));
                item.getCategoria().setId(resQuery.getInt("categoria_id"));
                item.getCategoria().setNombre(resQuery.getString("cat_name"));
                items.add(item);
            }
            return items;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Imposible obtener los Productos", "Conexion", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * obtien el ultimo id del item 
     * insertado en la DB
     * @return regresa un entero que es la
     * id del item
     */
    public int getLastIdItem(){
        return lastIdItem;
    }
    
    /**
     * regresa el ultimo id de la tabla sales
     * @return regresa un entero
     */
    public int getLastIdSale(){
        try {
            ResultSet resExecute = prdstItemLastId.executeQuery();
            int id = 0;
            while(resExecute.next()){
                id = resExecute.getInt(1);
            }
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    /**
     * Crea las Sentencias SQL
     */
    private void makeSQL(){
        try {
            prdsItemsAll = conexion.prepareStatement(SELECT_ALL_ITEMS);
            prdsCategoriaAll = conexion.prepareStatement(SELECT_ALL_CATEGORIAS);
            prdsItemInsert = conexion.prepareStatement(INSERT_ITEMS, Statement.RETURN_GENERATED_KEYS);
            prdstItemDelete = conexion.prepareStatement(DELETE_ITEMS);
            prdstItemUpdate = conexion.prepareStatement(UPDATE_ITEMS);
            prdstItemLastId = conexion.prepareStatement(SELECT_LAST_ID_SALES);
            prdstSaleInsert = conexion.prepareStatement(INSERT_SALES, Statement.RETURN_GENERATED_KEYS);
            prdstSaleDetailInsert = conexion.prepareStatement(INSERT_SALES_DETAILS);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Guarda un objeto item en 
     * la BD
     * @param item objeto producto
     * @return regresa true si tuvo exito
     */
    public boolean saveItem(Item item){
        try {
            prdsItemInsert.setString(1, item.getName());
            prdsItemInsert.setInt(2, item.getCategoria().getId());
            prdsItemInsert.setString(3, item.getMarca());
            prdsItemInsert.setDouble(4, item.getPrecio());
            prdsItemInsert.setInt(5, item.getStock());
            boolean execute = prdsItemInsert.execute();
            ResultSet resKeys = prdsItemInsert.getGeneratedKeys();
            while(resKeys.next()){
                lastIdItem = resKeys.getInt(1);
            }
            return !execute;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No Se Puede Guardar El Producto", "Conexion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean saveSale(Sale sale){
        try {
            prdstSaleInsert.setDouble(1, sale.getSubtotal());
            prdstSaleInsert.setDouble(2, sale.getTotal());
            prdstSaleInsert.setDouble(3, sale.getPayment());
            prdstSaleInsert.setDouble(4, sale.getChange_payment());
            boolean res = prdstSaleInsert.execute();
            ResultSet keys = prdstSaleInsert.getGeneratedKeys();
            while(keys.next()){
                sale.setSaleId(keys.getInt(1));
            }
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * guarda los items de la venta en la tabla details
     * @param sale
     * @return 
     */
    public boolean saveSaleDetail(Sale sale){
        ArrayList<SaleDetail> listDetails = sale.getListDetails();
        boolean resSave = false;
        for(SaleDetail saleDetail : listDetails){
            try {
                prdstSaleDetailInsert.setInt(1, sale.getSaleId());
                prdstSaleDetailInsert.setInt(2, saleDetail.getItem().getId());
                prdstSaleDetailInsert.setInt(3, saleDetail.getQuantity());
                prdstSaleDetailInsert.setDouble(4, saleDetail.getAmount());
                resSave = prdstSaleDetailInsert.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                resSave = true;
            }
        }
        return !resSave;
    }
    
    /**
     * Asigna valor a la ultima id en
     * la tabla items
     * @param id id del item
     */
    public void setLastIdItem(int id){
        lastIdItem = id;
    }
    
    /**
     * Actualiza un objeto item en la 
     * BD
     * @param item producto
     * @return regresa true si tubo exito
     */
    public boolean updateItem(Item item){
        try {
            prdstItemUpdate.setString(1, item.getName());
            prdstItemUpdate.setInt(2, item.getCategoria().getId());
            prdstItemUpdate.setString(3, item.getMarca());
            prdstItemUpdate.setDouble(4, item.getPrecio());
            prdstItemUpdate.setInt(5, item.getStock());
            prdstItemUpdate.setInt(6, item.getId());
            boolean execute = prdstItemUpdate.execute();
            return !execute;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error Al Actualizar Los Datos", "Conexion", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
}
