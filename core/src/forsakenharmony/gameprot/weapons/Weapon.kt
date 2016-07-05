package forsakenharmony.gameprot.weapons

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import forsakenharmony.gameprot.game.World

/**
 * @author ArmyOfAnarchists
 */
abstract class Weapon(protected val reloadable: Boolean) {
    protected var ammunition: Int = Int.MIN_VALUE
    protected var maxAmmunition: Int = 0
    protected var reloadTime: Float = 0f
    protected var currentReload: Float = 0f
    protected var fireRate: Float = 0f
    protected var timeSinceLastShot: Float = 0f
    protected val engine: PooledEngine = World.engine
    
    open fun update(deltaTime: Float) {
        var flag = true
        if (timeSinceLastShot < fireRate) {
            timeSinceLastShot += deltaTime
            flag = false
        }
        if (currentReload < reloadTime) {
            currentReload += deltaTime
            flag = false
        } else if (ammunition == 0) {
            ammunition = maxAmmunition
        }
        if (ammunition == 0 && !reloadable) {
            flag = false
            currentReload = 0f
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