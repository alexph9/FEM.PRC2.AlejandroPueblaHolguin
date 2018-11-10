package es.upm.miw.firebaselogin;

public class Delivery {

    private String user;
    private String game;
    private String sendDate;
    private String country;

    public Delivery(String user, String game, String sendDate, String country) {
        this.user = user;
        this.game = game;
        this.sendDate = sendDate;
        this.country = country;
    }


    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "sendDate='" + sendDate + '\'' +
                ", user='" + user + '\'' +
                ", country='" + country + '\'' +
                ", game='" + game + '\'' +
                '}';
    }
}
