package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "telephone")
public final class Telephone extends BaseEntity {
    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "area_code", nullable = false, length = 3)
    private String areaCode;

    @Column(name = "number", nullable = false, length = 14)
    private String number;

    public Telephone() {
    }

    public Telephone(String countryCode, String areaCode, String number) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "+" + countryCode + " (" + areaCode + ")" + " " + number ;
    }
}
