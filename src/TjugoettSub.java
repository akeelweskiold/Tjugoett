//-----------------------------------------------------------------------------------------------------------
//
// 			TjugoettSub.java
//			----------------
//
// Innhåler klass- och metoddefinitioner för programmet Tjugoett.
//
// 2017-03-15 Åke Elweskiöld

//-----------------------------------------------------------------------------------------------------------

public class TjugoettSub {
	
//-----------------------------------------------------------------------------------------------------------
// Metod SlumpaTal.
// Skapa fält med 52 element, som innehåller talen 0-51 i slumpad ordning.
// Anropas från metod SkapaBlandadLek.
//-----------------------------------------------------------------------------------------------------------
	public static void SlumpaTal(int[] a) {
		int i, slumpIndex, slumpVarde;
		int[] b = new int[a.length];
		
		
		for(i=0; i<a.length; i++) {						// Skapa ett fält med 52 element, där värdet = indexet.
			a[i] = i;
		}

		int kvar = a.length;
		
		for(i=0; i<a.length; i++) {			
			slumpIndex = (int) (kvar * Math.random());	// Slumpa ett tal mellan 0 o så många element som finns kvar.
			slumpVarde = a[slumpIndex];					// Spara undan talet som finns på den nyss framslumpade positionen.
			
			b[i] = slumpVarde;
			a[slumpIndex] = a[kvar-1];					// Flytta värdet i sista pos till den pos som nyss slumpats fram.

			kvar--;										// Minska räknaren för hur många element som är kvar att slumpa.
		}
		for(i=0; i<a.length; i++) a[i] = b[i];			// Flytta fältet med de framslumpade talen till metodens
														// parameter, så att den returneras.
	}													
	
//-----------------------------------------------------------------------------------------------------------
// Metod SkapaBlandadLek.
// Syftet med metoden är att skapa en blandad kortlek med 52 kort.
//-----------------------------------------------------------------------------------------------------------
	public static void SkapaBlandadLek(Kortlek lek) {
		int i=0;
		
		// Skapa först en sorterad kortlek.
		for(int f = Kort.KLOVER; f <= Kort.SPADER; f++) {
			for(int v=1; v<=13; v++) {
				lek.bunt[i] = new Kort();
				lek.bunt[i].farg = f;
				lek.bunt[i].valor = v;
				i++;
			}
		}
			
		// Skapa en talserie som innehåller talen 0 - 51 i slumpad ordning.
				int[] a = new int[52];
				SlumpaTal(a); // a innehåller den slumpade talserien.	
							
		// Skapa en blandad kortlek genom att använda den slumpade talserien.
				Kortlek blandadLek = new Kortlek();
				for(i=0; i<=51; i++) {
					blandadLek.bunt[i] = new Kort();
					blandadLek.bunt[i] = lek.bunt[a[i]];
				}
				
		// Flytta den blandade kortleken till metodens parameter så att den returneras.				
				for(i=0; i<=51; i++)
					lek.bunt[i] = blandadLek.bunt[i];
	}
	
//-----------------------------------------------------------------------------------------------------------
	
} 

//-----------------------------------------------------------------------------------------------------------
// Klassdefinitioner.
//-----------------------------------------------------------------------------------------------------------
class Kortlek {
	Kort[] bunt = new Kort[52];
}

class Kort {
	int farg, valor;
	
	public static final int KLOVER  = 0;
	public static final int RUTER   = 1;
	public static final int HJARTER = 2;
	public static final int SPADER  = 3;
	
	public static final String[] KOLOR = { "Klöver ", "Ruter  ", "Hjärter", "Spader " };
	
	public String toString() {
		return KOLOR[farg] + " " + valor;
	}
}

//-----------------------------------------------------------------------------------------------------------
// Klassdefinition Spelare.
// Syftet med klassen är att kunna skapa instansmetoden beraknaTotalpoang.
// Instanserna till "Spelare" är bankiren respektive den regelrätta spelaren.
//-----------------------------------------------------------------------------------------------------------
class Spelare {
	boolean dragitEss = false;
	int totalPoang = 0;
	
//-----------------------------------------------------------------------------------------------------------
// Metod beraknaTotalpoang.
// Syftet med metoden är att hålla redan på totalpoängen för bankiren såväl som spelaren.
// Om ett ess blir dragit, så minns detta i flaggan dragitEss. 
// I första hand räknas esset som 14, och då sätts flaggan.  
// Om totalpoängen senare överstiger 21, så backas totalpoängen med 13, och flaggan återställs.
//-----------------------------------------------------------------------------------------------------------
	public int beraknaTotalpoang(int valor) {
		if(valor == 1) {
			dragitEss = true;
			valor = 14;
		}
			
		totalPoang += valor;
			
		if(totalPoang > 21 && dragitEss) {
			totalPoang -= 13;
			dragitEss = false;
		}
		
		return totalPoang;
	}

	//-----------------------------------------------------------------------------------------------------------
	// Metod initieraPoang.
	// Syftet med metoden är att nollställa poängräknaren och återställa flaggan för att man dragit ett ess.
	//-----------------------------------------------------------------------------------------------------------
	public void initieraPoang() {
		totalPoang = 0;
		dragitEss = false;
	}
	
}

