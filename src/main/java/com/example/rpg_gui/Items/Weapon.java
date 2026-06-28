package com.example.rpg_gui.Items;

import com.example.rpg_gui.Characters.Hero;

import static com.example.rpg_gui.Characters.Hero.HeroType.Warrior;
import static com.example.rpg_gui.Characters.Hero.HeroType.Archer;
import static com.example.rpg_gui.Characters.Hero.HeroType.Mage;

public class Weapon extends Item {
    private final int bonusDamage;
    private final Hero.HeroType allowedHero;

    public int getBonusDamage () {
        return bonusDamage;
    }
    public Hero.HeroType getAllowedHero() {
        return allowedHero;
    }

    public Weapon(String name, int price, Hero.HeroType allowedHero, int bonusDamage) {
        super(name, price);
        this.allowedHero = allowedHero;
        this.bonusDamage = bonusDamage;
    }

    @Override
    public void use(Hero hero) {
        if (hero.getHero() == this.allowedHero) {
            hero.setEquippedWeapon(this);
            System.out.println(getItemName() + " equipped! Bonus damage: +" + bonusDamage);
        } else {
            System.out.println("This weapon is not for your class! Required: " + allowedHero);
        }
    }

    @Override
    public Item copy() {
        return new Weapon(getItemName(), getItemPrice(), allowedHero, bonusDamage);
    }
    public static final Weapon woodenSword = new Weapon ("Wooden Sword", 40, Warrior, 2);
    public static final Weapon woodenBow = new Weapon ("Wooden Bow", 40, Archer, 2);
    public static final Weapon woodenStaff = new Weapon ("Wooden Staff", 40, Mage, 2);
    public static final Weapon ironSword = new Weapon ("Iron Sword", 90, Warrior, 10);
    public static final Weapon ironBow = new Weapon ("Iron Bow", 90, Archer, 10);
    public static final Weapon ironStaff = new Weapon ("Iron Staff", 90, Mage, 10);
    public static final Weapon goldSword = new Weapon ("Gold Sword", 150, Warrior, 20);
    public static final Weapon goldBow = new Weapon ("Gold Bow", 150, Archer, 20);
    public static final Weapon goldStaff = new Weapon ("Gold Staff", 150, Mage, 20);
}