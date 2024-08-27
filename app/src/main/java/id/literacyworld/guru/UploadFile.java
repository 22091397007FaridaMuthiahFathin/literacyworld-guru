package id.literacyworld.guru;

public class UploadFile {

    public String name;
    public String url;
    public String type;

    public UploadFile() {
    }

    public UploadFile(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}