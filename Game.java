import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
// DO NOT ADD ANY ADDITIONAL IMPORTS ------

public class Game {
	// TODO: Attributes
	private File file; // whille be for the universe
	private int numCharacters;
	private Character[] characters;
	private Character playerOne;
	private Character playerTwo;

	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();

	// Default Constructor - DO NOT MODIFY
	public Game() {};

	// TODO: Implement setupGame() - DO NOT MODIFY METHOD HEADER
	public void setupGame() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome! Please choose a universe to play:"); // Have the user select which universe they want to play in first
		System.out.println("\t1.) Pokemon");
		System.out.println("\t2.) Star Wars");
		System.out.println("\t3.) Marvel");
		System.out.println("\t4.) DC");

		int choice = getIntInRange(1, 4, "Enter your choice (1-4): ");

		String filename; // set up what each choice represents
		if (choice == 1) {
			filename = "pokemon.txt";
		} else if (choice == 2) {
			filename = "starwars.txt";
		} else if (choice == 3) {
			filename = "marvel.txt";
		} else {
			filename = "dc.txt";
		}

		// These three lines must be included in this method as the last lines. DO NOT DELETE THEM. IF YOU CHOOSE TO CONSOLIDATE THEM INTO A SINGLE METHOD, MODIFY AS NEEDED.
		this.file = new File(filename); // replace TODO with the correct filename based on the user's selection
	    this.countCharactersInFile(); 
	    this.readCharacters();	
	}

	/** TODO: Implement countCharactersInFile() - DO NOT MODIFY METHOD HEADER
  	 ** ALTERNATIVELY, YOU CAN CONSOLIDATE BOTH readCharacters() and countCharactersInFile() methods into a single method. You may also choose
  	 ** to use a LinkedList instead of an array. This modification is optional. If you choose to do this, then you may modify the method header
  	 ** as needed. 
  	**/
	private void countCharactersInFile() {
		int count = 0;

		try {
			Scanner fScan = new Scanner(this.file);

			//Each line will be name, hp, attack, and defense
			while (fScan.hasNext()) {
				fScan.next(); // for the name
				if (!fScan.hasNextInt()) { // for the hp
					break;
				}
				fScan.nextInt();
				if (!fScan.hasNextInt()) { // for the attack
					break;
				}
				fScan.nextInt();
				if (!fScan.hasNextInt()) {// for the defense
					break;
				}
				fScan.nextInt();
				
				count++;
			}

			fScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not read the file" + this.file.getName());
			count = 0;
		}

		this.numCharacters = count;
	}

	/** TODO: Implement readCharacters() - DO NOT MODIFY METHOD HEADER
  	 ** ALTERNATIVELY, YOU CAN CONSOLIDATE BOTH readCharacters() and countCharactersInFile() methods into a single method. You may also choose
  	 ** to use a LinkedList instead of an array. This modification is optional. If you choose to do this, then you may modify the method header
  	 ** as needed. 
  	**/
	private void readCharacters() {
		if (this.numCharacters <= 0) {
			this.characters = new Character[0];
			return;
		}

		Character[] list = new Character[this.numCharacters];

		try{
			Scanner fScan = new Scanner(this.file);
			int i = 0;

			while (fScan.hasNext() && i < this.numCharacters) {
				String name = fScan.next();
				int hp = 0;
				int atk = 0;
				int def = 0;

				if (fScan.hasNextInt()) {
					hp = fScan.nextInt();
				}
				if (fScan.hasNextInt()) {
					atk = fScan.nextInt();
				}
				if (fScan.hasNextInt()) {
					def = fScan.nextInt();
				}

				// character contructor should set currHp = maxHp
				list[i] = new Character(name, hp, atk, def);
				i++;
			}

			fScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not read file " + this.file.getName());
		}

		this.characters = list;
	}
	
	// TODO: Implement playOptionOne() - DO NOT MODIFY METHOD HEADER
	public void playOptionOne() {
		if (this.numCharacters < 2) {
			System.out.println("Need at least 2 characters in the file to play.");
			return;
		}

		System.out.println("\nStarting One Player Game...");

		// level selection between easy and medium
		System.out.println("\nPlease select your difficulty level: ");
		System.out.println("\t1.) Easy");
		System.out.println("\t2.) Medium");
		int difficulty = getIntInRange(1, 2, "\nEnter difficulty (1 or 2): ");

		//User choose their character
		System.out.println("\nChoose your character: ");
		int playerIndex = selectCharacterIndex();
		this.playerOne = this.characters[playerIndex];

		// Computer chooses a random opponent
		int opponentIndex = random.nextInt(this.numCharacters);
		while (opponentIndex == playerIndex) {
			opponentIndex = random.nextInt(this.numCharacters);
		}
		this.playerTwo = this.characters[opponentIndex];

		// Reset HPs before the battle
		playerOne.setCurrHp(playerOne.getMaxHp());
		playerTwo.setCurrHp(playerTwo.getMaxHp());

		//Display the characters that are chosen
		System.out.println("\nYou chose: " + playerOne.getName() + " (Hp: " + playerOne.getMaxHp() + ", Attack: " + playerOne.getAttack() + ", Defense: " + playerOne.getDefense() + ")");
		System.out.println("\nOpponent: " + playerTwo.getName() + " (Hp: " + playerTwo.getMaxHp() + ", Attack: " + playerTwo.getAttack() + ", Defense: " + playerTwo.getDefense() + ")");
		System.out.println("---Battle Start!---");

		boolean gameOver = false;
		while (!gameOver) {
			// players turn
			System.out.println("\nYour turn:");
			System.out.println("\t1.) Attack");
			System.out.println("\t2.) Heal");
			System.out.println("\t3.) Forfeit");
			int choice = getIntInRange(1, 3, "Enter your choice (1-3): ");

			if (choice == 1) {
				playerOne.attack(playerTwo);
				if (playerTwo.getCurrHp() <= 0) {
					System.out.println(playerTwo.getName() + " ran out of HP. You win!");
					System.out.println("\nReturning to main menu...");
					gameOver = true;
					break;
				}
			} else if (choice == 2) {
				playerOne.heal(this.numCharacters);
			} else {
				System.out.println("You forfeited the game!");
				System.out.println("\nReturning to main menu...");
				gameOver = true;
				break;
			}

			// computers turn
			System.out.println("\n" + playerTwo.getName() + "'s turn");
			int cpuMove;

			if (difficulty == 1) { // this is if user chose easy
				int randMove = random.nextInt(2);
				if (randMove == 0) {
					cpuMove = 1; // for attack
				} else {
					cpuMove = 2; // for heal
				}
			} else {
				cpuMove = playerTwo.calculateBestMove(playerOne);
			}

			if (cpuMove == 1) {
				playerTwo.attack(playerOne);
				if (playerOne.getCurrHp() <= 0) {
					System.out.println(playerOne.getName() + " ran out of Hp. " + playerTwo.getName() + " win!");
					System.out.println("\nReturning to main menu...");
					gameOver = true;
				}
			} else {
				playerTwo.heal(this.numCharacters);
			}
		}
	}

	// TODO: Implement playOptionTwo() - DO NOT MODIFY METHOD HEADER
	public void playOptionTwo() {
		if (this.numCharacters < 2) {
			System.out.println("Need at least 2 characters in the file to play.");	
			return;
		}

		System.out.println("Enter player 1 name: "); // get name of the players
		String p1Name = scanner.nextLine();
		System.out.println("Enter player 2 name: ");
		String p2Name = scanner.nextLine();

		System.out.println("\n" + p1Name + ", select your character: "); // allow each player to select their character
		int p1Index = selectCharacterIndex();

		System.out.println("\n" + p2Name + ", select your character (must be different): ");
		int p2Index = -1;
		boolean ok = false;
		while (!ok) {
			p2Index = selectCharacterIndex();
			if (p2Index ==  p1Index) {
				System.out.println("That character was already chosen, please choose a different character");
			} else {
				ok = true;
			}
		}

		this.playerOne = this.characters[p1Index];
		this.playerTwo = this.characters[p2Index];

		// reset Hps
		playerOne.setCurrHp(playerOne.getMaxHp());
		playerTwo.setCurrHp(playerTwo.getMaxHp());

		System.out.println("\n" + p1Name + " chose: " + playerOne.getName() + " (Hp: " + playerOne.getMaxHp() + ", Attack: " + playerOne.getAttack() + ", Defense: " + playerOne.getDefense() + ")");
		System.out.println("\n" + p2Name + " chose: " + playerTwo.getName() + " (Hp: " + playerTwo.getMaxHp() + ", Attack: " + playerTwo.getAttack() + ", Defense: " + playerTwo.getDefense() + ")");
		System.out.println("--- Battle Start! ---");

		boolean gameOver = false;

		while (!gameOver) {
			//player 1's turn
			System.out.println("\n" + p1Name + "'s turn (" + playerOne.getName() + "):");
            System.out.println("\t1) Attack");
            System.out.println("\t2) Heal");
            System.out.println("\t3) Forfeit");
            int choice1 = getIntInRange(1, 3, "Enter your choice (1-3): ");

            if (choice1 == 1) {
                playerOne.attack(playerTwo);
                if (playerTwo.getCurrHp() <= 0) {
                    System.out.println(playerTwo.getName() + " ran out of HP. " + p1Name + " wins!");
                    System.out.println("Returning to main menu...");
                    break;
                }
            } else if (choice1 == 2) {
                playerOne.heal(this.numCharacters);
            } else {
                System.out.println(p1Name + " forfeits. " + p2Name + " wins!");
                break;
            }

            // Player 2 turn
            System.out.println("\n" + p2Name + "'s turn (" + playerTwo.getName() + "):");
            System.out.println("\t1) Attack");
            System.out.println("\t2) Heal");
            System.out.println("\t3) Forfeit");
            int choice2 = getIntInRange(1, 3, "Enter your choice (1-3): ");

            if (choice2 == 1) {
                playerTwo.attack(playerOne);
                if (playerOne.getCurrHp() <= 0) {
                    System.out.println(playerOne.getName() + " ran out of HP. " + p2Name + " wins!");
                    System.out.println("Returning to main menu...");
                    break;
                }
            } else if (choice2 == 2) {
                playerTwo.heal(this.numCharacters);
            } else {
                System.out.println(p2Name + " forfeits. " + p1Name + " wins!");
                break;
            }
        }
    }

	// TODO: Implement the new game mode logic
	// Implement this method as described in the instructions.
	// COME UP WITH YOUR OWN METHOD NAME, RETURN, AND PARAMETERS
    public void playTournamentMode() { // this method and new game mode is so that the user can play a tournament and see who wins the best out of 3 battles
        if (this.numCharacters < 2) {
            System.out.println("Need at least 2 characters to run a tournament.");
            return;
        }

        System.out.println("\n---Tournament Mode: Best of Three---");
        System.out.println("First, choose the medium-level computer character (Player One).");
        int mediumIndex = selectCharacterIndex();
        System.out.println("Now choose a different character as the easy-level computer (Player Two).");
        int easyIndex = -1;
        boolean ok = false;
        while (!ok) {
            easyIndex = selectCharacterIndex();
            if (easyIndex == mediumIndex) {
                System.out.println("That character is already chosen. Pick a different one.");
            } else {
                ok = true;
            }
        }

        Character baseMedium = this.characters[mediumIndex];
        Character baseEasy   = this.characters[easyIndex];

        int mediumWins = 0;
        int easyWins = 0;
        int ties = 0;

        System.out.println("\nStarting Best-of-Three Tournament between:");
        System.out.println("Medium-level computer: " + baseMedium.getName());
        System.out.println("Easy-level computer  : " + baseEasy.getName());

        int battle = 1;
        while (battle <= 3 && mediumWins < 2 && easyWins < 2) {
            System.out.println("\n=== Battle " + battle + " ===");

            // fresh copies so each battle starts from full stats
            Character medium = new Character(baseMedium.getName(), baseMedium.getMaxHp(), baseMedium.getAttack(), baseMedium.getDefense());
            Character easy   = new Character(baseEasy.getName(), baseEasy.getMaxHp(), baseEasy.getAttack(), baseEasy.getDefense());

            medium.setCurrHp(medium.getMaxHp());
            easy.setCurrHp(easy.getMaxHp());

            int result = playRecursively(medium, easy, 50, true, 1);

            if (result == 1) {
                mediumWins++;
                System.out.println("Battle " + battle + " winner: Medium-level (" + medium.getName() + ")");
            } else if (result == 2) {
                easyWins++;
                System.out.println("Battle " + battle + " winner: Easy-level (" + easy.getName() + ")");
            } else {
                ties++;
                System.out.println("Battle " + battle + " ended in a tie.");
            }

            battle++;
        }

        System.out.println("\nTournament Results:");
        System.out.println("\tMedium-level computer wins: " + mediumWins);
        System.out.println("\tEasy-level computer wins  : " + easyWins);
        System.out.println("\tTies                      : " + ties);

        if (mediumWins > easyWins) {
            System.out.println("Overall Champion: Medium-level computer (" + baseMedium.getName() + ")");
        } else if (easyWins > mediumWins) {
            System.out.println("Overall Champion: Easy-level computer (" + baseEasy.getName() + ")");
        } else {
            System.out.println("Overall result: Tournament tie!");
        }
    }

	// TODO: Implement playRecursively() - DO NOT MODIFY METHOD NAME, MUST REMAIN PRIVATE, YOU MAY MODIFY RETURN & PARAMETERS AS NEEDED
	private int playRecursively(Character medium, Character easy, int roundsLeft, boolean mediumTurn, int roundNumber) {
        // Base cases
        if (medium.getCurrHp() <= 0) {
            return 2; // easy wins
        }
        if (easy.getCurrHp() <= 0) {
            return 1; // medium wins
        }
        if (roundsLeft <= 0) {
            return -1; // tie
        }

        // Print round header per round
        if (mediumTurn) {
            System.out.println("\n---Round" + roundNumber + "---");
        }

        if (mediumTurn) {
            // Medium-level computer decides via calculateBestMove
            int move = medium.calculateBestMove(easy);

            if (move == 1) {
                System.out.println("Medium-level computer (" + medium.getName() + ") chooses to ATTACK.");
                medium.attack(easy);
            } else {
                System.out.println("Medium-level computer (" + medium.getName() + ") chooses to HEAL.");
                medium.heal(this.numCharacters);
            }

            // After medium's move, recurse to easy's turn, same roundsLeft, same roundNumber
            return playRecursively(medium, easy, roundsLeft, false, roundNumber);
        } else {
            // Easy-level computer: random move
            int randMove = random.nextInt(2); // 0 or 1
            int move;
            if (randMove == 0) {
                move = 1; // attack
            } else {
                move = 2; // heal
            }

            if (move == 1) {
                System.out.println("Easy-level computer (" + easy.getName() + ") chooses to ATTACK.");
                easy.attack(medium);
            } else {
                System.out.println("Easy-level computer (" + easy.getName() + ") chooses to HEAL.");
                easy.heal(this.numCharacters);
            }

            // After easy's move, a full round has completed: decrement roundsLeft, next roundNumber
            return playRecursively(medium, easy, roundsLeft - 1, true, roundNumber + 1);
        }
    }

    // A convenience public wrapper that can be called from CL3 main for a single recursive battle
    public void playRecursiveBattleOption() {
        if (this.numCharacters < 2) {
            System.out.println("Need at least 2 characters to run recursive battle.");
            return;
        }

        System.out.println("\nPick two characters for a recursive computer vs computer battle.");
        System.out.println("First selected character will be the medium-level computer (Player One).");
        int mediumIndex = selectCharacterIndex();

        System.out.println("Now pick a different character as the easy-level computer (Player Two).");
        int easyIndex = -1;
        boolean ok = false;
        while (!ok) {
            easyIndex = selectCharacterIndex();
            if (easyIndex == mediumIndex) {
                System.out.println("That character is already chosen. Pick a different one.");
            } else {
                ok = true;
            }
        }

        Character medium = new Character(characters[mediumIndex].getName(), characters[mediumIndex].getMaxHp(), characters[mediumIndex].getAttack(), characters[mediumIndex].getDefense());
        Character easy = new Character(characters[easyIndex].getName(), characters[easyIndex].getMaxHp(), characters[easyIndex].getAttack(), characters[easyIndex].getDefense());

        medium.setCurrHp(medium.getMaxHp());
        easy.setCurrHp(easy.getMaxHp());

        int result = playRecursively(medium, easy, 100, true, 1);

        if (result == -1) {
            System.out.println("Result: Tie (max rounds reached).");
        } else if (result == 1) {
            System.out.println("Result: Medium-level computer (" + medium.getName() + ") wins.");
        } else if (result == 2) {
            System.out.println("Result: Easy-level computer (" + easy.getName() + ") wins.");
        }
    }

	// TODO: Implement selectCharacter() - DO NOT MODIFY METHOD NAME, MUST REMAIN PRIVATE, YOU MAY MODIFY RETURN & PARAMETERS AS NEEDED
	private Character selectCharacter() {
        int index = selectCharacterIndex();
        if (index >= 0 && index < this.numCharacters) {
            return this.characters[index];
        }
        return null;
    }

    // selection but returns index
    private int selectCharacterIndex() {
        System.out.println("\nAvailable Characters:");
        for (int i = 0; i < this.numCharacters; i++) {
            Character c = this.characters[i];
            System.out.println((i + 1) + ") " + c.getName() + " - HP: " + c.getMaxHp() + ", Attack: " + c.getAttack() + ", Defense: " + c.getDefense());
        }

        int choice = getIntInRange(1, this.numCharacters, "Choose your character (1-" + this.numCharacters + "): ");
        return choice - 1; // convert to 0-based index
    }

    // Create a method to make sure the user is inputing a valid number when asked to choose an option
    public int getIntInRange(int min, int max, String enteredValue) {
        int value = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(enteredValue);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                String badWordEntry = scanner.next(); // Also make sure that if a number is supposed to be entered a string gets error message
                System.out.println("Invalid input. \"" + badWordEntry + "\" is not a number. Try again.");
                scanner.nextLine();
            }
        }
        return value;
    }
}