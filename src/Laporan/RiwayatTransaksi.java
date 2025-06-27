/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Laporan;

import Dashboard.Dashboard;
import Login.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class RiwayatTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form RiwayatTransaksi
     */
    public RiwayatTransaksi() {
        initComponents();
        setSize(1366, 768); // Atur ukuran manual
        setLocationRelativeTo(null); // Pusat layar
        setVisible(true); // Penting kalau di luar main()
        tampilkanDataTransaksi(); // tambahkan ini

    }
    
    private void rekapHarian() {
    try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT nama_barang, SUM(jumlah) AS total_terjual, SUM(total) AS total_pendapatan, metode_pembayaran "
                   + "FROM transaksi_penjualan "
                   + "WHERE DATE(tanggal_transaksi) = CURDATE() "
                   + "GROUP BY nama_barang, metode_pembayaran";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah Terjual");
        model.addColumn("Pendapatan");
        model.addColumn("Metode Pembayaran");

        int totalCash = 0;
        int totalQRIS = 0;

        while (rs.next()) {
            String nama = rs.getString("nama_barang");
            int jumlah = rs.getInt("total_terjual");
            int pendapatan = rs.getInt("total_pendapatan");
            String metode = rs.getString("metode_pembayaran");

            if ("Cash".equalsIgnoreCase(metode)) {
                totalCash += pendapatan;
            } else if ("QRIS".equalsIgnoreCase(metode)) {
                totalQRIS += pendapatan;
            }

            model.addRow(new Object[]{nama, jumlah, pendapatan, metode});
        }

        jTable2.setModel(model);

        JOptionPane.showMessageDialog(this,
            "Total Pendapatan Hari Ini:\n"
          + "Cash  : Rp. " + totalCash + "\n"
          + "QRIS  : Rp. " + totalQRIS,
          "Rekap Harian", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan rekap harian: " + e.getMessage());
    }
}


    private void rekapBulanan() {
    try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT nama_barang, SUM(jumlah) AS total_terjual, SUM(total) AS total_pendapatan, metode_pembayaran "
                   + "FROM transaksi_penjualan "
                   + "WHERE MONTH(tanggal_transaksi) = MONTH(CURDATE()) "
                   + "AND YEAR(tanggal_transaksi) = YEAR(CURDATE()) "
                   + "GROUP BY nama_barang, metode_pembayaran";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah Terjual");
        model.addColumn("Pendapatan");
        model.addColumn("Metode Pembayaran");

        int totalCash = 0;
        int totalQRIS = 0;

        while (rs.next()) {
            String nama = rs.getString("nama_barang");
            int jumlah = rs.getInt("total_terjual");
            int pendapatan = rs.getInt("total_pendapatan");
            String metode = rs.getString("metode_pembayaran");

            if ("Cash".equalsIgnoreCase(metode)) {
                totalCash += pendapatan;
            } else if ("QRIS".equalsIgnoreCase(metode)) {
                totalQRIS += pendapatan;
            }

            model.addRow(new Object[]{nama, jumlah, pendapatan, metode});
        }

        jTable2.setModel(model);

        JOptionPane.showMessageDialog(this,
            "Total Pendapatan Bulan Ini:\n"
          + "Cash  : Rp. " + totalCash + "\n"
          + "QRIS  : Rp. " + totalQRIS,
          "Rekap Bulanan", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan rekap bulanan: " + e.getMessage());
    }
}


private void rekapTahunan() {
    try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT nama_barang, SUM(jumlah) AS total_terjual, SUM(total) AS total_pendapatan, metode_pembayaran "
                   + "FROM transaksi_penjualan "
                   + "WHERE YEAR(tanggal_transaksi) = YEAR(CURDATE()) "
                   + "GROUP BY nama_barang, metode_pembayaran";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah Terjual");
        model.addColumn("Pendapatan");
        model.addColumn("Metode Pembayaran");

        int totalCash = 0;
        int totalQRIS = 0;

        while (rs.next()) {
            String nama = rs.getString("nama_barang");
            int jumlah = rs.getInt("total_terjual");
            int pendapatan = rs.getInt("total_pendapatan");
            String metode = rs.getString("metode_pembayaran");

            if ("Cash".equalsIgnoreCase(metode)) {
                totalCash += pendapatan;
            } else if ("QRIS".equalsIgnoreCase(metode)) {
                totalQRIS += pendapatan;
            }

            model.addRow(new Object[]{nama, jumlah, pendapatan, metode});
        }

        jTable2.setModel(model);

        JOptionPane.showMessageDialog(this,
            "Total Pendapatan Tahun Ini:\n"
          + "Cash  : Rp. " + totalCash + "\n"
          + "QRIS  : Rp. " + totalQRIS,
          "Rekap Tahunan", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan rekap tahunan: " + e.getMessage());
    }
}


    private void tampilkanDataTransaksi() {
    try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT * FROM transaksi_penjualan ORDER BY id_transaksi DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga Jual");
        model.addColumn("Jumlah");
        model.addColumn("Total");
        model.addColumn("tanggal_transaksi");
        model.addColumn("Metode Pembayaran");


        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_transaksi"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getDouble("harga_jual"),
                rs.getInt("jumlah"),
                rs.getDouble("total"),
                rs.getString("tanggal_transaksi"),
                rs.getString("metode_pembayaran")
            });
        }
        jTable2.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan data transaksi: " + e.getMessage());
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(200, 140, 919, 360);

        jButton1.setText("REKAP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(540, 510, 200, 35);

        jLabel1.setFont(new java.awt.Font("Constantia", 1, 48)); // NOI18N
        jLabel1.setText("RIWAYAT TRANSAKSI");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(410, 50, 503, 60);

        jButton2.setText("DASHBOARD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(930, 520, 190, 35);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harian ", "Bulanan", "Tahunan", " " }));
        jComboBox1.setSelectedIndex(-1);
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(200, 510, 320, 32);

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lenovo\\Downloads\\Desain tanpa judul.png")); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(-4, 0, 1340, 660);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
       tampilkanDataTransaksi(); // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String pilihan = jComboBox1.getSelectedItem() != null 
    ? jComboBox1.getSelectedItem().toString().trim() 
    : "";

switch (pilihan) {
    case "Harian":
        rekapHarian();
        break;
    case "Bulanan":
        rekapBulanan();
        break;
    case "Tahunan":
        rekapTahunan();
        break;
    default:
        JOptionPane.showMessageDialog(this, "Pilih jenis rekap terlebih dahulu!");
}

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Dashboard().setVisible(true);   
    dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(RiwayatTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RiwayatTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RiwayatTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RiwayatTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RiwayatTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
