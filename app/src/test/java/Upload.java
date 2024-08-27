public class Upload {
    private String name;
    private String url;

    // Konstruktor tanpa parameter diperlukan oleh Firebase
    public Upload() {
    }

    // Konstruktor dengan parameter untuk inisialisasi
    public Upload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // Getter untuk nama file
    public String getName() {
        return name;
    }

    // Setter untuk nama file
    public void setName(String name) {
        this.name = name;
    }

    // Getter untuk URL file
    public String getUrl() {
        return url;
    }

    // Setter untuk URL file
    public void setUrl(String url) {
        this.url = url;
    }
}