package com.runa.util.generator;

import com.runa.structure.card.abilities.Ability;
import com.runa.structure.card.abilities.magical.Focus;
import com.runa.structure.card.abilities.magical.defensive.Reflect;
import com.runa.structure.card.abilities.magical.offensive.Fire;
import com.runa.structure.card.abilities.magical.offensive.Ice;
import com.runa.structure.card.abilities.magical.offensive.Lightning;
import com.runa.structure.card.abilities.magical.offensive.Water;
import com.runa.structure.card.abilities.physical.defensive.Parry;
import com.runa.structure.card.abilities.physical.offensive.Pierce;
import com.runa.structure.card.abilities.physical.offensive.Slash;
import com.runa.structure.card.abilities.physical.offensive.Swing;
import com.runa.structure.card.abilities.physical.offensive.Thrust;
import com.runa.structure.card.characters.Monster;
import com.runa.structure.card.characters.monsters.one.Frog;
import com.runa.structure.card.characters.monsters.one.Ghost;
import com.runa.structure.card.characters.monsters.one.Gorgon;
import com.runa.structure.card.characters.monsters.one.Skeleton;
import com.runa.structure.card.characters.monsters.one.Spider;
import com.runa.structure.card.characters.monsters.one.Goblin;
import com.runa.structure.card.characters.monsters.one.Rat;
import com.runa.structure.card.characters.monsters.one.Mushroomlin;
import com.runa.structure.card.characters.monsters.two.Snake;
import com.runa.structure.card.characters.monsters.two.DarkElf;
import com.runa.structure.card.characters.monsters.two.ShadowBlade;
import com.runa.structure.card.characters.monsters.two.Hornet;
import com.runa.structure.card.characters.monsters.two.Tarantula;
import com.runa.structure.card.characters.monsters.two.Bear;
import com.runa.structure.card.characters.monsters.two.Mushroomlon;
import com.runa.structure.card.characters.monsters.two.WildBoar;

import java.util.ArrayList;
import java.util.List;

/**
 * The type List generator.
 *
 * @author Hanne
 * @version 0.1
 */
public final class ListGenerator {

    private ListGenerator() {

    }

    /**
     * Generate floor list.
     *
     * @param level the level
     * @return the list
     */
    public static List<Monster> generateFloor(int level) {
        ArrayList<Monster> monsterList = new ArrayList<>();
        if (level == 1) {
            monsterList = new ArrayList<>(List.of(new Frog(), new Ghost(), new Gorgon(), new Skeleton(),
                    new Spider(), new Goblin(), new Rat(), new Mushroomlin()));
        }
        if (level == 2) {
            monsterList = new ArrayList<>(List.of(new Snake(), new DarkElf(), new ShadowBlade(), new Hornet(),
                    new Tarantula(), new Bear(), new Mushroomlon(), new WildBoar()));
        }
        return monsterList;
    }

    /**
     * Generate abilities list.
     *
     * @param currentFloor the current floor
     * @return the list
     */
    public static List<Ability> generateAbilities(int currentFloor) {
        return new ArrayList<>(List.of(new Slash(currentFloor), new Swing(currentFloor),
                new Thrust(currentFloor), new Pierce(currentFloor), new Parry(currentFloor), new Focus(currentFloor),
                new Reflect(currentFloor), new Water(currentFloor), new Ice(currentFloor), new Fire(currentFloor),
                new Lightning(currentFloor)));
    }
}
