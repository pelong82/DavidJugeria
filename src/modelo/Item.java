/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Administrator
 */
public class Item {
    
    private int id;
    private int stock;
    private String name;
    private Categoria categoria;
    private String marca;
    private double precio;
    
    public Item(){
        id = 0;
        stock = 0;
        name = "";
        categoria = new Categoria();
        marca = "";
        precio = 0.0;
    }

    public Item(int id, int stock, String name, Categoria categoria, String marca, double precio) {
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
