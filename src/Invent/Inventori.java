package Invent;

/**
 *
 * @author Bubui
 */
import Database.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Inventori extends javax.swing.JFrame {

    /**
     * Creates new form Inventori
     */
    public Inventori() {
        initComponents();
        panel_Off();
        load_DataTabel();
    }
    
        /*Koneksi Database*/
        Koneksi db_connect = new Koneksi();
        Connection connect = db_connect.getKoneksi();
        /*================*/
    
    public void panel_Off(){
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        
    }
    
    public void load_DataTabel(){
        int no = 1;
        
        /*Membuat Model Tabel*/
        DefaultTableModel model = new DefaultTableModel();
        /*Membuat Kolom Tabel*/
        model.addColumn("No");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jenis");
        model.addColumn("Jumlah");
        model.addColumn("Supplier");
        model.setRowCount(0);
        data_Barang.setModel(model);
        data_Barang2.setModel(model);
        data_Barang3.setModel(model);
        
        /*=================*/
        /*Perintah Query Select*/
        try{
        
        String query = "SELECT barang.kode_brg, barang.nama_brg, barang.jenis, barang.stok, "
                + "supplier.nama_sup FROM barang INNER JOIN supplier ON barang.id_sup "
                + "= supplier.id_sup;";
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery(query);
        
        while(result.next()){
        
            Object[] obj_row = new Object[6];
                     obj_row[0] = no;
                     obj_row[1] = result.getString(1);
                     obj_row[2] = result.getString(2);
                     obj_row[3] = result.getString(3);
                     obj_row[4] = result.getString(4);
                     obj_row[5] = result.getString(5);
                model.addRow(obj_row);
            no++;
          }
            result.close();
            statement.close();
            data_Barang.setModel(model);
            data_Barang2.setModel(model);
            data_Barang3.setModel(model);
        } catch(SQLException e){
            System.out.println("Database Error!");
        }
        
    }
    
    public void load_BarangMasuk(){
        String kode_brg = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 1).toString();
        String nama_brg = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 2).toString();
        String jumlahan = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 4).toString();
        String sup = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 5).toString();
        
        BarangMasuk output_brg = new BarangMasuk();
        output_brg.kode_brg = kode_brg;
        output_brg.jumlah = jumlahan;
        output_brg.label_Barang.setText(nama_brg);
        output_brg.label_Sup.setText(sup);
        output_brg.setVisible(true);
        
    }
    
    public void load_BarangKeluar(){
        
        
        String kode_brg = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 1).toString();
        String nama_brg = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 2).toString();
        String jumlahan = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 4).toString();
        String sup = data_Barang2.getValueAt(data_Barang2.getSelectedRow(), 5).toString();
        
        BarangKeluar input_brg = new BarangKeluar();
        input_brg.kode_brg = kode_brg;
        input_brg.jumlah = jumlahan;
        input_brg.label_Barang.setText(nama_brg);
        input_brg.label_Sup.setText(sup);
        input_brg.setVisible(true);
    
    }
    
    public void load_HapusBarang(){
        String kode_brg = data_Barang3.getValueAt(data_Barang3.getSelectedRow(), 1).toString();
        
        try{
        String DelSQL = "CALL hapusbarang('"+kode_brg+"')";
        Statement statement = connect.createStatement();
        statement.executeUpdate(DelSQL);
        
        } catch(SQLException e){
            System.out.println("Terjadi Error");
        }
    }
    
    public void load_Data_Tmbh(){
      /*Membuat Model Tabel*/
        DefaultTableModel model = new DefaultTableModel();
        /*Membuat Kolom Tabel*/
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Supplier");
        model.setRowCount(0);
        data_Barang4.setModel(model);
      
        
        /*=================*/
        /*Perintah Query Select*/
        try{
        
        String query = "SELECT barang.kode_brg, barang.nama_brg, supplier.nama_sup FROM barang INNER JOIN supplier ON barang.id_sup = supplier.id_sup;";
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery(query);
        
        while(result.next()){
        
            Object[] obj_row = new Object[3];
                     obj_row[0] = result.getString(1);
                     obj_row[1] = result.getString(2);
                     obj_row[2] = result.getString(3);
                model.addRow(obj_row);
          }
            result.close();
            statement.close();
            data_Barang4.setModel(model);
        } catch(SQLException e){
            System.out.println("Database Error!");
        }
    }
    
    public void tmbh_Barang(){
        
        try{
            String sql_select = "SELECT id_sup FROM supplier";
            Statement statement = connect.createStatement();
            
            ResultSet rs = statement.executeQuery(sql_select);
                while(rs.next()){
                    box_Sup.addItem(rs.getString(1));
                }

        }catch(SQLException e){
            System.out.println("Terjadi Error!");
        }
  
    }
    
    public void input_Barang(){
    String nama_brg = txt_NamaBrg.getText();
        String kode_brg = txt_KodeBrg.getText();
        String jns_brg = txt_JnsBrg.getText();
        String id_sup = box_Sup.getSelectedItem().toString();
        
        try{
            String sql_insert = "CALL insertbarang(?,?,?,?,"+0+")";
            PreparedStatement statement = connect.prepareStatement(sql_insert);
            statement.setString(1, kode_brg);
            statement.setString(2, nama_brg);
            statement.setString(3, id_sup);
            statement.setString(4, jns_brg);
            statement.executeUpdate();
            
        } catch(SQLException e){ 
            JOptionPane.showMessageDialog(rootPane,"Data barang Sudah Ada");
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

        ThePanel = new javax.swing.JPanel();
        MainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        data_Barang2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btn_Masuk = new javax.swing.JButton();
        btn_Keluar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        data_Barang3 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btn_HapusBrg = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        data_Barang4 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btn_TambahBrg = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_KodeBrg = new javax.swing.JTextField();
        txt_NamaBrg = new javax.swing.JTextField();
        box_Sup = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_JnsBrg = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        data_Barang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        File = new javax.swing.JMenu();
        Home = new javax.swing.JMenuItem();
        Barang = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenuItem();
        menu_TambahBrg = new javax.swing.JMenu();
        menu_TbhBrg = new javax.swing.JMenuItem();
        menu_HapusBrg = new javax.swing.JMenuItem();
        Supplier = new javax.swing.JMenu();
        menu_TmbhSup = new javax.swing.JMenuItem();
        menu_EditSup = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainPanel.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        data_Barang2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Barang", "Nama Barang", "Jenis", "Jumlah", "Supplier"
            }
        ));
        jScrollPane2.setViewportView(data_Barang2);

        jLabel2.setFont(new java.awt.Font("Oxygen", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("KELUAR & MASUK BARANG");

        btn_Masuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Button-Add-Small.png"))); // NOI18N
        btn_Masuk.setContentAreaFilled(false);
        btn_Masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MasukActionPerformed(evt);
            }
        });

        btn_Keluar.setBackground(new java.awt.Color(204, 255, 255));
        btn_Keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus-small.png"))); // NOI18N
        btn_Keluar.setContentAreaFilled(false);
        btn_Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel2)
                .addContainerGap(256, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btn_Masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(65, Short.MAX_VALUE)))
        );

        MainPanel.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        data_Barang3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Barang", "Nama Barang", "Jenis", "Jumlah", "Supplier"
            }
        ));
        jScrollPane3.setViewportView(data_Barang3);
        if (data_Barang3.getColumnModel().getColumnCount() > 0) {
            data_Barang3.getColumnModel().getColumn(0).setHeaderValue("No");
            data_Barang3.getColumnModel().getColumn(3).setHeaderValue("Jenis");
            data_Barang3.getColumnModel().getColumn(4).setHeaderValue("Jumlah");
        }

        jLabel3.setFont(new java.awt.Font("Oxygen", 1, 18)); // NOI18N
        jLabel3.setText("HAPUS BARANG");

        btn_HapusBrg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete (Custom).png"))); // NOI18N
        btn_HapusBrg.setContentAreaFilled(false);
        btn_HapusBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HapusBrgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(281, 281, 281)
                .addComponent(jLabel3)
                .addContainerGap(299, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_HapusBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                .addComponent(btn_HapusBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(71, Short.MAX_VALUE)))
        );

        MainPanel.add(jPanel3, "card4");

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        data_Barang4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Supplier"
            }
        ));
        jScrollPane4.setViewportView(data_Barang4);

        jLabel4.setFont(new java.awt.Font("Oxygen", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TAMBAH BARANG");

        btn_TambahBrg.setFont(new java.awt.Font("Oxygen", 1, 14)); // NOI18N
        btn_TambahBrg.setText("Tambah");
        btn_TambahBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TambahBrgActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Oxygen", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Supplier");

        jLabel6.setFont(new java.awt.Font("Oxygen", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nama Barang");

        jLabel7.setFont(new java.awt.Font("Oxygen", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kode Barang");

        txt_KodeBrg.setFont(new java.awt.Font("Oxygen", 0, 16)); // NOI18N

        txt_NamaBrg.setFont(new java.awt.Font("Oxygen", 0, 16)); // NOI18N

        box_Sup.setFont(new java.awt.Font("Oxygen", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Oxygen", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jenis Barang");

        txt_JnsBrg.setFont(new java.awt.Font("Oxygen", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(281, 281, 281)
                .addComponent(jLabel4)
                .addContainerGap(310, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(70, 70, 70)
                                .addComponent(box_Sup, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_JnsBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_NamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_KodeBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_TambahBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(344, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(box_Sup, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_KodeBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_NamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_JnsBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(btn_TambahBrg)
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(41, Short.MAX_VALUE)))
        );

        MainPanel.add(jPanel4, "card4");

        jPanel1.setBackground(new java.awt.Color(0, 0, 153));

        data_Barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Barang", "Nama Barang", "Jenis", "Jumlah", "Supplier"
            }
        ));
        data_Barang.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        data_Barang.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(data_Barang);

        jLabel1.setFont(new java.awt.Font("Oxygen", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DAFTAR INVENTORI BARANG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(238, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(233, 233, 233))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        MainPanel.add(jPanel1, "card2");

        javax.swing.GroupLayout ThePanelLayout = new javax.swing.GroupLayout(ThePanel);
        ThePanel.setLayout(ThePanelLayout);
        ThePanelLayout.setHorizontalGroup(
            ThePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ThePanelLayout.setVerticalGroup(
            ThePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(0, 153, 153));

        File.setText("File");

        Home.setText("Home");
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });
        File.add(Home);

        Barang.setText("Barang");
        Barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarangActionPerformed(evt);
            }
        });
        File.add(Barang);

        Exit.setText("Keluar");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        File.add(Exit);

        jMenuBar1.add(File);

        menu_TambahBrg.setText("Data");
        menu_TambahBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_TambahBrgActionPerformed(evt);
            }
        });

        menu_TbhBrg.setText("Tambah Barang");
        menu_TbhBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_TbhBrgActionPerformed(evt);
            }
        });
        menu_TambahBrg.add(menu_TbhBrg);

        menu_HapusBrg.setText("Hapus Barang");
        menu_HapusBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_HapusBrgActionPerformed(evt);
            }
        });
        menu_TambahBrg.add(menu_HapusBrg);

        jMenuBar1.add(menu_TambahBrg);

        Supplier.setText("Supplier");

        menu_TmbhSup.setText("Tambah Supplier");
        menu_TmbhSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_TmbhSupActionPerformed(evt);
            }
        });
        Supplier.add(menu_TmbhSup);

        menu_EditSup.setText("Edit Supplier");
        menu_EditSup.setEnabled(false);
        Supplier.add(menu_EditSup);

        jMenuBar1.add(Supplier);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ThePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ThePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        panel_Off();
        jPanel1.setVisible(true);
    }//GEN-LAST:event_HomeActionPerformed

    private void BarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarangActionPerformed
        panel_Off();
        jPanel2.setVisible(true);
    }//GEN-LAST:event_BarangActionPerformed

    private void btn_MasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MasukActionPerformed
        load_BarangMasuk();
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btn_MasukActionPerformed

    private void menu_TambahBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_TambahBrgActionPerformed
        
    }//GEN-LAST:event_menu_TambahBrgActionPerformed

    private void menu_TbhBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_TbhBrgActionPerformed
        panel_Off();
        jPanel4.setVisible(true);
        load_Data_Tmbh();
        tmbh_Barang();
    }//GEN-LAST:event_menu_TbhBrgActionPerformed

    private void menu_HapusBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_HapusBrgActionPerformed
        panel_Off();
        jPanel3.setVisible(true);
    }//GEN-LAST:event_menu_HapusBrgActionPerformed

    private void btn_KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KeluarActionPerformed
        load_BarangKeluar();
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btn_KeluarActionPerformed

    private void btn_HapusBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HapusBrgActionPerformed
        load_HapusBarang();
        load_DataTabel();
    }//GEN-LAST:event_btn_HapusBrgActionPerformed

    private void btn_TambahBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TambahBrgActionPerformed
        input_Barang();
        this.load_Data_Tmbh();
        this.load_DataTabel();
    }//GEN-LAST:event_btn_TambahBrgActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
       System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed

    private void menu_TmbhSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_TmbhSupActionPerformed
       TambahSupplier sup = new TambahSupplier();
       sup.setVisible(true);
       this.setVisible(false);
       this.dispose();
    }//GEN-LAST:event_menu_TmbhSupActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inventori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Barang;
    private javax.swing.JMenuItem Exit;
    private javax.swing.JMenu File;
    private javax.swing.JMenuItem Home;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JMenu Supplier;
    private javax.swing.JPanel ThePanel;
    private javax.swing.JComboBox<String> box_Sup;
    private javax.swing.JButton btn_HapusBrg;
    private javax.swing.JButton btn_Keluar;
    private javax.swing.JButton btn_Masuk;
    private javax.swing.JButton btn_TambahBrg;
    private javax.swing.JTable data_Barang;
    private javax.swing.JTable data_Barang2;
    private javax.swing.JTable data_Barang3;
    private javax.swing.JTable data_Barang4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem menu_EditSup;
    private javax.swing.JMenuItem menu_HapusBrg;
    private javax.swing.JMenu menu_TambahBrg;
    private javax.swing.JMenuItem menu_TbhBrg;
    private javax.swing.JMenuItem menu_TmbhSup;
    private javax.swing.JTextField txt_JnsBrg;
    private javax.swing.JTextField txt_KodeBrg;
    private javax.swing.JTextField txt_NamaBrg;
    // End of variables declaration//GEN-END:variables
}
