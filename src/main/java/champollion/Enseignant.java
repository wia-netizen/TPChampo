package champollion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Enseignant extends Personne {

    private Map<UE, ServicePrevu> lesEnseignements = new HashMap<>();
    private Set<Intervention> interventions = new HashSet<>();
    public static final int HEURES_PREVUES_MINIMUM = 192;
    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        Double heuresPrevues = 0.0;
        for (UE key:lesEnseignements.keySet()) {
            heuresPrevues = heuresPrevues +
                    lesEnseignements.get(key).getVolumeCM()*1.5 +
                    lesEnseignements.get(key).getVolumeTD() +
                    lesEnseignements.get(key).getVolumeTP()*0.75;
        }
        return (int) Math.round(heuresPrevues);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        Double heuresPrevuesPourUE = lesEnseignements.get(ue).getVolumeCM()*1.5 +
                lesEnseignements.get(ue).getVolumeTD() +
                lesEnseignements.get(ue).getVolumeTP()*0.75;
        return heuresPrevuesPourUE.intValue();
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        if (volumeCM < 0 || volumeTD < 0 || volumeTP < 0) throw new IllegalArgumentException("On ne peut pas ajouter de valeur négative");
        if (this.lesEnseignements.containsKey(ue)){
            ServicePrevu servicePrevu = lesEnseignements.get(ue);
            servicePrevu.addVolumeCM(volumeCM);
            servicePrevu.addVolumeTD(volumeTD);
            servicePrevu.addVolumeTP(volumeTP);
            return;
        }
        this.lesEnseignements.put(ue, new ServicePrevu(volumeCM, volumeTD, volumeTP));
    }

    /**
     * Vérifie si un enseignant est en sous-service: il est en sous-service si son équivalant heure TD est en dessous
     * de HEURES_PREVUES_MINIMUM
     * @return un booléen répondant à la fonction
     */
    public boolean enSousService(){
        return this.heuresPrevues() < HEURES_PREVUES_MINIMUM;
    }

    /**
     * Calcule le reste d'heures à planifié d'une UE et d'un type d'intervention
     * @param ue une unité d'enseignement
     * @param type un type d'intervention
     * @return une durée calculé
     */
    public int resteAPlanifier(UE ue, TypeIntervention type){
        if (this.lesEnseignements.containsKey(ue)){
            int heuresPlanifiées = 0;
            switch (type){
                case CM:
                    for (Intervention inter: interventions) {
                        if (inter.getTypeIntervention() == TypeIntervention.CM){
                            heuresPlanifiées += inter.getDuree();
                        }
                    }
                    return this.lesEnseignements.get(ue).getVolumeCM() - heuresPlanifiées;
                case TD:
                    for (Intervention inter: interventions) {
                        if (inter.getTypeIntervention() == TypeIntervention.TD){
                            heuresPlanifiées += inter.getDuree();
                        }
                    }
                    return this.lesEnseignements.get(ue).getVolumeTD() - heuresPlanifiées;
                case TP:
                    for (Intervention inter: interventions) {
                        if (inter.getTypeIntervention() == TypeIntervention.TP){
                            heuresPlanifiées += inter.getDuree();
                        }
                    }
                    return this.lesEnseignements.get(ue).getVolumeTP() - heuresPlanifiées;
            }
        }
        return 0;
    }

    /**
     * ajoute une intervention pour un enseignant
     * @param inter une intervention
     */
    public void ajouteIntervention(Intervention inter){
        if (inter.getDuree() > resteAPlanifier(inter.getUe(), inter.getTypeIntervention())) throw new IllegalArgumentException("On ne peut pas dépasser le nombre d'heures prévues");
        this.interventions.add(inter);
    }
}