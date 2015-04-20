/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Sale {
    
    private int saleId;
    private Date sale_date;
    private double subtotal;
    private double total;
    private double payment;
    private double change_payment;
    private ArrayList<SaleDetail> listDetails;
    
    public Sale(){
        saleId = 0;
        sale_date = new Date();
        subtotal = 0.0;
        total = 0.0;
        payment = 0.0;
        change_payment = 0.0;
        listDetails = new ArrayList<>();
    }

    public Sale(int saleId, Date sale_date, double subtotal, double total, double payment, double change_payment, ArrayList<SaleDetail> listDetails) {
        this.saleId = saleId;
        this.sale_date = sale_date;
        this.subtotal = subtotal;
        this.total = total;
        this.payment = payment;
        this.change_payment = change_payment;
        this.listDetails = listDetails;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Date getSale_date() {
        return sale_date;
    }

    public void setSale_date(Date sale_date) {
        this.sale_date = sale_date;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getChange_payment() {
        return change_payment;
    }

    public void setChange_payment(double change_payment) {
        this.change_payment = change_payment;
    }

    public ArrayList<SaleDetail> getListDetails() {
        return listDetails;
    }

    public void setListDetails(ArrayList<SaleDetail> listDetails) {
        this.listDetails = listDetails;
    }
    
    

}
