package com.github.sejoslaw.vanillamagic2.common.tileentities.machines.modules;

import com.github.sejoslaw.vanillamagic2.common.tileentities.machines.IVMTileMachine;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public class ForgeEnergyModule extends AbstractForgeEnergyModule {
    protected boolean canExecuteNoEnergy(IVMTileMachine machine) {
        IEnergyStorage energyStorage = (IEnergyStorage) machine.getWorld().getTileEntity(this.getEnergySourcePos(machine));
        return this.checkForgeEnergyStorage(machine, energyStorage);
    }
}
