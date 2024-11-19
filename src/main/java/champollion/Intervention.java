package champollion;
import java.util.Date;

public class Intervention {
    private Date debut;
    private int duree;
    private boolean annulee = false;
    private int heureDebut;
    private Enseignant enseignant;
    private UE ue;
    private TypeIntervention typeIntervention;
    private Salle salle;

    public Intervention(Date debut, int duree, int heureDebut, Enseignant enseignant, UE ue, TypeIntervention typeIntervention, Salle salle) {
        this.debut = debut;
        this.duree = duree;
        this.heureDebut = heureDebut;
        this.enseignant = enseignant;
        this.ue = ue;
        this.typeIntervention = typeIntervention;
        this.salle = salle;
    }

    public Date getDebut() {
        return debut;
    }

    public int getDuree() {
        return duree;
    }

    public boolean isAnnulee() {
        return annulee;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public TypeIntervention getTypeIntervention() {
        return typeIntervention;
    }

    public UE getUe() {
        return ue;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}

