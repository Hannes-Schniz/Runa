package com.runa;

import com.runa.util.parser.EndGameException;
import com.runa.structure.card.abilities.Ability;
import com.runa.structure.card.abilities.AbilityType;
import com.runa.structure.card.abilities.MagicAbility;
import com.runa.structure.card.abilities.PhysicalAbility;
import com.runa.structure.card.abilities.magical.defensive.Reflect;
import com.runa.structure.card.characters.Monster;
import com.runa.structure.card.characters.Runa;
import com.runa.structure.card.characters.RunaType;
import com.runa.util.states.GameState;
import com.runa.structure.RunasAdventure;
import com.runa.util.states.Statemachine;
import com.runa.util.terminal.Terminal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Main.
 *
 * @author Hannes
 * @version 0.1
 */
public class Main {

    private static final String NUMBER = "number";

    private static final int LEVELTWO = 2;

    private static final int ROOMREWARDCOUNT = 4;

    private static final int FIRSTROOMREWARDCOUNT = 2;

    private static final int REWARDSIZEDIVIDER = 2;

    private static final int MULTISELECTCUTOFF = 2;

    private static final int HEALINGDIVIDER = 10;

    private static final int LIFEGAINMULTI = 10;

    private static final int NUMBEROFSEEDS = 2;

    private static final int MAXNUMBEROFTARGETS = 3;

    private static RunasAdventure game;

    private static final int MONSTERSEED = 0;

    private static final int ABILITYSEED = 1;

