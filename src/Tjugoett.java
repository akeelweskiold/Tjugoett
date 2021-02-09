//-----------------------------------------------------------------------------------------------------------
//

// 			Tjugoett - ett kortspel
//          -----------------------
// 
// Programmet kommunicerar med användaren både med dialogboxar och utskrifter i kommandofönstret.
// I dialogboxarna fokuseras informationen på vad användaren förväntas göra. 
// I kommandofönstret syns tidigare drag, så att man kan se historiken. Även använd för avlusning.
//
// En spelomgång varar så länge användaren vill, men avslutas senast när kortleken tar slut. 
//
// Programmet har två konstanter som kan manipuleras:
// BANKIREN_STANNAR_PA är den poängnivå som bankiren alltid stannar på. Jag har satt den till 15, 
// för att det ska blir lagom svårt att vinna över bankiren.
// TEST_MODE. Om man sätter den till true så slumpas inte korten fram, utan användaren väljer valör
// för varje kort. Bra för den som hatar att förlora, men också för den som ska prova ut programmet ;-)
//
// Programmet består av två filer; Tjugoett.java och TjugoettSub.java . 
// I inlämninguppgiften är dom hopslagna till filen Programmering2_UppgiftA_Åke.txt
//
// 2017-03-15 Åke Elweskiöld
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
		
		final int BANKIREN_STANNAR_PA = 15;			// Värdet där bankiren stannar.
		final boolean TEST_MODE = true;			// Testflagga. Om true, kan operatör ange önskat värde på kort. 

		Kortlek kl = new Kortlek();

		TjugoettSub.SkapaBlandadLek(kl);			// Blanda kortleken.
		

//-----------------------------------------------------------------------------------------------------------
// Välkomstmeddelande.
//-----------------------------------------------------------------------------------------------------------
		medd = "        xHejsan! \n" +
		       "Vill du spela Tjugoett?";
		int val = JOptionPane.showConfirmDialog(null, medd);
		nyttSpel = (val==JOptionPane.YES_OPTION);
		if(!nyttSpel) {
			System.out.println("Spelet avbryts. Ha en trevlig dag!");
			System.exit(0);
		}
		
		int i = -1;									// Initiera index för korten. (-1 eftersom den ökas i början av yttre loopen, o första indexvärdet är noll.)
		
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
// Låt spelaren dra kort.
//-----------------------------------------------------------------------------------------------------------
			while(flerKortForSpelare) {
				i++;												// Räkna upp index för kort.
				if(i > 51) {										// Avbryt spelet om kortleken är slut.
					System.out.println("Kortleken är slut, spelet avbryts. Ha en trevlig dag!");
					System.exit(0);
				}
				
				if(TEST_MODE) {										// Om TEST_MODE är satt, ange valfritt värde på nästa kort.
					String s = JOptionPane.showInputDialog("TEST_MODE: Ange kortets valör 1-13: ");
					kl.bunt[i].valor = Integer.parseInt(s);
				}
				spelare.totalPoang = spelare.beraknaTotalpoang(kl.bunt[i].valor);		// Dra ett kort och beräkna totalpoängen.
				System.out.println( i + "\t" + kl.bunt[i] + "\t" + spelare.totalPoang);
				medd = "Ditt kort blev : " + kl.bunt[i] + "\n" +
		           	   "Din totala poäng är : " + spelare.totalPoang + "\n" + "\n";
				
				// Om spelaren får exakt 21, skriv ut grattis och fråga om nytt spel önskas.
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
				
				// Om spelare får > 21, skriv ut att banken vinner och fråga om nytt spel önskas.
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
				
				// Om spelaren får < 21, fråga om nytt kort önskas
				if(spelare.totalPoang < 21) {
					medd += "Vill du ha fler kort?";
					val = JOptionPane.showConfirmDialog(null, medd);
					if(val==JOptionPane.CANCEL_OPTION) {
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
					flerKortForSpelare = (val==JOptionPane.YES_OPTION);					// Dra ännu ett kort för spelare om "Ja" har valts.
					flerKortForBankir = (val==JOptionPane.NO_OPTION);					// Gå vidare till bankiren om "Nej" har valts.
				}

			}	// Slut på while(flerKortForSpelare)
					
//-----------------------------------------------------------------------------------------------------------
// Låt bankiren dra kort.
//-----------------------------------------------------------------------------------------------------------
			if(flerKortForBankir) {
				System.out.println("Bankir");
			}
			while(flerKortForBankir) {
				i++;										// Räkna upp index för kort.
				if(i > 51) {								// Avbryt spelet om kortleken är slut.
					System.out.println("Kortleken är slut, spelet avbryts. Ha en trevlig dag!");
					System.exit(0);
				}
				
				if(TEST_MODE) {								// Om TEST_MODE är satt, ange valfritt värde på nästa kort.
					String s = JOptionPane.showInputDialog("TEST_MODE: Ange kortets valör 1-13: ");
					kl.bunt[i].valor = Integer.parseInt(s);
				}
				bankir.totalPoang = bankir.beraknaTotalpoang(kl.bunt[i].valor);			// Dra ett kort och beräkna totalpoängen.
				System.out.println(i + "\t" + kl.bunt[i] + "\t" + bankir.totalPoang);
				medd = "Bankens kort blev : " + kl.bunt[i] + "\n" +
					   "Bankens totala poäng är : " + bankir.totalPoang + "\n" +
			           "Din totala poäng är : " + spelare.totalPoang + "\n" + "\n";
				
				// Om bankiren får exakt 21, skriv ut att banken vinner och fråga om nytt spel önskas.
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
				
				// Om bankiren får mer än 21, skriv ut att spelaren vinner och fråga om nytt spel önskas.
				if(bankir.totalPoang > 21) {
					flerKortForBankir = false;
					medd += "GRATTIS!! Banken är tjock, du vinner. \n \n" +
							"Spela igen?";
					val = JOptionPane.showConfirmDialog(null, medd);					
					if(val==JOptionPane.NO_OPTION || val==JOptionPane.CANCEL_OPTION) {
						System.out.println("Spelet avbryts. Ha en trevlig dag!");
						System.exit(0);
					}
				}
				
				// Om bankiren får mindre än BANKIREN_STANNAR_PA, ska nytt kort dagas.
				if(bankir.totalPoang < BANKIREN_STANNAR_PA) {
					flerKortForBankir = true;
					medd += "Banken vill ha fler kort, tryck OK för att fortsätta";
					JOptionPane.showMessageDialog(null, medd);
				}

				// Om bankiren får BANKIREN_STANNAR_PA eller mer, stanna och jämför poäng för att utse vinnare.
				// Fråga sen om nytt spel önskas
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

			}	// Slut på while(flerKortForBankir)
		} // Slut på while(nyttSpel)
	}	// Slut på main
}	// Slut på Tjugoett