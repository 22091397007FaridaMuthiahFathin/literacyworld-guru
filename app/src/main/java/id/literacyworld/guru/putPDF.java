package id.literacyworld.guru;

public class putPDF {
    private String name;
    private String url;
    private boolean isRead;
    private String id;

    // Konstruktor tanpa parameter (diperlukan oleh Firebase)
    public putPDF() {
    }

    // Konstruktor dengan semua parameter
    public putPDF(String name, String url, boolean isRead, String id) {
        this.name = name;
        this.url = url;
        this.isRead = isRead;
        this.id = id;
    }

    // Konstruktor dengan dua parameter (name, url)
    public putPDF(String name, String url) {
        this.name = name;
        this.url = url;
        this.isRead = false; // Nilai default
        this.id = ""; // Nilai default
    }

    // Getter dan setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}