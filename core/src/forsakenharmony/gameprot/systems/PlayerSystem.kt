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

/**
 * @author ArmyOfAnarchists
 */
class PlayerSystem : IteratingSystem {

    private val im: ComponentMapper<InputComponent>
    private val mm: ComponentMapper<MovementComponent>
    private val tm: ComponentMapper<TransformComponent>

    constructor() : super(Family.all(InputComponent.javaClass, MovementComponent.javaClass, TransformComponent.javaClass).get()) {
        im = ComponentMapper.getFor(InputComponent.javaClass)
        mm = ComponentMapper.getFor(MovementComponent.javaClass)
        tm = ComponentMapper.getFor(TransformComponent.javaClass)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val input = im.get(entity)
        val movement = mm.get(entity)
        val transform = tm.get(entity)

        val forward = Gdx.input.isKeyPressed(input.forwardKey)
        val back = Gdx.input.isKeyPressed(input.backKey)
        val left = Gdx.input.isKeyPressed(input.leftKey)
        val right = Gdx.input.isKeyPressed(input.rightKey)

        val forwardVector = Vector2(1f, 0f).rotate(transform.rotation)
    }
}