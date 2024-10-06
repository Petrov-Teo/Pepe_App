package PetrovTodor.PepeMedicalKids.enums;

public enum Iva {
    IVA_4(4),
    IVA_5(5),
    IVA_10(10),
    IVA_22(22);

    private final int valore;

    Iva(int valore) {
        this.valore = valore;
    }

    public int getValore() {
        return valore;
    }

}
