package tonydarko.wi_fiscanner;

public class Element {
    private String name;
    private String security;
    private String level;

    public Element(String name, String security, String level) {
        this.name = name;
        this.security = security;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getSecurity() {
        return security;
    }

    public String getLevel() {
        return level;
    }
}
