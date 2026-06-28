package com.example.rpg_gui.Characters;

import com.example.rpg_gui.Systems.InventorySystem;
import com.example.rpg_gui.Items.*;

import java.util.ArrayList;

public class Hero extends Character {
    public enum HeroType {Warrior, Mage, Archer}
    private final HeroType type;
    protected Armor equippedArmor;
    protected Weapon equippedWeapon;
    protected int mana;
    protected int maxMana = 100;
    protected int money;
    protected int level;
    protected int exp;
    protected int maxExp = 100; //exp to next level
    protected int attackDistance;
    protected int currentAttackIndex = 0;
    protected ArrayList<String> learnedAttacks = new ArrayList<String>();
    protected InventorySystem inventorySystem = new InventorySystem();

    public HeroType getHero () {return type;}
    public int getMoney () {return money;}
    public int getAttackDistance() {return attackDistance;}
    public InventorySystem getInventory() {return this.inventorySystem;}
    public int getLevel() {
        return level;
    }
    public int getMana() {
        return mana;
    }
    public int getMaxHealth() {
        return this.maxHealth;
    }
    public int getMaxMana() {
        return this.maxMana;
    }
    public Armor getEquippedArmor() {return equippedArmor;}
    public Weapon getEquippedWeapon() {return equippedWeapon;}


    public void gainExp (int amount) {
        this.exp += amount;
        System.out.println("Gained " + amount + " exp");
        while (this.exp >= maxExp)  {
            this.exp -= maxExp;
            levelUp();
        }
    }


    public void levelUp() {
        level++;
        maxHealth += 10;
        health = maxHealth;
        maxMana += 10;
        mana = maxMana;

        System.out.println("Level up to " + level);
        System.out.println("Go to the Academy to learn new spells");
    }


    public void learnAttack (String attackName, int allowedLevel) {
        if (this.level < allowedLevel) {
            System.out.println("Your need level " + allowedLevel + " to learn " + attackName);
            return;
        }
        if (learnedAttacks.contains(attackName)) return;
        learnedAttacks.add(attackName);
        System.out.println("You learned new attack: " + attackName);
    }


    public void setEquippedArmor (Armor armor) {
        if (armor == null) return;
        if (this.equippedArmor != null) {
            inventorySystem.addItem(this.equippedArmor);
        }
        this.equippedArmor = armor;
        System.out.println("Armor equipped: " + armor.getItemName());
    }


    public void setEquippedWeapon (Weapon weapon) {
        if(weapon==null) return;
        if(this.equippedWeapon!=null) {
            inventorySystem.addItem(this.equippedWeapon);
        }
        this.equippedWeapon = weapon;
        System.out.println("Weapon equipped: " + weapon.getItemName());
    }


    public Hero (String name, HeroType type) {
        super (name);
        this.type = type;
        this.money = 100;
        this.level = 1;
        this.exp = 0;

        this.health = this.maxHealth;
        this.mana = this.maxMana;
    }


    public void earnMoney(int amount) {
        money += amount;
        System.out.println("You received " + amount + " coins");
        System.out.println(("Your total balance: "+money));
    }

    public void spendMoney (int amount) {
        money -= amount;
        System.out.println("You spent " + amount + " coins");
        System.out.println("Your total money: " + money);
    }


    public void addItem (Item item) {
        inventorySystem.addItem(item);
    }


    public void attack (String attackName, int damage, int manaNeed, Enemy... enemies) {
        if (!this.isAlive() || enemies == null || enemies.length == 0) return;
        if (!learnedAttacks.contains(attackName)) {
            System.out.println("You can not use it! You have to learn this attack first.");
            return;
        }
        if (this.mana < manaNeed) {
            System.out.println("You do not have enough mana!");
            return;
        }
        mana -= manaNeed;
        int finalDamage = damage;
        if (equippedWeapon != null) {
            finalDamage += equippedWeapon.getBonusDamage();
        }
        System.out.println("You use " + attackName);
        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.isAlive()) continue;

            enemy.takeDamage(finalDamage);
            this.checkEnemyDeath(enemy);
        }
    }


    public void checkEnemyDeath (Enemy enemy) {
        if (!enemy.isAlive()) {
            if (enemy.getEnemyType() == Enemy.enemyType.DungeonBoss) {
                this.gainExp(100);
                this.earnMoney(200);
            } else {
                this.gainExp(50);
                this.earnMoney(20);
                this.health = Math.min (health +50, maxHealth);
                this.mana = Math.min (mana +50, maxMana);
            }
        }
    }


    @Override
    public void takeDamage (int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage can not be negative");
        }
        int defense = this.equippedArmor != null ? this.equippedArmor.getDefenceBonus() : 0;
        int finalDamage = damage - defense;
        if (finalDamage < 5) {
            finalDamage = 5;
        }
        this.health -= finalDamage;

        if (this.health <= 0) {
            this.health = 0;
            System.out.println("You died!");
        } else {
            System.out.println("You took " + finalDamage + " damage!");
            System.out.println("Your health: " + this.health);
        }
    }


    public void useSelectedAttack(Enemy... enemies) {
    }

    public void switchAttack() {
        if (learnedAttacks.isEmpty()) {
            System.out.println("No skills learned yet!");
            return;
        }
        currentAttackIndex = (currentAttackIndex + 1) % learnedAttacks.size();
        System.out.println("Selected skill: " + getSelectedAttackName());
    }

    public String getSelectedAttackName() {
        if (learnedAttacks.isEmpty()) return "None";
        return learnedAttacks.get(currentAttackIndex);
    }


    public void restoreHealth (int amount) {
        this.health += amount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
        System.out.println("You restored +" + amount + " health!");
        System.out.println("Your total health " + this.health + "/" + maxHealth);
    }

    public void restoreMana (int amount) {
        this.mana += amount;
        if (this.mana > maxMana) {
            this.mana = maxMana;
        }
        System.out.println("You restored +" + amount + " mana!");
        System.out.println("Your total mana " + this.mana + "/" + maxMana);
    }
}