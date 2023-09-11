/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Paneles_rotativos;
import Clases.Main;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Clases.Peticiones;
import static Paneles_rotativos.Venta.SumaColumnaTabla.sumarColumna4;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author User_Default_0
 */
public class Venta extends javax.swing.JPanel {

    public Peticiones peticiones = new Peticiones();
    TableRowSorter trs;
    public Venta()throws SQLException{
        initComponents();
        initbolsa();
        jframe_fact.setVisible(false);
        jframe_fact.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void initbolsa () {
        try {
            
            tablaventa("");
            //leetable_bolsa();
            leebolsa();
            tf_total.setEditable(false);
            jLayeredPane3.add(jPanel1,JLayeredPane.POPUP_LAYER);
            
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public void cargatablabolsa(String tab[]) throws SQLException {
    DefaultTableModel modeloBolsa = (DefaultTableModel) table_bolsa.getModel();
    modeloBolsa.addRow(tab); // Agregar la nueva fila al final
}


    public void recorreryeliminar() throws SQLException{
        
        int rowCount = table_bolsa.getRowCount();
        DefaultTableModel model=(DefaultTableModel) table_bolsa.getModel();
        for (int fila = 0; fila < rowCount; fila++) {
            String cantidad = table_bolsa.getValueAt(fila, 2).toString();
            String descripcion = table_bolsa.getValueAt(fila, 1).toString();
            
            Main.conexion.EjecutarOperacionSQL("UPDATE productos SET cantidad = cantidad - " +cantidad +" WHERE descripcion = '"+ descripcion +"'");
            
        }
        model.setRowCount(0);
    }
    
    
   
    /*PUDE HACERLA COMO QUIERO
    public void leetable_bolsa() {
    table_bolsa.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int selectedRow = table_bolsa.getSelectedRow();
            int selectedColumn = table_bolsa.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn == 2 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    String cantidadStr = table_bolsa.getValueAt(selectedRow, 2).toString();
                    String preciostr = table_bolsa.getValueAt(selectedRow, 4).toString();
                    String stockstr = table_bolsa.getValueAt(selectedRow, 3).toString();
                    String descripcion = table_bolsa.getValueAt(selectedRow,1).toString();
                    int cant = Integer.parseInt(cantidadStr);
                    float precio = Float.parseFloat(preciostr);
                    int stock = Integer.parseInt(stockstr);
                    if(cant > stock){
                        JOptionPane.showMessageDialog(null, "No tiene esa cantidad de " + descripcion + " revisar stock!.");
                        table_bolsa.setValueAt("1",selectedRow,2);
                    }else{
                        table_bolsa.setValueAt(cant*precio, selectedRow, 5);
                        tf_total.setText(String.valueOf(sumarColumna4(table_bolsa)));
                    }
                    
                    
                    
                } catch (NumberFormatException ex) {
                    // Maneja la excepción si la cadena no es un número válido
                    System.err.println("Error: conflicto de tipos.");
                }
            }
        }
    });
}*/
    
    public void leebolsa(){
        DefaultTableModel model = (DefaultTableModel) table_bolsa.getModel();
        model.addTableModelListener(new TableModelListener() {
        @Override
        
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 2) { // Columna "cantidad"
                try {
                String cantidadStr = model.getValueAt(row, col).toString();
                String preciostr = model.getValueAt(row, 4).toString();
                String stockstr = model.getValueAt(row, 3).toString();
                String descripcion = model.getValueAt(row, 1).toString();
                int cant = Integer.parseInt(cantidadStr);
                float precio = Float.parseFloat(preciostr);
                int stock = Integer.parseInt(stockstr);

                if (cant > stock) {
                    JOptionPane.showMessageDialog(null, "No tiene esa cantidad de " + descripcion + " revisar stock!.");
                    model.setValueAt("1", row, 2);
                } else {
                    model.setValueAt(cant * precio, row, 5);
                    tf_total.setText(String.valueOf(sumarColumna4(table_bolsa)));
                }
            } catch (NumberFormatException ex) {
                // Maneja la excepción si la cadena no es un número válido
                System.err.println("Error: conflicto de tipos.");
            }
        }
    }
});
    }
    
    public class SumaColumnaTabla {
    public static double sumarColumna4(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int rowCount = modelo.getRowCount();
        double suma = 0.0;

        for (int fila = 0; fila < rowCount; fila++) {
            // Obtener el valor de la columna 4 (índice 3) en la fila actual
            Object valor = tabla.getValueAt(fila, 5);

            // Convertir el valor a un double y sumarlo a la suma total
            try {
                double valorDouble = Double.parseDouble(valor.toString());
                suma += valorDouble;
            } catch (NumberFormatException e) {
                // Manejar el caso en el que el valor no sea un número válido
                // Puedes mostrar un mensaje de error o realizar otra acción apropiada aquí
            }
        }

        return suma;
    }
}


            
    public void tablaventa (String str) throws SQLException{
        
        ResultSet res;
        
        res = peticiones.buscainventario(str);
        
        
        DefaultTableModel modelo = new DefaultTableModel();

        ArrayList<Object> nombrecolumna = new ArrayList<Object>();
        nombrecolumna.add("Codigo");
        nombrecolumna.add("Descripcion");
        nombrecolumna.add("Cantidad");
        nombrecolumna.add("Precio");

        for (Object columna : nombrecolumna) {
            modelo.addColumn(columna);
        }

        this.tabla_venta.setModel(modelo);
        
        while (res.next()) {

            String Codigo = res.getString("codigo");
            String Descripcion = res.getString("descripcion");
            String Cantidad = String.valueOf(res.getInt("cantidad"));
            String Precio = String.valueOf(res.getFloat("precio"));
           

            String tab[] = {Codigo, Descripcion, Cantidad, Precio};

            modelo.addRow(tab);
        }

        tabla_venta.setModel(modelo);
        
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane3 = new javax.swing.JLayeredPane();
        jPanel2 = new javax.swing.JPanel();
        jframe_fact = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        descipcion_p = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_venta = new javax.swing.JTable();
        j_buscar = new javax.swing.JLabel();
        cerrar = new javax.swing.JLabel();
        j_desc = new javax.swing.JLabel();
        j_select = new javax.swing.JLabel();
        j_close = new javax.swing.JLabel();
        panel_productos = new javax.swing.JPanel();
        buscar_p = new javax.swing.JLabel();
        J_CUIT = new javax.swing.JLabel();
        TF_cuit = new javax.swing.JTextField();
        J_CUIT1 = new javax.swing.JLabel();
        TF_cuit1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_bolsa = new javax.swing.JTable();
        j_t = new javax.swing.JLabel();
        tf_total = new javax.swing.JTextField();
        j_vender = new javax.swing.JLabel();

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jframe_fact.setVisible(true);

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });

