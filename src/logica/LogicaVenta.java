/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Item;

/**
 *
 * @author Administrator
 */
public class LogicaVenta {
    
    private Conexion conexion;
    private Item lastItem;
    
    public LogicaVenta(){
        conexion = new Conexion();
        lastItem = null;
    }

    public void close(){
        conexion.close();
    }
    
    public boolean deleteItem(int id){
        return conexion.deleteItem(id);
    }
    
    public ArrayList<Categoria> getCategorias(){
        return conexion.getCategorias();
    }
    
    public ArrayList<Item> getItems(){
       return conexion.getItems();
    }
    
    public int getLastIdItem(){
        return conexion.getLastIdItem();
    }
    
    public Item getLastItem() {
        return lastItem;
    }
    
    /**
     * regresa el ultimo id de la tabla sales
     * @return 
     */
    public int getLastIdSale(){
        return conexion.getLastIdSale();
    }
    
    public void setLastIdItem(int id){
        conexion.setLastIdItem(id);
    }
    
    public void setLastItem(Item lastItem) {
        this.lastItem = lastItem;
    }
    
    public boolean saveItem(Item item){
        return conexion.saveItem(item);
    }

    /**
     * guarda el item
     * @param desc
     * @param marca
     * @param precio
     * @param stock
     * @param categoria
     * @return 
     */
    public boolean saveItem(String desc, String marca, String precio, String stock, Categoria categoria) {
        Item item = validateItem(desc, marca, precio, stock, categoria);
        if(item != null){
            lastItem = item;
            return conexion.saveItem(item);
        }
        else
            return false;

    }
    
    /**
     * actualiza los datos del item
     * @param desc
     * @param marca
     * @param precio
     * @param stock
     * @param cat
     * @param item
     * @return 
     */
    public boolean updateItem(String desc, String marca, String precio, String stock, Categoria cat, Item item) {
        Item tmpItem = validateItem(desc, marca, precio, stock, cat);
        if(tmpItem != null){
            item.setCategoria(tmpItem.getCategoria());
            item.setMarca(tmpItem.getMarca());
            item.setName(tmpItem.getName());
            item.setPrecio(tmpItem.getPrecio());
            item.setStock(tmpItem.getStock());
            boolean res = conexion.updateItem(item);
            return res;
        }
        else
            return false;
    }
    
    /**
     * Valida los datos del formulario de items
     *
     * @param desc nombre del item
     * @param marca marca del item
     * @param precio precio del item
     * @param stock cantidad de items en el stock
     * @param categoria categoria a la que pertenece
     * @return regresa el item
     */
    private Item validateItem(String desc, String marca, String precio, String stock, Categoria categoria){
        if(!desc.isEmpty()){
            Item item = new Item();
            item.setName(desc);
            if(!marca.isEmpty()){
                item.setMarca(marca);
                if(!precio.isEmpty()){
                    String tmp = Utileria.limpiaCadena(precio);
                    double price = Utileria.convertDouble(tmp);
                    item.setPrecio(price);
                    item.setCategoria(categoria);
                    int stk = Utileria.convierteEntero(stock);
                    item.setStock(stk);
                    return item;
                }    
                else{
                    JOptionPane.showMessageDialog(null, "No Introdujo un Pricio Valido", "Items", JOptionPane.ERROR_MESSAGE);   
                    return null;
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "La Marca no Puede Estar En Blanco", "Items", JOptionPane.ERROR_MESSAGE);   
                return null;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "La Describcion no Puede Estar En Blanco", "Items", JOptionPane.ERROR_MESSAGE);   
            return null;
        }
    }

    
    
}
