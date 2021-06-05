package Models;

public class UserModel {

    private String name;
    private String email;
    private String country;
    private String city;
    private String password;
    private String rol;
    private String cityApart;
    private String countryApart;
    private String address;
    private String googleMaps;
    private String rooms;
    private String value;
    private String images;
    private String featured;
    private String review;

    public UserModel(String name, String email, String country, String city, String password, String cityApart, String countryApart, String address, String googleMaps, String rooms, String value, String images, String featured, String review) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.city = city;
        this.password = password;
        this.cityApart = cityApart;
        this.countryApart = countryApart;
        this.address = address;
        this.googleMaps = googleMaps;
        this.rooms = rooms;
        this.value = value;
        this.images = images;
        this.featured = featured;
        this.review = review;
    }

    private UserModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCityApart() {
        return cityApart;
    }

    public void setCityApart(String cityApart) {
        this.cityApart = cityApart;
    }

    public String getCountryApart() {
        return countryApart;
    }

    public void setCountryApart(String countryApart) {
        this.countryApart = countryApart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogleMaps() {
        return googleMaps;
    }

    public void setGoogleMaps(String googleMaps) {
        this.googleMaps = googleMaps;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