        descipcion_p.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                descipcion_pMousePressed(evt);
            }
        });
        descipcion_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descipcion_pActionPerformed(evt);
            }
        });
        descipcion_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                descipcion_pKeyTyped(evt);
            }
        });

        tabla_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla_venta);

        j_buscar.setText("Buscar");
        j_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                j_buscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                j_buscarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                j_buscarMousePressed(evt);
            }
        });

        cerrar.setText("X");
        cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cerrarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cerrarMousePressed(evt);
            }
        });

        j_desc.setText("Descripcion:");

        j_select.setText("Seleccionar");
        j_select.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                j_selectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                j_selectMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                j_selectMousePressed(evt);
            }
        });

        j_close.setText("Cerrar");
        j_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                j_closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                j_closeMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                j_closeMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j_select)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(j_close)
                        .addGap(17, 17, 17)))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(j_desc)
                        .addGap(43, 43, 43)
                        .addComponent(descipcion_p, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(j_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(j_select)
                .addGap(34, 34, 34)
                .addComponent(j_close)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(descipcion_p, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j_desc)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cerrar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout jframe_factLayout = new javax.swing.GroupLayout(jframe_fact.getContentPane());
        jframe_fact.getContentPane().setLayout(jframe_factLayout);
        jframe_factLayout.setHorizontalGroup(
            jframe_factLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jframe_factLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jframe_factLayout.setVerticalGroup(
            jframe_factLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jframe_factLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 175, Short.MAX_VALUE))
        );

        jPanel2.add(jframe_fact, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        buscar_p.setText("Buscar Producto");
        buscar_p.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buscar_pMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buscar_pMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buscar_pMousePressed(evt);
            }
        });

        J_CUIT.setText("CUIT/CUIL/DNI:");

        TF_cuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_cuitActionPerformed(evt);
            }
        });

        J_CUIT1.setText("Razon Social:");

        TF_cuit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_cuit1ActionPerformed(evt);
            }
        });

        table_bolsa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Stock", "Precio", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table_bolsa);

        j_t.setText("Total:");

        tf_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tf_totalMousePressed(evt);
            }
        });
        tf_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_totalActionPerformed(evt);
            }
        });

        j_vender.setText("Vender");
        j_vender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                j_venderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                j_venderMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                j_venderMousePressed(evt);
            }
        });

        javax.swing.GroupLayout panel_productosLayout = new javax.swing.GroupLayout(panel_productos);
        panel_productos.setLayout(panel_productosLayout);
        panel_productosLayout.setHorizontalGroup(
            panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_productosLayout.createSequentialGroup()
                        .addComponent(J_CUIT, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(TF_cuit, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(J_CUIT1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(TF_cuit1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_productosLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(buscar_p, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_productosLayout.createSequentialGroup()
                    .addGap(297, 311, Short.MAX_VALUE)
                    .addComponent(j_t, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)
                    .addComponent(j_vender, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 127, Short.MAX_VALUE)))
        );
        panel_productosLayout.setVerticalGroup(
            panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_productosLayout.createSequentialGroup()
                        .addComponent(TF_cuit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(buscar_p, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_productosLayout.createSequentialGroup()
                        .addGroup(panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(J_CUIT, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TF_cuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(J_CUIT1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
            .addGroup(panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_productosLayout.createSequentialGroup()
                    .addContainerGap(306, Short.MAX_VALUE)
                    .addGroup(panel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(j_t, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_productosLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(j_vender, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel2.add(panel_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLayeredPane3.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(750, 750, 750))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    
    
    private void descipcion_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descipcion_pActionPerformed
   
    }//GEN-LAST:event_descipcion_pActionPerformed

    private void descipcion_pMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_descipcion_pMousePressed
              
    }//GEN-LAST:event_descipcion_pMousePressed

    private void j_buscarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_buscarMousePressed
        if (descipcion_p.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe escribir alguna parte de la descripcion que desea buscar");
      }else {
            String descripcion = descipcion_p.getText();
        
        try {    
            tablaventa(descripcion);
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_j_buscarMousePressed

    private void j_buscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_buscarMouseEntered
        j_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_j_buscarMouseEntered

    private void j_buscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_buscarMouseExited
        j_buscar.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_j_buscarMouseExited

    private void j_venderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_venderMousePressed
        if(tf_total.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar al menos un producto que desea vender");
        }else{
            try {
                recorreryeliminar();
                tf_total.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
                   
            
        
        
            
        
        
        
        
    }//GEN-LAST:event_j_venderMousePressed

    private void descipcion_pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descipcion_pKeyTyped
        
        descipcion_p.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                
                
                trs.setRowFilter(RowFilter.regexFilter("(?i)" + descipcion_p.getText(), 1));
                
            }
            
            
        });
        TableModel dtm = null;
                
            trs = new TableRowSorter(tabla_venta.getModel());
            tabla_venta.setRowSorter(trs);
    }//GEN-LAST:event_descipcion_pKeyTyped

    private void j_venderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_venderMouseEntered
        j_vender.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_j_venderMouseEntered

    private void j_venderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_venderMouseExited
        j_vender.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_j_venderMouseExited

    private void buscar_pMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscar_pMousePressed
        try {
            tablaventa("");
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
        jframe_fact.setVisible(true);
        panel_productos.setVisible(false);
        
    }//GEN-LAST:event_buscar_pMousePressed

    private void cerrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMousePressed
        jframe_fact.setVisible(false);
        panel_productos.setVisible(true);
        
    }//GEN-LAST:event_cerrarMousePressed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
    }//GEN-LAST:event_jPanel1MouseEntered

    private void cerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseExited
        cerrar.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_cerrarMouseExited

    private void cerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseEntered
        cerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_cerrarMouseEntered

    private void j_selectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_selectMouseEntered
        j_select.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_j_selectMouseEntered

    private void j_selectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_selectMouseExited
        j_select.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_j_selectMouseExited

    private void j_closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_closeMouseEntered
        j_close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_j_closeMouseEntered

    private void j_closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_closeMouseExited
        j_close.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_j_closeMouseExited

    private void TF_cuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_cuitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_cuitActionPerformed

    private void TF_cuit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_cuit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_cuit1ActionPerformed

    private void j_selectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_selectMousePressed
        
        int filaSeleccionada = tabla_venta.getSelectedRow();
            
        if (filaSeleccionada != -1) { try {
            // Verifica si se ha seleccionado una fila
            DefaultTableModel modelo = (DefaultTableModel) tabla_venta.getModel();
    
            // Obtén los valores de la fila seleccionada
            String descripcion = modelo.getValueAt(filaSeleccionada, 1).toString(); // Columna "Descripción"
            String codigo = modelo.getValueAt(filaSeleccionada, 0).toString(); // Columna "Descripción"
            String stock = modelo.getValueAt(filaSeleccionada, 2).toString(); // Columna "Descripción"
            String precio = modelo.getValueAt(filaSeleccionada, 3).toString(); // Columna "Descripción"
            
            String tab[] = {codigo, descripcion, "1",stock, precio, precio};
            
            cargatablabolsa(tab);
            jframe_fact.setVisible(false);
            panel_productos.setVisible(true);
            
            tf_total.setText(String.valueOf(sumarColumna4(table_bolsa)));
            
            } catch (SQLException ex) {
                Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");

        }
    }//GEN-LAST:event_j_selectMousePressed

    private void buscar_pMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscar_pMouseEntered
        buscar_p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_buscar_pMouseEntered

    private void buscar_pMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscar_pMouseExited
        buscar_p.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_buscar_pMouseExited

    private void tf_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_totalActionPerformed

    private void tf_totalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_totalMousePressed
        
    }//GEN-LAST:event_tf_totalMousePressed

    private void j_closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_closeMousePressed
       jframe_fact.setVisible(false);
       panel_productos.setVisible(true);
    }//GEN-LAST:event_j_closeMousePressed

        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel J_CUIT;
    private javax.swing.JLabel J_CUIT1;
    private javax.swing.JTextField TF_cuit;
    private javax.swing.JTextField TF_cuit1;
    private javax.swing.JLabel buscar_p;
    private javax.swing.JLabel cerrar;
    private javax.swing.JTextField descipcion_p;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel j_buscar;
    private javax.swing.JLabel j_close;
    private javax.swing.JLabel j_desc;
    private javax.swing.JLabel j_select;
    private javax.swing.JLabel j_t;
    private javax.swing.JLabel j_vender;
    private javax.swing.JInternalFrame jframe_fact;
    private javax.swing.JPanel panel_productos;
    private javax.swing.JTable tabla_venta;
    private javax.swing.JTable table_bolsa;
    private javax.swing.JTextField tf_total;
    // End of variables declaration//GEN-END:variables
}
