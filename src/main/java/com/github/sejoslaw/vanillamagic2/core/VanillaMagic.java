package com.github.sejoslaw.vanillamagic2.core;

import net.minecraftforge.fml.common.Mod;

/**
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
@Mod(VanillaMagic.MODID)
public final class VanillaMagic {
    public static final String MODID = "vanillamagic2";

    public VanillaMagic() {
        VMFiles.initialize();
        VMRegistries.initialize();
//        VMEnergy.initialize();
        VMQuests.initialize();
//        VMEvents.initialize();
    }
}
