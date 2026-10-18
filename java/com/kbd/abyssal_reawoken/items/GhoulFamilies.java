package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.fams.GhoulFamily;

public class GhoulFamilies {

    public static final GhoulFamily GHOUL = new GhoulFamily(
            "ghoul",
            1.1F,
            1.25F,
            1.1F
    )
            .targets("abyssal_reawoken:dreaded_ghoul")
            .transformOnKill(
                    "abyssal_reawoken:ghoul",
                    0.25F,
                    "byssal_reawoken:dreaded_ghoul"
            );

    public static final GhoulFamily DREADED_GHOUL = new GhoulFamily(
            "dreaded",
            1.2F,
            1.5F,
            1.2F
    )
            .targets("abyssal_reawoken:ghoul")
            .transformOnKill(
                    "abyssal_reawoken:dreaded_ghoul",
                    0.25F,
                    "abyssal_reawoken:ghoul"
            );


    // Add every other ghoul family as another static field above.

    public static void initialize() {
        // Accessing this method initializes all static family definitions.
    }
}