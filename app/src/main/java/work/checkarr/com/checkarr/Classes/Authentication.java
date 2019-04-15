package work.checkarr.com.checkarr.Classes;

public class Authentication {
    Boolean issued;
    String token;
    String refresh_token;
    String activation_status;
    Integer user_id;
    String user_email;
    String dp_url;

    public Authentication(){

    }

    public Authentication(Boolean issued, String token, String refresh_token, String activation_status, Integer user_id, String user_email, String dp_url){
        this.issued = issued;
        this.token = token;
        this.refresh_token = refresh_token;
        this.activation_status = activation_status;
        this.user_id = user_id;
        this.user_email = user_email;
        this.dp_url = dp_url;
    }

    public Boolean getIssued() {
        return issued;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getActivationStatus() {
        return activation_status;
    }

    public Integer getUserID() {
        return user_id;
    }

    public String getUserEmail() {
        return user_email;
    }

    public String getDpUrl() {
        return dp_url;
    }

    public void setIssued(Boolean issued) {
        this.issued = issued;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setActivationStatus(String activation_status) {
        this.activation_status = activation_status;
    }

    public void setUserID(Integer user_id) {
        this.user_id = user_id;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    public void setDpUrl(String dp_url) {
        this.dp_url = dp_url;
    }
}
