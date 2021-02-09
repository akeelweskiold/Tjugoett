//-----------------------------------------------------------------------------------------------------------
//

// 			Tjugoett - ett kortspel
//          -----------------------
// 
// Programmet kommunicerar med anv�ndaren b�de med dialogboxar och utskrifter i kommandof�nstret.
// I dialogboxarna fokuseras informationen p� vad anv�ndaren f�rv�ntas g�ra. 
// I kommandof�nstret syns tidigare drag, s� att man kan se historiken. �ven anv�nd f�r avlusning.
//
// En spelomg�ng varar s� l�nge anv�ndaren vill, men avslutas senast n�r kortleken tar slut. 
//
// Programmet har tv� konstanter som kan manipuleras:
// BANKIREN_STANNAR_PA �r den po�ngniv� som bankiren alltid stannar p�. Jag har satt den till 15, 
// f�r att det ska blir lagom sv�rt att vinna �ver bankiren.
// TEST_MODE. Om man s�tter den till true s� slumpas inte korten fram, utan anv�ndaren v�ljer val�r
// f�r varje kort. Bra f�r den som hatar att f�rlora, men ocks� f�r den som ska prova ut programmet ;-)
//
// Programmet best�r av tv� filer; Tjugoett.java och TjugoettSub.java . 
// I inl�mninguppgiften �r dom hopslagna till filen Programmering2_UppgiftA_�ke.txt
//
// 2017-03-15 �ke Elweski�ld
//
//-----------------------------------------------------------------------------------------------------------

// Fil Tjugoett.java:

import javax.swing.JOptionPane;

public class Tjugoett {

