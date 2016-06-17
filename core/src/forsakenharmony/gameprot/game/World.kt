package forsakenharmony.gameprot.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Input
import forsakenharmony.gameprot.components.*

/**
 * @author ArmyOfAnarchists
 */
class World {
    private val engine: PooledEngine

    constructor(engine: PooledEngine) {
        this.engine = engine
    }

    fun create() {
        createBackground()
        createPlayer()
    }

    fun createPlayer(){
        val entity = engine.createEntity()

        val position = engine.createComponent(TransformComponent.javaClass)
        val movement = engine.createComponent(MovementComponent.javaClass)
        val texture = engine.createComponent(TextureComponent.javaClass)
        val input = engine.createComponent(InputComponent.javaClass)

        texture.texture = Assets.playerShipBlue[0]

        input.forwardKey = Input.Keys.W
        input.backKey = Input.Keys.S
        input.leftKey = Input.Keys.A
        input.rightKey = Input.Keys.D

        entity.add(position)
        entity.add(movement)
        entity.add(texture)
        entity.add(input)

        engine.addEntity(entity)
    }

    fun createBackground() {
        val entity = engine.createEntity()

        val background = engine.createComponent(BackgroundComponent.javaClass)
        val position = engine.createComponent(TransformComponent.javaClass)
        val texture = engine.createComponent(TextureComponent.javaClass)

        texture.texture = Assets.backgroundTile

        entity.add(background)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)
    }
}