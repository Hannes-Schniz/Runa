package com.runa.structure.card.characters.monsters.one;

import com.runa.structure.card.abilities.Ability;
import com.runa.structure.card.abilities.MagicType;
import com.runa.structure.card.abilities.physical.defensive.Block;
import com.runa.structure.card.abilities.physical.offensive.Bite;
import com.runa.structure.card.abilities.magical.Focus;
import com.runa.structure.card.abilities.magical.offensive.LightningMonster;
import com.runa.structure.card.characters.Monster;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The type Spider king.
 *
 * @author Hanne
 * @version 0.1
 */
public class SpiderKing extends Monster {

    private static final Queue<Ability> ABILITYSTACK = new LinkedList<>(List.of(new Bite(1),
            new Block(1), new Focus(1), new LightningMonster(1, 1)));

    private static final boolean ISBOSS = true;

    private static final MagicType PRIMARYTYPE = MagicType.LIGHTNING;

    private static final String NAME = "Spider King";

    private static final int HEALTHPOINTS = 50;


    /**
     * Instantiates a new Spider king.
     */
    public SpiderKing() {
        super(NAME, HEALTHPOINTS, 0, ABILITYSTACK, PRIMARYTYPE, ISBOSS);
    }
}
