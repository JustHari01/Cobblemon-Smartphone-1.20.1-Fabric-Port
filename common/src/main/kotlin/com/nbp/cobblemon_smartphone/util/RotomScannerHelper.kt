package com.nbp.cobblemon_smartphone.util

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.nbp.cobblemon_smartphone.social.SmartphoneDexData
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.ClipContext
import net.minecraft.world.phys.HitResult

object RotomScannerHelper {

    /**
     * Finds the PokemonEntity currently targeted by the player's crosshair within [maxDist] blocks,
     * taking line-of-sight and bounding box intersections into account.
     */
    fun findTargetedPokemon(player: Player, maxDist: Double = 10.0): PokemonEntity? {
        val eyePos = player.getEyePosition(1.0f)
        val viewVec = player.getViewVector(1.0f)
        val endPos = eyePos.add(viewVec.scale(maxDist))
        val aabb = player.boundingBox.inflate(maxDist)
        val entities = player.level().getEntitiesOfClass(PokemonEntity::class.java, aabb) { it.isAlive }

        var closest: PokemonEntity? = null
        var closestDist = maxDist

        for (entity in entities) {
            val box = entity.boundingBox.inflate(0.35)
            val hit = box.clip(eyePos, endPos)
            if (hit.isPresent) {
                val dist = eyePos.distanceTo(hit.get())
                if (dist < closestDist) {
                    // Check if block obscures line of sight
                    val blockHit = player.level().clip(
                        ClipContext(
                            eyePos,
                            hit.get(),
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.NONE,
                            player
                        )
                    )
                    if (blockHit.type == HitResult.Type.MISS) {
                        closestDist = dist
                        closest = entity
                    }
                }
            }
        }
        return closest
    }

    /**
     * Checks if the given Pokémon is unregistered or has new Pokédex information for the player.
     */
    fun isUnregisteredOrNewFor(dex: SmartphoneDexData, player: Player, entity: PokemonEntity): Boolean =
        !dex.hasSeen(player.uuid, entity.pokemon.species.resourceIdentifier)
}
