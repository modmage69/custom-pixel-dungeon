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

package com.qsr.customspd.sprites;

import com.qsr.customspd.Assets;
import com.qsr.customspd.actors.mobs.DM201;
import com.qsr.customspd.assets.Asset;
import com.qsr.customspd.assets.GeneralAsset;
import com.qsr.customspd.effects.MagicMissile;
import com.qsr.customspd.effects.Speck;
import com.qsr.customspd.messages.Messages;
import com.qsr.customspd.utils.GLog;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class DM201Sprite extends MobSprite {

	public DM201Sprite () {
		super();

		texture( Asset.getAssetFileHandle(GeneralAsset.DM200) );

		TextureFilm frames = new TextureFilm( texture, 21, 18 );

		int c = 12;

		idle = new Animation( 2, true );
		idle.frames( frames, c+0, c+1 );

		run = idle.clone();

		attack = new Animation( 15, false );
		attack.frames( frames, c+4, c+5, c+6 );

		zap = new Animation( 15, false );
		zap.frames( frames, c+7, c+8, c+8, c+7 );

		die = new Animation( 8, false );
		die.frames( frames, c+9, c+10, c+11 );

		play( idle );
	}

	@Override
	public void place(int cell) {
		if (parent != null) parent.bringToFront(this);
		super.place(cell);
	}

	@Override
	public void die() {
		emitter().burst( Speck.factory( Speck.WOOL ), 8 );
		super.die();
	}

	public void zap( int cell ) {

		super.zap( cell );

		MagicMissile.boltFromChar( parent,
				MagicMissile.CORROSION,
				this,
				cell,
				new Callback() {
					@Override
					public void call() {
						Sample.INSTANCE.play( Assets.Sounds.GAS );
						((DM201)ch).onZapComplete();
					}
				} );
		Sample.INSTANCE.play( Assets.Sounds.MISS, 1f, 1.5f );
		GLog.w(Messages.get(DM201.class, "vent"));
	}

	@Override
	public void onComplete( Animation anim ) {
		if (anim == zap) {
			idle();
		}
		super.onComplete( anim );
	}

	@Override
	public int blood() {
		return 0xFFFFFF88;
	}

}