    private static final int MAXSEED = 2147483647;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void main(String[] args) throws IllegalArgumentException {
        if (args.length != 0) { // if the args aren't empty it throws a exception
            throw new IllegalArgumentException("args have to be empty");
        }
        Main main = new Main();
        try {
            while (main.no10d()) {
                switch (RunasAdventure.getState()) {
                    case INIT: { // initializes the game
                        main.init();
                        break;
                    }
                    case SHUFFLE: { // shuffles the cards and enters the first room of the dungeon
                        main.shuffle();
                        main.enterRoom();
                        break;
                    }
                    case RUNATURN: { // executes runas turn
                        main.nextStage();
                        main.runaAttack();
                        break;
                    }
                    case MONSTERTURN: { // executes the monsters turn
                        main.monsterAttack();
                        break;
                    }
                    case FIGHTWON: { // executes the reward stage
                        main.reward();
                        break;
                    }
                    case HEALING: { // executes the healing stage
                        main.heal();
                        main.enterRoom();
                        break;
                    }
                    case RUNABOSSFIGHT: { // runs runas turn in a boss fight
                        main.nextStage();
                        main.runaAttack();
                        break;
                    }
                    case MONSTERBOSSFIGHT: { // runs the boss monsters turn
                        main.monsterAttack();
                        break;
                    }
                    case BOSSWIN: { // executes the rewards after the boss win
                        main.printUpgrade();
                        main.heal();
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        } catch (EndGameException ignored) { // ends the program if "quit" was the input and the exception got thrown
            return;
        }
        if (Statemachine.getCurrentState().equals(GameState.WIN)) { // prints the win message if the game is won
            Terminal.printWin();
        }
    }

    // -----------------------------------private primary
    // functions------------------------------------------------------

    private void init() throws EndGameException {
        Terminal.printHello(); // prints the hello message
        switch (Terminal.selectTarget(NUMBER, MAXNUMBEROFTARGETS, true)) { // selects the class according to the
            case 0: { // input of the user
                game = new RunasAdventure(RunaType.WARRIOR);
                return;
            }
            case 1: {
                game = new RunasAdventure(RunaType.MAGE);
                return;
            }
            case 2: {
                game = new RunasAdventure(RunaType.PALADIN);
                return;
            }
            default: {
            }
        }
    }

    private void shuffle() throws EndGameException { // shuffles the cards with the seeds given from the player
        Terminal.print("To shuffle ability cards and monsters, enter " + NUMBEROFSEEDS + " seeds");
        List<Integer> selected = Terminal.selectMultiTarget(MAXSEED, NUMBEROFSEEDS, true, "seeds");
        game.shuffleCards(selected.get(MONSTERSEED), selected.get(ABILITYSEED));
    }

    private void enterRoom() { // enters the next room and prints out the stage
        game.enterRoom();
        Terminal.printStage(game.getCurrentRoom(), game.getCurrentFloor());
    }

    private void nextStage() { // checks the focus of runa and prints out the level again
        Terminal.printFocus(game.getRuna().getName(), game.checkChangeFocus(game.getRuna()));
        Terminal.printLevel(game.getRuna(), game.getCurrentFight());
    }

    private void runaAttack() throws EndGameException {
        Terminal.print("Select card to play");
        Terminal.printAbilities(game.getRuna()); // makes the player select a ability card to play
        Ability use = game.getRuna().getAbilities().get(
                Terminal.selectTarget(NUMBER, game.getRuna().getAbilities().size(), true));
        int target = 0; // inits the target of the attack
        if (game.getCurrentFight().size() > 1 && use.getType().equals(AbilityType.OFFENSIVE)) {
            Terminal.print("Select Runa's target."); // if the fight is against more than 1 opp1nt it makes the
            Terminal.printTargets(game.getCurrentFight()); // player choose a target to attack
            target = Terminal.selectTarget(NUMBER, game.getCurrentFight().size(), true);
        }
        Terminal.printUse(game.getRuna(), use); // prints the use Ability message
        switch (use.getUsageType()) { // switches between the type of the attack (Physical/Magic)
            case PHYSICAL: {
                int dice = 0;
                if (use.getType().equals(AbilityType.OFFENSIVE)) { // if needed gets a dice roll of the player
                    dice = Terminal.selectTarget("dice roll", game.getRuna().getDice().getValue(), true)
                            + 1;
                }
                Terminal.printDamage(game.getCurrentFight().get(target), game.usePhysicalAbility(game.getRuna(),
                        game.getCurrentFight().get(target),
                        (PhysicalAbility) use, dice), use); // calculates and prints the damage of the attack
                break;
            }
            case MAGIC: { // calculates and prints the damage of the magic attack
                Terminal.printDamage(game.getCurrentFight().get(target), game.useMagicalAbility(game.getRuna(),
                        game.getCurrentFight().get(target), (MagicAbility) use).get(0), use);
                break;
            }
            default: {
                break;
            }
        }
        Terminal.printDeath(game.checkDead()); // prints a death message if 1 occurred
    }

    private void monsterAttack() throws EndGameException {
        for (Monster monster : game.getCurrentFight()) { // calculates and prints the focus points for the monsters
            Terminal.printFocus(monster.getName(), game.checkChangeFocus(monster));
        }
        List<Monster> iterate = new ArrayList<>(game.getCurrentFight());
        for (Monster monster : iterate) { // iterates over the monster list
            Terminal.printUse(monster, monster.getNextMove());
            switch (monster.getNextMove().getUsageType()) { // switches between the type of the attack
                                                            // (Physical/Magical)
                case PHYSICAL: { // calculates and prints the physical damage of runa
                    int dmg = game.usePhysicalAbility(monster, game.getRuna(),
                            (PhysicalAbility) monster.getNextMove(), 0);
                    Terminal.printDamage(game.getRuna(), dmg, monster.getNextMove());
                    break;
                }
                case MAGIC: { // calculates and prints the magical damage
                    List<Integer> dmg = game.useMagicalAbility(monster, game.getRuna(),
                            (MagicAbility) monster.getNextMove()); // gets the damage list of the magical damage
                    Terminal.printDamage(game.getRuna(), dmg.get(0), monster.getNextMove()); // prints the damage
                    if (dmg.size() > 1) { // if the damage list has a second entry the attacker took reflect damage
                        Terminal.printDamage(monster, dmg.get(1), new Reflect(1));
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            Terminal.printDeath(game.checkDead()); // checks and prints the dead monsters
            if (Statemachine.getCurrentState().equals(GameState.LOST)) { // if the state is lost the function returns
                return;
            }
            monster.rmTop(); // cycles the monsters attacks
        }
        game.monsterTurnOver(); // ends the monster turn
    }

    private void printUpgrade() {
        game.fightReward(0, null); // executes the fight reward 0 in the game
        for (Ability classAb : game.getRuna().getClassAbilities(LEVELTWO)) { // prints the upgraded class abilities
            Terminal.print("Runa gets " + Terminal.printAbility(classAb));
        }
    }

    private void reward() throws EndGameException {
        Terminal.print("Choose Runa's reward\n1) new ability cards\n2) next player dice");
        int selected = Terminal.selectTarget(NUMBER, 2, true) + 1; // forces an input
        if (selected == 1) {
            List<Ability> drawnCards = new ArrayList<>();
            if (game.getCurrentRoom() == 1) { // if the room is 1 adds 2 cards
                for (int i = 0; i < FIRSTROOMREWARDCOUNT; i++) {
                    drawnCards.add(game.getTopAbility());
                }
            } else if (game.getCurrentRoom() > 1) { // if the room is not the first room
                for (int i = 0; i < ROOMREWARDCOUNT; i++) {
                    Ability toAdd = game.getTopAbility();
                    if (toAdd != null) { // if there are not enough cards to choose from it doesn't add null
                        drawnCards.add(toAdd);
                    }
                }
            }
            List<Ability> chosen = selectReward(drawnCards); // makes the player choose the reward cards
            game.fightReward(selected, chosen); // puts the reward to the game
            for (Ability rewardPrint : chosen) { // prints the chosen rewards
                Terminal.print("Runa gets " + Terminal.printAbility(rewardPrint));
            }
        } else if (selected == 2) { // upgrade dice path
            game.fightReward(selected, null);
            Terminal.print("Runa upgrades her die to a d" + game.getRuna().getDice().getValue());
        }
    }

    private List<Ability> selectReward(List<Ability> rewards) throws EndGameException {
        List<Ability> selected = new ArrayList<>();
        int sizeRewards = rewards.size();
        if (sizeRewards % REWARDSIZEDIVIDER != 0) { // makes sure that the player can always choose 2 cards
            sizeRewards++;
        }
        sizeRewards = sizeRewards / REWARDSIZEDIVIDER;
        Terminal.print("Pick " + sizeRewards + " card(s) as loot");
        for (int i = 0; i < rewards.size(); i++) {
            Terminal.print((i + 1) + ") " + Terminal.printAbility(rewards.get(i)));
        }
        List<Integer> picked = new ArrayList<>();
        if (sizeRewards > 1) { // if the player has to pick multiple cards calls select multi
            picked = Terminal.selectMultiTarget(rewards.size(), sizeRewards, true, "numbers");
        } else { // if only 1 needs to be picked calls select single
            picked.add(Terminal.selectTarget(NUMBER, rewards.size(), true));
        }
        for (Integer pick : picked) { // adds all the picked cards to the selected list
            selected.add(rewards.get(pick));
        }
        return selected;
    }

    private void heal() throws EndGameException {
        double damage = Runa.getMaxhealth() - game.getRuna().getHealthPoints();
        int amount = (int) Math.ceil(damage / HEALINGDIVIDER); // rounds up the amount of damage runa took divided by 10
        if (amount == game.getRuna().getAbilities().size()) { // makes sure the player doesn't discard the last ability
            amount--;
        }
        if (amount > 0 && game.getRuna().getAbilities().size() > 1) { // checks that runa has more than 1 ability
            Terminal.print(Terminal.printRuna(game.getRuna(), false)
                    + " can discard ability cards for healing (or n1)");
            Terminal.printAbilities(game.getRuna()); // prints all of runas abilities
            List<Integer> selected = new ArrayList<>();
            if (game.getRuna().getAbilities().size() > MULTISELECTCUTOFF) { // if the player can choose more than 1 card
                selected = Terminal.selectMultiTarget(
                        game.getRuna().getAbilities().size(), amount, false, "numbers");
            } else { // if the player can only discard 1 card calls select single target
                int picked = Terminal.selectTarget(NUMBER,
                        game.getRuna().getAbilities().size(), false);
                if (picked != -1) { // if the player selected a card it adds to the selected list
                    selected.add(picked);
                }
            }
            List<Ability> runasAbilities = new ArrayList<>();
            for (int i = 0; i <= game.getRuna().getAbilities().size(); i++) { // finds all cards in the ability list
                if (selected.contains(i)) {
                    runasAbilities.add(game.getRuna().getAbilities().get(i));
                }
            }
            if (runasAbilities.size() > 0) {
                game.heal(runasAbilities); // heals runa
                int insertion = (int) damage;
                if (damage >= runasAbilities.size() * 10) {
                    insertion = runasAbilities.size() * LIFEGAINMULTI;
                }
                Terminal.print("Runa gains " + insertion + " health");
            }
        }
    }

    private boolean no10d() {
        return !Statemachine.getCurrentState().equals(GameState.LOST)
                && !Statemachine.getCurrentState().equals(GameState.WIN);
    }
}
