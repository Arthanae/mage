
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Loki
 */
public final class GravebornMuse extends CardImpl {
    private static FilterControlledPermanent filter = new FilterControlledPermanent("Zombie you control");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public GravebornMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, you draw X cards and you lose X life, where X is the number of Zombies you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)), TargetController.YOU, false);
        ability.addEffect(new LoseLifeSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
        this.addAbility(ability);
    }

    public GravebornMuse(final GravebornMuse card) {
        super(card);
    }

    @Override
    public GravebornMuse copy() {
        return new GravebornMuse(this);
    }
}
