/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class DurkwoodTracker extends CardImpl {

    public DurkwoodTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {1}{G}, {tap}: If Durkwood Tracker is on the battlefield, it deals damage equal to its power to target attacking creature. That creature deals damage equal to its power to Durkwood Tracker.
        Ability ability = new SimpleActivatedAbility(new DurkwoodTrackerEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    public DurkwoodTracker(final DurkwoodTracker card) {
        super(card);
    }

    @Override
    public DurkwoodTracker copy() {
        return new DurkwoodTracker(this);
    }
}

class DurkwoodTrackerEffect extends OneShotEffect {

    DurkwoodTrackerEffect() {
        super(Outcome.Benefit);
        this.staticText = "if {this} is on the battlefield, "
                + "it deals damage equal to its power to target attacking creature. "
                + "That creature deals damage equal to its power to {this}";
    }

    DurkwoodTrackerEffect(final DurkwoodTrackerEffect effect) {
        super(effect);
    }

    @Override
    public DurkwoodTrackerEffect copy() {
        return new DurkwoodTrackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null
                || permanent.getZoneChangeCounter(game)
                != source.getSourceObjectZoneChangeCounter()) {
            return false;
        }
        Permanent targeted = game.getPermanent(source.getFirstTarget());
        if (targeted == null) {
            return false;
        }
        targeted.damage(permanent.getPower().getValue(), permanent.getId(), game, false, true);
        permanent.damage(targeted.getPower().getValue(), targeted.getId(), game, false, true);
        return true;
    }
}