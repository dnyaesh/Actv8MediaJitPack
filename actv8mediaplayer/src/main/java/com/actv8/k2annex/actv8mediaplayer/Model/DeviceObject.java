package com.actv8.k2annex.actv8mediaplayer.Model;

/**
 * Created by mgriego on 6/8/17.
 */

public class DeviceObject {

    private String uuid;
    private String manufacturer;
    private String model;
    private String locale;
    private String os_name = "android";
    private String carrier_name;
    private String sdk_version;
    private String device_identifier;
    private String os_version;
    private String latitude;
    private String longitude;

    Placemark placemark;


    public class Placemark
    {
        String ZIP;
        String City;
        String Name;
        String State;
        String Street;
        String Country;
        String CountryCode;
        String SubLocality;
        String Thoroughfare;
        String SubThoroughfare;
        String [] FormattedAddressLines; //": ["American Friends of Bar-Ilan University", "115 S Arnaz Dr", "Beverly Hills, CA  90211", "United States"],
        String SubAdministrativeArea;


        public String getZIP() {
            return ZIP;
        }

        public void setZIP(String ZIP) {
            this.ZIP = ZIP;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String countryCode) {
            CountryCode = countryCode;
        }

        public String getSubLocality() {
            return SubLocality;
        }

        public void setSubLocality(String subLocality) {
            SubLocality = subLocality;
        }

        public String getThoroughfare() {
            return Thoroughfare;
        }

        public void setThoroughfare(String thoroughfare) {
            Thoroughfare = thoroughfare;
        }

        public String getSubThoroughfare() {
            return SubThoroughfare;
        }

        public void setSubThoroughfare(String subThoroughfare) {
            SubThoroughfare = subThoroughfare;
        }

        public String[] getFormattedAddressLines() {
            return FormattedAddressLines;
        }

        public void setFormattedAddressLines(String[] formattedAddressLines) {
            FormattedAddressLines = formattedAddressLines;
        }

        public String getSubAdministrativeArea() {
            return SubAdministrativeArea;
        }

        public void setSubAdministrativeArea(String subAdministrativeArea) {
            SubAdministrativeArea = subAdministrativeArea;
        }
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDevice_manufacturer(String device_manufacturer) {
        this.manufacturer = device_manufacturer;
    }

    public void setDevice_model(String device_model) {
        this.model = device_model;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setCarrier_name(String carrier_name) {
        this.carrier_name = carrier_name;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public void setDevice_identifier(String device_identifier) {
        this.device_identifier = device_identifier;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUuid() {
        return uuid;
    }

    public Placemark getPlacemark() {
        return placemark;
    }

    public void setPlacemark(Placemark placemark) {
        this.placemark = placemark;
    }
}
