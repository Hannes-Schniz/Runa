package com.runa.structure.card.abilities.magical.offensive;

import com.runa.structure.card.abilities.AbilityType;
import com.runa.structure.card.abilities.MagicType;
import com.runa.structure.card.abilities.MagicAbility;

/**
 * The type Lightning monster.
 *
 * @author Hannes
 * @version 0.1
 */
public class LightningMonster extends MagicAbility {

    private static final String DESCRIPTION = "14n + 2 magischer Schaden";

    private static final String NAME = "Lightning";

    private static final int TWO = 2;

    private static final int FOURTEEN = 14;

    /**
     * Instantiates a new Lightning monster.
     *
     * @param cost         the cost
     * @param abilityLevel the ability level
     */
    public LightningMonster(int cost, int abilityLevel) {
        super(NAME, AbilityType.OFFENSIVE, DESCRIPTION, false, cost, MagicType.LIGHTNING, abilityLevel);
    }

    @Override
    public int calculate(int focusPoints, MagicType opposingType) {
        return FOURTEEN * this.getAbilityLevel() + TWO;
    }
}
