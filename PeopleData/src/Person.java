public class Person {
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori;  // satuan Polri (dipilih lewat JComboBox)
    private String pangkat;   // atribut baru (JComboBox)

    // Constructor
    public Person(String id, String nama, int tahunLahir, String kategori, String pangkat) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.pangkat = pangkat;
    }

    // Getter & Setter

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getTahunLahir() { return tahunLahir; }
    public void setTahunLahir(int tahunLahir) { this.tahunLahir = tahunLahir; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getPangkat() { return pangkat; }
    public void setPangkat(String pangkat) { this.pangkat = pangkat; }
}