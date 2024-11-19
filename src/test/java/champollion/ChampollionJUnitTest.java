package champollion;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
	Intervention inter_uml_cm, inter_java_cm;
	Salle uneSalle;

	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");
		uneSalle = new Salle("uneSalle", 999);
		inter_uml_cm = new Intervention(new Date(), 12, 8, untel, uml, TypeIntervention.CM, uneSalle);
		inter_java_cm = new Intervention(new Date(), 10, 8, untel, java, TypeIntervention.CM, uneSalle);
	}

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
				"Un nouvel enseignant doit avoir 0 heures prévues");
	}

	@Test
	public void testAjouteHeures() {
// 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);
		assertEquals(10, untel.heuresPrevuesPourUE(uml),
				"L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

// Ajout de 20h TD pour UML
		untel.ajouteEnseignement(uml, 0, 20, 0);
		assertEquals(30, untel.heuresPrevuesPourUE(uml),
				"L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");

// Test avec des valeurs négatives
		assertThrows(IllegalArgumentException.class, () -> untel.ajouteEnseignement(uml, -1, -1, -1), "On ne peut pas ajouter de valeur négative");
	}

	@Test
	public void testEnseignantEnSousService() {
		assertTrue(untel.enSousService(), "Une personne doit avoir plus de Enseignant.HEURES_PREVUES_MINIMUM pour ne plus être en sousService");
		untel.ajouteEnseignement(uml, 0, Enseignant.HEURES_PREVUES_MINIMUM, 0);
		assertFalse(untel.enSousService(), "Une personne doit avoir moins de Enseignant.HEURES_PREVUES_MINIMUM pour être en sousService");
	}



	@Test
	public void testAjouteIntervention() {
// Cas valide : Ajouter une intervention
		untel.ajouteEnseignement(uml, 12, 0, 0); // Ajout de 12h CM pour UML
		untel.ajouteIntervention(inter_uml_cm); // Intervention de 12h CM pour UML
		assertEquals(untel.resteAPlanifier(uml, TypeIntervention.CM), 0, "Toutes les heures CM doivent être planifiées.");

// Cas d'erreur : Ajouter une intervention qui dépasse les heures prévues
		Intervention interTropLong = new Intervention(new Date(), 20, 8, untel, uml, TypeIntervention.CM, uneSalle);
		assertThrows(IllegalArgumentException.class, () -> untel.ajouteIntervention(interTropLong), "On ne peut pas dépasser le nombre d'heures prévues pour l'UE et le type d'intervention");
	}

	@Test
	public void testHeuresPrevues() {
// Tester le calcul des heures prévues pour l'enseignant
		untel.ajouteEnseignement(uml, 10, 10, 10); // 10h CM, 10h TD, 10h TP pour UML
		untel.ajouteEnseignement(java, 5, 5, 5); // 5h CM, 5h TD, 5h TP pour Java

// Calcul attendu :
// UML : 10*1.5 (CM) + 10 (TD) + 10*0.75 (TP) = 15 + 10 + 7.5 = 32.5 (arrondi à 33)
// Java : 5*1.5 (CM) + 5 (TD) + 5*0.75 (TP) = 7.5 + 5 + 3.75 = 16.25 (arrondi à 16)
		assertEquals(33 + 16, untel.heuresPrevues(), "Le total des heures prévues devrait être 49");
	}




	@Test
	public void testResteAPlanifier() {
		assertEquals(untel.resteAPlanifier(uml, TypeIntervention.CM), 0, "Le reste à planifié doit être égal à 0 pour un enseignement non planifié d'un enseignant");
		untel.ajouteEnseignement(uml, 12, 0, 0);
		assertEquals(untel.resteAPlanifier(uml, TypeIntervention.CM), 12, "Il doit rester les heures à planifié ajouté précédement");
		untel.ajouteIntervention(inter_uml_cm);
		assertEquals(untel.resteAPlanifier(uml, TypeIntervention.CM), 0, "Le calcul des heures déjà planifié n'est pas bon");
	}

}

