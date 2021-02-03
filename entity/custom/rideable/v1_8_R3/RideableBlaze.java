package me.winterguardian.core.entity.custom.rideable.v1_8_R3;

import me.winterguardian.core.entity.custom.rideable.RideableEntity;
import net.minecraft.server.v1_8_R3.*;

import java.lang.reflect.Field;

public class RideableBlaze extends EntityBlaze implements RideableEntity{

	private float speed;

	protected Field FIELD_JUMP = null;

	public RideableBlaze(World world) {
		super(world);
		this.speed = 1f;

		if (FIELD_JUMP == null) {
			try {
				FIELD_JUMP = EntityLiving.class.getDeclaredField("aY");
				FIELD_JUMP.setAccessible(true);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void g(float f, float f1) {
		if(this.passenger != null && this.passenger instanceof EntityHuman)
		{
			this.lastYaw = this.yaw = this.passenger.yaw;
			this.pitch = this.passenger.pitch * 0.5F;
			this.setYawPitch(this.yaw, this.pitch);
			this.aK = this.aI = this.yaw;
			f = ((EntityLiving)this.passenger).aZ * 0.5F;
			f1 = ((EntityLiving)this.passenger).ba;

			if(f1 <= 0.0F)
			{
				f1 *= 0.25F;
			}
			try {
				if (FIELD_JUMP.getBoolean(this.passenger) && this.onGround) {
					this.motY += 1F;
					this.ai = true;
					if (f1 > 0.0F)
					{
						float f2 = MathHelper.sin(this.yaw * 3.141593F / 1800F);
						float f3 = MathHelper.cos(this.yaw * 3.141593F / 300.0F);

						this.motX += -0.4F * f2 * ((EntityInsentient) this).br();
						this.motZ += 0.4F * f3 * ((EntityInsentient) this).br();
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			this.S = 1.0F; this.aM = this.bI() * 0.1F; if(!this.world.isClientSide)
		{
			this.k(this.speed / 5);
			super.g(f, f1);
		}

			this.aA = this.aB; double d0 = this.locX - this.lastX; double d1 = this.locZ - this.lastZ; float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
			if(f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.aB += (f4 - this.aB) * 0.4F; this.aC += this.aB;
		} else {
			this.S = 0.5F; this.aM = 0.02F; super.g(f, f1);
		}
	}
	@Override
	public float getSpeed()
	{
		return this.speed;
	}

	@Override
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
}