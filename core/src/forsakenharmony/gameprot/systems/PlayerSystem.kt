package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import forsakenharmony.gameprot.components.InputComponent
import forsakenharmony.gameprot.components.MovementComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.components.WeaponComponent

/**
 * @author ArmyOfAnarchists
 */
class PlayerSystem : IteratingSystem {

    private val im: ComponentMapper<InputComponent>
    private val mm: ComponentMapper<MovementComponent>
    private val tm: ComponentMapper<TransformComponent>
    private val wm: ComponentMapper<WeaponComponent>

    private val playerAcceleration = 0.15f
    private val rotationSpeed = 180f
    private val maxSpeed = 10f

    init {
        im = ComponentMapper.getFor(InputComponent::class.java)
        mm = ComponentMapper.getFor(MovementComponent::class.java)
        tm = ComponentMapper.getFor(TransformComponent::class.java)
        wm = ComponentMapper.getFor(WeaponComponent::class.java)
    }

    constructor() : super(Family.all(InputComponent::class.java, MovementComponent::class.java, TransformComponent::class.java).get()) {

    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val input = im.get(entity)
        val movement = mm.get(entity)
        val transform = tm.get(entity)
        val weapons = wm.get(entity)

        val forward = Gdx.input.isKeyPressed(input.forwardKey)
        val back = Gdx.input.isKeyPressed(input.backKey)
        val left = Gdx.input.isKeyPressed(input.leftKey)
        val right = Gdx.input.isKeyPressed(input.rightKey)

        val slowTurn = Gdx.input.isKeyPressed(input.slowTurnKey)

        val fire = Gdx.input.isKeyPressed(input.fireKey)

        var forwardVector = Vector2(0f, 1f)
        forwardVector = forwardVector.rotate(transform.rotation)

        if (fire && weapons != null) weapons.currentWeapon.fire(transform.x, transform.y, transform.rotation, null)

        if (forward) {
            movement.acceleration.set(forwardVector.scl(playerAcceleration, playerAcceleration))
        } else {
            movement.acceleration.set(0f, 0f)
        }

        if (back) {
            movement.acceleration.set(0f, 0f)
            movement.velocity.scl(0.95f)
        }

        if (movement.velocity.len() > maxSpeed) {
            val scale = maxSpeed / movement.velocity.len()
            movement.velocity.scl(scale, scale)
        }

        var rotationSpeed = this.rotationSpeed

        if (slowTurn) {
            rotationSpeed /= 2
        }

        if (right) {
            if (!left) transform.rotation -= rotationSpeed * deltaTime
        } else if (left) {
            transform.rotation += rotationSpeed * deltaTime
        }
    }
}