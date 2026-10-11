import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Year;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    // ===== komponen (nama harus sama dengan "binding" di PeopleMenu.form) =====
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JComboBox<String> kategoriComboBox;
    private JComboBox<String> pangkatComboBox;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JTable peopleTable;
    private JScrollPane tableScrollPane;

    // ===== data =====
    private static final int USIA_MINIMAL = 18;
    private ArrayList<Person> listPerson;
    private int selectedIndex = -1; // -1 = tidak ada baris yang dipilih (mode tambah)

    public static void main(String[] args) {
        PeopleMenu menu = new PeopleMenu();
        menu.setSize(700, 540);
        menu.setLocationRelativeTo(null);
        menu.setContentPane(menu.mainPanel);
        menu.getContentPane().setBackground(java.awt.Color.WHITE);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // constructor
    public PeopleMenu() {
        setTitle("Data Anggota Polri");
        listPerson = new ArrayList<>();
        populateList();

        peopleTable.setModel(setTable());
        // scrollbar vertikal selalu tampil di area tabel
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        titleLabel.setFont(titleLabel.getFont().deriveFont(java.awt.Font.BOLD, 20f));

        // isi pilihan satuan (kategori)
        String[] satuan = {"-- Pilih Satuan --", "Bareskrim", "Korlantas", "Baintelkam",
                "Korsabhara", "Korbrimob", "Korpolairud", "Densus 88 AT", "Div Propam"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(satuan));

        // isi pilihan pangkat (22 pangkat Polri, dari terendah sampai tertinggi)
        String[] pangkat = {"-- Pilih Pangkat --",
                "Bharada", "Bharatu", "Bharaka", "Abripda", "Abriptu", "Abrip",
                "Bripda", "Briptu", "Brigpol", "Bripka", "Aipda", "Aiptu",
                "Ipda", "Iptu", "AKP", "Kompol", "AKBP", "Kombes Pol",
                "Brigjen Pol", "Irjen Pol", "Komjen Pol", "Jenderal Pol"};
        pangkatComboBox.setModel(new DefaultComboBoxModel<>(pangkat));

        deleteButton.setVisible(false);

        // tombol Add / Update
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // tombol Delete (konfirmasi ada di dalam deleteData)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // tombol Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // klik baris tabel -> isi form
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = peopleTable.getSelectedRow();
                if (selectedIndex < 0) return;

                Person p = listPerson.get(selectedIndex);
                idField.setText(p.getId());
                namaField.setText(p.getNama());
                tahunLahirField.setText(String.valueOf(p.getTahunLahir()));
                kategoriComboBox.setSelectedItem(p.getKategori());
                pangkatComboBox.setSelectedItem(p.getPangkat());

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    // membuat model tabel dari listPerson
    public final DefaultTableModel setTable() {
        String[] columns = {"No", "ID Anggota", "Nama", "Tahun Lahir", "Satuan", "Pangkat"};
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel hanya untuk dilihat
            }
        };

        for (int i = 0; i < listPerson.size(); i++) {
            Person p = listPerson.get(i);
            Object[] row = {i + 1, p.getId(), p.getNama(), p.getTahunLahir(),
                    p.getKategori(), p.getPangkat()};
            model.addRow(row);
        }
        return model;
    }

    // CREATE
    public void insertData() {
        Person p = readForm();
        if (p == null) return;

        for (Person existing : listPerson) {
            if (existing.getId().equalsIgnoreCase(p.getId())) {
                JOptionPane.showMessageDialog(null, "ID sudah dipakai!");
                return;
            }
        }

        listPerson.add(p);
        peopleTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    // UPDATE
    public void updateData() {
        Person p = readForm();
        if (p == null) return;

        // cek ID bentrok dengan baris lain
        for (int i = 0; i < listPerson.size(); i++) {
            if (i != selectedIndex && listPerson.get(i).getId().equalsIgnoreCase(p.getId())) {
                JOptionPane.showMessageDialog(null, "ID sudah dipakai!");
                return;
            }
        }

        listPerson.set(selectedIndex, p);
        peopleTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    // DELETE (dengan confirmation prompt)
    public void deleteData() {
        if (selectedIndex < 0) return;

        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah kamu yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (pilihan == JOptionPane.YES_OPTION) {
            listPerson.remove(selectedIndex);
            peopleTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
    }

    // reset form ke kondisi awal
    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        pangkatComboBox.setSelectedIndex(0);

        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
        peopleTable.clearSelection();
    }

    // baca + validasi input form, return null jika tidak valid
    private Person readForm() {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunStr = tahunLahirField.getText().trim();
        String kategori = (String) kategoriComboBox.getSelectedItem();
        String pangkat = (String) pangkatComboBox.getSelectedItem();

        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!");
            return null;
        }
        if (kategoriComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Pilih satuan terlebih dahulu!");
            return null;
        }
        if (pangkatComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Pilih pangkat terlebih dahulu!");
            return null;
        }

        int tahun;
        try {
            tahun = Integer.parseInt(tahunStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun lahir harus berupa angka!");
            return null;
        }

        // validasi logis: usia minimal 18 tahun
        int usia = Year.now().getValue() - tahun;
        if (usia < USIA_MINIMAL) {
            JOptionPane.showMessageDialog(null, "Anggota minimal berusia " + USIA_MINIMAL + " tahun!");
            return null;
        }

        return new Person(id, nama, tahun, kategori, pangkat);
    }

    // data awal (nama fiktif)
    private void populateList() {
        listPerson.add(new Person("AGT-001", "Budi Santoso", 1990, "Bareskrim", "Iptu"));
        listPerson.add(new Person("AGT-002", "Siti Rahma", 1995, "Korlantas", "Bripda"));
        listPerson.add(new Person("AGT-003", "Andi Wijaya", 1988, "Densus 88 AT", "Aiptu"));
        listPerson.add(new Person("AGT-004", "Dewi Lestari", 1992, "Div Propam", "AKP"));
        listPerson.add(new Person("AGT-005", "Rudi Hartono", 1980, "Korbrimob", "Kompol"));
    }
}
