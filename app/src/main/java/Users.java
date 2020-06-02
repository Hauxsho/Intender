public class Users
{
    public String nam,birth,gender;

    public Users(String nam, String birth, String gender) {
        this.nam = nam;
        this.birth = birth;
        this.gender = gender;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
