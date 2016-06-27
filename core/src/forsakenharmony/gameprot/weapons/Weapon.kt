package forsakenharmony.gameprot.weapons

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine

/**
 * @author ArmyOfAnarchists
 */
abstract class Weapon(protected val reloadable: Boolean, protected val engine: PooledEngine) {
    protected var ammunition: Int = Int.MIN_VALUE
    protected var maxAmmunition: Int = 0
    protected var reloadTime: Float = 0f
    protected var currentReload: Float = 0f
    protected var firerate: Float = 0f
    protected var timeSinceLastShot: Float = 0f

    fun update(deltaTime: Float) {
        var flag = true
        if (timeSinceLastShot < firerate) {
            timeSinceLastShot += deltaTime
            flag = false
        }
        if (currentReload < reloadTime) {
            currentReload += deltaTime
            flag = false
        } else if (ammunition == 0){
            ammunition = maxAmmunition
        }
        canBeFired = flag
    }

    var canBeFired: Boolean = true
        protected set
        get

    open fun fire(x: Float, y: Float, rot: Float, target: Entity?) {
        if (!canBeFired) return

        timeSinceLastShot = 0f
        createProjectile(x, y, rot, target)
        ammunition--

        if (ammunition != Int.MIN_VALUE && ammunition <= 0) {
            currentReload = 0f
            ammunition = 0
        }
    }

    abstract fun createProjectile(x: Float, y: Float, rot: Float, target: Entity?)
}