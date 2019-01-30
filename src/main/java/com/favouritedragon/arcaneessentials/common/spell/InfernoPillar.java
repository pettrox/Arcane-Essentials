package com.favouritedragon.arcaneessentials.common.spell;

import com.favouritedragon.arcaneessentials.ArcaneEssentials;
import com.favouritedragon.arcaneessentials.common.entity.EntityFlamePillar;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.constants.SpellType;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class InfernoPillar extends Spell {

	public InfernoPillar() {
		super(Tier.ADVANCED, 80, Element.FIRE, "inferno_pillar", SpellType.DEFENCE, 200, EnumAction.BOW, false, ArcaneEssentials.MODID);
	}

	@Override
	public boolean cast(World world, EntityPlayer caster, EnumHand hand, int ticksInUse, SpellModifiers modifiers) {
		world.spawnEntity(new EntityFlamePillar(world, caster.posX, caster.posY, caster.posZ, caster, 100,
				1 * modifiers.get(WizardryItems.blast_upgrade)));
		if (!world.isRemote) {
			WizardryUtilities.playSoundAtPlayer(caster, WizardrySounds.SPELL_LOOP_FIRE, 1 + world.rand.nextFloat()/10, 0.5F + world.rand.nextFloat()/10);
			WizardryUtilities.playSoundAtPlayer(caster, WizardrySounds.SPELL_SUMMONING, 1 + world.rand.nextFloat()/10, 0.5F + world.rand.nextFloat()/10);
		}
		return true;
	}

	@Override
	public boolean cast(World world, EntityLiving caster, EnumHand hand, int ticksInUse, EntityLivingBase target, SpellModifiers modifiers) {
		return super.cast(world, caster, hand, ticksInUse, target, modifiers);
	}

	@Override
	public boolean canBeCastByNPCs() {
		return true;
	}
}
