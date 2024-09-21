package me.vaan

/**
 * Class used to keep track of multiple entries for different keys
 */
class CooldownManager<T> {
    private val timeMap = HashMap<String, HashMap<T, Long>>()
    private val cooldowns = HashMap<String, Long>()
    private val debugger: SimpleDebugger

    constructor() {
        debugger = object : SimpleDebugger {}
    }

    constructor(debugger: SimpleDebugger) {
        this.debugger = debugger
    }

    fun setCooldown(key: String, cooldown: Long) {
        cooldowns[key] = cooldown
    }

    fun checkCooldown(key: String, entry: T) : Boolean {
        val cooldown = cooldowns[key] ?: throw RuntimeException("[TimeUtils] Cooldown not set for $key")

        val cooldownTime = getEntry(key, entry)
        val current = System.currentTimeMillis()

        val output = "Time: $cooldownTime Cooldown: $cooldown Current: $current | "

        if (cooldownTime == null || cooldownTime + cooldown <= current) {
            setEntry(key, entry)
            debugger.debug(output + "Success!")
            return true
        }

        debugger.debug(output + "Fail!")
        return false
    }

    fun printAllCooldowns() {
        for (entry in cooldowns) {
            debugger.debug("Key: " + entry.key + " Value: " + entry.value)
        }
    }

    private fun getEntry(key: String, entry: T) : Long? {
        timeMap.putIfAbsent(key, HashMap())
        val playerMap = timeMap[key]!! // should never happen
        return playerMap[entry]
    }

    private fun setEntry(key: String, entry: T) {
        timeMap.putIfAbsent(key, HashMap())
        val playerMap = timeMap[key]!! // should never happen
        playerMap[entry] = System.currentTimeMillis()
        debugger.debug("Set time for $entry to ${playerMap[entry]}")
    }
}