/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package champollion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rbastide
 */
public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
	
	public ChampollionJUnitTest() {
	}
	
	@Before
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");		
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertTrue((0f <= untel.heuresPrevues()) && (untel.heuresPrevues() <= 0f));
	}
	
	@Test
	public void testAjouteHeures() {
		// Au début, aucune heure prévue
		assertEquals(0, untel.heuresPrevues());
		untel.ajouteEnseignement(uml, 0, 10, 0);
		// Il a maintenant 10 heures prévues pour cette UE
		assertEquals(10, untel.heuresPrevuesPourUE(uml));
		untel.ajouteEnseignement(uml, 0, 20, 0);
		// Il a maintenant 10 + 20 heures prévues pour cette UE
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml));		
		
	}
	
}
