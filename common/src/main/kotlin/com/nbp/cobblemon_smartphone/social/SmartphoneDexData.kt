package com.nbp.cobblemon_smartphone.social

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.saveddata.SavedData
import java.util.UUID

/**
 * Cobblemon 1.20.1 does not expose the later Pokedex manager API. Keep the smartphone's
 * encounter records in world SavedData so scanner and social-share features retain their data.
 */
class SmartphoneDexData : SavedData() {
    private val seenByPlayer = mutableMapOf<UUID, MutableSet<String>>()
    private val caughtByPlayer = mutableMapOf<UUID, MutableSet<String>>()

    fun hasSeen(playerId: UUID, speciesId: ResourceLocation): Boolean =
        speciesId.toString() in seenByPlayer[playerId].orEmpty()

    fun seenSpecies(playerId: UUID): Set<ResourceLocation> =
        seenByPlayer[playerId].orEmpty().mapNotNull(ResourceLocation::tryParse).toSet()

    fun recordEncounter(player: ServerPlayer, speciesId: ResourceLocation, caught: Boolean = false): Boolean {
        val key = speciesId.toString()
        val isNew = seenByPlayer.getOrPut(player.uuid) { linkedSetOf() }.add(key)
        val wasCaught = if (caught) caughtByPlayer.getOrPut(player.uuid) { linkedSetOf() }.add(key) else false
        if (isNew || wasCaught) setDirty()
        return isNew
    }

    override fun save(tag: CompoundTag): CompoundTag {
        tag.put("seen", writeSpeciesMap(seenByPlayer))
        tag.put("caught", writeSpeciesMap(caughtByPlayer))
        return tag
    }

    private fun writeSpeciesMap(source: Map<UUID, Set<String>>): ListTag {
        val list = ListTag()
        source.forEach { (playerId, species) ->
            val record = CompoundTag()
            record.putUUID("player", playerId)
            val speciesList = ListTag()
            species.forEach { speciesId -> speciesList.add(net.minecraft.nbt.StringTag.valueOf(speciesId)) }
            record.put("species", speciesList)
            list.add(record)
        }
        return list
    }

    companion object {
        private const val NAME = "cobblemon_smartphone_dex"

        private fun load(tag: CompoundTag): SmartphoneDexData {
            val data = SmartphoneDexData()
            readSpeciesMap(tag.getList("seen", Tag.TAG_COMPOUND.toInt()), data.seenByPlayer)
            readSpeciesMap(tag.getList("caught", Tag.TAG_COMPOUND.toInt()), data.caughtByPlayer)
            return data
        }

        private fun readSpeciesMap(list: ListTag, destination: MutableMap<UUID, MutableSet<String>>) {
            list.forEach { element ->
                val record = element as? CompoundTag ?: return@forEach
                if (!record.hasUUID("player")) return@forEach
                val playerId = record.getUUID("player")
                val species = record.getList("species", Tag.TAG_STRING.toInt())
                destination.getOrPut(playerId) { linkedSetOf() }
                    .addAll(species.map { it.asString })
            }
        }

        fun get(server: MinecraftServer): SmartphoneDexData = server.overworld().dataStorage
            .computeIfAbsent(::load, ::SmartphoneDexData, NAME)
    }
}
