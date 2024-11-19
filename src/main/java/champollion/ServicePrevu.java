package champollion;

public class ServicePrevu {
    private int volumeCM;
    private int volumeTD;
    private int volumeTP;

    public ServicePrevu(int volumeCM, int volumeTD, int volumeTP) {
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
    }

    public int getVolumeCM() {
        return volumeCM;
    }

    public int getVolumeTD() {
        return volumeTD;
    }

    public int getVolumeTP() {
        return volumeTP;
    }

    public void addVolumeCM(int volumeCM) {
        this.volumeCM = this.volumeCM + volumeCM;
    }

    public void addVolumeTD(int volumeTD) {
        this.volumeTD = this.volumeTD + volumeTD;
    }

    public void addVolumeTP(int volumeTP) {
        this.volumeTP = this.volumeTP + volumeTP;
    }
}


