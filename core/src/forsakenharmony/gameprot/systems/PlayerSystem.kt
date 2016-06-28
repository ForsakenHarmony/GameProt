package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import forsakenharmony.gameprot.components.InputComponent
import forsakenharmony.gameprot.components.PhysicsComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.components.WeaponComponent
import forsakenharmony.gameprot.utils.Constants.SHIP_DAMPING

/**
 * @author ArmyOfAnarchists
 */
class PlayerSystem : IteratingSystem {

    private val im: ComponentMapper<InputComponent>
    private val tm: ComponentMapper<TransformComponent>
    private val wm: ComponentMapper<WeaponComponent>
    private val pm: ComponentMapper<PhysicsComponent>


    init {
        im = ComponentMapper.getFor(InputComponent::class.java)
        tm = ComponentMapper.getFor(TransformComponent::class.java)
        wm = ComponentMapper.getFor(WeaponComponent::class.java)
        pm = ComponentMapper.getFor(PhysicsComponent::class.java)
    }

    constructor() : super(Family.all(InputComponent::class.java, TransformComponent::class.java, PhysicsComponent::class.java).get())

    override fun processEntity(entity: Entity?, deltaTime: Float) {

        val input = im.get(entity)
        val transform = tm.get(entity)
        val weapons = wm.get(entity)
        val physics = pm.get(entity)

        val forward = Gdx.input.isKeyPressed(input.forwardKey)
        val back = Gdx.input.isKeyPressed(input.backKey)
        val left = Gdx.input.isKeyPressed(input.leftKey)
        val right = Gdx.input.isKeyPressed(input.rightKey)

        val slowTurn = Gdx.input.isKeyPressed(input.slowTurnKey)

        val fire = Gdx.input.isKeyPressed(input.fireKey)

        physics.body.isActive = true
        physics.body.isAwake = true

        var forwardVector = Vector2(0f, 1f)
        forwardVector = forwardVector.rotate(transform.rotation)

        if (fire && weapons != null) {
            val bulletTransform = forwardVector.cpy().scl(0.2f).add(transform.x, transform.y)
            weapons.currentWeapon.fire(bulletTransform.x, bulletTransform.y, transform.rotation, null)
        }

        if (forward) {
            physics.body.applyForceToCenter(forwardVector.scl(playerAcceleration * physics.body.mass), true)
        } else {
        }

        physics.body.linearDamping = SHIP_DAMPING
        if (back) {
            physics.body.linearDamping = 2.2f
        }

        if (physics.body.linearVelocity.len() > maxSpeed) {
            val scale = maxSpeed / physics.body.linearVelocity.len()
            physics.body.linearVelocity.scl(scale, scale)
        }

        var rotationSpeed: Float = rotationSpeed

        if (slowTurn) {
            rotationSpeed /= 2
        }

        if (right) {
            if (!left) physics.body.angularVelocity = -rotationSpeed
        } else if (left) {
            physics.body.angularVelocity = rotationSpeed
        } else {
            physics.body.angularVelocity = 0f
        }
    }

    companion object {
        const val playerAcceleration = 20f
        const val rotationSpeed = 140f * MathUtils.degreesToRadians
        const val maxSpeed = 10f
    }
}