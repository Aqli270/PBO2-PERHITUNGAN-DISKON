/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tugas.pkg3;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AplikasiDiskon extends JFrame {
    private JTextField txtHargaAsli, txtKodeKupon;
    private JLabel lblHasil, lblRiwayat;
    private JComboBox<Integer> cmbDiskon;
    private JSlider sliderDiskon;
    private ArrayList<String> riwayatDiskon;

    public AplikasiDiskon() {
        setTitle("Aplikasi Diskon");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtHargaAsli = new JTextField(10);
        txtKodeKupon = new JTextField(10);
        lblHasil = new JLabel("Harga Akhir: ");
        lblRiwayat = new JLabel("Riwayat Diskon: ");
        riwayatDiskon = new ArrayList<>();

        // JComboBox untuk persentase diskon
        Integer[] diskonOptions = {0, 5, 10, 15, 20, 25, 30};
        cmbDiskon = new JComboBox<>(diskonOptions);

        // JSlider untuk persentase diskon
        sliderDiskon = new JSlider(0, 30, 0);
        sliderDiskon.setMajorTickSpacing(5);
        sliderDiskon.setMinorTickSpacing(1);
        sliderDiskon.setPaintTicks(true);
        sliderDiskon.setPaintLabels(true);
        sliderDiskon.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cmbDiskon.setSelectedItem(sliderDiskon.getValue());
            }
        });

        JButton btnHitung = new JButton("Hitung");
        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungDiskon();
            }
        });

        // Menambahkan komponen ke GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Harga Asli: "), gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(txtHargaAsli, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Kode Kupon (opsional): "), gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(txtKodeKupon, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Pilih Diskon: "), gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(cmbDiskon, gbc);
        gbc.gridx = 2; gbc.gridy = 2; add(sliderDiskon, gbc);

        gbc.gridx = 1; gbc.gridy = 3; add(btnHitung, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3; add(lblHasil, gbc);
        gbc.gridy = 5; add(lblRiwayat, gbc);
    }

    private void hitungDiskon() {
        try {
            double hargaAsli = Double.parseDouble(txtHargaAsli.getText());
            int diskon = (Integer) cmbDiskon.getSelectedItem();
            double penghematan = hargaAsli * diskon / 100.0;
            double hargaAkhir = hargaAsli - penghematan;

            // Cek kode kupon (misal: KODE20 untuk tambahan diskon 20%)
            String kodeKupon = txtKodeKupon.getText().trim();
            if (kodeKupon.equalsIgnoreCase("KODE20")) {
                hargaAkhir -= hargaAkhir * 0.20; // Tambahan diskon 20%
                penghematan += hargaAkhir * 0.20; // Menghitung penghematan tambahan
            }

            // Menampilkan hasil
            lblHasil.setText(String.format("Harga Akhir: %.2f (Penghematan: %.2f)", hargaAkhir, penghematan));

            // Menyimpan riwayat diskon
            riwayatDiskon.add(String.format("Harga: %.2f, Diskon: %d%%, Harga Akhir: %.2f", hargaAsli, diskon, hargaAkhir));
            updateRiwayat();
        } catch (NumberFormatException ex) {
            lblHasil.setText("Masukkan harga yang valid!");
        }
    }

    private void updateRiwayat() {
        StringBuilder riwayatText = new StringBuilder("<html>Riwayat Diskon:<br/>");
        for (String entry : riwayatDiskon) {
            riwayatText.append(entry).append("<br/>");
        }
        riwayatText.append("</html>");
        lblRiwayat.setText(riwayatText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplikasiDiskon app = new AplikasiDiskon();
            app.setVisible(true);
        });
    }
}