	public static void main(String[] args) {
		Spelare spelare = new Spelare();
		Spelare bankir = new Spelare();
		boolean flerKortForSpelare;	
		boolean flerKortForBankir;
		boolean nyttSpel;
		String medd;
		
		final int BANKIREN_STANNAR_PA = 15;			// V�rdet d�r bankiren stannar.
		final boolean TEST_MODE = true;			// Testflagga. Om true, kan operat�r ange �nskat v�rde p� kort. 

		Kortlek kl = new Kortlek();

		TjugoettSub.SkapaBlandadLek(kl);			// Blanda kortleken.
		

//-----------------------------------------------------------------------------------------------------------
// V�lkomstmeddelande.
//-----------------------------------------------------------------------------------------------------------
		medd = "        xHejsan! \n" +
		       "Vill du spela Tjugoett?";
		int val = JOptionPane.showConfirmDialog(null, medd);
		nyttSpel = (val==JOptionPane.YES_OPTION);
		if(!nyttSpel) {
			System.out.println("Spelet avbryts. Ha en trevlig dag!");
			System.exit(0);
		}
		
		int i = -1;									// Initiera index f�r korten. (-1 eftersom den �kas i b�rjan av yttre loopen, o f�rsta indexv�rdet �r noll.)
		
//-----------------------------------------------------------------------------------------------------------
// Starta spelet.
//-----------------------------------------------------------------------------------------------------------
		while(nyttSpel) {
			spelare.initieraPoang();				// Initiera variabler.
			bankir.initieraPoang();
			flerKortForSpelare = true;
			flerKortForBankir = false;
			
			System.out.println("Spelare");

//-----------------------------------------------------------------------------------------------------------
// L�t spelaren dra kort.
//-----------------------------------------------------------------------------------------------------------
			while(flerKortForSpelare) {
				i++;												// R�kna upp index f�r kort.
				if(i > 51) {										// Avbryt spelet om kortleken �r slut.
					System.out.println("Kortleken �r slut, spelet avbryts. Ha en trevlig dag!");
					System.exit(0);
				}
				
				if(TEST_MODE) {										// Om TEST_MODE �r satt, ange valfritt v�rde p� n�sta kort.
					String s = JOptionPane.showInputDialog("TEST_MODE: Ange kortets val�r 1-13: ");
					kl.bunt[i].valor = Integer.parseInt(s);
				}
				spelare.totalPoang = spelare.beraknaTotalpoang(kl.bunt[i].valor);		// Dra ett kort och ber�kna totalpo�ngen.
				System.out.println( i + "\t" + kl.bunt[i] + "\t" + spelare.totalPoang);
				medd = "Ditt kort blev : " + kl.bunt[i] + "\n" +
		           	   "Din totala po�ng �r : " + spelare.totalPoang + "\n" + "\n";
				
				// Om spelaren f�r exakt 21, skriv ut grattis och fr�ga om nytt spel �nskas.
				if(spelare.totalPoang == 21) {
					flerKortForSpelare = false;
					medd += "GRATTIS!! Du vinner " + "\n" + "\n" +
							"Spela igen?";					
					val = JOptionPane.showConfirmDialog(null, medd);					
					if(val==JOptionPane.NO_OPTION || val==JOptionPane.CANCEL_OPTION) {	// Avsluta spelet om "Nej" eller "Avbryt" har valts.
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
					nyttSpel = (val==JOptionPane.YES_OPTION);							// Starta nytt spel om "Ja" har valts.
				}
				
				// Om spelare f�r > 21, skriv ut att banken vinner och fr�ga om nytt spel �nskas.
				if(spelare.totalPoang > 21) {
					flerKortForSpelare = false;
					medd += "Tjock!! Banken vinner" + "\n" + "\n" +
							"Spela igen?";
					val = JOptionPane.showConfirmDialog(null, medd);					
					if(val==JOptionPane.NO_OPTION || val==JOptionPane.CANCEL_OPTION) {	// Avsluta spelet om "Nej" eller "Avbryt" har valts.
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
					nyttSpel = (val==JOptionPane.YES_OPTION);							// Starta nytt spel om "Ja" har valts.
				}
				
				// Om spelaren f�r < 21, fr�ga om nytt kort �nskas
				if(spelare.totalPoang < 21) {
					medd += "Vill du ha fler kort?";
					val = JOptionPane.showConfirmDialog(null, medd);
					if(val==JOptionPane.CANCEL_OPTION) {
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
					flerKortForSpelare = (val==JOptionPane.YES_OPTION);					// Dra �nnu ett kort f�r spelare om "Ja" har valts.
					flerKortForBankir = (val==JOptionPane.NO_OPTION);					// G� vidare till bankiren om "Nej" har valts.
				}

			}	// Slut p� while(flerKortForSpelare)
					
//-----------------------------------------------------------------------------------------------------------
// L�t bankiren dra kort.
//-----------------------------------------------------------------------------------------------------------
			if(flerKortForBankir) {
				System.out.println("Bankir");
			}
			while(flerKortForBankir) {
				i++;										// R�kna upp index f�r kort.
				if(i > 51) {								// Avbryt spelet om kortleken �r slut.
					System.out.println("Kortleken �r slut, spelet avbryts. Ha en trevlig dag!");
					System.exit(0);
				}
				
				if(TEST_MODE) {								// Om TEST_MODE �r satt, ange valfritt v�rde p� n�sta kort.
					String s = JOptionPane.showInputDialog("TEST_MODE: Ange kortets val�r 1-13: ");
					kl.bunt[i].valor = Integer.parseInt(s);
				}
				bankir.totalPoang = bankir.beraknaTotalpoang(kl.bunt[i].valor);			// Dra ett kort och ber�kna totalpo�ngen.
				System.out.println(i + "\t" + kl.bunt[i] + "\t" + bankir.totalPoang);
				medd = "Bankens kort blev : " + kl.bunt[i] + "\n" +
					   "Bankens totala po�ng �r : " + bankir.totalPoang + "\n" +
			           "Din totala po�ng �r : " + spelare.totalPoang + "\n" + "\n";
				
				// Om bankiren f�r exakt 21, skriv ut att banken vinner och fr�ga om nytt spel �nskas.
				if(bankir.totalPoang == 21) {					
					flerKortForBankir = false;
					medd += "SORRY!! Banken vinner." + "\n" + "\n" +
							"Spela igen?";					
					val = JOptionPane.showConfirmDialog(null, medd);					
					if(val==JOptionPane.NO_OPTION || val==JOptionPane.CANCEL_OPTION) {	// Avsluta spelet om "Nej" eller "Avbryt" har valts.
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
					nyttSpel = (val==JOptionPane.YES_OPTION);							// Starta nytt spel om "Ja" har valts.
				}
				
				// Om bankiren f�r mer �n 21, skriv ut att spelaren vinner och fr�ga om nytt spel �nskas.
				if(bankir.totalPoang > 21) {
					flerKortForBankir = false;
					medd += "GRATTIS!! Banken �r tjock, du vinner. \n \n" +
							"Spela igen?";
					val = JOptionPane.showConfirmDialog(null, medd);					
					if(val==JOptionPane.NO_OPTION || val==JOptionPane.CANCEL_OPTION) {
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
				}
				
				// Om bankiren f�r mindre �n BANKIREN_STANNAR_PA, ska nytt kort dagas.
				if(bankir.totalPoang < BANKIREN_STANNAR_PA) {
					flerKortForBankir = true;
					medd += "Banken vill ha fler kort, tryck OK f�r att forts�tta";
					JOptionPane.showMessageDialog(null, medd);
				}

				// Om bankiren f�r BANKIREN_STANNAR_PA eller mer, stanna och j�mf�r po�ng f�r att utse vinnare.
				// Fr�ga sen om nytt spel �nskas
				if(bankir.totalPoang >= BANKIREN_STANNAR_PA && bankir.totalPoang < 21) {
					flerKortForBankir = false;
					medd += "Banken stannar. \n";
					
					if(spelare.totalPoang >= bankir.totalPoang) {
						medd += "GRATTIS, du vinner!!";
					}
					else {
						medd += "SORRY!! Banken vinner.";
					}
					medd += "\n \n" + "Spela igen?";
					val = JOptionPane.showConfirmDialog(null, medd);					
					if(val==JOptionPane.NO_OPTION || val==JOptionPane.CANCEL_OPTION) {	// Avsluta spelet om "Nej" eller "Avbryt" har valts.
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
					nyttSpel = (val==JOptionPane.YES_OPTION);							// Starta nytt spel om "Ja" har valts.
				}

			}	// Slut p� while(flerKortForBankir)
		} // Slut p� while(nyttSpel)
	}	// Slut p� main
}	// Slut p� Tjugoett