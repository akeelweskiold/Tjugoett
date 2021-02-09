//-----------------------------------------------------------------------------------------------------------
//
// 			TjugoettSub.java
//			----------------
//
// Innh�ler klass- och metoddefinitioner f�r programmet Tjugoett.
//
// 2017-03-15 �ke Elweski�ld

//-----------------------------------------------------------------------------------------------------------

public class TjugoettSub {
	
//-----------------------------------------------------------------------------------------------------------
// Metod SlumpaTal.
// Skapa f�lt med 52 element, som inneh�ller talen 0-51 i slumpad ordning.
// Anropas fr�n metod SkapaBlandadLek.
//-----------------------------------------------------------------------------------------------------------
	public static void SlumpaTal(int[] a) {
		int i, slumpIndex, slumpVarde;
		int[] b = new int[a.length];
		
		
		for(i=0; i<a.length; i++) {						// Skapa ett f�lt med 52 element, d�r v�rdet = indexet.
			a[i] = i;
		}

		int kvar = a.length;
		
		for(i=0; i<a.length; i++) {			
			slumpIndex = (int) (kvar * Math.random());	// Slumpa ett tal mellan 0 o s� m�nga element som finns kvar.
			slumpVarde = a[slumpIndex];					// Spara undan talet som finns p� den nyss framslumpade positionen.
			
			b[i] = slumpVarde;
			a[slumpIndex] = a[kvar-1];					// Flytta v�rdet i sista pos till den pos som nyss slumpats fram.

			kvar--;										// Minska r�knaren f�r hur m�nga element som �r kvar att slumpa.
		}
		for(i=0; i<a.length; i++) a[i] = b[i];			// Flytta f�ltet med de framslumpade talen till metodens
														// parameter, s� att den returneras.
	}													
	
//-----------------------------------------------------------------------------------------------------------
// Metod SkapaBlandadLek.
// Syftet med metoden �r att skapa en blandad kortlek med 52 kort.
//-----------------------------------------------------------------------------------------------------------
	public static void SkapaBlandadLek(Kortlek lek) {
		int i=0;
		
		// Skapa f�rst en sorterad kortlek.
		for(int f = Kort.KLOVER; f <= Kort.SPADER; f++) {
			for(int v=1; v<=13; v++) {
				lek.bunt[i] = new Kort();
				lek.bunt[i].farg = f;
				lek.bunt[i].valor = v;
				i++;
			}
		}
			
		// Skapa en talserie som inneh�ller talen 0 - 51 i slumpad ordning.
				int[] a = new int[52];
				SlumpaTal(a); // a inneh�ller den slumpade talserien.	
							
		// Skapa en blandad kortlek genom att anv�nda den slumpade talserien.
				Kortlek blandadLek = new Kortlek();
				for(i=0; i<=51; i++) {
					blandadLek.bunt[i] = new Kort();
					blandadLek.bunt[i] = lek.bunt[a[i]];
				}
				
		// Flytta den blandade kortleken till metodens parameter s� att den returneras.				
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
	
	public static final String[] KOLOR = { "Kl�ver ", "Ruter  ", "Hj�rter", "Spader " };
	
	public String toString() {
		return KOLOR[farg] + " " + valor;
	}
}

//-----------------------------------------------------------------------------------------------------------
// Klassdefinition Spelare.
// Syftet med klassen �r att kunna skapa instansmetoden beraknaTotalpoang.
// Instanserna till "Spelare" �r bankiren respektive den regelr�tta spelaren.
//-----------------------------------------------------------------------------------------------------------
class Spelare {
	boolean dragitEss = false;
	int totalPoang = 0;
	
//-----------------------------------------------------------------------------------------------------------
// Metod beraknaTotalpoang.
// Syftet med metoden �r att h�lla redan p� totalpo�ngen f�r bankiren s�v�l som spelaren.
// Om ett ess blir dragit, s� minns detta i flaggan dragitEss. 
// I f�rsta hand r�knas esset som 14, och d� s�tts flaggan.  
// Om totalpo�ngen senare �verstiger 21, s� backas totalpo�ngen med 13, och flaggan �terst�lls.
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
	// Syftet med metoden �r att nollst�lla po�ngr�knaren och �terst�lla flaggan f�r att man dragit ett ess.
	//-----------------------------------------------------------------------------------------------------------
	public void initieraPoang() {
		totalPoang = 0;
		dragitEss = false;
	}
	
}

