package harambesoft.com.plusone.api.model;

/**
 * Created by yucel on 12.11.2016.
 */

public class UserModel {

    private Integer id;
    private String name;
    private String email;
    private String profilePicture;
    private String xp;
    private String createDate;
    private String fullname;
    private String gender;
    private String country;
    private String city;
    private String profession;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserModel() {
    }

    /**
     *
     * @param id
     * @param email
     * @param profilePicture
     * @param profession
     * @param name
     * @param gender
     * @param xp
     * @param createDate
     * @param fullname
     * @param city
     * @param country
     */
    public UserModel(Integer id, String name, String email, String profilePicture, String xp, String createDate, String fullname, String gender, String country, String city, String profession) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.xp = xp;
        this.createDate = createDate;
        this.fullname = fullname;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.profession = profession;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

}
