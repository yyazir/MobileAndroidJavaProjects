package com.yumel.rehber;

import com.google.gson.annotations.SerializedName;

public class Pharmacy {
    @SerializedName("eczaneAdi")
    private String eczaneAdi;

    @SerializedName("eczaneIlAdi")
    private String eczaneIlAdi;

    @SerializedName("eczaneIlceAdi")
    private String eczaneIlceAdi;

    @SerializedName("eczaneAdres")
    private String eczaneAdres;

    @SerializedName("tarih")
    private String tarih;

    @SerializedName("baslangic")
    private String baslangic;

    @SerializedName("bitis")
    private String bitis;

    @SerializedName("enlem")
    private String enlem;

    @SerializedName("boylam")
    private String boylam;

    @SerializedName("glnNo")
    private String glnNo;

    @SerializedName("id")
    private int id;

    @SerializedName("olusturmaTarihi")
    private String olusturmaTarihi;

    @SerializedName("guncellemeTarihi")
    private String guncellemeTarihi;

    @SerializedName("silindi")
    private String silindi;

    // Getter methods
    public String getEczaneAdi() {
        return eczaneAdi;
    }

    public String getEczaneIlAdi() {
        return eczaneIlAdi;
    }

    public String getEczaneIlceAdi() {
        return eczaneIlceAdi;
    }

    public String getEczaneAdres() {
        return eczaneAdres;
    }

    public String getTarih() {
        return tarih;
    }

    public String getBaslangic() {
        return baslangic;
    }

    public String getBitis() {
        return bitis;
    }

    public String getEnlem() {
        return enlem;
    }

    public String getBoylam() {
        return boylam;
    }

    public String getGlnNo() {
        return glnNo;
    }

    public int getId() {
        return id;
    }

    public String getOlusturmaTarihi() {
        return olusturmaTarihi;
    }

    public String getGuncellemeTarihi() {
        return guncellemeTarihi;
    }

    public String getSilindi() {
        return silindi;
    }
}


