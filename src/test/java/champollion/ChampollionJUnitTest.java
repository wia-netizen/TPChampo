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
		
	@Before
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");		
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		// Le dernier paramètre représente la "marge d'erreur" acceptable
		assertEquals(0, untel.heuresPrevues(), 0.001f);
	}
	
	@Test
	public void testAjouteHeures() {
		untel.ajouteEnseignement(uml, 0, 10, 0);
		// Il a maintenant 10 heures prévues pour cette UE
		assertEquals(10, untel.heuresPrevuesPourUE(uml), 0.001f);
		untel.ajouteEnseignement(uml, 0, 20, 0);
		// Il a maintenant 10 + 20 heures prévues pour cette UE
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml), 0.001f);		
		
	}
	
}
