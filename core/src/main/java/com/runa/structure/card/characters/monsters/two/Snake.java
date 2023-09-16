package com.runa.structure.card.characters.monsters.two;

import com.runa.structure.card.abilities.Ability;
import com.runa.structure.card.abilities.MagicType;
import com.runa.structure.card.abilities.magical.Focus;
import com.runa.structure.card.abilities.magical.offensive.IceMonster;
import com.runa.structure.card.abilities.physical.offensive.Bite;
import com.runa.structure.card.characters.Monster;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The type Frog.
 *
 * @author Hanne
 * @version 0.1
 */
public class Snake extends Monster {

    private static final Queue<Ability> ABILITYSTACK = new LinkedList<>(List.of(new Bite(2),
            new Focus(2), new IceMonster(2, 2)));

    private static final boolean ISBOSS = false;

    private static final MagicType PRIMARYTYPE = MagicType.ICE;

    private static final String NAME = "Snake";

    private static final int HEALTHPOINTS = 31;


    /**
     * Instantiates a new Frog.
     */
    public Snake() {
        super(NAME, HEALTHPOINTS, 0, ABILITYSTACK, PRIMARYTYPE, ISBOSS);
    }
}
