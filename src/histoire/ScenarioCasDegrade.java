package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		try {
			System.out.println(etal.acheterProduit(3, new Gaulois("Coucou", 10)));
		} catch (IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
	}
}
