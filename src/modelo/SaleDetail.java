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
public class SaleDetail {
    
    private int saleDetailId;
    private int saleId;
    private Item item;
    private int quantity;
    private double amount;
    
    public SaleDetail(){
        saleDetailId = 0;
        saleId = 0;
        item = new Item();
        quantity = 0;
        amount = 0.0;
    }

    public SaleDetail(int saleDetailId, int saleId, Item item, int quantity, double amount) {
        this.saleDetailId = saleDetailId;
        this.saleId = saleId;
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
    }

    public int getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(int saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    
}
