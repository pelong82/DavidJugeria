/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Administrator
 */
public class Utileria {
    
    
    public static double convertDouble(String cadena){
        try{
            double num = Double.parseDouble(cadena);
            return num;
        }
        catch(NumberFormatException | NullPointerException e){
            return -1;
        }
    }
    
    public static int convierteEntero(String cadena){
        try{
            int num = Integer.parseInt(cadena);
            return num;
        }
        catch(NumberFormatException | NullPointerException e){
            return -1;
        }
    }
    
    public static String formateaFecha(Date d){
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String f = ""+fecha.format(d);
        return f;
    }
    
    public static String formatMoneda(double aValor){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(aValor);
    }
    
    public static String limpiaCadena(String cdn){
        StringTokenizer token = new StringTokenizer(cdn,"$,");
        int numIngre = token.countTokens();
        String tmp = "";
        while(token.hasMoreTokens())
            tmp += token.nextToken();
        return tmp;
    }
    
}
