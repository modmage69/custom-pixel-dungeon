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

package com.qsr.customspd.items.weapon.missiles.darts;

import com.qsr.customspd.actors.Char;
import com.qsr.customspd.actors.buffs.Adrenaline;
import com.qsr.customspd.actors.buffs.Buff;
import com.qsr.customspd.actors.buffs.Cripple;
import com.qsr.customspd.assets.GeneralAsset;

public class AdrenalineDart extends TippedDart {
	
	{
		image = GeneralAsset.ADRENALINE_DART;
	}
	
	@Override
	public int proc(Char attacker, Char defender, int damage) {

		if (processingChargedShot && defender == attacker) {
			//do nothing to the hero when processing charged shot
		} else if (attacker.alignment == defender.alignment){
			Buff.prolong( defender, Adrenaline.class, Adrenaline.DURATION);
			return 0;
		} else {
			Buff.prolong( defender, Cripple.class, Cripple.DURATION/2);
		}
		
		return super.proc(attacker, defender, damage);
	}
}
