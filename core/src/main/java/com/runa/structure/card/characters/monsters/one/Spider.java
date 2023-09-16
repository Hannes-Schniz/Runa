package com.runa.structure.card.characters.monsters.one;

import com.runa.structure.card.abilities.Ability;
import com.runa.structure.card.abilities.MagicType;
import com.runa.structure.card.abilities.physical.defensive.Block;
import com.runa.structure.card.abilities.physical.offensive.Bite;
import com.runa.structure.card.characters.Monster;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The type Spider.
 *
 * @author Hanne
 * @version 0.1
 */
public class Spider extends Monster {

    private static final Queue<Ability> ABILITYSTACK = new LinkedList<>(List.of(new Bite(1),
            new Block( 1)));

    private static final boolean ISBOSS = false;

    private static final MagicType PRIMARYTYPE = MagicType.NONE;

    private static final String NAME = "Spider";

    private static final int HEALTHPOINTS = 15;


    /**
     * Instantiates a new Spider.
     */
    public Spider() {
        super(NAME, HEALTHPOINTS, 0, ABILITYSTACK, PRIMARYTYPE, ISBOSS);
    }
}
