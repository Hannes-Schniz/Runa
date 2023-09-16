package com.runa.structure.card.characters.monsters.one;

import com.runa.structure.card.abilities.Ability;
import com.runa.structure.card.abilities.MagicType;
import com.runa.structure.card.abilities.magical.Focus;
import com.runa.structure.card.abilities.magical.offensive.LightningMonster;
import com.runa.structure.card.characters.Monster;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The type Skeleton.
 *
 * @author Hanne
 * @version 0.1
 */
public class Skeleton extends Monster {

    private static final Queue<Ability> ABILITYSTACK = new LinkedList<>(List.of(new Focus(1),
            new LightningMonster(1, 1)));

    private static final boolean ISBOSS = false;

    private static final MagicType PRIMARYTYPE = MagicType.LIGHTNING;

    private static final String NAME = "Skeleton";

    private static final int HEALTHPOINTS = 14;


    /**
     * Instantiates a new Skeleton.
     */
    public Skeleton() {
        super(NAME, HEALTHPOINTS, 0, ABILITYSTACK, PRIMARYTYPE, ISBOSS);
    }
}
