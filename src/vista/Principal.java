/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Utilities;
import logica.LogicaVenta;
import logica.Utileria;
import modelo.Categoria;
import modelo.Item;
import modelo.Sale;
import modelo.SaleDetail;

/**
 *
 * @author Administrator
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        logica = new LogicaVenta();
        btsCategorias = new ArrayList<>();
        btsItems = new ArrayList<>();
        tmpPriceItem = "";
        lastSalesID = 0;
        sales = new Sale();
        listDetail = new ArrayList<>();
        makeBtCategorias();
        cargarCategorias();
        cargarItems();
        iniciarTableItems();
        initTableSales();
        disableBtsActionsItems();
        disableBtSaveItem();
        initSales();
        initBtsItems();
    }
    
    /**
     * agraga un item a la table de items
     * @param item el articulo agregar en 
     * la tabla
     */
    private void addItemToTable(Item item){
        registersTebleItems[0] = ""+item.getId();
        registersTebleItems[1] = item.getName();
        String tmp = Utileria.formatMoneda(item.getPrecio());
        registersTebleItems[2] = tmp;
        modelItems.addRow(registersTebleItems);
    }
    
    private void addItemToSaleDetails(SaleDetail saleDetail){
        
        
    }
    
    /**
     * Agrega el item la venta para su
     * proceso
     */
    private void addItemToSale(){
        int size = listaCategorias.size();
        for(int i=0; i<size; i++){
            String categoria = listaCategorias.get(i).getNombre();
            for(JToggleButton bt : btsCategorias){
                if(bt.isSelected()){
                    String tmp = bt.getActionCommand();
                    if(categoria.equalsIgnoreCase(tmp)){
                        int sizeItems = btsItems.size();
                        for(int j=0; j<sizeItems; j++){
                            JToggleButton btItems = btsItems.get(j);
                            if(btItems.isSelected()){
                                 SaleDetail saleDetail = new SaleDetail();
                                 saleDetail.setItem(listaItems.get(j));
                                 
                            }   
                        }
                    }
                        
                }
            }
        }
    }
    
    private void cargarCategorias(){
        listaCategorias = logica.getCategorias();
    }
    
    private void cargarItems(){
        listaItems = logica.getItems();
    }
    
    /**
     * elimina Item
     */
    private void deleteProducto(){
        int r = jTableItems.getSelectedRow();
        if(r > -1){
            int id = listaItems.get(r).getId();
            boolean res = logica.deleteItem(id);
            if(res) {
                listaItems.remove(r);
                iniciarTableItems();
                limpiarDatosFormProducto();
                disableBtSaveItem();
                disableBtsActionsItems();
            }
        }
        else
            JOptionPane.showMessageDialog(null, "No Ha Seleccionado Un Item", "Items", JOptionPane.ERROR_MESSAGE);
        
    }
    
    /**
     * desahabilita los botoenes
     * delete y update del panel
     * de items
     */
    private void disableBtsActionsItems(){
        jButtonDeleteItem.setEnabled(false);
        jButtonEditItem.setEnabled(false);
    }
    
    /**
     * desahabilita el boton
     * save del panel item
     */
    private void disableBtSaveItem(){
        jButtonSaveItem.setEnabled(false);
    }
    
    /**
     * habilita los botoenes
     * delete y update del panel
     * de items
     */
    private void enableBtsActionsItems(){
        jButtonDeleteItem.setEnabled(true);
        jButtonEditItem.setEnabled(true);
    }
    
    /**
     * habilita el boton
     * save del panel item
     */
    private void enableBtSaveItem(){
        jButtonSaveItem.setEnabled(true);
    }
    
    /**
     * Formate el precio del item
     * @param price precio del item
     */
    private void formatPrice(String price){
        if(!price.isEmpty()){
            double priceLimpio = Utileria.convertDouble(price);
            String tmp = Utileria.formatMoneda(priceLimpio);
            jTextFieldPrecio.setText(tmp);
        }
        else
            jTextFieldPrecio.setText(tmpPriceItem);
        tmpPriceItem = "";
    }
    
    /**
     * formate la tabla de items
     */
    private void formatTableItems(){
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        //tamano del texto de las celdas y centrado
        jTableItems.getColumnModel().getColumn(1).setPreferredWidth(500);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTableItems.setDefaultRenderer(String.class, centerRenderer);
        int c = jTableItems.getColumnCount();
        for(int x=2;x<c;x++)
            jTableItems.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        Font fuente=new Font("Arial", Font.BOLD, 12);
        jTableItems.setFont(fuente);
        jTableItems.setRowHeight(16);
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
    }

    /**
     * formate la tabla de items
     */
    private void formatTableSales(){
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        //tamano del texto de las celdas y centrado
        jTableSale.getColumnModel().getColumn(0).setPreferredWidth(280);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTableSale.setDefaultRenderer(String.class, centerRenderer);
        int c = jTableSale.getColumnCount();
        for(int x=1;x<c;x++)
            jTableSale.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        Font fuente=new Font("Arial", Font.BOLD, 12);
        jTableSale.setFont(fuente);
        jTableSale.setRowHeight(16);
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
    }
    
    /**
     * inicia la tabla items
     */
    private void iniciarTableItems(){
        makeModelTableItems();
        jTableItems.setModel(modelItems);
        formatTableItems();
        loadDataTableItems();
    }
    
    /**
     * Habilita solo el boton del item
     * seleccionado los demas los de
     * selecciona
     * @param btItem boton seleccionado 
     */
    private void disableBtsItems(String btItem){
        for(JToggleButton bt : btsItems){
            String tmp = bt.getActionCommand();
            if(tmp.equalsIgnoreCase(btItem))
                bt.setSelected(true);
            else
                bt.setSelected(false);
        }
    }
    
    private void initBtsItems(){
        for(Item item : listaItems){
            JToggleButton bt = new JToggleButton(item.getName());
            bt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    disableBtsItems(e.getActionCommand());
                }
            });
            btsItems.add(bt);   
        }
    }
    
    private void initTableSales(){
        makeModelTableSales();
        jTableSale.setModel(modelSales);
        formatTableSales();
    }
    
    /**
     * inicia las ventas
     * fecha y numero de folio
     */
    private void initSales(){
        lastSalesID = logica.getLastIdSale();
        lastSalesID++;
        String fecha = Utileria.formateaFecha(new Date());
        jTextFieldFecha.setText(fecha);
        jTextFieldRecibo.setText(""+lastSalesID);
    }

    /**
     * Crea el Modelo de la 
     * tabla de items
     */
    private void makeModelTableItems(){
        DefaultTableModel mod;
        String[] columnas = {"ID","DESCRIPCION","PRECIO"};
        String[] filas = new String[3];
        mod = new DefaultTableModel(null,columnas){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelItems = mod;
        registersTebleItems = filas;
    }
    
    /**
     * Crea el Modelo de la 
     * tabla de sales
     */
    private void makeModelTableSales(){
        DefaultTableModel mod;
        String[] columnas = {"PRODUCTO","PRECIO","CANTIDAD","IMPORTE"};
        String[] filas = new String[4];
        mod = new DefaultTableModel(null,columnas){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelSales = mod;
        registersTableSales = filas;
    }
    
    /**
     * Crea los botones de las 
     * categorias de los items
     */
    private void makeBtCategorias(){
        int num = namesCategorias.length;
        for(int i=0;i<num;i++){
            JToggleButton jtlButton = new JToggleButton(namesCategorias[i]);
            jtlButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    selectCategoria(e.getActionCommand());
                }
            });
            jtlButton.setFont(new java.awt.Font("Times New Roman", 0, 16));;
            btsCategorias.add(jtlButton);
            jPanelCategorias.add(jtlButton);
        }
    }
    
    
    /**
     * Carga los items en la
     * tabla
     */
    private void loadDataTableItems(){
        for(Item item : listaItems){
            addItemToTable(item);
        }
    }
    
    /**
     * limpia los datos del formulario
     * de items
     */
    private void limpiarDatosFormProducto(){
        jTextFieldDescripcion.setText("");
        jTextFieldMarca.setText("");
        jTextFieldPrecio.setText("");
        jTextFieldProductoId.setText("");
        jTextFieldStock.setText("");
        jComboBoxCategorias.setSelectedIndex(0);
        jTableItems.clearSelection();
    }
    
    /**
     * llena los datos de los item en
     * el formulario
     * 
     */
    private void llenarDatosFormProducto(){
        int r = jTableItems.getSelectedRow();
        if(r > -1){
            enableBtsActionsItems();
            Item item = listaItems.get(r);
            jTextFieldDescripcion.setText(item.getName());
            jTextFieldMarca.setText(item.getMarca());
            String tmp = Utileria.formatMoneda(item.getPrecio());
            jTextFieldPrecio.setText(tmp);
            jTextFieldProductoId.setText(""+item.getId());
            jTextFieldStock.setText(""+item.getStock());
            jComboBoxCategorias.setSelectedItem(item.getCategoria().getNombre());
        }
    }
    
    /**
     * selecciona una categoria
     * @param categoria  categoria seleccionada
     */
    private void selectCategoria(String categoria){
        for(JToggleButton jtb : btsCategorias){
            String temp = jtb.getActionCommand();
            if(temp.equals(categoria)){
                jtb.setSelected(true);
                switch(temp){
                    case "JUGOS":;
                        showBtsJugos(1);
                        break;
                    case "ENSALADAS":
                        showBtsJugos(2);
                        break;
                    case "REFRESCOS":
                        showBtsJugos(3);
                        break;
                    case "OTROS":
                        showBtsJugos(4);
                        break;
                }
            }
            else
                jtb.setSelected(false);
        }
    }
    
    /**
     * guarda un item 
     */
    private void saveProducto(){
        String desc = jTextFieldDescripcion.getText();
        String marca = jTextFieldMarca.getText();
        String precio = jTextFieldPrecio.getText();
        String stock = jTextFieldStock.getText();
        String categoria = ""+jComboBoxCategorias.getSelectedItem();
        Categoria cat = new Categoria();
        for(Categoria c : listaCategorias){
            if(c.getNombre().equals(categoria))
                cat = c;
        }
        boolean res = logica.saveItem(desc, marca, precio, stock, cat);
        if(res){
            Item item = logica.getLastItem();
            item.setId(logica.getLastIdItem());
            addItemToTable(item);
            updateDataTableItems();
            listaItems.add(item);
            jTextFieldProductoId.setText(""+item.getId());
            disableBtSaveItem();
            enableBtsActionsItems();
            JOptionPane.showMessageDialog(null, "Producto Guardad", "Items", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            logica.setLastIdItem(0);
            logica.setLastItem(null);
        }
    }
    
    private void showBtsJugos(int categoria){
        jPanelItems.removeAll();
        int size = listaItems.size();
        for(int i=0; i<size; i++){
            Item item = listaItems.get(i);
            int idCatItem = item.getCategoria().getId();
            if(categoria == idCatItem){
                JToggleButton bt = btsItems.get(i);
                jPanelItems.add(bt);
                bt.setSelected(false);
            }
        }
        jPanelItems.validate();
        jPanelItems.repaint();
    }    
    
    /**
     * Actualiza la tabla de items
     */
    private void updateDataTableItems(){
        modelItems.fireTableDataChanged();
    }
    
    /**
     * actuliza los datos del item
     * 
     */
    private void updateItem(){
        int r = jTableItems.getSelectedRow();
        if(r > -1){
            Item item = listaItems.get(r);
            String desc = jTextFieldDescripcion.getText();
            String marca = jTextFieldMarca.getText();
            String precio = jTextFieldPrecio.getText();
            String stock = jTextFieldStock.getText();
            String categoria = ""+jComboBoxCategorias.getSelectedItem();
            Categoria cat = new Categoria();
            for(Categoria c : listaCategorias){
                if(c.getNombre().equals(categoria))
                    cat = c;
            }
            boolean res = logica.updateItem(desc, marca, precio, stock, cat,item);
            if(res){
                iniciarTableItems();
                JOptionPane.showMessageDialog(null, "Producto Actualizado", "Items", JOptionPane.INFORMATION_MESSAGE); 
            }   
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSale = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldRecibo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldFecha = new javax.swing.JTextField();
        jPanelCategorias = new javax.swing.JPanel();
        jPanelItems = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonNote = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanelPrductosTabla = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableItems = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldProcutoSearch = new javax.swing.JTextField();
        jButtonProductoSeach = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldProductoId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDescripcion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxCategorias = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldMarca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPrecio = new javax.swing.JTextField();
        jButtonNewItem = new javax.swing.JButton();
        jButtonDeleteItem = new javax.swing.JButton();
        jButtonEditItem = new javax.swing.JButton();
        jButtonSaveItem = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldStock = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("izq"));

        jTableSale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "PRODUCTO", "PRECIO", "CANTIDAD", "IMPORTE"
            }
        ));
        jScrollPane1.setViewportView(jTableSale);

        jLabel1.setText("Recibo No.");

        jTextFieldRecibo.setEditable(false);
        jTextFieldRecibo.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldRecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldRecibo.setText("0");

        jLabel2.setText("Fecha.");

        jTextFieldFecha.setEditable(false);
        jTextFieldFecha.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextFieldFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldFecha.setText("04/09/2014");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );

        jPanelCategorias.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIAS"));
        jPanelCategorias.setLayout(new java.awt.GridLayout(1, 0));

        jPanelItems.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTOS"));
        jPanelItems.setLayout(new java.awt.GridLayout(6, 3));

        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/math-add-icon48.png"))); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonNote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/notes-icon48.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                            .addComponent(jPanelItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jButtonAdd)
                        .addGap(37, 37, 37)
                        .addComponent(jButtonNote)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelItems, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonNote))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanel1);

        jPanelPrductosTabla.setBorder(javax.swing.BorderFactory.createTitledBorder("LISTA PRODUCTOS"));

        jTableItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "DESCRIPCION", "PRECIO"
            }
        ));
        jTableItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableItemsMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableItems);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Buscar Producto");

        jTextFieldProcutoSearch.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jButtonProductoSeach.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonProductoSeach.setText("Buscar");

        javax.swing.GroupLayout jPanelPrductosTablaLayout = new javax.swing.GroupLayout(jPanelPrductosTabla);
        jPanelPrductosTabla.setLayout(jPanelPrductosTablaLayout);
        jPanelPrductosTablaLayout.setHorizontalGroup(
            jPanelPrductosTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
            .addGroup(jPanelPrductosTablaLayout.createSequentialGroup()
                .addGroup(jPanelPrductosTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrductosTablaLayout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel3))
                    .addGroup(jPanelPrductosTablaLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jTextFieldProcutoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jButtonProductoSeach)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPrductosTablaLayout.setVerticalGroup(
            jPanelPrductosTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrductosTablaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(38, 38, 38)
                .addGroup(jPanelPrductosTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldProcutoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonProductoSeach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DEL PRODUCTO"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("ID:");

        jTextFieldProductoId.setEditable(false);
        jTextFieldProductoId.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldProductoId.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Descripcion:");

        jTextFieldDescripcion.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Categorias:");

        jComboBoxCategorias.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBoxCategorias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "JUGOS", "ENSALADAS", "REFRESCOS", "OTROS" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Marca:");

        jTextFieldMarca.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Precio:");

        jTextFieldPrecio.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPrecio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldPrecioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldPrecioFocusLost(evt);
            }
        });
        jTextFieldPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPrecioKeyTyped(evt);
            }
        });

        jButtonNewItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new48.png"))); // NOI18N
        jButtonNewItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNewItem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonNewItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewItemActionPerformed(evt);
            }
        });

        jButtonDeleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel2.png"))); // NOI18N
        jButtonDeleteItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeleteItem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonDeleteItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteItemActionPerformed(evt);
            }
        });

        jButtonEditItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit48.png"))); // NOI18N
        jButtonEditItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonEditItem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonEditItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditItemActionPerformed(evt);
            }
        });

        jButtonSaveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save48.png"))); // NOI18N
        jButtonSaveItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSaveItem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonSaveItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveItemActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("Stock:");

        jTextFieldStock.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldStockKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonNewItem)
                        .addGap(32, 32, 32)
                        .addComponent(jButtonDeleteItem)
                        .addGap(31, 31, 31)
                        .addComponent(jButtonEditItem)
                        .addGap(33, 33, 33)
                        .addComponent(jButtonSaveItem)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDescripcion)
                                    .addComponent(jTextFieldMarca, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldProductoId, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBoxCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldStock, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                        .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(254, 254, 254)))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldProductoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNewItem)
                    .addComponent(jButtonDeleteItem)
                    .addComponent(jButtonEditItem)
                    .addComponent(jButtonSaveItem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanelPrductosTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrductosTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Productos", jPanel2);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewItemActionPerformed
        // TODO add your handling code here:
        disableBtsActionsItems();
        enableBtSaveItem();
        limpiarDatosFormProducto();
    }//GEN-LAST:event_jButtonNewItemActionPerformed

    private void jTextFieldPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPrecioKeyTyped
        // TODO add your handling code here:
        int tecla = evt.getKeyChar();
        //permite solo enteros y un punto
        if(!Character.isDigit(tecla) && tecla != '.')
            evt.consume();
        String temp = jTextFieldPrecio.getText();
        if(tecla == '.' && temp.contains("."))
            evt.consume();
    }//GEN-LAST:event_jTextFieldPrecioKeyTyped

    private void jTextFieldStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldStockKeyTyped
        // TODO add your handling code here:
         int tecla = evt.getKeyChar();
        //permite solo enteros
        if(!Character.isDigit(tecla))
            evt.consume();
    }//GEN-LAST:event_jTextFieldStockKeyTyped

    private void jButtonSaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveItemActionPerformed
        // TODO add your handling code here:
        saveProducto();
    }//GEN-LAST:event_jButtonSaveItemActionPerformed

    private void jButtonDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteItemActionPerformed
        // TODO add your handling code here:
        deleteProducto();
    }//GEN-LAST:event_jButtonDeleteItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        logica.close();
    }//GEN-LAST:event_formWindowClosing

    private void jTableItemsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableItemsMouseReleased
        // TODO add your handling code here:
        disableBtSaveItem();
        llenarDatosFormProducto();
        
    }//GEN-LAST:event_jTableItemsMouseReleased

    private void jTextFieldPrecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPrecioFocusLost
        // TODO add your handling code here:
        formatPrice(jTextFieldPrecio.getText());
    }//GEN-LAST:event_jTextFieldPrecioFocusLost

    private void jTextFieldPrecioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPrecioFocusGained
        // TODO add your handling code here:
        tmpPriceItem = jTextFieldPrecio.getText();
        jTextFieldPrecio.setText("");
    }//GEN-LAST:event_jTextFieldPrecioFocusGained

    private void jButtonEditItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditItemActionPerformed
        // TODO add your handling code here:
        updateItem();
    }//GEN-LAST:event_jButtonEditItemActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
        addItemToSale();
    }//GEN-LAST:event_jButtonAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDeleteItem;
    private javax.swing.JButton jButtonEditItem;
    private javax.swing.JButton jButtonNewItem;
    private javax.swing.JButton jButtonNote;
    private javax.swing.JButton jButtonProductoSeach;
    private javax.swing.JButton jButtonSaveItem;
    private javax.swing.JComboBox jComboBoxCategorias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelCategorias;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JPanel jPanelPrductosTabla;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableItems;
    private javax.swing.JTable jTableSale;
    private javax.swing.JTextField jTextFieldDescripcion;
    private javax.swing.JTextField jTextFieldFecha;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldPrecio;
    private javax.swing.JTextField jTextFieldProcutoSearch;
    private javax.swing.JTextField jTextFieldProductoId;
    private javax.swing.JTextField jTextFieldRecibo;
    private javax.swing.JTextField jTextFieldStock;
    // End of variables declaration//GEN-END:variables

    private ArrayList<JToggleButton> btsCategorias;
    private ArrayList<JToggleButton> btsItems;
    private ArrayList<Item> listaItems;
    private String[] namesCategorias = {"JUGOS","ENSALADAS","REFRESCOS","OTROS"};
    private ArrayList<Categoria> listaCategorias;
    private LogicaVenta logica;
    private DefaultTableModel modelItems;
    private DefaultTableModel modelSales;
    private String[] registersTebleItems;
    private String[] registersTableSales;
    private String tmpPriceItem;
    private Sale sales;
    private ArrayList<SaleDetail> listDetail;
    private int lastSalesID;
}
