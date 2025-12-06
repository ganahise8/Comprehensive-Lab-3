import java.util.Random;
// DO NOT ADD ANY ADDITIONAL IMPORTS ------
// NOTE: NO USER INPUT LOGIC SHOULD BE HANDLED IN THIS CLASS AT ALL - DO NOT IMPORT SCANNER.

public class Character {
	// Attributes
	private String name;
	private int maxHp;
	private int currHp;
	private int attack;
	private int defense;

	// Default Constructor - DO NOT MODIFY
	public Character() {};

	// Constructor that should take in name, maxHp, attack, and defense, and set all attribute value
	// DO NOT MODIFY PARAMETERS
	public Character(String name, int maxHp, int attack, int defense) {
		this.name = name;
		this.maxHp = maxHp;
		this.currHp = maxHp; // We will start their current HP at the max
		this.attack = attack;
		this.defense = defense;
	}

	// Implement all of the following getters for all attributes
	// DO NOT DELETE ANY GETTER - MODIFY RETURN AS NEEDED
	public String getName() {
		return this.name;
	}
	public int getMaxHp() {
		return this.maxHp;
	}
	public int getCurrHp() {
		return this.currHp;
	}
	public int getAttack() {
		return this.attack;
	}
	public int getDefense() {
		return this.defense;
	}

	// Implement all of the following setters for all attributes
	// DO NOT DELETE ANY SETTER - MODIFY RETURN & PARAMETERS AS NEEDED
	public void setName(String name) {
		this.name = name;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public void setCurrHp(int currHp) {
		this.currHp = currHp;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}

	// TODO: Implement heal() - DO NOT MODIFY METHOD HEADER.
	public void heal(int numCharacters) {
		Random randomNum = new Random();
		int bound = Math.max(1, numCharacters); // avoid zero bound
		int rand = 1 + randomNum.nextInt(bound); // make sure the random number fits what we need
		int healAmount = 4 + (rand % 3); // the formula for heals

		int possible = this.maxHp - this.currHp; // make sure that the HP will not exceed the maximum HP possible
		if (healAmount > possible) {
			healAmount =  possible;
		}
		if (healAmount < 0) {
			healAmount = 0;
		}

		this.currHp = this.currHp + healAmount; // update hp
		System.out.println(this.name + " chose to heal");
		System.out.println(this.name + " healed by " + healAmount + " HP");
		System.out.println(this.name + " current HP after heal: " + this.currHp + " / " + this.maxHp);
	}

	// TODO: Implement attack() - DO NOT MODIFY METHOD HEADER.
	public void attack(Character opponent) {
		int damage = this.attack - opponent.getDefense(); //subtract the defense from the attack 
		if (damage < 1)
			damage = 1;
		int oppHpBefore = opponent.getCurrHp();
		opponent.setCurrHp(oppHpBefore - damage); // the current HP should update by subtracting the damage from the HP
		System.out.println(this.name + " attacks " + opponent.getName() + " with strength " + this.attack + "!");
		System.out.println(opponent.getName() + " defends by " + opponent.getDefense());
		System.out.println("Net damage dealt: " + damage);
		System.out.println(opponent.getName() + " current HP: " + opponent.getCurrHp() + " / " + opponent.getMaxHp());
		System.out.println(this.name + " current HP: " + this.currHp + " / " + this.maxHp);
	}

	// TODO: Implement calculateBestMove() - DO NOT MODIFY METHOD NAME. YOU MAY MODIFY THE METHOD RETURN & PARAMETERS AS NEEDED
	public int calculateBestMove(Character opponent) {
		int compCurr = this.currHp;
		int compMax = this.maxHp;
		int compAtk = this.attack;
		int compDef = this.defense;

		int playerCurr = opponent.getCurrHp();
		int playerAtk = opponent.getAttack();
		int playerDef = opponent.getDefense();

		// 1) If it is best we attack
        if ((compAtk - playerDef) >= playerCurr) {
            return 1;
        }

        // If it is best we heal
        if (compCurr <= (playerAtk - compDef)) {
            return 2;
        }
        // If compCurr <= compMax - 5 then we attack
        if (compCurr <= compMax - 5) {
            return 1;
        }
        // If compCurr >= playerCurr then we attack
        if (compCurr >= playerCurr) {
            return 1;
        }
        // If the difference within 5 then we use random but biased to attack (0.6)
        if (Math.abs(compCurr - playerCurr) <= 5) {
            Random random = new Random();
            double probability = random.nextDouble(); // 0.0 .. 1.0
            if (probability < 0.6) return 1;
            else return 2;
        }
        // else heal
        return 2;
    }

	// Default toString() method already implemented. You may modify if you'd like, or leave it as is.
	// DO NOT DELETE
	@Override
    public String toString() {
        return this.name + ", HP=" + this.currHp + ", Attack=" + this.attack + ", Defense=" + this.defense;
    }

    // A clone helper to copy a characters stats
    public Character copy() {
        Character c = new Character(this.name, this.maxHp, this.attack, this.defense);
        c.setCurrHp(this.currHp);
        return c;
    }
}