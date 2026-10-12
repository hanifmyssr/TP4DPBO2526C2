# TP4 DPBO - PeopleData (Data Anggota Polri)

**Nama:** Muhammad Hanif Muyassar
**NIM:** (isi NIM)
**Kelas:** (isi kelas)

## Janji
Saya mengerjakan Tugas Praktikum 4 DPBO dengan sebaik-baiknya dan tanpa melakukan kecurangan. Aamiin.

## Desain & Fungsi Program
Aplikasi desktop Java Swing untuk mengelola **data anggota Polri** (data fiktif) dengan operasi CRUD dan tampilan tabel. Kategori mengacu pada satuan di tingkat Mabes Polri.

**Struktur class**
- `Person.java`: class model dengan atribut `id`, `nama`, `tahunLahir`, `kategori` (satuan), dan atribut baru `pangkat` (komponen `JComboBox`).
- `PeopleMenu.java`: class utama (extends `JFrame`), berisi logika CRUD dan listener.
- `PeopleMenu.form`: desain GUI (JTextField, JComboBox, JButton, JTable).

**Kategori (Satuan) dengan JComboBox**
Pilihan: Bareskrim, Korlantas, Baintelkam, Korsabhara, Korbrimob, Korpolairud, Densus 88 AT, Div Propam. Memakai combo box supaya penulisan satuan seragam dan tidak ada salah ketik.

**Atribut baru: Pangkat (JComboBox)**
- Berisi 22 pangkat Polri sesuai Perkapolri 3/2016, dari terendah sampai tertinggi: golongan Tamtama (Bharada sampai Abrip), Bintara (Bripda sampai Aiptu), dan Perwira (Ipda sampai Jenderal Pol).
- Pangkat harus dipilih (pilihan pertama "-- Pilih Pangkat --" tidak valid), sama seperti Satuan.
- Validasi logis di `readForm()`: usia anggota minimal 18 tahun.

**Method di PeopleMenu**
- `setTable()`: membuat model tabel dari `ArrayList<Person>`.
- `insertData()`: menambah data (validasi field kosong, satuan dan pangkat harus dipilih, tahun harus angka, ID tidak boleh kembar).
- `updateData()`: mengubah data yang dipilih dari tabel.
- `deleteData()`: menghapus data, didahului dialog konfirmasi Yes/No.
- `clearForm()`: mengosongkan form dan kembali ke mode Add.
- `readForm()`: membaca dan memvalidasi input form.

## Alur Program
1. Program dijalankan dari `main()`, window muncul dengan 5 data awal dari `populateList()`.
2. Mode **Add**: isi form (pilih satuan dan pangkat) lalu klik **Add**, data masuk ke list dan tabel diperbarui.
3. Klik salah satu baris tabel: form terisi, tombol berubah menjadi **Update** dan tombol **Delete** muncul.
4. Klik **Update** untuk menyimpan perubahan.
5. Klik **Delete**, muncul konfirmasi; pilih Yes untuk menghapus.
6. Klik **Cancel** untuk mengosongkan form dan kembali ke mode Add.

## Dokumentasi

Video demonstrasi program:

[TP4.mp4](TP4.mp4)

<video width="600" controls>
  <source src="TP4.mp4" type="video/mp4">
</video>
