package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.CameraComponent
import forsakenharmony.gameprot.components.TransformComponent

import forsakenharmony.gameprot.utils.Constants.PPM

/**
 * @author ArmyOfAnarchists
 */
class CameraSystem : IteratingSystem {

    private var tm: ComponentMapper<TransformComponent>
    private var cm: ComponentMapper<CameraComponent>

    constructor() : super(Family.all(CameraComponent.javaClass).get()) {
        tm = ComponentMapper.getFor(TransformComponent.javaClass)
        cm = ComponentMapper.getFor(CameraComponent.javaClass)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val cam = cm.get(entity)

        if (cam.target == null) return

        val target = tm.get(cam.target)

        if (target == null) return

        val camera = cam.camera;

        val position = camera!!.position
        position.x = camera.position.x + (target.pos.x * PPM - camera.position.x) * .1f
        position.y = camera.position.y + (target.pos.y * PPM - camera.position.y) * .1f

        camera.position.set(position)

        cam.camera = camera
    }

}