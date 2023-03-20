/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.qsr.customspd.actors.buffs;

import com.qsr.customspd.actors.Char;
import com.qsr.customspd.actors.hero.Hero;
import com.qsr.customspd.items.armor.glyphs.AntiMagic;
import com.qsr.customspd.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class MagicImmune extends FlavourBuff {

	public static final float DURATION = 20f;
	
	{
		type = buffType.POSITIVE;
		announced = true;
	}
	
	{
		immunities.addAll(AntiMagic.RESISTS);
	}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)){
			for (Buff b : target.buffs()){
				for (Class immunity : immunities){
					if (b.getClass().isAssignableFrom(immunity)){
						b.detach();
						break;
					}
				}
			}
			if (target instanceof Hero){
				((Hero) target).updateHT(false);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		super.detach();
		if (target instanceof Hero){
			((Hero) target).updateHT(false);
		}
	}

	@Override
	public int icon() {
		return BuffIndicator.COMBO;
	}
	
	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(0, 1, 0);
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (DURATION - visualcooldown()) / DURATION);
	}
}